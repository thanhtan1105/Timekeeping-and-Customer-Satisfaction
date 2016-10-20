package com.timelinekeeping.entity;

import javax.persistence.*;

/**
 * Created by HienTQSE60896 on 10/20/2016.
 */
@Entity
@Table(name = "connection_point", schema = "mydb")
public class ConnectionPointEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vertex1_id", nullable = false)
    private CoordinateEntity vertex1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vertex2_id", nullable = false)
    private CoordinateEntity vertex2;

    public ConnectionPointEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoordinateEntity getVertex1() {
        return vertex1;
    }

    public void setVertex1(CoordinateEntity vertex1) {
        this.vertex1 = vertex1;
    }

    public CoordinateEntity getVertex2() {
        return vertex2;
    }

    public void setVertex2(CoordinateEntity vertex2) {
        this.vertex2 = vertex2;
    }
}
