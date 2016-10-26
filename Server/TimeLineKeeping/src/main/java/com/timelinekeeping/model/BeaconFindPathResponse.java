package com.timelinekeeping.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 10/22/2016.
 */
public class BeaconFindPathResponse {
    private Boolean found = false;
    private CoordinateModel fromPoint;
    private CoordinateModel toPoint;
    private Double distance;
    private List<FloorPathReminderModel> paths;

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


    public void addPath(Integer floorId, List<CoordinateModel> listPath){
        if (paths == null){
            paths = new ArrayList<>();
        }
        List<Long> pathId = listPath.stream().map(CoordinateModel::getId).collect(Collectors.toList());
        FloorPathReminderModel floor = new FloorPathReminderModel(floorId, listPath, pathId);
        paths.add(floor);
    }

    public Boolean getFound() {
        return found;
    }

    public void setFound(Boolean found) {
        this.found = found;
    }

    public List<FloorPathReminderModel> getPaths() {
        return paths;
    }

    public void setPaths(List<FloorPathReminderModel> paths) {
        this.paths = paths;
    }
}
