package com.timelinekeeping.entity;

import com.timelinekeeping.constant.ETransaction;
import com.timelinekeeping.util.SessionGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
@Entity
@Table(name = "customer_service", schema = "mydb")
public class CustomerServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Timestamp createTime = new Timestamp(new Date().getTime());

    @Basic
    @Column(name = "customer_code", nullable = false)
    private String CustomerCode;

    @Basic
    @Column(name = "grade")
    private Double grade = 0d;

    @Basic
    @Column(name = "status")
    private ETransaction status = ETransaction.BEGIN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by")
    private AccountEntity createBy;


    @OneToMany(mappedBy = "customerService", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<EmotionCustomerEntity> emotion;

    public CustomerServiceEntity() {
        this.CustomerCode = SessionGenerator.nextSession();
    }

    public CustomerServiceEntity(AccountEntity createBy) {
        this.CustomerCode = SessionGenerator.nextSession();
        this.createBy = createBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double point) {
        this.grade = point;
    }

    public AccountEntity getCreateBy() {
        return createBy;
    }

    public void setCreateBy(AccountEntity createBy) {
        this.createBy = createBy;
    }

    public Set<EmotionCustomerEntity> getEmotion() {
        return emotion;
    }

    public void setEmotion(Set<EmotionCustomerEntity> emotion) {
        this.emotion = emotion;
    }

    public ETransaction getStatus() {
        return status;
    }

    public void setStatus(ETransaction status) {
        this.status = status;
    }

    public void calculateGrade() {
        double sum = 0d;
        for (EmotionCustomerEntity emotion : this.emotion) {
            sum += emotion.getEmotionMost().getGrade();
        }
        this.grade = sum / emotion.size();

    }
}
