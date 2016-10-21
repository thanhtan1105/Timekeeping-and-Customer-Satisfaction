package com.timelinekeeping.model;

import java.util.List;

/**
 * Created by HienTQSE60896 on 10/22/2016.
 */
public class BeaconFindPathResponse {
    private CoordinateModel fromPoint;
    private CoordinateModel toPoint;
    private Double distance;
    private List<CoordinateModel> path;
    private List<Long> pathId;

    public BeaconFindPathResponse() {
    }

    public CoordinateModel getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(CoordinateModel fromPoint) {
        this.fromPoint = fromPoint;
    }

    public CoordinateModel getToPoint() {
        return toPoint;
    }

    public void setToPoint(CoordinateModel toPoint) {
        this.toPoint = toPoint;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public List<CoordinateModel> getPath() {
        return path;
    }

    public void setPath(List<CoordinateModel> path) {
        this.path = path;
    }

    public List<Long> getPathId() {
        return pathId;
    }

    public void setPathId(List<Long> pathId) {
        this.pathId = pathId;
    }
}
