package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.model.FaceModifyModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.FaceRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

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

    //TODO compress error
    public FaceEntity create(FaceModifyModel faceModifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            FaceEntity entity = null;
            AccountEntity accountEntity;
            if (!ObjectUtils.isEmpty(faceModifyModel.getAccountId())){
                accountEntity = accountRepo.findOne(faceModifyModel.getAccountId());
            }else if (!StringUtils.isEmpty(faceModifyModel.getAccountCode())){
                accountEntity = accountRepo.findByUsercode(faceModifyModel.getAccountCode());
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

}

