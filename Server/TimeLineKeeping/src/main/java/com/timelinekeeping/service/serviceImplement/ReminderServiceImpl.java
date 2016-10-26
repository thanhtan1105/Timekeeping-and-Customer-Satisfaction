package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.BaseResponseG;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CoordinateEntity;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.entity.ReminderMessageEntity;
import com.timelinekeeping.model.ReminderModel;
import com.timelinekeeping.model.ReminderModifyModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.CoordinateRepo;
import com.timelinekeeping.repository.NotificationRepo;
import com.timelinekeeping.repository.ReminderRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
@Service
public class ReminderServiceImpl {

    @Autowired
    private ReminderRepo reminderRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private CoordinateRepo coordinateRepo;

    private Logger logger = LogManager.getLogger(ReminderServiceImpl.class);

    public Page<ReminderModel> findAll(int page, int size) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //request
            PageRequest pageRequest = new PageRequest(page, size);

            //save DB
            Page<ReminderMessageEntity> pageEntity = reminderRepo.findAll(pageRequest);

            //Convert data
            List<ReminderModel> departmentModels = pageEntity.getContent().stream().map(ReminderModel::new).collect(Collectors.toList());
            Page<ReminderModel> pageDepartment = new PageImpl<>(departmentModels, pageRequest, pageEntity.getTotalElements());
            logger.info("[Find All] " + JsonUtil.toJson(pageDepartment));

            return pageDepartment;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public ReminderModel get(Long id){
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            ReminderMessageEntity entity = reminderRepo.findOne(id);
            if (entity != null){
                Set<NotificationEntity> notificationEntitySet = notificationRepo.findReminder(id);
                entity.setNotificationSet(notificationEntitySet);
                return new ReminderModel(entity);
            }else {
                return null;
            }
        }finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Page<ReminderModel> listByEmployee(Long managerId, int page, int size) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //request
            PageRequest pageRequest = new PageRequest(page, size);

            //save DB
            Page<ReminderMessageEntity> pageEntity = reminderRepo.list(managerId, pageRequest);

            //Convert data
            List<ReminderModel> departmentModels = pageEntity.getContent().stream().map(ReminderModel::new).collect(Collectors.toList());
            Page<ReminderModel> pageDepartment = new PageImpl<>(departmentModels, pageRequest, pageEntity.getTotalElements());
            logger.info("[Find All] " + JsonUtil.toJson(pageDepartment));

            return pageDepartment;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public BaseResponseG<ReminderModel> create(ReminderModifyModel reminder) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("reminder: " + JsonUtil.toJson(reminder));

            //get ManagerID
            AccountEntity mangerEntity = accountRepo.findOne(reminder.getManagerId());
            if (mangerEntity == null || mangerEntity.getActive() == EStatus.DEACTIVE) {
                return new BaseResponseG<>(false, String.format(ERROR.REMINDER_MANAGER_ID_NO_EXIST, reminder.getManagerId()));
            }

            //coordinate
            CoordinateEntity coordinateEntity = coordinateRepo.findOne(reminder.getRoomId());
            if (coordinateEntity == null) {
                return new BaseResponseG<>(false, String.format(ERROR.REMINDER_ROOM_ID_NO_EXIST, reminder.getRoomId()));
            }

            //saveDB
            ReminderMessageEntity reminderEntity = new ReminderMessageEntity(reminder);
            reminderEntity.setManager(mangerEntity);
            reminderEntity.setRoom(coordinateEntity);
            ReminderMessageEntity entityResult = reminderRepo.saveAndFlush(reminderEntity);

            if (reminder.getEmployeeSet() != null && reminder.getEmployeeSet().size() > 0) {
                //create notification
                for (Long employeeId : reminder.getEmployeeSet()) {
                    AccountEntity employee = accountRepo.findOne(employeeId);
                    NotificationEntity notificationEntity = new NotificationEntity();
                    notificationEntity.setReminderMessage(entityResult);
                    notificationEntity.setAccountReceive(employee);
                    notificationRepo.save(notificationEntity);
                }
                notificationRepo.flush();
            }

            if (entityResult != null) {
                return new BaseResponseG<ReminderModel>(true, new ReminderModel(reminderRepo.findOne(entityResult.getId())));
            }
            return new BaseResponseG<>(false, ERROR.OTHER);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public BaseResponseG<ReminderModel> update(ReminderModifyModel reminder) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("reminder: " + JsonUtil.toJson(reminder));

            //find reminder is
            ReminderMessageEntity entity = reminderRepo.findOne(reminder.getId());

            //edit Manager
            if (entity.getManager() == null || (entity.getManager().getId() != reminder.getManagerId())) {

                //edit manager
                AccountEntity mangerEntity = accountRepo.findOne(reminder.getManagerId());
                if (mangerEntity == null || mangerEntity.getActive() == EStatus.DEACTIVE) {
                    return new BaseResponseG<>(false, String.format(ERROR.REMINDER_MANAGER_ID_NO_EXIST, reminder.getManagerId()));
                }
                entity.setManager(mangerEntity);
            }

            //edit coordinate
            CoordinateEntity coordinateEntity = coordinateRepo.findOne(reminder.getRoomId());
            if (coordinateEntity == null) {
                return new BaseResponseG<>(false, String.format(ERROR.REMINDER_ROOM_ID_NO_EXIST, reminder.getRoomId()));
            }
            entity.setRoom(coordinateEntity);


            // update reminder class
            reminderRepo.saveAndFlush(entity);


            //get Old Employee
            Set<NotificationEntity> notificationSet = notificationRepo.findReminder(reminder.getId());
            Set<Long> employeeIDEntity = notificationSet.stream().map(NotificationEntity::getAccountReceive).map(AccountEntity::getId).collect(Collectors.toSet());

            //get new Employee
            Set<Long> employeeModel = reminder.getEmployeeSet();

            // remove old notification
            notificationSet.stream().filter(notificationEntity -> !employeeModel.contains(notificationEntity.getAccountReceive().getId()))
                    .forEach(notificationEntity -> {
                        notificationEntity.setActive(EStatus.DEACTIVE);
                        notificationRepo.save(notificationEntity);
                    });

            // add new notfication
            reminder.getEmployeeSet().stream().filter(employeeId -> !employeeIDEntity.contains(employeeId)).forEach(
                    employeeId -> {
                        AccountEntity employee = accountRepo.findOne(employeeId);
                        NotificationEntity notificationEntity = new NotificationEntity();
                        notificationEntity.setReminderMessage(entity);
                        notificationEntity.setAccountReceive(employee);
                        notificationRepo.save(notificationEntity);
                    }
            );

            notificationRepo.flush();


            return new BaseResponseG<>(true);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public void delete(Long reminderId){
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("reminderIds: " + JsonUtil.toJson(reminderId));
            ReminderMessageEntity entity = reminderRepo.findOne(reminderId);
        }finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

}
