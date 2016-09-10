package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/9/2016.
 */
public class ResponseErrorWrap {
    private ReponseError error;

    public ResponseErrorWrap() {
    }

    public ReponseError getError() {
        return error;
    }

    public void setError(ReponseError error) {
        this.error = error;
    }
}
