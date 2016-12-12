package com.timelinekeeping.model;

import com.timelinekeeping.entity.FaceEntity;
import com.timelinekeeping.util.UtilApps;

/**
 * Created by lethanhtan on 9/19/16.
 */
public class FaceModel {
    private Long id;
    private String persistedFaceId;
    private String storePath;

    public FaceModel() {
    }

    public FaceModel(FaceEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.persistedFaceId = entity.getPersistedFaceId();
            this.storePath = entity.getStorePath();
        }
    }

    public FaceModel(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public String getPersistedFaceId() {
        return persistedFaceId;
    }

    public void setPersistedFaceId(String persistedFaceId) {
        this.persistedFaceId = persistedFaceId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStorePath() {
        return UtilApps.correctUrl(storePath);
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }
}
