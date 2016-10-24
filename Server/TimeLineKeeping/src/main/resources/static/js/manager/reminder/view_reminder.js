/**
 * Created by ASUS on 10/24/2016.
 */

/**
 * Event: click button edit
 * Description: submit form
 */
$('#btn-edit-reminder').on('click', function () {
    var $form_submit_edit_reminder = $('#form-submit-edit-reminder');
    //submit form
    $form_submit_edit_reminder.submit();
});
