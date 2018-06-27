<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link active" href="#" style="color:#007bff"><ins>Nueva Unidad</ins></a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShUnid");'>Ver Unidades</a> 
    </nav>
</div>   
<div class="container-fluid"><br>
    <form accept-charset="UTF-8" class="needs-validation"  novalidate name="fors" method="POST" action="ProcesaUnid">
        <h6 style="text-align: center; color: whitesmoke">Datos de la unidad</h6>
        <hr class="mb-4">
        <div class="form-row">
            <div class="col mb-3">
                <label class="sr-only" >Nombre de la unidad</label>
                <input style="text-align: center" type="text" class="form-control form-control-lg" name="nombre_unidad" id="nombre_unidad" placeholder="Nombre de la unidad" required>
                <div class="invalid-feedback">
                    El nombre de la unidad es obligatorio.
                </div>
            </div>
            <div class="col-3 mb-3">
                <label class="sr-only" >Nombre de la unidad</label>
                <input style="text-align: center" type="text" class="form-control form-control-lg" name="clave_unidad" id="clave_unidad" placeholder="Clave" required>
                <div class="invalid-feedback">
                    El nombre de la unidad es obligatorio.
                </div>
            </div>
        </div>   
        <h6 style="text-align: center; color: whitesmoke">Datos de la persona encargada</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col-md-4 mb-3">
                <label class="sr-only" >Nombre de Encargad@</label>
                <input style="text-align: center" type="text" class="form-control" name="nombre_persona" id="nombre_persona" placeholder="Nombre" required>
                <div class="invalid-feedback">
                    Se requiere un nombre valido.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >A Paterno de Encargad@</label>
                <input style="text-align: center" type="text" class="form-control" name="a_paterno" id="a_paterno" placeholder="Apellido Paterno" required>
                <div class="invalid-feedback">
                    Se requiere un apellido valido.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >A Materno de Encargad@</label>
                <input style="text-align: center" type="text" class="form-control" name="a_materno" id="a_materno" placeholder="Apellido Materno" required>
                <div class="invalid-feedback">
                    Se requiere un segundo apellido valido.
                </div>
            </div>
        </div>
        <h6 style="text-align: center; color: whitesmoke">Datos de contacto de la Unidad</h6>
        <hr class="mb-4">
        <div class="row">       
            <div class="col-md-6 mb-3 input-group">
                <label class="sr-only" >Correo Electrónico</label>
                <div class="input-group-prepend">
                    <span class="input-group-text">@</span>
                </div>
                <input style="text-align: center" type="email" class="form-control" name="mail" id="mail" placeholder="Correo Electrónico" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Correo Electrónico no valido.
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <label class="sr-only" >Teléfono</label>
                <input style="text-align: center" type="number" class="form-control" name="telefono" id="telefono" placeholder="Teléfono" required>
                <div class="invalid-feedback">
                    Numero de teléfono obligatorio.
                </div>
            </div>
        </div>
        <h6 style="text-align: center; color: whitesmoke">Dirección de la Unidad</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col mb-3">
                <label class="sr-only" >Código Postal</label>
                <input style="text-align: center" type="number" onchange="llenaColonia();" class="form-control" name="c_p" id="c_p" placeholder="Código Postal" required>
                <div class="invalid-feedback" style="width: 100%;">
                    Código Postal Obligatorio.
                </div>
            </div>
            <div id="dir" class="col mb-3">
                <label for="colonia" class="sr-only">Colonia</label>
                <select class="custom-select d-block w-100" id="colonia" name="colonia" required>
                    <option value="">Colonia</option>                    
                </select>
                <div class="invalid-feedback" style="width: 100%;">
                    Por favor seleccione una colonia.
                </div>
            </div>
            <div class="col-md-5 mb-3">
                <label class="sr-only" >Calle</label>
                <input style="text-align: center" type="text" class="form-control" name="calle" id="calle" placeholder="Calle" required>
                <div class="invalid-feedback">
                    Se requiere un calle valida.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >No Int</label>
                <input style="text-align: center" type="text" class="form-control" name="no_int" id="no_int" placeholder="No Int">
                <div class="invalid-feedback" style="width: 100%;">
                    Se requiere un No Int.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >No Ext</label>
                <input style="text-align: center" type="text" class="form-control" name="no_ext" id="no_ext" placeholder="No Ext">
                <div class="invalid-feedback" style="width: 100%;">
                    Se requiere un No Ext.
                </div>
            </div>
        </div>
        <h6 style="text-align: center; color: whitesmoke">Datos de acceso</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col mb-3">
                <label class="sr-only" >Correo Electrónico</label>
                <input style="text-align: center" type="text" class="form-control" name="usuario" id="usuario" placeholder="Usuario" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Usuario invalido.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Contraseña</label>
                <input style="text-align: center" type="text" class="form-control" name="pass" id="pass" placeholder="Contraseña" required>
                <div class="invalid-feedback">
                    Contraseña invalida.
                </div>
            </div>
            <input type="hidden" class="form-control" name="hid" id="hid">
        </div>
    </form>
    <form>
    <h6 style="text-align: center; color: white">Permisos</h6>
    <hr class="mb-4">
    <div class="form-row" style="color: white">
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="reportes" value="2">
            <label class="col custom-control-label" for="reportes">Reportes</label>
        </div>
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="material" value="3">
            <label class="col custom-control-label" for="material">Materiales</label>
        </div>    
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="estudios" value="4">
            <label class="col custom-control-label" for="estudios">&emsp13;Estudios</label>
        </div>     
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="empleados" value="5">
            <label class="col custom-control-label" for="empleados">Empleados</label>
        </div>      
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="medicos" value="6">
            <label class="col custom-control-label" for="medicos">&emsp13;Médicos</label>
        </div>    
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="pacientes" value="7">
            <label class="col custom-control-label" for="pacientes">&emsp13;Pacientes</label>
        </div>
        <div class="col custom-control custom-checkbox form-check-inline">        
            <input class="custom-control-input" type="checkbox" name="permisos" id="ordenes" value="8">
            <label class="col custom-control-label" for="ordenes">&emsp13;Órdenes</label>
        </div>
    </div>
    </form>
    <hr class="mb-4">
    <input class="btn btn-primary btn-lg btn-block" onclick="val(this);" type="button" value="Registrar Nueva Unidad">
    <br>    
</div>
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>