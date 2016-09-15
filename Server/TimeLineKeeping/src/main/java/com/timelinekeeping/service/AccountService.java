package com.timelinekeeping.service;

import com.timelinekeeping.model.Account;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
public interface AccountService {
    public Account create(Account account, String groupID);
    public List<Account> listAll();

}
