package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 11/15/2016.
 */


public class ReportCustomerEmotionQuery {
    private Long id;
    private Long count;
    private Double grade;

    public ReportCustomerEmotionQuery() {
    }

    public ReportCustomerEmotionQuery(Long id, Long count, Object grade) {
        this.id = id;
        this.count = count;
        this.grade = (Double) grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
