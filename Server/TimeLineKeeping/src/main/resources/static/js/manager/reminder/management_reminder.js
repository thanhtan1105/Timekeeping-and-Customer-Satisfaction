/**
 * Created by ASUS on 10/24/2016.
 */

/**
 * Event: click button edit
 * Description: submit form
 */
$('.btn-edit-reminder').on('click', function () {
    var id = $(this).attr('data-id'),
        $form_submit_view_reminder = $('#form-submit-view-reminder'),
        reminderId = $form_submit_view_reminder.find('[name="reminderId"]');
    console.info('id: ' + id);
    reminderId.val(id);

    //submit form
    $form_submit_view_reminder.submit();
})
