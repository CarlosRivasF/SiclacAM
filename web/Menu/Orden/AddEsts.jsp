<%@page import="DataAccesObject.Orden_DAO"%>
<%@page import="DataTransferObject.Det_Orden_DTO"%>
<%@page import="java.time.Period"%>
<%@page import="DataTransferObject.Orden_DTO"%>
<%@page import="DataBase.Util"%>
<%@page import="DataTransferObject.Paciente_DTO"%>
<%@page import="DataTransferObject.Tipo_Estudio_DTO"%>
<%@page import="java.util.List"%>
<%@page import="DataAccesObject.Tipo_Estudio_DAO"%>
<%
    HttpSession sesion = request.getSession();
    Orden_DTO Orden = null;
    if (sesion.getAttribute("user") != null && sesion.getAttribute("unidad") != null) {
        Boolean modify = false;
        if (request.getParameter("id_Orden") != null) {
            int id_Orden = Integer.parseInt(request.getParameter("id_Orden").trim());
            Orden_DAO O = new Orden_DAO();
            Orden = O.getOrden(id_Orden);
            modify = true;
            sesion.setAttribute("VarmodOrd", modify);
            sesion.setAttribute("Orden", Orden);
            modify = true;
        }
        Tipo_Estudio_DAO TE = new Tipo_Estudio_DAO();
        List<Tipo_Estudio_DTO> tipos = TE.getTipo_Estudios();
        Orden = (Orden_DTO) sesion.getAttribute("Orden");
        Paciente_DTO pac = Orden.getPaciente();
        Util f = new Util();
%>
<div class="nav-scroller bg-white box-shadow">
    <nav class="nav nav-underline">        
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Orden/Registro.jsp');">Nueva �rden</a>
        <a class="nav-link" href="#" onclick="mostrarForm('Menu/Cotizacion/Registro.jsp');">Nueva Cotizaci�n</a>
        <a class="nav-link" href="#" onclick='mostrarForm("${pageContext.request.contextPath}/ShowOrds?mode=ord");'>Ver �rdenes</a>                                             
    </nav>
</div>
<div class="container-fluid" style="color: white"><hr>
    <div id="step1">
        <h6 style="text-align: center">Datos de Paciente</h6><br>  
        <div class="form-row">
            <div class="col-8 col-sm-5 col-md-4">
                <label><strong>Nombre: </strong><%=pac.getNombre() + " " + pac.getAp_Paterno() + " " + pac.getAp_Materno()%></label>
            </div>
            <div class=" col-4 col-sm-3 col-md-3">
                <%Period edad = f.getEdad(pac.getFecha_Nac().trim());%>
                <label><strong>Edad: </strong><%=edad.getYears()%> a�os , <%=edad.getMonths()%> meses y <%=edad.getDays()%> d�as</label>
            </div>
            <div class="col-6 col-sm-2 col-md-2">
                <label><strong>Sexo: </strong><%=pac.getSexo()%></label>
            </div>
            <div class="col-6 col-sm-2 col-md-2">
                <label><strong>Tel�fono: </strong><%=pac.getTelefono1()%></label>
            </div>
        </div>
    </div>
    <hr>           
    <div  id="DtsMed">
        <h6 style="text-align: center"><strong>ACTUALIZAR Medico</strong></h6><br> 
        <div class="form-row col-12">
            <%if (modify) {%>
                <div class="form-row col-12">
                    <div class="col-8 col-sm-5 col-md-4">
                        <label><strong>Nombre: </strong><%=Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno()%></label>
                    </div>            
                    <div class="col-6 col-sm-2 col-md-2">
                        <label><strong>Tel�fono: </strong><%=Orden.getMedico().getTelefono1()%></label>
                    </div>
                </div>
            <br><hr>
            <div id="srchMed" class="col-sm-8 col-md-9 mb-3">
                <div class="alert alert-primary alert-dismissible fade show" role="alert">
                    <center><p>Aqu� puede actualizar el m�dico que tiene asignado a la �rden...</p></center>
                    <center><p><strong>Omitir.</strong> Si no se desea actualizar el m�dico</p></center>
                </div>
            </div>
            <br>
            <div class="col col-sm-4 col-md-3 mb-3">
                <input style="text-align: center" type="text" onkeyup="SrchMed(this, 'est');" class="form-control" name="BMed" id="BMed" placeholder="Medico" required>
                <br><button class="btn btn-danger btn-block" onclick="AddMed('form');" id="sendForm"  name="sendForm"><strong>M�dico No Registrado</strong></button>
            </div>           

            <%} else {%>
            <%if (Orden.getMedico() == null || Orden.getMedico().getNombre() == null || Orden.getMedico().getNombre().trim() == "") {%>
            <div class="col col-sm-4 col-md-3 mb-3">
                <input style="text-align: center" type="text" onkeyup="SrchMed(this, 'est');" class="form-control" name="BMed" id="BMed" placeholder="Medico" required>
                <br><button class="btn btn-danger btn-block" onclick="AddMed('form');" id="sendForm"  name="sendForm"><strong>M�dico No Registrado</strong></button>
            </div><br>                        
            <div id="srchMed" class="col-sm-8 col-md-9 mb-3">
                <div class="alert alert-warning alert-dismissible fade show" role="alert">
                    <center>Debe de asignar un m�dico para esta orden.</center>
                </div>
            </div>
            <%} else {%>
            <div class="col-8 col-sm-5 col-md-4">
                <label><strong>Nombre: </strong><%=Orden.getMedico().getNombre() + " " + Orden.getMedico().getAp_Paterno() + " " + Orden.getMedico().getAp_Materno()%></label>
            </div>            
            <div class="col-6 col-sm-2 col-md-2">
                <label><strong>Tel�fono: </strong><%=Orden.getMedico().getTelefono1()%></label>
            </div>
            <%}
                }%>
        </div>    
    </div>
    <hr>     
    <%if (modify) {%>                
    <div class="form-row">
        <!--Descripcion de cambios para la actualizaci�n de la Orden-->
        <div class="col-12 col-sm-12 col-md-12 mb-3">
            <div class="alert alert-success" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="alert-heading">Est� actualizando la orden con folio '<%=Orden.getFolio_Unidad()%>'</h4>
                <p>Actualizar los estudios de una �rden tiene algunas condiciones:</p>
                <hr>
                <p class="mb-1"><strong>1: Siempre Puede agregar estudios.</strong></p>
                <p class="mb-1"><strong>2: No ser� posible eliminar un estudio.</strong> Si el estudio ya contiene resultados.</p>
                <p class="mb-1"><strong>3: Puede eliminar estudios.</strong> Si la ord�n no tiene ning�n pago y a�n no se carga ningun resultado.</p>                

            </div>
        </div>
    </div>      
    <%}%>
    <h6 style="text-align: center"><strong>A�adir Estudios</strong></h6><br>       
    <div id="FrmSrch">
        <!--A�adir Estudio por codigo-->
        <div class="form-row">
            <div class=" offset-3 col-6 mb-3" id="Gconvenvio">
                <label class="sr-only" >Convenio</label>
                <%if (Orden.getConvenio() == null || Orden.getConvenio().equals("") || Orden.getConvenio().trim().equals("null")) {%>
                <input style="text-align: center" onchange="SaveConv(this.value, 'ord')" type="text" class="form-control" name="Convenio" id="Convenio" placeholder="Convenio" required>                      
                <%} else {%>            
                <input style="text-align: center" type="text"  value="<%=Orden.getConvenio()%>"class="form-control" name="Convenio" id="Convenio" placeholder="Convenio" readonly="">          
                <%}%>            
            </div>
        </div>
        <div class="form-row">
            <div class="offset-md-1 col-6 col-sm-6 col-md-3 mb-3">
                <div class="col-6 col-sm-3 col-md-1 mb-3 custom-control custom-radio custom-control-inline">
                    <input id="prEsN" class="custom-control-input" name="precE" type="radio" required>
                    <label class="custom-control-label mb-3"  for="prEsN">Normal</label>
                </div>
                <div class="offset-md-1 col-6 col-sm-3 col-md-1 mb-3 custom-control custom-radio custom-control-inline">
                    <input id="prEsU" class="custom-control-input" name="precE" type="radio" required>
                    <label class="custom-control-label" for="prEsU">Urgente</label>
                </div>  
            </div>
            <div class="col-6 col-sm-6 col-md-2 mb-3">
                <input style="text-align: center" type="text" class="form-control" onkeypress="return soloNumeros(event)" name="descE" id="descE" placeholder="% Descuento" required>
            </div>
            <div class="col-6 col-sm-6 col-md-2 mb-3">
                <input style="text-align: center" type="text" class="form-control" onkeypress="return soloNumeros(event)" name="scaE" id="scaE" placeholder="% Cargo Adicional" required>
            </div>
            <div class="col-6 col-sm-6 col-md-4 mb-3">
                <input style="text-align: center" type="text" class="form-control" name="codeEst" onchange="AddEst(this, 'code');" id="codeEst" placeholder="Codigo de Estudio" required>
            </div>
        </div>            
        <div class="form-row">
            <!--A�adir Estudio por Busqueda-->
            <div class="col-12 col-sm-12 col-md-5 mb-3">
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
            <div class="col-12 col-sm-12 col-md-7 mb-3">
                <label class="sr-only" >Buscar...</label>
                <input style="text-align: center" type="text" class="form-control" onkeyup="test22(this, 'Orden');" name="clave_mat" id="clave_mat" placeholder="Buscar..." required>
                <div class="invalid-feedback">
                    Ingresa un criterio de busqueda.
                </div>
            </div>
            <!--Cambiar busqueda por promociones-->
            <div class="col-12 col-sm-12 col-md-12 mb-3">
                <button class="btn btn-outline-info btn-sm btn-block" onclick="chOpt('per');" >Buscar Paquetes(perfiles)</button>
            </div>
        </div>    
    </div>
    <div id="EstsAdded">
        <div id="BEst"></div>
        <%if (Orden.getDet_Orden() != null) {%>     
        <%if (!Orden.getDet_Orden().isEmpty()) {%>
        <div style="color: white" class="table-responsive">
            <table style=" text-align: center" class="table table-bordered table-hover table-sm">
                <tbody>
                    <tr class="bg-warning" style="color: black">
                        <th>Nombre de Estudio</th>
                        <th>Entrega</th>
                        <th>Precio</th>
                        <th>Desc.</th>
                        <th>Cargo</th>
                        <th>Espera</th>
                        <th>Quitar</th>
                    </tr>
                    <%
                        Float total = Float.parseFloat("0");
                        for (Det_Orden_DTO dto : Orden.getDet_Orden()) {
                            Float p = Float.parseFloat("0");
                            int e = 0;
                            if (dto.getT_Entrega().equals("Normal")) {
                                p = dto.getEstudio().getPrecio().getPrecio_N();
                                e = dto.getEstudio().getPrecio().getT_Entrega_N();
                            } else if (dto.getT_Entrega().equals("Urgente")) {
                                p = dto.getEstudio().getPrecio().getPrecio_U();
                                e = dto.getEstudio().getPrecio().getT_Entrega_U();
                            }
                            Float pd = ((dto.getDescuento() * p) / 100);
                            Float ps = ((dto.getSobrecargo() * p) / 100);
                    %>
                    <tr>
                        <td><%=dto.getEstudio().getNombre_Estudio()%></td>
                        <td><%=dto.getT_Entrega()%></td>
                        <td><%=p%></td>
                        <td>$<%=pd%></td>
                        <td>$<%=ps%></td>
                        <td><%=e%> d�as</td>
                        <%if (dto.getEstudio().getAddRes()) {%>
                        <td><div id="mat-<%=Orden.getDet_Orden()%>"><button href="#" disabled="" class="btn btn-danger"><span><img src="images/trash.png"></span></button></div></td>
                                        <%} else {%>
                        <td><div id="mat-<%=Orden.getDet_Orden()%>"><button href="#" class="btn btn-danger" onclick="DelEst(<%=Orden.getDet_Orden().indexOf(dto)%>, 'Ord')"><span><img src="images/trash.png"></span></button></div></td>
                                        <%}%>                        
                    </tr>
                    <%
                            total = total + dto.getSubtotal();
                        }
                        Orden.setMontoRestante(total);
                        sesion.setAttribute("Orden", Orden);
                    %>
                </tbody>
            </table>
        </div>
        <p class="offset-8 col-3 col-sm-3 col-md-3"><strong>Pagar <%=Util.redondearDecimales(Orden.getMontoRestante())%> pesos</strong></p>
        <%if (modify) {%>
        <a class='btn btn-info btn-lg btn-block' href='FinalOrd' >Reimprimir �rden</a>
        <%} else {%>
        <button class="btn btn-success btn-lg btn-block" id="ConPay" onclick="contOr('ord');" name="ConPay">Continuar</button>
        <%}%>               
        <%}%>                
        <%}%>
    </div>                
</div>
<hr>
<%} else {
        out.print("<h1 style='color:white'>La sesi�n ha caducado, Actualice el sistema y realice nuevamente el proceso necesario</h1>");
    }%>   