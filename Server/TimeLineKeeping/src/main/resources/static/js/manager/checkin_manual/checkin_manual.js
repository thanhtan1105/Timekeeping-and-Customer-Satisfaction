/**
 * Created by TrungNN on 9/24/2016.
 */

function getAccountCheckInModels() {
    var $form_submit_checkin = $('#form-submit-checkin-manual'),
        sizeOfListAccounts = $form_submit_checkin.find('[name="sizeOfListAccounts"]').val();
    alert('Size: ' + sizeOfListAccounts);
}
