package com.timelinekeeping.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lethanhtan on 9/19/16.
 */

@Entity
@Table(name = "face", schema = "mydb")
public class FaceEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "persisted_face_id", nullable = false, unique = true)
    private String persistedFaceId;


    @Basic
    @Column(name = "store_path", length = 300)
    private String stoePath;

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

    public String getStoePath() {
        return stoePath;
    }

    public void setStoePath(String stoePath) {
        this.stoePath = stoePath;
    }
}

