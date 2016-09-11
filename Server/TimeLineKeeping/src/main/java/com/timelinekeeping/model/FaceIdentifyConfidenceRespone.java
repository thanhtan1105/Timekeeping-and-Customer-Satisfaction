package com.timelinekeeping.model;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/11/2016.
 */
public class FaceIdentifyConfidenceRespone {
    public String faceId;
    public List<FaceIdentityCandidate> candidates;

    public FaceIdentifyConfidenceRespone() {
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public List<FaceIdentityCandidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<FaceIdentityCandidate> candidates) {
        this.candidates = candidates;
    }

    @Override
    public String toString() {
        return "FaceIdentifyConfidenceRespone{" +
                "faceId='" + faceId + '\'' +
                ", candidates=" + candidates +
                '}';
    }
}
