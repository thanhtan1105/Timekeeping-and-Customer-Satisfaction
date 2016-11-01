/**
 * Created by TrungNN on 10/4/2016.
 */

var accountId = $('#accountId').val();
var customerCode;
var timer_get_emotion;
var timer_get_image;
var nextAngle = 0;
var isFirst = true;

/**
 * Event: next transaction
 */
$('#btn-next-transaction').on('click', function () {
    //disable button next
    event_disabled('#btn-next-transaction', true);
    //disable button skip
    event_disabled('#btn-skip-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //show div loader
    event_show('#div-loader');

    //call request: next transaction (isSkip == false)
    worker_next_transaction(false);
});

/**
 * Event: skip transaction
 */
$('#btn-skip-transaction').on('click', function () {
    console.info('Running btn skip');
    //disable button next
    event_disabled('#btn-next-transaction', true);
    //disable button skip
    event_disabled('#btn-skip-transaction', true);
    //hide div overview customer emotion
    event_hide('#div-overview-customer-emotion');
    //show div loader
    event_show('#div-loader');

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
                    isFinal = data.final,
                    awsUrl = data.awsUrl,
                    customer_emotion_msg = messages.message,
                    suggestions = messages.sugguest,
                    age_predict = messages.predict,
                    gender = messages.gender;
                console.info('final' + isFinal);

                //hide div loader
                event_hide('#div-loader');
                //show div overview customer emotion
                event_show('#div-overview-customer-emotion');
                //enable button next
                event_disabled('#btn-next-transaction', false);
                //enable button skip
                event_disabled('#btn-skip-transaction', false);

                //set overview customer emotion
                set_content_overview_customer_emotion(age_predict, gender, awsUrl, customer_emotion_msg, suggestions);
                if (isFirst) {
                    //set content
                    //set isFirst = false;

                    //set overview customer emotion
                    set_content_overview_customer_emotion(age_predict, gender, awsUrl, customer_emotion_msg, suggestions);
                    //set isFirst = false
                    isFirst = false;
                } else {
                    //if isFinal --> set content, stop worker, set isFirst = true;
                    //else do nothing
                    if (isFinal) {
                        //set overview customer emotion
                        set_content_overview_customer_emotion(age_predict, gender, awsUrl, customer_emotion_msg, suggestions);

                        //set isFirst = true;
                        isFirst = true;

                        //stop request: get first emotion
                        clearTimeout(timer_get_emotion);
                    } else {
                        //do nothing
                    }
                }
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
    $.ajax({
        type: "GET",
        url: urlString,
        success: function (response) {
            if (response.success) {
                customerCode = response.data;
                if (customerCode != null) {
                    //call request: load page
                    worker_get_emotion();
                }
            } else {
                alert(response.data);
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
 * Fc: set content age_predict
 * @param age_predict
 */
function set_age_predict(age_predict) {
    var $font_age_predict = $('#font-age-predict');
    if (age_predict != null) {
        $font_age_predict.html(age_predict);
    } else {
        $font_age_predict.html('N/A');
    }
}

/**
 * Fc: set content gender
 * @param gender
 */
function set_gender(gender) {
    var $font_gender = $('#font-gender');
    if (gender == 0) {
        $font_gender.html('Nam');
    } else {
        $font_gender.html('Nữ');
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
 * Fc: set content customer_emotion_message
 * @param customer_emotion_msg
 */
function set_customer_emotion_message(customer_emotion_msg) {
    var ul_content_customer_emotion = '',
        $customer_emotion_msg = $('#customer-emotion-message');
    if (customer_emotion_msg != null && customer_emotion_msg.length > 0) {
        for (var i = 0; i < customer_emotion_msg.length; i++) {
            ul_content_customer_emotion += '<li>' +
                customer_emotion_msg[i] +
                '</li>';
        }
    } else {
        ul_content_customer_emotion += '<li>N/A</li>';
    }
    $customer_emotion_msg.html(ul_content_customer_emotion);
}

/**
 * Fc: set content suggest_behaviour
 * @param suggestions
 */
function set_suggest_behaviour(suggestions) {
    var ul_content_suggestion_behavior = '',
        $suggestion_behavior_msg = $('#suggestion-behavior-message');
    if (suggestions != null && suggestions.length > 0) {
        for (var i = 0; i < suggestions.length; i++) {
            ul_content_suggestion_behavior += '<li>' +
                suggestions[i].message +
                '</li>';
            ul_content_suggestion_behavior += '<ul class="list-inline">' +
                '<li>' +
                '<button type="button" class="btn btn-default btn-xs btn-vote" ' +
                'id="btn-vote-' +
                suggestions[i].id +
                '" ' +
                'onclick="vote_suggestion(' +
                suggestions[i].id +
                ')">' +
                '<i class="fa fa-thumbs-o-up margin-r-5"></i> Vote</button>' +
                '</li>' +
                '<li><span class="badge bg-aqua-gradient" id="span-vote-' +
                suggestions[i].id +
                '">' +
                suggestions[i].vote +
                '</span></li>' +
                '</ul>';
            if (i < suggestions.length - 1) {
                ul_content_suggestion_behavior += '<hr/>';
            }
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
function set_content_overview_customer_emotion(age_predict, gender, awsUrl, customer_emotion_msg, suggestions) {
    //set age predict
    set_age_predict(age_predict);

    //set gender
    set_gender(gender);

    //set image
    set_image_customer(awsUrl);

    //reset next angle
    // resetNextAngle('#image-customer');
    resetNextAngle('#image-customer');
    //rotate image right 90
    rotateRight('#image-customer', 90);

    //set customer emotion message
    set_customer_emotion_message(customer_emotion_msg);

    //set suggestion behavior
    set_suggest_behaviour(suggestions);
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
