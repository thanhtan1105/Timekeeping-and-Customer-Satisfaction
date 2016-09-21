package com.timelinekeeping.constant;

/**
 * Created by HienTQSE60896 on 9/19/2016.
 */
public interface ERROR {
    String ACCOUNT_ADD_FACE_CANNOT_SAVE_DB = "Cannot save database for faceId";
    String ACCOUNT_ADD_FACE_CANNOT_FOUND_ACCOUNTID = "Cannot found accountId";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE = "Cannot detect Image.";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_EXIST_MANY_PEOPLE_IN_IMAGE = "The image has greater than 1 person.";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE = "The image cannot identify.";
    String ERROR_ACCOUNT_CHECKIN_NOT_FOUND_PERSONID = "Cannot found the Account with PersonID.";
    String ERROR_ACCOUNT_CHECKIN_MSDS = "Error from Database";

    String ACCOUNT_API_SEARCH_DEPARTMENT_EMPTY = "DepartmentID is empty.";
    String EMOTION_API_GET_CUSTOMER_EMOTION_EMPTY_DETECT = "Image cannot detect.";
    String ACCOUNT_API_CRATE_CUSTOMER_ALREADY_EXIST = "User name %s already exists.";
    String ACCOUNT_API_CRATE_DEPARTMENT_DOES_NOT_EXIST = "DepartmentID '%s' does not exist.";
    String ACCOUNT_API_CRATE_ROLE_DOES_NOT_EXIST = "RoleId '%s' does not exist.";
    String ERROR_IN_MCS = "Error in MCS: ";

    String DEPARTMENT_API_CREATE_DEPARTMENT_DOES_EXIST = "Person group '%s' already exists.";

    String OTHER = "Other Error.";
}
