<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%@page import="DataTransferObject.Material_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Material_DAO"%>
<%  HttpSession sesion = request.getSession();
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        int id_unidad = Integer.parseInt(sesion.getAttribute("unidad").toString().trim());
        Material_DAO M = new Material_DAO();
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Material_DTO> mats;
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        if (sesion.getAttribute("matsU") != null) {
            mats = ((List<Material_DTO>) sesion.getAttribute("matsU"));
        } else {
            mats = M.getMaterialesByUnidad(id_unidad);
            sesion.setAttribute("matsU", mats);
        }
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link active" href="#" style="color: blue"><ins>Nueva Estudio</ins></a>
        <a class="nav-link" href="#/" onclick='mostrarForm("${pageContext.request.contextPath}/ShowEst");'>Lista de Estudios</a>                               
    </nav>
</div>   
<div class="container-fluid" style="color: white"><br>
    <form class="needs-validation" novalidate name="fors" method="POST" action="ProcesaEst">
        <h6 style="text-align: center">Datos del Estudio</h6>
        <hr class="mb-4">
        <div class="form-row">
            <div class="col-5 mb-3">
                <label class="sr-only" >Nombre del Estudio</label>
                <input style="text-align: center" type="text" class="form-control form-control-lg" name="nombre_estudio" id="nombre_estudio" placeholder="Nombre" required>
                <div class="invalid-feedback">
                    Se requiere un nombre de estudio valido.
                </div>
            </div>
            <div class="col-3 mb-3">
                <label class="sr-only" >Clave del Estudio</label>
                <input style="text-align: center" type="text" class="form-control form-control-lg" name="clave_estudio" id="clave_estudio" placeholder="Clave" required>
                <div class="invalid-feedback">
                    Se requiere una clave de estudio valido.
                </div>
            </div>
            <div class="col-md-4  mb-3">
                <label class="sr-only" >Metodología</label>
                <input style="text-align: center" type="text" class="form-control form-control-lg" name="Metodo" id="Metodo" placeholder="Metodología" required>
                <div class="invalid-feedback">
                    Se requiere una clave de estudio valido.
                </div>
            </div>
        </div>   
        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="sr-only" >Preparación</label>
                <textarea class="form-control" name="preparacion" id="preparacion"  placeholder="Preparación" required></textarea>
                <div class="invalid-feedback">
                    Escriba una preparación.
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <div>
                    <label class="sr-only" >Utilidad</label>
                    <input style="text-align: center" type="text" class="form-control form-control-sm" name="utilidad" id="utilidad" placeholder="Utilidad" required>
                    <div class="invalid-feedback">
                        Utlidad obligatoria.
                    </div>
                </div>
                <div>
                    <label for="Tipo_Estudio" class="sr-only">Tipo de Estudio</label><br>
                    <select class="custom-select d-block w-100 form-control-sm" id="Tipo_Estudio" name="Tipo_Estudio" required>
                        <option value="">Tipo de Estudio</option>   
                        <%for (Tipo_Estudio_DTO dto : tipos) {%>
                        <option value="<%=dto.getId_Tipo_Estudio()%>"><%=dto.getNombre_Tipo_Estudio().toUpperCase()%></option> 
                        <%}%>
                    </select>
                    <div class="invalid-feedback" style="width: 100%;">
                        Por favor seleccione un Tipo de Estudio.
                    </div>
                </div>
            </div>
        </div>
        <h6 style="text-align: center">Datos de Entrega</h6>
        <hr class="mb-4">
        <div class=" form-row">       
            <div class="col-xs-6 col-sm-6 col-md-3 mb-3 input-group">
                <label class="sr-only" >Dias Normal</label>           
                <input style="text-align: center" type="number" class="form-control" name="dias_n" id="dias_n" placeholder="Dias Normal" required="">            
                <div class="input-group-prepend">
                    <span class="input-group-text">Dias</span>
                </div>
                <div class="invalid-feedback" style="width: 100%;">
                    Número de días no valido.
                </div>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-3 mb-3 input-group">
                <label class="sr-only" >Precio Normal</label>           
                <input style="text-align: center" type="number" class="form-control" name="precio_n" id="precio_n" placeholder="Precio Normal" required="">            
                <div class="input-group-prepend">
                    <span class="input-group-text">Pesos</span>
                </div>
                <div class="invalid-feedback" style="width: 100%;">
                    Precio no valido.
                </div>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-3 mb-3 input-group">
                <label class="sr-only" >Dias Urgente</label>           
                <input style="text-align: center" type="number" class="form-control" name="dias_u" id="dias_u" placeholder="Dias Urgente" required>            
                <div class="input-group-prepend">
                    <span class="input-group-text">Dias</span>
                </div>
                <div class="invalid-feedback" style="width: 100%;">
                    Número de días no valido.
                </div>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-3 mb-3 input-group">
                <label class="sr-only" >Precio Urgente</label>           
                <input style="text-align: center" type="number" class="form-control" name="precio_u" id="precio_u" placeholder="Precio Urgente" required>            
                <div class="input-group-prepend">
                    <span class="input-group-text">Pesos</span>
                </div>
                <div class="invalid-feedback" style="width: 100%;">
                    Precio no valido.
                </div>
            </div>
        </div>
        <h6 style="text-align: center">Datos de Configuración</h6>
        <hr class="mb-4">
        <div class="form-row">
            <div class="col-md-4 mb-3">
                <label class="sr-only" >Descripción</label>
                <input style="text-align: center" type="text" class="form-control form-control-sm" name="desc" id="desc" placeholder="Descripción" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Descripción requerida.
                </div>
            </div>
            <div class="col-4 col-md-2 mb-3">  
                <label for="sexo" class="sr-only">Sexo</label>
                <select class="custom-select d-block w-100 form-control-sm" id="sexo" name="sexo" required>
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
                <label class="sr-only" >Valor Min</label>
                <input style="text-align: center" type="text" class="form-control form-control-sm" name="min" id="min" placeholder="Valor 1" >
                <div class="invalid-feedback">
                    Valor minimo obligatorio.
                </div>
            </div>
            <div class="col-4 col-md-2 mb-3">
                <label class="sr-only" >Valor Max</label>
                <input style="text-align: center" type="text" class="form-control form-control-sm" name="max" id="max" placeholder="Valor 2" >
                <div class="invalid-feedback">
                    Valor máximo obligatorio.
                </div>
            </div>
            <div class="col-md-2 mb-3">
                <label class="sr-only" >Unidades</label>
                <input style="text-align: center" type="text" class="form-control form-control-sm" name="unidades" id="unidades" placeholder="Unidades">
                <div class="invalid-feedback">
                    Unidades no validas
                </div>
            </div>
        </div>
        <div id="n-conf_1"><input type="hidden" name="nconf" id="nconf" value="1"></div>
        <div id="n-conf_2"><input type="hidden" name="nconf" id="nconf" value="2"></div>
        <div id="n-conf_3"><input type="hidden" name="nconf" id="nconf" value="3"></div>
        <div id="n-conf_4"><input type="hidden" name="nconf" id="nconf" value="4"></div>
        <div id="n-conf_5"><input type="hidden" name="nconf" id="nconf" value="5"></div>
        <div id="n-conf_6"><input type="hidden" name="nconf" id="nconf" value="6"></div>
        <div id="n-conf_7"><input type="hidden" name="nconf" id="nconf" value="7"></div>
        <div id="n-conf_8"><input type="hidden" name="nconf" id="nconf" value="8"></div>
        <div id="n-conf_9"><input type="hidden" name="nconf" id="nconf" value="9"></div>
        <div id="n-conf_10"><input type="hidden" name="nconf" id="nconf" value="10"></div>
        <div id="n-conf_11"><input type="hidden" name="nconf" id="nconf" value="11"></div>
        <div id="n-conf_12"><input type="hidden" name="nconf" id="nconf" value="12"></div>
        <div id="n-conf_13"><input type="hidden" name="nconf" id="nconf" value="13"></div>
        <div id="n-conf_14"><input type="hidden" name="nconf" id="nconf" value="14"></div>
        <div id="n-conf_15"><input type="hidden" name="nconf" id="nconf" value="15"></div>
        <div id="n-conf_16"><input type="hidden" name="nconf" id="nconf" value="16"></div>
        <div id="n-conf_17"><input type="hidden" name="nconf" id="nconf" value="17"></div>
        <div id="n-conf_18"><input type="hidden" name="nconf" id="nconf" value="18"></div>
        <div id="n-conf_19"><input type="hidden" name="nconf" id="nconf" value="19"></div>
        <div id="n-conf_20"><input type="hidden" name="nconf" id="nconf" value="20"></div>
        <div id="n-conf_21"><input type="hidden" name="nconf" id="nconf" value="21"></div>
        <div id="n-conf_22"><input type="hidden" name="nconf" id="nconf" value="22"></div>
        <div id="n-conf_23"><input type="hidden" name="nconf" id="nconf" value="23"></div>
        <div id="n-conf_24"><input type="hidden" name="nconf" id="nconf" value="24"></div>
        <div id="n-conf_25"><input type="hidden" name="nconf" id="nconf" value="25"></div>
        <div id="n-conf_26"><input type="hidden" name="nconf" id="nconf" value="26"></div>
        <div id="n-conf_27"><input type="hidden" name="nconf" id="nconf" value="27"></div>
        <div id="n-conf_28"><input type="hidden" name="nconf" id="nconf" value="28"></div>
        <div id="n-conf_29"><input type="hidden" name="nconf" id="nconf" value="29"></div>
        <div id="n-conf_30"><input type="hidden" name="nconf" id="nconf" value="30"></div>        
        <input type="button" class="btn btn-success col-md-12" onclick="MostrarConf('Menu/Configuracion/newConf.jsp');" value="Agregar otra configuración">        
        <hr class="mb-4">
        <h6 style="text-align: center">Materiales requeridos</h6>
        <hr class="mb-4">
        <div class="form-row">       
            <div class="col-md-3 mb-3">
                <label for="mat" class="sr-only">Material</label>
                <select class="custom-select d-block w-100 form-control-sm" id="mat" name="mat" required>
                    <option value="">Material</option>   
                    <%for (Material_DTO dto : mats) {%>
                    <option value="<%=dto.getId_Unid_Mat()%>"><%=dto.getNombre_Material().toUpperCase()%></option> 
                    <%}%>
                </select>
                <div class="invalid-feedback" style="width: 100%;">
                    Por favor seleccione un material.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Unidad</label>
                <input style="text-align: center" type="text" class="form-control form-control-sm" name="unidad" id="unidad" placeholder="Unidad" required>
                <div class="invalid-feedback">
                    Escriba una unidad de medida.
                </div>
            </div>
            <div class="col mb-3">
                <label class="sr-only" >Cantidad</label>
                <input style="text-align: center" type="number" class="form-control form-control-sm" name="cant" id="cant" placeholder="Cantidad" required>
                <div class="invalid-feedback">
                    Esciba una cantidad mayor a 0.
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <label class="sr-only" >Tipo de muestra</label>
                <input style="text-align: center" type="text" class="form-control form-control-sm" name="muestra" id="muestra" placeholder="Tipo de muestra" required>            
                <div class="invalid-feedback" style="width: 100%;">
                    Tipo de muestra requerido.
                </div>
            </div>
        </div>
        <div id="n-mat_1"><input type="hidden" name="nmat" id="nmat" value="1"></div>
        <div id="n-mat_2"><input type="hidden" name="nmat" id="nmat" value="2"></div>
        <div id="n-mat_3"><input type="hidden" name="nmat" id="nmat" value="3"></div>
        <div id="n-mat_4"><input type="hidden" name="nmat" id="nmat" value="4"></div>
        <div id="n-mat_5"><input type="hidden" name="nmat" id="nmat" value="5"></div>
        <div id="n-mat_6"><input type="hidden" name="nmat" id="nmat" value="6"></div>
        <div id="n-mat_7"><input type="hidden" name="nmat" id="nmat" value="7"></div>
        <div id="n-mat_8"><input type="hidden" name="nmat" id="nmat" value="8"></div>
        <div id="n-mat_9"><input type="hidden" name="nmat" id="nmat" value="9"></div>
        <div id="n-mat_10"><input type="hidden" name="nmat" id="nmat" value="10"></div>
        <div id="n-mat_11"><input type="hidden" name="nmat" id="nmat" value="11"></div>
        <div id="n-mat_12"><input type="hidden" name="nmat" id="nmat" value="12"></div>
        <div id="n-mat_13"><input type="hidden" name="nmat" id="nmat" value="13"></div>
        <div id="n-mat_14"><input type="hidden" name="nmat" id="nmat" value="14"></div>
        <div id="n-mat_15"><input type="hidden" name="nmat" id="nmat" value="15"></div>
        <div id="n-mat_16"><input type="hidden" name="nmat" id="nmat" value="16"></div>
        <div id="n-mat_17"><input type="hidden" name="nmat" id="nmat" value="17"></div>
        <div id="n-mat_18"><input type="hidden" name="nmat" id="nmat" value="18"></div>
        <div id="n-mat_19"><input type="hidden" name="nmat" id="nmat" value="19"></div>
        <div id="n-mat_20"><input type="hidden" name="nmat" id="nmat" value="20"></div>
        <input type="button" class="btn btn-success col-md-12" onclick="MostrarMat('Menu/Material/addMat.jsp');" value="Agregar material">        
        <hr class="mb-4">
        <input class="btn btn-primary btn-lg btn-block" onclick="val(this);" type="button" value="Registrar Nuevo Estudio">
        <br>
    </form>
</div>
<%} else {
        response.sendRedirect("" + request.getContextPath() + "");
    }%>