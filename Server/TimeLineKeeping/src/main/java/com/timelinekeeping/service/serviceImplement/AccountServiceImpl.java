package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.ETypeCheckin;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.*;
import com.timelinekeeping.model.*;
import com.timelinekeeping.modelMCS.FaceDetectResponse;
import com.timelinekeeping.modelMCS.FaceIdentifyConfidenceRespone;
import com.timelinekeeping.modelMCS.FaceIdentityCandidate;
import com.timelinekeeping.repository.*;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.StoreFileUtils;
import com.timelinekeeping.util.UtilApps;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private RoleRepo roleRepo;

    @Autowired
    private NotificationRepo notificationRepo;


    @Autowired
    private TimekeepingRepo timekeepingRepo;

    private Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public BaseResponseG<AccountModel> create(AccountModifyModel account) throws IOException, URISyntaxException {
        BaseResponse baseResponse = new BaseResponse();
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("Account: " + JsonUtil.toJson(account));
            Integer count = accountRepo.checkExistUsername(account.getUsername());
            if (count > 0) {
                return new BaseResponseG<>(false, String.format(ERROR.ACCOUNT_API_CRATE_CUSTOMER_ALREADY_EXIST, account.getUsername()));
            }
            // get department code
            DepartmentEntity departmentEntity = departmentRepo.findOne(account.getDepartmentId());
            if (departmentEntity == null) {
                return new BaseResponseG<>(false, String.format(ERROR.ACCOUNT_API_CRATE_DEPARTMENT_DOES_NOT_EXIST, account.getDepartmentId()));
            }

            // get role code
            RoleEntity roleEntity = roleRepo.findOne(account.getRoleId());
            if (roleEntity == null) {
                return new BaseResponseG<>(false, String.format(ERROR.ACCOUNT_API_CRATE_ROLE_DOES_NOT_EXIST, account.getRoleId()));
            }

            //get DepartmentCode
            String departmentCode = departmentEntity.getCode();
            logger.info("departmentCode: " + departmentCode);
            BaseResponse response = personServiceMCS.createPerson(departmentCode, account.getUsername(), JsonUtil.toJson(account));
            if (!response.isSuccess()) {
                return new BaseResponseG<>(false, ERROR.ERROR_IN_MCS + response.getMessage());
            }
            Map<String, String> map = (Map<String, String>) response.getData();
            String personCode = map.get("personId");
            logger.info("personCode: " + personCode);


            //create entity
            AccountEntity entity = new AccountEntity(account);
            entity.setUserCode(personCode);
            entity.setDepartment(departmentEntity);
            entity.setRole(roleEntity);
            entity.setPassword("123456");

            //save db
            AccountEntity result = accountRepo.saveAndFlush(entity);
            if (result != null) {
                return new BaseResponseG<>(true, new AccountModel(result));
            }
            return new BaseResponseG<>(false, ERROR.OTHER);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Page<AccountModel> listAll(Integer page, Integer size) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            // paging
            Pageable pageable = new PageRequest(page, size);

            //repo db
            Page<AccountEntity> entityPage = accountRepo.findAll(pageable);

            //covert list
            List<AccountModel> accountModels = entityPage.getContent().stream().map(AccountModel::new).collect(Collectors.toList());
            Page<AccountModel> returnPage = new PageImpl<>(accountModels, pageable, entityPage.getTotalElements());

            logger.info("Entity result:" + JsonUtil.toJson(returnPage));

            return returnPage;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public List<AccountModel> searchByDepartment(Long departmentId, Integer start, Integer top) {

        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            // paging
            Pageable pageable = new PageRequest(start, top);

            //repo db
            List<AccountEntity> entityPage = departmentRepo.findByDepartment(departmentId);

            //covert list
            List<AccountModel> accountModels = entityPage.stream().map(AccountModel::new).collect(Collectors.toList());
//            Page<AccountModel> returnPage = new PageImpl<>(accountModels, pageable, entityPage.getTotalElements());

//            logger.info("Entity result:" + JsonUtil.toJson(returnPage));

//            return returnPage;
            return accountModels;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public List<AccountModel> searchByDepartmentAndRole(Long departmentId, Long roleId, Integer start, Integer top) {

        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            // paging
            Pageable pageable = new PageRequest(start, top);

            //repo db
            Page<AccountEntity> entityPage = accountRepo.findByDepartmentAndRole(departmentId, roleId, pageable);

            //covert list
            List<AccountModel> accountModels = entityPage.getContent().stream().map(AccountModel::new).collect(Collectors.toList());

            return accountModels;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public BaseResponse addFaceImg(Long accountId, InputStream imgStream) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            InputStream[] streams = UtilApps.muitleStream(imgStream, 2);

            AccountEntity accountEntity = accountRepo.findOne(accountId);
            if (accountEntity == null) {
                return new BaseResponse(false, ERROR.ACCOUNT_ADD_FACE_CANNOT_FOUND_ACCOUNTID, null);
            }
            BaseResponse baseResponse = personServiceMCS.addFaceImg(accountEntity.getDepartment().getCode(), accountEntity.getUserCode(), streams[0]);
            logger.info("RESPONSE" + baseResponse);
            if (!baseResponse.isSuccess()) {
                return new BaseResponse(false, ERROR.ERROR_IN_MCS + baseResponse.getMessage(), null);
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

                    //STORE FILE
//                    String nameFile = accountEntity.getDepartment().getCode() + "_" + accountId + "_" + (new Date().getTime());
//                    StoreFileUtils.storeFile(nameFile, imgStream);
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

            BaseResponse response = new BaseResponse(); /** return */
            String faceID;
            /** call API MCS get List FaceID*/
            List<String> listFace = detectImg(fileInputStream);
            if (listFace == null) {
                logger.error(IContanst.ERROR_LOGGER + ERROR.ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE);
                return new BaseResponse(false, ERROR.ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE, null);
            }

            //just detect for only person
            if (listFace.size() > 1) {
                logger.error(IContanst.ERROR_LOGGER + "List face size: " + listFace.size());
                return new BaseResponse(false, ERROR.ACCOUNT_CHECKIN_IMAGE_EXIST_MANY_PEOPLE_IN_IMAGE, null);
            }

            faceID = listFace.get(0);

            // Get List Department from data
            List<DepartmentEntity> departmentEntities = departmentRepo.findAll();
            if (departmentEntities == null || departmentEntities.size() == 0) {
                return new BaseResponse(false, ERROR.ACCOUNT_CHECKIN_MSDS, null);
            }
            List<String> departmentCode = departmentEntities.stream().map(DepartmentEntity::getCode).collect(Collectors.toList());
            logger.info("-- List department: " + JsonUtil.toJson(departmentCode));

            /** get PersonID from */
            String personID = checkExistFaceInDepartment(faceID, departmentCode);
            if (ValidateUtil.isEmpty(personID)) {

                logger.error(IContanst.ERROR_LOGGER + ERROR.ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE);
                return new BaseResponse(false, ERROR.ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE, null);
            }
            logger.info("-- PersonID: " + personID);

            // PersonID -> AccountEntity
            AccountEntity accountEntity = accountRepo.findByUsercode(personID.trim());
            if (accountEntity == null) {
                return new BaseResponse(false, ERROR.ACCOUNT_CHECKIN_NOT_FOUND_PERSONID, null);
            }

            // Save TimeKeeping fro accountID
            TimeKeepingEntity timeKeepingEntity = new TimeKeepingEntity();
//            ETimeKeeping timeKeepingStatus = UtilApps.checkStatusTimeKeeping();
            timeKeepingEntity.setType(ETypeCheckin.CHECKIN_CAMERA);
            timeKeepingEntity.setStatus(ETimeKeeping.PRESENT);
            timeKeepingEntity.setAccount(accountEntity);
            timeKeepingEntity.setTimeCheck(new Timestamp(new Date().getTime()));
            timekeepingRepo.saveAndFlush(timeKeepingEntity);
            logger.info("-- Save TimeKeeping: " + timeKeepingEntity.getTimeCheck());


            //TODO reminder
            // accountID -> get Reminder
            List<NotificationEntity> notificationSet = notificationRepo.findByAccountReceive(accountEntity.getId());


            // convert Reminder
            List<NotificationCheckInModel> message = notificationSet.stream().map(NotificationCheckInModel::new).collect(Collectors.toList());

            //Response to Server
            CheckinResponse checkinResponse = new CheckinResponse();
            checkinResponse.setTimeCheckIn(new Date());
            checkinResponse.setAccount(new AccountModel(accountEntity));
            checkinResponse.setMessageReminder(message);
            response.setSuccess(true);
            response.setData(checkinResponse);
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
                listFace.addAll(faceDetects.stream().map(FaceDetectResponse::getFaceId).collect(Collectors.toList()));
            } else {
                return null;
            }
        } else {
            return null;
        }
        return listFace;
    }

    /**
     * check exist faceID in list department
     */
    private String checkExistFaceInDepartment(String faceID, List<String> departmentCode) throws IOException, URISyntaxException {

        String personID = null;
        double confidence = 0d;

        for (String departmentName : departmentCode) {
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

        //check greater then confidence
        if (confidence > IContanst.MCS_PERSON_DETECT_CONFIDINCE_CORRECT) {
            return personID;
        } else {
            return null;
        }
    }
}
