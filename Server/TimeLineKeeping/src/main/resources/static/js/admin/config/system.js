/**
 * Created by TrungNN on 11/14/2016.
 */

var current_sendSMS;

/**
 * For event fc
 */
function load_page() {
    //hide button update processing
    event_hide('#btn-update-config-processing');
    //show button update
    event_show('#btn-update-config');

    //call ajax get list config
    ajax_get_list_config();
}

function update_system_config() {
    //form
    var $form_config_system = $('#form-config-system'),
        emotionAccept = $form_config_system.find('[name="emotionAccept"]').val(),
        checkinConfident = $form_config_system.find('[name="checkinConfident"]').val(),
        emailCompany = $form_config_system.find('[name="emailCompany"]').val(),
        timeCheckinBegin = $form_config_system.find('[name="timeCheckinBegin"]').val(),
        timeCheckinEnd = $form_config_system.find('[name="timeCheckinEnd"]').val();

    //model
    var configurationModel = {
        'sendSMS': current_sendSMS,
        'emotionAccept': emotionAccept,
        'emailCompay': emailCompany,
        'checkinConfident': checkinConfident,
        'timeCheckinBegin': timeCheckinBegin,
        'timeCheckinEnd': timeCheckinEnd
    };

    //hide button update
    event_hide('#btn-update-config');
    //show button update processing
    event_show('#btn-update-config-processing');

    //call ajax update system config
    ajax_update_system_config(configurationModel);
}

function ajax_get_list_config() {
    var urlString = '/api/handler/list_config';
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[get list config][success] ' + success);
            if (success) {
                var data = response.data;

                //set content list config
                set_content_list_config(data);
            }
        }
    });
}

function ajax_update_system_config(configurationModel) {
    var urlString = '/api/handler/update_config';
    $.ajax({
        type: 'POST',
        url: urlString,
        data: configurationModel,
        success: function (response) {
            var success = response.success;
            console.info('[update system config][success] ' + success);

            //set content message update config
            set_content_message_update_config(success);

            if (success) {
                //reload page
                load_page();

                //show modal
                show_modal('#modal-msg-success', true);
            } else {
                //show modal
                show_modal('#modal-msg-fail', true);
            }

            //hide button update processing
            event_hide('#btn-update-config-processing');
            //show button update
            event_show('#btn-update-config');
        }
    });
}

function set_content_list_config(data) {
    //response data
    var sendSMS = data.sendSMS,
        emotionAccept = data.emotionAccept,
        emailCompay = data.emailCompay,
        checkinConfident = data.checkinConfident,
        timeCheckinBegin = data.timeCheckinBegin,
        timeCheckinEnd = data.timeCheckinEnd;

    //form
    var $form_config_system = $('#form-config-system'),
        $emotionAccept = $form_config_system.find('[name="emotionAccept"]'),
        $checkinConfident = $form_config_system.find('[name="checkinConfident"]'),
        $emailCompany = $form_config_system.find('[name="emailCompany"]'),
        $timeCheckinBegin = $form_config_system.find('[name="timeCheckinBegin"]'),
        $timeCheckinEnd = $form_config_system.find('[name="timeCheckinEnd"]');

    //set current sendSMS
    current_sendSMS = sendSMS;

    //set content html
    $emotionAccept.val(emotionAccept);
    $checkinConfident.val(checkinConfident);
    $emailCompany.val(emailCompay);
    $timeCheckinBegin.val(timeCheckinBegin);
    $timeCheckinEnd.val(timeCheckinEnd);
}

function set_content_message_update_config(success) {
    var content;
    if (success) {
        content = 'Successful saving changes';
        $('#modal-msg-success-content').html(content);
    } else {
        content = 'Save the changes failed';
        $('#modal-msg-fail-content').html(content);
    }
}

/**
 * For common fc
 */
/**
 * Fc: show modal
 * @param id
 * @param enabled ('true', 'false')
 */
function show_modal(id, keyboard) {
    if (!keyboard) {//prevent closing
        $(id).modal({
            backdrop: 'static',
            keyboard: keyboard, //(false) prevent closing with Esc button
            show: true
        });
    } else {//allow closing
        $(id).modal({
            show: true
        });
    }

}

/**
 * Fc: hide
 * @param id
 */
function event_hide(id) {
    $(id).hide();
}

/**
 * Fc: show
 * @param id
 */
function event_show(id) {
    $(id).show();
}
