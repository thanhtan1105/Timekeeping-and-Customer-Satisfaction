package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.ETypeCheckin;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.model.AccountTKReportModel;
import com.timelinekeeping.model.CheckinManualModel;
import com.timelinekeeping.model.TimekeepingResponseModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.TimekeepingRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
@Service
public class TimekeepingServiceImpl {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private TimekeepingRepo timekeepingRepo;

    private Logger logger = LogManager.getLogger(TimekeepingServiceImpl.class);

    public List<AccountCheckInModel> getEmployeeUnderManager(Long managerID) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<AccountCheckInModel> listResult = new ArrayList<>();
//            List<AccountEntity> listAccount = accountRepo.findByDepartment(departmentId);
            List<AccountEntity> listAccount = accountRepo.findByManager(managerID);
            for (AccountEntity accountEntity : listAccount) {
                TimeKeepingEntity timeKeepingEntity = timekeepingRepo.findByAccountCheckinDate(accountEntity.getId(), new Date());
                listResult.add(new AccountCheckInModel(accountEntity, timeKeepingEntity));
            }
            return listResult;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public List<CheckinManualModel> checkInManual(List<CheckinManualModel> listCheckin) {

        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<CheckinManualModel> checkinManualModels = new ArrayList<>();

            for (CheckinManualModel checkinModel : listCheckin) {
                Long accountId = checkinModel.getAccountId();
                AccountEntity accountEntity = accountRepo.findOne(accountId);
                if (accountEntity != null) {
                    TimeKeepingEntity timeKeeping = timekeepingRepo.findByAccountCheckinDate(accountId, new Date());
                    if (timeKeeping == null) {
                        timeKeeping = new TimeKeepingEntity();
                        timeKeeping.setAccount(accountEntity);
                        timeKeeping.setNote(checkinModel.getNote());
                        timeKeeping.setType(ETypeCheckin.CHECKIN_MANUAL);
                        timeKeeping.setStatus(ETimeKeeping.PRESENT);
                        timeKeeping.setTimeCheck(new Timestamp(new Date().getTime()));
                        timekeepingRepo.save(timeKeeping);
                        checkinModel.setSuccess(true);
                    } else {
                        checkinModel.setSuccess(false);
                        checkinModel.setMessage(String.format(ERROR.CHECK_IN_MANUAL_ACCOUNT_CHECKINED, accountId + ""));
                    }
                } else {
                    checkinModel.setSuccess(false);
                    checkinModel.setMessage(String.format(ERROR.CHECK_IN_MANUAL_NO_EXIST_ACCOUNTID, accountId + ""));
                }
                checkinManualModels.add(checkinModel);
            }
            timekeepingRepo.flush();
            return checkinManualModels;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    /**
     * get*/
    public TimekeepingResponseModel getTimeKeeping(Long managerId, Integer year, Integer month) {
        //get All acount by manager
        //get all timekeeping in month
        //compare to data
        //respose server
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            List<AccountEntity> listAccount = accountRepo.findByManagerNoActive(managerId);

            //filter account not in month
            //TODO filter account desiable in moth

            List<Long[]> listCountTime = timekeepingRepo.countEmployeeTime(year, month);
            Map<Long, Long> mapValue = new HashMap<>();
            for (Long[] countTime : listCountTime){
                mapValue.put(countTime[0], countTime[1]);
            }

            List<AccountTKReportModel> accountTKReportModels = new ArrayList<>();
            //create list accountResponse
            for (AccountEntity accountEntity : listAccount) {
                TimeKeepingEntity timeKeepingEntity = timekeepingRepo.findByAccountCheckinDate(accountEntity.getId(), new Date());
//                listResult.add(new CheckinManualModel(accountEntity, timeKeepingEntity));
            }

            //prepare mode response
            AccountEntity manager = accountRepo.findOne(managerId);
            TimekeepingResponseModel responseModel = new TimekeepingResponseModel(manager, year, month);
            return responseModel;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}
