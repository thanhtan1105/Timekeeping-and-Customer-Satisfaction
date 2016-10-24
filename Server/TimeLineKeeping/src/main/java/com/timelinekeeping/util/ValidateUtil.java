package com.timelinekeeping.util;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by TrungNN on 9/18/2016.
 */
public class ValidateUtil implements Serializable {

    public static Long parseNumber(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static boolean isImageFile(InputStream data) {
        try {
            ImageIO.read(data).toString();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() <= 0;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() <= 0;
    }

    public static boolean isNotEmpty(String text) {
        return text != null && text.length() > 0;
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isResultAutocomplete(String text) {
        String pattern = "^(.*)-(.*)$";
        return text.matches(pattern);
    }

    public static void main(String[] args) {
        String text = "Đào Tạo - dt";
        if (isResultAutocomplete(text)) {
            System.out.println("Matching");
            String[] groups = CommonUtil.parseSearchValue(text);
            System.out.println("Length: " + groups.length);
            for (String group : groups) {
                System.out.println("-" + group.trim() + ".");
            }
        } else {
            System.out.println("Not matching");
        }
    }
}
