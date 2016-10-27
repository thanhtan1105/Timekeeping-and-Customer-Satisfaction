/**
 * Created by TrungNN on 10/24/2016.
 */

function event_hide(id) {
    $(id).hide();
}

function event_show(id) {
    $(id).show();
}

/**
 * Event: click button edit
 * Description: submit form
 */
$('#btn-edit-reminder').on('click', function () {
    var $form_submit_edit_reminder = $('#form-submit-edit-reminder'),
        $text_time_reminder = $('#text-time-reminder').val();
    console.info('[text time reminder] ' + $text_time_reminder);
    //check (time_reminder - current_time) >= 15 minutes
    var time_reminder = new Date(parseInt($text_time_reminder));
    var current_time = new Date();
    console.info('[time reminder] ' + time_reminder);
    console.info('[current time] ' + current_time);
    var difference_time = time_reminder - current_time;
    console.info('[difference time] ' + difference_time);

    if (difference_time >= time_out_reminder) {
        //show button edit
        event_show('#btn-edit-reminder');
        //submit form
        $form_submit_edit_reminder.submit();
    } else {
        //hide button edit
        event_hide('##btn-edit-reminder');
    }
});
