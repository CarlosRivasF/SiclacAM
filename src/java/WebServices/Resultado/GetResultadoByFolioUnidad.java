/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebServices.Resultado;

import DataAccesObject.Orden_DAO;
import DataTransferObject.Orden_DTO;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Carlos Rivas
 */
@WebService(serviceName = "GetResultadoByFolioUnidad")
public class GetResultadoByFolioUnidad {

    /**
     * Web service operation
     * @param folio
     * @return 
     */
    @WebMethod(operationName = "GeOrden")
    public Orden_DTO GeOrden(@WebParam(name = "folio") int folio) {
        Orden_DAO O = new Orden_DAO();
       Orden_DTO ord= O.getOrden(folio);
        return ord;
    }


}
