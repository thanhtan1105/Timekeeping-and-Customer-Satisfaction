/**
 * Created by TrungNN on 10/24/2016.
 */

var total_pages = 0;
var current_index_page;
var first_page = false;
var last_page = false;
var deleted_reminder_id;
var current_page_size = 10;

/**
 * Fc: load list reminders by index page
 * @param index
 */
function load_list_reminders(index, page_size) {
    var managerId = $('#text-managerId').val(),
        title = $('#text-search-value').val(),
        urlString = '/api/reminder/search?managerId=' + managerId +
            '&title=' + title +
            '&start=' + index +
            '&top=' + page_size,
        $tbody_list_reminders = $('#tbody-list-reminders');
    console.info('[title] ' + title);

    //set current page size
    current_page_size = page_size;

    //call ajax getting list reminders
    ajax_get_list_reminders(urlString, 'GET', index, $tbody_list_reminders);
}

/**
 * Fc: load next page when click next
 */
function load_next_page() {
    //check if is last page
    if (last_page) {
        //do nothing
    } else {
        //current index page + 1
        ++current_index_page;
        //reload list reminders
        load_list_reminders(current_index_page, current_page_size);
    }
}

/**
 * Fc: load previous page when click previous
 */
function load_previous_page() {
    //check if is first page
    if (first_page) {
        //do nothing
    } else {
        //current index page - 1
        --current_index_page;
        //reload list reminders
        load_list_reminders(current_index_page, current_page_size);
    }
}

/**
 * Fc: set content for table list of reminders
 * @param list_reminders
 * @param $tbody_list_reminders
 */
function set_list_reminders(list_reminders, $tbody_list_reminders) {
    var time_reminder,
        content_list_reminders = '',
        content_btn_delete;
    for (var i = 0; i < list_reminders.length; i++) {
        time_reminder = new Date(list_reminders[i].time);
        console.info(time_reminder);
        //check (time_reminder - current_time) <= 0 (disabled)
        if (difference_date(time_reminder) <= 0) {
            //set row
            content_list_reminders += '<tr style="background-color: #dddddd">';
            //set btn delete
            content_btn_delete = ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Reminder" onclick="confirm_delete(' + list_reminders[i].id + ')" disabled>' +
                '<i class="fa fa-remove"></i>' +
                '</button>';
        } else {
            //set row
            content_list_reminders += '<tr>';
            //set btn delete
            content_btn_delete = ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Reminder" onclick="confirm_delete(' + list_reminders[i].id + ')">' +
                '<i class="fa fa-remove"></i>' +
                '</button>';
        }
        content_list_reminders += '<td>' + formatDate(time_reminder) + '</td>' +
            '<td id="td-title-' + list_reminders[i].id + '">' + list_reminders[i].title + '</td>' +
            '<td>' + list_reminders[i].message + '</td>' +
            '<td>' + list_reminders[i].room + '</td>' +
            '<td>' +
            '<button class="btn btn-success btn-flat btn-sm" type="button" title="View Reminder" onclick="view_reminder(' + list_reminders[i].id + ')">' +
            '<i class="fa fa-eye"></i>' +
            '</button>' +
            content_btn_delete +
            '</td>' +
            '</tr>' +
            '<input type="hidden" id="text-time-reminder-' + list_reminders[i].id + '" value="' + list_reminders[i].time + '"/>';
    }
    //set content html
    $tbody_list_reminders.html(content_list_reminders);
}

/**
 * Fc: set content for pagination
 * @param total_pages
 * @param $footer_pagination
 */
function set_pagination(total_pages, $footer_pagination) {
    var content_pagination = '',
        content_list_pages = '',
        content_previous_page,
        content_next_page,
        count_page = 0;
    for (var i = 0; i < total_pages; i++) {
        if (current_index_page == i) {
            content_list_pages += '<li class="active">';
        } else {
            content_list_pages += '<li>';
        }
        content_list_pages += '<a href="#" onclick="load_list_reminders(' + i + ', ' + current_page_size + ')">' + (++count_page) + '</a></li>';
    }
    //check if is first page
    if (first_page) {
        content_previous_page = '<li class="disabled"><a onclick="load_previous_page()">&laquo;</a></li>';
    } else {
        content_previous_page = '<li><a href="#" onclick="load_previous_page()">&laquo;</a></li>';
    }
    //check if is last page
    if (last_page) {
        content_next_page = '<li class="disabled"><a onclick="load_next_page()">&raquo;</a></li>';
    } else {
        content_next_page = '<li><a href="#" onclick="load_next_page()">&raquo;</a></li>';
    }
    //set content pagination
    content_pagination += '<ul class="pagination pagination-sm no-margin">' +
        content_previous_page +
        content_list_pages +
        content_next_page +
        '</ul>';
    //set content html
    $footer_pagination.html(content_pagination);
}

/**
 * Fc: ajax get list of reminders
 * @param urlString
 * @param method
 * @param index
 * @param $tbody_list_reminders
 */
function ajax_get_list_reminders(urlString, method, index, $tbody_list_reminders) {
    $.ajax({
        type: method,
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    list_reminders = data.content,
                    $footer_pagination = $('#footer-pagination');

                total_pages = data.totalPages;
                first_page = data.first;
                last_page = data.last;
                console.info('[total pages] ' + total_pages);
                console.info('[first pages] ' + first_page);
                console.info('[last pages] ' + last_page);

                //set current index page: first page
                current_index_page = index;

                //set list reminders
                set_list_reminders(list_reminders, $tbody_list_reminders);

                //set pagination
                set_pagination(total_pages, $footer_pagination);
            }
        }
    });
}

/**
 * Fc: confirm delete
 * @param id
 */
function confirm_delete(id) {
    var reminder_title = $('#td-title-' + id).text(),
        $b_reminder_title = $('#b-reminder-title');
    console.info('[reminder title] ' + reminder_title);

    deleted_reminder_id = id;
    console.info('[deleted reminder id] ' + deleted_reminder_id);

    $b_reminder_title.html('"' + reminder_title + '"');
    show_modal('#modal-confirm-delete', 'true');
}

/**
 * Fc: delete reminder
 */
function delete_reminder() {
    var urlString = '/api/reminder/delete?reminderId=' + deleted_reminder_id;
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                //reload list reminders
                load_list_reminders(current_index_page, current_page_size);
                //show modal result success
                show_modal('#modal-result-success', 'true');
            }
        }
    });
}

/**
 * Fc: enable to show modal
 * @param id
 * @param enabled ('true', 'false')
 */
function show_modal(id, enabled) {
    $(id).modal({
        show: enabled
    });
}

/**
 * Fc: view reminder
 * @param id
 */
function view_reminder(id) {
    var $form_submit_view_reminder = $('#form-submit-view-reminder'),
        reminderId = $form_submit_view_reminder.find('[name="reminderId"]');
    console.info('[id] ' + id);
    console.info('[text time reminder] ' + $text_time_reminder);

    //check (time_reminder - current_time) >= 15 minutes
    var time_reminder = new Date(parseInt($text_time_reminder));
    var current_time = new Date();
    console.info('[time reminder] ' + time_reminder);
    console.info('[current time] ' + current_time);
    var difference_time = time_reminder - current_time;
    console.info('[difference time] ' + difference_time);
    if (difference_time >= time_out_reminder) {
        reminderId.val(id);

        //submit form
        $form_submit_view_reminder.submit();
    }
}

/**
 * Fc: time_reminder - current_time
 * @param time_reminder
 * @returns {number}
 */
function difference_date(time_reminder) {
    return time_reminder - new Date();
}

/**
 * Event: click button search
 */
$('#btn-search-reminder').on('click', function () {
    load_list_reminders(0, current_page_size);
});

/**
 * Event: click show entries
 */
$('#select-entries').on('change', function () {
    var entries = $(this).val();
    load_list_reminders(0, entries);
});


