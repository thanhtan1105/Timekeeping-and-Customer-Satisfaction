package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public class ReminderModifyModel {

    private String title;
    private String message;

    private Long time;
    private EStatus active = EStatus.ACTIVE;
    private Long managerId;
    private List<Long> employeeSet;

    public ReminderModifyModel() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public List<Long> getEmployeeSet() {
        return employeeSet;
    }

    public void setEmployeeSet(String employeeString) {
        String[] listNumber = employeeString.split(",");
        List<Long> list = new ArrayList<>();
        for (String number : listNumber){
            list.add(Long.parseLong(number));
        }
        this.employeeSet = list;
    }

    public void setEmployeeSet(List<Long> employeeSet) {
        this.employeeSet = employeeSet;
    }
}
