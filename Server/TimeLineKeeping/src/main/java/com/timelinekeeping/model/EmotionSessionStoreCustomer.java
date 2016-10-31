package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 10/31/2016.
 */
public class EmotionSessionStoreCustomer {

    private String customerCode;
    private Long emotionCamera1;
    private Long emotionCamera2;
    private String urlImage;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
}
