package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class EmotionCustomerResponse {
    private String customerCode;
    private EmotionAnalysisModel analyzes;
    private MessageModel messages;
    private boolean isFinal;

    public EmotionCustomerResponse() {
    }

    public EmotionCustomerResponse(String customerCode, EmotionAnalysisModel analyzes, MessageModel messages) {
        this.customerCode = customerCode;
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

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }
}
