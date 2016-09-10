package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class FaceDetectRespone {
    private String faceId;
    private FaceDetectRectangle faceRectangle;
    private FaceDetectAttribute faceAttributes;

    public FaceDetectRespone() {
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public FaceDetectRectangle getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceDetectRectangle faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public FaceDetectAttribute getFaceAttributes() {
        return faceAttributes;
    }

    public void setFaceAttributes(FaceDetectAttribute faceAttributes) {
        this.faceAttributes = faceAttributes;
    }

    @Override
    public String toString() {
        return "FaceDetectRespone{" +
                "faceId='" + faceId + '\'' +
                ", faceRectangle=" + faceRectangle +
                ", faceAttributes=" + faceAttributes +
                '}';
    }
}
