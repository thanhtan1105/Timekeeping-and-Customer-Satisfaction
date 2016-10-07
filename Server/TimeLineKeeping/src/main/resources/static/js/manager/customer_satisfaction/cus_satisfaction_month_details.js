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
 * Event: select another month
 */
$('.selected-date').on('change', function() {
    var selectedDate = $('.selected-date').val(),
        $form_submit_change_month = $('#form-submit-change-month'),
        selectedMonth = $form_submit_change_month.find('[name="selectedMonth"]');
    selectedMonth.val(selectedDate);

    // submit form
    $form_submit_change_month.submit();
});