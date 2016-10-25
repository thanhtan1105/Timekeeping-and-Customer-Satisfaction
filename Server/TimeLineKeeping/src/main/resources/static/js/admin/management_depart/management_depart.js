/**
 * Created by TrungNN on 9/17/2016.
 */

var resultSearch;

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

