<%@page import="DataTransferObject.Cotizacion_DTO"%>
<%@page import="DataTransferObject.Orden_DTO"%>
<%@page import="DataBase.Util"%>
<%@page import="DataTransferObject.Paciente_DTO"%>
<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        Cotizacion_DTO Cotizacion = (Cotizacion_DTO) sesion.getAttribute("Cotizacion");
        Paciente_DTO pac = Cotizacion.getPaciente();
        Util f = new Util();
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Orden/Registro.jsp');">Nueva Órden</a>
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Cotizacion/Registro.jsp');">Nueva Cotización</a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowOrds?mode=ord");'>Ver Órdenes</a>                      
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowSaldos");'>Saldos</a>                         
    </nav>
</div>
<div class="container-fluid" style="color: white"><hr>
    <div style="text-align: center" id="step1">
        <h6>Cotización Para:</h6>
        <div class="form-row">
            <div class="col">
                <label><strong>Nombre: </strong><%=pac.getNombre() + " " + pac.getAp_Paterno() + " " + pac.getAp_Materno()%></label>
            </div>
        </div>
    </div>
    <hr>           
    <div style="text-align: center" id="DtsMed">
        <h6 style="text-align: center">Elegir Medico</h6><br> 
        <div class="form-row">
            <div class="col col-sm-4 col-md-3">
                <input style="text-align: center" type="text" onkeyup="SrchMed(this, 'cot');" class="form-control" name="BMed" id="BMed" placeholder="Medico" required><br>
                <button class="btn btn-danger btn-block" onclick="NewMedord('form');" type="button" id="sendForm"  name="sendForm"><strong>Médico No Encontrado</strong></button>
            </div><br>
            <div id="srchMed" class="col-sm-8 col-md-9"></div>
        </div>    
    </div>
    <hr>        
    <h6 style="text-align: center"><strong>Añadir Estudios</strong></h6><br>       
    <div id="FrmSrch">
        <div class="form-row">
            <div class=" offset-3 col-6 mb-3" id="Gconvenvio">
                <label class="sr-only" >Convenio</label>
                <input style="text-align: center" onchange="SaveConv(this.value, 'cot')" type="text" class="form-control" name="Convenio" id="Convenio" placeholder="Convenio" required>          
            </div> 
            </div> 
            <div class="form-row">
            <div class="offset-md-1 col-6 col-sm-6 col-md-3 mb-3">
                <div class="col-6 col-sm-3 col-md-1 mb-3 custom-control custom-radio custom-control-inline">
                    <input id="prEsN" class="custom-control-input" name="precE" type="radio" required>
                    <label class="custom-control-label mb-3"  for="prEsN">Normal</label>
                </div>
                <div class="offset-md-1 col-6 col-sm-3 col-md-1 mb-3 custom-control custom-radio custom-control-inline">
                    <input id="prEsU" class="custom-control-input" name="precE" type="radio" required>
                    <label class="custom-control-label" for="prEsU">Urgente</label>
                </div>  
            </div>
            <div class="col-6 col-sm-6 col-md-2 mb-3">
                <input style="text-align: center" type="text" class="form-control" onkeypress="return soloNumeros(event)" name="descE" id="descE" placeholder="% Descuento" required>
            </div>
            <div class="col-6 col-sm-6 col-md-2 mb-3">
                <input style="text-align: center" type="text" class="form-control" onkeypress="return soloNumeros(event)" name="scaE" id="scaE" placeholder="% Cargo Adicional" required>
            </div>
            <div class="col-6 col-sm-6 col-md-4 mb-3">
                <input style="text-align: center" type="text" class="form-control" name="codeEst" onchange="AddEstCot(this, 'code');" id="codeEst" placeholder="Codigo de Estudio" required>
            </div>
        </div>            
        <div class="form-row">
            <div class="col-12 col-sm-12 col-md-5 mb-3">
                <label for="Tipo_Estudio" class="sr-only">Tipo de Estudio</label>
                <select class="custom-select d-block w-100 form-control" id="Tipo_Estudio" name="Tipo_Estudio" required>
                    <option value="">Tipo de Estudio</option>   
                    <%for (Tipo_Estudio_DTO dto : tipos) {%>
                    <option value="<%=dto.getId_Tipo_Estudio()%>"><%=dto.getNombre_Tipo_Estudio().toUpperCase()%></option> 
                    <%}%>
                </select>
                <div class="invalid-feedback" style="width: 100%;">
                    Por favor seleccione un Tipo de Estudio.
                </div>
            </div>
            <div class="col-12 col-sm-12 col-md-7 mb-3">
                <label class="sr-only" >Buscar...</label>
                <input style="text-align: center" type="text" class="form-control" onkeyup="test22(this, 'Cotizacion');" name="clave_mat" id="clave_mat" placeholder="Buscar..." required>
                <div class="invalid-feedback">
                    Ingresa un criterio de busqueda.
                </div>
            </div>
            <div class="col-12 col-sm-12 col-md-12 mb-3">
                <button class="btn btn-outline-info btn-sm btn-block" onclick="chOptCot('per');" >Buscar Paquetes(perfiles)</button>
            </div>
        </div>
    </div>
    <div id="EstsAdded">
        <div id="BEst"></div>
    </div>                
</div>
<hr>
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice el sistema y realice nuevamente el proceso necesario</h1>");
    }%>   