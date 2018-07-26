<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link active" href="#" style="color: blue"><ins>Nuevo Médico</ins></a>
        <a class="nav-link" href="#/" onclick='mostrarForm("${pageContext.request.contextPath}/ShowMedi");'>Ver Médicos</a>                                                               
    </nav>
</div>   
<div class="container-fluid" style="color: white"><br>
    <form class="needs-validation" novalidate name="fors" method="POST" action="ProcesaMed"> 
        <h6 style="text-align: center">Datos de Médico</h6>
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
                <input style="text-align: center" type="number" class="form-control" name="celular" id="celular" placeholder="Celular">
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
                <input style="text-align: center" type="number" onchange="llenaColonia();" class="form-control" name="c_p" id="c_p" placeholder="Código Postal">
                <div class="invalid-feedback" style="width: 100%;">
                    Código Postal Obligatorio.
                </div>
            </div>
            <div id="dir" class="col mb-3">
                <label for="colonia" class="sr-only">Colonia</label>
                <select class="custom-select d-block w-100" id="colonia" name="colonia">
                    <option value="">Colonia</option>                    
                </select>
                <div class="invalid-feedback" style="width: 100%;">
                    Por favor seleccione una colonia.
                </div>
            </div>
            <div class="col-md-5 mb-3">
                <label class="sr-only" >Calle</label>
                <input style="text-align: center" type="text" class="form-control" name="calle" id="calle" placeholder="Calle">
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
        <div id="n-dir_1"><input type="hidden" name="ndir" id="ndir" value="1"></div>
        <div id="n-dir_2"><input type="hidden" name="ndir" id="ndir" value="2"></div>
        <div id="n-dir_3"><input type="hidden" name="ndir" id="ndir" value="3"></div>
        <div id="n-dir_4"><input type="hidden" name="ndir" id="ndir" value="4"></div>
        <div id="n-dir_5"><input type="hidden" name="ndir" id="ndir" value="5"></div>
        <div id="n-dir_6"><input type="hidden" name="ndir" id="ndir" value="6"></div>
        <div id="n-dir_7"><input type="hidden" name="ndir" id="ndir" value="7"></div>
        <div id="n-dir_8"><input type="hidden" name="ndir" id="ndir" value="8"></div>
        <div id="n-dir_9"><input type="hidden" name="ndir" id="ndir" value="9"></div>
        <div id="n-dir_10"><input type="hidden" name="ndir" id="ndir" value="10"></div>
        <!-- <input type="button" class="btn btn-success col-md-12" onclick="MostrarDir('Menu/Medico/addDir.jsp');" value="Agregar otra Dirección">  -->
        <hr class="mb-4">
        <h6 style="text-align: center">Datos de Referencia</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col-md-4 mb-3">
                <label class="sr-only">Empresa</label>
                <input style="text-align: center" type="text" class="form-control" name="empresa" id="empresa" placeholder="Empresa" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Empresa obligatoria.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only">Participación</label>
                <input style="text-align: center" type="number" step="any" class="form-control" name="participacion" id="participacion" placeholder="Participación" required>
                <div class="invalid-feedback">
                    Participación requerdia.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only">Descuento</label>
                <input style="text-align: center" type="number" class="form-control" name="descuento" id="descuento" placeholder="Descuento" required>
                <div class="invalid-feedback">
                    Descuento requerido.
                </div>
            </div>
        </div>
        <hr class="mb-4">
        <input class="btn btn-primary btn-lg btn-block" onclick="val(this);" type="button" value="Registrar Nuevo Médico">
        <br>
    </form>
</div>
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>