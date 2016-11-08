/**
 * Created by TrungNN on 11/8/2016.
 */

/**
 * Fc: load page
 */
function load_page() {
    //call ajax get list history
    ajax_get_list_history();
}

/**
 * Fc: sync data
 */
function sync_data() {
    //hide icon sync, btn sync and last time sync
    event_hide('#font-icon-sync');
    event_hide('#btn-sync');
    event_hide('#div-last-time-sync');
    //show icon sync, btn sync
    event_show('#font-icon-sync-processing');
    event_show('#btn-sync-processing');

    //call ajax sync data
    ajax_sync_data();
}

/**
 * For ajax
 */
/**
 * Fc: ajax get list history
 */
function ajax_get_list_history() {
    var urlString = '/api/handler/list_history';
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[get history][success] ' + success);
            if (success) {
                var data = response.data;
                //set content history
                set_content_history(data);

                //show div last time sync
                event_show('#div-last-time-sync');
                //show div history
                event_show('#div-history');
            } else {
                //hide div last time sync
                event_hide('#div-last-time-sync');
                //hide div history
                event_hide('#div-history');
            }
        }
    });
}

/**
 * Fc: ajax sync data
 */
function ajax_sync_data() {
    var urlString = '/api/handler/sync';
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[sync data][success] ' + success);
            if (success) {
                //reload page
                load_page();
                //show message
            } else {
                //show message
            }

            //hide icon sync, btn sync
            event_hide('#font-icon-sync-processing');
            event_hide('#btn-sync-processing');
            //show icon sync, btn sync and last time sync
            event_show('#font-icon-sync');
            event_show('#btn-sync');
            event_show('#div-last-time-sync');
        }
    });
}

/**
 * For set
 */
/**
 * Fc: set content history
 * @param data
 */
function set_content_history(data) {
    var $tbody_history = $('#tbody-history'),
        $font_last_time_sync = $('#font-last-time-sync'),
        content_history = '',
        content_last_time_sync,
        time;
    if (data != null && data.length > 0) {
        for (var i = 0; i < data.length; i++) {
            time = data[i].time;
            //set content time
            content_history += set_time(new Date(time));
        }

        //set content last time sync
        content_last_time_sync = set_last_time_sync(new Date(data[0].time));
    }
    //set content html
    $tbody_history.html(content_history);
    $font_last_time_sync.html(content_last_time_sync);
}

/**
 * Fc: set time
 * @param date
 * @returns {string}
 */
function set_time(date) {
    var year = date.getFullYear(),
        month = date.getMonth() + 1, // months are zero indexed
        day = date.getDate(),
        hour = date.getHours(),
        minute = date.getMinutes(),
        second = date.getSeconds(),
        hourFormatted = hour % 24 || 24, // hour returned in 24 hour format
        minuteFormatted = minute < 10 ? "0" + minute : minute,
        format_date = year + '-' + month + '-' + day,
        format_time = hourFormatted + ':' + minuteFormatted + ':' + second,
        content = '';

    //set content
    content += '<tr>' +
        '<td>' + format_date + '</td>' +
        '<td>' + format_time + '</td>' +
        '</td>';
    return content;
}

/**
 * Fc: set last time sync
 * @param date
 * @returns {string}
 */
function set_last_time_sync(date) {
    var year = date.getFullYear(),
        month = date.getMonth() + 1, // months are zero indexed
        day = date.getDate(),
        hour = date.getHours(),
        minute = date.getMinutes(),
        second = date.getSeconds(),
        hourFormatted = hour % 24 || 24, // hour returned in 24 hour format
        minuteFormatted = minute < 10 ? "0" + minute : minute,
        format_date = year + '-' + month + '-' + day,
        format_time = hourFormatted + ':' + minuteFormatted + ':' + second;

    return format_date + '  ' + format_time;
}

/**
 * For util fc
 */
/**
 * Fc: hide
 * @param id
 */
function event_hide(id) {
    $(id).hide();
}

/**
 * Fc: show
 * @param id
 */
function event_show(id) {
    $(id).show();
}
