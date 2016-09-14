package com.timelinekeeping.modelAPI;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/14/2016.
 */
public class PersonInformation {

    private String personId;
    private String name;
    private String userData;
    private List<String> persistedFaceIds;

    public PersonInformation() {
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public List<String> getPersistedFaceIds() {
        return persistedFaceIds;
    }

    public void setPersistedFaceIds(List<String> persistedFaceIds) {
        this.persistedFaceIds = persistedFaceIds;
    }
}
