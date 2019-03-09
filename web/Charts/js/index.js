/* global google */

function test() {
    alert("e.value");
}

google.load()("visualization", "1", {packages: ["corechart"]});
google.setOnLoadCallback(drawChart1);

var xhr;
function buscarComentario() {
    if (window.ActiveXObject) {
        xhr = new AciveXObject("Microsoft.XMLHttp");
        //alert("IE Soporta AJAX");
    } else if ((window.XMLHttpRequest) || (typeof XMLHttpRequest) !== undefined) {
        xhr = new XMLHttpRequest();
        //alert("Soporta AJAX");
    } else {
        alert("No Soporta AJAX");
        return;
    }
    return xhr;
}

function verGrafico(url) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('filtros');
    Ajax = buscarComentario();
    Ajax.open('GET', url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send(null);
}

function DibujaGrafico(arrParams, arrOpts) {
    var data = google.visualization.arrayToDataTable(arrParams);

    var options = arrOpts;

    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div1'));
    chart.draw(data, options);
}

function drawChart1() {
    var data = google.visualization.arrayToDataTable([
        ['Year', 'Sales'],
        ['2004', 1000],
        ['2005', 1170],
        ['2006', 660],
        ['2007', 1030]
    ]);

    var options = {
        title: 'Título', hAxis: {title: '', titleTextStyle: {color: 'red'}
        }
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('chart_div1'));
    chart.draw(data, options);
}

google.load("visualization", "1", {packages: ["corechart"]});
google.setOnLoadCallback(drawChart2);
function drawChart2() {
    var data = google.visualization.arrayToDataTable([
        ['Year', 'Sales', 'Expenses', 'Other'],
        ['2013', 1000, 400, 300],
        ['2014', 1170, 460, 260],
        ['2015', 660, 1120, 142],
        ['2016', 1030, 540, 340]
    ]);

    var options = {
        title: 'Company Performance',
        hAxis: {title: 'Year', titleTextStyle: {color: '#333'}},
        vAxis: {minValue: 0}
    };

    var chart = new google.visualization.AreaChart(document.getElementById('chart_div2'));
    chart.draw(data, options);
}

$(window).resize(function () {
    drawChart1();
    drawChart2();
});

// Reminder: you need to put https://www.google.com/jsapi in the head of your document or as an external resource on codepen //