package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.model.AccountAuthen;
import com.timelinekeeping.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by HienTQSE60896 on 10/1/2016.
 */
@Service
@Component
public class AuthenServiceIml {

    @Autowired
    private AccountRepo accountRepo;

    public AccountAuthen author(String username, String password){
        return new AccountAuthen(accountRepo.findByUserNameAndPassword(username, password));
    }
}
