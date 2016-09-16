package com.timelinekeeping.service;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.AccountView;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
public interface AccountService {
    public AccountView create(AccountEntity accountView, String groupID);
    public List<AccountView> listAll(int page, int size);

}
