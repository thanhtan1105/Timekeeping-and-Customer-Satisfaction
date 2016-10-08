/**
 * Created by TrungNN on 10/4/2016.
 */

/**
 * Hide: div overview customer emotion
 */
event_hide('#div-overview-customer-emotion');

/**
 * Hide: div loader
 */
event_hide('#div-loader');

/**
 * Event: begin transaction
 */
$('#btn-begin-transaction').on('click', function () {

    //disable button begin
    event_disabled('#btn-begin-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //show div loader
    event_show('#div-loader');
    //request begin transaction
    worker_begin_transaction();
});

var timer_begin_transaction;
var timer_get_emotion;
var customerCode;

/**
 * Event: end transaction
 */
$('#btn-end-transaction').on('click', function () {
    var formDataJson = {
        'customerCode': customerCode
    };
    console.info('[end_transaction] customerCode: ' + customerCode);
    $.ajax({
        type: "POST",
        url: '/api/emotion/employee/customer_emotion/end_transaction',
        data: formDataJson,
        success: function (response) {
            if (response.success) {
                //disable button end
                event_disabled('#btn-end-transaction', true);
                //enable button begin
                event_disabled('#btn-begin-transaction', false);
                //hide div overview customer emotion
                event_hide('#div-overview-customer-emotion');
            }
        }
    });
});

/**
 * Worker: begin transaction
 */
function worker_begin_transaction() {
    var employeeId = $('#employeeId').val(),
        formDataJson = {
            'employeeId': employeeId
        };
    console.info('[worker_begin_transaction] employeeId: ' + employeeId);
    $.ajax({
        type: "POST",
        url: '/api/emotion/employee/customer_emotion/begin_transaction',
        data: formDataJson,
        success: function (response) {
            if (response.success) {
                customerCode = response.data;
                if (customerCode != null) {
                    //stop request: begin transaction
                    clearTimeout(timer_begin_transaction);

                    //call request: get first emotion
                    worker_get_emotion();
                }
            } else {
                alert(response.data);
            }
        }
    });
    timer_begin_transaction = setTimeout(worker_begin_transaction, 2500);
};

/**
 * Worker: get first emotion
 */
function worker_get_emotion() {
    var formDataJson = {
        'customerCode': customerCode
    };
    console.info('[worker_get_emotion] customerCode: ' + customerCode);
    $.ajax({
        type: "POST",
        url: '/api/emotion/employee/customer_emotion/get_emotion',
        data: formDataJson,
        success: function (response) {
            console.info('success: ' + response.success);
            if (response.success) {
                var data = response.data,
                    anger = data.anger,
                    contempt = data.contempt,
                    disgust = data.disgust,
                    fear = data.fear,
                    happiness = data.happiness,
                    neutral = data.neutral,
                    sadness = data.sadness,
                    surprise = data.surprise,
                    age = data.age,
                    gender = data.gender,
                    emotionMost = data.emotionMost,
                    $font_age = $('#font-age'),
                    $font_gender = $('#font-gender');

                //hide div loader
                event_hide('#div-loader');
                //show div overview customer emotion
                event_show('#div-overview-customer-emotion');
                //enable button end
                event_disabled('#btn-end-transaction', false);
                //set age
                $font_age.html(age);
                //set gender
                if (gender == 0) {
                    $font_gender.html('Nam');
                } else {
                    $font_gender.html('Nữ');
                }
                //set image customer
                setSrcImage('#image-customer', '/libs/dist/img/user2-160x160.jpg')

                //stop request: get first emotion
                clearTimeout(timer_get_emotion);
            }
        }
    });
    timer_get_emotion = setTimeout(worker_get_emotion, 2500);
};

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
