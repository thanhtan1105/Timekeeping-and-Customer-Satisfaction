/**
 * Created by TrungNN on 11/3/2016.
 */

/**
 * Initial datepicker
 */
$('#datepicker-reminder').datepicker({
    format: 'yyyy-mm-dd',
    startDate: new Date(),
    autoclose: true
});

/**
 * Initial timepicker
 */
$(".timepicker-reminder").timepicker({
    format: 'hh:mm',
    defaultTime: 'current',
    minuteStep: 1,
    disableFocus: false,
    template: 'dropdown',
    showInputs: true
});
