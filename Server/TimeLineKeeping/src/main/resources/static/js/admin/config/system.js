/**
 * Created by TrungNN on 11/14/2016.
 */

//Timepicker
$("#time-picker-end-time-check-in").timepicker({
    format: 'hh:mm',
    defaultTime: 'current',
    minuteStep: 1,
    disableFocus: false,
    template: 'dropdown',
    showInputs: true
});

$("#time-picker-start-time-check-in").timepicker({
    format: 'hh:mm',
    defaultTime: 'current',
    minuteStep: 1,
    disableFocus: false,
    template: 'dropdown',
    showInputs: true
});
