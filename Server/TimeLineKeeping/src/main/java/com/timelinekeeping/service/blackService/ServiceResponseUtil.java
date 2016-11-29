package com.timelinekeeping.service.blackService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by HienTQSE60896 on 11/28/2016.
 */
@Service
@Component
public class ServiceResponseUtil {

    @Autowired
    private ConfigurationResponse configurationResponse;

    public String convertAgePredict(Double ageOfFace) {
        int ageBegin, ageEnd;
        if (ageOfFace < 5) {
            ageBegin = (int) (ageOfFace - 0.5) > 0 ? (int) (ageOfFace - 0.5) : 0;
            ageEnd = (int) Math.round(ageOfFace + 0.5) > 0 ? (int) Math.round(ageOfFace + 0.5) : 0;

        } else if (ageOfFace < 20) {
            ageBegin = (int) (ageOfFace - 0.75) > 0 ? (int) (ageOfFace - 0.75) : 0;
            ageEnd = (int) Math.round(ageOfFace + 0.75) > 0 ? (int) Math.round(ageOfFace + 0.75) : 0;
        } else if (ageOfFace < 50) {
            ageOfFace = Math.abs(appendAge(ageOfFace));
            ageBegin = (int) (ageOfFace - 1) > 0 ? (int) (ageOfFace - 1) : 0;
            ageEnd = (int) Math.round(ageOfFace + 1) > 0 ? (int) Math.round(ageOfFace + 1) : 0;
        } else {
            ageBegin = (int) (ageOfFace - 1.5) > 0 ? (int) (ageOfFace - 1.5) : 0;
            ageEnd = (int) Math.round(ageOfFace + 1.5) > 0 ? (int) Math.round(ageOfFace + 1.5) : 0;
        }
        return String.format("%s - %s", ageBegin, ageEnd);
    }

    private Double appendAge(Double ageOfFace) {
        Double ageConstantA = configurationResponse.getEmotionAgeA();
        Double ageConstantB = configurationResponse.getEmotionAgeB();
//                Double ageConstantA = 0.0251;
//        Double ageConstantB = -14.096;

        if (ageConstantA != null && ageConstantB != null) {
            return ageConstantA * ageOfFace + ageConstantB;
        } else {
            return ageOfFace;
        }
    }
//
//    public static void main(String[] args) {
//        System.out.println(appendAge(32.1));
//    }
}
