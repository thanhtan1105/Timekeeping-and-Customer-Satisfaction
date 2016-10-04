/**
 * Created by TrungNN on 10/4/2016.
 */

/**
 * Event: begin transaction
 */
$('#btn-begin-transaction').on('click', function () {
    worker_begin_transaction();
});

var timer_begin_transaction;
var timer_get_emotion;
var customerCode;

/**
 * Event: end transaction
 */
$('#btn-end-transaction').on('click', function () {
    clearTimeout(timer);
});

function worker_begin_transaction() {
    var formDataJson = {
        'employeeId': 4
    };
    $.ajax({
        type: "POST",
        url: '/api/emotion/employee/customer_emotion/begin_transaction',
        data: formDataJson,
        success: function (response) {
            if (response.success) {
                customerCode = response.data;
                $('#result').html(customerCode);
                if (customerCode != null) {
                    clearTimeout(timer_begin_transaction);
                    worker_get_emotion();
                }
            } else {
                alert(response.data);
            }
        }
    });
    timer_begin_transaction = setTimeout(worker_begin_transaction, 5000);
};

function worker_get_emotion() {
    var formDataJson = {
        'customerCode': customerCode
    };
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
                    content = '';
                content += 'anger: ' + anger;
                content += ' - contempt: ' + contempt;
                content += ' - disgust: ' + disgust;
                content += ' - fear: ' + fear;
                content += ' - happiness: ' + happiness;
                content += ' - happiness: ' + happiness;
                content += ' - neutral: ' + neutral;
                content += ' - sadness: ' + sadness;
                content += ' - surprise: ' + surprise;
                content += ' - age: ' + age;
                content += ' - gender: ' + gender;
                content += ' - emotionMost: ' + emotionMost;
                $('#customer-emotion').html(content);
                clearTimeout(timer_get_emotion);
            }
        }
    });
    timer_get_emotion = setTimeout(worker_get_emotion, 5000);
};

