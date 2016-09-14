package com.timelinekeeping.modelAPI;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class FaceDetectAttribute {
    private String gender;
    private Double age;
    private Double smile;
    private String glasses;


    public FaceDetectAttribute() {
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Double getSmile() {
        return smile;
    }

    public void setSmile(Double smile) {
        this.smile = smile;
    }

    public String getGlasses() {
        return glasses;
    }

    public void setGlasses(String glasses) {
        this.glasses = glasses;
    }

    @Override
    public String toString() {
        return "FaceDetectAttribute{" +
                "gender='" + gender + '\'' +
                ", age=" + age +
                ", smile=" + smile +
                ", glasses='" + glasses + '\'' +
                '}';
    }
}
