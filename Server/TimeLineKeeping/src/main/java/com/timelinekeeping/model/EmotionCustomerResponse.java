package com.timelinekeeping.model;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class EmotionCustomerResponse {
    private String customerCode;
    private List<EmotionAnalysisModel> analyzes;
    private List<MessageModel> messages;

    public EmotionCustomerResponse() {
    }

    public EmotionCustomerResponse(String customerCode, List<EmotionAnalysisModel> analyzes, List<MessageModel> messages) {
        this.customerCode = customerCode;
        this.analyzes = analyzes;
        this.messages = messages;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public List<EmotionAnalysisModel> getAnalyzes() {
        return analyzes;
    }

    public void setAnalyzes(List<EmotionAnalysisModel> analyzes) {
        this.analyzes = analyzes;
    }

    public List<MessageModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageModel> messages) {
        this.messages = messages;
    }
}
