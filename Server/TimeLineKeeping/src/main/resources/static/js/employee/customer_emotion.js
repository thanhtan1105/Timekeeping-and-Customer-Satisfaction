/**
 * Created by TrungNN on 10/4/2016.
 */

/**
 * Data bar chart
 * @type {{data: *[emotion ratios], color: string}}
 */
var bar_data = {
    data: [["Anger", 0.0001], ["Contempt", 0.0005], ["Disgust", 0.00001], ["Fear", 0.00003],
        ["Happiness", 0.9], ["Neutral", 0.1], ["Sadness", 0.00002], ["Surprise", 0.0002]],
    color: "#3c8dbc"
};

/**
 * Init bar chart
 */
function load_bar_chart() {
    $.plot("#bar-chart", [bar_data], {
        grid: {
            borderWidth: 1,
            borderColor: "#f3f3f3",
            tickColor: "#f3f3f3"
        },
        series: {
            bars: {
                show: true,
                barWidth: 0.5,
                align: "center"
            }
        },
        xaxis: {
            mode: "categories",
            tickLength: 0
        }
    });
}

/**
 * Event: begin transaction
 */
$('#btn-begin-transaction').on('click', function () {
    //enable button end
    event_disabled('#btn-end-transaction', false);
    //disable button begin
    event_disabled('#btn-begin-transaction', true);
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
                $('#result').html(customerCode);
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
    timer_begin_transaction = setTimeout(worker_begin_transaction, 5000);
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

                //set data bar chart
                bar_data = {
                    data: [["Anger", anger], ["Contempt", contempt], ["Disgust", disgust], ["Fear", fear],
                        ["Happiness", happiness], ["Neutral", neutral], ["Sadness", sadness], ["Surprise", surprise]],
                    color: "#3c8dbc"
                };
                load_bar_chart();

                //stop request: get first emotion
                clearTimeout(timer_get_emotion);
            }
        }
    });
    timer_get_emotion = setTimeout(worker_get_emotion, 5000);
};

function event_disabled(id, isDisabled) {
    $(id).prop('disabled', isDisabled);
}

