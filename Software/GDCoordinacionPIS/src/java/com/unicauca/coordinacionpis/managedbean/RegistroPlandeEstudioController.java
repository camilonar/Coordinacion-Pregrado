/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.bean.form.FormElement;
import com.openkm.sdk4j.bean.form.Input;
import com.openkm.sdk4j.exception.AccessDeniedException;
import com.openkm.sdk4j.exception.AutomationException;
import com.openkm.sdk4j.exception.DatabaseException;
import com.openkm.sdk4j.exception.ExtensionException;
import com.openkm.sdk4j.exception.FileSizeExceededException;
import com.openkm.sdk4j.exception.ItemExistsException;
import com.openkm.sdk4j.exception.LockException;
import com.openkm.sdk4j.exception.NoSuchGroupException;
import com.openkm.sdk4j.exception.NoSuchPropertyException;
import com.openkm.sdk4j.exception.ParseException;
import com.openkm.sdk4j.exception.PathNotFoundException;
import com.openkm.sdk4j.exception.RepositoryException;
import com.openkm.sdk4j.exception.UnknowException;
import com.openkm.sdk4j.exception.UnsupportedMimeTypeException;
import com.openkm.sdk4j.exception.UserQuotaExceededException;
import com.openkm.sdk4j.exception.VersionException;
import com.openkm.sdk4j.exception.VirusDetectedException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.unicauca.coordinacionpis.classMetadatos.MetadatosPlanEstudio;
import com.unicauca.coordinacionpis.managedbean.Document.RegistroDocumentoTemplate;
import com.unicauca.coordinacionpis.utilidades.ConexionOpenKM;
import com.unicauca.coordinacionpis.utilidades.Validador;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author alexa
 */
@ManagedBean
@ViewScoped
public class RegistroPlandeEstudioController extends RegistroDocumentoTemplate implements Serializable {

    /**
     * Variable donde se guarda los metadatos del archivo
     */
    private MetadatosPlanEstudio metadatosPlandeEstudio;
    /**
     * Variable donde se guarda el nombre de archivo
     */
    private String nombreArchivo;
    /**
     * Variable donde se guarda el exito de subir el archivo
     */
    private boolean exitoSubirArchivo;
    /**
     * Variable donde se guarda el archivo seleccionado mediante un fileupload
     */
    private UploadedFile archivoPlan;
    /**
     * Variable donde se guarda los datos extras de un archivo
     */
    private String datos;
    /**
     * Variable donde se guarda los documentos de un plan de estudio
     */
    private List<Document> documentosPlanEstudio;
    /**
     * Variable utilizada para escribir
     */
    private StreamedContent streamedContent;
    /**
     * Variable utilizada para convertir el archivo en un documento
     */
    private com.openkm.sdk4j.bean.Document documento;
    /**
     * Variable utilizada para escribir
     */
    private BufferedOutputStream output;
    /**
     * Variable utilizada para leer
     */
    private BufferedInputStream input;
    /**
     * Variable utilizada para darle un formato a la fecha
     */
    private SimpleDateFormat formatoFecha;
    /**
     * Variable donde se guarda el documento anterior
     */
    private String documentoAnterior;
    /**
     * Variable donde se guarda cosas auxiliares
     */
    private int auxNumeroPlan;
    /**
     * Variable donde se guarda cosas auxiliares
     */
    private String auxAcuerdoPlan;
    /**
     * Variable donde se guarda cosas auxiliares
     */
    private Date auxFechaPlan;

    /**
     * Constructor encargado de inicializar algunas de los atributos asignados a
     * la clase
     */
    public RegistroPlandeEstudioController() {
        super();
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        this.metadatosPlandeEstudio = new MetadatosPlanEstudio();
        this.documentosPlanEstudio = new ArrayList<>();
        this.exitoSubirArchivo = false;
        this.documentoAnterior = "";
        auxNumeroPlan = 1;
        auxAcuerdoPlan = "";
        auxFechaPlan = null;
    }

    /**
     * Metodo encargado de retornar a la vista PlanEstudio un objeto de tipo
     * streamedContet para poder visualizar un plan de estudio.
     *
     * @return retorna un objeto de tipo streamedContent.
     */
    public StreamedContent getStreamedContent() {
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return new DefaultStreamedContent();

        } else {
            return streamedContent;
        }
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    /**
     * Metodo encargado de restaurar los metadatos del plan de estudio y
     * actualizar la vista de registro de un plan.
     */
    public void cancelarRegistroPlanEstudio() {
        metadatosPlandeEstudio = new MetadatosPlanEstudio();
        limpiarVariables();
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("formSeleccionarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoPlanEstudio");
        rc.update("formMetadatosPlanEstudio");

    }

    /**
     * Recibe como parametro el archivo(Plan de estudio) que se va a guardar y
     * se obtiene el archivo y nombre, acontinuacion se envia un mensaje de
     * exito a la vista de registro y se actualiza el campo del archivo en la
     * vista.
     *
     * @param event archivo cargado desde el computador.
     */
    public void seleccionarArchivo(FileUploadEvent event) {
        nombreArchivo = event.getFile().getFileName();
        System.out.println("nombre archivo: " + nombreArchivo);
        archivoPlan = event.getFile();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + nombreArchivo + "' se seleccionó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado
        exitoSubirArchivo = true;
        rc.update("formSeleccionarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoPlanEstudio");
        rc.update("formMetadatosPlanEstudio");

    }

    /**
     * Recibe como parametro el archivo cargado desde el computador, obtiene el
     * nombre, y el archivo. A continuacion se envia un mensaje de exito a la
     * vista de actualizacion y se actualiza el formulario.
     *
     * @param event archivo cargado desde el computador.
     */
    public void seleccionarArchivoActualizacion(FileUploadEvent event) {
        nombreArchivo = event.getFile().getFileName();
        System.out.println("nombre archivo: " + nombreArchivo);
        archivoPlan = event.getFile();
        FacesMessage mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + nombreArchivo + "' se seleccionó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado
        exitoSubirArchivo = true;
        rc.update("formActualizarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoActualizarPlanEstudio");
        rc.update("formActualizarMetadatosPlanEstudio");

    }

    /**
     * Realiza un llamado al repositorio de openkm para verificar si existe el
     * directorio donde se van a guardar los planes de estudio, una ves
     * verificado esto, verifica si el plan de estudio a guardar existe,
     * realizadas estas verificaciones, si la carpeta no existe la crea, si el
     * plan de estudio existe enviar un mensaje de error a la vista, de lo
     * contrario realiza un llamado a openkm para guardar el plan de estudio,
     * guardado el plan le asigna los metadatos al plan de estudios creado.
     * Terminados estos pasos envia un mensaje de exito a la vista de
     * PlanEstudio, actualiza el formulario de registro y la vista que contiene
     * los planes de estudio(planEstudio).
     */
    public void aceptarRegistroPlanEstudio() {
        Document subirDocumento = this.subirDocumento(archivoPlan);
        RequestContext rc = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        //TODO: ARREGLAR EL MENSAJE:  MODFIQUE EL CODIGO DE SUBIR DOCUMENTO 
        if (subirDocumento == null) {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + nombreArchivo + "' esta repetido, se agregara un consecutivo al nuevo documento");
        } else {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + nombreArchivo + "' se registró con exito");
        }
        //rc.execute("PF('dlgRegistroPlandeEstudio').hide()");//Cerrar el dialog que contiene el formulario
        FacesContext.getCurrentInstance().addMessage(null, message);
        limpiarVariables();
        rc.update("formSeleccionarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoPlanEstudio");
        rc.update("formMetadatosPlanEstudio");//Actualizar el formulario de registro
        rc.update("lstPlanesEstudio");
        rc.execute("PF('dlgRegistroPlandeEstudio').hide()");//Cerrar el dialog que contiene el formulario
        rc.update("formMetadatosPlanEstudio");//Actualizar el formulario de registro
        rc.update("mensaje2");
        rc.update("msg");

        limpiarVariables();

        rc.update("formSeleccionarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoPlanEstudio");
        rc.update("formMetadatosPlanEstudio");//Actualizar el formulario de registro

        rc.update("lstPlanesEstudio");

        rc.execute("PF('dlgRegistroPlandeEstudio').hide()");//Cerrar el dialog que contiene el formulario

        rc.update("formMetadatosPlanEstudio");//Actualizar el formulario de registro

        rc.update("mensaje2");
        rc.update("msg");

    }

    /**
     * Recibe como parametro un plan de estudio, luego de esto obtiene los
     * metadatos del plan, los guarda en un objeto de metadatosPlanEstudio y una
     * variables auxiliares para poder saber si al momento de actualizar un plan
     * de estudio se han realizado cambios en los metadatos. Obtenidos los
     * metadatos del documento se actualiza la vista de ActualizaPlanEstudio
     * para mostrar los metadatos asociados al plan de estudio.
     *
     * @param document plan de estudio
     */
    public void cargarPlanEstudio(Document documento) {

        RequestContext rc = RequestContext.getCurrentInstance();

        this.documento = documento;
        List<FormElement> fElements;
        try {
            fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:PlanEstudio");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:PlanEstudio.Numero")) {
                    Input name = (Input) fElement;
                    this.metadatosPlandeEstudio.setNumero(Integer.parseInt(name.getValue()));
                }
                if (fElement.getName().equals("okp:PlanEstudio.Acuerdo")) {
                    Input name = (Input) fElement;
                    this.metadatosPlandeEstudio.setAcuerdo(name.getValue());
                }
                if (fElement.getName().equals("okp:PlanEstudio.Vigencia")) {
                    Input name = (Input) fElement;
                    this.metadatosPlandeEstudio.setVigencia(formatoFecha.parse(name.getValue()));
                }
            }
        } catch (java.text.ParseException | IOException | ParseException | NoSuchGroupException | PathNotFoundException | RepositoryException | DatabaseException | UnknowException | WebserviceException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }

        nombreArchivo = nombreDelArchivo(documento.getPath());
        documentoAnterior = nombreArchivo;
        this.exitoSubirArchivo = true;

        rc.update("formActualizarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoActualizarPlanEstudio");
        rc.update("formActualizarMetadatosPlanEstudio");

    }

    /**
     * Verifica si se han realizado cambios en el documento de plan de estudio y
     * sus metadatos, si han habido cambios realiza un llamado a openkm para
     * guardar los cambios. Realizados estos cambios, reinicia los metadatos a
     * sus valores por defecto, actualiza y envia un mensaje de exito a la vista
     * PlanEstudio
     */
    public void editarPlanEstudio() {

        RequestContext rc = RequestContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El plan de estudio se editó con éxito");
        String path = this.getPathDocumento() + nombreArchivo;
        try {
            if (!nombreArchivo.equals(documentoAnterior)) {
                okm.deleteDocument(this.getPathDocumento() + "/" + documentoAnterior);
                subirDocumento(archivoPlan);
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El plan de estudio se editó con éxito");
            } else {
                okm.addGroup(path, "okg:PlanEstudio");
                List<FormElement> fElements = okm.getPropertyGroupProperties(path, "okg:PlanEstudio");
                for (FormElement fElement : fElements) {
                    if (fElement.getName().equals("okp:PlanEstudio.Numero")) {
                        Input name = (Input) fElement;
                        name.setValue("" + this.metadatosPlandeEstudio.getNumero());
                    }
                    if (fElement.getName().equals("okp:PlanEstudio.Acuerdo")) {
                        Input name = (Input) fElement;
                        name.setValue(this.metadatosPlandeEstudio.getAcuerdo());
                    }
                    if (fElement.getName().equals("okp:PlanEstudio.Vigencia")) {
                        Input name = (Input) fElement;
                        name.setValue("" + formatoFecha.format(this.metadatosPlandeEstudio.getVigencia()));
                    }
                }
                okm.setPropertyGroupProperties(path, "okg:PlanEstudio", fElements);
            }
        } catch (LockException | PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | UnknowException | WebserviceException | ExtensionException | NoSuchGroupException | AutomationException | IOException | ParseException | NoSuchPropertyException ex) {
            Logger.getLogger(RegistroPlandeEstudioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (message != null) {
            FacesContext.getCurrentInstance().addMessage(null, message);

        }
        rc.update("formMetadatosPlanEstudio");//Actualizar el formulario de registro
        rc.execute("PF('dlgEditarPlanEstudio').hide()");//Cerrar el dialog que contiene el formulario

        limpiarVariables();

        rc.update("formActualizarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoActualizarPlanEstudio");
        rc.update("formActualizarMetadatosPlanEstudio");//Actualizar el formulario de registro
        rc.update("lstPlanesEstudio");
        rc.execute("PF('dlgEditarPlanEstudio').hide()");//Cerrar el dialog que contiene el formulario

    }

    /**
     * Limita la fecha de la vigencia del plan de estudio
     *
     * @return
     */
    public Date fechaActual() {
        return new Date();
    }

    /**
     * Reinicia los atributos del objeto metadatosPlanEstudio, y tambien elimina
     * el archivo subido desde el computador.
     */
    public void limpiarVariables() {
        metadatosPlandeEstudio = new MetadatosPlanEstudio();
        nombreArchivo = "";
        archivoPlan = null;
        exitoSubirArchivo = false;

    }

    /**
     * Recibe como parametro el plan de estudios a descargar, obtiene la ruta de
     * este plan de estudios en openkm y recupera el archivo completo. Una ves
     * recuperado lo retorna a la vista PlanEstudio para poder ser descargado.
     *
     * @param document plan de estudios a descargar
     * @return Retorna un objeto de tipo StreamedContent
     */
    public StreamedContent descargarDocumento(Document document) {
        StreamedContent file = null;
        com.openkm.sdk4j.bean.Document doc = document;
        try {
            InputStream is = okm.getContent(doc.getPath());
            file = new DefaultStreamedContent(is, "application/pdf", nombreDelArchivo(doc.getPath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathNotFoundException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessDeniedException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknowException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebserviceException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return file;
    }

    /**
     * Recibe como parametro la ruta del plan de estudio en openkm, luego
     * obtiene el nombre del plan de estudio de la ruta y retorna este nombre.
     *
     * @param path Ruta completa del plan de estudio en openkm
     * @return nombre del plan de estudio
     */
    public String nombreDelArchivo(String path) {
        String partesPath[] = path.split("/");
        return partesPath[partesPath.length - 1];
    }

    /**
     * Recibe como parametro la fecha de creacion de un plan de estudio, luego
     * de esto le da el formato establecido y la retorna a la vista para ser
     * mostrada.
     *
     * @param fecha fecha creacion plan de estudio
     * @return fecha con nuevo formato
     */
    public String fecha(Date fecha) {
        return formatoFecha.format(fecha.getTime());
    }

    /**
     * Recibe como parametro un plan de estudio. Una ves recibido realiza un
     * llamado a openkm y le pasa la ruta completa del plan para obtener el
     * docuemtno completo, recuperado el documeto lo envia al visor de
     * documentos para ser mostrado.
     *
     * @param doc plan de estudio
     */
    public void visualizardePlanEstudio(Document doc) {
        System.out.println(";; " + doc.getPath());
        InputStream in = null;
        try {
            this.documento = doc;
            in = okm.getContent(doc.getPath());
            System.out.println("in: " + in.toString());
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
            //-------
            Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            byte[] b = (byte[]) session.get("reportBytes");
            System.out.println("ANTeS");
            if (b != null) {
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(b), "application/pdf");
            }
            System.out.println("b: " + b);
            System.out.println("StreamContent" + streamedContent.getName());
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":visualizacionPlanPdf");
            requestContext.execute("PF('visualizarPlanPDF').show()");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cambia de estado a la variable exitoSubirArchivo para poder mostrar en la
     * vista de registro de plan de estudio que no hay un archivo seleccionado,
     * luego de esto actualiza la vista de registro.
     */
    public void cambiarArchivo() {
        exitoSubirArchivo = false;
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("formSeleccionarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoPlanEstudio");
        rc.update("formMetadatosPlanEstudio");

    }

    /**
     * Cambia de estado a la variable exitoSubirArchivo para poder mostrar en la
     * vista de actualizacion de plan de estudio que no hay un archivo
     * seleccionado, luego de esto actualiza la vista de actualizacion.
     */
    public void cambiarArchivoActualizacion() {
        exitoSubirArchivo = false;
        RequestContext rc = RequestContext.getCurrentInstance();
        rc.update("formActualizarArchivoPlanEstudio");
        rc.update("formArchivoSelecionadoActualizarPlanEstudio");
        rc.update("formActualizarMetadatosPlanEstudio");

    }

    /**
     * Realiza un llamado a openkm para verificar si hay una comunicacion con el
     * repositorio, en caso de que haya comunicacion retorna true, de lo
     * contrario retorna false.
     *
     * @return valor booleano
     */
    public boolean getComprobarConexionOpenKM() {
        boolean conexion = true;
        try {
            okm.getAppVersion();

        } catch (RepositoryException ex) {
            conexion = false;
        } catch (DatabaseException ex) {
            conexion = false;
        } catch (UnknowException ex) {
            conexion = false;
        } catch (WebserviceException ex) {
            conexion = false;
        }
        return conexion;
    }

    public void confirmarEliminacion(com.openkm.sdk4j.bean.Document documento) {
        System.out.println("Recibí documento: " + documento.getPath());
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "¿Está seguro que desea eliminar el documento?"));
        context.execute("PF('Confirmacion').show()");
        this.documento = documento;
    }

    /**
     * Recibe como parametro un plan de estudio, realiza un llamado a openkm
     * para eliminar el plan de estudio, al realizar este llamado se pasa la
     * ruta completa del plan. Una ves eliminado el plan de estudio se realiza
     * el llamado al metodo purgeTrash de openkm para eliminar el documento de
     * la papelera.
     *
     * @param doc plan de estudio
     */
    public void cancelarEdicion() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEditarPlanEstudio').hide()");
    }

    public void deleteDocument() {
        try {
            okm.deleteDocument(this.documento.getPath());
            okm.purgeTrash();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo se eliminó con éxito"));
            requestContext.execute("PF('Confirmacion').hide()");
            //requestContext.execute("PF('mensajeRegistroExitoso').show()");
            requestContext.update(":lstPlanesEstudio");
            requestContext.update("formPlanesdeEstudio");
        } catch (Exception e) {

        }
    }

    /**
     * Retorna la ruta en donde sera guardado el documento
     *
     * @return
     */
    @Override
    public String getPathDocumento() {
        return "/okm:root/Coordinacion/Plan de Estudios/" + this.getPrgramaUsuario() + "/";
    }

    /**
     * Registra los metadatos a el archivo pasado como parametro
     *
     * @param archivOferta
     */
    @Override
    public void addMetadata(String archivOferta) {
        try {
            String path = this.getPathDocumento();
            okm.addGroup(path + archivOferta, "okg:PlanEstudio");

            List<FormElement> fElements = okm.getPropertyGroupProperties(path + archivOferta, "okg:PlanEstudio");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:PlanEstudio.Numero")) {
                    Input name = (Input) fElement;
                    name.setValue("" + this.metadatosPlandeEstudio.getNumero());
                }
                if (fElement.getName().equals("okp:PlanEstudio.Acuerdo")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosPlandeEstudio.getAcuerdo());
                }
                if (fElement.getName().equals("okp:PlanEstudio.Vigencia")) {
                    Input name = (Input) fElement;
                    name.setValue("" + formatoFecha.format(this.metadatosPlandeEstudio.getVigencia()));
                }
            }
            okm.setPropertyGroupProperties(path + archivOferta, "okg:PlanEstudio", fElements);
        } catch (NoSuchGroupException | LockException | PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | ExtensionException | AutomationException | UnknowException | WebserviceException | IOException | ParseException | NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Getters and setters
     *
     * @return
     */
    @Override
    public String getOKGPropierties() {
        return "okg:PlanEstudio";
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public boolean isExitoSubirArchivo() {
        return exitoSubirArchivo;
    }

    public void setExitoSubirArchivo(boolean exitoSubirArchivo) {
        this.exitoSubirArchivo = exitoSubirArchivo;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public void setDocumentosPlanEstudio(List<Document> documentosPlanEstudio) {
        this.documentosPlanEstudio = documentosPlanEstudio;
    }

    public MetadatosPlanEstudio getMetadatosPlandeEstudio() {
        return metadatosPlandeEstudio;
    }

    public void setMetadatosPlandeEstudio(MetadatosPlanEstudio metadatosPlandeEstudio) {
        this.metadatosPlandeEstudio = metadatosPlandeEstudio;
    }

}
