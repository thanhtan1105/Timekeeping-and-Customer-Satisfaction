package com.timelinekeeping.util;

import java.io.Serializable;

/**
 * Created by TrungNN on 9/18/2016.
 */
public class ValidateUtil implements Serializable {

    public static Long validateNumber(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
