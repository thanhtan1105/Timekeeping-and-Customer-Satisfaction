/**
 * Created by TrungNN on 11/2/2016.
 */

var resultSearch;
var total_pages = 0;
var current_index_page;
var first_page = false;
var last_page = false;
var current_page_size = 10;
var current_search_value;
var deleted_department_id;
var viewed_department_id;

/**
 * Fc: load list accounts by index page
 * @param search_value
 * @param index
 * @param page_size
 */
function load_list_accounts(search_value, index, page_size) {
    //parser search value
    var groups = search_value.split('-'),
        name,
        code;
    if (groups != null) {
        if (groups.length != 1) {
            // name = groups[0].trim();
            code = groups[1].trim();
        } else {
            code = groups;
            name = groups;
        }
    }
    console.info('[name] ' + name);
    console.info('[code] ' + code);

    //set current search value
    current_search_value = search_value;

    var urlString = '/api/account/list?start=' + index +
            '&top=' + page_size,
        $tbody_list_accounts = $('#tbody-list-accounts');

    //set current page size
    current_page_size = page_size;

    //call ajax getting list accounts
    ajax_get_list_accounts(urlString, 'GET', index, $tbody_list_accounts);
}

/**
 * Fc: ajax get list of account
 * @param urlString
 * @param method
 * @param index
 * @param $tbody_list_accounts
 */
function ajax_get_list_accounts(urlString, method, index, $tbody_list_accounts) {
    $.ajax({
        type: method,
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    list_accounts = data.content,
                    $footer_pagination = $('#footer-pagination');

                total_pages = data.totalPages;
                first_page = data.first;
                last_page = data.last;
                console.info('[total pages] ' + total_pages);
                console.info('[first pages] ' + first_page);
                console.info('[last pages] ' + last_page);

                //set current index page: first page
                current_index_page = index;

                //set list account
                set_list_accounts(list_accounts, $tbody_list_accounts);

                //set pagination
                set_pagination(total_pages, $footer_pagination);
            }
        }
    });
}

/**
 * Fc: set content for table list of account
 * @param list_accounts
 * @param $tbody_list_accounts
 */
function set_list_accounts(list_accounts, $tbody_list_accounts) {
    var content_list_accounts = '';
    for (var i = 0; i < list_accounts.length; i++) {
        content_list_accounts += '<tr>' +
            '<td>' + list_accounts[i].fullname + '</td>' +
            '<td>' + list_accounts[i].username + '</td>' +
            '<td>' + list_accounts[i].role.name + '</td>' +
            '<td>' + list_accounts[i].department.name + '</td>' +
            '<td>' + set_status(list_accounts[i].active) + '</td>' +
            '<td>' +
            '<button class="btn btn-success btn-flat btn-sm" type="button" title="View Account" onclick="view(' + list_accounts[i].id + ')">' +
            '<i class="fa fa-eye"></i>' +
            '</button>' +
            ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Account" onclick="confirm_delete(' + list_accounts[i].id + ')">' +
            '<i class="fa fa-remove"></i>' +
            '</button>' +
            '</td>' +
            '</tr>';
    }
    //set content html
    $tbody_list_accounts.html(content_list_accounts);
}

/**
 * Fc: set status
 * @param status
 * @returns {*}
 */
function set_status(status) {
    if (status == 1) {
        return 'Activated';
    } else {
        return 'Deactivated';
    }
}