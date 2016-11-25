/**
 * Created by HienTQSE60896 on 10/11/2016.
 */

/**
 * For time out
 * @type {number}
 */
var time_out = 2500;
/**
 * Not allow update reminder if time_reminder - current_time <= 15 minutes
 * @type {number}
 */
var time_out_reminder = 1000 * 60 * 15;
var com_time_out_worker_get_emotion = 60 * 2 * 1000;

/**
 * Check is first time load page get customer emotion
 * @type {boolean}
 */
var com_first_time_load_get_customer_emotion = true;

/**
 * Fc: format date
 * @param date
 * @returns {string}
 */
function formatDate(date) {
    var year = date.getFullYear(),
        month = date.getMonth() + 1, // months are zero indexed
        day = date.getDate(),
        hour = date.getHours(),
        minute = date.getMinutes(),
        second = date.getSeconds(),
        hourFormatted = hour % 24 || 24, // hour returned in 24 hour format
        minuteFormatted = minute < 10 ? "0" + minute : minute;


    return day + "/" + month + "/" + year + " (" + hourFormatted + ":" + minuteFormatted + ")";
}

/**
 * Pattern time
 */
var com_full_year_month = 'yyyy-MM';
var com_full_date = 'yyyy-M-dd';

/**
 * Fc: check empty
 * @param value
 * @returns {boolean}
 */
function check_empty(value) {
    return (value != null && value != '');
}

/**
 * Fc: get age from year of birth
 * @param year_of_birth
 * @returns {number}
 */
function get_age(year_of_birth) {
    var current = new Date(),
        current_year = current.getFullYear();

    //return age
    return (current_year - year_of_birth);
}

/**
 * Fc: get year of birth
 * @param age
 * @returns {number}
 */
function get_year_of_birth(age) {
    var current = new Date(),
        current_year = current.getFullYear();

    //return year of birth
    return (current_year - age);
}
