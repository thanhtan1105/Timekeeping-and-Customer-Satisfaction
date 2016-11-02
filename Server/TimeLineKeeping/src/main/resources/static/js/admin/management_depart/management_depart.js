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
var deleted_department_id;
var viewed_department_id;

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
        //reload list departments
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
            '<td id="td-name-' + list_departments[i].id + '">' + list_departments[i].name + '</td>' +
            '<td id="td-code-' + list_departments[i].id + '">' + list_departments[i].code + '</td>' +
            '<td>' + list_departments[i].description + '</td>' +
            '<td>' +
            '<button class="btn btn-success btn-flat btn-sm" type="button" title="View Department" onclick="view(' + list_departments[i].id + ')">' +
            '<i class="fa fa-eye"></i>' +
            '</button>' +
            ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Delete Department" onclick="confirm_delete(' + list_departments[i].id + ')">' +
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
 * Fc: set content modal view
 * @param code
 * @param name
 * @param description
 */
function set_content_modal_view(code, name, description) {
    var $viewing_code = $('#viewing_code'),
        $viewing_name = $('#viewing_name'),
        $viewing_description = $('#viewing_description');

    //set content
    $viewing_code.html(code);
    $viewing_name.html(name);
    $viewing_description.html(description);
}

/**
 * Fc: set content modal update
 * @param code
 * @param name
 * @param description
 * @param active
 * @param status
 */
function set_content_modal_update(code, name, description, active, status) {
    var $form_edit_department = $('#form-edit-department'),
        $code = $form_edit_department.find('[name="code"]'),
        $name = $form_edit_department.find('[name="name"]'),
        $description = $form_edit_department.find('[name="description"]'),
        $active = $form_edit_department.find('[name="active"]'),
        $status = $form_edit_department.find('[name="status"]');

    //set content
    $code.val(code);
    $name.val(name);
    $description.html(description);
    $active.html(active);
    $status.html(status);
}

/**
 * Fc: view department information
 * @param id
 */
function view(id) {
    viewed_department_id = id;
    console.info('[viewed department id] ' + viewed_department_id);

    var urlString = '/api/department/get?id=' + viewed_department_id;
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    code = data.code,
                    name = data.name,
                    description = data.description;

                //set content modal view
                set_content_modal_view(code, name, description);
                //show modal view department
                show_modal('#modal-view-department', false);
            }
        }
    });
}

/**
 * Fc: load update view
 */
function update() {
    console.info('[update]');
    console.info('[viewed department id] ' + viewed_department_id);

    var urlString = '/api/department/get?id=' + viewed_department_id;
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    code = data.code,
                    name = data.name,
                    description = data.description,
                    active = data.active,
                    status = data.status;

                //set content modal update
                set_content_modal_update(code, name, description, active, status);
                //show modal update department
                show_modal('#modal-update-department', false);
            }
        }
    });
}

/**
 * Fc: process updating
 */
function update_processing() {
    var $form_edit_department = $('#form-edit-department'),
        code = $form_edit_department.find('[name="code"]').val(),
        name = $form_edit_department.find('[name="name"]').val(),
        description = $form_edit_department.find('[name="description"]').val(),
        department = {
            'id': viewed_department_id,
            'code': code,
            'name': name,
            'description': description
        };
    $.ajax({
        type: 'POST',
        url: '/api/department/update',
        data: department,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                //hide modal update department
                close_modal('#modal-update-department');

                //reload view
                back_to_view();
                //reload list departments
                load_list_departments(current_search_value, current_index_page, current_page_size);
            } else {
                //do nothing
            }
        }
    });
}

/**
 * Fc: back to view modal
 */
function back_to_view() {
    //reload view
    view(viewed_department_id);
}

/**
 * Fc: confirm delete
 * @param id
 */
function confirm_delete(id) {
    var department_name = $('#td-name-' + id).text(),
        department_code = $('#td-code-' + id).text(),
        $b_department_name_code = $('#b-department-name-code');
    console.info('[department name] ' + department_name);
    console.info('[department code] ' + department_code);

    deleted_department_id = id;
    console.info('[deleted department id] ' + deleted_department_id);

    $b_department_name_code.html('"' + department_name + ' - ' + department_code + '"');
    show_modal('#modal-confirm-delete', false);
}

/**
 * Fc: delete department
 */
function delete_department() {
    var urlString = '/api/department/delete?id=' + deleted_department_id;
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                //reload list departments
                load_list_departments(current_search_value, current_index_page, current_page_size);
                //show modal result success
                show_modal('#modal-result-success', true);
            }
        }
    });
}

/**
 * Fc: show modal
 * @param id
 * @param enabled ('true', 'false')
 */
function show_modal(id, keyboard) {
    if (!keyboard) {//prevent closing
        $(id).modal({
            backdrop: 'static',
            keyboard: keyboard, //(false) prevent closing with Esc button
            show: true
        });
    } else {//allow closing
        $(id).modal({
            show: true
        });
    }

}

/**
 * Fc: close modal
 * @param id
 */
function close_modal(id) {
    $(id).modal('toggle');
}

/**
 * Event: click button search
 */
$('#btn-search-department').on('click', function () {
    var search_value = $('#input-search-department').val();

    //reload list departments
    load_list_departments(search_value, 0, current_page_size);
});

/**
 * Event: click show entries
 */
$('#select-entries').on('change', function () {
    var entries = $(this).val();

    //reload list departments
    load_list_departments(current_search_value, 0, entries);
});
