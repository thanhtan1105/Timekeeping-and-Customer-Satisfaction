/**
 * Created by TrungNN on 9/17/2016.
 */

var resultSearch;
var total_pages = 0;
var current_index_page;
var first_page = false;
var last_page = false;
var deleted_reminder_id;
var current_page_size = 10;

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
                // set_pagination(total_pages, $footer_pagination);
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
