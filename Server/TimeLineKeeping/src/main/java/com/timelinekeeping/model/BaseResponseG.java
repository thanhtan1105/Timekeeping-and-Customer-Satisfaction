package com.timelinekeeping.model;

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
}
