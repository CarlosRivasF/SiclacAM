<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        if (sesion.getAttribute("Cotizacion") != null) {
            sesion.removeAttribute("Cotizacion");
        }
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Orden/Registro.jsp');">Nueva Órden</a>
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Cotizacion/Registro.jsp');">Nueva Cotización</a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowOrds?mode=ord");'>Ver Órdenes</a>                      
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowSaldos");'>Saldos</a>                         
    </nav>
</div>
<div class="container-fluid" style="color: white"><br>
    <div class="alert alert-primary alert-dismissible fade show" role="alert">
        <center>Usted iniciará una Orden Cotización... <br>  <strong>Debe elegir un paciente Regitrar uno Nuevo!</strong></center>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div id="step1">
        <h6 style="text-align: center">Nueva Cotización</h6>                 
        <hr class="mb-4">
        <div class="form-row">
            <div class="col col-md-6 mb-3">
                <button class="btn btn-primary btn-block" type="button" id="sendForm" onclick="step12();" name="sendForm">Paciente No Registrado</button>                  
            </div>
            <div class="col col-md-6 mb-3">
                <input style="text-align: center" type="text" onkeyup="SrchPac(this, 'cot');" class="form-control" name="Bupac" id="Bupac" placeholder="Buscar Paciente..." required>
            </div>            
        </div>
    </div>
    <div id="srchPac">      
    </div>
    <div id="step13" style="display: none">
        <form class="needs-validation" name="fors" novalidate method="POST" action="#">
            <h6 style="text-align: center">Datos de Paciente</h6>
            <hr class="mb-4">
            <div class=" form-row">       
                <div class="col-md-4 mb-3">
                    <label class="sr-only" >Nombre</label>
                    <input style="text-align: center" type="text" class="form-control" name="nombre_persona" id="nombre_persona" placeholder="Nombre" required>
                    <div class="invalid-feedback">
                        Se requiere un nombre valido.
                    </div>
                </div>
                <div class="col mb-3">
                    <label class="sr-only" >A Paterno</label>
                    <input style="text-align: center" type="text" class="form-control" name="a_paterno" id="a_paterno" placeholder="Apellido Paterno" required>
                    <div class="invalid-feedback">
                        Se requiere un apellido valido.
                    </div>
                </div>
                <div class="col mb-3">
                    <label class="sr-only" >A Materno</label>
                    <input style="text-align: center" type="text" class="form-control" name="a_materno" id="a_materno" placeholder="Apellido Materno" required>
                    <div class="invalid-feedback">
                        Se requiere un segundo apellido valido.
                    </div>
                </div>
            </div>
            <hr class="mb-4">
            <input class="btn btn-primary btn-lg btn-block" onclick="AddPacCot('dts');" type="button" value="Registrar Nuevo Paciente">
            <br>
        </form>
    </div>
</div>
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>   