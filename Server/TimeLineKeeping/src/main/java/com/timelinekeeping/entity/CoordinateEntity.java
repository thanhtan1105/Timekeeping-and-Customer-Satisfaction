package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EBeaconPoint;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by lethanhtan on 10/17/16.
 */

@Entity
@Table(name = "coordinate")
public class CoordinateEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "floor", nullable = false)
    private Integer floor = 0;

    @Basic
    @Column(name = "latitude", nullable = false)
    private Double latitude = 0d;

    @Basic
    @Column(name = "longitude", nullable = false)
    private Double longitude = 0d;

    @Basic
    @Column(name = "minjor")
    private int minjor;

    @Basic
    @Column(name = "major")
    private int major;

    @Basic
    @NotNull
    @Column(name = "type")
    private EBeaconPoint type = EBeaconPoint.CAN_MOVE;

    public CoordinateEntity() {
    }

    public CoordinateEntity(Double latitude, Double longitude, int minjor, int major, String areaName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.minjor = minjor;
        this.major = major;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public EBeaconPoint getType() {
        return type;
    }

    public void setType(EBeaconPoint type) {
        this.type = type;
    }
}
