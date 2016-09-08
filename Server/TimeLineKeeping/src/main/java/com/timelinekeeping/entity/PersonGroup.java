package com.timelinekeeping.entity;

/**
 * Created by lethanhtan on 9/8/16.
 */
public class PersonGroup {

    String id;
    String name;
    String descriptions;

    public PersonGroup(String id, String name, String descriptions) {
        this.id = id;
        this.name = name;
        this.descriptions = descriptions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }
}
