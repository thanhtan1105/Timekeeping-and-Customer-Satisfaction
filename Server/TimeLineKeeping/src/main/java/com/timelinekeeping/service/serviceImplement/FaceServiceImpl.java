package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.model.FaceModifyModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.FaceRepo;
import com.timelinekeeping.util.HTTPClientUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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

    Logger logger = LogManager.getLogger(FaceServiceImpl.class);

    String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url");

    //TODO compress error
    public FaceEntity create(FaceModifyModel faceModifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            FaceEntity entity = null;
            AccountEntity accountEntity;
            if (!ObjectUtils.isEmpty(faceModifyModel.getAccountId())){
                accountEntity = accountRepo.findOne(faceModifyModel.getAccountId());
            }else if (!StringUtils.isEmpty(faceModifyModel.getAccountCode())){
                accountEntity = accountRepo.findByUserCode(faceModifyModel.getAccountCode());
            }else {
                return null;
            }
            if (accountEntity == null){
                return null;
            }
            entity = new FaceEntity(faceModifyModel.getPersistedFaceId(), accountEntity);
            FaceEntity result = faceRepo.saveAndFlush(entity);
            return result;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Boolean removeFace(String personGroupId, Long accountId, Long faceId) throws URISyntaxException, IOException {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

        AccountEntity accountEntity = accountRepo.findOne(accountId);
        if (accountEntity == null){
            return null;
        }
        FaceEntity faceEntity = faceRepo.findOne(faceId);
        if (faceEntity == null){
            return null;
        }
        BaseResponse baseResponse = callAPIremoveMCS(personGroupId, accountEntity.getUserCode(), faceEntity.getPersistedFaceId());

        if (baseResponse.isSuccess() == true) {
            // remove on db
            faceEntity.setActive(EStatus.DEACTIVE);
            faceRepo.save(faceEntity);
            return true;
        }else {

            return false;
        }
    }

    public BaseResponse callAPIremoveMCS(String personGroupId, String personID, String persistedFaceId) throws URISyntaxException, IOException {
        String urlDeleteFace = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group") + "/" + personGroupId;
        urlDeleteFace += "/" + AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.delete.person.face.1.addition") + "/" + personID;
        urlDeleteFace += "/" + AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.delete.person.face.2.addition") + "/" + persistedFaceId;
        String url = rootPath  + urlDeleteFace;

        BaseResponse faceResponse = HTTPClientUtil.getInstanceFace().toGet(new URI(url), String.class);
        return faceResponse;

    }
}

