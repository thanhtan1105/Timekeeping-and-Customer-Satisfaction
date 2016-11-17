/**
 * Created by TrungNN on 10/1/2016.
 */

var nextAngle = 0;

//Date picker
$('#date-picker-timekeeping-detail').datepicker({
    format: com_full_year_month,
    viewMode: "months",
    minViewMode: "months",
    endDate: new Date(),
    autoclose: true
});

/**
 * Event: select another month
 */
$('.selected-date').on('change', function () {
    var selectedDate = $('.selected-date').val(),
        $form_submit_change_month = $('#form-submit-change-month'),
        selectedMonth = $form_submit_change_month.find('[name="selectedMonth"]');
    selectedMonth.val(selectedDate);

    // submit form
    $form_submit_change_month.submit();
});

function show_image_employee(src) {
    console.info('[show img emp][src] ' + src);
    //reset angle
    nextAngle = 0;

    if (src != null) {
        //set content image
        set_src_img('http://localhost:8080/file/' + src, '#img-employee');

        //rotate right 90
        rotateRight('#img-employee', 90);

        //show modal
        show_modal('#modal-img-employee', true);
    } else {
        //do nothing
    }


}

/**
 * Fc: set source image
 * @param src
 * @param id
 */
function set_src_img(src, id) {
    $(id).attr('src', src);
}

/**
 * For common fc
 */
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
 * function: rotate right
 * @param id
 * @param degrees
 */
function rotateRight(id_image, degrees) {
    $(id_image).rotate(getAngle(degrees));
}

/**
 * function: get angle
 * @param degrees
 * @returns {number}
 */
function getAngle(degrees) {
    nextAngle += degrees;
    if (nextAngle >= 360) {
        nextAngle = 0;
    }
    return nextAngle;
}