<%-- 
    Document   : MainAdmin
    Created on : 21/02/2018, 04:07:14 PM
    Author     : ZionS
--%>
<%@ page errorPage = "Error.jsp" %>
<%@page import="DataTransferObject.Empleado_DTO"%>
<%@page import="DataAccesObject.Salida_DAO"%>
<%@page import="DataAccesObject.Regreso_DAO"%>
<%@page import="DataAccesObject.Comida_DAO"%>
<%@page import="DataAccesObject.Entrada_DAO"%>
<%@page import="java.util.Date"%>
<%@page import="DataBase.Util"%>
<%@page import="DataTransferObject.Empresa_DTO"%>
<%@page import="DataAccesObject.Empresa_DAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="DataTransferObject.Permiso_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataTransferObject.Usuario_DTO"%>
<%@page import="DataAccesObject.Usuario_DAO"%>
<%
    HttpSession sesion = request.getSession();
    String msg = "Elige una opción";
    int user = 0;
    int unidad = 0;
    if (sesion.getAttribute("permisos") != null && sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        Empresa_DTO empresa = null;
        empresa = (Empresa_DTO) sesion.getAttribute("empresa");
        List<Permiso_DTO> lst = (ArrayList<Permiso_DTO>) sesion.getAttribute("permisos");
        user = Integer.parseInt(sesion.getAttribute("user").toString());
        if (sesion.getAttribute("msg") != null) {
            msg = sesion.getAttribute("msg").toString();
            sesion.removeAttribute("msg");
        }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><%=empresa.getNombre_Empresa()%> | Inicio</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/offcanvas.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
        <script src="${pageContext.request.contextPath}/js/Inter.js"></script>                
    </head>
    <body class="bg-Labs">
        <div id="Main" style="color: white">
            <nav class="navbar navbar-expand-md fixed-top navbar-light bg-light">                
                <button class="navbar-toggler p-0 border-0" data-toggle="offcanvas">
                    <span><img src="${pageContext.request.contextPath}/images/tooglebtn.png"></span>
                </button>
                <div class="navbar-collapse offcanvas-collapse bg-light">
                   <%if (sesion.getAttribute("clave_unidad") != null) {
                            String clave = sesion.getAttribute("clave_unidad").toString().trim().toLowerCase();
                            if (clave.length() > 3) {
                                clave = clave.substring(0, 3);
                            }
                            clave = clave.substring(0, 1).toUpperCase() + clave.substring(1);
                    %>
                    <strong><a class="navbar-brand" href="MainAdmin.jsp" style="color: black"><%=clave%></a></strong>
                        <%} else {%>
                    <strong><a class="navbar-brand" href="MainAdmin.jsp" style="color: black"><%=empresa.getNombre_Empresa()%></a></strong>  
                        <%}%> 
                    <ul class="navbar-nav mr-auto">
                        <%for (Permiso_DTO permisos : lst) {%>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=permisos.getRuta()%>"><%=permisos.getNombre()%></a>
                        </li>
                        <%}%>                        
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="Configuración" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Configuración</a>
                            <div class="dropdown-menu" aria-labelledby="dropdown01">
                                <%if (sesion.getAttribute("empleado") != null) {%>
                                <a class="dropdown-item" href="#Cuenta">Configurar cuenta</a>                            
                                <%}%>
                                <a class="dropdown-item" href="LogOut">Salir</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>                        
            <div><br>  
                <%if (sesion.getAttribute("empleado") == null) {%>
                <Strong><h2 style="text-align: center; color: #ffffff"><%=msg%></h2></Strong>
                    <%} else {
                        Empleado_DTO emp = (Empleado_DTO) sesion.getAttribute("empleado");
                        Entrada_DAO En = new Entrada_DAO();
                        String horaE = En.getHrEnt(emp.getId_Empleado());
                        Comida_DAO Cm = new Comida_DAO();
                        String horaC = Cm.getHrCom(emp.getId_Empleado());
                        Regreso_DAO Rg = new Regreso_DAO();
                        String horaR = Rg.getHrReg(emp.getId_Empleado());
                        Salida_DAO Sa = new Salida_DAO();
                        String horaS = Sa.getHrSal(emp.getId_Empleado());
                    %>  
                <div id="step1">
                    <h2 style="text-align: center">Registro de horario</h2>                 
                    <hr class="mb-4">
                    <div class="form-row">                        
                        <div id="entrada" class="col-6 mb-3">
                            <%if (horaE == null) {%>
                            <button class="btn btn-success btn-block" onclick="Rentr();" ><span><img src='images/entrada.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora entrada</h4></strong></span></button>
                                            <%} else {%>
                            <button class="btn btn-success btn-block"  disabled=""><span><img src='images/entrada.png' class="img-fluid" alt="Responsive image"><strong><h4><%=horaE%></h4></strong></span></button>
                                            <%}%>
                        </div> 
                        <div id="comida" class="col-6 mb-3">
                            <%if (horaC != null) {%>
                            <button class="btn btn-warning btn-block"  disabled=""><span><img src='images/comida.png' class="img-fluid" alt="Responsive image"><strong><h4><%=horaC%></h4></strong></span></button>                                
                                            <%} else {
                                                if (horaE == null || horaS != null) {%>                                               
                            <button class="btn btn-warning btn-block"  disabled=""><span><img src='images/comida.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora comida</h4></strong></span></button>
                                            <%} else {%>
                            <button class="btn btn-warning btn-block" onclick="Rcomida();" ><span><img src='images/comida.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora comida</h4></strong></span></button>
                                            <%}
                                                }%>                            
                        </div>                
                    </div>                    
                    <div class="form-row">
                        <div id="regreso" class="col-6 mb-3">
                            <%if (horaR != null) {%>
                            <button class="btn btn-dark btn-block"  disabled=""><span><img src='images/regreso.png' class="img-fluid" alt="Responsive image"><strong><h4><%=horaR%></h4></strong></span></button>                            
                                            <%} else {
                                                if (horaC == null) {%>
                            <button class="btn btn-dark btn-block"  disabled=""><span><img src='images/regreso.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora regreso</h4></strong></span></button>
                                            <%} else {%>
                            <button class="btn btn-dark btn-block" onclick="Rregreso();" ><span><img src='images/regreso.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora regreso</h4></strong></span></button>
                                            <%}
                                                }%>
                        </div> 
                        <div id="salida" class="col-6 mb-3">
                            <%if (horaS != null) {%>
                            <button class="btn btn-danger btn-block"  disabled=""><span><img src='images/salida.png' class="img-fluid" alt="Responsive image"><strong><h4><%=horaS%></h4></strong></span></button>                            
                                            <%} else {
                                                if (horaE == null) {%>                            
                            <button class="btn btn-danger btn-block"  disabled=""><span><img src='images/salida.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora salida</h4></strong></span></button>
                                            <%} else {%>
                            <button class="btn btn-danger btn-block" onclick="RSalida();" ><span><img src='images/salida.png' class="img-fluid" alt="Responsive image"><strong><h4>Hora salida</h4></strong></span></button>
                                            <%}
                                                }%>
                        </div>                
                    </div>
                </div>                
                <%}%>                
            </div>            
        </div>
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>        
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/holder.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/offcanvas.js"></script>        
    </body>
</html>
<%} else {%>
<script>location.replace("${pageContext.request.contextPath}");</script>
<%}%>