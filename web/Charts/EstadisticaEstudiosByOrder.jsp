<%-- 
    Document   : testChar
    Created on : 6/03/2019, 05:10:55 PM
    Author     : c
--%>

<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%@page import="DataTransferObject.Empresa_DTO"%>
<%@page import="DataTransferObject.Permiso_DTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%
    HttpSession sesion = request.getSession();
    String msg = "Elige una opción";
    boolean r = false;
    int user = 0;
    int unidad = 0;
    if (sesion.getAttribute("permisos") != null && sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        Empresa_DTO empresa = null;
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        empresa = (Empresa_DTO) sesion.getAttribute("empresa");
        List<Permiso_DTO> lst = (ArrayList<Permiso_DTO>) sesion.getAttribute("permisos");
        user = Integer.parseInt(sesion.getAttribute("user").toString());
        for (Permiso_DTO permisos : lst) {
            if (permisos.getNombre().equals("Reportes")) {
                r = false;
                if (sesion.getAttribute("msg") != null) {
                    msg = sesion.getAttribute("msg").toString();
                    sesion.removeAttribute("msg");
                }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>   
    <head>
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
        <script src='https://www.google.com/jsapi'></script>     
        <script  src="js/index.js"></script>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=empresa.getNombre_Empresa()%> | Reportes</title>
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
        <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css'>
        <link rel="stylesheet" href="css/style.css"> 
    </head>
    <body onload="verGrafico(<%=sesion.getAttribute("unidad").toString().trim()%>);">
        <div class="row">
            <div class="col-md-12 text-center">
                <h1><%=sesion.getAttribute("nombre_unidad").toString()%></h1>
                <p></p>
            </div>            
            <div class="col-md-4 col-md-offset-4">
                <hr />
            </div>
            <div class="clearfix"></div>
            <div class="col-md-12">
                <div id="chDyn1" class="chart"></div>
            </div>
            <div class="col-md-6">
                <div id="chDyn12" class="chart"></div>
            </div>
        </div>           
    </body>
</html>
<%
                break;
            } else {
                r = true;
            }
        }
        if (r) {
            sesion.setAttribute("msg", "Usted no tiene acceso al menú 'Reportes'.");
            response.sendRedirect("" + request.getContextPath() + "/MainAdmin.jsp");
        }
    } else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>