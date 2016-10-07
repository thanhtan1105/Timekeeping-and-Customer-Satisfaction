package com.timelinekeeping.entity;

import javax.persistence.*;

/**
 * Created by HienTQSE60896 on 10/7/2016.
 */
@Entity
@Table(name = "quantity_emotion", schema = "mydb")
public class QuantityEmotionEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "from_value")
    private Double fromValue;

    @Column(name = "to_value")
    private Double toValue;

    public QuantityEmotionEnity() {
    }

    public QuantityEmotionEnity(String name, Double fromValue, Double toValue) {
        this.name = name;
        this.fromValue = fromValue;
        this.toValue = toValue;
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

    public Double getFromValue() {
        return fromValue;
    }

    public void setFromValue(Double fromValue) {
        this.fromValue = fromValue;
    }

    public Double getToValue() {
        return toValue;
    }

    public void setToValue(Double toValue) {
        this.toValue = toValue;
    }
}
