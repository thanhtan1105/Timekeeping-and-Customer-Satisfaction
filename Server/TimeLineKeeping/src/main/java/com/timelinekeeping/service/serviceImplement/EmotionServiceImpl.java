package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.ETransaction;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CustomerServiceEntity;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.model.*;
import com.timelinekeeping.modelMCS.EmotionRecognizeResponse;
import com.timelinekeeping.modelMCS.FaceDetectResponse;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.CustomerServiceRepo;
import com.timelinekeeping.repository.EmotionContentRepo;
import com.timelinekeeping.repository.EmotionRepo;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ServiceUtils;
import com.timelinekeeping.util.UtilApps;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 10/10/2016.
 */
@Service
public class EmotionServiceImpl {

    @Autowired
    private CustomerServiceRepo customerRepo;

    @Autowired
    private EmotionRepo emotionRepo;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private EmotionContentRepo contentRepo;

    private Logger logger = LogManager.getLogger(EmotionServiceImpl.class);

    public EmotionCustomerResponse getEmotionCustomer(EmotionSessionStoreCustomer customerEmotionSession) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("CustomerCode:" + JsonUtil.toJson(customerEmotionSession));

            //getEmotion from db
            EmotionCustomerEntity emotionCamera1 = customerEmotionSession.getEmotionCamera1() != null ? emotionRepo.findOne(customerEmotionSession.getEmotionCamera1()) : null;
            EmotionCustomerEntity emotionCamera2 = customerEmotionSession.getEmotionCamera2() != null ? emotionRepo.findOne(customerEmotionSession.getEmotionCamera2()) : null;
            if (emotionCamera1 == null) {
                emotionCamera1 = emotionCamera2;
            }

            //choose db
            if (emotionCamera1 != null) {
                if (emotionCamera2 != null) {
                    emotionCamera1.merge(emotionCamera2);
                }
                return emotionAnalyzis(emotionCamera1);
            } else {
                return null;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public EmotionCustomerResponse emotionAnalyzis(EmotionCustomerEntity emotionCustomerEntity) {
        //get analysis
        EmotionAnalysisModel analysisModel = new EmotionAnalysisModel(emotionCustomerEntity);
        String messageEmotion = suggestionService.getEmotionMessage(analysisModel);
        List<EmotionContentModel> suggestion = suggestionService.getSuggestion(emotionCustomerEntity.getEmotionMost(), emotionCustomerEntity.getAge(), emotionCustomerEntity.getGender());
        //create message
        MessageModel messageModel = new MessageModel(emotionCustomerEntity);
        messageModel.setMessage(Collections.singletonList(UtilApps.formatSentence(messageEmotion)));
        messageModel.setSugguest(suggestion);
        return new EmotionCustomerResponse(analysisModel, messageModel);
    }

    public CustomerServiceModel beginTransaction(Long employeeId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //create Customer service
            AccountEntity employee = accountRepo.findOne(employeeId);
            if (employee == null) {
                logger.error(String.format(ERROR.TIME_KEEPING_ACCOUNT_ID_CANNOT_EXIST, employeeId));
                return null;
            }
            CustomerServiceEntity customerResultEntity = new CustomerServiceEntity(employee);

            //set status = BEGIN
            customerResultEntity.setStatus(ETransaction.BEGIN);
            CustomerServiceEntity entity = customerRepo.saveAndFlush(customerResultEntity);
            return new CustomerServiceModel(entity);

        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * author TrungNN
     * Web employee: end transaction
     */
    public Long changeStatusTransaction(String customerCode, ETransaction stauts) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info(String.format("customerCode = '%s', Status = ''", customerCode, stauts));
            //get Customer Service with customerCode
            CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);
            if (customerResultEntity != null
                    && (customerResultEntity.getStatus() == ETransaction.BEGIN
                    || customerResultEntity.getStatus() == ETransaction.PROCESS)) {
                //change status = END
                customerResultEntity.setStatus(stauts);
                if (stauts == ETransaction.END) {
                    customerResultEntity.calculateGrade();
                }
                customerRepo.saveAndFlush(customerResultEntity);
                return customerResultEntity.getCreateBy() != null ? customerResultEntity.getCreateBy().getId() : null;
            } else {
                logger.error("CustomerService has status: " + customerResultEntity.getStatus());
                return null;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    /**
     * upload image to transaction
     * author: hientq
     */
    public Long uploadImage(InputStream imageStream, String customerCode) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("CustomerCode: " + customerCode);

            //get Customer Service with customerCode;
            CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);
            logger.info("customer Emotion: " + JsonUtil.toJson(new CustomerServiceModel(customerResultEntity)));
            if (customerResultEntity != null && (customerResultEntity.getStatus() == ETransaction.BEGIN || customerResultEntity.getStatus() == ETransaction.PROCESS)) {

                //if status == BEGIN, change status = PROCESS. save db
                if (customerResultEntity.getStatus() == ETransaction.BEGIN) {
                    customerResultEntity.setStatus(ETransaction.PROCESS);
                    customerRepo.saveAndFlush(customerResultEntity);
                }

                //analyze emotion
                EmotionAnalysisModel emotionAnalysis = getCustomerEmotion(imageStream);
                if (emotionAnalysis != null) {
                    //save mostChoose
                    EmotionCustomerEntity emotionEntity = new EmotionCustomerEntity(emotionAnalysis, customerResultEntity);
                    EmotionCustomerEntity result = emotionRepo.saveAndFlush(emotionEntity);


                    //save to session
                    return result.getId();
                } else {
                    logger.error("********************** Cannot analyze customer emotion  ********************");
                    return null;
                }
            } else {
                logger.error("CustomerService has status: " + customerResultEntity.getStatus());
                return null;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }

    }


    /**
     * next transaction
     * author: hientq
     * call entransaction old, and create new transaction
     */
    public CustomerServiceModel nextTransaction(String customerCode, Boolean isSkip) {
        ETransaction transaction = null;
        if (isSkip == null || isSkip == false) {
            transaction = ETransaction.END;
        } else {
            transaction = ETransaction.PAUSE;
        }
        Long accountId = changeStatusTransaction(customerCode, transaction);
        if (accountId != null) {
            return beginTransaction(accountId);
        }
        return null;
    }


    public EmployeeReportCustomerService reportCustomerServiceEmployee(Integer year, Integer month, Long eployeeId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //List Date Report
            List<EmployeeReportDate> dayReports = new ArrayList<>();

            //get employee
            AccountEntity employee = accountRepo.findOne(eployeeId);
            if (employee == null) {
                return null;
            }

            //getListObject day
            List<Object[]> objs = customerRepo.reportCustomerByMonthAndEmployee(year, month, eployeeId);
            //Convert qua mapValue
            Map<Long, Object[]> mapVal = UtilApps.converListObject2Map(objs);
            //get dayMax in month
            YearMonth yearMonth = YearMonth.of(year, month);
            int dayInMonth = yearMonth.lengthOfMonth();

            //for one day create Employee Customer Report
            for (int i = 1; i <= dayInMonth; i++) {

                //create day, dateTime
                EmployeeReportDate employeeReportDate = new EmployeeReportDate();
                employeeReportDate.setDay(i);
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month - 1, i);
                employeeReportDate.setDate(calendar.getTime());


                //day type
                employeeReportDate.setDayStatus(ServiceUtils.convertDateType(employee, calendar));


                //get tu value trong map add to account
                Object[] objects = mapVal.get((long) i);
                if (objects != null && objects.length > 0) {
                    employeeReportDate.from(objects);
                }

                //add to list
                dayReports.add(employeeReportDate);
            }


            //create object return
            EmployeeReportCustomerService customerService = new EmployeeReportCustomerService(year, month, employee);
            customerService.setReportDate(dayReports);
            customerService.complete();
            return customerService;

        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public CustomerServiceReport reportCustomerService(Integer year, Integer month, Integer day, Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            AccountEntity manager = accountRepo.findOne(managerId);
            if (manager == null) {
                return null;
            }
            List<AccountEntity> accountEntities = accountRepo.findByManager(managerId);
            List<AccountReportCustomerService> accountReports = accountEntities.stream().map(AccountReportCustomerService::new).collect(Collectors.toList());

            //get report
            List<Object[]> objs = customerRepo.reportCustomerByMonth(year, month, day);
            //convert to map
            Map<Long, Object[]> mapVal = UtilApps.converListObject2Map(objs);

            //get tu value trong map add to account
            for (AccountReportCustomerService customerService : accountReports) {
                Object[] objects = mapVal.get(customerService.getId());
                if (objects != null && objects.length > 0) {
                    customerService.from(objects);
                }
            }

            //create object return

            DepartmentModel departmentModel = new DepartmentModel(manager.getDepartment());
            CustomerServiceReport customerServiceReport = new CustomerServiceReport(year, month, departmentModel, accountReports);
            customerServiceReport.complete();
            return customerServiceReport;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public EmotionAnalysisModel getCustomerEmotion(InputStream inputStreamImg)
            throws IOException, URISyntaxException {
        logger.info("[Get Customer Emotion] BEGIN SERVICE");

        byte[] bytes = IOUtils.toByteArray(inputStreamImg);


        Date dateFrom = new Date();
        //Call API
        // face detect
        FaceServiceMCSImpl faceServiceMCS = new FaceServiceMCSImpl();
        // emotion recognize
        EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(bytes));
        BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(bytes));
        logger.info("[Get Customer Emotion] face response success: " + faceResponse.isSuccess());
        logger.info("EEEEEEEEEEEEE : faceResponse: " + JsonUtil.toJson(faceResponse));
        Date dateto = new Date();
        logger.info(" -- Time Call API: " + (dateto.getTime() - dateFrom.getTime()));

        if (faceResponse.isSuccess() && faceResponse.getData() != null &&
                emotionResponse.isSuccess() && emotionResponse.getData() != null) {

            // parser face response
            List<FaceDetectResponse> faceRecognizeList = (List<FaceDetectResponse>) faceResponse.getData();
            //parser emotionRecognizeList
            List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();

            return chooseMapping(faceRecognizeList, emotionRecognizeList);

        }

        logger.info("[Get Customer Emotion] END SERVICE");
        return null;
    }

    /**
     * choose pair from list Face and list Emotion
     * author: hientq
     */
    private EmotionAnalysisModel chooseMapping(List<FaceDetectResponse> faceRecognizeList, List<EmotionRecognizeResponse> emotionRecognizeList) {
        Map<String, FaceDetectResponse> faceMap = new HashMap<>();
        for (FaceDetectResponse face : faceRecognizeList) {
            faceMap.put(face.getFaceRectangle().toFaceRectangle(), face);
        }

        long area = 0l;
        EmotionAnalysisModel analysisModel = null;
        for (EmotionRecognizeResponse emotionRecognize : emotionRecognizeList) {
            FaceDetectResponse face = faceMap.get(emotionRecognize.getFaceRectangle().toFaceRectangle());
            if (face != null) {
                long areaEmotion = emotionRecognize.getFaceRectangle().area();
                if (area < areaEmotion) {
                    area = areaEmotion;
                    analysisModel = new EmotionAnalysisModel(face, emotionRecognize);
                }

            }
        }
        return analysisModel;
    }


    public void vote(Long contentId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            contentRepo.updateVote(contentId);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}
