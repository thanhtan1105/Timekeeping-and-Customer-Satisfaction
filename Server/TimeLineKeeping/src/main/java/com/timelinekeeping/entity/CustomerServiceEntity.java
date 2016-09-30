package com.timelinekeeping.entity;

import javax.persistence.*;
import java.sql.Timestamp;
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
    private Timestamp createTime;

    @Basic
    @Column(name = "customer_code", nullable = false)
    private String CustomerCode;

    @Basic
    @Column(name = "point")
    private Double point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by", nullable = false)
    private AccountEntity createBy;

    @OneToMany(mappedBy = "customerService", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<EmotionCustomerEntity> emotion;

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

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
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
}
