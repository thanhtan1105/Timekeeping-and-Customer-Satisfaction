package com.timelinekeeping.model;

import com.timelinekeeping.constant.EBeaconPoint;
import com.timelinekeeping.entity.CoordinateEntity;

/**
 * Created by HienTQSE60896 on 10/21/2016.
 */
public class CoordinateModel {

    private Long id;

    private String name;

    private Integer floor = 0;

    private Double latitude = 0d;

    private Double longitude = 0d;

    private int minjor;

    private int major;

    private EBeaconPoint type;

    public CoordinateModel() {
    }

    public CoordinateModel(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public CoordinateModel(CoordinateEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.floor = entity.getFloor();
            this.latitude = entity.getLatitude();
            this.longitude = entity.getLongitude();
            this.minjor = entity.getMinjor();
            this.major = entity.getMajor();
            this.type = entity.getType();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getMinjor() {
        return minjor;
    }

    public void setMinjor(int minjor) {
        this.minjor = minjor;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public EBeaconPoint getType() {
        return type;
    }

    public void setType(EBeaconPoint type) {
        this.type = type;
    }
}
