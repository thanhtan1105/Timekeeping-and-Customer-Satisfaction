package com.timelinekeeping.util;

import java.io.Serializable;

/**
 * Created by TrungNN on 10/24/2016.
 */
public class CommonUtil implements Serializable {

    public static String[] parseSearchValue(String searchValue) {
        String regular = "[-]";
        String[] groups = searchValue.split(regular);
        return groups;
    }
}
