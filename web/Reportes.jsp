<%-- 
    Document   : MainAdmin
    Created on : 21/02/2018, 04:07:14 PM
    Author     : ZionS
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
        <script  src="${pageContext.request.contextPath}/Charts/js/index.js"></script>         
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css"> 
        <title><%=empresa.getNombre_Empresa()%> | Reportes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/offcanvas.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico">
    </head>
    <body style=" background: #666666" onload="verGrafico(1);">
        <div id="Main" style="color: white">
            <nav class="navbar navbar-expand-md fixed-top navbar-light bg-light">                
                <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
                    <span><img src="${pageContext.request.contextPath}/images/tooglebtn.png"></span>
                </button>
                <div class="navbar-collapse offcanvas-collapse bg-light" id="navbarsExampleDefault">
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
                        <%for (Permiso_DTO permisos1 : lst) {
                                if (permisos1.getNombre().equals("Reportes")) {%>
                        <li class="nav-item active">
                            <a style="color: #72d0f6" class="nav-link" href="<%=permisos1.getRuta()%>"><strong><ins><%=permisos1.getNombre()%></ins></strong></a>
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
            <div id="Interaccion">
                <div class="nav-scroller bg-white box-shadow">
                    <nav class="nav nav-underline">
                        <a onclick="verRep('CardEstudios');" class="nav-link" href="#/ESTUDIOS">ESTUDIOS</a>
                        <a onclick="verRep('CardOrdenes');" class="nav-link" href="#/ÓRDENES">ÓRDENES </a>
                        <a onclick="verRep('CardResultados');" class="nav-link" href="#/RESULTADOS">RESULTADOS </a>
                        <a class="nav-link" href="#/CORTES">CORTES </a>      
                        <a class="nav-link" href="#/PARTICIPACIONES">PARTICIPACIONES </a>
                    </nav>
                </div>
                <script>

                </script>
                <div class="container-fluid"><br>
                    <Strong><h2 style="text-align: center; color: #ffffff"><%=msg%></h2></Strong>
                    <!--CardEstudios-->                    
                    <div id="CardEstudios" class="form-row" style="display:none">                        
                        <div class="form-row col-md-12">
                            <div class="card col-md-5 mb-3" style="background: darkgrey">
                                <h5 class="card-header">Catalogo de Esudios de la Unidad</h5>
                                <div class="card-body form-row col-md-12">
                                    <div class="col-md-6 mb-3">
                                        <a href="#" onclick="OpenRep('PrinCatXLS');" class="btn btn-light btn-block">Reporte en Excel  <span><img src='images/Excel.png'></span></a>    
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <a href="#" onclick="OpenRep('PrintCatPDF' + ITpoEto());" class="btn btn-light btn-block">Reporte en PDF<span><img src='images/Pdf.png'></span></a>
                                    </div>
                                </div>
                            </div>
                            <div class="offset-md-1"></div>
                            <div class="card col-md-6 mb-3" style="background: darkgrey">
                                <h5 class="card-header">Catalogo de Esudios a Detalle de la Unidad</h5>
                                <div class="card-body form-row col-md-12">                                    
                                    <div class="col-md-6 mb-3">
                                        <a href="#" onclick="OpenRep('PrinCatXLS?DetCat=Ys');" class="btn btn-light btn-block">Reporte en Excel<span><img src='images/Excel.png'/></span></a>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <a href="#" onclick="OpenRep('PrintCatPDF?DetCat=Ys');" class="btn btn-light btn-block">Reporte en PDF<span><img src='images/Pdf.png'/></span></a>
                                    </div>                                    
                                </div>    
                            </div>
                            <div class="card col-md-5 mb-3" style="background: darkgrey">
                                <h6 class="card-header">Catalogo de Esudios de la Unidad (Un solo Tipo de Estudio)</h6>
                                <div class="card-body form-row col-md-12 mb-3">
                                    <div class="form-group col-md-12 mb-3">
                                        <label for="Tipo_Estudio1" class="sr-only">Tipo de Estudio</label><br>
                                        <select class="custom-select d-block w-100 form-control-sm" id="Tipo_Estudio1" name="Tipo_Estudio1" required>
                                            <option value="0">Tipo de Estudio</option>   
                                            <%for (Tipo_Estudio_DTO dto : tipos) {%>
                                            <option value="<%=dto.getId_Tipo_Estudio()%>"><%=dto.getNombre_Tipo_Estudio().toUpperCase()%></option> 
                                            <%}%>
                                        </select>                                   
                                    </div>
                                    <div class="form-row col-md-12 mb-3">
                                        <div class="col-md-6 mb-3">
                                            <a href="#" onclick="OpenRep('PrinCatXLS' + ITpoEto());" class="btn btn-light btn-block">Reporte en Excel<span><img src='images/Excel.png'></span></a>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <a href="#" onclick="OpenRep('PrintCatPDF' + ITpoEto());" class="btn btn-light btn-block">Reporte en PDF<span><img src='images/Pdf.png'></span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="offset-1"></div>
                            <div class="card col-md-6 mb-3" style="background: darkgrey">
                                <h6 class="card-header">Catalogo de Esudios a Detalle de la Unidad (Un solo Tipo de Estudio)</h6>
                                <div class="card-body form-row col-md-12 mb-3">
                                    <div class="form-group col-md-12 mb-3">
                                        <label for="Tipo_Estudio2" class="sr-only">Tipo de Estudio</label><br>
                                        <select class="custom-select d-block w-100 form-control-sm" id="Tipo_Estudio2" name="Tipo_Estudio2" required>
                                            <option value="0">Tipo de Estudio</option>   
                                            <%for (Tipo_Estudio_DTO dto : tipos) {%>
                                            <option value="<%=dto.getId_Tipo_Estudio()%>"><%=dto.getNombre_Tipo_Estudio().toUpperCase()%></option> 
                                            <%}%>
                                        </select>
                                    </div>
                                    <div class="form-row col-md-12 mb-3">
                                        <div class="col-md-6 mb-3">
                                            <a href="#" onclick="OpenRep('PrinCatXLS' + ITpoEto2() + '&DetCat=Ys');" class="btn btn-light btn-block">Reporte en Excel<span><img src='images/Excel.png'></span></a>
                                        </div>
                                        <div class="col-md-6 mb-3">
                                            <a href="#" onclick="OpenRep('PrintCatPDF' + ITpoEto2() + '&DetCat=Ys');" class="btn btn-light btn-block">Reporte en PDF<span><img src='images/Pdf.png'></span></a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--CardOrdenes-->
                    <div id="CardOrdenes" class="form-row" style="display:none">
                        <div class="form-row  col-md-12">
                            <div class="card form-row col-md-6  mb-3" style="background: darkgrey">
                                <h6 class="card-header">Reporte General de Ordenes de la Unidad</h6>
                                <div class="card-body">
                                    <div class="form-row col-md-12 mb-3">
                                        <div class="form-group col-md-6 mb-3">
                                            <label style="text-align: center" for="fechaI">Fecha Inicio</label>
                                            <input type="date" class="form-control" name="fechaI1Ord" id="fechaI1Ord" required>
                                        </div>
                                        <div class="form-group col-md-6 mb-3">
                                            <label style="text-align: center" for="fechaF">Fecha Inicio</label>
                                            <input type="date" class="form-control" name="fechaF1Ord" id="fechaF1Ord" required>
                                        </div>
                                    </div>                                    
                                    <div class="form-row col-md-12" >
                                        <div class="form col-md-6 mb-3">
                                            <button style="text-align: center" disabled="" onclick="OpenRep('');" class="btn btn-light btn-block">Reporte Excel  <span><img src='images/Excel.png' class="img-fluid" alt="Responsive image"></span></button>
                                        </div>
                                        <div class="form col-md-6 mb-3">
                                            <button style="text-align: center" onclick="OpenRep('PrintReporteOrders' + fchROrdGrl());" class="btn btn-light btn-block">Reporte PDF  <span><img src='images/Pdf.png' class="img-fluid" alt="Responsive image"></span></button>
                                        </div>
                                    </div>                                    
                                </div>
                            </div>
                            <div class="offset-md-1"></div>                                              
                            <div class="card col-md-5 mb-3" style="background: darkgrey">
                                <h6 class="card-header">Estadistica de Estudios en Ordenes</h6>
                                <div class="card-body form-row col-md-12 mb-3" id="EstadisticarepChrt">
                                    <div class="form col-md-12 mb-3">
                                        <button style="text-align: center" onclick="OpenRep('${pageContext.request.contextPath}/Charts/EstadisticaEstudiosByOrder.jsp');" class="btn btn-light btn-block">Ver Grafica de Estadistica</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>         
                    <!--CardResultados-->
                    <div id="CardResultados" class="form-row" style="display:none">
                        <div class="form-row  col-12">
                            <div class="card form-row col-12  mb-3" style="background: darkgrey">
                                <h6 class="card-header">Buscar Resultados De Órden</h6>
                                <div class="card-body">
                                    <div class="form-row col-12 col-md-12 mb-3">
                                        <div class="form-group col-12 col-md-6 mb-3">                                            
                                            <input type="text" class="form-control" name="fechaI1Ord" id="fechaI1Ord" placeholder="Folio de Órden" required>
                                        </div>
                                        <div class="form-group col-12 col-md-6 mb-3">
                                            <button style="text-align: center" disabled="" onclick="OpenRep('PrintReporteOrders' + fchROrdGrl());" class="btn btn-light btn-block">Reporte PDF  <span><img src='images/Pdf.png' class="img-fluid" alt="Responsive image"></span></button>
                                        </div>
                                    </div>                                    
                                    <div class="form-row col-12" >
                                        <div class="form col-12 mb-3" style="align-items:  center">
                                            Espacio de Respuesta
                                        </div>                                       
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                    </div>         
                </div>
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
            sesion.setAttribute("msg", "Usted no tiene acceso al menú 'Reportes'.");
            response.sendRedirect("" + request.getContextPath() + "/MainAdmin.jsp");
        }
    } else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>