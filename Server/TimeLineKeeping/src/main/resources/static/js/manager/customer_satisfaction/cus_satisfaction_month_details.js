/**
 * Created by TrungNN on 10/5/2016.
 */

//Date picker
$('#date-picker-cs-date-detail').datepicker({
    format: com_full_year_month,
    viewMode: "months",
    minViewMode: "months",
    endDate: new Date(),
    autoclose: true
});

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