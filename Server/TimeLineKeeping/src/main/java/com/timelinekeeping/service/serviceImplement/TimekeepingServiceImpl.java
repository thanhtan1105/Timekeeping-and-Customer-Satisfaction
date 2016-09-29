package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.*;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;
import com.timelinekeeping.model.*;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.TimekeepingRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.YearMonth;
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
     * get
     */
    public TimekeepingResponseModel getTimeKeeping(Long managerId, Integer year, Integer month) {
        //get All acount by manager
        //get all timekeeping in month
        //compare to data
        //respose server
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info(String.format("getTimeKeeping [managerId = '%s'], [year = '%s'], [month = '%s']", managerId, year, month));

            List<AccountEntity> listAccount = accountRepo.findByManagerNoActive(managerId);

            //filter account not in month
            //TODO filter account desiable in moth

            List<Object[]> listCountTime = timekeepingRepo.countEmployeeTime(year, month);
            Map<Long, Long> mapChekin = new HashMap<>();
            listCountTime.stream().filter(countTime -> countTime.length >= 2)
                    .forEach(countTime -> mapChekin.put(((BigInteger) countTime[0]).longValue(), ((BigInteger) countTime[1]).longValue()));

            List<AccountTKReportModel> accountTKReportModels = new ArrayList<>();
            //create list accountResponse
            for (AccountEntity accountEntity : listAccount) {

                //getDayCheckIn
                Long accountId = accountEntity.getId();
                Long dayCheckIn = mapChekin.get(accountId);

                //Count Work day
                int workDay = countWorkDay(year, month, accountEntity.getTimeCreate(), accountEntity.getTimeDeactive());

                AccountTKReportModel accountTK = new AccountTKReportModel(accountEntity);
                if (dayCheckIn != null) {
                    accountTK.setDayCheckin(dayCheckIn.intValue());
                }
                accountTK.setDayWork(workDay);
                accountTKReportModels.add(accountTK);
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
            if (timeKeepingEntity.getStatus() == ETimeKeeping.PRESENT) {
                mapTimeKeeping.put(dayOfMonth, timeKeepingEntity);
            }
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

            //set DayStatus
            if (accountEntity.getTimeCreate() != null && calendar.getTime().compareTo(accountEntity.getTimeCreate()) < 0) {
                attendance.setDayStatus(EDayStatus.DAY_BEFORE_CREATE);
            } else if (accountEntity.getTimeDeactive() != null && calendar.getTime().compareTo(accountEntity.getTimeDeactive()) > 0) {
                attendance.setDayStatus(EDayStatus.DAY_AFTER_DEACTIVE);
            } else {
                EDayOfWeek dayOfWeek = EDayOfWeek.fromIndex(calendar.get(Calendar.DAY_OF_WEEK));
                if (dayOfWeek == EDayOfWeek.SUNDAY || dayOfWeek == EDayOfWeek.SATURDAY) {
                    attendance.setDayStatus(EDayStatus.DAY_OFF);
                }
            }

            //Get present from entity
            TimeKeepingEntity timeKeeping = mapTimeKeeping.get(calendar.get(Calendar.DAY_OF_MONTH));
            if (timeKeeping != null) {
                attendance.from(timeKeeping);
            }

            //add attendance
            listAttendance.add(attendance);
        }

        //dayWork
        int dayWork = countWorkDay(year, month, accountEntity.getTimeCreate(), accountEntity.getTimeDeactive());
        //prepare return model

        AccountAttendanceModel accountAttendance = new AccountAttendanceModel(accountEntity, year, month);
        accountAttendance.setDayWork(dayWork);
        accountAttendance.setTotalTimeKeeping(timeKeepingEntityList.size());
        accountAttendance.setAttendances(listAttendance);
        return accountAttendance;
    }

    // detemeter fromday, today
    public static int countWorkDay(int year, int month, Date timeCreate, Date timeDeactive) {

        if (timeCreate == null || (timeDeactive != null && timeCreate.compareTo(timeDeactive) > 0)) {
            return 0;
        }

        //init
        int dayOfMonth = YearMonth.of(year, month).lengthOfMonth();
        int fromday = 1, today = dayOfMonth;


        //start day
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, fromday);

        //if timeDeactive < fromday, return 0
        if (timeDeactive != null && calendar.getTime().compareTo(timeDeactive) > 0) {
            return 0;
        }

        //if formday < createtime,  fromday = createTime
        if (calendar.getTime().compareTo(timeCreate) < 0) {
            calendar.setTime(timeCreate);
            fromday = calendar.get(Calendar.DAY_OF_MONTH);
        }

        //today
        calendar.set(year, month - 1, dayOfMonth);

        //if today < createTime, return 0;
        if (calendar.getTime().compareTo(timeCreate) < 0) {
            return 0;
        }
        //if timeDeactive < today,  today = timeDeactive
        if (timeDeactive != null && calendar.getTime().compareTo(timeDeactive) > 0) {
            calendar.setTime(timeDeactive);
            today = calendar.get(Calendar.DAY_OF_MONTH);

            //if now() < today,  today = now()
        } else if (calendar.getTime().compareTo(new Date()) > 0) {
            calendar.setTime(new Date());
            today = calendar.get(Calendar.DAY_OF_MONTH);
        }


        return countWorkDay(year, month, fromday, today);

    }

    private static int countWorkDay(int year, int month, int fromday, int today) {
        if (fromday > today) {
            return 0;
        }
        int dayWork = 0;
        Calendar calendar = Calendar.getInstance();
        for (int i = fromday; i <= today; i++) {
            calendar.set(year, month - 1, i);
            EDayOfWeek dayOfWeek = EDayOfWeek.fromIndex(calendar.get(Calendar.DAY_OF_WEEK));
            if (dayOfWeek != EDayOfWeek.SATURDAY && dayOfWeek != EDayOfWeek.SUNDAY) {
                dayWork++;
            }
        }
        return dayWork;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 8, 1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2016, 8, 1);
        System.out.println(countWorkDay(2016, 9, calendar.getTime(), calendar2.getTime()));
    }
}
