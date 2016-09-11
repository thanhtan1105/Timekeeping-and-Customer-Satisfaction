package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/11/2016.
 */
public class FaceIdentityCandidate {

    private String personId;
    private Double confidence;

    public FaceIdentityCandidate() {
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "FaceIdentityCandidate{" +
                "personId='" + personId + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
