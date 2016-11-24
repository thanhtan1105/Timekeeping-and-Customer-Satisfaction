package com.timelinekeeping.model;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class EmotionCustomerResponse {
    private String customerCode;

    //emotion analysis model
    private EmotionAnalysisModel analyzes;

    // message model
    private MessageModel messages;

    // emotion percent
    private List<EmotionCompare> emotionPercent;

    private CustomerModel customerInformation;

    private List<String> customerHistory;

    // link aws url
    private String awsUrl;
    // check final
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
        isFinal = aFinal;
    }

    public String getAwsUrl() {
        return awsUrl;
    }

    public void setAwsUrl(String awsUrl) {
        this.awsUrl = awsUrl;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

    public List<EmotionCompare> getEmotionPercent() {
        return emotionPercent;
    }

    public void setEmotionPercent(List<EmotionCompare> emotionPercent) {
        this.emotionPercent = emotionPercent;
    }

    public CustomerModel getCustomerInformation() {
        return completeAnalysisCustomer();
    }

    public void setCustomerInformation(CustomerModel customerInformation) {
        this.customerInformation = customerInformation;
    }

    /** if customer is null, convert information detect to  customer infor*/
    public CustomerModel completeAnalysisCustomer(){
        if (customerInformation == null && analyzes != null){
            CustomerModel customerModel = new CustomerModel();
            customerModel.setGender(analyzes.getGender());
            DateTime dateTime = new DateTime(new Date());
            Integer year = dateTime.plusYears(analyzes.getAge().intValue() * -1).getYear();
            customerModel.setYearBirth(year);
            return customerModel;
        }else {
            return customerInformation;
        }
    }

    public List<String> getCustomerHistory() {
        return customerHistory;
    }

    public void setCustomerHistory(List<String> customerHistory) {
        this.customerHistory = customerHistory;
    }
}
