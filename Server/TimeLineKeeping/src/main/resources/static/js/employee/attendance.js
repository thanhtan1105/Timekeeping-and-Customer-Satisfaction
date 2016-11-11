/**
 * Created by TrungNN on 10/2/2016.
 */

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

//Date picker
$('#date-picker-attendance').datepicker({
    format: com_full_year_month,
    viewMode: "months",
    minViewMode: "months",
    autoclose: true
});