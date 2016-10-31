package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class EmotionCustomerResponse {
    private String customerCode;
    private EmotionAnalysisModel analyzes;
    private MessageModel messages;
    private Boolean isFinal = false;

    public EmotionCustomerResponse() {
    }

//    public EmotionCustomerResponse(String customerCode, EmotionAnalysisModel analyzes, MessageModel messages) {
//        this.customerCode = customerCode;
//        this.analyzes = analyzes;
//        this.messages = messages;
//    }


    public EmotionCustomerResponse(EmotionAnalysisModel analyzes, MessageModel messages) {
        this.analyzes = analyzes;
        this.messages = messages;
    }

    public EmotionCustomerResponse(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public EmotionAnalysisModel getAnalyzes() {
        return analyzes;
    }

    public void setAnalyzes(EmotionAnalysisModel analyzes) {
        this.analyzes = analyzes;
    }

    public MessageModel getMessages() {
        return messages;
    }

    public void setMessages(MessageModel messages) {
        this.messages = messages;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
>>>>>>> 22932d2c64e5ed7e47bf8dd1509ec8a88f389d07
=======
>>>>>>> origin/w9-hien-api-2-camera
        isFinal = aFinal;
    }
}
