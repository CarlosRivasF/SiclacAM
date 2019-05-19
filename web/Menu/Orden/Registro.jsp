<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        if (sesion.getAttribute("Orden") != null) {
            sesion.removeAttribute("Orden");
        }
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Orden/Registro.jsp');">Nueva �rden</a>
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Cotizacion/Registro.jsp');">Nueva Cotizaci�n</a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowOrds?mode=ord");'>Ver �rdenes</a>                      
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowSaldos");'>Saldos</a>                         
    </nav>
</div>
<div class="container-fluid mb-3" style="color: white"><br>
    <div class="alert alert-primary alert-dismissible fade show" role="alert">
        <center>Usted iniciar� una Orden Nueva... <br>  <strong>Debe elegir un paciente o escanear el codigo de una cotizaci�n realizada!</strong></center>
        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div id="step1">
        <h6 style="text-align: center">Nueva �rden</h6>                 
        <hr class="mb-4">
        <div class="form-row">
            <div class=" offset-3 col-6 mb-3">
                <label class="sr-only" >Codigo de Cotizaci�n</label>
                <input style="text-align: center" type="text" class="form-control" onchange="CastCot(this);" name="CodeCot" id="CodeCot" placeholder="Escanear Codigo de Cotizaci�n" required>          
            </div>               
        </div>
        <hr class="mb-4">
        <div class="form-row">
            <div class="col col-md-6 mb-3">
                <button class="btn btn-primary btn-block" type="button" id="sendForm" onclick="step12();" name="sendForm">Ingresar Nuevo Paciente</button>                  
            </div>
            <div class="col col-md-6 mb-3">
                <input style="text-align: center" type="text" onkeyup="SrchPac(this, 'est');" class="form-control" name="Bupac" id="Bupac" placeholder="Buscar Paciente..." required>
            </div>            
        </div>
    </div>
    <div id="srchPac">
    </div>
    <div id="step13" style="display: none">
        <form class="needs-validation" name="fors" novalidate method="POST" action="InsPac">
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
            <div class=" form-row">       
                <div class="col-md-3 mb-3">
                    <label>Fecha de Nacimiento</label>
                    <input style="text-align: center" type="date" class="form-control" name="fecha_nac" id="fecha_nac" required>
                    <div class="invalid-feedback">
                        Fecha invalida.
                    </div>
                </div><br>
                <div class="col-md-4 mb-3"><br>
                    <div class="col col-md-2 mb-3 custom-control custom-radio custom-control-inline">
                        <input id="mujer" class="custom-control-input" name="sexo" type="radio" required>
                        <label class="custom-control-label mb-3" for="mujer">Mujer</label>
                    </div>
                    <div class="col col-md-2 mb-3 custom-control custom-radio custom-control-inline">
                        <input id="hombre" class="custom-control-input" name="sexo" type="radio" required>
                        <label class="custom-control-label" for="hombre">Hombre</label>
                    </div>  
                </div>
                <div class="col-md-5">                
                    <div class="col-1 col-md-1 custom-control custom-radio custom-control-inline">                    
                    </div>
                    <div class="col col-md-2 mb-3 custom-control custom-radio custom-control-inline">
                        <input id="Yenv" class="custom-control-input" name='sma' type="radio" required>
                        <label class="custom-control-label" for="Yenv">Enviar correo</label>
                    </div>
                    <div class="col col-md-2 mb-3 custom-control custom-radio custom-control-inline">
                        <input id="Nenv" class="custom-control-input"  name='sma' type="radio" required>
                        <label class="custom-control-label" for="Nenv">No enviar correo</label>
                    </div>  
                </div>
            </div>
            <h6 style="text-align: center">Datos de contacto</h6>
            <hr class="mb-4">
            <div class="form-row">       
                <div class="col-md-4 mb-3 input-group">
                    <label class="sr-only" >Correo Electr�nico</label>
                    <div class="input-group-prepend">
                        <span class="input-group-text">@</span>
                    </div>
                    <input style="text-align: center" type="email" class="form-control" name="mail" id="mail" placeholder="Correo Electr�nico">            
                    <div class="invalid-feedback" style="width: 100%;">
                        Correo Electr�nico no valido.
                    </div>
                </div>
                <div class="col mb-3">
                    <label class="sr-only" >Tel�fono</label>
                    <input style="text-align: center" type="number" class="form-control" name="telefono" id="telefono" placeholder="Tel�fono">
                    <div class="invalid-feedback">
                        Numero de tel�fono obligatorio.
                    </div>
                </div>
                <div class="col mb-3">
                    <label class="sr-only" >Celular</label>
                    <input style="text-align: center" type="number" class="form-control" name="celular" id="celular" placeholder="Celular" >
                    <div class="invalid-feedback">
                        Numero de celular obligatorio.
                    </div>
                </div>
            </div>
            <h6 style="text-align: center; color: whitesmoke">Direcci�n</h6>
            <hr class="mb-4">
            <div class="form-row">       
                <div class="col mb-3">
                    <label class="sr-only" >C�digo Postal</label>
                    <input style="text-align: center" type="number" onchange="llenaColonia();" class="form-control" name="c_p" id="c_p" placeholder="C�digo Postal" >
                    <div class="invalid-feedback" style="width: 100%;">
                        C�digo Postal Obligatorio.
                    </div>
                </div>
                <div id="dir" class="col mb-3">
                    <label for="colonia" class="sr-only">Colonia</label>
                    <select class="custom-select d-block w-100" id="colonia" name="colonia" >
                        <option value="">Colonia</option>                    
                    </select>
                    <div class="invalid-feedback" style="width: 100%;">
                        Por favor seleccione una colonia.
                    </div>
                </div>
                <div class="col-md-5 mb-3">
                    <label class="sr-only" >Calle</label>
                    <input style="text-align: center" type="text" class="form-control" name="calle" id="calle" placeholder="Calle" >
                    <div class="invalid-feedback">
                        Se requiere un calle valida.
                    </div>
                </div>
                <div class="col mb-3">
                    <label class="sr-only" >No Int</label>
                    <input style="text-align: center" type="text" class="form-control" name="no_int" id="no_int" placeholder="No Int" >
                    <div class="invalid-feedback" style="width: 100%;">
                        Se requiere un No Int.
                    </div>
                </div>
                <div class="col mb-3">
                    <label class="sr-only" >No Ext</label>
                    <input style="text-align: center" type="text" class="form-control" name="no_ext" id="no_ext" placeholder="No Ext" >
                    <div class="invalid-feedback" style="width: 100%;">
                        Se requiere un No Ext.
                    </div>                    
                </div>
                <input type="hidden" name="formu" id="formu" value="Orden">
            </div>
            <hr class="mb-4">
            <input class="btn btn-primary btn-lg btn-block" onclick="val(this);" type="button" value="Registrar Nuevo Paciente">
            <br>
        </form>
    </div>
</div>
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>   