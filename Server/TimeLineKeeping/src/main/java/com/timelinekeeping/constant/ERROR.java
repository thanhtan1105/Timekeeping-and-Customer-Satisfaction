package com.timelinekeeping.constant;

/**
 * Created by HienTQSE60896 on 9/19/2016.
 */
public interface ERROR {

    String OTHER = "Other Error.";

    String ERROR_IN_MCS = "Error in MCS: ";

    /** COMMON*/
    String ACCOUNT_ID_CANNOT_EXIST = "AccountID = '%s' does not exist.";

    /** ACCOUNT */
    String ACCOUNT_ADD_FACE_CANNOT_SAVE_DB = "Cannot save database for faceId";
    String ACCOUNT_ADD_FACE_CANNOT_FOUND_ACCOUNTID = "Cannot found accountId";
    String ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE = "Cannot detect Image.";
    String ACCOUNT_CHECKIN_IMAGE_EXIST_MANY_PEOPLE_IN_IMAGE = "The image has greater than 1 person.";
    String ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE = "The image cannot identify.";
    String ACCOUNT_CHECKIN_NOT_FOUND_PERSONID = "Cannot found the Account with PersonID.";
    String ACCOUNT_CHECKIN_MSDS = "Error from Database";


    String ACCOUNT_API_SEARCH_DEPARTMENT_EMPTY = "DepartmentID is empty.";
    String ACCOUNT_API_CRATE_CUSTOMER_ALREADY_EXIST = "User name %s already exists.";
    String ACCOUNT_API_CRATE_DEPARTMENT_DOES_NOT_EXIST = "DepartmentID '%s' does not exist.";
    String ACCOUNT_API_CRATE_ROLE_DOES_NOT_EXIST = "RoleId '%s' does not exist.";

    String ACCOUNT_API_USERNAME_IS_NOT_EMPTY = "Username is Empty.";
    String ACCOUNT_API_PASSWORD_IS_NOT_EMPTY = "Password is Empty.";

    /** EMOTION*/
    String EMOTION_API_GET_CUSTOMER_EMOTION_EMPTY_DETECT = "Image cannot detect.";

    /**DEPARTMENT*/
    String DEPARTMENT_API_CREATE_DEPARTMENT_DOES_EXIST = "Person group '%s' already exists.";
    String DEPARTMENT_API_DEPARTMENT_DOES_NOT_EXIST = "Department id = '%s' does not exists.";




    /** REMINDER**/
    String REMINDER_MANAGER_ID_NO_EXIST = "Managerid = '%s' no exist in System";
    String REMINDER_ROOM_ID_NO_EXIST = "Room = '%s' no exist in System";

    /** checkin*/
    String CHECK_IN_MANUAL_NO_EXIST_ACCOUNTID = "No exist accountId = '%s'.";
    String CHECK_IN_MANUAL_ACCOUNT_CHECKINED = "AccountId = '%s' is used to checkIn.";



    /** TO DO */
    String TODO_TITLE_EMPTY = "Title is empty";
    String TODO_ACCOUNT_CREATE_EMPTY = "Account create is empty";
    String TODO_ID_EMPTY = "ID  is empty";
    String TODO_ID_CANNOT_EXIST = "TodoId = '%s' does not exist.";

    /** OTHER */
    String MESSAGE_CHECKIN_MANUAL = "Check in manual by %s.";


}
