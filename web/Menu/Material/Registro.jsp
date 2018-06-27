<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link active" href="#" style="color:#007bff"><ins>Nuevo Material</ins></a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowMats");'>Ver Materiales</a> 
    </nav>
</div>
<div class="container-fluid" style="color: white"><br>
    <form class="needs-validation" novalidate name="fors" method="POST" action="ProcesaMat">
        <h6 style="text-align: center">Datos del Material</h6>
        <hr class="mb-4">
        <div class="form-row">
            <div class="col-md-2 mb-3">
                <label class="sr-only" >Clave</label>
                <input style="text-align: center" type="text" class="form-control" name="clave_mat" id="clave_mat" placeholder="Clave del Material" required>
                <div class="invalid-feedback">
                    La clave del material es obligatorio.
                </div>
            </div>
            <div class="col-md-4 mb-3">
                <label class="sr-only" >Nombre del Material</label>
                <input style="text-align: center" type="text" class="form-control" name="nombre_material" id="nombre_material" placeholder="Nombre del Material" required>
                <div class="invalid-feedback">
                    El nombre del material es obligatorio.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Cantidad</label>
                <input style="text-align: center" type="number" onkeypress="return soloNumeros(event)" min="1" class="form-control" name="cantidad" id="cantidad" placeholder="Cantidad" required>
                <div class="invalid-feedback">
                    Se requiere una cantidad mayor a 0.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Precio</label>
                <input style="text-align: center" type="number" onkeypress="return soloNumeros(event)" class="form-control" name="precio" id="precio" placeholder="Precio" required>
                <div class="invalid-feedback">
                    Se requiere un precio valido.
                </div>
            </div>
        </div>
        <hr class="mb-4">
        <input class="btn btn-primary btn-lg btn-block" type="button" id="sendForm" onclick="val(this);" name="sendForm" value="Registrar Nuevo Material">
        <br>
    </form>
</div>
<%} else {
        out.print("<h1 style='color:white'>Por seguridad la sesión ha caducado: Actualice y realice nuevamente el proceso necesario</h1>");;
    }%>