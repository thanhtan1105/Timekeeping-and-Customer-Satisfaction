/**
 * Created by TrungNN on 11/14/2016.
 */

/**
 * For event fc
 */
/**
 * Fc: load page
 */
function load_page() {
    //hide button update processing
    event_hide('#btn-update-config-processing');
    //show button update
    event_show('#btn-update-config');

    //call ajax get list config
    ajax_get_list_config();
}

/**
 * Fc: ajax update system config
 */
function update_system_config() {
    //form
    var $form_config_system = $('#form-config-system'),
        emotionAccept = $form_config_system.find('[name="emotionAccept"]').val(),
        checkinConfident = $form_config_system.find('[name="checkinConfident"]').val(),
        trainConfident = $form_config_system.find('[name="trainConfident"]').val(),
        emailCompany = $form_config_system.find('[name="emailCompany"]').val(),
        timeCheckinBegin = $form_config_system.find('[name="timeCheckinBegin"]').val(),
        timeCheckinEnd = $form_config_system.find('[name="timeCheckinEnd"]').val(),
        emotionAdvanceConfidence = $form_config_system.find('[name="emotionAdvanceConfidence"]').val(),
        emotionAgeA = $form_config_system.find('[name="emotionAgeA"]').val(),
        emotionAgeB = $form_config_system.find('[name="emotionAgeB"]').val(),
        sendSMS = get_radio_selected_value($form_config_system, 'sendSMS'),
        emotionAdvance = get_radio_selected_value($form_config_system, 'emotionAdvance');

    //model
    var configurationModel = {
        'sendSMS': sendSMS,
        'emotionAccept': emotionAccept,
        'emailCompany': emailCompany,
        'checkinConfident': checkinConfident,
        'trainConfident': trainConfident,
        'timeCheckinBegin': timeCheckinBegin,
        'timeCheckinEnd': timeCheckinEnd,
        'emotionAdvanceConfidence': emotionAdvanceConfidence,
        'emotionAdvance': emotionAdvance,
        'emotionAgeA': emotionAgeA,
        'emotionAgeB': emotionAgeB
    };

    //hide button update
    event_hide('#btn-update-config');
    //show button update processing
    event_show('#btn-update-config-processing');

    //call ajax update system config
    ajax_update_system_config(configurationModel);
}

/**
 * Fc: ajax get list config
 */
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

/**
 * Fc: update system config
 * @param configurationModel
 */
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

/**
 * Fc: set content list config
 * @param data
 */
function set_content_list_config(data) {
    //response data
    var sendSMS = data.sendSMS,
        emotionAccept = data.emotionAccept,
        emailCompany = data.emailCompany,
        checkinConfident = data.checkinConfident,
        trainConfident = data.trainConfident,
        timeCheckinBegin = data.timeCheckinBegin,
        timeCheckinEnd = data.timeCheckinEnd,
        emotionAdvance = data.emotionAdvance,
        emotionAdvanceConfidence = data.emotionAdvanceConfidence,
        emotionAgeA = data.emotionAgeA,
        emotionAgeB = data.emotionAgeB,
        boundScorce = data.boundScorce,
        anger = boundScorce.anger,
        contempt = boundScorce.contempt,
        disgust = boundScorce.disgust,
        fear = boundScorce.fear,
        happiness = boundScorce.happiness,
        neutral = boundScorce.neutral,
        sadness = boundScorce.sadness,
        surprise = boundScorce.surprise;

    //form
    var $form_config_system = $('#form-config-system'),
        $emotionAccept = $form_config_system.find('[name="emotionAccept"]'),
        $checkinConfident = $form_config_system.find('[name="checkinConfident"]'),
        $trainConfident = $form_config_system.find('[name="trainConfident"]'),
        $emailCompany = $form_config_system.find('[name="emailCompany"]'),
        $timeCheckinBegin = $form_config_system.find('[name="timeCheckinBegin"]'),
        $timeCheckinEnd = $form_config_system.find('[name="timeCheckinEnd"]'),
        $emotionAdvanceConfidence = $form_config_system.find('[name="emotionAdvanceConfidence"]'),
        $emotionAgeA = $form_config_system.find('[name="emotionAgeA"]'),
        $emotionAgeB = $form_config_system.find('[name="emotionAgeB"]'),
        $anger = $form_config_system.find('[name="anger"]'),
        $contempt = $form_config_system.find('[name="contempt"]'),
        $disgust = $form_config_system.find('[name="disgust"]'),
        $fear = $form_config_system.find('[name="fear"]'),
        $happiness = $form_config_system.find('[name="happiness"]'),
        $neutral = $form_config_system.find('[name="neutral"]'),
        $sadness = $form_config_system.find('[name="sadness"]'),
        $surprise = $form_config_system.find('[name="surprise"]'),
        selected_radio_sendSMS = 0,
        selected_radio_emotionAdvance = 0;

    //set selected radio sendSMS
    if (sendSMS) {
        selected_radio_sendSMS = 1;
    }
    //set selected radio emotionAdvance
    if (emotionAdvance) {
        selected_radio_emotionAdvance = 1;
    }
    //set radio sendSMS
    set_radio_selected_value($form_config_system, 'sendSMS', selected_radio_sendSMS);
    //set radio emotionAdvance
    set_radio_selected_value($form_config_system, 'emotionAdvance', selected_radio_emotionAdvance);

    //set content html
    $emotionAccept.val(emotionAccept);
    $checkinConfident.val(checkinConfident);
    $trainConfident.val(trainConfident);
    $emailCompany.val(emailCompany);
    $timeCheckinBegin.val(timeCheckinBegin);
    $timeCheckinEnd.val(timeCheckinEnd);
    $emotionAdvanceConfidence.val(emotionAdvanceConfidence);
    $emotionAgeA.val(emotionAgeA);
    $emotionAgeB.val(emotionAgeB);
    $anger.val(anger);
    $contempt.val(contempt);
    $disgust.val(disgust);
    $fear.val(fear);
    $happiness.val(happiness);
    $neutral.val(neutral);
    $sadness.val(sadness);
    $surprise.val(surprise);
}

/**
 * Fc: set content message update config
 * @param success
 */
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

/**
 * Fc: set radio selected value
 * @param $form
 * @param name
 * @param value
 */
function set_radio_selected_value($form, name, value) {
    $form.find('input:radio[name="' + name + '"][value="' + value + '"]').iCheck('check');
}

/**
 * Fc: get radio selected value
 * @param $form
 * @param name
 * @returns {*}
 */
function get_radio_selected_value($form, name) {
    var value = $form.find('input:radio[name="' + name + '"]:checked').val();
    return value;
}
