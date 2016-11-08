package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.modelMCS.PersonInformation;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.FaceRepo;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 11/7/2016.
 */
@Service
@Component
public class HandlerServiceImpl {

    Logger logger = LogManager.getLogger(HandlerServiceImpl.class);

    @Autowired
    AccountRepo accountRepo;


    @Autowired
    PersonServiceMCSImpl personServiceMCS;

    @Autowired
    FaceRepo faceRepo;

    public Boolean synchonize() throws IOException, URISyntaxException {

        /** Account*/
        List<AccountEntity> accountEntities = accountRepo.findAll();
        BaseResponse baseResponse = personServiceMCS.listPersonInGroup(IContanst.DEPARTMENT_MICROSOFT);
        if (!baseResponse.isSuccess()) {
            //call api error
            return false;
        }
        List<PersonInformation> personInformationList = (List<PersonInformation>) baseResponse.getData();
        Map<String, PersonInformation> personMap = personInformationList.stream().collect(Collectors.toMap(PersonInformation::getPersonId, personInformation -> personInformation));


        // check userCode exist in list Persisted
        for (AccountEntity accountEntity : accountEntities) {
            PersonInformation personSelect = personMap.get(accountEntity.getUserCode());
            if (personSelect == null) {
                //create person
                personServiceMCS.createPerson(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), accountEntity.getFullName());
                for (FaceEntity faceEntity : accountEntity.getFaces()) {
                    BaseResponse response = personServiceMCS.addFaceUrl(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), faceEntity.getStorePath());
                    if (response.isSuccess() == true) {
                        Map<String, String> mapResult = (Map<String, String>) response.getData();
                        if (mapResult != null && mapResult.size() > 0) {
                            String persistedFaceID = mapResult.get("persistedFaceId");
                            faceEntity.setPersistedFaceId(persistedFaceID);
                            faceRepo.save(faceEntity);
                        }

                    }
                }
            } else {
                List<String> persistedFaces = personSelect.getPersistedFaceIds();
                List<String> listFace = accountEntity.getFaces().stream().map(FaceEntity::getPersistedFaceId).collect(Collectors.toList());
                if (ValidateUtil.isNotEmpty(accountEntity.getFaces())) {
                    for (FaceEntity faceEntity : accountEntity.getFaces()) {
                        if (persistedFaces != null && persistedFaces.contains(faceEntity.getPersistedFaceId())){
                            BaseResponse response = personServiceMCS.addFaceUrl(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), faceEntity.getStorePath());
                            if (response.isSuccess() == true) {
                                Map<String, String> mapResult = (Map<String, String>) response.getData();
                                if (mapResult != null && mapResult.size() > 0) {
                                    String persistedFaceID = mapResult.get("persistedFaceId");
                                    faceEntity.setPersistedFaceId(persistedFaceID);
                                    faceRepo.save(faceEntity);
                                }

                            }
                        }
                    }

                }

//                /**delete face*/
//                if ()


            }

        }


        return null;
    }
}
