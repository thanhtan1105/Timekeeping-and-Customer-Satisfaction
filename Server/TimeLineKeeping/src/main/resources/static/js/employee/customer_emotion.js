/**
 * Created by TrungNN on 10/4/2016.
 */

var accountId = $('#accountId').val();
var customerCode;
var timer_get_emotion;
var timer_get_image;
var timer_stop_get_emotion;
var nextAngle = 0;
var current_gender;
var current_customer_id;
var current_customer_code;

/**
 * Fc: load page
 */
function load_page() {
    //call request: load page
    worker_get_emotion();

    //timeout stop worker get emotion
    time_out_worker_get_emotion();
}

/**
 * Event: next transaction
 */
$('#btn-next-transaction').on('click', function () {
    console.info('Running btn next');
    //disable button next
    event_disabled('#btn-next-transaction', true);
    //disable button skip
    event_disabled('.btn-skip-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //hide div add customer information
    event_hide('#div-add-customer-information');
    //hide div not get emotion
    event_hide('#div-not-get-emotion');
    //hide button show modal customer emotion
    event_hide('#btn-show-modal-customer-emotion');

    //check is first call
    if (com_first_time_load_get_customer_emotion) {//is first call get_emotion api
        //call request: load page
        load_page();
        //reset is first time
        com_first_time_load_get_customer_emotion = false;
    } else {// is not first call next api
        //call request: next transaction (isSkip == false)
        worker_next_transaction(false);
    }
});

/**
 * Event: skip transaction
 */
$('.btn-skip-transaction').on('click', function () {
    console.info('Running btn skip');
    //disable button next
    event_disabled('#btn-next-transaction', true);
    //disable button skip
    event_disabled('.btn-skip-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //hide div add customer information
    event_hide('#div-add-customer-information');
    //hide div not get emotion
    event_hide('#div-not-get-emotion');
    //hide button show modal customer emotion
    event_hide('#btn-show-modal-customer-emotion');

    //call request: next transaction (isSkip == true)
    worker_next_transaction(true);
});

/**
 * Event: click button rotate
 * Description: rotate right 90
 */
$('#btn-rotate-image').on('click', function () {
    var degrees = 90;
    rotateRight('#image-customer', degrees);
});

/**
 * Fc: keydown customer emotion
 */
// $('#div-keydown-get-customer-emotion').keydown(function (event) {
//     var keyCode = (event.keyCode ? event.keyCode : event.which);
//     if (keyCode == 13) {
//         if (prevent_keydown_saving_customer_information()) {
//             console.info('Running keydown');
//             //disable button next
//             event_disabled('#btn-next-transaction', true);
//             //disable button skip
//             event_disabled('.btn-skip-transaction', true);
//             //hide div overview customer emotion
//             event_hide('#div-overview-customer-emotion');
//             //hide div add customer information
//             event_hide('#div-add-customer-information');
//             //hide div not get emotion
//             event_hide('#div-not-get-emotion');
//             //hide button show modal customer emotion
//             event_hide('#btn-show-modal-customer-emotion');
//
//             //check is first call
//             if (com_first_time_load_get_customer_emotion) {//is first call get_emotion api
//                 //call request: load page
//                 load_page();
//                 //reset is first time
//                 com_first_time_load_get_customer_emotion = false;
//             } else {// is not first call next api
//                 //call request: next transaction (isSkip == false)
//                 worker_next_transaction(false);
//             }
//         }
//     }
// });

/**
 * Initial datetime picker year of birth
 */
$('#datetime-picker-year-of-birth').datepicker({
    format: "yyyy",
    viewMode: "years",
    minViewMode: "years",
    autoclose: true
});

/**
 * Worker: get first emotion
 * Description: run on loading page or clicking skip button
 */
function worker_get_emotion() {
    var urlString = '/api/emotion/get_emotion?accountId=' + accountId;
    console.info('[worker_get_emotion] accountId: ' + accountId);
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            console.info('success: ' + response.success);
            if (response.success) {
                var data = response.data,
                    messages = data.messages,
                    emotionExist = data.emotionPercent,
                    awsUrl = data.awsUrl,
                    customerCode = data.customerCode,
                    customerInformation = data.customerInformation,
                    customerHistory = data.customerHistory,
                    customer_emotion_msg = messages.message,
                    suggestions = messages.sugguest,
                    age_predict = messages.predict,
                    gender = messages.gender;

                //hide div not get emotion
                event_hide('#div-not-get-emotion');
                //show div overview customer emotion
                event_show('#div-overview-customer-emotion');
                //show div add customer information
                event_show('#div-add-customer-information');
                //show button show modal customer emotion
                event_show('#btn-show-modal-customer-emotion');
                //reset scroll
                reset_scroll('#div-body-scroll-customer-emotion');
                //enable button next
                event_disabled('#btn-next-transaction', false);
                //enable button skip
                event_disabled('.btn-skip-transaction', false);

                //set overview customer emotion
                set_content_overview_customer_emotion(age_predict, gender, awsUrl, emotionExist, customer_emotion_msg, suggestions, customerInformation, customerHistory);
                //set adding customer emotion
                set_content_add_customer_emotion(customerInformation);
                //stop time out worker get emotion
                clearTimeout(timer_stop_get_emotion);
                //stop request: get first emotion
                clearTimeout(timer_get_emotion);

                //set current customer code
                current_customer_code = customerCode;
            }
        }
    });
    timer_get_emotion = setTimeout(worker_get_emotion, time_out);
};

/**
 * Worker: next transaction
 * @param isSkip (true: skip; false: next)
 */
function worker_next_transaction(isSkip) {
    var urlString = '/api/emotion/next?accountId=' + accountId
        + '&skip=' + isSkip;
    console.info('[Worker next transaction][accountId] ' + accountId);
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            console.info('[Worker next transaction][success] ' + response.success);
            if (response.success) {
                customerCode = response.data;
                if (customerCode != null) {
                    //call request: load page
                    load_page();
                }
            } else {
                console.log(response.data)
            }
        }
    });
};

/**
 * Worker: get image
 * @param accountId
 */
function worker_get_image() {
    var urlString = '/api/emotion/get_image?accountId=' + accountId;
    console.info('[worker_get_image] accountId: ' + accountId);
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            console.info('success: ' + response.success);
            if (response.success) {
                var data = response.data,
                    urlImage = data.image;
                console.info('[urlImage] ' + urlImage);
                if (urlImage != null) {
                    //hide div preloader image
                    event_hide('#div-preloader-image');
                    //show div image customer
                    event_show('#div-image-customer');
                    //show div rotate image
                    event_show('#div-rotate-image');

                    // setSrcImage('#image-customer', urlImage);
                    setSrcImage('#image-customer', "data:image/png;base64," + urlImage);
                    //stop request: get image
                    clearTimeout(timer_get_image);
                }
            }
        }
    });
    timer_get_image = setTimeout(worker_get_image, time_out);
}

/**
 * Fc: set time out stop worker get emotion
 */
function time_out_worker_get_emotion() {
    //set timeout stop worker get emotion
    timer_stop_get_emotion = setTimeout(function () {
        console.info('[Stopped worker get emotion]')
        //stop request get emotion
        clearTimeout(timer_get_emotion);

        //hide div overview customer emotion
        event_hide('#div-overview-customer-emotion');
        //hide div add customer information
        event_hide('#div-add-customer-information');
        //hide button show modal customer emotion
        event_hide('#btn-show-modal-customer-emotion');
        //show div not get emotion
        event_show('#div-not-get-emotion');
        //enable button next
        event_disabled('#btn-next-transaction', false);
        //enable button skip
        event_disabled('.btn-skip-transaction', false);
    }, com_time_out_worker_get_emotion);
}

/**
 * Fc: stop get emotion manual
 */
function stop_get_emotion_manual() {
    console.info('[Stopped worker get emotion manual]');
    //stop request get emotion
    clearTimeout(timer_get_emotion);
    //stop time out worker get emotion
    clearTimeout(timer_stop_get_emotion);

    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //hide div add customer information
    event_hide('#div-add-customer-information');
    //hide button show modal customer emotion
    event_hide('#btn-show-modal-customer-emotion');
    //show div not get emotion
    event_show('#div-not-get-emotion');
    //enable button next
    event_disabled('#btn-next-transaction', false);
    //enable button skip
    event_disabled('.btn-skip-transaction', false);
}

/**
 * Fc: save customer information
 */
function save_customer_information() {
    var customer_name = $('#saving-customer-name').val(),
        transaction_content = $('#saving-transaction-content').val(),
        year = $('#datetime-picker-year-of-birth').val();

    console.info('[Save Customer Information][customer id] ' + current_customer_id);
    console.info('[Save Customer Information][customer name] ' + customer_name);
    console.info('[Save Customer Information][gender] ' + current_gender);
    console.info('[Save Customer Information][year] ' + year);
    console.info('[Save Customer Information][transaction content] ' + transaction_content);
    console.info('[Save Customer Information][customer code] ' + current_customer_code);

    //call ajax save customer information
    ajax_save_customer_information(customer_name, year, transaction_content);
}

/**
 * Fc: ajax save customer information
 * @param customer_name
 * @param year
 * @param transaction_content
 */
function ajax_save_customer_information(customer_name, year, transaction_content) {
    var urlString = '/api/emotion/update_customer_infor',
        customerTransactionModel = {
            'id': current_customer_id,
            'name': customer_name,
            'code': '',
            'yearBirth': year,
            'gender': current_gender,
            'description': transaction_content,
            'content': '',
            'customerCode': current_customer_code
        };
    $.ajax({
        type: 'POST',
        url: urlString,
        data: customerTransactionModel,
        success: function (response) {
            var success = response.success
            console.info('[Save Customer Information][success] ' + success);
            if (success) {
                var customerId = response.data.customerId;
                console.info('[Save Customer Information][customerId] ' + customerId);

                //set current customer id
                current_customer_id = customerId;

                //show message saving success
                event_show('#div-message-saving-success');
            } else {
                //hide message saving success
                event_hide('#div-message-saving-success');
            }
        }
    });
}

/**
 * Event: disabled/enabled
 * @param id
 * @param isDisabled
 */
function event_disabled(id, isDisabled) {
    $(id).prop('disabled', isDisabled);
}

/**
 * Event: hide
 * @param id
 */
function event_hide(id) {
    $(id).hide();
}

/**
 * Event: show
 * @param id
 */
function event_show(id) {
    $(id).show();
}

/**
 * Event: set source image
 * @param id
 * @param src
 */
function setSrcImage(id, src) {
    $(id).attr('src', src);
}

/**
 * function: get angle
 * @param degrees
 * @returns {number}
 */
function getAngle(degrees) {
    nextAngle += degrees;
    if (nextAngle >= 360) {
        nextAngle = 0;
    }
    return nextAngle;
}

/**
 * function: rotate right
 * @param id
 * @param degrees
 */
function rotateRight(id_image, degrees) {
    $(id_image).rotate(getAngle(degrees));
}

/**
 * function: reset next angle = 0
 * @param id_image
 */
function resetNextAngle(id_image) {
    nextAngle = 0;
    //rotate right image 0 degree
    // rotateRight(id_image, 0);
}

/**
 * function: vote suggestion
 * @param id contentId
 */
function vote_suggestion(id) {
    var urlString = '/api/emotion/vote?content_id=' + id;
    console.info('[Function voteSuggestion] contentId: ' + id);
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[Function voteSuggestion] success: ' + success);
            if (success) {
                set_vote(id);
            } else {
                console.info('[Function voteSuggestion] error: ' + response.data);
            }
        }
    });
}

/**
 * Fc: reset scroll
 * @param id
 */
function reset_scroll(id) {
    $(id).scrollTop(0);
}

/**
 * Fc: close modal customer emotion
 * @param isClose
 */
function close_modal_customer_emotion(isClose) {
    if (isClose) {
        //hide div overview customer emotion
        event_hide('#div-overview-customer-emotion');
        //hide div add customer information
        event_hide('#div-add-customer-information');
    } else {
        //show div overview customer emotion
        event_show('#div-overview-customer-emotion');
        //show div add customer information
        event_show('#div-add-customer-information');
        //reset scroll
        reset_scroll('#div-body-scroll-customer-emotion');
    }
}

function prevent_keydown_saving_customer_information() {
    var prevent_customer_name = prevent_keydown('#saving-customer-name');
    var prevent_year_of_birth = prevent_keydown('#datetime-picker-year-of-birth');
    var prevent_transaction_content = prevent_keydown('#saving-transaction-content');
    return (prevent_customer_name && prevent_year_of_birth && prevent_transaction_content);
}

function prevent_keydown(id) {
    var isPrevent = true;
    $(id).keydown(function (event) {
        var keyCode = (event.keyCode ? event.keyCode : event.which);
        if (keyCode == 13) {
            isPrevent = false;
        }
    });
    return isPrevent;
}

/**
 * Fc: select gender updating
 * @param gender
 */
function select_gender(gender) {
    var $btn_gender_male = $('#btn-gender-male'),
        $btn_gender_female = $('#btn-gender-female');
    if (gender == 1) {//female
        $btn_gender_female.attr('class', 'btn bg-aqua-gradient');
        $btn_gender_male.attr('class', 'btn btn-default');

        //set current gender updating
        current_gender = 1;
    } else {//male
        $btn_gender_male.attr('class', 'btn bg-aqua-gradient');
        $btn_gender_female.attr('class', 'btn btn-default');

        //set current gender updating
        current_gender = 0;
    }
}

/**
 * Fc: set content age_predict
 * @param age_predict
 */
function set_age_predict(age_predict, customerInformation) {
    var customerId = customerInformation.id;
    var $font_age_predict = $('#font-age-predict'),
        $label_age_predict = $('#label-age-predict'),
        content_label_age_predict = '',
        content_age_predict = '';

    if (check_empty(customerId)) {//if is old customer
        var yearBirth = customerInformation.yearBirth;
        content_label_age_predict = 'Tuổi:';
        content_age_predict = yearBirth;
    } else {//if is new customer
        content_label_age_predict = 'Tuổi khuôn mặt:';
        if (age_predict != null) {
            content_age_predict = age_predict;
        } else {
            content_age_predict = 'N/A';
        }
    }
    //set content html
    $label_age_predict.html(content_label_age_predict);
    $font_age_predict.html(content_age_predict);
}

/**
 * Fc: set content gender
 * @param gender
 */
function set_gender(gender, customerInformation) {
    var customerId = customerInformation.id;
    var $font_gender = $('#font-gender');
    if (check_empty(customerId)) {//if is old customer
        var gender_customer = customerInformation.gender;
        if (gender_customer == 0) {
            $font_gender.html('Nam');
        } else {
            $font_gender.html('Nữ');
        }
    } else {//if is new customer
        if (gender == 0) {
            $font_gender.html('Nam');
        } else {
            $font_gender.html('Nữ');
        }
    }
}

/**
 * Fc: set content image customer
 * @param imageByte
 */
function set_image_customer(awsUrl) {
    if (awsUrl != null) {
        //set link image
        setSrcImage('#image-customer', awsUrl);
    } else {
        //hide div image customer
        event_hide('#div-image-customer');
        //hide div rotate image
        event_hide('#div-rotate-image');
        //show div preloader image
        event_show('#div-preloader-image');

        //call request: get image (byte)
        worker_get_image();
    }
}

/**
 * Fc: set content ratios emotion
 * @param emotionExist
 */
function set_ratios_emotion(emotionExist) {
    var $font_ratios_emotion = $('#font-ratios-emotion'),
        content_ratios_emotion = '',
        formt_number;
    //set content
    if (emotionExist != null && emotionExist.length > 0) {
        for (var i = 0; i < emotionExist.length; i++) {
            formt_number = Math.round(emotionExist[i].percent);
            content_ratios_emotion += formt_number + '% ' +
                emotionExist[i].emotionName;
            if (i != (emotionExist.length - 1)) {
                content_ratios_emotion += ', ';
            }
        }
    }
    $font_ratios_emotion.html(content_ratios_emotion);
}

/**
 * Fc: set content customer_emotion_message
 * @param customer_emotion_msg
 */
function set_customer_emotion_message(customer_emotion_msg) {
    var ul_content_customer_emotion = '',
        $customer_emotion_msg = $('#customer-emotion-message');
    if (customer_emotion_msg != null && customer_emotion_msg.length > 0) {
        for (var i = 0; i < customer_emotion_msg.length; i++) {
            ul_content_customer_emotion += customer_emotion_msg[i];
        }
    } else {
        ul_content_customer_emotion += 'N/A';
    }
    $customer_emotion_msg.html(ul_content_customer_emotion);
}

/**
 * Fc: set content suggest_behaviour
 * @param suggestions
 */
function set_suggest_behaviour(suggestions, customerHistory) {
    var ul_content_suggestion_behavior = '',
        $suggestion_behavior_msg = $('#suggestion-behavior-message');
    //set history if is old customer
    if (customerHistory != null && customerHistory.length > 0) {
        for (var i = 0; i < customerHistory.length; i++) {
            ul_content_suggestion_behavior += '<li>' +
                customerHistory[i] +
                '</li>';
        }
    }
    //set suggestion
    if (suggestions != null && suggestions.length > 0) {
        for (var i = 0; i < suggestions.length; i++) {
            ul_content_suggestion_behavior += '<li>' +
                suggestions[i].message +
                '</li>';
        }
    } else {
        ul_content_suggestion_behavior += '<li>N/A</li>';
    }
    $suggestion_behavior_msg.html(ul_content_suggestion_behavior);
}

/**
 * Fc: set content overview_customer_emotion
 * @param age_predict
 * @param gender
 * @param imageByte
 * @param customer_emotion_msg
 * @param suggestions
 */
function set_content_overview_customer_emotion(age_predict, gender, awsUrl, emotionExist, customer_emotion_msg, suggestions, customerInformation, customerHistory) {
    //set age predict
    set_age_predict(age_predict, customerInformation);

    //set gender
    set_gender(gender, customerInformation);

    //set image
    set_image_customer(awsUrl);

    //reset next angle
    // resetNextAngle('#image-customer');
    resetNextAngle('#image-customer');
    //rotate image right 90
    rotateRight('#image-customer', 90);

    //set ratios emotion
    set_ratios_emotion(emotionExist);

    //set customer emotion message
    set_customer_emotion_message(customer_emotion_msg);

    //set suggestion behavior
    set_suggest_behaviour(suggestions, customerHistory);
}

/**
 * Fc: set content adding customer emotion
 * @param gender
 */
function set_content_add_customer_emotion(customerInformation) {
    var id = customerInformation.id,
        gender = customerInformation.gender,
        name = customerInformation.name,
        yearBirth = customerInformation.yearBirth,
        description = customerInformation.description;
    var $customer_name = $('#saving-customer-name'),
        $transaction_content = $('#saving-transaction-content'),
        $year = $('#datetime-picker-year-of-birth'),
        $btn_gender_male = $('#btn-gender-male'),
        $btn_gender_female = $('#btn-gender-female');

    //set content
    $customer_name.val(name);
    $year.val(yearBirth);
    $transaction_content.val(description);
    //set value datetime-picker
    // set_value_date_picker('#datetime-picker-year-of-birth', yearBirth);

    //set gender
    if (gender == 1) {//female
        $btn_gender_female.attr('class', 'btn bg-aqua-gradient');
        $btn_gender_male.attr('class', 'btn btn-default');
    } else {//male
        $btn_gender_male.attr('class', 'btn bg-aqua-gradient');
        $btn_gender_female.attr('class', 'btn btn-default');
    }

    //set current customer id
    current_customer_id = id;

    //set current gender updating
    current_gender = gender;

    //hide message saving success
    event_hide('#div-message-saving-success');
}

/**
 * function: set content span_vote
 * @param id
 */
function set_vote(id) {
    var $span_vote = $('#span-vote-' + id),
        $btn_vote = $('#btn-vote-' + id),
        vote = parseInt($span_vote.text());
    vote += 1;
    $span_vote.html(vote);
    //disabled button vote
    $btn_vote.prop('disabled', true);
}

/**
 * Fc: set value datetime-picker
 * @param id
 * @param text
 */
function set_value_date_picker(id, text) {
    $(id).datepicker('setDate', new Date(text));
}
