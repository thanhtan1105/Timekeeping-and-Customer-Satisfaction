/**
 * Created by TrungNN on 10/5/2016.
 */

//Date picker
$('#date-picker-cs-date').datepicker({
    format: com_full_date,
    endDate: new Date(),
    autoclose: true
});

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
        $form_submit_change_month = $('#form-submit-change-date'),
        selectedMonth = $form_submit_change_month.find('[name="selectedMonth"]');
    selectedMonth.val(selectedDate);

    // submit form
    $form_submit_change_month.submit();
});