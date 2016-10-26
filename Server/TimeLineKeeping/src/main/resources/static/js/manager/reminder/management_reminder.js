/**
 * Created by ASUS on 10/24/2016.
 */

var total_pages = 0;

/**
 * Event: click button edit
 * Description: submit form
 */
$('.btn-edit-reminder').on('click', function () {
    var id = $(this).attr('data-id'),
        $form_submit_view_reminder = $('#form-submit-view-reminder'),
        reminderId = $form_submit_view_reminder.find('[name="reminderId"]'),
        $text_time_reminder = $('#text-time-reminder-' + id).val();
    console.info('[id] ' + id);
    console.info('[text time reminder] ' + $text_time_reminder);

    //check (time_reminder - current_time) >= 15 minutes
    var time_reminder = new Date($text_time_reminder);
    var current_time = new Date();
    console.info('[time reminder] ' + time_reminder);
    console.info('[current time] ' + current_time);
    var difference_time = time_reminder - current_time;
    console.info('[difference time] ' + difference_time);
    if (difference_time >= time_out_reminder) {
        reminderId.val(id);

        //submit form
        $form_submit_view_reminder.submit();
    }
});

function load_list_reminders() {
    var managerId = $('#text-managerId').val(),
        urlString = "/api/reminder/list_by_manager?managerId=" + managerId,
        $tbody_list_reminders = $('#tbody-list-reminders'),
        content_list_reminders = '';
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    list_reminders = data.content,
                    time_reminder,
                    content_pagination = '',
                    $footer_pagination = $('#footer-pagination');
                total_pages = data.totalPages;
                //set list reminders
                for (var i = 0; i < list_reminders.length; i++) {
                    time_reminder = new Date(list_reminders[i].time);
                    console.info(time_reminder);
                    content_list_reminders += '<tr>' +
                        '<td>' + list_reminders[i].time + '</td>' +
                        '<td>' + list_reminders[i].title + '</td>' +
                        '<td>' + list_reminders[i].message + '</td>' +
                        '<td>' + list_reminders[i].room.name + '</td>' +
                        '<td>' +
                        '<button class="btn btn-success btn-flat btn-sm btn-edit-reminder" type="button" title="View Reminder">' +
                        '<i class="fa fa-eye"></i>' +
                        '</button>' +
                        '<button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Reminder">' +
                        '<i class="fa fa-remove"></i>' +
                        '</button>' +
                        '</td>' +
                        '</tr>';
                }
                //set pagination
                // for
                $tbody_list_reminders.html(content_list_reminders);
            }
        }
    });
}
