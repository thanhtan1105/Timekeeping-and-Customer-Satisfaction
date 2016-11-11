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
var full_month = '';
