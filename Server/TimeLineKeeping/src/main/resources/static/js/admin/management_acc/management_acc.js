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
var viewed_account_id;
var current_gender;
var error_username_exist = false;

/**
 * For load
 */
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
 * Fc: load next page when click next
 */
function load_next_page() {
    //check if is last page
    if (last_page) {
        //do nothing
    } else {
        //current index page + 1
        ++current_index_page;
        //reload list accounts
        load_list_accounts(current_search_value, current_index_page, current_page_size);
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
        //reload list accounts
        load_list_accounts(current_search_value, current_index_page, current_page_size);
    }
}

/**
 * For call ajax
 */
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
            console.info('[get list account][success] ' + success);
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
 * Fc: ajax get list all departments
 */
function ajax_get_list_department() {
    var urlString = '/api/department/list_department';
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success,
                data = null;
            console.info('[get list departments][success] ' + success);
            if (success) {
                data = response.data;
            }

            //set list departments
            set_list_departments(data);
        }
    });
}

/**
 * Fc: ajax get list all roles
 */
function ajax_get_list_role() {
    var urlString = '/api/role/list_role';
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success,
                data = null;
            console.info('[get list roles][success] ' + success);
            if (success) {
                data = response.data;
            }

            //set list roles
            set_list_roles(data);
        }
    });
}

/**
 * Fc: ajax check username exist
 */
function ajax_check_username_exist() {
    var username = $('#input-username').val(),
        urlString = '/api/account/exits_username?username=' + username;
    console.info('[check username exist][username] ' + username);
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[check username exist][success] ' + success);

            //set checking username exist
            set_checking_username_exist(success);
            //set error username exist
            error_username_exist = success;
        }
    });
}

/**
 * Fc: ajax add new processing
 * @param account
 */
function ajax_add_new_processing(account) {
    console.info('[add new processing][account] ' + account);
    $.ajax({
        type: 'POST',
        url: '/api/account/create',
        data: account,
        success: function (response) {
            var success = response.success;
            console.info('[add new processing][success] ' + success);
            if (success) {
                //hide modal add new
                close_modal('#modal-add-new');

                //reload list accounts
                load_list_accounts(current_search_value, current_index_page, current_page_size);
            } else {
                //do nothing
            }
        },
        error: function (response, exception) {
            console.log(response);
            console.log(exception);
        }
    });
}

/**
 * For set
 */
/**
 * Fc: set content for table list of account
 * @param list_accounts
 * @param $tbody_list_accounts
 */
function set_list_accounts(list_accounts, $tbody_list_accounts) {
    var content_list_accounts = '',
        status,
        tr_status,
        btn_activate;
    for (var i = 0; i < list_accounts.length; i++) {
        status = list_accounts[i].active;
        if (status == 1) {
            tr_status = '<tr>';
            btn_activate = ' <button class="btn btn-default btn-flat btn-sm" type="button" title="Deactivate Account">Deactivate</button>';
        } else {
            tr_status = '<tr class="text-muted">';
            btn_activate = ' <button class="btn btn-danger btn-flat btn-sm" type="button" title="Activate Account">Activate</button>';
        }
        content_list_accounts += tr_status +
            '<td>' + list_accounts[i].fullName + '</td>' +
            '<td>' + list_accounts[i].username + '</td>' +
            '<td>' + list_accounts[i].department.name + '</td>' +
            '<td>' + list_accounts[i].role.name + '</td>' +
            '<td>' +
            '<button class="btn btn-success btn-flat btn-sm" type="button" title="View Account" onclick="view(' + list_accounts[i].id + ')">' +
            '<i class="fa fa-eye"></i>' +
            '</button>' +
            btn_activate +
            '</td>' +
            '</tr>';
    }
    //set content html
    $tbody_list_accounts.html(content_list_accounts);
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
        content_list_pages += '<a href="#" onclick="load_list_accounts(current_search_value,' + i + ', ' + current_page_size + ')">' + (++count_page) + '</a></li>';
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

/**
 * Fc: set content modal view
 * @param username
 * @param fullName
 * @param role
 * @param department
 * @param manager
 * @param email
 * @param gender
 * @param phone
 * @param address
 * @param description
 */
function set_content_modal_view(username, fullName, role, department, manager, email, gender, phone, address, description) {
    var $viewing_username = $('#viewing_username'),
        $viewing_fullName = $('#viewing_fullName'),
        $viewing_role = $('#viewing_role'),
        $viewing_department = $('#viewing_department'),
        $viewing_email = $('#viewing_email'),
        $viewing_gender = $('#viewing_gender'),
        $viewing_phone = $('#viewing_phone'),
        $viewing_address = $('#viewing_address'),
        $viewing_description = $('#viewing_description');

    //set content
    $viewing_username.html(username);
    $viewing_fullName.html(fullName);
    $viewing_role.html(role);
    $viewing_department.html(department);
    set_manager(manager);
    $viewing_email.html(set_not_available(email));
    $viewing_gender.html(set_gender(gender));
    $viewing_phone.html(set_not_available(phone));
    $viewing_address.html(set_not_available(address));
    $viewing_description.html(set_not_available(description));
}

/**
 * Fc: set gender
 * @param gender
 * @returns {*}
 */
function set_gender(gender) {
    if (gender == 1) {
        return 'Female';
    } else {
        return 'Male';
    }
}

function set_manager(manager) {
    if (manager == null) {
        //hide div viewing manager
        event_hide('#div-viewing-manager');
    } else {
        var $viewing_manager = $('#viewing_manager');
        //set content
        $viewing_manager.html(manager.fullName);

        //show div viewing manager
        event_show('#div-viewing-manager');
    }
}

/**
 * Fc: set not available
 * @param value
 * @returns {*}
 */
function set_not_available(value) {
    if (value == null || value.trim() == '') {
        return 'N/A';
    } else {
        return value;
    }
}

/**
 * Fc: set list departments
 * @param departments
 */
function set_list_departments(departments) {
    var $select_department = $('.select-department'),
        data = new Array();
    data.push({id: -1, text: '-- Select department --'})

    $('.select-department').select2();
    if (departments != null && departments.length > 0) {
        for (var i = 0; i < departments.length; i++) {
            data.push({id: departments[i].id, text: departments[i].name + ' - ' + departments[i].code});
        }
    }

    //initialize select for department
    $select_department.select2({
        data: data
    });
}

/**
 * Fc: set list roles
 * @param roles
 */
function set_list_roles(roles) {
    var $select_role = $('.select-role'),
        data = new Array();

    $('.select-role').select2();
    if (roles != null && roles.length > 0) {
        for (var i = 0; i < roles.length; i++) {
            data.push({id: roles[i].id, text: roles[i].name});
        }
    }

    //initialize select for role
    $select_role.select2({
        data: data
    });

    //default select employee
    set_value_select('.select-role', 3);
}

/**
 * Fc: set checking username exist
 * @param isExisted
 */
function set_checking_username_exist(isExisted) {
    var $div_username = $('#div-username'),
        $span_message_username = $('#span-message-username');

    if (isExisted) {
        $div_username.attr('class', 'form-group has-error');
        $span_message_username.show();
    } else {
        $div_username.attr('class', 'form-group');
        $span_message_username.hide();
    }
}

/**
 * Fc: get errors
 */
function get_errors() {
    var errors = false;

    if (error_username_exist) {
        errors = true;
    }

    console.info('[get errors] ' + errors);
    return errors;
}

/**
 * For Fc
 */
/**
 * Fc: view department information
 * @param id
 */
function view(id) {
    viewed_account_id = id;
    console.info('[viewed account id] ' + viewed_account_id);

    var urlString = '/api/account/get?accountId=' + viewed_account_id;
    $.ajax({
        type: 'GET',
        url: urlString,
        success: function (response) {
            var success = response.success;
            console.info('[success] ' + success);
            if (success) {
                var data = response.data,
                    username = data.username,
                    fullName = data.fullName,
                    role = data.role.name,
                    department = data.department.name,
                    manager = data.manager,
                    email = data.email,
                    gender = data.gender,
                    phone = data.phone,
                    address = data.address,
                    description = data.description;

                //set content modal view
                set_content_modal_view(username, fullName, role, department, manager, email, gender, phone, address, description);
                //show modal view account
                show_modal('#modal-view-account', false);
            }
        }
    });
}

/**
 * Fc: load update view
 */
function update() {
    console.info('[update]');
    console.info('[viewed account id] ' + viewed_account_id);

    var urlString = '/api/account/get?accountId=' + viewed_account_id;
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
 * Fc: show modal adding new
 */
function add_new() {
    //reset form
    reset_form_add_new();

    //set default gender is male
    select_gender(0);
    //call ajax get list departments
    ajax_get_list_department();
    //call ajax get list roles
    ajax_get_list_role();

    //show modal add new
    show_modal('#modal-add-new', false);
}

/**
 * Fc: add new processing
 */
function add_new_processing() {
    if (get_errors()) {//has errors
        //do nothing
    } else {//has not errors
        var $form_add_new = $('#form-add-new'),
            username = $form_add_new.find('[name="username"]').val(),
            fullname = $form_add_new.find('[name="fullname"]').val(),
            phone = $form_add_new.find('[name="phone"]').val(),
            address = $form_add_new.find('[name="address"]').val(),
            department = $form_add_new.find('[name="department"]').val(),
            role = $form_add_new.find('[name="role"]').val(),
            note = $form_add_new.find('[name="note"]').val(),
            account = {
                'id': '',
                'username': username,
                'password': '',
                'fullName': fullname,
                'email': '',
                'phone': phone,
                'address': address,
                'note': note,
                'active': '',
                'roleId': role,
                'departmentId': department,
                'gender': current_gender,
                'managerId': '',
            };

        //call ajax add new processing
        ajax_add_new_processing(account);
    }
}

/**
 * For util fc
 */
/**
 * Fc: hide
 * @param id
 */
function event_hide(id) {
    $(id).hide();
}

/**
 * Fc: show
 * @param id
 */
function event_show(id) {
    $(id).show();
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
 * Fc: select gender
 * @param gender
 */
function select_gender(gender) {
    var $btn_gender_male = $('#btn-gender-male'),
        $btn_gender_female = $('#btn-gender-female');
    if (gender == 1) {//female
        $btn_gender_female.attr('class', 'btn bg-aqua-gradient');
        $btn_gender_male.attr('class', 'btn btn-default');

        //set current gender
        current_gender = 1;
    } else {//male
        $btn_gender_male.attr('class', 'btn bg-aqua-gradient');
        $btn_gender_female.attr('class', 'btn btn-default');

        //set current gender
        current_gender = 0;
    }
}

/**
 * Fc: set value for select
 * @param id
 * @param value
 */
function set_value_select(id, value) {
    $(id).val(value).trigger("change");
}

/**
 * Fc: reset form add new
 */
function reset_form_add_new() {
    reset_form('#form-add-new');
}

/**
 * Fc: reset form
 * @param id
 */
function reset_form(id) {
    $(id).trigger('reset');
}

/**
 * For Event
 */
/**
 * Event: click show entries
 */
$('#select-entries').on('change', function () {
    var entries = $(this).val();

    //reload list accounts
    load_list_accounts(current_search_value, 0, entries);
});

