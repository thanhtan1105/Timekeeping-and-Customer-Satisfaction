/**
 * Created by TrungNN on 9/14/2016.
 */

$('#btn-train-image').on('click', function () {
    var personGroupId = $('#personGroupId').val(),
        formDataJson = {
            'person_groups_id': personGroupId
        };
    alert('personGroupId: ' + personGroupId);
    $.ajax({
        type: "GET",
        url: "/api/persongroups/training",
        data: formDataJson,
        success: function (response) {
            alert('Training...');
        },
        error: function () {
            alert('Error');
        }
    });
});