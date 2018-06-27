<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">    
        <a class="nav-link active" href="#/" style="color: blue"><ins>Nuevo Empleado</ins></a>
        <a class="nav-link" href="#/" onclick='mostrarForm("${pageContext.request.contextPath}/ShowEmpl");'>Ver Empleados</a>                                        
    </nav>
</div>   
<div class="container-fluid" style="color: white"><br>
    <form class="needs-validation" novalidate name="fors" method="POST" action="ProcesaEmp"> 
        <h6 style="text-align: center">Datos de Empleado</h6>
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
        <div class="form-row">       
            <div class="col-md-6 mb-3">
                <label class="sr-only" >CURP</label>
                <input style="text-align: center" type="text" class="form-control" name="curp" id="curp" placeholder="CURP" required>
                <div class="invalid-feedback">
                    Se requiere un curp valido.
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <label class="sr-only" >NSS</label>
                <input style="text-align: center" type="text" class="form-control" name="nss" id="nss" placeholder="No de Seguro Social" required>
                <div class="invalid-feedback">
                    Se requiere un apellido valido.
                </div>
            </div>
        </div>
        <h6 style="text-align: center">Datos de contacto</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col-md-4 mb-3 input-group">
                <label class="sr-only" >Correo Electrónico</label>
                <div class="input-group-prepend">
                    <span class="input-group-text">@</span>
                </div>
                <input style="text-align: center" type="email" class="form-control" name="mail" id="mail" placeholder="Correo Electrónico">            
                <div class="invalid-feedback" style="width: 100%;">
                    Correo Electrónico no valido.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Teléfono</label>
                <input style="text-align: center" type="number" class="form-control" name="telefono" id="telefono" placeholder="Teléfono">
                <div class="invalid-feedback">
                    Numero de teléfono obligatorio.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Celular</label>
                <input style="text-align: center" type="number" class="form-control" name="celular" id="celular" placeholder="Celular" required>
                <div class="invalid-feedback">
                    Numero de celular obligatorio.
                </div>
            </div>
        </div>
        <h6 style="text-align: center; color: whitesmoke">Dirección</h6>
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
                <input style="text-align: center" type="text" class="form-control" name="no_int" id="no_int" placeholder="No Int" required>
                <div class="invalid-feedback" style="width: 100%;">
                    Se requiere un No Int.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >No Ext</label>
                <input style="text-align: center" type="text" class="form-control" name="no_ext" id="no_ext" placeholder="No Ext" required>
                <div class="invalid-feedback" style="width: 100%;">
                    Se requiere un No Ext.
                </div>
            </div>
        </div>
        <h6 style="text-align: center">Datos Laborales</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col mb-3">
                <label>Fecha de Ingreso</label>
                <input style="text-align: center" type="date" class="form-control" name="fechaIng" id="fechaIng" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Fecha no valida.
                </div>
            </div>
            <div class="col mb-3">
                <label>Salario</label>
                <input style="text-align: center" type="number" step="any" class="form-control" name="salario" id="salario" placeholder="Salario" required>
                <div class="invalid-feedback">
                    Salario obligatorio.
                </div>
            </div>
        </div>
        <h6 style="text-align: center">Dias a trabajar</h6>
        <hr class="mb-4">
        <div class="form-row">
            <div class="col custom-control custom-checkbox form-check-inline" >        
                <input type="checkbox" class="custom-control-input" name="dia" id="lunes" value="1">
                <label class="col custom-control-label" for="lunes">&emsp13;Lunes</label>
            </div>
            <div class="col custom-control custom-checkbox form-check-inline">        
                <input class="custom-control-input" type="checkbox" name="dia" id="martes" value="2">
                <label class="col custom-control-label" for="martes">&emsp13;Martes</label>
            </div>
            <div class="col custom-control custom-checkbox form-check-inline">        
                <input class="custom-control-input" type="checkbox" name="dia" id="miercoles" value="3">
                <label class="col custom-control-label" for="miercoles">Miércoles</label>
            </div>    
            <div class="col custom-control custom-checkbox form-check-inline">        
                <input class="custom-control-input" type="checkbox" name="dia" id="jueves" value="4">
                <label class="col custom-control-label" for="jueves">&emsp13;Jueves</label>
            </div>     
            <div class="col custom-control custom-checkbox form-check-inline">        
                <input class="custom-control-input" type="checkbox" name="dia" id="viernes" value="5">
                <label class="col custom-control-label" for="viernes">&emsp13;Viernes</label>
            </div>      
            <div class="col custom-control custom-checkbox form-check-inline">        
                <input class="custom-control-input" type="checkbox" name="dia" id="sabado" value="6">
                <label class="col custom-control-label" for="sabado">&emsp13;Sábado</label>
            </div>    
            <div class="col custom-control custom-checkbox form-check-inline">        
                <input class="custom-control-input" type="checkbox" name="dia" id="domingo" value="7">
                <label class="col custom-control-label" for="domingo">&emsp13;Domingo</label>
            </div>
        </div>
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
        <h6 style="text-align: center">Horarios</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col-3 mb-3">
                <label>Hora de entrada</label>
                <input style="text-align: center" type="time" class="form-control" name="hora_e" id="hora_e" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Hora no valida.
                </div>
            </div>
            <div class="col-3 mb-3">
                <label>Hora de salida</label>
                <input style="text-align: center" type="time" class="form-control" name="hora_s" id="hora_s" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Hora no valida.
                </div>
            </div>
            <div class="col-3 mb-3">
                <label>Hora de comida</label>
                <input style="text-align: center" type="time" class="form-control" name="hora_c" id="hora_c"  required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Hora no valida.
                </div>
            </div>
            <div class="col-3 mb-3">
                <label>Hora de regreso</label>
                <input style="text-align: center" type="time" class="form-control" name="hora_r" id="hora_r" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Hora no valida.
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
        <hr class="mb-4">
        <input class="btn btn-primary btn-lg btn-block" onclick="val(this);" type="button" value="Registrar Nuevo Empleado">
        <br>
    </form>
</div>
</div>
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice y realice nuevamente el proceso necesario</h1>");
    }%>