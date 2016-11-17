/**
 * Created by TrungNN on 10/1/2016.
 */

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
    if (src != null) {
        //set content image
        set_src_img(src, '#img-employee');
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