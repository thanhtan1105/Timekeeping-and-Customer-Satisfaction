package com.timelinekeeping.service;

import com.timelinekeeping.model.BaseResponse;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 9/9/2016.
 */
public interface PersonGroupsService {

    public BaseResponse create(String groupId, String groupName, String groupData) throws URISyntaxException, IOException;

    public BaseResponse listAll(int start, int top) throws URISyntaxException, IOException;

    public BaseResponse trainGroup(String personGroupId) throws URISyntaxException, IOException;

    public BaseResponse trainPersonGroupStatus(String personGroupId) throws URISyntaxException, IOException;
}
