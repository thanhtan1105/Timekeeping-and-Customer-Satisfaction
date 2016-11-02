package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.service.blackService.OneSignalNotification;
import com.timelinekeeping.service.blackService.SMSNotification;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.*;
import com.timelinekeeping.entity.*;
import com.timelinekeeping.model.AccountManagerModel;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.AccountModifyModel;
import com.timelinekeeping.model.NotificationCheckInModel;
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
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

    // METHOD
    public AccountModel login(String username, String password) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            AccountEntity accountEntity = accountRepo.findByUserNameAndPassword(username, password);
            if (accountEntity == null) {
                return null;
            } else {
                logger.info("[Service- Login] account: " + JsonUtil.toJson(accountEntity.getId()));
                AccountModel accountModel = new AccountModel(accountEntity);
                accountModel.replaceRele(accountEntity.getRole());
                return accountModel;
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Pair<Boolean, String> create(AccountModifyModel account) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("Account: " + JsonUtil.toJson(account));

            //validateAccount
            validateCreate(account);

            Integer count = accountRepo.checkExistUsername(account.getUsername());
            if (count > 0) {
                return new Pair<>(false, String.format(ERROR.ACCOUNT_API_CRATE_CUSTOMER_ALREADY_EXIST, account.getUsername()));
            }
            // get department code
            DepartmentEntity departmentEntity = departmentRepo.findOne(account.getDepartmentId());
            if (departmentEntity == null) {
                return new Pair<>(false, String.format(ERROR.ACCOUNT_API_CRATE_DEPARTMENT_DOES_NOT_EXIST, account.getDepartmentId()));
            }

            // get role code
            RoleEntity roleEntity = roleRepo.findOne(account.getRoleId());
            if (roleEntity == null) {
                return new Pair<>(false, String.format(ERROR.ACCOUNT_API_CRATE_ROLE_DOES_NOT_EXIST, account.getRoleId()));
            }

            //create person in MCS
            String departmentCode = departmentEntity.getCode();
            logger.info("departmentCode: " + departmentCode);
            BaseResponse response = personServiceMCS.createPerson(departmentCode, account.getUsername(), JsonUtil.toJson(account));
            if (!response.isSuccess()) {
                return new Pair<>(false, ERROR.ERROR_IN_MCS + response.getMessage());
            }
            Map<String, String> map = (Map<String, String>) response.getData();
            String personCode = map.get("personId");
            logger.info("personCode: " + personCode);


            //create entity
            AccountEntity entity = new AccountEntity(account);
            entity.setUsername(account.getUsername());
            entity.setUserCode(personCode);
            entity.setPassword(UtilApps.generatePassword());
            entity.setFullname(account.getFullName());
            entity.setRole(roleEntity);
            entity.setDepartment(departmentEntity);

            //add manager
            if (account.getManagerId() != null) {
                AccountEntity manager = accountRepo.findOne(account.getManagerId());
                entity.setManager(manager);
            }
            //save db
            AccountEntity result = accountRepo.saveAndFlush(entity);
            if (result != null) {
                return new Pair<>(true, result.getId() + "");
            }
            return new Pair<>(false, ERROR.OTHER);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    //update account
    public Pair<Boolean, String> update(AccountModifyModel model) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            //validate model
            if (model.getId() == null) {
                return new Pair<>(false, "AccountId is empty.");
            }

            //get account
            AccountEntity entity = accountRepo.findOne(model.getId());

            //check exist in db
            if (entity == null) {
                return new Pair<>(false, "AccountId does not exist in system.");
            }

            //update model to account
            entity.update(model);
            //2. update field entity, if not null

            //2.1 role
            if (model.getRoleId() != null) {
                RoleEntity roleEntity = roleRepo.findOne(model.getRoleId());
                if (roleEntity != null) {
                    entity.setRole(roleEntity);
                }

            }

            //2.2 department
            if (model.getDepartmentId() != null) {
                DepartmentEntity departmentEntity = departmentRepo.findOne(model.getDepartmentId());
                if (departmentEntity != null) {
                    entity.setDepartment(departmentEntity);
                }
            }

            //2.3 mananegr
            if (model.getManagerId() != null) {
                AccountEntity managerEntity = accountRepo.findOne(model.getManagerId());
                if (managerEntity != null) {
                    entity.setManager(managerEntity);
                }
            }

            //store db
            accountRepo.saveAndFlush(entity);

            return new Pair<>(false, ERROR.OTHER);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Pair<Boolean, String> deactive(Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //get account
            AccountEntity entity = accountRepo.findOne(accountId);

            //check exist in db
            if (entity == null) {
                return new Pair<>(false, "AccountId does not exist in system.");
            }

            entity.setActive(EStatus.DEACTIVE);
            entity.setTimeDeactive(new Date().getTime());
            accountRepo.saveAndFlush(entity);
            return new Pair<>(false, ERROR.OTHER);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public AccountModel get(Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //get account
            AccountEntity entity = accountRepo.findOne(accountId);

            //check exist in db
            if (entity == null) {
                return null;
            }

            //return
            //if (entity.get)
            return new AccountModel(entity);

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

    /**
     * list all manager in system*/

    public List<AccountManagerModel> listManager() {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //validate model
            List<AccountEntity> listEntity = accountRepo.listAllManger();
            if (listEntity == null) {
                return null;
            }
            return listEntity.stream().map(AccountManagerModel::new).collect(Collectors.toList());

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
            Page<AccountEntity> entityPage = accountRepo.findByDepartmentPaging(departmentId, pageable);

            //covert list
            List<AccountModel> accountModels = entityPage.getContent().stream().map(AccountModel::new).collect(Collectors.toList());

            return accountModels;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * @author TrungNN
     * Using for selection employee
     */
    public List<AccountModel> getEmployeesOfDepart(Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            List<AccountEntity> accountEntities = accountRepo.findByManager(managerId);

            // convert list
            return accountEntities.stream().map(AccountModel::new).collect(Collectors.toList());
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Long addFaceImg(Long accountId, InputStream imgStream) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            InputStream[] streams = UtilApps.muitleStream(imgStream, 2);

            AccountEntity accountEntity = accountRepo.findOne(accountId);
            if (accountEntity == null) {
                return null;
            }
            BaseResponse baseResponse = personServiceMCS.addFaceImg(accountEntity.getDepartment().getCode(), accountEntity.getUserCode(), streams[0]);
            logger.info("RESPONSE" + baseResponse);
            if (!baseResponse.isSuccess()) {
                return null;
            }


            // encoding data
            Map<String, String> mapResult = (Map<String, String>) baseResponse.getData(); // get face
            if (mapResult != null && mapResult.size() > 0) {
                String persistedFaceID = mapResult.get("persistedFaceId");
                //STORE FILE
                String nameFile = accountEntity.getDepartment().getId() + "_" + accountEntity.getDepartment().getCode()
                        + File.separator + accountId + "_" + accountEntity.getUsername() + File.separator + new Date().getTime();
                String outFileName = StoreFileUtils.storeFile(nameFile, streams[1]);
                //return faceReturn.getId();

                // save db
                FaceEntity faceCreate = new FaceEntity(persistedFaceID, accountEntity);
                faceCreate.setStoePath(outFileName);
                FaceEntity faceReturn = faceRepo.saveAndFlush(faceCreate);
                return faceReturn.getId();
            }
            return null;
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
            /** call API MCS get List FaceID*/
            String faceID = detectImg(fileInputStream);
            if (faceID == null) {
                logger.error(IContanst.ERROR_LOGGER + ERROR.ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE);
                return new BaseResponse(false, ERROR.ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE, null);
            }

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
            TimeKeepingEntity timeKeepingEntity = timekeepingRepo.findByAccountCheckinDate(accountEntity.getId(), new Date());

            if (timeKeepingEntity != null) {
                // TODO checked, show message

            } else {
                timeKeepingEntity = new TimeKeepingEntity();
                timeKeepingEntity.setType(ETypeCheckin.CHECKIN_CAMERA);
                timeKeepingEntity.setStatus(ETimeKeeping.PRESENT);
                timeKeepingEntity.setAccount(accountEntity);
                timeKeepingEntity.setTimeCheck(new Timestamp(new Date().getTime()));
                timekeepingRepo.saveAndFlush(timeKeepingEntity);
                logger.info("-- Save TimeKeeping: " + timeKeepingEntity.getTimeCheck());
            }

            // push notification
            pushNotification(accountEntity);

            // push sms
            SMSNotification.getInstance().sendSms(new AccountModel(accountEntity));

            //Response to Server
            response.setSuccess(true);
            return response;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public void pushNotification(AccountEntity accountEntity){
        // make data
        Gender gender = accountEntity.getGender();
        String welcomeMessage = "Xin chào ";
        String prefix = gender == Gender.MALE ? "anh" : "chị";
        welcomeMessage += prefix + " ";
        welcomeMessage += accountEntity.getFullname() + " ";
        welcomeMessage += ". Chúc " + prefix + " " + "một ngày làm việc tốt lành";

        String header = "Check in successfully";
        OneSignalNotification.instance().pushNotification(new AccountModel(accountEntity),  header, welcomeMessage);
    }



    /**
     *
     */
    public List<NotificationCheckInModel> getReminder(Long accountID) {
        AccountEntity accountEntity = accountRepo.findOne(accountID);

        // accountID -> get Reminder
        List<NotificationEntity> notificationSet = notificationRepo.findByAccountReceiveByDate(accountEntity.getId());
        List<NotificationCheckInModel> message = new ArrayList<>();
        for (NotificationEntity notificationEntity : notificationSet) {
            if (notificationEntity.getStatus() == ENotification.NOSEND) {
//                notificationEntity.setStatus(ENotification.SENDED);
//                notificationEntity.setTimeNotify(new Timestamp(new Date().getTime()));
//                notificationRepo.save(notificationEntity);
                message.add(new NotificationCheckInModel(notificationEntity));
            }
        }
//        notificationRepo.flush();

        return message;
    }

    /**
     * call API and detect img
     *
     * @return faceId has rectangle maximun
     */
    private String detectImg(InputStream fileInputStream) throws IOException, URISyntaxException {
//        List<String> listFace = new ArrayList<>();
        String faceId = null;

        BaseResponse responseDetect = faceServiceMCS.detect(fileInputStream);

        if (responseDetect.isSuccess()) {
            List<FaceDetectResponse> faceDetects = (List<FaceDetectResponse>) responseDetect.getData();
            if (faceDetects.size() > 0) {
                Long area = 0l;
                for (FaceDetectResponse face : faceDetects) {
                    Long areaFace = face.getFaceRectangle().area();
                    if (area < areaFace) {
                        area = areaFace;
                        faceId = face.getFaceId();
                    }
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
        return faceId;
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
                // TODO check again check in
//                if (ValidateUtil.isEmpty(faceIdentifies) && faceIdentifies.size() == 1) {
                List<FaceIdentityCandidate> candidateList = faceIdentifies.get(0).getCandidates();
                for (FaceIdentityCandidate candidate : candidateList) {
                    if (candidate.getConfidence() > confidence) {
                        confidence = candidate.getConfidence();
                        personID = candidate.getPersonId();
                    }
                }
//                } else {
//                    logger.error("When get face identify one image, has many value");
//                }

            }
        }

        //check greater then confidence
        if (confidence > IContanst.MCS_PERSON_DETECT_CONFIDINCE_CORRECT) {
            return personID;
        } else {
            return null;
        }
    }

    public boolean addMobileTokenID(String accountID, String tokenID) {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        List<AccountEntity> list = accountRepo.findByToken(tokenID);
        if (list.size() != 0 && list != null) {
            // update null to id
            AccountEntity accountEntity = list.get(0);
            accountEntity.setToken(tokenID);
            accountRepo.saveAndFlush(accountEntity);
        } else {
            AccountEntity accountEntity = accountRepo.findOne(Long.parseLong(accountID));
            accountEntity.setToken(tokenID);
            accountRepo.saveAndFlush(accountEntity);
        }
        return true;
    }

    private Pair<Boolean, String> validateCreate(AccountModifyModel model) {
        if (ValidateUtil.isEmpty(model.getUsername())) {
            return new Pair<>(false, ERROR.ACCOUNT_API_USERNAME_IS_NOT_EMPTY);
        }

        if (ValidateUtil.isEmpty(model.getFullName())) {
            return new Pair<>(false, "Fullname is Empty.");
        }

        if (model.getRoleId() == null) {
            return new Pair<>(false, "Role is Empty.");
        }

        if (model.getDepartmentId() == null) {
            return new Pair<>(false, "Department is Empty.");
        }
        if (model.getGender() == null) {
            return new Pair<>(false, "Gender is Empty.");
        }

        if (model.getPhone() == null) {
            return new Pair<>(false, "Phone is Empty.");
        }

        if (model.getEmail() == null) {
            return new Pair<>(false, "Email is Empty.");
        }
        return new Pair<>(true);
    }


}
