package com.timelinekeeping.util;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.model.AccountNotificationModel;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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


    public static String correctUrl(String url){
        return url.replace("\\", "/");
    }

    public static void main(String[] args) {
        System.out.println(correctUrl("http://192.168.1.43:8080/file\\Timekeeping\\5_khaothitest6\\8_tanlt1\\Train/20161129011840.jpg"));
    }



}
