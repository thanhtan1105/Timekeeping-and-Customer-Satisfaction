/**
 * Created by TrungNN on 9/14/2016.
 */

$(document).ready(function () {
    loadPersons();
});


function loadPersons() {
    var personGroupId = $('#personGroupId').val();
    var formDataJson = {
        'groupId': personGroupId
    };
    $.ajax({
        type: "GET",
        url: "/api/person/list_all_person",
        data: formDataJson,
        success: function (response) {
            var $table_body_group = $('#table-body-group');
            var body_content = '',
                personId,
                name;
            for (var i = 0; i < response.data.length; i++) {
                personId = response.data[i].personId;
                name = response.data[i].name;
                body_content += '<tr><td style="text-align: left">' +
                    name +
                    '</td>' +
                    '<td><input type="radio" name="personId" class="flat-red" value="' +
                    personId +
                    '"/></td></tr>';
            }
            $table_body_group.html(body_content);
        },
        error: function () {
            alert('Error');
        }
    });
}