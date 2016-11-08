package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.modelMCS.PersonInformation;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.FaceRepo;
import com.timelinekeeping.util.JsonUtil;
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
    PersonGroupServiceMCSImpl personGroupServiceMCS;

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

        logger.info(String.format("AccountEntities.size() = %s, personInformationList.size() = %s", accountEntities.size(), personInformationList.size()));

        // check userCode exist in list Persisted
        for (AccountEntity accountEntity : accountEntities) {
            PersonInformation personSelect = personMap.get(accountEntity.getUserCode());
            if (personSelect == null) {

                logger.info(String.format("Create account: id = [%s], username = [%s] ", accountEntity.getId(), accountEntity.getUsername()));
                //create person
                BaseResponse responseAccount = personServiceMCS.createPerson(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), accountEntity.getFullName());
                if (responseAccount.isSuccess()) {

                    /*** replace person in person group*/

                    //get personCode
                    Map<String, String> mapValue = (Map<String, String>) responseAccount.getData();
                    String personCode = mapValue.get("personId");
                    logger.info("personCode: " + personCode);

                    // store db
                    accountEntity.setUserCode(personCode);
                    accountRepo.save(accountEntity);


                    for (FaceEntity faceEntity : accountEntity.getFaces()) {

                        logger.info(String.format("Create face: id = [%s], path = [%s] ", faceEntity.getId(), faceEntity.getStorePath()));

                        BaseResponse response = personServiceMCS.addFaceUrl(IContanst.DEPARTMENT_MICROSOFT, personCode, faceEntity.getStorePath());
                        if (response.isSuccess() == true) {
                            Map<String, String> mapResult = (Map<String, String>) response.getData();
                            if (mapResult != null && mapResult.size() > 0) {
                                String persistedFaceID = mapResult.get("persistedFaceId");
                                faceEntity.setPersistedFaceId(persistedFaceID);
                                faceRepo.save(faceEntity);
                            }

                        }
                    }

                    accountRepo.flush();
                    faceRepo.flush();
                }
            } else {
                List<String> persistedFaces = personSelect.getPersistedFaceIds();
                List<String> listFace = accountEntity.getFaces().stream().map(FaceEntity::getPersistedFaceId).collect(Collectors.toList());

                logger.info("ListFace: " + JsonUtil.toJson(listFace));
                logger.info("ListFace Size(): " + listFace.size());
                logger.info("ListPersisted: " + JsonUtil.toJson(persistedFaces));
                logger.info("ListPersisted size: " + persistedFaces.size());

                if (ValidateUtil.isNotEmpty(accountEntity.getFaces())) {
                    for (FaceEntity faceEntity : accountEntity.getFaces()) {
                        if (persistedFaces != null && !persistedFaces.contains(faceEntity.getPersistedFaceId())) {
                            logger.info(String.format("Create face: id = [%s], path = [%s] ", faceEntity.getId(), faceEntity.getStorePath()));
                            BaseResponse responseFace = personServiceMCS.addFaceUrl(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), faceEntity.getStorePath());
                            if (responseFace.isSuccess() == true) {
                                Map<String, String> mapResult = (Map<String, String>) responseFace.getData();
                                if (mapResult != null && mapResult.size() > 0) {
                                    String persistedFaceID = mapResult.get("persistedFaceId");
                                    faceEntity.setPersistedFaceId(persistedFaceID);
                                    faceRepo.save(faceEntity);
                                }
                            } else {
                                logger.info("FaceId: detectFail");
                            }
                        }
                    }
                }

                if (ValidateUtil.isNotEmpty(persistedFaces)) {
                    // delete list face
                    persistedFaces.removeAll(listFace);
                    logger.info("Remove list: " + JsonUtil.toJson(persistedFaces));
                    for (String persiste : persistedFaces) {
                        logger.info("Remove FaceCode: " + persiste);
                        BaseResponse responseRemoveFace = personServiceMCS.removePersonFace(IContanst.DEPARTMENT_MICROSOFT, accountEntity.getUserCode(), persiste);
                        if (responseRemoveFace.isSuccess()) {
                            logger.info(String.format("persiste = [%s] remove success", persiste));
                        } else {
                            logger.info(String.format("persiste = [%s] remove fail", persiste));
                        }


                    }
                }
//                /**delete face*/
//                if ()

                faceRepo.flush();

            }

        }

        personGroupServiceMCS.trainGroup(IContanst.DEPARTMENT_MICROSOFT);


        return null;
    }
}
