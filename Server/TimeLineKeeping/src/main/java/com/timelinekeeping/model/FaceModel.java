package com.timelinekeeping.model;

/**
 * Created by lethanhtan on 9/19/16.
 */
public class FaceModel {

    private String persistedFaceId;

    public FaceModel(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }
}
