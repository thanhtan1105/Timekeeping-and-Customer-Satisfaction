package com.timelinekeeping.service;

import com.timelinekeeping.model.BaseResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public interface FaceServiceMCS {

    public BaseResponse detect(InputStream imgStream) throws URISyntaxException, IOException;

    public BaseResponse detect(String url) throws URISyntaxException, IOException;

    public BaseResponse identify(String groupId, List<String> faceIds) throws URISyntaxException, IOException;
}
