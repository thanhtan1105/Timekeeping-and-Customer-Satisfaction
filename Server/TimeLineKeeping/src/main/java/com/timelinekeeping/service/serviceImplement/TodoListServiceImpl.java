package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.EStatusToDoTask;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.ToDoListEntity;
import com.timelinekeeping.model.ToDoListModel;
import com.timelinekeeping.repository.ToDoListRepo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lethanhtan on 10/9/16.
 */

@Service
public class TodoListServiceImpl {

    @Autowired
    private ToDoListRepo toDoListRepo;

    private Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public List<ToDoListModel> getToDoTask(int day, int month, int year, String accountId) {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

        // convert list
        List<ToDoListEntity> list = toDoListRepo.findByToDoListOnMonth(accountId, month, year, day);
        List<ToDoListModel> returnValue = list.stream().map(ToDoListModel::new).collect(Collectors.toList());
        return returnValue;
    }

    public EStatusToDoTask selectTask(Long id) {
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
    }
}
