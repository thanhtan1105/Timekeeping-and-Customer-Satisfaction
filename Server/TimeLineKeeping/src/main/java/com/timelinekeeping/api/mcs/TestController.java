package com.timelinekeeping.api.mcs;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.NotificationCheckInModel;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.management.remote.NotificationResult;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
@RestController
@RequestMapping("/api/test")
public class TestController {
    @Autowired
    private AccountRepo repo;

    @Autowired
    private NotificationRepo notificationRepo;

    @RequestMapping("/list_account")
    public Page<AccountModel> list(@RequestParam(value = "id", required = false) Long idDepartment) {

        Page<AccountEntity> page = repo.findByDepartment(idDepartment, new PageRequest(0, 100));
        List<AccountModel> list = page.getContent().stream().map(AccountModel::new).collect(Collectors.toList());
        return new PageImpl<AccountModel>(list, new PageRequest(0,100), page.getTotalElements());
    }

    @RequestMapping("/get_notify")
    public List<NotificationCheckInModel> getNotify(@RequestParam(value = "account_id", required = false) Long accountId) {
        List<NotificationEntity> lisNotify =  notificationRepo.findByAccountReceiveByDate(accountId);
        List<NotificationCheckInModel> listCheckIn = lisNotify.stream().map(NotificationCheckInModel::new).collect(Collectors.toList());
        return listCheckIn;
    }
}
