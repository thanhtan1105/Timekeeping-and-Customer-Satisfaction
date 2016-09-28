package com.timelinekeeping.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by HienTQSE60896 on 9/28/2016.
 */
public class MergeResult {
    private static final String FILE_SURVEY = "";
    private void readSurvey() throws FileNotFoundException {
        FileReader reader = new FileReader(FILE_SURVEY);
        BufferedReader bf = new BufferedReader(reader);
    }
}
