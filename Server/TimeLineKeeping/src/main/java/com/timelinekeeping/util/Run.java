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
public class Run {
    public static void main(String[] args) {


        try {
            Date dateBegin = new Date();
            Map<String, String> map = new HashMap<>();
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478317683817.jpg","1478317683817.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478317697703.jpg","1478317697703.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323323927.jpg","1478323323927.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323437938.jpg","1478323437938.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323554758.jpg","1478323554758.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323735042.jpg","1478323735042.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323812379.jpg","1478323812379.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323899888.jpg","1478323899888.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478492850261.jpg","1478492850261.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478492887502.jpg","1478492887502.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478538951382.jpg","1478538951382.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478587251982.jpg","1478587251982.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478590759833.jpg","1478590759833.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478591741320.jpg","1478591741320.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478592374556.jpg","1478592374556.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478675883995.jpg","1478675883995.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478676445335.jpg","1478676445335.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478677429815.jpg","1478677429815.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478677784201.jpg","1478677784201.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478679983487.jpg","1478679983487.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478680055045.jpg","1478680055045.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478680149846.jpg","1478680149846.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478750500477.jpg","1478750500477.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478750583726.jpg","1478750583726.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478846216426.jpg","1478846216426.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478847152803.jpg","1478847152803.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478937577560.jpg","1478937577560.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478939092563.jpg","1478939092563.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478939199662.jpg","1478939199662.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478939315840.jpg","1478939315840.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479042669251.jpg","1479042669251.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479096482604.jpg","1479096482604.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479096588421.jpg","1479096588421.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479116773667.jpg","1479116773667.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479121712775.jpg","1479121712775.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479121733810.jpg","1479121733810.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479121766215.jpg","1479121766215.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479121927366.jpg","1479121927366.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479131973692.jpg","1479131973692.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479182872583.jpg","1479182872583.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478255415074.jpg","1478255415074.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478255415074.jpg","1478255415074.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323899888.jpg","1478323899888.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478317683817.jpg","1478317683817.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478317697703.jpg","1478317697703.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323554758.jpg","1478323554758.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323735042.jpg","1478323735042.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478323812379.jpg","1478323812379.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478587875768.jpg","1478587875768.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478601062699.jpg","1478601062699.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478601200794.jpg","1478601200794.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1478601354472.jpg","1478601354472.jpg");
            map.put("https://s3-ap-southeast-1.amazonaws.com/customersatisfaction/1479058321438.jpg","1479058321438.jpg");



            int i = 0;

            for (Map.Entry<String, String> entry : map.entrySet()) {
                Date dateFrom = new Date();
                try {
                    System.out.print(++i + " : " + entry.getKey());
//                    URL url = new URL(entry.getKey());
//                    BufferedImage image = ImageIO.read(url);
//                    ImageIO.write(image, "jpg", new File("D:\\CP\\K\\" + entry.getValue()));

                    URL url = new URL(entry.getKey());
                    InputStream is = url.openStream();
                    OutputStream os = new FileOutputStream("D:\\CP\\K\\" + entry.getValue());

                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                    is.close();
                    os.close();

//                try (InputStream in = new URL(entry.getKey()).openStream()) {
//                    Files.copy(in, Paths.get("D:\\CP\\K\\" + entry.getValue()), StandardCopyOption.REPLACE_EXISTING);
//                    System.out.print(" Success.");
                } catch (Exception e) {
                    System.out.print(" fail.");
                }

                Date dateTo = new Date();
                System.out.println(" Time:" + (dateTo.getTime() - dateFrom.getTime()));
            }
            Date dateEnd = new Date();
            System.out.println("Time finish:" + (dateEnd.getTime() - dateBegin.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
