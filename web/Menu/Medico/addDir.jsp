<%-- 
    Document   : addDir
    Created on : 15/04/2018, 12:49:05 PM
    Author     : ZionSystems
--%>
<%
    HttpSession sesion = request.getSession();
    int nc = Integer.parseInt(request.getParameter("ndir"));
    int n = Integer.parseInt(request.getParameter("nc"));

    if (sesion.getAttribute("ndir") != null) {
        nc = nc + Integer.parseInt(sesion.getAttribute("ndir").toString().trim());
        sesion.setAttribute("ndir", nc);
        sesion.setAttribute("nd", n);
    } else {
        sesion.setAttribute("ndir", nc);
        sesion.setAttribute("nd", n);
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<hr class="mb-4">
<div class="form-row">       
    <div class="col mb-3">
        <label class="sr-only" >Código Postal</label>
        <input style="text-align: center" type="number" onchange="llenaColonia();" class="form-control form-control-sm" name="c_p" id="c_p" placeholder="Código Postal" required>
        <div class="invalid-feedback" style="width: 100%;">
            Código Postal Obligatorio.
        </div>
    </div>
    <div id="dir" class="col mb-3">
        <label for="colonia" class="sr-only">Colonia</label>
        <select class="custom-select d-block w-100 form-control-sm" id="colonia" name="colonia" required>
            <option value="">Colonia</option>                    
        </select>
        <div class="invalid-feedback" style="width: 100%;">
            Por favor seleccione una colonia.
        </div>
    </div>
    <div class="col-md-4 mb-3">
        <label class="sr-only" >Calle</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="calle" id="calle" placeholder="Calle" required>
        <div class="invalid-feedback">
            Se requiere un calle valida.
        </div>
    </div>
    <div class="col mb-3">
        <label class="sr-only" >No Int</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="no_int" id="no_int" placeholder="No Int" required>
        <div class="invalid-feedback" style="width: 100%;">
            Se requiere un No Int.
        </div>
    </div>
    <div class="col mb-3">
        <label class="sr-only" >No Ext</label>
        <input style="text-align: center" type="number" class="form-control form-control-sm" name="no_ext" id="no_ext" placeholder="No Ext" required>
        <div class="invalid-feedback" style="width: 100%;">
            Se requiere un No Ext.
        </div>
    </div>
    <div class="col-2 col-md-1 mb-3">
        <input type="button" class="btn btn-danger btn-sm col-md-12" onclick="QuitarConf('Menu/Configuracion/delConf.jsp',<%=n%>);" value="X">
    </div>
</div>