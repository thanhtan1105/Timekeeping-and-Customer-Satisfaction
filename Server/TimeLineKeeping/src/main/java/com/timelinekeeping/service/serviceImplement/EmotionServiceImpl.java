package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.*;
import com.timelinekeeping.entity.*;
import com.timelinekeeping.model.*;
import com.timelinekeeping.modelMCS.*;
import com.timelinekeeping.repository.*;
import com.timelinekeeping.service.blackService.SuggestionService;
import com.timelinekeeping.util.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
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
    AccountServiceImpl accountService;

    @Autowired
    private CustomerServiceRepo customerServiceRepo;

    @Autowired
    private CustomerRepo customerRepo;


    @Autowired
    private EmotionRepo emotionRepo;

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private EmotionContentRepo contentRepo;

    @Autowired
    FaceServiceMCSImpl faceServiceMCS;

    // emotion recognize
    @Autowired
    EmotionServiceMCSImpl emotionServiceMCS;

    @Autowired
    PersonServiceMCSImpl personServiceMCS;

    @Autowired
    PersonGroupServiceMCSImpl personGroupServiceMCS;

    @Autowired
    ConfigurationRepo configurationRepo;


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
                emotionCamera2 = null;
            }

            //choose db
            if (emotionCamera1 == null) {
                return null;
            }

            if (emotionCamera2 != null) {
                emotionCamera1.merge(emotionCamera2);
            }

            EmotionCustomerResponse emotionCustomerResponse = emotionAnalyzis(emotionCamera1);

            //add customer code
            emotionCustomerResponse.setCustomerCode(customerEmotionSession.getCustomerCode());

            // configuration
            ConfigurationEntity configurationEntity = configurationRepo.findByKey(IContanst.EMOTION_ADVANCE_KEY);
            if (configurationEntity != null && Integer.valueOf(configurationEntity.getValue()) == EConfiguration.ALLOW.getIndex()) {
                /** Update History*/
                updateHistoryIntoResponse(emotionCustomerResponse, customerEmotionSession.getCustomerCode());
                emotionCustomerResponse.setEmotionAdvance(true);
            } else {
                emotionCustomerResponse.setEmotionAdvance(false);
            }

            logger.info("Response:" + JsonUtil.toJson(emotionCustomerResponse));
            return emotionCustomerResponse;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    private void updateHistoryIntoResponse(EmotionCustomerResponse emotionCustomerResponse, String customerCode) {

        // emotionCustomer
        CustomerServiceEntity customerServiceEntity = customerServiceRepo.findByCustomerCode(customerCode);

        //set Customer
        CustomerEntity customerEntity = customerServiceEntity.getCustomerInformation();
        if (customerEntity != null) {

            emotionCustomerResponse.setCustomerInformation(new CustomerModel(customerEntity));

            //get All customer service
            List<CustomerServiceEntity> customerServiceEntityList = customerServiceRepo.findByCustomer(customerEntity.getId());

            if (ValidateUtil.isNotEmpty(customerServiceEntityList)) {

                //convert list customer service to list suggestion
                List<String> suggestHistories = new ArrayList<>();
                customerServiceEntityList.stream().filter(customerService -> ValidateUtil.isNotEmpty(customerEntity.getName()) && ValidateUtil.isNotEmpty(customerService.getContentTransaction())).forEach(customerService -> {
                    String suggestHistory = suggestionService.convertHistory(customerEntity.getName(), customerEntity.getGender(), customerService.getContentTransaction());
                    suggestHistory = UtilApps.formatSentence(suggestHistory);
                    suggestHistories.add(suggestHistory);
                });

                emotionCustomerResponse.setCustomerHistory(suggestHistories);
            }

            /*** update suggestion*/
            /** replace*/
            if (ValidateUtil.isNotEmpty(customerEntity.getName()) && customerEntity.getGender() != null) {
                List<EmotionContentModel> suggestion = suggestionService.getSuggestion(emotionCustomerResponse.getAnalyzes().getEmotionMost(), customerEntity.getName(), customerEntity.getGender());
                emotionCustomerResponse.getMessages().setSugguest(suggestion);
            }
        }

    }

    private EmotionCustomerResponse emotionAnalyzis(EmotionCustomerEntity emotionCustomerEntity) {

        //prepare

        //get analysis
        EmotionAnalysisModel analysisModel = new EmotionAnalysisModel(emotionCustomerEntity);


        // add emotion percent

        EmotionRecognizeScores emotionScores = analysisModel.getEmotion();
        emotionScores.clearData(IContanst.EXCEPTION_VALUE);

        Map<EEmotion, Double> map = emotionScores.map();

        //get emotionCompar
        List<EmotionCompare> emotionCompares = ServiceUtils.getEmotionExist(map);


        // get messageEmotion
        String messageEmotion = suggestionService.getEmotionMessage(analysisModel);
        List<EmotionContentModel> suggestion = suggestionService.getSuggestion(emotionCustomerEntity.getEmotionMost(), emotionCustomerEntity.getAge(), emotionCustomerEntity.getGender());


        //create message
        MessageModel messageModel = new MessageModel(emotionCustomerEntity);
        messageModel.setMessage(Collections.singletonList(UtilApps.formatSentence(messageEmotion)));
        messageModel.setSugguest(suggestion);
        EmotionCustomerResponse emotionCustomerResponse = new EmotionCustomerResponse(analysisModel, messageModel);


        //add to model
        emotionCustomerResponse.setEmotionPercent(emotionCompares);


        return emotionCustomerResponse;
    }

    public CustomerServiceModel beginTransaction(Long employeeId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //create Customer service
            AccountEntity employee = accountRepo.findOne(employeeId);
            if (employee == null) {
                logger.error(String.format(ERROR.ACCOUNT_ID_CANNOT_EXIST, employeeId));
                return null;
            }
            CustomerServiceEntity customerResultEntity = new CustomerServiceEntity(employee);

            //set status = BEGIN
            customerResultEntity.setStatus(ETransaction.BEGIN);

            //set rpmanager and set rp deparment
            //customerResultEntity.setRpDepartmentId(employee.getDepartment().getId());
            CustomerServiceEntity entity = customerServiceRepo.saveAndFlush(customerResultEntity);
            return new CustomerServiceModel(entity);

        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * author TrungNN
     * Web employee: end transaction
     */
    public Long changeStatusTransaction(String customerCode, ETransaction stauts) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info(String.format("customerCode = '%s', Status = ''", customerCode, stauts));
            //get Customer Service with customerCode
            CustomerServiceEntity customerResultEntity = customerServiceRepo.findByCustomerCode(customerCode);
            if (customerResultEntity != null
                    && (customerResultEntity.getStatus() == ETransaction.BEGIN
                    || customerResultEntity.getStatus() == ETransaction.PROCESS)) {
                //change status = END
                customerResultEntity.setStatus(stauts);
                if (stauts == ETransaction.END) {
                    customerResultEntity.calculateGrade();
                }

                // configuration
                ConfigurationEntity configurationEntity = configurationRepo.findByKey(IContanst.EMOTION_ADVANCE_KEY);
                if (configurationEntity != null && Integer.valueOf(configurationEntity.getValue()) == EConfiguration.ALLOW.getIndex()) {
                    /**history */
                    personGroupServiceMCS.trainGroup(IContanst.DEPARTMENT_MICROSOFT_CUSTOMER);
                }


                customerServiceRepo.saveAndFlush(customerResultEntity);
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
    public Long uploadImage(byte[] bytesImage, String customerCode) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("CustomerCode: " + customerCode);

//            byte[] bytesImage = IOUtils.toByteArray(imageStream);
            //get Customer Service with customerCode;
            CustomerServiceEntity customerResultEntity = customerServiceRepo.findByCustomerCode(customerCode);
            logger.info("customer Emotion: " + JsonUtil.toJson(new CustomerServiceModel(customerResultEntity)));
            if (customerResultEntity != null && (customerResultEntity.getStatus() == ETransaction.BEGIN || customerResultEntity.getStatus() == ETransaction.PROCESS)) {

                //if status == BEGIN, change status = PROCESS. save db
                if (customerResultEntity.getStatus() == ETransaction.BEGIN) {
                    customerResultEntity.setStatus(ETransaction.PROCESS);
                    customerServiceRepo.saveAndFlush(customerResultEntity);
                }

                //analyze emotion
                EmotionAnalysisModel emotionAnalysis = getCustomerEmotion(bytesImage);
                if (emotionAnalysis == null) {
                    logger.error("********************** Cannot analyze customer emotion  ********************");
                    return null;
                }


                //save mostChoose
                EmotionCustomerEntity emotionEntity = new EmotionCustomerEntity(emotionAnalysis, customerResultEntity);
                EmotionCustomerEntity result = emotionRepo.saveAndFlush(emotionEntity);

                ConfigurationEntity configurationEntity = configurationRepo.findByKey(IContanst.EMOTION_ADVANCE_KEY);
                if (configurationEntity != null && Integer.valueOf(configurationEntity.getValue()) == EConfiguration.ALLOW.getIndex()) {
                    /** history customer*/
                    historyCustomer(customerResultEntity, emotionAnalysis.getFaceId(), bytesImage);
                }

                //save to session
                return result.getId();
            } else {
                logger.error("CustomerService has status: " + customerResultEntity.getStatus());
                return null;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }

    }

    public void historyCustomer(CustomerServiceEntity customerResultEntity, String faceId, byte[] bytesImage) throws IOException, URISyntaxException {

        // identify customer Emotion
        CustomerEntity customerEntity = customerResultEntity.getCustomerInformation();
        if (customerEntity == null) {


            //identify
            String personID = identifyCustomer(faceId);
            if (personID != null) {

                customerEntity = customerRepo.findByCode(personID);
                //if exist -> asign to customer emotion. store db
                if (customerEntity == null) {
                    // save image new customer
                    customerEntity = new CustomerEntity();
                    customerEntity.setCode(personID);
                    customerEntity = customerRepo.save(customerEntity);
                }
                //if not exist -> create customer emotion -> add to db, create customer db. add to face to image, size image

                //connect with customerService
                customerResultEntity.setCustomerInformation(customerEntity);
                customerServiceRepo.save(customerResultEntity);

            } else {
                //if null -> create customer emotion. add faceto, add to fb
                //create new MCS
                BaseResponse response = personServiceMCS.createPerson(IContanst.DEPARTMENT_MICROSOFT_CUSTOMER, ".", ".");
                if (response.isSuccess()) {
                    Map<String, String> map = (Map<String, String>) response.getData();
                    personID = map.get("personId");
                    logger.info("personCode: " + personID);

                    //create in db
                    customerEntity = new CustomerEntity();
                    customerEntity.setCode(personID);
                    customerEntity = customerRepo.save(customerEntity);

                    //connect with customerService
                    customerResultEntity.setCustomerInformation(customerEntity);
                    customerResultEntity = customerServiceRepo.save(customerResultEntity);
                }


            }
        }

        if (customerEntity.getImageSize() < IContanst.SIZE_IMAGE_CUSTOMER_TRAINING) {
            BaseResponse responseFace = personServiceMCS.addFaceImg(IContanst.DEPARTMENT_MICROSOFT_CUSTOMER, customerEntity.getCode(), bytesImage);
            if (responseFace.isSuccess() == true) {
                Map<String, String> mapResult = (Map<String, String>) responseFace.getData();
                if (mapResult != null && mapResult.size() > 0) {
                    String persistedFaceID = mapResult.get("persistedFaceId");
                    // find one
                    customerEntity.setImageSize(customerEntity.getImageSize() + 1);
                    customerRepo.save(customerEntity);
                }

            }
        }
        //find in data customer


        //if size < 5 - add , if size > 5 if confidance > 0.8 mới add
        // update image in db and MCS

    }

    private String identifyCustomer(String faceId) throws IOException, URISyntaxException {

        Double confidenceValue = 0.5d;
        ConfigurationEntity configurationEntity = configurationRepo.findByKey(IContanst.EMOTION_ADVANCE_CONFIDENCE_KEY);
        if (configurationEntity != null) {
            /** Update History*/
            confidenceValue = Double.valueOf(configurationEntity.getValue());
        }

        BaseResponse response = faceServiceMCS.identify(IContanst.DEPARTMENT_MICROSOFT_CUSTOMER, faceId);
        if (response.isSuccess()) {

            List<FaceIdentifyConfidenceRespone> confidenceResponeList = (List<FaceIdentifyConfidenceRespone>) response.getData();

            if (ValidateUtil.isNotEmpty(confidenceResponeList)) {
                List<FaceIdentityCandidate> candidates = confidenceResponeList.get(0).getCandidates();

                double confidence = 0d;
                String personId = null;
                for (FaceIdentityCandidate candidate : candidates) {
                    if (candidate.getConfidence() > confidence) {
                        confidence = candidate.getConfidence();
                        personId = candidate.getPersonId();
                    }
                }

                if (confidence >= confidenceValue) {
                    return personId;
                }
            }
        }
        return null;
    }


    /**
     * next transaction
     * author: hientq
     * call entransaction old, and create new transaction
     */
    public CustomerServiceModel nextTransaction(String customerCode, Boolean isSkip) throws IOException, URISyntaxException {
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
            List<Object[]> objs = customerServiceRepo.reportCustomerByMonthAndEmployee(year, month, eployeeId);
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

            logger.info("Return Json: " + JsonUtil.toJson(customerService));
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
            AccountEntity managerEntity = accountRepo.findOne(managerId);
            List<AccountEntity> accountEntities = accountRepo.findEmployeeByDepartmentNoActive(managerEntity.getDepartment().getId());
            List<AccountReportCustomerService> accountReports = accountEntities.stream().map(AccountReportCustomerService::new).collect(Collectors.toList());

            Pair<Date, Date> datePair = null;
            if (day > 0) {
                datePair = TimeUtil.createDayBetween(new DateTime(year, month, day, 0, 0).toDate());
            } else {
                datePair = TimeUtil.createMonthBetween(new DateTime(year, month, 1, 0, 0).toDate());
            }
            //get report
            List<ReportCustomerEmotionQuery> listquery = customerServiceRepo.reportCustomerByMonth(datePair.getKey(), datePair.getValue());
            //convert to map
            Map<Long, ReportCustomerEmotionQuery> mapVal = listquery.stream().collect(Collectors.toMap(rp -> rp.getId(), rp -> rp));

            //get tu value trong map add to account
            for (AccountReportCustomerService customerService : accountReports) {
                ReportCustomerEmotionQuery report = mapVal.get(customerService.getId());
                if (report != null) {
                    customerService.from(report);
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


    public EmotionAnalysisModel getCustomerEmotion(byte[] byteImage)
            throws IOException, URISyntaxException {
        logger.info("[Get Customer Emotion] BEGIN SERVICE");

//        byte[] bytes = IOUtils.toByteArray(inputStreamImg);


        Date dateFrom = new Date();
        //Call API
        // face detect
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(byteImage));
        BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(byteImage));
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

            EmotionAnalysisModel analysisModel = chooseMapping(faceRecognizeList, emotionRecognizeList);


            //

            return analysisModel;

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

    public Pair<Boolean, Object> updateCustomer(CustomerTransactionModel customerTransactionModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            if (customerTransactionModel == null || ValidateUtil.isEmpty(customerTransactionModel.getCustomerCode())) {
                return new Pair<>(false);
            }

            CustomerEntity customerEntity = null;
            if (customerTransactionModel.getId() != null) {
                customerEntity = customerRepo.findOne(customerTransactionModel.getId());
            }

            if (customerEntity == null) {
                customerEntity = new CustomerEntity();
            }

            customerEntity.update(customerTransactionModel);

            //store customer
            CustomerEntity customerResponse = customerRepo.save(customerEntity);


            //update customer service
            CustomerServiceEntity customerServiceEntity = customerServiceRepo.findByCustomerCode(customerTransactionModel.getCustomerCode());
            if (customerServiceEntity != null) {
                customerServiceEntity.setContentTransaction(customerTransactionModel.getContent());
                customerServiceEntity.setCustomerInformation(customerResponse);
                customerServiceRepo.save(customerServiceEntity);
            }

            return new Pair<>(true, new Pair<String, Long>("customerId", customerResponse.getId()));

        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


}
