package com.timelinekeeping.common;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public class BaseResponseG<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;

    public BaseResponseG() {
    }

    public BaseResponseG(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public BaseResponseG(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResponseG(boolean success) {
        this.success = success;
    }

    public BaseResponseG(Exception e) {
        this.success = false;
        this.message = e.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                '}';
    }

    public BaseResponse toBaseResponse(){
        return new BaseResponse(success, message, data, errorCode);
    }
}
