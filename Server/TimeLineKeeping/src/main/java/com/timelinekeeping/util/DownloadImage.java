package com.timelinekeeping.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 11/16/2016.
 */
public class DownloadImage {
//    public static void main(String[] args) {
//
//
//        try {
//            Date dateBegin = new Date();
//            Map<String, String> map = new HashMap<>();
//            map.put(" https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479131804141.jpg","1479131804141.jpg");
//            map.put(" https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479132092000.jpg","1479132092000.jpg");
//            map.put(" https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479183225210.jpg","1479183225210.jpg");
//            map.put(" https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479183246800.jpg","1479183246800.jpg");
//            map.put(" https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479233022808.jpg","1479233022808.jpg");
//
//            int i = 0;
//
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                Date dateFrom = new Date();
//                try {
//                    System.out.print(++i + " : " + entry.getKey());
////                    URL url = new URL(entry.getKey());
////                    BufferedImage image = ImageIO.read(url);
////                    ImageIO.write(image, "jpg", new File("D:\\CP\\K\\" + entry.getValue()));
//
//                    URL url = new URL(entry.getKey());
//                    InputStream is = url.openStream();
//                    OutputStream os = new FileOutputStream("D:\\CP\\K\\" + entry.getValue());
//
//                    byte[] b = new byte[2048];
//                    int length;
//
//                    while ((length = is.read(b)) != -1) {
//                        os.write(b, 0, length);
//                    }
//                    is.close();
//                    os.close();
//
////                try (InputStream in = new URL(entry.getKey()).openStream()) {
////                    Files.copy(in, Paths.get("D:\\CP\\K\\" + entry.getValue()), StandardCopyOption.REPLACE_EXISTING);
////                    System.out.print(" Success.");
//                } catch (Exception e) {
//                    System.out.print(" fail.");
//                }
//
//                Date dateTo = new Date();
//                System.out.println(" Time:" + (dateTo.getTime() - dateFrom.getTime()));
//            }
//            Date dateEnd = new Date();
//            System.out.println("Time finish:" + (dateEnd.getTime() - dateBegin.getTime()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
