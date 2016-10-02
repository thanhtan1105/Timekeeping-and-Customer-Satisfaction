/**
 * Created by TrungNN on 9/30/2016.
 */

/**
 * Submit form view details
 */
$('.view-details-tk').on('click', function () {
    var id = $(this).attr('data-id'),
        $form_submit_details = $('#form-submit-view-details-' + id),
        accountTKDetailsModel = $form_submit_details.find('[name="accountTKDetailsModel"]'),
        accountId = id,
        selectedDate = $('.selected-date').val(),
        accountTKDetailsModelJson = '{';
    accountTKDetailsModelJson += '"accountId":' + accountId;
    accountTKDetailsModelJson += ', "selectedDate":"' + selectedDate + '"';
    accountTKDetailsModelJson += '}';

    accountTKDetailsModel.val(accountTKDetailsModelJson);

    // submit form
    $form_submit_details.submit();
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
