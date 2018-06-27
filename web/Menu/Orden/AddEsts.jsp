<%@page import="java.time.Period"%>
<%@page import="DataTransferObject.Orden_DTO"%>
<%@page import="DataBase.Fecha"%>
<%@page import="DataTransferObject.Paciente_DTO"%>
<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        Orden_DTO Orden = (Orden_DTO) sesion.getAttribute("Orden");
        Paciente_DTO pac = Orden.getPaciente();
        Fecha f = new Fecha();
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link" href="#/" onclick="mostrarForm('Menu/Orden/Registro.jsp');">Nueva Órden</a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowMats");'>Ver Órdenes</a> 
    </nav>
</div>
<div class="container-fluid" style="color: white"><hr>
    <div id="step1">
        <h6 style="text-align: center">Datos de Paciente</h6><br>  
        <div class="form-row">
            <div class="col-8 col-sm-5 col-md-4">
                <label><strong>Nombre: </strong><%=pac.getNombre() + " " + pac.getAp_Paterno() + " " + pac.getAp_Materno()%></label>
            </div>
            <div class=" col-4 col-sm-3 col-md-3">
                <%Period edad=f.getEdad(pac.getFecha_Nac().trim());%>
                <label><strong>Edad: </strong><%=edad.getYears() %> años , <%=edad.getMonths()%> meses y <%=edad.getDays()%> días</label>
            </div>
            <div class="col-6 col-sm-2 col-md-2">
                <label><strong>Sexo: </strong><%=pac.getSexo()%></label>
            </div>
            <div class="col-6 col-sm-2 col-md-2">
                <label><strong>Teléfono: </strong><%=pac.getTelefono1()%></label>
            </div>
        </div>
    </div>
    <hr>           
    <div  id="DtsMed">
        <h6 style="text-align: center">Elegir Medico</h6><br> 
        <div class="form-row">     
            <div class="col col-sm-4 col-md-3">
                <input style="text-align: center" type="text" onkeyup="SrchMed(this, 'est');" class="form-control" name="BMed" id="BMed" placeholder="Medico" required>
                <br><button class="btn btn-danger btn-block" onclick="AddMed('form');" id="sendForm"  name="sendForm"><strong>Médico No Registrado</strong></button>
            </div><br>                        
            <div id="srchMed" class="col-sm-8 col-md-9"></div>
        </div>    
    </div>
    <hr>        
    <h6 style="text-align: center"><strong>Añadir Estudios</strong></h6><br>       
    <div class="form-row">
        <div class=" offset-3 col-6 mb-3" id="Gconvenvio">
            <label class="sr-only" >Convenio</label>
            <input style="text-align: center" onchange="SaveConv(this.value,'ord')" type="text" class="form-control" name="Convenio" id="Convenio" placeholder="Convenio" required>          
        </div> 
        <div class="offset-1 col-7 col-sm-6 col-md-3 mb-3"> 
            <div class="col-2 col-sm-2 col-md-2 mb-3 custom-control custom-radio custom-control-inline">
                <input id="prEsN" class="custom-control-input" name="precE" type="radio" required>
                <label class="custom-control-label mb-3"  for="prEsN">Normal</label>&nbsp;
            </div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <div class="col-2 col-sm-2 col-md-2 mb-3 custom-control custom-radio custom-control-inline">
                <input id="prEsU" class="custom-control-input" name="precE" type="radio" required>
                <label class="custom-control-label" for="prEsU">Urgente</label>&nbsp;
            </div>  
        </div>&nbsp;&nbsp;&nbsp;
        <div class="col-5 col-sm-4 col-md-2 mb-3">            
            <input style="text-align: center" type="text" class="form-control" onkeypress="return soloNumeros(event)" name="descE" id="descE" placeholder="%" required>
        </div>
        <div class="col-7 col-sm-12 col-md-5 mb-3">                      
            <input style="text-align: center" type="text" class="form-control" name="codeEst" onchange="AddEst(this.value, 'code');" id="codeEst" placeholder="Codigo de Estudio" required>
        </div>
    </div>        
    <form class="needs-validation" novalidate name="fors" action="#" method="post">
        <div class="form-row">
            <div class="col-5 col-sm-5 col-md-5 mb-3">
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
            <div class="col-7 col-sm-7 col-md-7 mb-3">
                <label class="sr-only" >Buscar...</label>
                <input style="text-align: center" type="text" class="form-control" onkeyup="test22(this,'Orden');" name="clave_mat" id="clave_mat" placeholder="Buscar..." required>
                <div class="invalid-feedback">
                    Ingresa un criterio de busqueda.
                </div>
            </div>              
        </div>
    </form>
    <div id="EstsAdded">
        <div id="BEst"></div>
    </div>                
</div>
<hr>
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice el sistema y realice nuevamente el proceso necesario</h1>");
    }%>   