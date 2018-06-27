<%
    HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int nc = Integer.parseInt(request.getParameter("nconf"));
        int n = Integer.parseInt(request.getParameter("nc"));

        if (sesion.getAttribute("nconf") != null) {
            nc = nc + Integer.parseInt(sesion.getAttribute("nconf").toString().trim());
            sesion.setAttribute("nconf", nc);
            sesion.setAttribute("nc", n);
        } else {
            sesion.setAttribute("nconf", nc);
            sesion.setAttribute("nc", n);
        }
%>
<hr class="mb-4">
<div class="form-row" >
    <div class="col-md-4 mb-3">
        <label class="sr-only" >Descripción</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="desc<%=nc%>" id="desc<%=nc%>" placeholder="Descripción" required/>            
        <div class="invalid-feedback" style="width: 100%;">
            Descripción requerida.
        </div>
    </div>
    <div class="col-4 col-md-2 mb-3">
        <label for="sexo" class="sr-only">Sexo</label>
        <select class="custom-select d-block w-100 form-control-sm" id="sexo<%=nc%>" name="sexo<%=nc%>" required>
            <option value="">Sexo</option> 
            <option value="all-Femenino">TODOS Femenino</option>                       
            <option value="all-Masculino">TODOS Masculino</option>                       
            <option value="all-Ambos">TODOS Ambos</option>
            <option value="3E-Femenino">3ra Edad Femenino</option>                       
            <option value="3E-Masculino">3ra Edad Masculino</option>                       
            <option value="3E-Ambos">3ra Edad Ambos</option>   
            <option value="A-Femenino">Adulto Femenino</option>                       
            <option value="A-Masculino">Adulto Masculino</option>                       
            <option value="A-Ambos">Adulto Ambos</option>                       
            <option value="N-Masculino">Niño Femenino</option>                       
            <option value="N-Masculino">Niño Masculino</option>                       
            <option value="N-Ambos">Niño Ambos</option>                       
            <option value="RN-Masculino">Rec Nac Femenino</option>                       
            <option value="RN-Masculino">Rec Nac Masculino</option>                       
            <option value="RN-Ambos">Rec Nac Ambos</option>                        
        </select>
        <div class="invalid-feedback" style="width: 100%;">
            Por favor seleccione un Sexo.
        </div>
    </div>
    <div class="col-4 col-md-2 mb-3">
        <label class="sr-only" >Valor 1</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="min<%=nc%>" id="min<%=nc%>" placeholder="Valor 1" />
        <div class="invalid-feedback">
            Valor minimo obligatorio.
        </div>
    </div>
    <div class="col-4 col-md-2 mb-3">
        <label class="sr-only" >Valor 2</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="max<%=nc%>" id="max<%=nc%>" placeholder="Valor 2" />
        <div class="invalid-feedback">
            Valor máximo obligatorio.
        </div>
    </div>
    <div class="col-10 col-md-1 mb-3">
        <label class="sr-only" >Unidades</label>
        <input style="text-align: center" type="text" class="form-control form-control-sm" name="unidades<%=nc%>" id="unidades<%=nc%>" placeholder="Unidades">
        <div class="invalid-feedback">
            Unidades no validas
        </div>
    </div>
    <div class="col-2 col-md-1 mb-3">
        <input type="button" class="btn btn-danger btn-sm col-md-12" onclick="QuitarConf('Menu/Configuracion/delConf.jsp',<%=n%>);" value="X">
    </div>
</div>
<%} else {
        out.print("<h1 style='color:white'>La sesión ha caducado, Actualice el sistema y realice nuevamente el proceso necesario</h1>");
    }%>