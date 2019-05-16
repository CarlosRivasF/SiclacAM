/*
 * @author Carlos Rivas
 * @phone 55-31-66-37-29
 * @mail CRivasF-9705@outlook.com
 */
function test(e) {
    if (e.value.length > 0) {
        alert(e.value);
        e.value = "";
    }
}

function test22(e, mode) {
    //alert(e.value + " - " + mode);
    var busq = e.value;
    if (document.getElementById("Tipo_Estudio").value === "") {
        var forms = document.getElementsByClassName('needs-validation');
        var validation = Array.prototype.filter.call(forms, function (form) {
            form.classList.add('was-validated');
        });
    } else {
        if (busq.length === 0) {
            Ajax = buscarComentario();
            Ajax.open('POST', "SearchEst", true);
            Ajax.onreadystatechange = function () {
                if (Ajax.readyState === 4) {
                    document.getElementById("BEst").innerHTML = Ajax.responseText;
                }
            };
            Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            Ajax.send(null);
        } else {
            var tipoE = document.getElementById("Tipo_Estudio").value;
            Ajax = buscarComentario();
            Ajax.open('POST', "SearchEst", true);
            Ajax.onreadystatechange = function () {
                if (Ajax.readyState === 4) {
                    document.getElementById("BEst").innerHTML = Ajax.responseText;
                }
            };
            Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            var p = "tipoE=" + tipoE + " &busq=" + busq + " &mode=" + mode;
            //alert(p);
            Ajax.send(p);
        }
    }
}

var xhr;
function buscarComentario() {
    if (window.ActiveXObject) {
        xhr = new AciveXObject("Microsoft.XMLHttp");
        //alert("IE Soporta AJAX");
    } else if ((window.XMLHttpRequest) || (typeof XMLHttpRequest) !== undefined) {
        xhr = new XMLHttpRequest();
        //alert("Soporta AJAX");
    } else {
        alert("No Soporta AJAX");
        return;
    }
    return xhr;
}

function Menu(n) {
//alert(n);
    Ajax = buscarComentario();
    var divRes = document.getElementById("Main");
    Ajax = buscarComentario();
    Ajax.open('POST', "Menu" + "?n=" + n);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send("n=" + n);
}

function fullScreen() {
//alert("entrando a pantalla completa");
    var docElm = document.documentElement;
    if (docElm.requestFullscreen) {
//alert("requestFullscreen");
        docElm.requestFullscreen();
    } else if (docElm.msRequestFullscreen) {
//alert("msRequestFullscreen");
        docElm = document.body; //overwrite the element (for IE)
        docElm.msRequestFullscreen();
    } else if (docElm.mozRequestFullScreen) {
//alert("mozRequestFullScreen");
        docElm.mozRequestFullScreen();
    } else if (docElm.webkitRequestFullScreen) {
//alert("webkitRequestFullScreen");
        docElm.webkitRequestFullScreen();
    }
}

function val(e) {
    var forms = document.getElementsByClassName('needs-validation');
    var validation = Array.prototype.filter.call(forms, function (form) {
        if (form.checkValidity() === false) {
        } else {
            e.disabled = true;
            validarFormulario(e);
        }
        form.classList.add('was-validated');
    });
}

function x() {
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission    
    var validation = Array.prototype.filter.call(forms, function (form) {
        form.classList.add('was-validated');
        validarFormulario();
    });
}

function validarFormulario(e) {
    buscarComentario();
    var todoCorrecto = true;
    var formulario = document.fors;
    for (var i = 0; i < formulario.length; i++) {
        if (formulario[i].type === 'text' || formulario[i].type === 'number') {
            if (/^\s*$%#"!&()='?¿¡*´¨{[]}-_:.;,><@/.test(formulario[i].value)) {
                alert(formulario[i].name + ' Contiene caracteres invalidos');
                todoCorrecto = false;
                formulario[i].focus();
                e.disabled = false;
                break;
            }//
            if (formulario[i].name.substring(0, 7) === "muestra" || formulario[i].name === "muestra" || formulario[i].name.substring(0, 7) === "unidad" || formulario[i].name === "unidad" || formulario[i].name === "desc" || formulario[i].name === "min" || formulario[i].name === "max" || formulario[i].name === "unidades" || formulario[i].name === "telefono" || formulario[i].name === "celular"
                    || formulario[i].name === "c_p" || formulario[i].name === "calle" || formulario[i].name === "no_int" || formulario[i].name === "no_ext") {
            } else if (formulario[i].name.substring(0, 4) === "desc" || formulario[i].name.substring(0, 3) === "min" || formulario[i].name.substring(0, 3) === "max" || formulario[i].name.substring(0, 8) === "unidades") {
            } else if (formulario[i].value.trim() === "") {
                alert('El campo ' + formulario[i].name + ' debe contener un valor completo');
                todoCorrecto = false;
                formulario[i].value = "";
                formulario[i].focus();
                e.disabled = false;
                break;
            }
        } else if (formulario[i].type === 'password') {
            if (formulario[i].value.length === 0) {
                alert('escriba una contraseña');
                todoCorrecto = false;
                formulario[i].focus();
                e.disabled = false;
                break;
            }
        }
    }
    if (todoCorrecto === true) {
//alert("ENVIANDO, fomulario correcto");
        enviar();
    }
}
function enviar() {
    Ajax = buscarComentario();
    action = document.fors.action;
    //alert(action);
    Ajax.open('POST', action, true);
    Ajax.onreadystatechange = procesar;
    Ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var dts = obtener();
    Ajax.send(dts);
}
function obtener() {
    var controles = document.fors.elements;
    var cad = "";
    var p = "";
    for (var i = 0; i < controles.length - 1; i++) {
        cad = encodeURIComponent(controles[i].name) + "=";
        cad += encodeURIComponent(controles[i].value);
        cad = " &" + cad;
        p = p + cad;
    }
    p = p + recorrerPermisos() + recorrerDias() + getSexo() + getSendMail();
    //alert("parametro a  enviar: " + p);
    return p;
}
function procesar() {
//alert("procesando");    
    if (Ajax.readyState === 4) {
        document.fors.reset();
        document.getElementById("Interaccion").innerHTML = Ajax.responseText;
    }
}
function recorrerPermisos() {
    var listaCompras = '';
    $("input[name=permisos]").each(function (index) {
        if ($(this).is(':checked')) {
            listaCompras += ' &permiso' + $(this).val() + '=' + $(this).val() + ' ';
        }
    });
    return listaCompras;
}
function recorrerDias() {
    var listaCompras = '';
    $("input[name=dia]").each(function (index) {
        if ($(this).is(':checked')) {
            listaCompras += ' &dia' + $(this).val() + '=' + $(this).val() + ' ';
        }
    });
    return listaCompras;
}
function getSexo() {
    var sexo = ' &SexoP=';
    if (document.getElementById("mujer") !== null || document.getElementById("hombre") !== null) {
        if (document.getElementById("mujer").checked) {
            var sexo = ' &SexoP=Femenino';
        } else if (document.getElementById("hombre").checked) {
            var sexo = ' &SexoP=Masculino';
        }
        return sexo;
    }
    return sexo;
}
function getSendMail() {
    var sexo = ' &SendMail=';
    if (document.getElementById("Yenv") !== null || document.getElementById("Nenv") !== null) {
        if (document.getElementById("Yenv").checked) {
            var sexo = ' &SendMail=true';
        } else if (document.getElementById("Nenv").checked) {
            var sexo = ' &SendMail=false';
        }
        return sexo;
    }
    return sexo;
}
///////////////////////////////////////////////////////////////////
function ShFilter(url) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('filtros');
    Ajax = buscarComentario();
    Ajax.open('GET', url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send(null);
}
function mostrarOpc(url) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('Main');
    Ajax = buscarComentario();
    Ajax.open('GET', url);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send(null);
}
function mostrarForm(url) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('Interaccion');
    Ajax = buscarComentario();
    Ajax.open('GET', url);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send(null);
}
function MostrarDir(url) {
    var n = document.getElementById("ndir").value;
    // alert(n);
    Ajax = buscarComentario();
    var divRes = document.getElementById("n-dir" + "_" + n);
    Ajax = buscarComentario();
    Ajax.open('GET', url + "?ndir=" + 1 + "&nc=" + n);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send("ndir=" + 1 + "&nc=" + n);
}
function MostrarConf(url) {
    var n = document.getElementById("nconf").value;
    // alert(n);
    Ajax = buscarComentario();
    var divRes = document.getElementById("n-conf" + "_" + n);
    Ajax = buscarComentario();
    Ajax.open('GET', url + "?nconf=" + 1 + "&nc=" + n);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send("nconf=" + 1 + "&nc=" + n);
}
function QuitarConf(url, n) {
//alert(n);
    Ajax = buscarComentario();
    var divRes = document.getElementById("n-conf" + "_" + n);
    Ajax = buscarComentario();
    Ajax.open('GET', url + "?nc=" + n);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send("nc=" + n);
}
function MostrarMat(url) {
    var n = document.getElementById("nmat").value;
    // alert(n);
    Ajax = buscarComentario();
    var divRes = document.getElementById("n-mat" + "_" + n);
    Ajax = buscarComentario();
    Ajax.open('GET', url + "?nmat=" + 1 + "&nm=" + n);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send("nmat=" + 1 + "&nm=" + n);
}
function QuitarMat(url, n) {
//alert(n);
    Ajax = buscarComentario();
    var divRes = document.getElementById("n-mat" + "_" + n);
    Ajax = buscarComentario();
    Ajax.open('GET', url + "?nm=" + n);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send("nm=" + n);
}
function mostrarRes(url) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('Interacion');
    Ajax = buscarComentario();
    Ajax.open('GET', url);
    //alert(url);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
        }
    };
    Ajax.send(null);
}
function llenaColonia() {
    buscarComentario();
    var c_p = document.getElementById('c_p').value;
    if (c_p.length > 3 & c_p.length < 6) {
        xhr.open("POST", "Col", true);
        xhr.onreadystatechange = PrsendCol;
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send("c_p=" + c_p);
        //alert(c_p);
    }
}
function PrsendCol() {
    if (xhr.readyState === 4)
    {
        document.getElementById('dir').innerHTML = xhr.responseText;
    }
}
function llenaColor() {
    buscarComentario();
    var categoria = document.getElementById('categoria').value;
    var talla = document.getElementById('talla').value;
    xhr.open("POST", "sendColor", true);
    xhr.onreadystatechange = PrsendColor;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("categoria=" + categoria + " &talla=" + talla);
    //alert(categoria+"-"+talla);
}
function PrsendColor() {
    if (xhr.readyState === 4)
    {
        document.getElementById('camabioColor').innerHTML = xhr.responseText;
    }
}
function sendQuery() {
    buscarComentario();
    var categoria = document.getElementById('categoria').value;
    var talla = document.getElementById('talla').value;
    var color = document.getElementById('color').value;
    xhr.open("POST", "ConsultarPrenda", true);
    xhr.onreadystatechange = PrsendQyery;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("categoria=" + categoria + " &talla=" + talla + " &color=" + color);
}
function PrsendQyery() {
    if (xhr.readyState === 4)
    {
        document.getElementById('busca').innerHTML = xhr.responseText;
    }
}
function ShDetAp(id_Apart) {
    buscarComentario();
    xhr.open("POST", "ShowDetApart", true);
    xhr.onreadystatechange = PrShDetAp;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("id_Apartado=" + id_Apart);
}
function PrShDetAp() {
    if (xhr.readyState === 4)
    {
        document.getElementById("detailS").innerHTML = xhr.responseText;
    }
}
function ShDetVnt(id_Venta) {
    buscarComentario();
    xhr.open("POST", "../ShowDetVenta", true);
    xhr.onreadystatechange = PrDetVnt;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("id_Venta=" + id_Venta);
}
function PrDetVnt() {
    if (xhr.readyState === 4)
    {
        document.getElementById("detailS").innerHTML = xhr.responseText;
    }
}
function validarFormulario2() {
//alert("validando");
    buscarComentario();
    var todoCorrecto = true;
    var formulario = document.fors;
    for (var i = 0; i < formulario.length; i++) {
        if (formulario[i].name === "cantidad" || formulario[i].name === "txtCantidad") {
            if (formulario[i].value === 0 || formulario[i].value === null) {
                alert('Ingrese una Cantidad mayor a 0');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            }
        } else if (formulario[i].name === "categoria") {
            if (formulario[i].value === 0) {
                alert('Debe elegir una categoria');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            }
        } else if (formulario[i].type === 'text' || formulario[i].type === 'number') {
            if (/^\s*$%#"!&()='?¿¡*´¨{[]}-_:.;,><@/.test(formulario[i].value)) {
                alert(formulario[i].name + ' esta incorrecto');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            } else if (formulario[i].value.length === 0) {
                alert("El campo de " + formulario[i].name + ' esta vacio');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            } else if (formulario[i].value === 0) {
                alert("Ingrese un numero mayor a 0");
                todoCorrecto = false;
                formulario[i].focus();
                break;
            }
        } else if (formulario[i].type === 'date') {
            if (formulario[i].value.length === 0) {
                alert(formulario[i].name + ' esta incorrecto');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            } else if (formulario[i].value.length === 0) {
                alert("El campo de " + formulario[i].name + ' esta vacio');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            } else if (formulario[i].value === 0) {
                alert("Ingrese un numero mayor a 0");
                todoCorrecto = false;
                formulario[i].focus();
                break;
            }
        } else if (formulario[i].name === "TelC") {
            if (formulario[i].value.length !== 10) {
                alert('Su Numero de telefono debe ser de 10 digitos');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            }
        } else if (formulario[i].type === 'email') {
            expr = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
            if (!expr.test(formulario[i].value)) {
                alert('Su correo Electrónico es incorrecto');
                todoCorrecto = false;
                formulario[i].focus();
                break;
            }
        }
    }
    if (todoCorrecto === true) {
//alert("ENVIANDO, fomulario correcto");
        enviar2();
    }
}
function enviar2() {
    var n = document.forms.length;
    var action;
    switch (n) {
        case 1:
            action = document.forms[0].action;
            break;
        case 2:
            action = document.forms[1].action;
            break;
    }
//alert(action);
    Ajax.open('POST', action, true);
    Ajax.onreadystatechange = procesar2;
    Ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    var dts = obtener2();
    Ajax.send(dts);
}
function obtener2() {
    var n = document.forms.length;
    var controles;
    switch (n) {
        case 1:
            var controles = document.forms[0].elements;
            break;
        case 2:
            var controles = document.forms[1].elements;
            break;
    }
    var cad = "";
    var p = "";
    for (var i = 0; i < controles.length - 1; i++) {
        cad = encodeURIComponent(controles[i].name) + "=";
        cad += encodeURIComponent(controles[i].value);
        cad = " &" + cad;
        p = p + cad;
    }
//alert("parametro a  enviar: " + p);
    return p;
}
function procesar2() {
//alert("procesando");    
    if (Ajax.readyState === 4) {
        document.fors.reset();
        document.getElementById("Interaccion").innerHTML = Ajax.responseText;
    }
}
function soloLetras(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
    especiales = [8, 37, 39, 46];
    tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) === -1 && !tecla_especial)
        return false;
}
function soloNumeros(e) {
    key = e.keyCode || e.which;
    tecla = String.fromCharCode(key).toLowerCase();
    letras = "0123456789";
    especiales = [8, 37, 39, 46];
    tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }
    if (letras.indexOf(tecla) === -1 && !tecla_especial)
        return false;
}
//////////////////////////////////////////////////////Eliminar
function FormDelMat(i, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('mat-' + i);
            break;
        case 'NO':
            divRes = document.getElementById('mat-' + i);
            break;
        case 'SI':
            divRes = document.getElementById("Interaccion");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormDelMat", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act;
    Ajax.send(p);
}
function FormDelMed(i, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('medi-' + i);
            break;
        case 'NO':
            divRes = document.getElementById('medi-' + i);
            break;
        case 'SI':
            divRes = document.getElementById("Interaccion");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormDelMed", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act;
    Ajax.send(p);
}
function FormDelEst(i, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('est-' + i);
            break;
        case 'NO':
            divRes = document.getElementById('est-' + i);
            break;
        case 'SI':
            divRes = document.getElementById("Interaccion");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormDelEst", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act;
    Ajax.send(p);
}
function FormDelEst_Conf(i, l, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('estCn-' + l);
            break;
        case 'NO':
            divRes = document.getElementById('estCn-' + l);
            break;
        case 'SI':
            divRes = document.getElementById("configs");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormDelEst_Conf", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act + " &liCo=" + l;
    Ajax.send(p);
}
function FormDelEst_Mat(i, l, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('estMt-' + l);
            break;
        case 'NO':
            divRes = document.getElementById('estMt-' + l);
            break;
        case 'SI':
            divRes = document.getElementById("matis");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormDelEst_Mat", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act + " &liCo=" + l;
    Ajax.send(p);
}
function FormDelEmp(i, act) {
    divRes = document.getElementById('Empl-' + i);
    Ajax = buscarComentario();
    Ajax.open('POST', "FormDelEmp", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act;
    Ajax.send(p);
}

function FormDelPac(i, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('pac-' + i);
            break;
        case 'NO':
            divRes = document.getElementById('pac-' + i);
            break;
        case 'SI':
            divRes = document.getElementById("Interaccion");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "DelPac", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act;
    Ajax.send(p);
}

function AddMat(l, index, list) {
    var cant;
    var prec;
    var p;
    var r = true;
    //alert(l + " " + index + " " + list);
    switch (list) {
        case 'MatsNotUnidad':
            var cant = document.getElementById("cantidad" + l).value;
            if (cant.length === 0) {
                r = false;
            } else {
                var p = "index=" + index + " &list=" + list + " &cantidad=" + cant;
            }
            break;
        case 'MatsNotEmpresa':
            var cant = document.getElementById("cantidad" + l).value;
            var prec = document.getElementById("precio" + l).value;
            if (cant.length === 0 || prec.length === 0) {
                r = false;
            } else {
                var p = "index=" + index + " &list=" + list + " &cantidad=" + cant + " &precio=" + prec;
            }
            break;
    }
    if (r) {
        buscarComentario();
        xhr.open("POST", "AddMatU", true);
        xhr.onreadystatechange = PrAddMat;
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        //alert(p);
        xhr.send(p);
    } else {
        alert("Es necesario llenar todos los campos");
        document.getElementById("cantidad" + l).focus();
    }
}
function PrAddMat() {
    if (xhr.readyState === 4)
    {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
//////////////////////////////////////////////////Actualizar
function FormUpUni(index, part, acc) {
    var dta = " &f=f";
    switch (part) {
        case 'prep':
            divRes = document.getElementById("preparacion");
            if (acc === "upd") {
                var prepar = document.getElementById("prepar").value;
                dta = " &prepar=" + prepar;
            }
            break;
        case 'util':
            divRes = document.getElementById("utilidad");
            if (acc === "upd") {
                var util = document.getElementById("util").value;
                dta = " &util=" + util;
            }
            break;
        case 'prec':
            divRes = document.getElementById("precio");
            if (acc === "upd") {
                var precN = document.getElementById("precN").value;
                var entrN = document.getElementById("entrN").value;
                var precU = document.getElementById("precU").value;
                var entrU = document.getElementById("entrU").value;
                dta = " &precN=" + precN + " &entrN=" + entrN + " &precU=" + precU + " &entrU=" + entrU;
            }
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpEst", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &part=" + part + " &acc=" + acc + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}
function FormUpDesc(index, part, acc) {
    var dta = " &f=f";
    switch (part) {
        case 'met':
            divRes = document.getElementById("metodo");
            if (acc === "upd") {
                var tipoE = document.getElementById("tipoE").value;
                var metodo = document.getElementById("metodoE").value;
                dta = " &tipoE=" + tipoE + " &metodoE=" + metodo;
            }
            break;
        case 'prep':
            divRes = document.getElementById("preparacion");
            if (acc === "upd") {
                var prepar = document.getElementById("prepar").value;
                dta = " &prepar=" + prepar;
            }
            break;
        case 'util':
            divRes = document.getElementById("utilidad");
            if (acc === "upd") {
                var util = document.getElementById("util").value;
                dta = " &util=" + util;
            }
            break;
        case 'prec':
            divRes = document.getElementById("precio");
            if (acc === "upd") {
                var precN = document.getElementById("precN").value;
                var entrN = document.getElementById("entrN").value;
                var precU = document.getElementById("precU").value;
                var entrU = document.getElementById("entrU").value;
                dta = " &precN=" + precN + " &entrN=" + entrN + " &precU=" + precU + " &entrU=" + entrU;
            }
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpEst", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &part=" + part + " &acc=" + acc + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    //alert(p);
    Ajax.send(p);
}
function FormUpCn(index, part, acc) {
    var dta = " &f=f";
    divRes = document.getElementById("configs");
    if (acc === "upd") {
        var descrip = document.getElementById("descrip").value;
        var sexo = document.getElementById("sexo").value;
        var valMin = document.getElementById("valMin").value;
        var valMax = document.getElementById("valMax").value;
        var uns = document.getElementById("uns").value;
        dta = " &descrip=" + descrip + " &sexo=" + sexo + " &valMin=" + valMin + " &valMax=" + valMax + " &uns=" + uns;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUp_MatCnf", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &part=" + part + " &acc=" + acc + dta;
    //alert(p);
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}

function FormUpEstMat(index, part, acc) {
    var dta = " &f=f";
    divRes = document.getElementById("matis");
    if (acc === "upd") {
        var mater = document.getElementById("mater").value;
        var canti = document.getElementById("canti").value;
        var unid = document.getElementById("unid").value;
        var TMstra = document.getElementById("TMstra").value;
        dta = " &mater=" + mater + " &canti=" + canti + " &unid=" + unid + " &TMstra=" + TMstra;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpEstMat", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &part=" + part + " &acc=" + acc + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}
function FormUpDtsP(index, part, acc, lst) {
    var dta = " &f=f";
    var r = true;
    switch (part) {
        case 'name':
            divRes = document.getElementById("nameP");
            if (acc === "upd") {
                var namep = document.getElementById("namep").value;
                var patp = document.getElementById("patp").value;
                var matp = document.getElementById("matp").value;
                dta = " &namep=" + namep + " &patp=" + patp + " &matp=" + matp;
            }
            break;
        case 'contacto':
            divRes = document.getElementById("contactoP");
            if (acc === "upd") {
                var mailP = document.getElementById("mailP").value;
                var tel1P = document.getElementById("tel1P").value;
                if (lst !== "uns") {
                    var tel2P = document.getElementById("tel2P").value;
                    dta = " &mailP=" + mailP + " &tel1P=" + tel1P + " &tel2P=" + tel2P;
                } else {
                    dta = " &mailP=" + mailP + " &tel1P=" + tel1P;
                }
            }
            break;
        case 'fcsx':
            divRes = document.getElementById("fcsx");
            if (acc === "upd") {
                var fNac = document.getElementById("fNac").value;
                var Sexo = document.getElementById("Sexo").value;
                dta = " &fNac=" + fNac + " &Sexo=" + Sexo;
            }
            break;
        case 'direccion':
            divRes = document.getElementById("direccion");
            if (acc === "upd") {
                var c_p = document.getElementById("c_p").value;
                if (c_p.length > 3) {
                    var colonia = document.getElementById("colonia").value;
                    if (colonia.length !== 0) {
                        var calle = document.getElementById("calle").value;
                        var no_int = document.getElementById("no_int").value;
                        var no_ext = document.getElementById("no_ext").value;
                    } else {
                        r = false;
                        alert("Selecciones una colonia");
                    }
                } else {
                    r = false;
                    alert("Ingrese un codigo postal");
                    document.getElementById("c_p").focus();
                }

                dta = " &c_p=" + c_p + " &colonia=" + colonia + " &calle=" + calle + " &no_int=" + no_int + " &no_ext=" + no_ext;
            }
            break;
    }
    if (r) {
        Ajax = buscarComentario();
        Ajax.open('POST', "FormUpDtsP", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                divRes.focus();
                divRes.innerHTML = Ajax.responseText;
                divRes.focus();
            }
        };
        var p = "index=" + index + " &part=" + part + " &acc=" + acc + " &list=" + lst + dta;
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(p);
    }
}
function FormUpCrNsP(index, acc, part) {
    var dta = " &f=f";
    divRes = document.getElementById(part);
    switch (part) {
        case "datosEmp":
            if (acc === "upd") {
                var curpEm = document.getElementById("curpEm").value;
                var NssEm = document.getElementById("NssEm").value;
                dta = " &curpEm=" + curpEm + " &NssEm=" + NssEm;
            }
            break;
        case "datoslab":
            if (acc === "upd") {
                var fechIngr = document.getElementById("fechIngr").value;
                var salBr = document.getElementById("salBr").value;
                dta = " &fechIngr=" + fechIngr + " &salBr=" + salBr;
            }
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpCrNsP", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &acc=" + acc + " &part=" + part + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}
function FormUpDiPrivHrs(index, acc, part) {
    var dta = " &f=f";
    divRes = document.getElementById(part);
    switch (part) {
        case "DiasEmp":
            if (acc === "upd") {
                dta = recorrerDias();
            }
            break;
        case "PrivsEmp":
            if (acc === "upd") {
                dta = recorrerPermisos();
            }
            break;
        case "HorasEmp":
            if (acc === "upd") {
                var horaE = document.getElementById("horaE").value;
                var horaS = document.getElementById("horaS").value;
                var horaC = document.getElementById("horaC").value;
                var horaR = document.getElementById("horaR").value;
                dta = " &horaE=" + horaE + " &horaS=" + horaS + " &horaC=" + horaC + " &horaR=" + horaR;
            }
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpDiPrivHrs", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &acc=" + acc + " &part=" + part + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}
function FormUpUser(index, acc, lst) {
    var dta = " &f=f";
    divRes = document.getElementById("acceso");
    if (acc === "upd") {
        var nameUs = document.getElementById("nameUs").value;
        var passUs = document.getElementById("passUs").value;
        var edoUs = document.getElementById("edoUs").value;
        dta = " &nameUs=" + nameUs + " &passUs=" + passUs + " &edoUs=" + edoUs;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpUser", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &list=" + lst + " &acc=" + acc + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}
function FormUpMat(index) {
    buscarComentario();
    xhr.open("POST", "FormUpdMat", true);
    xhr.onreadystatechange = PrFormUpMat;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrFormUpMat() {
    if (xhr.readyState === 4)
    {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
//////////////////////////////////////////////Detalles
function ShDetUn(index) {
    buscarComentario();
    xhr.open("POST", "ShDetUn", true);
    xhr.onreadystatechange = PrShDetUn;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrShDetUn() {
    if (xhr.readyState === 4)
    {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
function ShDetEst(index) {
    buscarComentario();
    xhr.open("POST", "ShDetEst", true);
    xhr.onreadystatechange = PrShDetEst;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrShDetEst() {
    if (xhr.readyState === 4)
    {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
function ShDetEmpl(index) {
    buscarComentario();
    xhr.open("POST", "ShDetEmpl", true);
    xhr.onreadystatechange = PrShDetEmpl;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrShDetEmpl() {
    if (xhr.readyState === 4) {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
function ShDetMed(index) {
    buscarComentario();
    xhr.open("POST", "ShDetMed", true);
    xhr.onreadystatechange = PrShDetMed;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrShDetMed() {
    if (xhr.readyState === 4) {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
function ShDetPac(index) {
    buscarComentario();
    xhr.open("POST", "ShDetPac", true);
    xhr.onreadystatechange = PrShDetMed;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrShDetPac() {
    if (xhr.readyState === 4) {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}
///////////////////////////////////// BUSQUEDA ///////////////////////////////////////////////////////////////////
//SrchEmpl
function SchMat(e) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SearchMat", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BMat").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(null);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SearchMat", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BMat").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq;
        Ajax.send(p);
    }
}
function SchEst(e) {
    var busq = e.value;
    if (document.getElementById("Tipo_Estudio").value === "") {
        var forms = document.getElementsByClassName('needs-validation');
        var validation = Array.prototype.filter.call(forms, function (form) {
            form.classList.add('was-validated');
        });
    } else {
        if (busq.length === 0) {
            var tipoE = document.getElementById("Tipo_Estudio").value;
            Ajax = buscarComentario();
            Ajax.open('POST', "SrchEstu", true);
            Ajax.onreadystatechange = function () {
                if (Ajax.readyState === 4) {
                    document.getElementById("BEstu").innerHTML = Ajax.responseText;
                }
            };
            Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            var p = "tipoE=" + tipoE;
            Ajax.send(p);
        } else {
            var tipoE = document.getElementById("Tipo_Estudio").value;
            Ajax = buscarComentario();
            Ajax.open('POST', "SrchEstu", true);
            Ajax.onreadystatechange = function () {
                if (Ajax.readyState === 4) {
                    document.getElementById("BEstu").innerHTML = Ajax.responseText;
                }
            };
            Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            var p = "tipoE=" + tipoE + " &busq=" + busq;
            Ajax.send(p);
        }
    }
}
function SchEst1() {
    var busq = document.getElementById("Buestu").value;
    if (document.getElementById("Buestu").value.length !== 0 && document.getElementById("Tipo_Estudio").value !== "") {
        var tipoE = document.getElementById("Tipo_Estudio").value;
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchEstu", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEstu").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "tipoE=" + tipoE + " &busq=" + busq;
        Ajax.send(p);
    } else if (document.getElementById("Tipo_Estudio").value !== "" && document.getElementById("Buestu").value.length === 0) {
        var tipoE = document.getElementById("Tipo_Estudio").value;
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchEstu", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEstu").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "tipoE=" + tipoE;
        Ajax.send(p);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchEstu", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEstu").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(null);
    }
}
function SchEmpl(e) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchEmpl", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEmpl").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(null);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchEmpl", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEmpl").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq;
        Ajax.send(p);
    }
}
function SrchMed(e, mode) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchMed", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("srchMed").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "mode=" + mode;
        Ajax.send(p);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchMed", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("srchMed").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq + " &mode=" + mode;
        Ajax.send(p);
    }
}
function SrchPac(e, mode) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchPac", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("srchPac").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "mode=" + mode;
        Ajax.send(p);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchPac", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("srchPac").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq + " &mode=" + mode;
        Ajax.send(p);
    }
}
function AddByCot(e) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SearchMat", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BMat").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(null);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SearchMat", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BMat").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq;
        Ajax.send(p);
    }
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function AddPac(a) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('Interaccion');
    var p = "mode=x";
    var add = false;
    if (a === "code") {
        var CodePac = document.getElementById("CodePac").value;
        if (CodePac.length === 12) {
            add = true;
            p = "CodePac=" + CodePac + " &mode=" + a;
        } else {
            alert("Escriba un codigo correcto");
            add = false;
        }
    } else {
        p = "mode=" + a;
        add = true;
    }
    if (add) {
        Ajax.open('POST', "AddPac");
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                divRes.innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(p);
    }
}
function test2(e) {
    //alert("test2 - " + e.value);
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SearchEst", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEst").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(null);
    } else {
        var tipoE = document.getElementById("Tipo_Estudio").value;
        Ajax = buscarComentario();
        Ajax.open('POST', "SearchEst", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEst").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "tipoE=" + tipoE + " &busq=" + busq;
        Ajax.send(p);
    }
}
function AddEst(x, mode) {
    if (mode === "code") {
        var s = false;
        var ds = document.getElementById("descE").value;
        var sc = document.getElementById("scaE").value;
        var Sco = " &Sco=" + sc;
        var Desc = " &Desc=" + ds;
        var Tprec;
        if (document.getElementById("prEsN").checked) {
            Tprec = " &Tprec=Normal";
            s = true;
            document.getElementById("prEsN").checked = false;
        } else if (document.getElementById("prEsU").checked) {
            Tprec = " &Tprec=Urgente";
            s = true;
            document.getElementById("prEsU").checked = false;
        } else {
            alert("Seleccione un tipo de precio");
            s = false;
        }
        var p = Desc + Tprec + Sco;
        document.getElementById("descE").value = "";
        document.getElementById("scaE").value = "";

        if (x.value !== "" && s) {
            buscarComentario();
            xhr.open("POST", "AddEst", true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    document.getElementById("EstsAdded").innerHTML = xhr.responseText;

                }
            };
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.send("estudio=" + x.value + " &mode=" + mode + p);
            x.value = "";
        }
    } else {
        var s = false;
        var ds = document.getElementById("desc" + x).value;
        var sc = document.getElementById("sca" + x).value;
        var Sco = " &Sco=" + sc;
        var Desc = " &Desc=" + ds;
        var Tprec;
        if (document.getElementById("PreN" + x).checked) {
            Tprec = " &Tprec=Normal";
            s = true;
        } else if (document.getElementById("PreU" + x).checked) {
            Tprec = " &Tprec=Urgente";
            s = true;
        } else {
            alert("Seleccione un tipo de precio");
            s = false;
        }
        var p = Desc + Tprec + Sco;
        if (s) {
            buscarComentario();
            xhr.open("POST", "AddEst", true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    document.getElementById("EstsAdded").innerHTML = xhr.responseText;
                }
            };
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.send("estudio=" + x + " &mode=" + mode + p);
        }
    }
}
function AddEstCot(x, mode) {
    if (mode === "code") {
        var s = false;
        var ds = document.getElementById("descE").value;
        var sc = document.getElementById("scaE").value;
        var Sco = " &Sco=" + sc;
        var Desc = " &Desc=" + ds;
        var Tprec;
        if (document.getElementById("prEsN").checked) {
            Tprec = " &Tprec=Normal";
            s = true;
            document.getElementById("prEsN").checked = false;
        } else if (document.getElementById("prEsU").checked) {
            Tprec = " &Tprec=Urgente";
            s = true;
            document.getElementById("prEsU").checked = false;
        } else {
            alert("Seleccione un tipo de precio");
            s = false;
        }
        var p = Desc + Tprec + Sco;
        document.getElementById("descE").value = "";

        if (x.value !== "" && s) {
            buscarComentario();
            xhr.open("POST", "AddEstCot", true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    document.getElementById("EstsAdded").innerHTML = xhr.responseText;

                }
            };
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            //alert("estudio=" + x.value + " &mode=" + mode + p);
            xhr.send("estudio=" + x.value + " &mode=" + mode + p);
            x.value = "";
        }
    } else {
        var s = false;
        var ds = document.getElementById("desc" + x).value;
        var sc = document.getElementById("sca" + x).value;
        var Sco = " &Sco=" + sc;
        var Desc = " &Desc=" + ds;
        var Tprec;
        if (document.getElementById("PreN" + x).checked) {
            Tprec = " &Tprec=Normal";
            s = true;
        } else if (document.getElementById("PreU" + x).checked) {
            Tprec = " &Tprec=Urgente";
            s = true;
        } else {
            alert("Seleccione un tipo de precio");
            s = false;
        }
        var p = Desc + Tprec + Sco;
        if (s) {
            buscarComentario();
            xhr.open("POST", "AddEstCot", true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    document.getElementById("EstsAdded").innerHTML = xhr.responseText;
                }
            };
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.send("estudio=" + x + " &mode=" + mode + p);
        }
    }
}
function AddMed(x) {
    var p;
    var s = false;
    switch (x) {
        case 'form':
            s = true;
            p = "mode=" + x;
            break;
        case 'ins':
            s = true;
            var nombre_persona = document.getElementById("nombre_persona").value;
            var a_paterno = document.getElementById("a_paterno").value;
            var a_materno = document.getElementById("a_materno").value;
            if (nombre_persona.length > 3 && a_paterno.length > 3 && a_materno.length > 3) {
                p = "nombre_persona=" + nombre_persona + " &a_paterno=" + a_paterno + " &a_materno=" + a_materno + " &mode=" + x;
            } else {
                s = false;
                alert("Esccriba el Nombre completo del Médico");
            }
            break;
        default:
            s = true;
            p = "mode=" + x;
            break;
    }
    if (s) {
        buscarComentario();
        xhr.open("POST", "AddMed", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                document.getElementById("DtsMed").innerHTML = xhr.responseText;
            }
        };
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send(p);
    }
}
function NewMedord(a) {
    var p;
    var s = false;
    switch (a) {
        case 'form':
            s = true;
            p = "mode=" + a;
            break;
        case 'ins':
            s = true;
            var nombre_persona = document.getElementById("nombre_persona").value;
            var a_paterno = document.getElementById("a_paterno").value;
            var a_materno = document.getElementById("a_materno").value;
            if (nombre_persona.length > 3 && a_paterno.length > 3 && a_materno.length > 3) {
                p = "nombre_persona=" + nombre_persona + " &a_paterno=" + a_paterno + " &a_materno=" + a_materno + " &mode=" + a;
            } else {
                s = false;
                alert("Esccriba el Nombre completo del Médico");
            }
            break;
        default:
            s = true;
            p = "mode=" + a;
            break;
    }
    if (s) {
        Ajax = buscarComentario();
        Ajax.open('POST', "NewMedord", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("DtsMed").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(p);
    }
}
function SaveConv(x, mode) {
    buscarComentario();
    xhr.open("POST", "SveConv", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Gconvenvio").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("conv=" + x + "&mode=" + mode);
}
function DelEst(x, modulo) {
    buscarComentario();
    xhr.open("POST", "DelEst", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("EstsAdded").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + x + " &modulo=" + modulo);
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function AddPacCot(a) {
    Ajax = buscarComentario();
    var divRes = document.getElementById('Interaccion');
    var p = "mode=x";
    var add = false;
    if (a === "dts") {
        var nombre_persona = document.getElementById("nombre_persona").value;
        var a_paterno = document.getElementById("a_paterno").value;
        var a_materno = document.getElementById("a_materno").value;
        if (nombre_persona.length > 3 || a_paterno.length > 3 || a_materno.length > 3) {
            add = true;
            p = "nombre_persona=" + nombre_persona + " &a_paterno=" + a_paterno + " &a_materno=" + a_materno + " &mode=" + a;
        } else {
            var forms = document.getElementsByClassName('needs-validation');
            Array.prototype.filter.call(forms, function (form) {
                form.classList.add('was-validated');
            });
            add = false;
        }
    } else {
        p = "mode=" + a;
        add = true;
    }
    if (add) {
        Ajax.open('POST', "AddPacCot");
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                divRes.innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        Ajax.send(p);
    }
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
function Rentr() {
    buscarComentario();
    xhr.open("POST", "Rentrada", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            location.reload();
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(null);
}
function Rcomida() {
    buscarComentario();
    xhr.open("POST", "Rcomida", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            location.reload();
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(null);
}
function Rregreso() {
    buscarComentario();
    xhr.open("POST", "Rregreso", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            location.reload();
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(null);
}
function RSalida() {
    buscarComentario();
    xhr.open("POST", "Rsalida", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            location.reload();
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(null);
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function contOr(m) {
    buscarComentario();
    xhr.open("POST", "Contin", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "mode=" + m;
    xhr.send(p);
}
function FormPago(mode) {
    buscarComentario();
    xhr.open("POST", "FormPago", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("pago").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("mode=" + mode);
}

function Pagar(mode) {
    var Tp = document.getElementById("Tipo_Pago").value;
    var monto = document.getElementById("monto").value;
    if (document.getElementById("Tipo_Pago").value === "") {
        alert("Seleccione Forma de pago");
    } else {
        if (monto.length === 0) {
            alert("Escriba un monto");
        } else {
            buscarComentario();
            switch (mode) {
                case 'ord':
                    xhr.open("POST", "InsPago", true);
                    break;
                case 'list':
                    xhr.open("POST", "AddPay", true);
                    break;
            }
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    switch (mode) {
                        case 'ord':
                            document.getElementById("estsOrd").innerHTML = xhr.responseText;
                            break;
                        case 'list':
                            document.getElementById("Interaccion").innerHTML = xhr.responseText;
                            break;
                    }
                }
            };
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            var p = "Tipo_Pago=" + Tp + " &monto=" + monto;
            xhr.send(p);
        }
    }
}

function FinalOrd() {
    buscarComentario();
    xhr.open("POST", "FinalOrd", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(null);
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function step1() {
    document.getElementById("step1").style.display = 'none';
    document.getElementById("step12").style.display = 'block';
}
function step12() {
    document.getElementById("step1").style.display = 'none';
    document.getElementById("step13").style.display = 'block';
}

function buscaSrv() {
    var p = document.getElementById("busq").value;
    buscarComentario();
    xhr.open("POST", "BusqServ", true);
    xhr.onreadystatechange = buscaServ;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("busq=" + p);
}
function buscaServ() {
    if (xhr.readyState === 4)
    {
        document.getElementById("busca").innerHTML = xhr.responseText;
    }
}
function GenPdf() {
    buscarComentario();
    xhr.open("POST", "PDF", true);
    xhr.onreadystatechange = GenrPdf;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
}
function GenrPdf() {
    if (xhr.readyState === 4)
    {
        document.getElementById("reporte").innerHTML = xhr.responseText;
    }
}

function pagina(lab, serv, pg) {
    //alert("Paginará "+lab);
    var n = pg;
    divRes = document.getElementById('resultado');
    Ajax = buscarComentario();
    Ajax.open('GET', "" + serv + "?pg=" + n * 10 + "&Lab=" + lab);
    //alert("envio los parametros al sevlet:"+lab+" "+serv+" "+pg);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            //alert("obtuvo respuesta");
        }
    };
    Ajax.send(null);
}

function FormInsSanc(x) {
    buscarComentario();
    xhr.open("POST", "InSanc", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("idEmpl=" + x);
}

function InsSanc(x) {
    buscarComentario();
    var motivoS = document.getElementById("motivoS-" + x).value;
    var montoS = document.getElementById("montoS-" + x).value;
    var fechaS = document.getElementById("fechaS-" + x).value;

    xhr.open("POST", "InSanc", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + x + " &motivoS=" + motivoS + " &montoS=" + montoS + " &fechaS=" + fechaS;
    //alert(p);
    xhr.send(p);
}

function FormInsNom(x) {
    buscarComentario();
    xhr.open("POST", "InsNomina", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("idEmpl=" + x);
}

function InsNom(x) {
    buscarComentario();
    var montoN = document.getElementById("montoN-" + x).value;
    var fechaN = document.getElementById("fechaN-" + x).value;
    xhr.open("POST", "InsNomina", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + x + " &montoN=" + montoN + " &fechaN=" + fechaN;
    xhr.send(p);
}

function ShareEst(x,btn) {
    btn.disabled = true;
    buscarComentario();
    xhr.open("POST", "AddShareEst", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + x);
}

function PrintCoti() {
    window.open('PrintCont');
    window.location.href = 'http://ejemplo.com';
}

function AddEstProm(x, mode) {
    if (mode === "code") {
        document.getElementById("codeEst").value = "";
        document.getElementById("codeEst").focus();
    } else {
        var s = false;
        var ds = document.getElementById("desc" + x).value;
        var sc = document.getElementById("scaE").value;
        var Sco = " &Sco=" + sc;
        var Desc = " &Desc=" + ds;
        var Tprec;
        if (document.getElementById("PreN" + x).checked) {
            Tprec = " &Tprec=Normal";
            s = true;
        } else if (document.getElementById("PreU" + x).checked) {
            Tprec = " &Tprec=Urgente";
            s = true;
        } else {
            alert("Seleccione un tipo de precio");
            s = false;
        }
        var p = Desc + Tprec + Sco;
        if (document.getElementById("shdet") !== null) {
            var shd = " &shdet=xxxx";
            p = p + shd;
        }
        if (s) {
            buscarComentario();
            xhr.open("POST", "AddEstProm", true);
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    document.getElementById("EstsAdded").innerHTML = xhr.responseText;
                }
            };
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.send("estudio=" + x + " &mode=" + mode + p);
        }
    }
}

function saveProm(e) {
    e.disabled = true;
    var titulo = document.getElementById("nameProm").value;
    var Desc = document.getElementById("descProm").value;
    var FecI = document.getElementById("fechaI").value;
    var FecF = document.getElementById("fechaF").value;
    if (titulo.trim() === "" || Desc.trim() === "" || FecI.trim() === "" || FecF.trim() === "") {
        alert("Todos los campos deben de estar llenos..");
        e.disabled = false;
    }
    buscarComentario();
    xhr.open("POST", "ProcesaProm", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("titulo=" + titulo + " &Desc=" + Desc + " &FecI=" + FecI + " &FecF=" + FecF);
}

function verifyEst() {
    var te = document.getElementById("ctrl_est").value;
    if (te === "Referenciado") {
        document.getElementById("porcEst").innerHTML = "<br><label class='sr-only' >Porcentaje</label>" +
                "<input style='text-align: center' type='number' class='form-control form-control-sm' name='porc' id='porc' placeholder=' % ' required >" +
                "<div class='invalid-feedback'>" +
                "Porcentaje obligatorio." +
                "</div>";
    } else {
        document.getElementById("porcEst").innerHTML = "";
    }
}

function FormAddNWConf(x) {
    buscarComentario();
    xhr.open("POST", "FormAddNWConf", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("addcnf").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + x);
}

function AddNWConf(x) {
    var desc, sexo, v1, v2, us;
    desc = document.getElementById("desc").value;
    sexo = document.getElementById("sexo").value;
    v1 = document.getElementById("min").value;
    v2 = document.getElementById("max").value;
    us = document.getElementById("unidades").value;
    if (desc.trim() === "" || sexo.trim() === "") {
        alert("Ingrese descripción y sexo para una nueva configuración");
    } else {
        buscarComentario();
        xhr.open("POST", "AddNWConf", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                document.getElementById("configs").innerHTML = xhr.responseText;
            }
        };
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send("desc=" + desc + " &sexo=" + sexo + " &min=" + v1 + " &max=" + v2 + " &unidades=" + us + " &index=" + x);
    }
}

function SrchOrd(e, mode, part) {
    var busq = e.value;
    var Ajax;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchOrd", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("SerchOrd").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "mode=" + mode + " &part=" + part;
        Ajax.send(p);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchOrd", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("SerchOrd").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq + " &mode=" + mode + " &part=" + part;
        Ajax.send(p);
    }
}

function ShDetOrden(index, part) {
    buscarComentario();
    xhr.open("POST", "ShDetOrd", true);
    xhr.onreadystatechange = PrShDetOrd;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    if (part === 'folio') {
        xhr.send("id_Orden=" + index);
    } else {
        xhr.send("index=" + index + " &part=" + part);
    }
}//FormResDet

function ShDetOrdenRS(index, part) {
    buscarComentario();
    xhr.open("POST", "ShowDetOrdRs", true);
    xhr.onreadystatechange = PrShDetOrd;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    if (part === 'folio') {
        xhr.send("id_Orden=" + index);
    } else {
        xhr.send("index=" + index);
    }
}
function PrShDetOrd() {
    if (xhr.readyState === 4) {
        document.getElementById("Interaccion").innerHTML = xhr.responseText;
    }
}

function FormResDet(index) {
    buscarComentario();
    xhr.open("POST", "FormResDet", true);
    xhr.onreadystatechange = PrResDet;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + index);
}
function PrResDet() {
    if (xhr.readyState === 4) {
        document.getElementById("fill_detor").innerHTML = xhr.responseText;
    }
}

function SaveResDet(index, size) {
    var Res = "";
    for (var i = 0; i < size; i++) {
        if (document.getElementById("valRes-" + i) === null) {
            continue;
        } else {
            Res = Res + "valRes-" + i + "=" + document.getElementById("valRes-" + i).value + " &";
        }
    }
    var Observ = document.getElementById("Observ-" + index).value;
    document.getElementById("fill_detor").innerHTML = "";
    buscarComentario();
    xhr.open("POST", "UplResults", true);
    xhr.onreadystatechange = PrSavResDet;
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send(Res + "index=" + index + " &Observ=" + Observ);
}
function PrSavResDet() {
    if (xhr.readyState === 4) {
        document.getElementById("detors").innerHTML = xhr.responseText;
    }
}

function FormUpRes(index, ixconf, acc, Id_Tpo_Est) {
    var dta = " &f=f";//BTdiValRes
    var divRes = document.getElementById("diValRes-" + ixconf);
    var BTdiV = document.getElementById("BTdiValRes-" + ixconf);
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpRes", true);
    if (acc === "upd") {
        var Resultado = document.getElementById("valRes-" + ixconf).value;
        dta = " &Resultado=" + Resultado;
        if (Id_Tpo_Est !== null) {
            if (Id_Tpo_Est === 2 || Id_Tpo_Est === 4 || Id_Tpo_Est === 5 || Id_Tpo_Est === 6) {
                BTdiV.innerHTML = "<button href='#' class='btn btn-warning btn-sm btn-block' onclick=FormUpRes(" + index + "," + ixconf + ",'form'," + Id_Tpo_Est + ")>Modificar valoración de médico</button>";
            }
        } else {
            BTdiV.innerHTML = "<button href='#' class='btn btn-warning btn-sm' onclick=FormUpRes(" + index + "," + ixconf + ",'form')><span><img src=images/pencil.png></span></button>";
        }
    } else {
        if (Id_Tpo_Est !== null) {
            if (Id_Tpo_Est === 2 || Id_Tpo_Est === 4 || Id_Tpo_Est === 5 || Id_Tpo_Est === 6) {
                BTdiV.innerHTML = "<button href='#' class='btn btn-success btn-sm btn-block' onclick=FormUpRes(" + index + "," + ixconf + ",'upd'," + Id_Tpo_Est + ")>Guardar valoración de médico</button>";
            }
        } else {
            BTdiV.innerHTML = "<button href='#' class='btn btn-success btn-sm' onclick=FormUpRes(" + index + "," + ixconf + ",'upd')><span><img src=images/save.png></span></button>";
        }
    }
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &acc=" + acc + " &ixconf=" + ixconf + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}

function SrchProm(e, mode) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchProm", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("srchProm").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "mode=" + mode;
        Ajax.send(p);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchProm", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("srchProm").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq + " &mode=" + mode;
        Ajax.send(p);
    }
}

function chOpt(mode) {//Añadir validacion para diferir Orden y Cotización
    switch (mode) {
        case 'per':
            document.getElementById("FrmSrch").innerHTML =
                    "<div class='form-row'>" +
                    "<div class='col-12 col-sm-12 col-md-12 mb-3'>" +
                    "<label class='sr-only'>Buscar...</label>" +
                    "<input style='text-align: center' type='text' class='form-control' onkeyup=SrchPromAt(this,'Orden'); name='clave_mat' id='clave_mat' placeholder='Buscar Paquetes(perfiles)...' required=''>" +
                    "<div class='invalid-feedback'>" +
                    "Ingresa un criterio de busqueda." +
                    "</div>" +
                    "</div><div class='col-12 col-sm-12 col-md-12 mb-3'>" +
                    "<button class='btn btn-outline-info btn-sm btn-block' onclick=chOpt('est'); >Buscar Estudios</button>" +
                    "</div>";
            break;
        case 'est':
            document.getElementById("FrmSrch").innerHTML = "<div class='form-row'>" +
                    "<div class='offset-3 col-6 mb-3' id='Gconvenvio'>" +
                    "<label class='sr-only' >Convenio</label>" +
                    "<input style='text-align: center' onchange=SaveConv(this.value,'ord') type='text' class='form-control' name='Convenio' id='Convenio' placeholder='Convenio' required>" +
                    "</div>" +
                    "<div class='offset-1 col-7 col-sm-6 col-md-3 mb-3'>" +
                    "<div class='col-2 col-sm-2 col-md-2 mb-3 custom-control custom-radio custom-control-inline'>" +
                    "<input id='prEsN' class='custom-control-input' name='precE' type='radio' required>" +
                    "<label class='custom-control-label mb-3'  for='prEsN'>Normal</label>&nbsp;" +
                    "</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<div class='col-2 col-sm-2 col-md-2 mb-3 custom-control custom-radio custom-control-inline'>" +
                    "<input id='prEsU' class='custom-control-input' name='precE' type='radio' required>" +
                    "<label class='custom-control-label' for='prEsU'>Urgente</label>&nbsp;" +
                    "</div>" +
                    "</div>&nbsp;&nbsp;&nbsp;" +
                    "<div class='col-5 col-sm-4 col-md-2 mb-3'>" +
                    "<input style='text-align: center' type='text' class='form-control' onkeypress='return soloNumeros(event)' name='descE' id='descE' placeholder='%' required>" +
                    "</div>" +
                    "<div class='col-7 col-sm-12 col-md-5 mb-3'>" +
                    "<input style='text-align: center' type='text' class='form-control' name='codeEst' onchange=AddEst(this,'code'); id='codeEst' placeholder='Codigo de Estudio' required>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-row'>" +
                    "<div class='col-5 col-sm-5 col-md-5 mb-3'>" +
                    "    <label for='Tipo_Estudio' class='sr-only'>Tipo de Estudio</label>" +
                    "    <select class='custom-select d-block w-100 form-control' id='Tipo_Estudio' name='Tipo_Estudio' required=''>" +
                    "        <option value='1'>RUTINARIO</option> " +
                    "        <option value='2'>DE IMAGEN</option> " +
                    "        <option value='3'>ESPECIALES</option> " +
                    "        <option value='4'>RAYOS X</option> " +
                    "        <option value='5'>ESTUDIOS ESPECIALES DE RAYOS X</option> " +
                    "        <option value='6'>ULTRASONIDOS</option> " +
                    "        <option value='7'>PERFILES</option> " +
                    "        <option value='8'>CHECK UP</option> " +
                    "    </select>" +
                    "    <div class='invalid-feedback' style='width: 100%;'>" +
                    "        Por favor seleccione un Tipo de Estudio." +
                    "    </div>" +
                    "</div>" +
                    "<div class='col-7 col-sm-7 col-md-7 mb-3'>" +
                    "    <label class='sr-only'>Buscar...</label>" +
                    "    <input style='text-align: center' type='text' class='form-control' onkeyup=test22(this,'Orden'); name='clave_mat' id='clave_mat' placeholder='Buscar...' required=''>" +
                    "    <div class='invalid-feedback'>" +
                    "        Ingresa un criterio de busqueda." +
                    "    </div>" +
                    "</div><div class='col-12 col-sm-12 col-md-12 mb-3'>" +
                    "<button class='btn btn-outline-info btn-sm btn-block' onclick=chOpt('per');>Buscar Paquetes(perfiles)</button>" +
                    "</div>" +
                    "</div>";
            break;
    }
}

function chOptCot(mode) {//Añadir validacion para diferir Orden y Cotización
    switch (mode) {
        case 'per':
            document.getElementById("FrmSrch").innerHTML =
                    "<div class='form-row'>" +
                    "<div class='col-12 col-sm-12 col-md-12 mb-3'>" +
                    "<label class='sr-only'>Buscar...</label>" +
                    "<input style='text-align: center' type='text' class='form-control' onkeyup=SrchPromAt(this,'Cotizacion'); name='clave_mat' id='clave_mat' placeholder='Buscar Paquetes(perfiles)...' required=''>" +
                    "<div class='invalid-feedback'>" +
                    "Ingresa un criterio de busqueda." +
                    "</div>" +
                    "</div><div class='col-12 col-sm-12 col-md-12 mb-3'>" +
                    "<button class='btn btn-outline-info btn-sm btn-block' onclick=chOptCot('est'); >Buscar Estudios</button>" +
                    "</div>";
            break;
        case 'est':
            document.getElementById("FrmSrch").innerHTML = "<div class='form-row'>" +
                    "<div class='offset-3 col-6 mb-3' id='Gconvenvio'>" +
                    "<label class='sr-only' >Convenio</label>" +
                    "<input style='text-align: center' onchange=SaveConv(this.value,'ord') type='text' class='form-control' name='Convenio' id='Convenio' placeholder='Convenio' required>" +
                    "</div>" +
                    "<div class='offset-1 col-7 col-sm-6 col-md-3 mb-3'>" +
                    "<div class='col-2 col-sm-2 col-md-2 mb-3 custom-control custom-radio custom-control-inline'>" +
                    "<input id='prEsN' class='custom-control-input' name='precE' type='radio' required>" +
                    "<label class='custom-control-label mb-3'  for='prEsN'>Normal</label>&nbsp;" +
                    "</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<div class='col-2 col-sm-2 col-md-2 mb-3 custom-control custom-radio custom-control-inline'>" +
                    "<input id='prEsU' class='custom-control-input' name='precE' type='radio' required>" +
                    "<label class='custom-control-label' for='prEsU'>Urgente</label>&nbsp;" +
                    "</div>" +
                    "</div>&nbsp;&nbsp;&nbsp;" +
                    "<div class='col-5 col-sm-4 col-md-2 mb-3'>" +
                    "<input style='text-align: center' type='text' class='form-control' onkeypress='return soloNumeros(event)' name='descE' id='descE' placeholder='%' required>" +
                    "</div>" +
                    "<div class='col-7 col-sm-12 col-md-5 mb-3'>" +
                    "<input style='text-align: center' type='text' class='form-control' name='codeEst' onchange=AddEstCot(this,'code'); id='codeEst' placeholder='Codigo de Estudio' required>" +
                    "</div>" +
                    "</div>" +
                    "<div class='form-row'>" +
                    "<div class='col-5 col-sm-5 col-md-5 mb-3'>" +
                    "    <label for='Tipo_Estudio' class='sr-only'>Tipo de Estudio</label>" +
                    "    <select class='custom-select d-block w-100 form-control' id='Tipo_Estudio' name='Tipo_Estudio' required=''>" +
                    "        <option value='1'>RUTINARIO</option> " +
                    "        <option value='2'>DE IMAGEN</option> " +
                    "        <option value='3'>ESPECIALES</option> " +
                    "        <option value='4'>RAYOS X</option> " +
                    "        <option value='5'>ESTUDIOS ESPECIALES DE RAYOS X</option> " +
                    "        <option value='6'>ULTRASONIDOS</option> " +
                    "        <option value='7'>PERFILES</option> " +
                    "        <option value='8'>CHECK UP</option> " +
                    "    </select>" +
                    "    <div class='invalid-feedback' style='width: 100%;'>" +
                    "        Por favor seleccione un Tipo de Estudio." +
                    "    </div>" +
                    "</div>" +
                    "<div class='col-7 col-sm-7 col-md-7 mb-3'>" +
                    "    <label class='sr-only'>Buscar...</label>" +
                    "    <input style='text-align: center' type='text' class='form-control' onkeyup=test22(this,'Cotizacion'); name='clave_mat' id='clave_mat' placeholder='Buscar...' required=''>" +
                    "    <div class='invalid-feedback'>" +
                    "        Ingresa un criterio de busqueda." +
                    "    </div>" +
                    "</div><div class='col-12 col-sm-12 col-md-12 mb-3'>" +
                    "<button class='btn btn-outline-info btn-sm btn-block' onclick=chOptCot('per');>Buscar Paquetes(perfiles)</button>" +
                    "</div>" +
                    "</div>";
            break;
    }
}

function ShDetProm(index) {
    Ajax = buscarComentario();
    Ajax.open('POST', "ShDetProm", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = Ajax.responseText;
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send("index=" + index);
}

function addEstMode(mode) {
    document.getElementById("EstsAdded").innerHTML = "<div class='form-row'><div class='col-5 col-sm-5 col-md-5 mb-3'>" +
            "    <label for='Tipo_Estudio' class='sr-only'>Tipo de Estudio</label><input type='hidden' name='shdet' id='shdet'>" +
            "    <select class='custom-select d-block w-100 form-control' id='Tipo_Estudio' name='Tipo_Estudio' required=''>" +
            "        <option value=''>Tipo de Estudio</option>   " +
            "        <option value='1'>RUTINARIO</option> " +
            "        <option value='2'>DE IMAGEN</option> " +
            "        <option value='3'>ESPECIALES</option> " +
            "        <option value='4'>RAYOS X</option> " +
            "        <option value='5'>ESTUDIOS ESPECIALES DE RAYOS X</option> " +
            "        <option value='6'>ULTRASONIDOS</option> " +
            "        <option value='7'>PERFILES</option> " +
            "        <option value='8'>CHECK UP</option> " +
            "    </select>" +
            "    <div class='invalid-feedback' style='width: 100%;'>" +
            "        Por favor seleccione un Tipo de Estudio." +
            "    </div>" +
            "</div>" +
            "<div class='col-7 col-sm-7 col-md-7 mb-3'>" +
            "    <label class='sr-only'>Buscar...</label>" +
            "    <input style='text-align: center' type='text' class='form-control' onkeyup=test22(this,'" + mode + "');  name='clave_mat' id='clave_mat' placeholder='Buscar...' required=''>" +
            "    <div class='invalid-feedback'>" +
            "        Ingresa un criterio de busqueda." +
            "    </div>" +
            "</div></div>" +
            "<div id='BEst'></div>" +
            "<br>";
}

function DelEstSecc(x, modulo) {
    buscarComentario();
    xhr.open("POST", "DelEst", true);
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            document.getElementById("EstsAdded").innerHTML = xhr.responseText;
        }
    };
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.send("index=" + x + " &modulo=" + modulo + " &shdet=xxx");
}

function FormDelProm(i, act) {
    switch (act) {
        case 'show':
            divRes = document.getElementById('pac-' + i);
            break;
        case 'NO':
            divRes = document.getElementById('pac-' + i);
            break;
        case 'SI':
            divRes = document.getElementById("Interaccion");
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "DelProm", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + i + " &ADelm=" + act;
    Ajax.send(p);
}

function FormUpProm(index, part, acc) {
    var dta = " &f=f";
    var divRes;
    switch (part) {
        case 'name':
            divRes = document.getElementById("DvnameProm");
            if (acc === "upd") {
                var nameProm = document.getElementById("nameProm").value;
                dta = " &nameProm=" + nameProm;
            }
            break;
        case 'desc':
            divRes = document.getElementById("DvdescProm");
            if (acc === "upd") {
                var descProm = document.getElementById("descProm").value;
                dta = " &descProm=" + descProm;
            }
            break;
        case 'fchs':
            divRes = document.getElementById("DvfcsProm");
            if (acc === "upd") {
                var fechaI = document.getElementById("fechaI").value;
                var fechaF = document.getElementById("fechaF").value;
                dta = " &fechaI=" + fechaI + " &fechaF=" + fechaF;
            }
            break;
    }
    Ajax = buscarComentario();
    Ajax.open('POST', "UpdProm", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &part=" + part + " &acc=" + acc + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}

function SrchPromAt(e, mode) {
    var busq = e.value;
    if (busq.length === 0) {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchPromAt", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEst").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "mode=" + mode;
        Ajax.send(p);
    } else {
        Ajax = buscarComentario();
        Ajax.open('POST', "SrchPromAt", true);
        Ajax.onreadystatechange = function () {
            if (Ajax.readyState === 4) {
                document.getElementById("BEst").innerHTML = Ajax.responseText;
            }
        };
        Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        var p = "busq=" + busq + " &mode=" + mode;
        Ajax.send(p);
    }
}

function AddProm(x, mode) {
    Ajax = buscarComentario();
    Ajax.open('POST', "CaptureProm", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            document.getElementById("EstsAdded").innerHTML = Ajax.responseText;
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "index=" + x + " &mode=" + mode;
    Ajax.send(p);
}

function CastCot(e) {
    var Id_Cot = e.value;
    Ajax = buscarComentario();
    Ajax.open('POST', "CastToOrden", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = Ajax.responseText;
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "Id_Cot=" + Id_Cot;
    Ajax.send(p);
}

function FormUpResObs(index, acc) {
    var dta = " &f=f";//BTdiValRes
    var divRes = document.getElementById("diValObs-" + index);
    var BTdiV = document.getElementById("BTdiValObs-" + index);
    Ajax = buscarComentario();
    Ajax.open('POST', "FormUpResObs", true);
    if (acc === "upd") {
        var Observ = document.getElementById("Observ-" + index).value;
        dta = " &Observ=" + Observ;
        BTdiV.innerHTML = "<button href='#' class='btn btn-warning btn-sm' onclick=FormUpResObs(" + index + ",'form')><span><img src=images/pencil.png></span></button>";
    } else {
        BTdiV.innerHTML = "<button href='#' class='btn btn-success btn-sm' onclick=FormUpResObs(" + index + ",'upd')><span><img src=images/save.png></span></button>";
    }
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            divRes.focus();
            divRes.innerHTML = Ajax.responseText;
            divRes.focus();
        }
    };
    var p = "index=" + index + " &acc=" + acc + dta;
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    Ajax.send(p);
}

function SrchOrdFolio(e, mode) {
    var Folio = e.value;
    e.value = "";
    Ajax = buscarComentario();
    Ajax.open('POST', "SrchOrdFolio", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            document.getElementById("SerchOrd").innerHTML = Ajax.responseText;//Interaccion
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "Folio=" + Folio + " &mode=" + mode;
    Ajax.send(p);
}


function UplResbydFolio(e) {
    var Folio = e.value;
    e.value = "";
    Ajax = buscarComentario();
    Ajax.open('POST', "FormSrchOrdByFolio", true);
    Ajax.onreadystatechange = function () {
        if (Ajax.readyState === 4) {
            document.getElementById("Interaccion").innerHTML = Ajax.responseText;//Interaccion
        }
    };
    Ajax.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    var p = "Folio=" + Folio;
    Ajax.send(p);
}



var vITpoEto = "";
var fechasRep = "";
function ITpoEto() {
    if (document.getElementById("Tipo_Estudio1").value !== "0") {
        vITpoEto = "?ITpoEto=" + document.getElementById("Tipo_Estudio1").value;
    }
    return vITpoEto;
}
function ITpoEto2() {
    if (document.getElementById("Tipo_Estudio2").value !== "0") {
        vITpoEto = "?ITpoEto=" + document.getElementById("Tipo_Estudio2").value;
    }
    return vITpoEto;
}
function fchROrdGrl() {
    if (document.getElementById("fechaI1Ord").value !== "" & document.getElementById("fechaF1Ord").value !== "") {
        fechasRep = "?fchaI=" + document.getElementById("fechaI1Ord").value + " &fchaF=" + document.getElementById("fechaF1Ord").value;
    }
    return fechasRep;
}

function fchROrdGrlEmp() {
    if (document.getElementById("fechaI1OrdEmp").value !== "" & document.getElementById("fechaF1OrdEmp").value !== "") {
        fechasRep = "?fchaI=" + document.getElementById("fechaI1OrdEmp").value + " &fchaF=" + document.getElementById("fechaF1OrdEmp").value;
    }
    return fechasRep;
}

function ScannOrderRes(elemt) {
    if (elemt.value !== "") {
        var val = elemt.value;
        elemt.value = "";
        OpenRep('PrintRes?ScannBarr=' + val);
    } else {
        alert("Escanea un Orden");
    }
}

function FolioOrderRep() {
    if (document.getElementById("FolioOrdenRs").value !== "") {
        var val = document.getElementById("FolioOrdenRs").value;
        document.getElementById("FolioOrdenRs").value = "";
        fechasRep = "?OrdFol=" + val;
    }
    return fechasRep;
}

function fchROrdGrlCrt() {
    if (document.getElementById("fechaI1OrdCrt").value !== "" & document.getElementById("fechaF1OrdCrt").value !== "") {
        fechasRep = "?fchaI=" + document.getElementById("fechaI1OrdCrt").value + " &fchaF=" + document.getElementById("fechaF1OrdCrt").value;
    }
    return fechasRep;
}

function fchROrdGrlEmpCrt() {
    if (document.getElementById("fechaI1OrdEmpCrt").value !== "" & document.getElementById("fechaF1OrdEmpCrt").value !== "") {
        fechasRep = "?fchaI=" + document.getElementById("fechaI1OrdEmpCrt").value + " &fchaF=" + document.getElementById("fechaF1OrdEmpCrt").value;
    }
    return fechasRep;
}

function OpenRep(url) {
    window.open(url);
}
function verRep(id) {
    var IDs = ['CardEstudios', 'CardOrdenes', 'CardResultados','CardCortes'];
    for (var i = 0; i < IDs.length; i++) {
        document.getElementById(IDs[i]).style.display = "none";
    }
    document.getElementById(id).style.display = "block";
}
//CancelOrd(id)