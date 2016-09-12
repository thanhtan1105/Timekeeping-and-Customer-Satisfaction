/**
 * Created by TrungNN on 9/12/2016.
 */

$(document).ready(function () {
    /**
     * Load list groups by ajax
     */
    loadPersonGroup();
});

function loadPersonGroup() {
    var formDataJson = {
        'start': 1,
        'top': 1000
    };
    $.ajax({
        type: "GET",
        url: "/api/persongroups/listAll",
        data: formDataJson,
        success: function (response) {
            var $table_body_group = $('#table-body-group');
            var body_content = '',
                personGroupId;
            for (var i = 0; i < response.data.length; i++) {
                personGroupId = response.data[i].personGroupId;
                body_content += '<tr><td style="text-align: left">' +
                    personGroupId +
                    '</td>' +
                    '<td><input type="radio" name="r3" class="flat-red" value="' +
                    personGroupId +
                    '"/></td></tr>';
            }
            $table_body_group.html(body_content);
        },
        error: function () {
            alert('Error');
        }
    });
}