/**
 * Created by TrungNN on 9/17/2016.
 */

var resultSearch;
var total_pages = 0;
var current_index_page;
var first_page = false;
var last_page = false;
var current_page_size = 10;
var current_search_value;
var deleted_reminder_id;

$.mockjax({
    url: '/departments/list',
    response: function (query) {
        var searchValue = query.data.query;
        console.info('query: ' + searchValue);
        var urlString = '/api/department/search?code=' + searchValue
            + '&name=' + searchValue;
        $.ajax({
            type: "GET",
            url: urlString,
            success: function (response) {
                var success = response.success;
                if (success) {
                    var departmentList = response.data.content;
                    resultSearch = new Array();
                    if (departmentList != null) {
                        for (var i = 0; i < departmentList.length; i++) {
                            console.info('department: ' + departmentList[i].name);
                            resultSearch.push({name: departmentList[i].name + ' - ' + departmentList[i].code});
                        }
                    }
                }
            }
        });
        this.responseText = resultSearch;
    }
});

/**
 * Autocomplete
 */
$('#input-search-department').typeahead({
    ajax: {
        url: '/departments/list',
        triggerLength: 2
    }
});

/**
 * Fc: load list departments by index page
 * @param index
 */
function load_list_departments(search_value, index, page_size) {
    //parser search value
    var groups = search_value.split('-'),
        code,
        name;
    if (groups != null) {
        if (groups.length != 1) {
            code = groups[0].trim();
            name = groups[1].trim();
        } else {
            code = groups;
            name = groups;
        }
    }
    console.info('[name] ' + name);
    console.info('[code] ' + code);

    //set current search value
    current_search_value = search_value;

    var urlString = '/api/department/search?code=' + code +
            '&name=' + name +
            '&start=' + index +
            '&top=' + page_size,
        $tbody_list_departments = $('#tbody-list-departments');

    //set current page size
    current_page_size = page_size;

    //call ajax getting list departments
    ajax_get_list_departments(urlString, 'GET', index, $tbody_list_departments);
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
        //reload list departments
        load_list_departments(current_search_value, current_index_page, current_page_size);
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
        load_list_departments(current_search_value, current_index_page, current_page_size);
    }
}

/**
 * Fc: ajax get list of departments
 * @param urlString
 * @param method
 * @param index
 * @param $tbody_list_departments
 */
function ajax_get_list_departments(urlString, method, index, $tbody_list_departments) {
    $.ajax({
        type: method,
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    list_departments = data.content,
                    $footer_pagination = $('#footer-pagination');

                total_pages = data.totalPages;
                first_page = data.first;
                last_page = data.last;
                console.info('[total pages] ' + total_pages);
                console.info('[first pages] ' + first_page);
                console.info('[last pages] ' + last_page);

                //set current index page: first page
                current_index_page = index;

                //set list departments
                set_list_departments(list_departments, $tbody_list_departments);

                //set pagination
                set_pagination(total_pages, $footer_pagination);
            }
        }
    });
}

/**
 * Fc: set content for table list of departments
 * @param list_departments
 * @param $tbody_list_departments
 */
function set_list_departments(list_departments, $tbody_list_departments) {
    var content_list_departments = '';
    for (var i = 0; i < list_departments.length; i++) {
        content_list_departments += '<tr>' +
            '<td>' + list_departments[i].name + '</td>' +
            '<td>' + list_departments[i].code + '</td>' +
            '<td>' + list_departments[i].description + '</td>' +
            '<td>' +
            '<button class="btn btn-success btn-flat btn-sm" type="button" title="View Reminder">' +
            '<i class="fa fa-eye"></i>' +
            '</button>' +
            ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Reminder">' +
            '<i class="fa fa-remove"></i>' +
            '</button>' +
            '</td>' +
            '</tr>';
    }
    //set content html
    $tbody_list_departments.html(content_list_departments);
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
        content_list_pages += '<a href="#" onclick="load_list_departments(current_search_value,' + i + ', ' + current_page_size + ')">' + (++count_page) + '</a></li>';
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
 * Event: click button search
 */
$('#btn-search-department').on('click', function () {
    var search_value = $('#input-search-department').val();

    //reload list departments
    load_list_departments(search_value, 0, current_page_size);
});
