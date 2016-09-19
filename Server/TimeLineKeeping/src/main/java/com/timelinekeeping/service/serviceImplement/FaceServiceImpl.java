package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.repository.FaceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by lethanhtan on 9/19/16.
 */

@Component
@Service
public class FaceServiceImpl {

    @Autowired
    private FaceRepo faceRepo;

    public FaceEntity create(String faceId, AccountEntity accountEntity) {
        FaceEntity faceEntity = new FaceEntity(faceId, accountEntity);
        faceRepo.saveAndFlush(faceEntity);
        return faceEntity;
    }

}

