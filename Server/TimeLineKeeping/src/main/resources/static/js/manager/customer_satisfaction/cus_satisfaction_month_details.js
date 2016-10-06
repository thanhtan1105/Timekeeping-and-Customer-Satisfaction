/**
 * Created by TrungNN on 10/5/2016.
 */

//datePicker
initDatePicker('#date_picker_month', 'MM-yyyy', 'months', 'months');

/**
 * Init DatePicker
 * @param id
 * @param format
 * @param viewMode
 * @param minViewMode
 */
function initDatePicker(id, format, viewMode, minViewMode) {
    $(id).datepicker({
        format: format,
        viewMode: viewMode,
        minViewMode: minViewMode,
        autoclose: true
    });
}

/**
 * Data line chart
 * @type {*[]}
 */
var line_data = [
    {y: '2011-02-01', item1: 2666},
    {y: '2011-02-02', item1: 2778},
    {y: '2011-02-03', item1: 4912},
    {y: '2011-02-04', item1: 3767},
    {y: '2011-02-05', item1: 6810},
    {y: '2011-02-06', item1: 5670},
    {y: '2011-02-07', item1: 4820},
    {y: '2011-02-08', item1: 15073},
    {y: '2011-02-09', item1: 10687},
    {y: '2011-02-11', item1: 8432},
    {y: '2011-02-12', item1: 8432},
    {y: '2011-02-13', item1: 8432},
    {y: '2011-02-14', item1: 8432},
    {y: '2011-02-15', item1: 8432},
    {y: '2011-02-16', item1: 8432},
    {y: '2011-02-17', item1: 8432},
    {y: '2011-02-18', item1: 8432},
    {y: '2011-02-19', item1: 8432},
    {y: '2011-02-20', item1: 8432},
    {y: '2011-02-21', item1: 8432}
];

/**
 * Init line chart
 */
function load_line_chart() {
    var line = new Morris.Line({
        element: 'line-chart',
        resize: true,
        data: line_data,
        xkey: 'y',
        ykeys: ['item1'],
        labels: ['Grade'],
        xLabels: ['day'],
        lineColors: ['#3c8dbc'],
        hideHover: 'auto'
    });
}

//line chart
load_line_chart();