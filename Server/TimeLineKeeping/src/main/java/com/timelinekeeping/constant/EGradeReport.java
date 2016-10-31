package com.timelinekeeping.constant;

/**
 * Created by TrungNN on 10/6/2016.
 */
public enum EGradeReport {

    EXCELLENT(0, "RẤT TỐT", 9d, 10d),
    GOOD(1, "TỐT", 8d, 9d),
    NORMAL(2, "BÌNH THƯỜNG", 5d, 8d),
    MEDIUM(3, "TRUNG BÌNH KHÁ", 2.5d, 5d),
    BAD(4, "CHƯA TỐT", 0d, 2.5d);

    private int index;
    private String name;
    private double from;
    private double to;

    EGradeReport(int index, String name, double from, double to) {
        this.index = index;
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public static EGradeReport fromIndex(int index) {
        for (EGradeReport eg : values()) {
            if (eg.getIndex() == index) {
                return eg;
            }
        }
        return null;
    }

    public static EGradeReport fromGrade(double grade) {
        for (EGradeReport eg : values()) {
            if (grade == 10d) {
                return EXCELLENT;
            }
            if (grade >= eg.getFrom() && grade < eg.getTo()) {
                return eg;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

//    public static void main(String[] args) {
//        System.out.println(EGradeReport.fromGrade(9).getName());
//    }
}
