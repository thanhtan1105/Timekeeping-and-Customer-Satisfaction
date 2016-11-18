/**
 * Created by TrungNN on 11/18/2016.
 */

function submit_form() {
    //reset error (if any)
    reset_content_error_group('div-username');
    reset_content_error_full('#div-password', '#span-msg-error');

    var $form_login = $('#form-login'),
        username = $form_login.find('[name="username"]').val(),
        password = $form_login.find('[name="password"]').val();

    //call ajax check login
    ajax_check_login(username, password, $form_login);
}

function ajax_check_login(username, password, $form_login) {
    console.info('[Check login][username] ' + username);
    console.info('[Check login][password] ' + password);
    var urlString = '/api/account/login',
        data_input = {
            'username': username,
            'password': password
        };
    $.ajax({
        type: 'POST',
        url: urlString,
        data: data_input,
        success: function (response) {
            var success = response.success;
            console.info('[check login][success] ' + success);
            if (success) {
                //submit form
                $form_login.submit();
            } else {
                var message_error = response.message;
                set_content_error_group('#div-username');
                set_content_error_full('#div-password', '#span-msg-error', message_error);
            }
        }
    });
}

/**
 * Fc: set content error
 * @param id_form_group
 * @param id_msg_error
 * @param msg_error
 */
function set_content_error_full(id_form_group, id_msg_error, msg_error) {
    $(id_form_group).attr('class', 'form-group has-error');
    $(id_msg_error).html(msg_error);
}

function set_content_error_group(id_form_group) {
    $(id_form_group).attr('class', 'form-group has-error');
}

/**
 * Fc: reset content error
 * @param id_form_group
 * @param id_msg_error
 */
function reset_content_error_full(id_form_group, id_msg_error) {
    $(id_form_group).attr('class', 'form-group');
    $(id_msg_error).html('');
}

function reset_content_error_group(id_form_group, id_msg_error) {
    $(id_form_group).attr('class', 'form-group');
    $(id_msg_error).html('');
}
