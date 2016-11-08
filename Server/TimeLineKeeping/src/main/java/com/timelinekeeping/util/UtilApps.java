package com.timelinekeeping.util;

import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.model.AccountNotificationModel;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class UtilApps {


    public static String trim(String text) {
        return ValidateUtil.isEmpty(text) ? null : text.trim();
    }


    public static InputStream[] muitleStream(InputStream stream, int size) {
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

    public static AccountNotificationModel getAccountFromNotify(NotificationEntity notificationEntity) {
        return new AccountNotificationModel(notificationEntity.getAccountReceive());
    }

    public static String generatePassword() {
        String password = "";
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int p = 97 + r.nextInt(122 - 97);
            password += r.nextInt(100) + "" + ((char) p);
        }
        return password;
    }

    public static String generateToken() {
        String token = "";
        Random r = new Random();
        token = Math.abs(r.nextLong()) + "";
        return token;
    }

    public static Map<Long, Object[]> converListObject2Map(List<Object[]> objs) {
        Map<Long, Object[]> map = new HashMap<>();
        for (Object[] obj : objs) {
            map.put(((Number) obj[0]).longValue(), obj);
        }
        return map;
    }


    public static int random(int min, int max) {
        return Math.abs(new Random().nextInt()) % (max - min + 1) + min;
    }

    public static String formatSentence(String sentence) {
        if (ValidateUtil.isEmpty(sentence)) {
            return "";
        }
        sentence.replaceAll("\\s+", "");
        sentence = sentence.trim();
        sentence = sentence.substring(0, 1).toUpperCase() + sentence.substring(1);
        if (sentence.charAt(sentence.length() - 1) != '.') {
            sentence += '.';
        }
        return sentence;
    }

    public static String parseNoneAccent(String value) {
        String text = Normalizer.normalize(value, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(text).replaceAll("");
    }

    public static String[] parseSearchValue(String searchValue) {
        String regular = "[-]";
        String[] groups = searchValue.split(regular);
        return groups;
    }

    public static void rotateImage(String url) {
        File file = new File(url);
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            double rotationRequired = Math.toRadians(90);
            double locationX = bufferedImage.getHeight() / 2;
            double locationY = bufferedImage.getWidth() / 2;
            AffineTransform transform = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);

            AffineTransformOp affineTransformOp = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            int maximun = (int) (Math.max(bufferedImage.getHeight(), bufferedImage.getWidth()) * 1.20);
            BufferedImage newImage = new BufferedImage(maximun, maximun, bufferedImage.getType());
            affineTransformOp.filter(bufferedImage, newImage);
//            g2d.drawImage(op.filter(image, null), drawLocationX, drawLocationY, null);


            String fileoutName = file.getParent() + "\\" + "147849309073-1.jpg";
            File fileOut = new File(fileoutName);
            file.mkdirs();
            ImageIO.write(newImage, "jpg", fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        rotateImage("D:\\CP\\FILE\\1478493090734.jpg");
    }

}
