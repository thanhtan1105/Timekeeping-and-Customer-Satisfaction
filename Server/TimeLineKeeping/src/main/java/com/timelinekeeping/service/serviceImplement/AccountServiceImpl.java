package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonServiceMCSImpl;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.AccountView;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
@Component
@Service
public class AccountServiceImpl {

    @Autowired
    private PersonServiceMCSImpl personServiceMCS;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private DepartmentServiceImpl departmentService;

    public BaseResponse create(AccountEntity account) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            System.out.println("Account: " + account.getUsername());
            if (isExist(account.getUsername())) {
                baseResponse.setSuccess(false);
                baseResponse.setMessage("User name " + account.getUsername() + " already exists.");
                baseResponse.setErrorCode("Account already exist");

            } else {
                // get department code
                DepartmentEntity departmentEntity = departmentService.findBy(account.getDepartmentId());
                String departmentCode = departmentEntity.getCode();

                BaseResponse response = personServiceMCS.createPerson(departmentCode, account.getUsername(), JsonUtil.toJson(account));
                Map<String, String> map = (Map<String, String>) response.getData();
                String personCode = map.get("personId");
                account.setUserCode(personCode);
                account.setActive(1);
                AccountEntity result = accountRepo.saveAndFlush(account);
                if (result != null) {
                    baseResponse.setSuccess(true);
                    baseResponse.setData(new AccountView(result));
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return baseResponse;
        }
    }

    public BaseResponse listAll(Integer page, Integer size) {
        BaseResponse response = new BaseResponse();
        if (page != null && size != null){
            response.setSuccess(true);
            response.setData(accountRepo.findAll());
        } else {
            response.setSuccess(true);
            response.setData(accountRepo.findAll(new PageRequest(page, size)));
        }
        return response;
    }

    public boolean isExist(String username) {
        AccountEntity accountView = accountRepo.findByUsername(username);
        return accountView == null ? false : true;
    }
}
