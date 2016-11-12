/**
 * Created by TrungNN on 11/3/2016.
 */

/**
 * Initial datepicker
 */
$('#datepicker-reminder-adding').datepicker({
    format: 'yyyy-M-dd',
    startDate: new Date(),
    todayHighlight: 1,
    autoclose: true
});

/**
 * Initial timepicker
 */
$(".timepicker-reminder-adding").timepicker({
    format: 'hh:mm',
    defaultTime: 'current',
    minuteStep: 1,
    disableFocus: false,
    template: 'dropdown',
    showInputs: true
});
