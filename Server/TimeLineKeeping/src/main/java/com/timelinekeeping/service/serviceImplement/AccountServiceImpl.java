package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.AccountView;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.service.AccountService;
import com.timelinekeeping.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
public class AccountServiceImpl implements AccountService {
    @Autowired
    private PersonServiceMCSImpl personServiceMCS;


    @Autowired
    private AccountRepo accountRepo;



    @Override
    public AccountView create(AccountEntity account, String groupID) {
        try {
            BaseResponse response = personServiceMCS.createPerson(groupID, account.getUsername(), JsonUtil.toJson(account));
            Map<String, String> map = (Map<String, String>) response.getData();
            String personCode = map.get("personId");
            account.setUserCode(personCode);
            account.setActive(1);
            AccountEntity result = accountRepo.saveAndFlush(account);
            if (result != null) {
                return new AccountView(result);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public BaseResponse listAll(Integer page, Integer size) {
        BaseResponse response = new BaseResponse();
        if (page != null && size != null){
            response.setSuccess(true);
            response.setData(accountRepo.findAll());
        }else {
            response.setSuccess(true);
            response.setData(accountRepo.findAll(new PageRequest(page, size)));
        }
//        List<AccountView> accountViews = new ArrayList<>();
//        for (AccountEntity accountEntity : accountEntities) {
//            accountViews.add(new AccountView(accountEntity));
//        }
        return response;
    }
}
