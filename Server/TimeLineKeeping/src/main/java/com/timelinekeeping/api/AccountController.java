package com.timelinekeeping.api;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.service.serviceImplement.FaceServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/14/2016.
 */
@RestController
@RequestMapping(I_URI.API_ACCOUNT)
public class AccountController {

    Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private FaceServiceImpl faceService;

    @RequestMapping(value = I_URI.API_CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse create(@ModelAttribute AccountModifyModel account) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("Account: " + JsonUtil.toJson(account));
            Pair<Boolean, String> response = accountService.create(account);
            logger.info("AccountModel: " + JsonUtil.toJson(response));
            if (response.getKey() == true) {
                return new BaseResponse(true, new Pair<String, String>("accountId", response.getValue()));
            } else {
                return new BaseResponse(false, response.getValue());
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }

    @RequestMapping(value = I_URI.API_UPDATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse update(@ModelAttribute AccountModifyModel account) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Pair<Boolean, String> response = accountService.update(account);
            return new BaseResponse(response.getKey(), response.getValue());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }

    @RequestMapping(value = I_URI.API_DEACTIVE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse deactive(@RequestParam("accountId") Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Pair<Boolean, String> response = accountService.deactive(accountId);
            return new BaseResponse(response.getKey(), response.getValue());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }


    @RequestMapping(value = I_URI.API_ACTIVE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse reActive(@RequestParam("accountId") Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Pair<Boolean, String> response = accountService.reActive(accountId);
            return new BaseResponse(response.getKey(), response.getValue());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_LIST_MANAGER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse listManager() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<AccountManagerModel> managers = accountService.listManager();
            if (managers != null) {
                return new BaseResponse(true, managers);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }

    @RequestMapping(value = I_URI.API_GET, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse get(@RequestParam("accountId") Long accountId) {
        try {
            AccountModel result = accountService.get(accountId);
            if (result != null) {
                return new BaseResponse(true, result);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }

    @RequestMapping(value = I_URI.API_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) Integer page,
                               @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) Integer size) {

        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Page<AccountModel> accountModelList = accountService.listAll(page, size);
            return new BaseResponse(true, accountModelList);
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_SEARCH_DEPARTMENT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse searchByDepartment(@RequestParam(value = "departmentID") Long departmentID,
                                           @RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) Integer start,
                                           @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) Integer top) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("departmentID: " + departmentID);
            logger.info("start: " + start);
            logger.info("top: " + top);
            if (departmentID == null) {
                return new BaseResponse(false, ERROR.ACCOUNT_API_SEARCH_DEPARTMENT_EMPTY);
            }

            return new BaseResponse(true, accountService.searchByDepartment(departmentID, start, top));
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }


    @RequestMapping(value = I_URI.API_ACCOUNT_ADD_FACE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse addFaceToAccount(@RequestParam(value = "image") MultipartFile imageFile,
                                         @RequestParam(value = "accountId") Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Long faceId = faceService.addFaceImg(Long.valueOf(accountId), imageFile.getInputStream());
            if (faceId != null) {
                return new BaseResponse(true, new Pair<>("faceId", faceId));
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_REMOVE_FACE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse removeFaceFromAccount(@RequestParam(value = "accountId") String accountId,
                                              @RequestParam(value = "faceId") String faceId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            String personGroupId = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.department");
            Boolean result = faceService.removeFace(personGroupId, Long.parseLong(accountId), Long.parseLong(faceId));
            if (result != null) {
                return new BaseResponse(result);
            } else {
                return new BaseResponse(false);

            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_LIST_FACE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse listFace(@RequestParam(value = "accountId") String accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("accountId: " + accountId);
            List<FaceModel> faces = faceService.listFace(Long.parseLong(accountId));
            if (ValidateUtil.isNotEmpty(faces)) {
                return new BaseResponse(true, faces);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_CHECK_IN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse checkin(@RequestParam(value = "image") MultipartFile fileImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.checkin(fileImg.getInputStream());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_GET_REMINDER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse getReminderOnDay(@RequestParam(value = "accountId") String accountId) {
        try {
            BaseResponse baseResponse = new BaseResponse();
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<NotificationCheckInModel> list = accountService.getReminder(Long.parseLong(accountId));
            baseResponse.setSuccess(true);
            baseResponse.setData(list);
            return baseResponse;
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_UPDATE_TOKEN_ONE_SIGNAL_ID_MOBILE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse updateTokenMobile(@RequestParam(value = "accountID") String accountID,
                                          @RequestParam(value = "tokenID") String tokenID) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse baseResponse = new BaseResponse();
            boolean result = accountService.addMobileTokenID(accountID, tokenID);
            baseResponse.setSuccess(result);
            return baseResponse;
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_ACCOUNT_LOGIN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse login(@RequestParam(value = "username") String username,
                              @RequestParam(value = "password") String password) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse baseResponse = new BaseResponse();
            AccountModel accountModel = accountService.login(username, password);
            if (accountModel == null) {
                baseResponse.setSuccess(false);
                baseResponse.setMessage("Incorrect username or password");
            } else {
                baseResponse.setSuccess(true);
                baseResponse.setData(accountModel);
            }
            return baseResponse;
        } catch (Exception e) {
            logger.error(e);
            System.out.println(e.getStackTrace());
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

    }


    @RequestMapping(value = I_URI.API_ACCOUNT_CHECK_EXIST_USERNAME, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse checkExistUsername(@RequestParam(value = "username") String username) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(accountService.checkExistUsername(username));
        } catch (Exception e) {
            logger.error(e);
            System.out.println(e.getStackTrace());
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

    }

    @RequestMapping(value = I_URI.API_ACCOUNT_CREATE_EMAIL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public BaseResponse createEmail(@RequestParam(value = "fullName") String fullName) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("fullName : " + fullName);
            String email = accountService.createEmail(fullName);
            if (email != null) {
                return new BaseResponse(true, email);
            } else {
                return new BaseResponse(false);
            }

        } catch (Exception e) {
            logger.error(e);
            System.out.println(e.getStackTrace());
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }

    }


}
