<%@page import="DataTransferObject.Material_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Material_DAO"%>
<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int nc = Integer.parseInt(request.getParameter("nmat"));
        int n = Integer.parseInt(request.getParameter("nm"));
       
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        Material_DAO M = new Material_DAO();
        List<Material_DTO> matsU;
        if (sesion.getAttribute("matsU") != null) {
            matsU = ((List<Material_DTO>) sesion.getAttribute("matsU"));
        } else {
            matsU = M.getMaterialesByUnidad(id_unidad);
            sesion.setAttribute("matsU", matsU);
        }
        
        if (sesion.getAttribute("nmat") != null) {
            nc = nc + Integer.parseInt(sesion.getAttribute("nmat").toString().trim());
            sesion.setAttribute("nmat", nc);
            sesion.setAttribute("nm", n);
        } else {
            sesion.setAttribute("nmat", nc);
            sesion.setAttribute("nm", n);
        }

%>
<hr class="mb-4">
<div class="form-row">       
    <div class="col-md-3 mb-3">
        <label for="mat" class="sr-only">Material</label>
        <select class="custom-select d-block w-100 form-control-sm" id="mat<%=nc%>" name="mat<%=nc%>" required>
            <option value="">Material</option>   
            <%for (Material_DTO dto : matsU) {%>
            <option value="<%=dto.getId_Unid_Mat()%>"><%=dto.getNombre_Material().toUpperCase()%></option> 
            <%}%>
        </select>
        <div class="invalid-feedback" style="width: 100%;">
            Por favor seleccione un material.
        </div>
    </div>
    <div class="col-6 col-md-2 mb-3">
        <label class="sr-only" >Unidad</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="unidad<%=nc%>" id="unidad<%=nc%>" placeholder="Unidad">
        <div class="invalid-feedback">
            Escriba una unidad de medida.
        </div>
    </div>
    <div class="col-6 col-md-2 mb-3">
        <label class="sr-only" >Cantidad</label>
        <input style="text-align: center" type="number" class="form-control form-control-sm" name="cant<%=nc%>" id="cant<%=nc%>" placeholder="Cantidad" required>
        <div class="invalid-feedback">
            Esciba una cantidad mayor a 0.
        </div>
    </div>
    <div class="col-10 col-md-4 mb-3">
        <label class="sr-only" >Tipo de muestra</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="muestra<%=nc%>" id="muestra<%=nc%>" placeholder="Tipo de muestra">            
        <div class="invalid-feedback" style="width: 100%;">
            Tipo de muestra requerido.
        </div>
    </div>
    <div class="col-2 col-md-1 mb-3">
        <input type="button" class="btn btn-danger btn-sm col-md-12" onclick="QuitarMat('Menu/Material/delMat.jsp',<%=n%>);" value="X">
    </div>
</div>
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice y realice nuevamente el proceso necesario</h1>");
    }%>