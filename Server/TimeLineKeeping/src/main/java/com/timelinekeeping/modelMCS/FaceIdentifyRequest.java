package com.timelinekeeping.modelMCS;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/11/2016.
 */
public class FaceIdentifyRequest {

    private String personGroupId;
    private List<String> faceIds;
    private Integer maxNumOfCandidatesReturned = 1;
    private Double confidenceThreshold = 0.5d;

    public FaceIdentifyRequest() {
    }

    public FaceIdentifyRequest(String personGroupId, List<String> faceIds) {
        this.personGroupId = personGroupId;
        this.faceIds = faceIds;
    }

    public String getPersonGroupId() {
        return personGroupId;
    }

    public void setPersonGroupId(String personGroupId) {
        this.personGroupId = personGroupId;
    }

    public List<String> getFaceIds() {
        return faceIds;
    }

    public void setFaceIds(List<String> faceIds) {
        this.faceIds = faceIds;
    }

    public Integer getMaxNumOfCandidatesReturned() {
        return maxNumOfCandidatesReturned;
    }

    public void setMaxNumOfCandidatesReturned(Integer maxNumOfCandidatesReturned) {
        this.maxNumOfCandidatesReturned = maxNumOfCandidatesReturned;
    }

    public Double getConfidenceThreshold() {
        return confidenceThreshold;
    }

    public void setConfidenceThreshold(Double confidenceThreshold) {
        this.confidenceThreshold = confidenceThreshold;
    }

    @Override
    public String toString() {
        return "FaceIdentifyRequest{" +
                "personGroupId='" + personGroupId + '\'' +
                ", faceIds=" + faceIds +
                ", maxNumOfCandidatesReturned=" + maxNumOfCandidatesReturned +
                ", confidenceThreshold=" + confidenceThreshold +
                '}';
    }
}
