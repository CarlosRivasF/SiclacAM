<%-- 
    Document   : testChar
    Created on : 6/03/2019, 05:10:55 PM
    Author     : c
--%>

<%@page import="java.util.List"%>
<%@page import="DataTransferObject.Estadistica_DTO"%>
<%@page import="DataAccesObject.Orden_DAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script>
            function data() {
                var arrayBidimensional = new Array();
                arrayBidimensional[0] = ['Estudio', 'Ventas'];
            <%
                Orden_DAO O = new Orden_DAO();
                List<Estadistica_DTO> datos = O.getEstadisticadeOrdenesByEstudios(1);
                for (Estadistica_DTO e : datos) {
            %>
                arrayBidimensional[<%=(datos.indexOf(e) + 1)%>] = ['<%=e.getClave_Estudio().trim()%>', <%=e.getCantidad()%>];
            <%}%>
                return arrayBidimensional;
            }
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>testChart</title> 
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
        <link rel="stylesheet" href="css/style.css"> 
    </head>
    <body>             
        <div class="row">
            <div class="col-md-12 text-center">
                <h1>Google Chart Responsive Static</h1>
                <p>Using JS</p>
            </div>
            <div class="col-md-4 col-md-offset-4">
                <hr />
            </div>
            <div class="clearfix"></div>
            <div class="col-md-6">
                <div id="chart_div1" class="chart"></div>
            </div>
            <div class="col-md-6">
                <div id="chart_div2" class="chart"></div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12 text-center">
                <h1>Google Chart Responsive Dynamic</h1>
                <p>Using JS, Java & MySQL</p>
            </div>
            <button class="btn-default btn-block" onclick="ver();">Actualizar</button>
            <div class="col-md-4 col-md-offset-4">
                <hr />
            </div>
            <div class="clearfix"></div>
            <div class="col-md-6">
                <div id="chDyn1" class="chart"></div>
            </div>
            <div class="col-md-6">
                <div id="chDyn12" class="chart"></div>
            </div>
        </div>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src='https://www.google.com/jsapi'></script>
        <script  src="js/index.js"></script>
    </body>
</html>