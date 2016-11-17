package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lethanhtan on 9/19/16.
 */

@Entity
@Table(name = "face")
public class FaceEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "persisted_face_id", length = 100, nullable = false, unique = true)
    private String persistedFaceId;


    @Basic
    @Column(name = "store_path", length = 500)
    private String storePath;


    @Basic
    @Column(name = "active")
    private EStatus active = EStatus.ACTIVE;

    @Basic
    @Column(name = "create_time")
    private Timestamp create_time = new Timestamp(new Date().getTime());

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;

    public FaceEntity() {
    }



    public FaceEntity(String persistedFaceId, AccountEntity accountEntity) {
        this.persistedFaceId = persistedFaceId;
        this.accountEntity = accountEntity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }
}

