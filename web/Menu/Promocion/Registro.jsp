<%@page import="DataTransferObject.Persona_DTO"%>
<%@page import="DataAccesObject.Persona_DAO"%>
<%@page import="DataTransferObject.Promocion_DTO"%>
<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%@page import="DataTransferObject.Material_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Material_DAO"%>
<%  HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        Promocion_DTO Prom = new Promocion_DTO();
        Prom.setId_Unidad(id_unidad);
        Persona_DAO P = new Persona_DAO();
        Persona_DTO por = P.getPersona(Integer.parseInt(sesion.getAttribute("persona").toString().trim()));
        Prom.setEmpleado(por);
        Prom.setEstado("Activa");
        sesion.setAttribute("Promocion", Prom);
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link active" href="#" style="color: blue"><ins>Nueva Promoción</ins></a>
        <a class="nav-link" href="#/" onclick='mostrarForm("${pageContext.request.contextPath}/ShowEst");'>Lista de Promociones</a>                               
    </nav>
</div>   
<div class="container-fluid" style="color: white"><br>
    <form class="needs-validation" novalidate name="fors" method="POST" action="ProcesaProm">
        <h6 style="text-align: center">Datos de Promoción</h6>
        <hr class="mb-4">
        <div class="form-row">
            <div class=" offset-3 col-6 mb-3">
                <label class="sr-only" >Título de Promoción</label>
                <input style="text-align: center" type="text" class="form-control form-control-lg" name="nameProm" id="nameProm" placeholder="Título de Promoción" required>
                <div class="invalid-feedback">
                    Se requiere un Título para la promoción.
                </div>
            </div>            
        </div> 
        <div class="row" style="color: white">
            <div class="col-md-6 mb-3">
                <label class="sr-only" >Descripción</label>
                <textarea rows="5" class="form-control" name="descProm" id="descProm"  placeholder="Descripción de Promoción" required></textarea>
                <div class="invalid-feedback">
                    Escriba una descripción.
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div>
                    <label style="text-align: center; color: white">Fecha Inicio</label>
                    <input style="text-align: center" type="date" class="form-control form-control-sm" name="fechaI" id="fechaI" required>
                    <div class="invalid-feedback">
                        Fecha de Inicio obligatoria.
                    </div>
                </div>
                <div>
                    <label style="text-align: center; color: white">Fecha Termino</label>
                    <input style="text-align: center" type="date" class="form-control form-control-sm" name="fechaF" id="fechaF" required>
                    <div class="invalid-feedback">
                        Fecha de Termino  obligatoria.
                    </div>
                </div>
            </div>
        </div>
    </form>
    <h6 style="text-align: center; color: white">Estudios de la Promoción</h6>
    <hr class="mb-4">        
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
            <input style="text-align: center" type="text" class="form-control" onkeyup="test22(this, 'Prom');" name="clave_mat" id="clave_mat" placeholder="Buscar..." required>
            <div class="invalid-feedback">
                Ingresa un criterio de busqueda.
            </div>
        </div>              
    </div>
    <div id="EstsAdded">
        <div id="BEst"></div>
    </div>
</div>  
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>