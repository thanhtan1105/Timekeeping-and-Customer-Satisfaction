package com.timelinekeeping.constant;


/**
 * Created by HienTQSE60896 on 9/17/2016.
 */
public interface ERROR {

    /***
     * stucture ERROR:
     * A: Account
     */

    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE = "Cannot detect Image.";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_EXIST_MANY_PEOPLE_IN_IMAGE = "The image has greater than 1 person.";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE = "The image cannot identify.";

    String ERROR_ACCOUNT_CHECKIN_NOT_FOUND_PERSONID = "Cannot found the Account with PersonID.";
    String FACE_CANNOT_SAVE_DB = "Cannot save database for faceId";

    String ACCOUNT_ADD_FACE_CANNOT_SAVE_DB = "Cannot save database for faceId";
    String ACCOUNT_ADD_FACE_CANNOT_FOUND_ACCOUNTID = "Cannot found accountId";


    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE = "A00001";


    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_DETECT_IMAGE = "Cannot detect Image.";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_EXIST_MANY_PEOPLE_IN_IMAGE = "The image has greater than 1 person.";
    String ERROR_ACCOUNT_CHECKIN_IMAGE_CANNOT_IDENTIFY_IMAGE = "The image cannot identify.";

    String ERROR_ACCOUNT_CHECKIN_NOT_FOUND_PERSONID = "Cannot found the Account with PersonID.";

}
