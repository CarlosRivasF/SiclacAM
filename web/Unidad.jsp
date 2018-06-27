<%-- 
    Document   : MainAdmin
    Created on : 21/02/2018, 04:07:14 PM
    Author     : ZionS
--%>
<%@page import="DataTransferObject.Empresa_DTO"%>
<%@page import="DataAccesObject.Empresa_DAO"%>
<%@page import="java.util.List"%>
<%@page import="DataTransferObject.Permiso_DTO"%>
<%@page import="java.util.ArrayList"%>
<%
    HttpSession sesion = request.getSession();
    String msg = "Elige una opción";
    boolean r = false;
    if (sesion.getAttribute("permisos") != null && sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        Empresa_DAO E = new Empresa_DAO();
        Empresa_DTO empresa = null;
        if (sesion.getAttribute("persona") == null) {
            empresa = E.getEmpresa(Integer.parseInt(sesion.getAttribute("unidad").toString()));
        } else {
            empresa = E.getEmpresaByUnidad(Integer.parseInt(sesion.getAttribute("unidad").toString()));
        }
        List<Permiso_DTO> lst = (ArrayList<Permiso_DTO>) sesion.getAttribute("permisos");        
        for (Permiso_DTO permisos : lst) {
            if (permisos.getNombre().equals("Unidad")) {
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
        <title><%=empresa.getNombre_Empresa()%> | Unidad</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/offcanvas.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/form-validation.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    </head>
    <body style="background: #32383e">  
        <div class="container-fluid">
            <nav class="navbar navbar-expand-md fixed-top navbar-Am-Labs bg-light">
                <strong><a class="navbar-brand" href="MainAdmin.jsp" style="color: black"><%=empresa.getNombre_Empresa()%></a></strong>                
                <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
                    <span><img src="${pageContext.request.contextPath}/images/tooglebtn.png"></span>
                </button>
                <div class="navbar-collapse offcanvas-collapse bg-light" id="navbarsExampleDefault">
                    <%if (sesion.getAttribute("nombre_unidad") != null) {%>
                    <strong><a class="navbar-brand" href="MainAdmin.jsp" style="color: black"><%=sesion.getAttribute("nombre_unidad").toString()%></a></strong>
                        <%}%> 
                    <ul class="navbar-nav mr-auto">
                        <%for (Permiso_DTO permisos1 : lst) {
                                if (permisos1.getNombre().equals("Unidad")) {%>
                        <li class="nav-item active">
                            <a style="color: #72d0f6" class="nav-link active" href="<%=permisos1.getRuta()%>"><strong><ins><%=permisos1.getNombre()%></ins></strong></a>
                        </li>
                        <%} else {%>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=permisos1.getRuta()%>"><%=permisos1.getNombre()%></a>
                        </li>
                        <%}
                            }%>  
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="Configuración" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Configuración</a>
                            <div class="dropdown-menu" aria-labelledby="dropdown01">
                                <a class="dropdown-item" href="#Cuenta">Configurar cuenta</a>                            
                                <a class="dropdown-item" href="LogOut">Salir</a>
                            </div>
                        </li>
                    </ul>
                </div>
            </nav>
        </div>
        <div id="Interaccion">
            <div class="nav-scroller bg-white box-shadow">
                <nav class="nav nav-underline">
                    <a class="nav-link" href="#/" onclick="mostrarForm('Menu/Unidad/Registro.jsp');">Nueva Unidad</a>
                    <a class="nav-link" href="#/" onclick='mostrarForm("ShUnid");'>Ver Unidades</a>              
                </nav>
            </div>
            <div><br>
                <Strong><h2 style="text-align: center; color: #ffffff"><%=msg%></h2></Strong>
            </div> 
        </div>               
        <script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>        
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/holder.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/offcanvas.js"></script>
        <script src="${pageContext.request.contextPath}/js/Inter.js"></script>
    </body>
</html>
<%
                break;
            } else {
                r = true;
            }
        }
        if (r) {
            sesion.setAttribute("msg", "Usted no tiene acceso al menú 'Unidad'.");
            response.sendRedirect("" + request.getContextPath() + "/MainAdmin.jsp");
        }
    } else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>