package com.timelinekeeping.util;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

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
}
