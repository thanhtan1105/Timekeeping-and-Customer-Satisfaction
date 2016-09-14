/**
 * Created by TrungNN on 9/14/2016.
 */

$(document).ready(function () {
    //
});

$('.btn-add-face-to-person').on('click', function () {
    var personGroupId = $('#personGroupId').val(),
        personId = $('#personId').val(),
        url = 'http://vip.media6.tiin.vn/medias12/2016/05/13/1839f890437db47f5bfa87ed5334b68e57140e28.jpg',
        formDataJson = {
            'groupId': personGroupId,
            'personId': personId,
            'url': url
        };
    $.ajax({
        type: 'POST',
        url: '/api/person/add_face_url',
        data: formDataJson,
        success: function (response) {
            var persistedFaceId = response.data.persistedFaceId;
            alert('[persistedFaceId: ' + persistedFaceId + ']');
        },
        error: function () {
            alert('Error');
        }
    });
});
