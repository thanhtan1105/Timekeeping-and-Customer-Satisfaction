package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.FaceCreateModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.FaceRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
@Component
@Service
public class AccountServiceImpl {

    @Autowired
    private PersonServiceMCSImpl personServiceMCS;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private FaceRepo faceRepo;

    @Autowired
    private DepartmentServiceImpl departmentService;

    @Autowired
    private FaceServiceImpl faceService;



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
                DepartmentEntity departmentEntity = departmentService.findBy(account.getDepartmentId());
                String departmentCode = departmentEntity.getCode();

                BaseResponse response = personServiceMCS.createPerson(departmentCode, account.getUsername(), JsonUtil.toJson(account));
                Map<String, String> map = (Map<String, String>) response.getData();
                String personCode = map.get("personId");
                account.setUserCode(personCode);
                account.setActive(1);
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
        }
        finally {
            return baseResponse;
        }
    }

    public BaseResponse listAll(Integer page, Integer size) {
        BaseResponse response = new BaseResponse();
        if (page != null && size != null) {
            response.setSuccess(true);
            response.setData(accountRepo.findAll());
        } else {
            response.setSuccess(true);
            response.setData(accountRepo.findAll(new PageRequest(page, size)));
        }
        return response;
    }

    public List<AccountEntity> searchByDepartment(Integer departmentId, Integer start, Integer top) {
        List<AccountEntity> accountEntities = new ArrayList<>();
        if (start != null && top != null) {
            return accountRepo.findByDepartment(departmentId, start, top);
        } else {
            // dump
            return accountRepo.findByDepartment(departmentId, start, top);
        }
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
            if (accountEntity == null){
                return new BaseResponse(false, ERROR.ACCOUNT_ADD_FACE_CANNOT_FOUND_ACCOUNTID, null);
            }
            BaseResponse baseResponse = personServiceMCS.addFaceImg(departmentId, accountEntity.getUserCode(), imgStream);
            logger.info("RESPONSE" + baseResponse);
            if (!baseResponse.isSuccess()){
                return baseResponse;
            }
            //Result
            BaseResponse responseResult = new BaseResponse();
            // encoding data
            Map<String, String> mapResult = (Map<String, String>) baseResponse.getData(); // get face
            if (mapResult != null && mapResult.size()> 0) {
                String persistedFaceID = mapResult.get("persistedFaceId");
                // save db
                FaceEntity faceCreate = new FaceEntity(persistedFaceID, accountEntity);
                FaceEntity faceReturn = faceRepo.saveAndFlush(faceCreate);
                if (faceReturn != null) {
                    responseResult.setSuccess(true);
                    Map<String, Long> map = new HashMap<>();
                    map.put("faceId", faceReturn.getId());
                    responseResult.setData(JsonUtil.toJson(map));
                }else{
                    responseResult.setSuccess(false);
                    responseResult.setMessage(ERROR.ACCOUNT_ADD_FACE_CANNOT_SAVE_DB);
                }
            }

            return responseResult;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


}
