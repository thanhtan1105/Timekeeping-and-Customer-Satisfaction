package com.timelinekeeping.api.mcs;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/list_account")
    public List<AccountModel> list(@RequestParam(value = "id", required = false) Long idDepartment) {

        List<AccountEntity> listEntity = repo.findByDepartment(idDepartment);
        List<AccountModel> list = listEntity.stream().map(AccountModel::new).collect(Collectors.toList());
        return list;
    }
}
