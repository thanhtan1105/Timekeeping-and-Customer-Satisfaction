package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.entity.AccountEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/26/2016.
 */
public class AccountTKReportModel {
    private Long id;
    private String fullname;
    private Integer countDay;
    private Integer dayWork;
    private EStatus active;
    private Date dateDeactive;


    public AccountTKReportModel(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.fullname = entity.getFullname();
            this.active = entity.getActive();
            this.dateDeactive = entity.getTimeDeactive();
        }
    }

    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCountDay() {
        return countDay;
    }

    public void setCountDay(Integer countDay) {
        this.countDay = countDay;
    }
}
