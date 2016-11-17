package com.timelinekeeping.service.serviceImplement;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.FaceModel;
import com.timelinekeeping.model.FaceModifyModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.FaceRepo;
import com.timelinekeeping.util.FileUtils;
import com.timelinekeeping.util.StoreFileUtils;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lethanhtan on 9/19/16.
 */

@Component
@Service
public class FaceServiceImpl {

    @Autowired
    private FaceRepo faceRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PersonServiceMCSImpl personServiceMCS;


    Logger logger = LogManager.getLogger(FaceServiceImpl.class);

    String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url");

    //TODO compress error
    public FaceEntity create(FaceModifyModel faceModifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            FaceEntity entity = null;
            AccountEntity accountEntity;
            if (!ObjectUtils.isEmpty(faceModifyModel.getAccountId())) {
                accountEntity = accountRepo.findOne(faceModifyModel.getAccountId());
            } else if (!StringUtils.isEmpty(faceModifyModel.getAccountCode())) {
                accountEntity = accountRepo.findByUserCode(faceModifyModel.getAccountCode());
            } else {
                return null;
            }
            if (accountEntity == null) {
                return null;
            }
            entity = new FaceEntity(faceModifyModel.getPersistedFaceId(), accountEntity);
            FaceEntity result = faceRepo.saveAndFlush(entity);
            return result;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Boolean removeFace(Long accountId, Long faceId) throws URISyntaxException, IOException {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(String.format("Remove Face: accountId = [%s] faceId = [%s]", accountId, faceId));
        AccountEntity accountEntity = accountRepo.findOne(accountId);
        if (accountEntity == null) {
            logger.info(String.format("AccountId = [%s] no exist.", accountId));
            return null;
        }
        FaceEntity faceEntity = faceRepo.findOne(faceId);
        if (faceEntity == null) {
            logger.info(String.format("faceEntity = [%s] no exist.", faceId));
            return null;
        }
        BaseResponse baseResponse = personServiceMCS.removePersonFace(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), faceEntity.getPersistedFaceId());

        if (baseResponse.isSuccess() == true) {
            // remove on db
            faceEntity.setActive(EStatus.DEACTIVE);
            faceRepo.save(faceEntity);
            return true;
        } else {

            return false;
        }
    }

    public Pair<Boolean, String> removeAllFace(Long accountId) throws URISyntaxException, IOException {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(String.format("Remove Face: accountId = [%s]", accountId));
        AccountEntity accountEntity = accountRepo.findOne(accountId);
        if (accountEntity == null) {
            logger.error(String.format("AccountId = [%s] no exist.", accountId));
            return new Pair<>(false, String.format("AccountId = [%s] no exist.", accountId));
        }

        List<FaceEntity> listFaceEntity = faceRepo.findByAccount(accountId);

        if (ValidateUtil.isEmpty(listFaceEntity)) {
            logger.error(String.format("listFaceEntity.size = [0]"));
            return new Pair<>(false, String.format("listFaceEntity.size = [0]"));
        }

        logger.info("Listface size = " + listFaceEntity.size());
        for (FaceEntity faceEntity : listFaceEntity) {
            logger.info(String.format("Delete persistence_Id = [%s], url = [%s]", faceEntity.getPersistedFaceId(), faceEntity.getStorePath()));
            BaseResponse baseResponse = personServiceMCS.removePersonFace(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), faceEntity.getPersistedFaceId());

            if (baseResponse.isSuccess() == true) {
                // remove on db
                faceEntity.setActive(EStatus.DEACTIVE);
                faceRepo.save(faceEntity);
                logger.info("----- Succuess. ");
            } else {
                logger.info("************* Delete fail. **********");
            }
        }
        return new Pair<>(true);
    }


    public Long addFaceImg(Long accountId, InputStream imgStream) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            byte[] byteImage = IOUtils.toByteArray(imgStream);
//            new StoreFileUtils().storeFile("1. cdsere.jpg", new ByteArrayInputStream(byteImage));
            //rotate image
            byte[] byteImageRoute = StoreFileUtils.rotateImage(new ByteArrayInputStream(byteImage));
//            new StoreFileUtils().storeFile("2. ajbhdbf.jpg", new ByteArrayInputStream(byteImageRoute));
            AccountEntity accountEntity = accountRepo.findOne(accountId);
            if (accountEntity == null) {
                return null;
            }

//            byte[] byteImage = IOUtils.toByteArray(imgStream);
            String departmentCode = IContanst.DEPARTMENT_MICROSOFT;
            BaseResponse baseResponse = personServiceMCS.addFaceImg(departmentCode, accountEntity.getUserCode(), byteImageRoute);
            logger.info("RESPONSE" + baseResponse);
            if (!baseResponse.isSuccess()) {
                return null;
            }

            // encoding data
            Map<String, String> mapResult = (Map<String, String>) baseResponse.getData(); // get face
            if (mapResult != null && mapResult.size() > 0) {

                String persistedFaceID = mapResult.get("persistedFaceId");

                //STORE FILE
                String nameFile = FileUtils.createFolderTrain(new AccountModel(accountEntity));

                //Store
                new StoreFileUtils().storeFile(nameFile, new ByteArrayInputStream(byteImage));

                // save db
                FaceEntity faceCreate = new FaceEntity(persistedFaceID, accountEntity);
                faceCreate.setStorePath(nameFile);
                FaceEntity faceReturn = faceRepo.saveAndFlush(faceCreate);
                return faceReturn.getId();
            }
            return null;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * list all face in account
     */
    public List<FaceModel> listFace(Long accountID) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<FaceEntity> entities = faceRepo.findByAccount(accountID);
            if (ValidateUtil.isEmpty(entities)) {
                return null;
            }
            List<FaceModel> faceModelList = entities.stream().filter(faceEntity -> faceEntity.getStorePath() != null).map(FaceModel::new).collect(Collectors.toList());


            // replace url
            faceModelList.stream().forEach(faceModel -> faceModel.setStorePath(FileUtils.correctUrl(faceModel.getStorePath())));

            return faceModelList;


        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

}

