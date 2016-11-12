package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.EStatusToDoTask;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.ToDoListEntity;
import com.timelinekeeping.model.ToDoListModel;
import com.timelinekeeping.model.ToDoListModifyModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.ToDoListRepo;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lethanhtan on 10/9/16.
 */

@Service
public class TodoListServiceImpl {

    @Autowired
    private ToDoListRepo toDoListRepo;

    @Autowired
    private AccountRepo accountRepo;

    private Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public List<ToDoListModel> getToDoTask(Long time, String accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            Date dateQuery = new Date(time);

            // convert list
            List<ToDoListEntity> list = toDoListRepo.findByToDoListOnMonth(accountId, dateQuery);
            List<ToDoListModel> returnValue = list.stream().map(ToDoListModel::new).collect(Collectors.toList());
            return returnValue;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public EStatusToDoTask selectTask(Long id) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            ToDoListEntity entity = toDoListRepo.findOne(id);
            EStatusToDoTask status = entity.getIsComplete();
            if (status == EStatusToDoTask.INCOMPLETE) {
                entity.setIsComplete(EStatusToDoTask.COMPLETE);
            } else {
                entity.setIsComplete(EStatusToDoTask.INCOMPLETE);
            }
            toDoListRepo.saveAndFlush(entity);
            return status == EStatusToDoTask.COMPLETE ? EStatusToDoTask.INCOMPLETE : EStatusToDoTask.COMPLETE;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public Pair<Boolean, String> create(ToDoListModifyModel modifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("Model: " + JsonUtil.toJson(modifyModel));
            //validate
            if (ValidateUtil.isEmpty(modifyModel.getTitle())) {
                return new Pair<>(false, ERROR.TODO_TITLE_EMPTY);
            }

            if (modifyModel.getAccountCreated() == null) {
                return new Pair<>(false, ERROR.TODO_ACCOUNT_CREATE_EMPTY);
            }

            //account create
            AccountEntity accountEntity = accountRepo.findOne(modifyModel.getAccountCreated());
            if (accountEntity == null) {
                return new Pair<>(false, String.format(ERROR.ACCOUNT_ID_CANNOT_EXIST, modifyModel.getAccountCreated()));
            }

            // prepare data
            ToDoListEntity entity = new ToDoListEntity(modifyModel);
            entity.setAccountCreated(accountEntity);

            // convert list
            ToDoListEntity entityResult = toDoListRepo.saveAndFlush(entity);
            if (entityResult != null && entityResult.getId() != null) {
                return new Pair<>(true, entityResult.getId() + "");
            } else {
                return new Pair<>(false, ERROR.OTHER);
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public Pair<Boolean, String> update(ToDoListModifyModel modifyModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("Model: " + JsonUtil.toJson(modifyModel));

            if (modifyModel.getId() == null) {
                return new Pair<>(false, ERROR.TODO_ID_EMPTY);
            }

            // prepare data
            ToDoListEntity entity = toDoListRepo.findOne(modifyModel.getId());
            if (entity == null) {
                return new Pair<>(false, String.format(ERROR.TODO_ID_CANNOT_EXIST, modifyModel.getId()));
            }

            //update entity
            entity.update(modifyModel);

            //account create
            if (modifyModel.getAccountCreated() != null) {
                AccountEntity accountEntity = accountRepo.findOne(modifyModel.getAccountCreated());
                if (accountEntity == null) {
                    return new Pair<>(false, String.format(ERROR.ACCOUNT_ID_CANNOT_EXIST, modifyModel.getAccountCreated()));
                }
                entity.setAccountCreated(accountEntity);
            }

            // convert list
            ToDoListEntity entityResult = toDoListRepo.saveAndFlush(entity);
            if (entityResult != null && entityResult.getId() != null) {
                return new Pair<>(true, entityResult.getId() + "");
            } else {
                return new Pair<>(false, ERROR.OTHER);
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public Pair<Boolean, String> delete(Long todoId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("todoId: " + JsonUtil.toJson(todoId));
            if (todoId == null) {
                return new Pair<>(false, ERROR.TODO_ID_EMPTY);
            }

            // prepare data
            ToDoListEntity entity = toDoListRepo.findOne(todoId);
            if (entity == null) {
                return new Pair<>(false, String.format(ERROR.TODO_ID_CANNOT_EXIST, todoId));
            }

            //delete deactive
            entity.setActive(EStatus.DEACTIVE);


            // convert list
            ToDoListEntity entityResult = toDoListRepo.saveAndFlush(entity);
            if (entityResult != null && entityResult.getId() != null) {
                return new Pair<>(true);
            } else {
                return new Pair<>(false, ERROR.OTHER);
            }
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}
