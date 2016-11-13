package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.*;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import com.timelinekeeping.model.*;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.TimekeepingRepo;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ServiceUtils;
import com.timelinekeeping.util.TimeUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

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
                if (timeKeepingEntity != null) {
                    //if type checkin = camera, reset time keeping
                    if (timeKeepingEntity.getType() == ETypeCheckin.CHECKIN_CAMERA) {
                        if (TimeUtil.isPresentTimeCheckin(timeKeepingEntity.getTimeCheck())) {
                            timeKeepingEntity.setStatus(ETimeKeeping.PRESENT);
                        } else {
                            timeKeepingEntity.setStatus(ETimeKeeping.ABSENT);
                        }
                    }else{
                        timeKeepingEntity.setTimeCheck(null);
                    }
                }
                AccountCheckInModel accountCheckInModel = new AccountCheckInModel(accountEntity, timeKeepingEntity);
                listResult.add(accountCheckInModel);
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
                    String note = String.format(ERROR.MESSAGE_CHECKIN_MANUAL, accountEntity.getManager().getUsername()) + " " + checkinModel.getNote();
                    if (timeKeeping == null) {


                        timeKeeping = new TimeKeepingEntity();
                        timeKeeping.setAccount(accountEntity);
                        timeKeeping.setNote(note);
                        timeKeeping.setType(ETypeCheckin.CHECKIN_MANUAL);
                        timeKeeping.setStatus(ETimeKeeping.PRESENT);
                        timeKeeping.setTimeCheck(new Timestamp(new Date().getTime()));
                        timeKeeping.setRpManagerId(accountEntity.getManager().getId());
                        timeKeeping.setRpDepartmentId(accountEntity.getDepartment().getId());
                        timekeepingRepo.save(timeKeeping);
                        checkinModel.setSuccess(true);
                    } else if (timeKeeping.getType() == ETypeCheckin.CHECKIN_CAMERA && TimeUtil.isPresentTimeCheckin(timeKeeping.getTimeCheck()) == false) {
                        timeKeeping.setNote(note);
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
     * get
     */
    public TimekeepingResponseModel getTimeKeeping(Long managerId, Integer year, Integer month) {

        //get from_time, to_time
        Date timeFrom = TimeUtil.parseToDate(IContanst.TIME_CHECK_IN_SYSTEM_START, I_TIME.TIME_MINUTE);
        Date timeto = TimeUtil.parseToDate(IContanst.TIME_CHECK_IN_SYSTEM_END, I_TIME.TIME_MINUTE);

        //get All acount by manager
        //get all timekeeping in month
        //compare to data
        //respose server
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info(String.format("getTimeKeeping [managerId = '%s'], [year = '%s'], [month = '%s']", managerId, year, month));

            List<AccountEntity> listAccount = accountRepo.findByManagerNoActive(managerId);


            //get All Timekeepung
            List<TimeKeepingEntity> listTimekeeping = timekeepingRepo.countEmployeeTime(year, month);
            Map<Long, Long> mapChekin = new HashMap<>();
            for (TimeKeepingEntity timeKeepingEntity : listTimekeeping){
                Long count = mapChekin.get(timeKeepingEntity.getAccount().getId());
                if (count == null) count = 0l;
                if (timeKeepingEntity.getType() == ETypeCheckin.CHECKIN_CAMERA ){
                    if (TimeUtil.isPresentTimeCheckin(timeKeepingEntity.getTimeCheck())){
                        count++;
                    }
                }else {
                    if (timeKeepingEntity.getStatus() == ETimeKeeping.PRESENT){
                        count++;
                    }
                }
                mapChekin.put(timeKeepingEntity.getAccount().getId(), count);
            }



//            Map<Long, Long> mapChekin = new HashMap<>();
//            listCountTime.stream().filter(countTime -> countTime.length >= 2)
//                    .forEach(countTime -> mapChekin.put(((BigInteger) countTime[0]).longValue(), ((BigInteger) countTime[1]).longValue()));

            List<AccountTKReportModel> accountTKReportModels = new ArrayList<>();
            //create list accountResponse
            for (AccountEntity accountEntity : listAccount) {

                boolean isRun = checkMonthBetweenMonth(year, month, accountEntity.getTimeCreate(), accountEntity.getActive() == EStatus.DEACTIVE ? accountEntity.getTimeDeactive() : null);
                if (isRun) {

                    //getDayCheckIn
                    Long accountId = accountEntity.getId();
                    Long dayCheckIn = mapChekin.get(accountId);

                    //Count Work day
                    // note
                    Timestamp deactiveTime = accountEntity.getActive() == EStatus.DEACTIVE ? accountEntity.getTimeDeactive() : null;
                    int workDay = ServiceUtils.countWorkDay(year, month, accountEntity.getTimeCreate(), deactiveTime);

                    // it must account deactive workDay >0, it cannot accept timekeeping 0/0
                    if (accountEntity.getActive() == EStatus.DEACTIVE && workDay <= 0) {
                        continue;
                    }

                    AccountTKReportModel accountTK = new AccountTKReportModel(accountEntity);
                    if (dayCheckIn != null) {
                        accountTK.setDayCheckin(dayCheckIn.intValue());
                    }
                    accountTK.setDayWork(workDay);

                    accountTKReportModels.add(accountTK);

                }
            }

            //prepare mode response
            AccountEntity manager = accountRepo.findOne(managerId);
            TimekeepingResponseModel responseModel = new TimekeepingResponseModel(manager, year, month);
            responseModel.setListEmployee(accountTKReportModels);
            logger.info("Response: " + JsonUtil.toJson(responseModel));
            return responseModel;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public AccountAttendanceModel getAttendance(Long accountId, Integer year, Integer month) {


        //getAttendance from sql
        List<TimeKeepingEntity> timeKeepingEntityList = timekeepingRepo.getTimekeepingByAccount(accountId, year, month);

        //convert to map with date key
        Map<Integer, TimeKeepingEntity> mapTimeKeeping = new HashMap<>();
        for (TimeKeepingEntity timeKeepingEntity : timeKeepingEntityList) {
            Date timeCheck = timeKeepingEntity.getTimeCheck();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(timeCheck);
            Integer dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (timeKeepingEntity.getType() == ETypeCheckin.CHECKIN_CAMERA) {
                if (TimeUtil.isPresentTimeCheckin(timeCheck)) {
                    timeKeepingEntity.setStatus(ETimeKeeping.PRESENT);
                } else {
                    timeKeepingEntity.setStatus(ETimeKeeping.ABSENT);
                }
            } else {
                timeKeepingEntity.setTimeCheck(null);
            }
            mapTimeKeeping.put(dayOfMonth, timeKeepingEntity);
        }

        //getAccount Entity
        AccountEntity accountEntity = accountRepo.findOne(accountId);

        //deplay attendance in repo with list day status, day, present
        List<AttendanceDateModel> listAttendance = new ArrayList<>();
        YearMonth yearMonth = YearMonth.of(year, month);
        int dayInMonth = yearMonth.lengthOfMonth();
        for (int i = 1; i <= dayInMonth; i++) {

            //create attendance
            AttendanceDateModel attendance = new AttendanceDateModel();
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, i);
            attendance.setDay(i);
            attendance.setDate(calendar.getTime());

            attendance.setDayStatus(ServiceUtils.convertDateType(accountEntity, calendar));

            //Get present from entity
            TimeKeepingEntity timeKeeping = mapTimeKeeping.get(calendar.get(Calendar.DAY_OF_MONTH));
            if (timeKeeping != null) {
                attendance.from(timeKeeping);
            }

            //add attendance
            listAttendance.add(attendance);
        }

        //dayWork
        int dayWork = ServiceUtils.countWorkDay(year, month, accountEntity.getTimeCreate(), accountEntity.getTimeDeactive());

        //count totalPresent

        int totalPresent = listAttendance.stream().filter(attendanceDateModel -> attendanceDateModel.getPresent() == ETimeKeeping.PRESENT).collect(Collectors.toList()).size();

        //prepare return model
        AccountAttendanceModel accountAttendance = new AccountAttendanceModel(accountEntity, year, month);
        accountAttendance.setDayWork(dayWork);
        accountAttendance.setTotalTimeKeeping(totalPresent);
        accountAttendance.setAttendances(listAttendance);
        return accountAttendance;
    }


    /***
     * compare time select between fromMonth, toMonth
     * 11-9-2016
     * @param year + month: month of select
     * @param fromMonth : Time from
     * @param toMonth: time to*/
    private boolean checkMonthBetweenMonth(int year, int month, Timestamp fromMonth, Timestamp toMonth) {
        YearMonth monthSelect = YearMonth.of(year, month);
        YearMonth monthCreate = TimeUtil.parseYearMonth(fromMonth);
        YearMonth monthDeactive = toMonth == null ? YearMonth.now() : TimeUtil.parseYearMonth(toMonth);

        // check condition monthCreate <= monthSelect <= monthDeactive
        boolean result = (monthSelect.compareTo(monthCreate) >= 0 && monthSelect.compareTo(monthDeactive) <= 0);
        return result;
    }


}
