package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/19/2016.
 */
public class FaceCreateModel {
    private String persistedFaceId;
    private Long accountId;
    private String accountCode;

    public FaceCreateModel() {
    }

    public FaceCreateModel(String persistedFaceId, Long accountId) {
        this.persistedFaceId = persistedFaceId;
        this.accountId = accountId;
    }

    public FaceCreateModel(String persistedFaceId, String accountCode) {
        this.persistedFaceId = persistedFaceId;
        this.accountCode = accountCode;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    @Override
    public String toString() {
        return "FaceCreateModel{" +
                "persistedFaceId='" + persistedFaceId + '\'' +
                ", accountId='" + accountId + '\'' +
                ", accountCode='" + accountCode + '\'' +
                '}';
    }
}
