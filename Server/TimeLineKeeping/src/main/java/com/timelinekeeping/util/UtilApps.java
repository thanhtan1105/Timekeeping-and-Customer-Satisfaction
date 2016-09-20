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

    public static boolean isImageFile(InputStream data){
        try {
            ImageIO.read(data).toString();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isEmpty(Collection collection){
        return collection != null && collection.size() >0;
    }

    public static boolean isEmpty(String text){
        return text == null || text.length() <=0;
    }
    public static boolean isNotEmpty(String text){
        return text != null && text.length() >0;
    }
    public static String trim(String text){
        return isEmpty(text)? null : text.trim();
    }

    //TODO condition check Account is Late and ontime
    public static ETimeKeeping checkStatusTimeKeeping() {
        return ETimeKeeping.ON_TIME;
    }
}
