/**
 * Created by TrungNN on 10/5/2016.
 */

//datePicker
initDatePicker('#date_picker_month', 'MM-yyyy', 'months', 'months');

/**
 * Init DatePicker
 * @param id
 * @param format
 * @param viewMode
 * @param minViewMode
 */
function initDatePicker(id, format, viewMode, minViewMode) {
    $(id).datepicker({
        format: format,
        viewMode: viewMode,
        minViewMode: minViewMode,
        autoclose: true
    });
}

/**
 * Event: change to tag date
 */
$('#change-to-tag-date').on('click', function () {
    var $form_submit_change_to_tag_date = $('#form-submit-change-to-tag-date');

    // submit form
    $form_submit_change_to_tag_date.submit();
});