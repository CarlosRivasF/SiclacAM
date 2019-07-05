/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices.Resultado;

import DataTransferObject.Orden_DTO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Carlos Rivas
 */


@WebService(serviceName = "WS_Suma")
public class WS_Suma {
    /**
     * Web service operation
     * @param val1
     * @param val2
     * @return 
     */
    @WebMethod(operationName = "sumar")
    public int sumar(@WebParam(name = "val1") int val1, @WebParam(name = "val2") int val2) {
        //TODO write your implementation code here:
        int resultado = val1+val2;
        return resultado;
    }

    /**
     * Web service operation
     * @param folio
     * @return 
     */
    @WebMethod(operationName = "GetOrden")
    public Orden_DTO GetOrden(@WebParam(name = "folio") String folio) {
        //TODO write your implementation code here:
        return null;
    }
}
