package com.timelinekeeping.service;

import com.timelinekeeping.model.BaseResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public interface EmotionServiceMCS {

    public BaseResponse recognize(String url) throws URISyntaxException, IOException;

    public BaseResponse recognize(InputStream inputStreamImg) throws URISyntaxException, IOException;

}
