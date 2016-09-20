package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.modelAPI.FaceDetectResponse;
import com.timelinekeeping.modelAPI.FaceIdentifyConfidenceRespone;
import com.timelinekeeping.modelAPI.FaceIdentityCandidate;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.DepartmentRepo;
import com.timelinekeeping.repository.FaceRepo;
import com.timelinekeeping.repository.TimekeepingRepo;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.UtilApps;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
@Component
@Service
public class AccountServiceImpl {

    @Autowired
    private PersonServiceMCSImpl personServiceMCS;

    @Autowired
    private FaceServiceMCSImpl faceServiceMCS;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private FaceRepo faceRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private TimekeepingRepo timekeepingRepo;

    private Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public BaseResponse create(AccountEntity account) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            System.out.println("Account: " + account.getUsername());
            if (isExist(account.getUsername())) {
                baseResponse.setSuccess(false);
                baseResponse.setMessage("User name " + account.getUsername() + " already exists.");
                baseResponse.setErrorCode("Account already exist");

            } else {
                // get department code
                DepartmentEntity departmentEntity = departmentRepo.findOne(account.getDepartment().getId());
                String departmentCode = departmentEntity.getCode();

                BaseResponse response = personServiceMCS.createPerson(departmentCode, account.getUsername(), JsonUtil.toJson(account));
                Map<String, String> map = (Map<String, String>) response.getData();
                String personCode = map.get("personId");
                account.setUserCode(personCode);

                AccountEntity result = accountRepo.saveAndFlush(account);
                if (result != null) {
                    baseResponse.setSuccess(true);
                    baseResponse.setData(new AccountModel(result));
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return baseResponse;
        }
    }

    public List<AccountModel> listAll(Integer page, Integer size) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            Page<AccountEntity> entityPage = accountRepo.findAll(new PageRequest(page, size));
            List<AccountEntity> entityList = entityPage.getContent();
            List<AccountModel> accountModels = entityList.stream().map(AccountModel::new).collect(Collectors.toList());
            logger.info("Entity result:" + JsonUtil.toJson(accountModels));
            return accountModels;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public List<AccountModel> searchByDepartment(Long departmentId, Integer start, Integer top) {
        List<AccountEntity> accountEntities = departmentRepo.findByDepartment(departmentId, new PageRequest(start, top));
        List<AccountModel> accountModels = accountEntities.stream().map(AccountModel::new).collect(Collectors.toList());
        logger.info("Entity result:" + JsonUtil.toJson(accountModels));
        return accountModels;
    }

    public boolean isExist(String username) {
        AccountEntity accountView = accountRepo.findByUsername(username);
        return accountView == null ? false : true;
    }

    public AccountEntity findByCode(String code) {
        return accountRepo.findByCode(code);
    }

    public BaseResponse addFaceImg(String departmentId, Long accountId, InputStream imgStream) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //STORE FILE

//            String nameFile = persongroupId + "_" + personId + "_" + (new Date().getTime());
//            StoreFileUtils.storeFile(nameFile, imgStream);

            AccountEntity accountEntity = accountRepo.findOne(accountId);
            if (accountEntity == null) {
                return new BaseResponse(false, ERROR.ACCOUNT_ADD_FACE_CANNOT_FOUND_ACCOUNTID, null);
            }
            BaseResponse baseResponse = personServiceMCS.addFaceImg(departmentId, accountEntity.getUserCode(), imgStream);
            logger.info("RESPONSE" + baseResponse);
            if (!baseResponse.isSuccess()) {
                return baseResponse;
            }
            //Result
            BaseResponse responseResult = new BaseResponse();
            // encoding data
            Map<String, String> mapResult = (Map<String, String>) baseResponse.getData(); // get face
            if (mapResult != null && mapResult.size() > 0) {
                String persistedFaceID = mapResult.get("persistedFaceId");
                // save db
                FaceEntity faceCreate = new FaceEntity(persistedFaceID, accountEntity);
                FaceEntity faceReturn = faceRepo.saveAndFlush(faceCreate);
                if (faceReturn != null) {
                    responseResult.setSuccess(true);
                    Map<String, Long> map = new HashMap<>();
                    map.put("faceId", faceReturn.getId());
                    responseResult.setData(JsonUtil.toJson(map));
                } else {
                    responseResult.setSuccess(false);
                    responseResult.setMessage(ERROR.ACCOUNT_ADD_FACE_CANNOT_SAVE_DB);
                }
            }
            return responseResult;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }

    }

    /**
     * check_in by image from client
     * <p>
     * author hientq
     * date: 17-09-2016
     *
     * @param fileInputStream is input Stream Reader
     */
    public BaseResponse checkin(InputStream fileInputStream) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            /** return */
            BaseResponse response = new BaseResponse();
            String faceID;
            /** call API MCS get List FaceID*/
            List<String> listFace = detectImg(fileInputStream);
            if (listFace == null) {
                logger.error(IContanst.ERROR_LOGGER + ERROR.ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE);
                return new BaseResponse(false, ERROR.ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE, null);
            }

            //just detect for only person
            if (listFace.size() > 1) {
                logger.error(IContanst.ERROR_LOGGER + "List face size: " + listFace.size());
                return new BaseResponse(false, ERROR.ERROR_ACCOUNT_CHECKIN_IMAGE_EXIST_MANY_PEOPLE_IN_IMAGE, null);
            }

            faceID = listFace.get(0);

            // Get List Department from data
            List<DepartmentEntity> departmentEntities = departmentRepo.findAll();
            //TODO: ERROR from database
            if (departmentEntities == null || departmentEntities.size() == 0) {
                return new BaseResponse(false, ERROR.ERROR_ACCOUNT_CHECKIN_MSDS, null);
            }
            List<String> departmentNames = getDepartmentName(departmentEntities);
            logger.info("-- List department: " + JsonUtil.toJson(departmentNames));

            /** get PersonID from */
            String personID = checkExistFaceInDepartment(faceID, departmentNames);
            if (ValidateUtil.isEmpty(personID)) {

                logger.error(IContanst.ERROR_LOGGER + ERROR.ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE);
                return new BaseResponse(false, ERROR.ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE, null);
            }
            logger.info("-- PersonID: " + personID);

            // PersonID -> AccountEntity
            AccountEntity accountEntity = accountRepo.findByUsercode(personID.trim());
            if (accountEntity == null) {
                //TODO: ERROR not found personID in database
                return new BaseResponse(false, ERROR.ERROR_ACCOUNT_CHECKIN_NOT_FOUND_PERSONID, null);
            }

            // Save TimeKeeping fro accountID
            TimeKeepingEntity timeKeepingEntity = new TimeKeepingEntity();
            ETimeKeeping timeKeepingStatus = UtilApps.checkStatusTimeKeeping();
            timeKeepingEntity.setStatus(timeKeepingStatus);
            timeKeepingEntity.setAccount(accountEntity);
            timeKeepingEntity.setTimeCheck(new Timestamp(new Date().getTime()));
            timekeepingRepo.saveAndFlush(timeKeepingEntity);
            logger.info("-- Save TimeKeeping: " + JsonUtil.toJson(timeKeepingEntity));

            // accountID -> get Reminder

            // convert Reminder

            //Response to Server
            response.setSuccess(true);
            return response;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * call API and detect img
     */
    private List<String> detectImg(InputStream fileInputStream) throws IOException, URISyntaxException {
        List<String> listFace = new ArrayList<>();

        BaseResponse responseDetect = faceServiceMCS.detect(fileInputStream);

        if (responseDetect.isSuccess()) {
            List<FaceDetectResponse> faceDetects = (List<FaceDetectResponse>) responseDetect.getData();
            if (faceDetects.size() > 0) {
                for (FaceDetectResponse face : faceDetects) {
                    listFace.add(face.getFaceId());
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return listFace;
    }

    /**
     * get department Name from list departmentEntities
     */
    private List<String> getDepartmentName(List<DepartmentEntity> departmentEntities) {
        List<String> departmentNames = new ArrayList<>();
        for (DepartmentEntity department : departmentEntities) {
            departmentNames.add(department.getCode());
        }
        return departmentNames;
    }

    /**
     * check exist faceID in list department
     */
    private String checkExistFaceInDepartment(String faceID, List<String> departmentNames) throws IOException, URISyntaxException {

        String personID = null;
        double confidence = 0d;

        for (String departmentName : departmentNames) {
            BaseResponse response = faceServiceMCS.identify(departmentName, faceID);
            if (response.isSuccess()) {

                //get list Identifies success
                List<FaceIdentifyConfidenceRespone> faceIdentifies = (List<FaceIdentifyConfidenceRespone>) response.getData();

                //check success
                if (ValidateUtil.isEmpty(faceIdentifies) && faceIdentifies.size() == 1) {
                    List<FaceIdentityCandidate> candidateList = faceIdentifies.get(0).getCandidates();
                    for (FaceIdentityCandidate candidate : candidateList) {
                        if (candidate.getConfidence() > confidence) {
                            confidence = candidate.getConfidence();
                            personID = candidate.getPersonId();
                        }
                    }
                } else {
                    logger.error("When get face identify one image, has many value");
                }

            }
        }

        /*** check greater then confidence*/
        if (confidence > IContanst.MCS_PERSON_DETECT_CONFIDINCE_CORRECT) {
            return personID;
        } else {
            return null;
        }
    }
}
