/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.classMetadatos.MetadatosOfertaAcademica;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.Folder;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.bean.QueryResult;
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
import com.openkm.sdk4j.exception.VirusDetectedException;
import com.openkm.sdk4j.exception.WebserviceException;
import com.unicauca.coordinacionpis.classMetadatos.Docente;

import com.unicauca.coordinacionpis.classMetadatos.MetadatosAntepoyecto;
import com.unicauca.coordinacionpis.entidades.Anteproyecto;
import com.unicauca.coordinacionpis.entidades.Formatob;
import com.unicauca.coordinacionpis.managedbean.Document.RegistroDocumentoTemplate;
import com.unicauca.coordinacionpis.sessionbean.FormatobFacade;
import com.unicauca.coordinacionpis.utilidades.ConexionOpenKM;
import com.unicauca.coordinacionpis.validadores.ValidarEdicionUsuarios;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * Controlador de las vistas: registrar, editar, listar y ver FormatoB.
 *
 */
@ManagedBean
@ViewScoped
public class RegistroFormatoBController extends RegistroDocumentoTemplate implements Serializable {

    /**
     * Facade para la conexión a la tabla FormatoB
     */
    /**
     * Facade para la conexión a la tabla FormatoB
     */
    @EJB
    private FormatobFacade ejbFormatoB;
    /**
     * Variable que encapsula los metadatos que va a tener el documento
     */
    private MetadatosAntepoyecto metadatosAnteproyectos;
    /**
     * Variable para controlar si el archvio se cargo correctamente
     */
    private boolean exitoSubirArchivo;
    /**
     * Posee el nombre del archivo que se va a guardar
     */
    private String nombreArchivo;
    /**
     * documnto que se va a guardar en el gestor
     */
    private UploadedFile archivOferta;
    /**
     * flujo para extraer informacion de openKM
     */
    private StreamedContent streamedContent;

    private String datos;
    private List<com.openkm.sdk4j.bean.Document> listadoDocsFormatoB;
    private com.openkm.sdk4j.bean.Document documento;
    /**
     * Variable para controlar el formato de fecha
     */
    private SimpleDateFormat formatoFecha;

    public RegistroFormatoBController() {
        super();
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        metadatosAnteproyectos.setViabilidad("Si");

    }

    @PostConstruct
    public void init() {
        metadatosAnteproyectos.setViabilidad("Si");
        try {
            InputStream in = okm.getContent(documento.getPath());
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
            Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            byte[] b = (byte[]) session.get("reportBytes");
            if (b != null) {
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(b), "application/pdf");
            }
        } catch (Exception e) {
        }
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public MetadatosAntepoyecto getMetadatosAnteproyectos() {
        return metadatosAnteproyectos;
    }

    public void setMetadatosAnteproyectos(MetadatosAntepoyecto metadatosAnteproyectos) {
        this.metadatosAnteproyectos = metadatosAnteproyectos;
    }

    public boolean isExitoSubirArchivo() {
        return exitoSubirArchivo;
    }

    public void setExitoSubirArchivo(boolean exitoSubirArchivo) {
        this.exitoSubirArchivo = exitoSubirArchivo;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public StreamedContent getStreamedContent() {
        return streamedContent;
    }

    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }

    public List<com.openkm.sdk4j.bean.Document> getListadoFormatoC() {
        listadoDocsFormatoB.clear();
        try {
            List<QueryResult> lista = okm.findByName(datos);
            for (int i = 0; i < lista.size(); i++) {
                String[] pathDividido = lista.get(i).getDocument().getPath().split("/");
                String path = "/" + pathDividido[1] + "/" + pathDividido[2] + "/" + pathDividido[3];
                if (path.equalsIgnoreCase("/okm:root/Coordinacion/FormatoC")) {
                    listadoDocsFormatoB.add(lista.get(i).getDocument());
                }
            }
        } catch (RepositoryException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknowException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebserviceException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoDocsFormatoB;
    }

    public Date getTodayDate() {
        return new Date();
    }

    /**
     * Se encarga de cargar el archivo
     *
     * @param event el evento disparado en la vista que contiene el archivo a
     * subir
     */
    public void seleccionarArchivo(FileUploadEvent event) {
        nombreArchivo = event.getFile().getFileName();
        archivOferta = event.getFile();
        System.out.println("archivo b:" + archivOferta.getFileName());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + event.getFile().getFileName() + "' se selccionó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("messages");
        exitoSubirArchivo = true;
        requestContext.update("formSeleccionarArchivoFormatoB");
        requestContext.update("formMetadatosFormatoB");
        requestContext.update("formArchivoSelecionadoFormatoB");
    }

    public void cambiarArchivo() {
        exitoSubirArchivo = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("dlgRegistroFormatoB");
        requestContext.update("formSeleccionarArchivoFormatoB");
        requestContext.update("formMetadatosFormatoB");
        requestContext.update("formArchivoSelecionadoFormatoB");
    }

    public void cancelarFormatoB() {
        exitoSubirArchivo = false;
        nombreArchivo = "";
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoB");
        requestContext.update("formMetadatosFormatoB");
        requestContext.execute("PF('dlgRegistroFormatoB').hide()");
        requestContext.update("formArchivoSelecionadoFormatoB");
    }

    public void aceptarFormatoB(Anteproyecto anteproyecto) throws PathNotFoundException {
        Document documentoCreado = this.subirDocumento(archivOferta);
        Formatob formatob = new Formatob();
        formatob.setAnteproyectoFormatoB(anteproyecto);
        formatob.setClaveFormatoB(documentoCreado.getUuid());
        this.ejbFormatoB.create(formatob);
        this.ejbFormatoB.limpiarCache();
    }

    public void actualizarInfoFormatoB() {
        try {
            okm.addGroup(documento.getPath(), "okg:FormatoB");
            List<FormElement> fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:FormatoB");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoB.docente")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getProfesor());
                }
                if (fElement.getName().equals("okp:FormatoB.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getTitulo());
                }
                if (fElement.getName().equals("okp:FormatoB.Fecha")) {
                    /*Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getFecha());*/

                    Input name = (Input) fElement;
                    Date aux1 = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
                    String aux2 = name.getValue();
                    System.out.println("actualizar fecha1 es " + name.getValue());
                    System.out.println("actualizar fecha2 es " + aux1);
                    name.setValue(this.metadatosAnteproyectos.getFecha());
                    name.setValue(this.metadatosAnteproyectos.getDate3().toString());
                }
                if (fElement.getName().equals("okp:FormatoB.Viabilidad")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getViabilidad());
                }
                if (fElement.getName().equals("okp:FormatoB.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante1());
                }
                if (fElement.getName().equals("okp:FormatoB.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante2());
                }
                if (fElement.getName().equals("okp:FormatoB.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getActaAprobacion());
                }
            }
            okm.setPropertyGroupProperties(documento.getPath(), "okg:FormatoB", fElements);
        } catch (NoSuchGroupException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LockException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathNotFoundException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessDeniedException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExtensionException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AutomationException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknowException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebserviceException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMetadatosEditFormatoB");
        requestContext.execute("PF('dlgEditarFormatoB').hide()");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información se editó con éxito"));
        requestContext.getCurrentInstance().update("msgRFA");
    }

    public void cargarDatosEdicion(com.openkm.sdk4j.bean.Document documento) throws java.text.ParseException {
        this.documento = documento;
        List<FormElement> fElements;
        try {
            fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:FormatoB");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoB.docente")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setProfesor(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoB.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setTitulo(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoB.Fecha")) {
                    /*Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setFecha(name.getValue());*/

                    Input name = (Input) fElement;
                    String aux2 = name.getValue();
                    String[] partes = aux2.split(" ");
                    String mes = partes[1];
                    String dia = partes[2];
                    String anio = partes[5];
                    String fechafinal = dia + "-" + mes + "-" + anio;
                    System.out.println("dia es " + dia);
                    System.out.println("mes es " + mes);
                    System.out.println("anio es " + anio);
                    System.out.println("fecha sumada " + fechafinal);
                    DateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                    Date aux1 = new Date();
                    aux1 = format.parse(fechafinal);
                    this.metadatosAnteproyectos.setFecha(name.getValue());
                    this.metadatosAnteproyectos.setDate3(aux1);
                    System.out.println("en cargar fecha1 es " + name.getValue());
                    System.out.println("en cargar fecha2 es " + aux1);
                }
                if (fElement.getName().equals("okp:FormatoB.Viabilidad")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setViabilidad(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoB.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setNombreEstudiante1(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoB.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setNombreEstudiante2(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoB.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setActaAprobacion(name.getValue());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchGroupException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathNotFoundException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknowException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebserviceException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMetadatosEditFormatoB");
        requestContext.execute("PF('dlgEditarFormatoB').show()");
    }

    public List<Docente> getListaDocentes() {
        List<Docente> listaDocentes = new ArrayList<>();
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpGet httpget = new HttpGet("http://wmyserver.sytes.net:8080/JefaturaPIS/webresources/docente");
//        httpget.setHeader("Content-type", "application/json");
//        String strResultado = "NaN";
//        try {
//            //ejecuta
//            HttpResponse response = httpclient.execute(httpget);
//            //Obtiene la respuesta del servidor
//            String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
//            JSONArray array = new JSONArray(jsonResult);
//            //JSONObject object = new JSONObject(jsonResult);
//            //obtiene el status
//            // String status = object.getString("status");
//            //200 -> todo esta bien
//            //if( status.equals("200") )
//            //{
//            strResultado = "";
//            //extrae los registros
//            //JSONArray array = new JSONArray(object.getString("Registros"));
//            for (int i = 0; i < array.length(); i++) {
//                //recorre cada registro y concatena el resultado
//                JSONObject row = array.getJSONObject(i);
//                Docente docente = new Docente();
//                String nombres = row.getString("nombres");
//                docente.setNombres(nombres);
//                String apellidos = row.getString("apellidos");
//                docente.setApellidos(apellidos);
//                String documento = row.getString("documento");
//                docente.setDocumento(documento);
//                //String estId = row.getString("estId");
//                //String apellidos = row.getString("apellidos");
//                //System.out.println("PLC_TU "+ (i+1) +"\n\n"+"Nombres: "+ nombres + "\n"+"Apellidos: "+ apellidos + "\n\n"+ "\n"+"Estudios: "+ estId + "\n\n");
//                listaDocentes.add(docente);
//            }
//
//            // }
//        } catch (ClientProtocolException e) {
//            strResultado = e.getMessage();
//            e.printStackTrace();
//        } catch (IOException e) {
//            strResultado = e.getMessage();
//            e.printStackTrace();
//        } catch (JSONException e) {
//            strResultado = e.getMessage();
//            e.printStackTrace();
//        }
        if (listaDocentes.isEmpty()) {
            Docente docente;
            System.out.println("lista vacia");
            for (int i = 0; i < 5; i++) {
                docente = new Docente();
                docente.setNombres("Docente fake" + i);
                docente.setApellidos("Fake ");
                docente.setDocumento("12345");
                listaDocentes.add(docente);
            }
        }
        System.out.println("tamaño lista profesores" + listaDocentes.size());
        return listaDocentes;
    }

    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((line = rd.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }

    public String nombreDelArchivo(String path) {
        String partesPath[] = path.split("/");
        return partesPath[partesPath.length - 1];
    }

    public String fecha(Calendar fecha) {
        return formatoFecha.format(fecha.getTime());
    }

    /**
     * Funcion que permite descargar un determinado documento desde el gestor de
     * documentos OpenKM
     *
     * @param queryResult Documento que se desea descargar desde el gestor
     * @return retorna el contenido del documento como un flujo de datos si no
     * se encuentra devuelve null
     */
    public StreamedContent descargarDocumento(com.openkm.sdk4j.bean.Document queryResult) {
        StreamedContent file = null;
        com.openkm.sdk4j.bean.Document doc = queryResult;
        try {
            InputStream is = okm.getContent(doc.getPath());
            file = new DefaultStreamedContent(is, "application/pdf", nombreDelArchivo(doc.getPath()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException | PathNotFoundException | AccessDeniedException | DatabaseException | UnknowException | WebserviceException | IOException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return file;
    }

    public void visualizarDocumento(com.openkm.sdk4j.bean.Document documento) {
        try {
            this.documento = documento;
            InputStream in = okm.getContent(documento.getPath());
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
            Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            byte[] b = (byte[]) session.get("reportBytes");
            if (b != null) {
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(b), "application/pdf");
            }
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update(":visualizacion");
            requestContext.execute("PF('visualizarPDF').show()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmarEliminacion(com.openkm.sdk4j.bean.Document documento) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "¿Está seguro que desea eliminar el documento?"));
        context.execute("PF('Confirmacion').show()");
        this.documento = documento;
    }

    public void deleteDocument() {
        try {
            okm.deleteDocument(documento.getPath());
            okm.purgeTrash();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.execute("PF('Confirmacion').hide()");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo se eliminó con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            requestContext.update("msg");
            requestContext.update("formListaAnteproyectos");
        } catch (Exception e) {
        }
    }

    public void cancelarEditar() {
        System.out.println("incas");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEditarFormatoB').hide()");
    }

    public void cancelarEdicion() {
        System.out.println("incas");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoB");
        requestContext.update("formMetadatosFormatoB");
        requestContext.update("formArchivoSelecionadoFormatoB");
        requestContext.execute("PF('dlgEditarFormatoB').hide()");
        requestContext.execute("PF('dlgRegistroFormatoB').hide()");
    }

    public void cancelarRegistro() {
        System.out.println("invocado apá");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoB");
        requestContext.update("formMetadatosFormatoB");
        requestContext.update("formArchivoSelecionadoFormatoB");
        requestContext.execute("PF('dlgRegistroFormatoB').hide()");
    }

    public boolean getComprobarConexionOpenKM() {
        boolean conexion = true;
        try {
            okm.getAppVersion();
        } catch (RepositoryException | DatabaseException | UnknowException | WebserviceException ex) {
            conexion = false;
        }
        return conexion;
    }

    @Override
    public String getPathDocumento() {
        return "/okm:root/Coordinacion/Anteproyectos/" + this.getPrgramaUsuario() + "/FormatoB/";
    }

    @Override
    public void addMetadata(String archivOferta) {
        try {
            String path = this.getPathDocumento();
            okm.addGroup(path + archivOferta, "okg:FormatoB");
            List<FormElement> fElements = okm.getPropertyGroupProperties(path + archivOferta, "okg:FormatoB");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoB.docente")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getProfesor());
                }
                if (fElement.getName().equals("okp:FormatoB.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getTitulo());
                }
                if (fElement.getName().equals("okp:FormatoB.Fecha")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getFecha());
                }
                if (fElement.getName().equals("okp:FormatoB.Viabilidad")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getViabilidad());
                }
                if (fElement.getName().equals("okp:FormatoB.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante1());
                }
                if (fElement.getName().equals("okp:FormatoB.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante2());
                }
                if (fElement.getName().equals("okp:FormatoB.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getActaAprobacion());
                }
            }
            okm.setPropertyGroupProperties(this.getPathDocumento() + archivOferta, "okg:FormatoB", fElements);
            exitoSubirArchivo = false;
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("formSeleccionarArchivoFormatoB");
            requestContext.update("formMetadatosFormatoB");
            requestContext.update("formArchivoSelecionadoFormatoB");
            requestContext.execute("PF('dlgRegistroFormatoB').hide()");
            metadatosAnteproyectos = new MetadatosAntepoyecto();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información se registró con éxito"));
            requestContext.getCurrentInstance().update("msgRFA");
        } catch (NoSuchGroupException | LockException | PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | ExtensionException | AutomationException | UnknowException | WebserviceException | IOException | ParseException | NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getOKGPropierties() {
        return "okg:FormatoB";
    }
}
