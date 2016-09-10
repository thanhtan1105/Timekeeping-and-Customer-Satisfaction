package com.timelinekeeping.service;

import com.timelinekeeping.model.BaseResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public interface FaceServiceMCS {

    public BaseResponse detech(InputStream imgStream) throws URISyntaxException, IOException;
}
