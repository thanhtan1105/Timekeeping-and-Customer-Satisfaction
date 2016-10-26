package com.timelinekeeping.model;

import java.util.List;

/**
 * Created by HienTQSE60896 on 10/26/2016.
 */
public class FloorPathReminderModel {
    Integer floor;
    List<CoordinateModel> path;
    List<Long> pathId;


    public FloorPathReminderModel() {
    }

    public FloorPathReminderModel(Integer floor, List<CoordinateModel> path, List<Long> pathId) {
        this.floor = floor;
        this.path = path;
        this.pathId = pathId;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
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
