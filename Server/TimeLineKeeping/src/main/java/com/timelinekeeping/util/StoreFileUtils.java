package com.timelinekeeping.util;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
public class StoreFileUtils {
    private static String PATH = AppConfigKeys.getInstance().getApiPropertyValue("file.store.path");

    public static String storeFile(String nameFile, InputStream fileStore){
        try {
            String fileName = PATH + File.separator + String.format("%s.%s", nameFile, IContanst.EXTENSION_FILE_IMAGE);
            File file = new File(fileName);
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }else{
                if (file.exists()){
                    file.delete();
                }
            }

            BufferedImage bufferedImage = ImageIO.read(fileStore);
            ImageIO.write(bufferedImage, IContanst.EXTENSION_FILE_IMAGE, file);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
