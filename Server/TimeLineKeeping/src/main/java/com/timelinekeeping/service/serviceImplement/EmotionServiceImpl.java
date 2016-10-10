package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.ETransaction;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CustomerServiceEntity;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.model.*;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.CustomerServiceRepo;
import com.timelinekeeping.repository.EmotionRepo;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    private Logger logger = LogManager.getLogger(EmotionServiceImpl.class);

    public EmotionCustomerResponse getEmotionCustomer(String customerCode) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //get Customer Service with customerCode
            CustomerServiceEntity customerResultEntity = customerRepo.findByCustomerCode(customerCode);
            if (customerResultEntity != null
                    && (customerResultEntity.getStatus() == ETransaction.BEGIN
                    || customerResultEntity.getStatus() == ETransaction.PROCESS)) {
                Long customerId = customerResultEntity.getId();
                EmotionCustomerEntity emotionCustomerEntity = emotionRepo.findByCustomerIdLeast(customerId);
                logger.info("emotionCustomer Exist: " + emotionCustomerEntity != null);
                if (emotionCustomerEntity != null) {
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

    public CustomerServiceModel beginTransaction(Long employeeId){
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
}
