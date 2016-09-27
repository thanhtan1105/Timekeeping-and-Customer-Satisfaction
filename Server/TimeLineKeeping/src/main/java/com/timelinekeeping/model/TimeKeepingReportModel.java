package com.timelinekeeping.model;

import java.math.BigInteger;

/**
 * Created by HienTQSE60896 on 9/26/2016.
 */
public class TimeKeepingReportModel {
    private Long accountId;
    private Integer countDay;

    public Long getAccountId() {
        return accountId;
    }

    public TimeKeepingReportModel(Object[] obj) {
        if (obj.length >= 2) {
            this.accountId = ((BigInteger) obj[0]).longValue();
            this.countDay = ((BigInteger) obj[1]).intValue();
        }
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getCountDay() {
        return countDay;
    }

    public void setCountDay(Integer countDay) {
        this.countDay = countDay;
    }
}
