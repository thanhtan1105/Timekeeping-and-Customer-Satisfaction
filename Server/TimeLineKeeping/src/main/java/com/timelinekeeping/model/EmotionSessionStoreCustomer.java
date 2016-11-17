package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 10/31/2016.
 */
public class EmotionSessionStoreCustomer {

    private String customerCode;
    private Long emotionCamera1;
    private Long emotionCamera2;
    private String pathImage;
    private String uriImage;
    private Boolean isFinal = false;

    public EmotionSessionStoreCustomer(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Long getEmotionCamera1() {
        return emotionCamera1;
    }

    public void setEmotionCamera1(Long emotionCamera1) {
        if (this.emotionCamera1 == null) {
            this.emotionCamera1 = emotionCamera1;
        } else {
            this.isFinal = true;
        }
    }

    public Long getEmotionCamera2() {
        return emotionCamera2;
    }

    public void setEmotionCamera2(Long emotionCamera2) {
        if (this.emotionCamera2 == null) {
            this.emotionCamera2 = emotionCamera2;
        } else {
            this.isFinal = true;
        }

    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uriImage) {
        this.uriImage = uriImage;
    }

    public Boolean getFinal() {
        if (!isFinal) {
            if (this.emotionCamera1 != null && this.emotionCamera2 != null) {
                isFinal = true;
            }
        }
        return isFinal;

    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
}
