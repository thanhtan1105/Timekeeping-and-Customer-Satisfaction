package com.timelinekeeping.modelAPI;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class FaceDetectResponse {
    private String faceId;
    private RectangleImage faceRectangle;
    private FaceDetectAttribute faceAttributes;

    public FaceDetectResponse() {
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public RectangleImage getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(RectangleImage faceRectangle) {
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
        return "FaceDetectResponse{" +
                "faceId='" + faceId + '\'' +
                ", faceRectangle=" + faceRectangle +
                ", faceAttributes=" + faceAttributes +
                '}';
    }
}
