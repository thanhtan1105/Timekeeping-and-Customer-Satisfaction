package com.timelinekeeping.api;

import com.timelinekeeping._config.EmotionSession;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.blackService.SuggestionService;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by HienTQSE60896 on 10/10/2016.
 */
@RestController
@RequestMapping(I_URI.API_EMOTION)
public class CustomerEmotionController {

    private Logger logger = LogManager.getLogger(CustomerEmotionController.class);

    @Autowired
    private EmotionServiceImpl emotionService;

    @Autowired
    private SuggestionService suggestionService;

    @RequestMapping(value = I_URI.API_EMOTION_GET_EMOTION, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse getEmotion(@RequestParam(I_URI.PARAMETER_EMOTION_ACCOUNT_ID) Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());


            //get emotion from session
            EmotionSessionStoreCustomer customerValue = EmotionSession.getValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (customerValue == null || ValidateUtil.isEmpty(customerValue.getCustomerCode())) {
                return new BaseResponse(false);
            }

            logger.info(String.format("Session = '%s' ", JsonUtil.toJson(customerValue)));

            //call service
            EmotionCustomerResponse emotionCustomer = emotionService.getEmotionCustomer(customerValue);


            if (emotionCustomer != null) {
                emotionCustomer.setFinal(customerValue.getFinal());
                emotionCustomer.setAwsUrl(customerValue.getUriImage());
                return new BaseResponse(true, emotionCustomer);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_EMOTION_GET_IMAGE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse getImage(@RequestParam(I_URI.PARAMETER_EMOTION_ACCOUNT_ID) Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("accountId = '%s' ", accountId));

            //get emotion from session
            EmotionSessionStoreCustomer customerValue = EmotionSession.getValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (customerValue == null || ValidateUtil.isEmpty(customerValue.getCustomerCode())) {
                return new BaseResponse(false);
            }

            logger.info(String.format("Session = '%s' ", JsonUtil.toJson(customerValue)));

            //get url image
            if (customerValue.getPathImage() != null) {
                String url = customerValue.getPathImage();
                byte[] data = Files.readAllBytes(Paths.get(url));
                return new BaseResponse(true, new Pair<String, Object>("image", data));
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = "testSuggestEmotion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse testGetSuggestEmotion(@RequestParam("image") MultipartFile imageFile) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            byte[] byteImage = IOUtils.toByteArray(imageFile.getInputStream());
            EmotionAnalysisModel emotionAnalysis = null;
            emotionAnalysis = emotionService.getCustomerEmotion(byteImage);
            String messageEmotion = suggestionService.getEmotionMessage(emotionAnalysis);
            logger.info("Suggest message: " + messageEmotion);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setSuccess(true);
            baseResponse.setData(emotionAnalysis);
            baseResponse.setMessage(messageEmotion);
            return baseResponse;
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

        return null;

    }

    @RequestMapping(value = I_URI.API_EMOTION_UPLOAD_IMAGE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse uploadImage(@RequestParam(I_URI.PARAMETER_EMOTION_ACCOUNT_ID) Long accountId,
                                    @RequestParam("image") MultipartFile imageFile,
                                    @RequestParam("camera") Integer cameraId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("accountId = '%s' ", accountId));


            //get emotion from session
            EmotionSessionStoreCustomer customerValue = EmotionSession.getValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (customerValue == null || ValidateUtil.isEmpty(customerValue.getCustomerCode())) {
                return new BaseResponse(false);
            }
            String customerCode = customerValue.getCustomerCode();
            logger.info(String.format("Session = '%s' ", JsonUtil.toJson(customerValue)));
            // split image
            byte[] byteImage = IOUtils.toByteArray(imageFile.getInputStream());

//            /** TEST store file before*/
//            String fileNameBefore = I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId + "BEFORE" + new Date().getTime();
//            StoreFileUtils.storeFile(fileNameBefore, new ByteArrayInputStream(byteImage));
//            /** TEST store file before*/


            //call service
            Long resultEmotion = emotionService.uploadImage(byteImage, customerCode);
            if (resultEmotion != null) {

                //store in session
                if (cameraId == 1) {
                    customerValue.setEmotionCamera1(resultEmotion);
                } else if (cameraId == 2) {
                    customerValue.setEmotionCamera2(resultEmotion);
                }

                //store Image
                String fileName = FileUtils.createFolderEmotion(customerCode);
                /**store image*/
                String urlFile = new StoreFileUtils().storeFile(fileName, new ByteArrayInputStream(byteImage));

                String uriImage = FileUtils.correctUrl(fileName);

                logger.info("url: " + uriImage);
                //set session
                customerValue.setPathImage(urlFile);
                customerValue.setUriImage(uriImage);

                //return
                return new BaseResponse(true, new Pair<>("uploadSuccess", true));
            } else {
                return new BaseResponse(false);
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_EMOTION_NEXT_TRANSACTION, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse nextTransaction(@RequestParam(I_URI.PARAMETER_EMOTION_ACCOUNT_ID) Long accountId,
                                        @RequestParam(value = "skip", required = false) Boolean isSkip) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("accountId = '%s' ", accountId));

            EmotionSessionStoreCustomer customerValue = EmotionSession.getValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (customerValue == null || ValidateUtil.isEmpty(customerValue.getCustomerCode())) {
                return new BaseResponse(false);
            }
            String customerCode = customerValue.getCustomerCode();
            logger.info(String.format("Session = '%s' ", JsonUtil.toJson(customerValue)));
            CustomerServiceModel result = emotionService.nextTransaction(customerCode, isSkip);
            if (result != null && ValidateUtil.isNotEmpty(result.getCustomerCode())) {
                String newCustomer = result.getCustomerCode();

                //delete image
                StoreFileUtils.delete(customerValue.getPathImage());

                //replace newCustomer to session
                EmotionSession.remove(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
                EmotionSession.setValue(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId, new EmotionSessionStoreCustomer(newCustomer));

                return new BaseResponse(true, new Pair<String, String>("customerCode", result.getCustomerCode()));
            } else {
                return new BaseResponse(false);
            }

        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }


    @RequestMapping(value = {I_URI.API_EMOTION_REPORT}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse reportEmotion(@RequestParam("year") Integer year,
                                      @RequestParam("month") Integer month,
                                      @RequestParam(value = "day", defaultValue = IContanst.DEFAULT_INT) Integer day,
                                      @RequestParam("managerId") Long managerId) {
        BaseResponse response = null;
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            CustomerServiceReport customerServiceReport = emotionService.reportCustomerService(year, month, day, managerId);
            if (customerServiceReport != null) {
                return new BaseResponse(true, customerServiceReport);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_REPORT_EMPLOYEE}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse reportEmotion(@RequestParam("year") Integer year,
                                      @RequestParam("month") Integer month,
                                      @RequestParam("employeeId") Long employeeId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            EmployeeReportCustomerService customerServiceReport = emotionService.reportCustomerServiceEmployee(year, month, employeeId);
            if (customerServiceReport != null) {
                return new BaseResponse(true, customerServiceReport);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_VOTE}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse vote(@RequestParam("content_id") Long contentId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            emotionService.vote(contentId);
            return new BaseResponse(true);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }


    @RequestMapping(value = {I_URI.API_EMOTION_MODIFY}, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse updateCustomer(@ModelAttribute CustomerTransactionModel customerTransactionModel) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            Pair<Boolean, Object> result  = emotionService.updateCustomerInformation(customerTransactionModel);
            if (result.getKey() == true){
                return new BaseResponse(true, result.getValue());
            }else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}
