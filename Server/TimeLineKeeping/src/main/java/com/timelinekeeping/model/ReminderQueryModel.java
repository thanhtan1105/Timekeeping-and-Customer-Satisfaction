package com.timelinekeeping.model;

import java.util.Set;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public class ReminderQueryModel {

    private String title;
    private Long timeFrom;
    private Long timeTo;
    private Long roomId;
    private Set<Long> employeeSet;

    public ReminderQueryModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Long timeTo) {
        this.timeTo = timeTo;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Set<Long> getEmployeeSet() {
        if (employeeSet == null || employeeSet.size() ==0){
            return null;
        }
        return employeeSet;
    }

    public void setEmployeeSet(Set<Long> employeeSet) {
        this.employeeSet = employeeSet;
    }
}
