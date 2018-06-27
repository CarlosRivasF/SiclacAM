function recorrer() {
    if (!$("input[name=diasSemana]").is(":checked")) {
        alert("Por Favor Seleccionar algún día de la Semana");
        return false;
    }
    if (!$("input[name=permisos]").is(":checked")) {
        alert("Por Favor Seleccionar algún elemento de la lista de compras");
        return false;
    }
    alert("El Dia de Hoy: " + recorrerDiasSemana() + " \n Harás las compras siguientes : \n" + recorrerListacompras());
}
function recorrerListacompras() {
    var listaCompras = '';    
            $("input[name=permisos]").each(function (index) {
        if ($(this).is(':checked')) {
            listaCompras += ' &permiso' + $(this).val() + '=' + $(this).val() + ' ';
        }
    });
    return listaCompras;
}

function recorrerDiasSemana() {
    var listaDias = '';
    $("input[name=diasSemana]").each(function (index) {
        if ($(this).is(':checked')) {
            listaDias = $(this).val();
        }
    });
    return listaDias;
}

 