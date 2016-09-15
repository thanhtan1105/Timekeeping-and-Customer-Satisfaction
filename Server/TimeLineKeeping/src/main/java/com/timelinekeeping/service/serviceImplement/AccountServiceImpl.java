package com.timelinekeeping.service.serviceImplement;

import com.sun.org.apache.xpath.internal.operations.String;
import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.Account;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.service.AccountService;
import com.timelinekeeping.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
@Component
@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private PersonServiceMCSImpl personServiceMCS;

    @Autowired
    private AccountRepo accountRepo;


    @Override
    public Account create(Account account, String groupID) {
        try {
            AccountEntity entity = new AccountEntity(account);
            Map<String, String> map = (Map<String, String>) personServiceMCS.createPerson(groupID, account.getUsername(), JsonUtil.toJson(account));
            String personCode = map.get("personId");
            account.setUserCode(personCode);
            accountRepo.saveAndFlush(entity);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Account> listAll() {
        List<AccountEntity> accountEntities = accountRepo.findAll();
        List<Account> accounts = new ArrayList<>();
        for (AccountEntity accountEntity : accountEntities){
            accounts.add(new Account(accountEntity));
        }
        return accounts;
    }
}
