package com.timelinekeeping.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by lethanhtan on 10/17/16.
 */

@Entity
@Table(name = "beacon", schema = "mydb")
public class BeaconEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Basic
    @NotNull
    @Column(name = "latitude")
    private Double latitude;

    @Basic
    @NotNull
    @Column(name = "longitude")
    private Double longitude;

    @Basic
    @NotNull
    @Column(name = "minjor")
    private int minjor;

    @Basic
    @NotNull
    @Column(name = "major")
    private int major;

    public BeaconEntity() { }

    public BeaconEntity(Double latitude, Double longitude, int minjor, int major) {
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
}
