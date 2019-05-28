<%@page import="DataAccesObject.Orden_DAO"%>
<%@page import="DataTransferObject.Det_Orden_DTO"%>
<%@page import="DataTransferObject.Medico_DTO"%>
<%@page import="DataTransferObject.Orden_DTO"%>
<%@page import="DataBase.Util"%>
<%@page import="DataTransferObject.Paciente_DTO"%>
<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int id_Orden = Integer.parseInt(request.getParameter("id_Orden").trim());
        sesion.setAttribute("id_Orden", id_Orden);
        Orden_DAO O = new Orden_DAO();
        Orden_DTO Orden = O.getOrden(id_Orden);
        Paciente_DTO pac = Orden.getPaciente();
        Medico_DTO med = Orden.getMedico();
        List<Det_Orden_DTO> Det_Orden = Orden.getDet_Orden();
        Util f = new Util();
        
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Orden/Registro.jsp');">Nueva Órden</a>
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Cotizacion/Registro.jsp');">Nueva Cotización</a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowOrds?mode=ord");'>Ver Órdenes</a>                                             
    </nav>
</div>
<div class="container-fluid" style="color: white"> <hr>
    <h6 style="text-align: center">Datos de Paciente</h6><br>  
    <div class="form-row">
        <div class="col-8 col-sm-5 col-md-4">
            <label><strong>Nombre: </strong><%=pac.getNombre() + " " + pac.getAp_Paterno() + " " + pac.getAp_Materno()%></label>
        </div>
        <div class=" col-4 col-sm-3 col-md-3">
            <label><strong>Edad: </strong><%=f.getEdad(pac.getFecha_Nac().trim()).getYears()%> años</label>
        </div>
        <div class="col-6 col-sm-2 col-md-2">
            <label><strong>Sexo: </strong><%=pac.getSexo()%></label>
        </div>
        <div class="col-6 col-sm-2 col-md-2">
            <label><strong>Teléfono: </strong><%=pac.getTelefono1()%></label>
        </div>
    </div>    
    <hr>               
    <h6 style="text-align: center"><strong>Datos de Medico</strong></h6>
    <div class="form-row">
        <div class="col-8 col-sm-5 col-md-4">
            <label><strong>Nombre: </strong><%=med.getNombre() + " " + med.getAp_Paterno() + " " + med.getAp_Materno()%></label>
        </div>
        <div class="col-6 col-sm-2 col-md-2">
            <label><strong>Teléfono: </strong><%=med.getTelefono1()%></label>
        </div>
    </div>        
    <hr>        
    <h6 style="text-align: center"><strong>Estudios</strong></h6>
    <div id="estsOrd">
        <div style='color: white' class='table-responsive'>
            <table style=' text-align: center' class='table table-bordered table-sm'>
                <tr class='bg-info' style='color: black'>
                    <th>Nombre de Estudio</th>                
                    <th>Fecha Entrega</th>
                    <th>Subtotal</th>                                                                 
                </tr>                
                <%for (Det_Orden_DTO dto : Det_Orden) {%>    
                <tr>
                    <td ><%=dto.getEstudio().getNombre_Estudio()%></td>
                    <td ><%=dto.getFecha_Entrega()%></td>                    
                    <td ><%=dto.getSubtotal()%></td>            
                </tr>
                <%}%>                
            </table>                       
        </div>
        <div class='container offset-7 col'>
            <table>
                <tr>
                    <td>Total : </td>
                    <td><%=Util.redondearDecimales(Orden.getMontoRestante() + Orden.getMontoPagado())%></td>
                </tr>
                <tr>
                    <td>A/C : </td>
                    <td><%=Util.redondearDecimales(Orden.getMontoPagado())%></td>
                </tr>
                <tr>
                    <td>Por Pagar : </td>
                    <td><%=Util.redondearDecimales(Orden.getMontoRestante())%></td>
                </tr>
            </table>
        </div>
        <div id="pago">            
            <button class='btn btn-success btn-lg btn-block' id='ConPay' onclick="FormPago('list');" name='ConPay'>Realizar Pago<span><img src='images/pay.png'></span></button>
        </div><br>
    </div>

</div>   
<hr>
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice el sistema y realice nuevamente el proceso necesario</h1>");
    }%>   