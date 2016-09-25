/**
 * Created by TrungNN on 9/24/2016.
 */

function getAccountCheckInModels() {
    var $form_submit_checkin = $('#form-submit-checkin-manual'),
        sizeOfListAccounts = $form_submit_checkin.find('[name="sizeOfListAccounts"]').val(),
        accountCheckInModels = $form_submit_checkin.find('[name="accountCheckInModels"]'),
        accountCheckInModelsJson = '[',
        accountId,
        statusCheckin,
        note;
    for (var i = 1; i <= sizeOfListAccounts; i++) {
        accountId = $('#accountId-' + i).val();
        statusCheckin = false;
        if ($('#statusCheckin-' + i).is(':checked')) {
            statusCheckin = true;
        }
        note = $('#note-' + i).val();
        accountCheckInModelsJson += '{';
        accountCheckInModelsJson += '"accountId":' + accountId;
        accountCheckInModelsJson += ', "statusCheckin":' + statusCheckin;
        accountCheckInModelsJson += ', "note":"' + note + '"';
        if (i == sizeOfListAccounts) {
            accountCheckInModelsJson += '}';
        } else {
            accountCheckInModelsJson += '},';
        }
    }
    accountCheckInModelsJson += ']';
    accountCheckInModels.val(accountCheckInModelsJson);
}
