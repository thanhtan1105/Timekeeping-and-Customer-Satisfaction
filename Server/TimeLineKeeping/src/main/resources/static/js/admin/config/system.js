/**
 * Created by TrungNN on 11/14/2016.
 */

function load_page() {
    //
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
                //
            } else {
                //
            }
        }
    });
}

function set_content_list_config(data) {
    var sendSMS = data.sendSMS,
        emotionAccept = data.emotionAccept,
        emailCompay = data.emailCompay,
        checkinConfident = data.checkinConfident,
        timeCheckinBegin = data.timeCheckinBegin,
        timeCheckinEnd = data.timeCheckinEnd;

}
