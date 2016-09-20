package com.timelinekeeping.util;

import com.timelinekeeping.constant.ETimeKeeping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class UtilApps {


    public static String trim(String text){
        return ValidateUtil.isEmpty(text)? null : text.trim();
    }

    //TODO condition check Account is Late and ontime
    public static ETimeKeeping checkStatusTimeKeeping() {
        return ETimeKeeping.ON_TIME;
    }
}
