package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 10/31/2016.
 */
public class EmotionSessionStoreCustomer {

    private String customerCode;
    private Long EmotionCamera1;
    private Long EmotionCamera2;
    private String urlImage;

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
        return EmotionCamera1;
    }

    public void setEmotionCamera1(Long emotionCamera1) {
        EmotionCamera1 = emotionCamera1;
    }

    public Long getEmotionCamera2() {
        return EmotionCamera2;
    }

    public void setEmotionCamera2(Long emotionCamera2) {
        EmotionCamera2 = emotionCamera2;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
