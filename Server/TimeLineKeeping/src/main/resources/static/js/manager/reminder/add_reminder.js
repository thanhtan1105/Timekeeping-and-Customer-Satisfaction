/**
 * Created by TrungNN on 11/3/2016.
 */

/**
 * Initial datepicker
 */
$('#datepicker-reminder-adding').datepicker({
    format: 'yyyy-M-dd',
    startDate: new Date(),
    todayHighlight: 1,
    autoclose: true
});

/**
 * Initial timepicker
 */
$(".timepicker-reminder-adding").timepicker({
    format: 'hh:mm',
    defaultTime: 'current',
    minuteStep: 1,
    disableFocus: false,
    template: 'dropdown',
    showInputs: true
});

/**
 * Fc: submit form add reminder
 */
function submit_form() {
    var $form_add = $('#form-add-reminder'),
        $title = $form_add.find('[name="title"]'),
        $roomId = $form_add.find('[name="roomId"]'),
        $listEmployees = $form_add.find('[name="listEmployees"]'),
        title = $title.val(),
        date_text = $('#datepicker-reminder-adding').datepicker('getDate'),
        time_text = $('.timepicker-reminder-adding').data("timepicker").getTime(),
        roomId = $roomId.val(),
        listEmployees = $listEmployees.val(),
        find_error = false,
        message_error,
        date = parse_date(date_text),
        date_time_reminder = new Date(date + ' ' + time_text);
    console.info('[Add reminder][date time] ' + date_time_reminder);

    //check validation
    //title
    if (!check_string(title, 2)) {
        find_error = true;
        message_error = 'Length of title must be >= 2 characters';
        set_content_error('#div-title', '#span-title-msg-error', message_error);
    } else {
        //reset error null title (if any)
        reset_content_error('#div-title', '#span-title-msg-error');
    }
    //date
    if (!check_string(date, 1)) {
        find_error = true;
        message_error = 'Date is required';
        set_content_error('#div-date', '#span-date-msg-error', message_error);
    } else if (!check_string(time_text, 1)) {
        find_error = true;
        message_error = 'Time is required';
        set_content_error('#div-time', '#span-time-msg-error', message_error);

        //reset error null date (if any)
        reset_content_error('#div-date', '#span-date-msg-error');
    } else if (!check_date(date_time_reminder, '>=')) {
        find_error = true;
        message_error = 'Reminder time must be created before current 15 minutes';
        set_content_error('#div-time', '#span-time-msg-error', message_error);

        //reset error null date (if any)
        reset_content_error('#div-date', '#span-date-msg-error');
    } else {
        //reset error null date (if any)
        reset_content_error('#div-date', '#span-date-msg-error');
        //reset error time (if any)
        reset_content_error('#div-time', '#span-time-msg-error');
    }
    //room
    if (!check_number_id(roomId)) {
        find_error = true;
        message_error = 'Room is required';
        set_content_error('#div-room', '#span-room-msg-error', message_error);
    } else {
        //reset error empty room (if any)
        reset_content_error('#div-room', '#span-room-msg-error');
    }
    // //participants
    // if (!check_list(listEmployees)) {
    //     find_error = true;
    //     message_error = 'Participants are required';
    //     set_content_error('#div-participant', '#span-participant-msg-error', message_error);
    // } else {
    //     //reset error empty participants (if any)
    //     reset_content_error('#div-participant', '#span-participant-msg-error');
    // }

    console.info('[Add reminder][find error] ' + find_error);
    if (!find_error) {
        //submit form
        $form_add.submit();
    } else {
        //do nothing
    }
}

/**
 * Fc: check string: not null && length > 0
 * @param text
 * @param length_text
 * @returns {boolean}
 */
function check_string(text, length_text) {
    return (text != null && text.length >= length_text);
}

/**
 * Fc: check date: time reminder - current >= 15 minutes
 * @param date
 * @param comparison
 * @returns {boolean}
 */
function check_date(date, comparison) {
    var current_date = new Date();
    console.info('[Check date][current] ' + current_date);
    console.info('[Check date][date reminder] ' + date);
    if (comparison == '>=') {
        return date - current_date >= 0;
        // return date - current_date >= time_out_reminder;
    }
}

/**
 * Fc: check number: not null && > 0
 * @param value
 * @returns {boolean}
 */
function check_number_id(value) {
    return (value != null && value != '' && value > 0);
}

/**
 * Fc: check list: not null && length > 0
 * @param value
 * @returns {boolean}
 */
function check_list(value) {
    return (value != null && value.length > 0);
}

/**
 * Fc: set content error
 * @param id_form_group
 * @param id_msg_error
 * @param msg_error
 */
function set_content_error(id_form_group, id_msg_error, msg_error) {
    $(id_form_group).attr('class', 'form-group has-error');
    $(id_msg_error).html(msg_error);
}

/**
 * Fc: reset content error
 * @param id_form_group
 * @param id_msg_error
 */
function reset_content_error(id_form_group, id_msg_error) {
    $(id_form_group).attr('class', 'form-group');
    $(id_msg_error).html('');
}

/**
 * Fc: parse date to 'yyyy-M-dd'
 * @param date_text
 * @returns {string}
 */
function parse_date(date_text) {
    if (date_text != null) {
        var date = new Date(date_text),
            year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate();
        return year + '-' + month + '-' + day;
    }
}
