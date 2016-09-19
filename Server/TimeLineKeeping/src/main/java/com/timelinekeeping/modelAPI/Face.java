package com.timelinekeeping.modelAPI;

/**
 * Created by lethanhtan on 9/19/16.
 */
public class Face {

    private String persistedFaceId;

    public Face(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }
}
