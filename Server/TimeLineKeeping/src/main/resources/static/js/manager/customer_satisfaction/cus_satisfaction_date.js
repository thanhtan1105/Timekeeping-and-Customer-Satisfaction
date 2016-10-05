/**
 * Created by TrungNN on 10/5/2016.
 */

//datePicker
initDatePicker('#date_picker_date', 'dd-MM-yyyy', 'days', 'days');

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
 * Event: change to tag month
 */
$('#change-to-tag-month').on('click', function () {
    var $form_submit_change_to_tag_month = $('#form-submit-change-to-tag-month');

    // submit form
    $form_submit_change_to_tag_month.submit();
});

/**
 * Event: select another date
 */
$('.selected-date').on('change', function() {
    var selectedDate = $('.selected-date').val(),
        $form_submit_change_date = $('#form-submit-change-date'),
        selectedDate = $form_submit_change_date.find('[name="selectedDate"]');
    selectedDate.val(selectedDate);

    // submit form
    $form_submit_change_date.submit();
});