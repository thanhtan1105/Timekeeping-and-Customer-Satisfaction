package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.constant.*;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CustomerServiceEntity;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.entity.MessageEntity;
import com.timelinekeeping.model.*;
import com.timelinekeeping.modelMCS.EmotionRecognizeResponse;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.modelMCS.FaceDetectResponse;
import com.timelinekeeping.modelMCS.RectangleImage;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.CustomerServiceRepo;
import com.timelinekeeping.repository.EmotionRepo;
import com.timelinekeeping.repository.MessageRepo;
import com.timelinekeeping.util.ServiceUtils;
import com.timelinekeeping.util.UtilApps;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lethanhtan on 9/15/16.
 */
@Service
public class EmotionServiceImpl {

    private Logger logger = Logger.getLogger(EmotionServiceImpl.class);

    @Autowired
    private EmotionRepo emotionRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private CustomerServiceRepo customerRepo;

    @Autowired
    private SuggestionService suggestionService;

    public BaseResponse save(InputStream inputStreamImg, Long employeeId, boolean isFirstTime) throws IOException, URISyntaxException {

        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse responseResult = new BaseResponse();
        byte[] bytes = IOUtils.toByteArray(inputStreamImg);

        // emotion
        EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
        BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(bytes));

        // face detect
        FaceServiceMCSImpl faceServiceMCS = new FaceServiceMCSImpl();
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(bytes));

        if (isFirstTime) {
            // add suggess
        }

        // TODO: implement multi thread
        // parser emotion response
        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();
        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

        // parser face response
        List<FaceDetectResponse> faceRecognizeList = (List<FaceDetectResponse>) faceResponse.getData();
        FaceDetectResponse faceDetectResponse = faceRecognizeList.get(0);

        // create time
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Double anger = emotionRecognize.getScores().getAnger(); // get anger
        Double contempt = emotionRecognize.getScores().getContempt(); // get contempt
        Double disgust = emotionRecognize.getScores().getDisgust(); // get Disgust
        Double fear = emotionRecognize.getScores().getFear(); // get Fear
        Double happiness = emotionRecognize.getScores().getHappiness(); // get happiness
        Double neutral = emotionRecognize.getScores().getNeutral(); // get neutral
        Double sadness = emotionRecognize.getScores().getSadness(); // get sadness
        Double surprise = emotionRecognize.getScores().getSurprise(); // get surprise
        Double age = faceDetectResponse.getFaceAttributes().getAge();
        Gender gender = faceDetectResponse.getFaceAttributes().getGender().toUpperCase().equals("MALE") ? Gender.MALE : Gender.FEMALE;
        Double smile = faceDetectResponse.getFaceAttributes().getSmile();

        // save to database
        EmotionCustomerEntity emotionCustomerEntity = new EmotionCustomerEntity(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise, age, gender, smile);
//        emotionCustomerEntity.setCreateBy(accountRepo.findOne(employeeId));
        EmotionCustomerEntity emotionRespone = emotionRepo.saveAndFlush(emotionCustomerEntity);

        logger.info(IContanst.END_METHOD_SERVICE);
        return new BaseResponse(true, emotionRespone);
    }

    public BaseResponse analyseEmotion(Long id) {
        logger.info("[Analyse Emotion] BEGIN");
        BaseResponse baseResponse = new BaseResponse();
        EmotionCustomerEntity emotion = emotionRepo.findOne(id);
        if (emotion != null) {
            logger.info("[Analyse Emotion] anger: " + emotion.getAnger());
            logger.info("[Analyse Emotion] contempt: " + emotion.getContempt());
            logger.info("[Analyse Emotion] disgust: " + emotion.getDisgust());
            logger.info("[Analyse Emotion] fear: " + emotion.getFear());
            logger.info("[Analyse Emotion] happiness: " + emotion.getHappiness());
            logger.info("[Analyse Emotion] neutral: " + emotion.getNeutral());
            logger.info("[Analyse Emotion] sadness: " + emotion.getSadness());
            logger.info("[Analyse Emotion] surprise: " + emotion.getSurprise());
            Map<String, Double> listEmotions = new HashMap<String, Double>();
            listEmotions.put("anger", emotion.getAnger());
            listEmotions.put("contempt", emotion.getContempt());
            listEmotions.put("disgust", emotion.getDisgust());
            listEmotions.put("fear", emotion.getFear());
            listEmotions.put("happiness", emotion.getHappiness());
            listEmotions.put("neutral", emotion.getNeutral());
            listEmotions.put("sadness", emotion.getSadness());
            listEmotions.put("surprise", emotion.getSurprise());
            Map.Entry<String, Double> maxEntry = null;
            for (Map.Entry<String, Double> entry : listEmotions.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            logger.info("[Analyse Emotion] current emotion: " + maxEntry.getKey());
            logger.info("[Analyse Emotion] ratio: " + maxEntry.getValue());
            baseResponse.setData(maxEntry);
        }
        logger.info("[Analyse Emotion] END");
        return baseResponse;
    }

    private EmotionAnalysisModel analyseEmotion(EmotionRecognizeScores emotionScores) {
        logger.info("[Analyse Emotion Service] BEGIN SERVICE");
        Map<EEmotion, Double> listEmotions = new HashMap<EEmotion, Double>();
        listEmotions.put(EEmotion.ANGER, emotionScores.getAnger());
        listEmotions.put(EEmotion.CONTEMPT, emotionScores.getContempt());
        listEmotions.put(EEmotion.DISGUST, emotionScores.getDisgust());
        listEmotions.put(EEmotion.FEAR, emotionScores.getFear());
        listEmotions.put(EEmotion.HAPPINESS, emotionScores.getHappiness());
        listEmotions.put(EEmotion.NEUTRAL, emotionScores.getNeutral());
        listEmotions.put(EEmotion.SADNESS, emotionScores.getSadness());
        listEmotions.put(EEmotion.SURPRISE, emotionScores.getSurprise());
        Map.Entry<EEmotion, Double> maxEntry = null;
        for (Map.Entry<EEmotion, Double> entry : listEmotions.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        logger.info("[Analyse Emotion Service] emotionMost: " + maxEntry.getKey());
        logger.info("[Analyse Emotion Service] END SERVICE");

        return new EmotionAnalysisModel(maxEntry.getKey(), emotionScores);
    }

    private List<MessageEntity> getListMessage(Gender gender, Double fromAge, Double toAge, EEmotion emotion) {

        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        List<MessageEntity> messageEntities = messageRepo.getListMessage(fromAge, toAge, gender.getIndex(), emotion.getIndex());
        logger.info(IContanst.END_METHOD_SERVICE);
        return messageEntities;
    }

    public List<EmotionAnalysisModel> getCustomerEmotion(InputStream inputStreamImg)
            throws IOException, URISyntaxException {
        logger.info("[Get Customer Emotion] BEGIN SERVICE");

        List<EmotionAnalysisModel> emotionAnalysisModels = new ArrayList<>();
        byte[] bytes = IOUtils.toByteArray(inputStreamImg);

        // face detect
        FaceServiceMCSImpl faceServiceMCS = new FaceServiceMCSImpl();
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(bytes));
        logger.info("[Get Customer Emotion] face response success: " + faceResponse.isSuccess());

        if (faceResponse.isSuccess() && faceResponse.getData() != null) {

            // parser face response
            List<FaceDetectResponse> faceRecognizeList = (List<FaceDetectResponse>) faceResponse.getData();
            if (faceRecognizeList != null || faceRecognizeList.size() > 0) {

                for (FaceDetectResponse faceDetectResponse : faceRecognizeList) {
                    // get face_attributes
                    Double age = faceDetectResponse.getFaceAttributes().getAge(); // get age
                    Gender gender = faceDetectResponse.getFaceAttributes().getGender().toUpperCase()
                            .equals("MALE") ? Gender.MALE : Gender.FEMALE; // get gender
                    // get rectangle image
                    RectangleImage rectangleImage = faceDetectResponse.getFaceRectangle();

                    // emotion recognize
                    EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
                    BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(bytes), rectangleImage);

                    // parser emotion response
                    List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();

                    //TODO check many face
                    EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

                    // get emotion_scores
                    Double anger = emotionRecognize.getScores().getAnger(); // get anger
                    Double contempt = emotionRecognize.getScores().getContempt(); // get contempt
                    Double disgust = emotionRecognize.getScores().getDisgust(); // get Disgust
                    Double fear = emotionRecognize.getScores().getFear(); // get Fear
                    Double happiness = emotionRecognize.getScores().getHappiness(); // get happiness
                    Double neutral = emotionRecognize.getScores().getNeutral(); // get neutral
                    Double sadness = emotionRecognize.getScores().getSadness(); // get sadness
                    Double surprise = emotionRecognize.getScores().getSurprise(); // get surprise

                    EmotionRecognizeScores emotionRecognizeScores
                            = new EmotionRecognizeScores(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise);
                    // get customer emotion
                    EmotionAnalysisModel emotionAnalysisModel = analyseEmotion(emotionRecognizeScores);
                    emotionAnalysisModel.setAge(age);
                    emotionAnalysisModel.setGender(gender);
                    emotionAnalysisModel.setRectangleImage(faceDetectResponse.getFaceRectangle());

                    emotionAnalysisModels.add(emotionAnalysisModel);

                }
            }
        }

        logger.info("[Get Customer Emotion] END SERVICE");
        return emotionAnalysisModels;
    }

    public MessageModel suggestMessage(EmotionAnalysisModel emotionAnalysisModel) {
        try {
//            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
//            List<MessageEntity> messageEntities = getListMessage(emotionAnalysisModel.getGender(),
//                    emotionAnalysisModel.getAge() - IContanst.AGE_AMOUNT,
//                    emotionAnalysisModel.getAge() + IContanst.AGE_AMOUNT,
//                    emotionAnalysisModel.getEmotionMost());
//            return messageEntities.stream().map(MessageModel::new).collect(Collectors.toList());
            return null;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Pair<EmotionCustomerResponse, String> beginTransaction(InputStream imageStream, Long employeeId) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //Create Customer service
            AccountEntity employee = accountRepo.findOne(employeeId);
            if (employee == null) {
                logger.error(String.format(ERROR.TIME_KEEPING_ACCOUNT_ID_CANNOT_EXIST, employeeId));
                return new Pair<>(null, String.format(ERROR.TIME_KEEPING_ACCOUNT_ID_CANNOT_EXIST, employeeId));
            }
            CustomerServiceEntity customerResultEntity = customerRepo.saveAndFlush(new CustomerServiceEntity(employee));

            List<EmotionAnalysisModel> listEmotionAnalysis = getCustomerEmotion(imageStream);
            if (listEmotionAnalysis != null && listEmotionAnalysis.size() > 0) {
                //TODO choose best way
                EmotionAnalysisModel mostChoose = listEmotionAnalysis.get(0);

                //save mostChoose
                EmotionCustomerEntity emotionEntity = new EmotionCustomerEntity(mostChoose, customerResultEntity);
                emotionRepo.saveAndFlush(emotionEntity);

                //getMessage
                MessageModel messageModel = null;
                if (listEmotionAnalysis != null && listEmotionAnalysis.size() > 0) {
                    messageModel = suggestMessage(mostChoose);
                }
                EmotionCustomerResponse responseEmotion = new EmotionCustomerResponse(customerResultEntity.getCustomerCode(), mostChoose, messageModel);
                return new Pair<>(responseEmotion);
            } else {
                return new Pair<>(null, "Cannot analyze image.");
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Boolean processTransaction(InputStream imageStream, String customerCode) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("CustomerCode: " + customerCode);

            //get Customer Service with customerCode;
            CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);

            if (customerResultEntity != null && (customerResultEntity.getStatus() == ETransaction.BEGIN || customerResultEntity.getStatus() == ETransaction.PROCESS)) {

                //if status == BEGIN, change status = PROCESS. save db
                if (customerResultEntity.getStatus() == ETransaction.BEGIN) {
                    customerResultEntity.setStatus(ETransaction.PROCESS);
                    customerRepo.saveAndFlush(customerResultEntity);
                }

                //analyze emotion
                List<EmotionAnalysisModel> listEmotionAnalysis = getCustomerEmotion(imageStream);
                if (listEmotionAnalysis != null && listEmotionAnalysis.size() > 0) {
                    //TODO choose best way
                    EmotionAnalysisModel mostChoose = listEmotionAnalysis.get(0);

                    //save mostChoose
                    EmotionCustomerEntity emotionEntity = new EmotionCustomerEntity(mostChoose, customerResultEntity);
                    emotionRepo.saveAndFlush(emotionEntity);
                } else {
                    logger.error("Cannot analyze customer emotion");
                    return false;
                }
            } else {
                logger.error("CustomerService has status: " + customerResultEntity.getStatus());
                return false;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
        return true;
    }

    public Boolean shouldEndTransaction(String customerCode) {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info("CustomerCode: " + customerCode);
        //get Customer Service with customerCode;
        CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);
        return customerResultEntity.getStatus() == ETransaction.END ? true : false;
    }

    /**
     * @author TrungNN
     * Web employee: begin transaction
     */
    public CustomerServiceEntity beginTransactionWeb(Long employeeId) {
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
            return customerRepo.saveAndFlush(customerResultEntity);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * @author TrungNN
     * Web employee: get 1st emotion
     */
    public EmotionCustomerResponse getFirstEmotionWeb(String customerCode) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //get Customer Service with customerCode
            CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);
            if (customerResultEntity != null
                    && (customerResultEntity.getStatus() == ETransaction.BEGIN
                    || customerResultEntity.getStatus() == ETransaction.PROCESS)) {
                Long customerId = customerResultEntity.getId();
                List<EmotionCustomerEntity> emotionCustomerEntities = emotionRepo.findByCustomerId(customerId);
                logger.info("[API Service- Get First Customer Emotion Web] list emotion customer[size]: "
                        + emotionCustomerEntities.size());
                if (emotionCustomerEntities != null && emotionCustomerEntities.size() > 0) {
                    EmotionCustomerEntity emotionCustomerEntity= emotionCustomerEntities.get(0);

                    //get analysis
                    EmotionAnalysisModel analysisModel = new EmotionAnalysisModel(emotionCustomerEntity);
                    String messageEmotion = suggestionService.getEmotionMessage(analysisModel);
                    String sugguestion = suggestionService.getSuggestion(emotionCustomerEntity.getEmotionMost(), emotionCustomerEntity.getAge(), emotionCustomerEntity.getGender());

                    //create message
                    MessageModel messageModel = new MessageModel(emotionCustomerEntity);
                    messageModel.setMessage(Arrays.asList(UtilApps.formatSentence(messageEmotion)));
                    messageModel.setSugguest(Arrays.asList(UtilApps.formatSentence(sugguestion)));
                    return new EmotionCustomerResponse(customerCode, analysisModel, messageModel);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * @author TrungNN
     * Web employee: end transaction
     */
    public Boolean endTransactionWeb(String customerCode) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //get Customer Service with customerCode
            CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);
            if (customerResultEntity != null
                    && (customerResultEntity.getStatus() == ETransaction.BEGIN
                    || customerResultEntity.getStatus() == ETransaction.PROCESS)) {
                //change status = END
                customerResultEntity.setStatus(ETransaction.END);
                customerResultEntity.calculateGrade();
                customerRepo.saveAndFlush(customerResultEntity);
                return true;
            } else {
                logger.error("CustomerService has status: " + customerResultEntity.getStatus());
                return false;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * @author TanLT
     * Mobile employee: begin transaction
     */
    public CustomerServiceEntity beginTransactionMobile(Long employeeId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            CustomerServiceEntity customerServiceEntity = customerRepo.getLastCustomerById(employeeId);
            return customerServiceEntity;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    /**
     * @author TanLT
     * Mobile employee: start transaction
     */
    public Boolean startTransactionMobile(String customerCode) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            CustomerServiceEntity customerServiceEntity = customerRepo.startTransactionByCustomercode(customerCode);
            customerServiceEntity.setStatus(ETransaction.PROCESS);
            customerRepo.saveAndFlush(customerServiceEntity);
            return true;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public EmployeeReportCustomerService reportCustomerServiceEmployee(Integer year, Integer month, Long eployeeId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //List Date Report
            List<EmployeeReportDate> dayReports = new ArrayList<>();

            //get employee
            AccountEntity employee = accountRepo.findById(eployeeId);
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
            AccountEntity manager = accountRepo.findById(managerId);
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
}
