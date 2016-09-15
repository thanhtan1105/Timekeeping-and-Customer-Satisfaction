package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.entity.Emotion;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.repository.EmotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * Created by lethanhtan on 9/15/16.
 */
@Service
public class EmotionServiceImpl {

    @Autowired
    private EmotionRepo repo;

    public BaseResponse save(InputStream inputStreamImg) throws IOException, URISyntaxException {
        BaseResponse baseResponse = new BaseResponse();
        EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
        BaseResponse response = emotionServiceMCS.recognize(inputStreamImg);
        System.out.println(response);
        return baseResponse;
    }


}
