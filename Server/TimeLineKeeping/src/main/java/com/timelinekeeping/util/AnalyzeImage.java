package com.timelinekeeping.util;

import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/26/2016.
 */
public class AnalyzeImage {

    private static final String fileResult = "D:\\fileResult05.csv";
    private static final String folder = "D:\\Hinh\\5";

    public static void writeFile(Map<String, EmotionAnalysisModel> mapData) throws IOException {
        FileWriter fw = new FileWriter(fileResult);
        BufferedWriter bf = new BufferedWriter(fw);
        bf.write("Code,NameFile,Age,Gender,EmotionMost,Anger,Contempt,Disgust,Fear,Happiness,Neutral,Sadness,Surprise");
        bf.newLine();
        for (Map.Entry<String, EmotionAnalysisModel> map : mapData.entrySet()) {
            String nameFile = map.getKey();
            String code = nameFile.trim().split("[._]+")[2];
            EmotionAnalysisModel emotionAnalysisModel = map.getValue();
            bf.write(code + ",");
            bf.write(nameFile + ",");
            if (emotionAnalysisModel != null) {
                bf.write(emotionAnalysisModel.getAge() + ",");
                bf.write(emotionAnalysisModel.getGender() + ",");
                bf.write(emotionAnalysisModel.getEmotionMost() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getAnger() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getContempt() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getDisgust() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getFear() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getHappiness() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getNeutral() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getSadness() + ",");
                bf.write(emotionAnalysisModel.getEmotion().getSurprise() + "");
            }
            bf.newLine();
        }
        bf.close();
        fw.close();
    }


    public static void main(String[] args) {

        try {
            File fileRoot = new File(folder);
            File[] imageList = fileRoot.listFiles();
            Map<String, EmotionAnalysisModel> mapResult = new HashMap<>();
            int i = 1;
            for (File imageFile : imageList) {
                try {
                    if (ValidateUtil.isImageFile(new FileInputStream(imageFile))) {
                        EmotionServiceImpl emotionService = new EmotionServiceImpl();
                        BaseResponse response = emotionService.getCustomerEmotion(new FileInputStream(imageFile), null, false);
                        if (response.isSuccess()) {
                            List<EmotionAnalysisModel> listAnalyze = (List<EmotionAnalysisModel>) response.getData();
                            for (EmotionAnalysisModel analysisModel : listAnalyze) {
                                mapResult.put(imageFile.getName(), analysisModel);
                            }
                        }
                    }
                } catch (Exception e) {
                    mapResult.put(imageFile.getName(), null);
                }
            }

            writeFile(mapResult);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
