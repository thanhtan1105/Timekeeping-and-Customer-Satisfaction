package com.timelinekeeping.util;

import antlr.collections.List;
import com.timelinekeeping.constant.ETimeKeeping;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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

    public static InputStream[] muitleStream(InputStream stream, int size){
        InputStream[] streamResult = new InputStream[size];
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            for (int i = 0; i < size; i++) {
                streamResult[i] = new ByteArrayInputStream(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return streamResult;

    }
}
