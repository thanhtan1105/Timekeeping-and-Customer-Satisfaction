package com.timelinekeeping.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 10/22/2016.
 */
public class BeaconFindPathResponse {
    private CoordinateModel fromPoint;
    private CoordinateModel toPoint;
    private Double distance;
    private List<List<CoordinateModel>> paths;
    private List<List<Long>> pathIds;

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

    public List<List<CoordinateModel>> getPaths() {
        return paths;
    }


    public List<List<Long>> getPathIds() {
        return pathIds;
    }

    public void addPath(List<CoordinateModel> listPath){
        if (paths == null){
            paths = new ArrayList<>();
        }
        if (pathIds == null){
            pathIds = new ArrayList<>();
        }
        List<Long> pathId = listPath.stream().map(CoordinateModel::getId).collect(Collectors.toList());
        paths.add(listPath);
        pathIds.add(pathId);
    }
}
