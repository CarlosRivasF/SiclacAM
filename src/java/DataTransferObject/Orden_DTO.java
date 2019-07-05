package DataTransferObject;

import DataAccesObject.Orden_DAO;
import DataBase.Util;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author ZionSystems
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "id_Orden",
    "unidad",
    "Paciente",
    "Medico",
    "Empleado",
    "FechaOr",
    "Hora",
    "montoRestante",
    "montoPagado",
    "Det_Orden",
    "pagos",
    "estado",
    "convenio",
    "Folio_Unidad",
    "montoPartselect"
})
@XmlRootElement(name = "Orden_DTO")
public class Orden_DTO {

    @XmlElement(name = "id_Orden", required = true)
    protected int id_Orden;
    @XmlElement(name = "unidad", required = true)
    protected Unidad_DTO unidad;
    @XmlElement(name = "Paciente", required = true)
    protected Paciente_DTO Paciente;
    @XmlElement(name = "Medico", required = true)
    protected Medico_DTO Medico;
    @XmlElement(name = "Empleado", required = true)
    protected Empleado_DTO Empleado;
    @XmlElement(name = "FechaOr", required = true)
    protected String FechaOr;
    @XmlElement(name = "Hora", required = true)
    protected String Hora;
    @XmlElement(name = "montoRestante", required = true)
    protected Float montoRestante;
    @XmlElement(name = "montoPagado", required = true)
    protected Float montoPagado;

    @XmlElementWrapper(name = "Det_Orden")
    @XmlElement(name = "Det_Orden_DTO")
    protected List<Det_Orden_DTO> Det_Orden;

    @XmlElementWrapper(name = "pagos")
    @XmlElement(name = "Pago_DTO")
    protected List<Pago_DTO> pagos;

    @XmlElement(name = "estado", required = true)
    protected String estado;
    @XmlElement(name = "convenio", required = true)
    protected String convenio;
    @XmlElement(name = "Folio_Unidad", required = true)
    protected int Folio_Unidad;
    @XmlElement(name = "montoPartselect", required = true)
    private Float montoPartselect;

    public int getId_Orden() {
        return id_Orden;
    }

    public void setId_Orden(int id_Orden) {
        this.id_Orden = id_Orden;
    }

    public Unidad_DTO getUnidad() {
        return unidad;
    }

    public void setUnidad(Unidad_DTO unidad) {
        this.unidad = unidad;
    }

    public Paciente_DTO getPaciente() {
        return Paciente;
    }

    public void setPaciente(Paciente_DTO Paciente) {
        this.Paciente = Paciente;
    }

    public Medico_DTO getMedico() {
        return Medico;
    }

    public void setMedico(Medico_DTO Medico) {
        this.Medico = Medico;
    }

    public Empleado_DTO getEmpleado() {
        return Empleado;
    }

    public void setEmpleado(Empleado_DTO Empleado) {
        this.Empleado = Empleado;
    }

    public String getFecha() {
        return FechaOr;
    }

    public void setFecha(String Fecha) {
        this.FechaOr = Fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String Hora) {
        this.Hora = Hora;
    }

    public Float getMontoRestante() {
        return Util.redondearDecimales(montoRestante);
    }

    public void setMontoRestante(Float montoRestante) {
        this.montoRestante = Util.redondearDecimales(montoRestante);
    }

    public Float getMontoPagado() {
        return Util.redondearDecimales(montoPagado);
    }

    public void setMontoPagado(Float montoPagado) {
        this.montoPagado = Util.redondearDecimales(montoPagado);
    }

    public List<Det_Orden_DTO> getDet_Orden() {
        return Det_Orden;
    }

    public void setDet_Orden(List<Det_Orden_DTO> Det_Orden) {
        this.Det_Orden = Det_Orden;
    }

    public List<Pago_DTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago_DTO> pagos) {
        this.pagos = pagos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConvenio() {
        return convenio;
    }

    public void setConvenio(String convenio) {
        this.convenio = convenio;
    }

    public int getFolio_Unidad() {
        return Folio_Unidad;
    }

    public void setFolio_Unidad(int Folio_Unidad) {
        this.Folio_Unidad = Folio_Unidad;
    }

    public Float getMontoPartselect() {
        return montoPartselect;
    }

    public void setMontoPartselect(Float montoPartselect) {
        this.montoPartselect = montoPartselect;
    }

    public void ToXml(Orden_DTO orden) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Orden_DTO.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);

            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");

            jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "SICLAC.Orden_DTO.xsd");

            jaxbMarshaller.marshal(orden, System.out);

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            /**
             * La clase JAXBContext proporciona el punto de entrada del cliente
             * a la API de JAXB
             */
            JAXBContext jaxbContext = JAXBContext.newInstance(Estudio_DTO.class);
            /**
             * La clase Marshaller proporciona a la aplicación cliente la
             * capacidad de convertir un árbol de contenido Java en datos XML.
             */
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            /**
             * Creamos una objeto cliente y agregamos algunos datos
             */
            Orden_DAO O = new Orden_DAO();
            Orden_DTO orden = O.getOrden(12);

            /**
             * Se definen algunas propiedades standar
             */
            //Datos formateados con salto de linea y sangria
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Especifica el valor del atributo xsi: schemaLocation para colocar en la salida XML 
            jaxbMarshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI);
            //Codificacion de salida
            jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
            //El nombre del esquemaXSD para el atributo xsi: noNamespaceSchemaLocation
            jaxbMarshaller.setProperty(Marshaller.JAXB_NO_NAMESPACE_SCHEMA_LOCATION, "miesquema.xsd");

            /**
             * Se genera el XML y se muestra en consola
             */
            jaxbMarshaller.marshal(orden.getDet_Orden().get(0).getEstudio(), System.out);

        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
    }
}
