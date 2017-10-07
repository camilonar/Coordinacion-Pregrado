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
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
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
import com.unicauca.coordinacionpis.validadores.ValidarEdicionUsuarios;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 *
 * @author ROED26
 */
@ManagedBean
@ViewScoped
public class RegistroFormatoAController implements Serializable {

    //TEMPORALLLL
    String programaTemporal = "Sistemas";
    ///

    private MetadatosAntepoyecto metadatosAnteproyectos;
    private boolean exitoSubirArchivo;
    private String nombreArchivo;
    private UploadedFile archivOferta;
    private StreamedContent streamedContent;
    private String datos;
    private List<com.openkm.sdk4j.bean.Document> listadoDocsAnteproecto;
    private com.openkm.sdk4j.bean.Document documento;
    String url = "http://localhost:8083/OpenKM";
    String user = "okmAdmin";
    String pass = "admin";
    OKMWebservices okm = OKMWebservicesFactory.newInstance(url, user, pass);
    private SimpleDateFormat formatoFecha;

    public RegistroFormatoAController() {
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        metadatosAnteproyectos.setViabilidad("Si");
        listadoDocsAnteproecto = new ArrayList<>();
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

    public List<com.openkm.sdk4j.bean.Document> getListadoAnteproecto() throws PathNotFoundException, RepositoryException {
        listadoDocsAnteproecto.clear();
        try {
            QueryParams parametros = new QueryParams();
            parametros.setPath(this.getPathFormatoA());
            parametros.setName(datos);
            List<QueryResult> lista = okm.find(parametros);
            for (int i = 0; i < lista.size(); i++) {
                listadoDocsAnteproecto.add(lista.get(i).getDocument());

            }

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
        return listadoDocsAnteproecto;
    }

    public Date getTodayDate() {
        return new Date();
    }

    public void seleccionarArchivo(FileUploadEvent event) {
        nombreArchivo = event.getFile().getFileName();
        archivOferta = event.getFile();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + event.getFile().getFileName() + "' se selccionó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("messages");
        exitoSubirArchivo = true;
        requestContext.update("formSeleccionarArchivoFormatoA");
        requestContext.update("formMetadatosFormatoA");
        requestContext.update("formArchivoSelecionadoFormatoA");
    }

    public void cambiarArchivo() {
        exitoSubirArchivo = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoA");
        requestContext.update("formMetadatosFormatoA");
        requestContext.update("formArchivoSelecionadoFormatoA");
    }

    public void cancelarFormatoA() {
        exitoSubirArchivo = false;
        nombreArchivo = "";
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoA");
        requestContext.update("formMetadatosFormatoA");
        requestContext.execute("PF('dlgRegistroFormatoA').hide()");
        requestContext.update("formArchivoSelecionadoFormatoA");
    }

    public void aceptarFormatoA() throws PathNotFoundException {


        System.out.println("viabilidad:" + metadatosAnteproyectos.getViabilidad());

        try {

            if (!okm.hasNode("/okm:root/Coordinacion")) {
                okm.createFolderSimple("/okm:root/Coordinacion");
            }
            if (!okm.hasNode("/okm:root/Coordinacion/Anteproyectos")) {
                okm.createFolderSimple("/okm:root/Coordinacion/Anteproyectos");
            }
            if (!okm.hasNode("/okm:root/Coordinacion/Anteproyectos/" + programaTemporal)) {
                okm.createFolderSimple("/okm:root/Coordinacion/Anteproyectos/" + programaTemporal);
            }
            if (!okm.hasNode("/okm:root/Coordinacion/Anteproyectos/" + programaTemporal + "/FormatoA")) {
                okm.createFolderSimple("/okm:root/Coordinacion/Anteproyectos/" + programaTemporal + "/FormatoA");
            }
            
            okm.createDocumentSimple(this.getPathFormatoA() + archivOferta.getFileName(), archivOferta.getInputstream());
            okm.addGroup(this.getPathFormatoA() + archivOferta.getFileName(), "okg:FormatoA");

            List<FormElement> fElements = okm.getPropertyGroupProperties(this.getPathFormatoA() + archivOferta.getFileName(), "okg:FormatoA");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoA.docente")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getProfesor());
                }
                if (fElement.getName().equals("okp:FormatoA.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getTitulo());
                }
                if (fElement.getName().equals("okp:FormatoA.Fecha")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getFecha());
                }

                if (fElement.getName().equals("okp:FormatoA.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante1());
                }
                if (fElement.getName().equals("okp:FormatoA.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante2());
                }
                if (fElement.getName().equals("okp:FormatoA.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getActaAprobacion());
                }
            }
            okm.setPropertyGroupProperties(this.getPathFormatoA()+ archivOferta.getFileName(), "okg:FormatoA", fElements);
        } catch (PathNotFoundException | RepositoryException | DatabaseException | UnknowException | WebserviceException | AccessDeniedException | ItemExistsException | ExtensionException | AutomationException | IOException | UnsupportedMimeTypeException | FileSizeExceededException | UserQuotaExceededException | VirusDetectedException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchGroupException | LockException | ParseException | NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }
        agregarMetadatos();
        exitoSubirArchivo = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoA");
        requestContext.update("formMetadatosFormatoA");
        requestContext.update("formArchivoSelecionadoFormatoA");
        requestContext.execute("PF('dlgRegistroFormatoA').hide()");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información se registró con éxito"));
        requestContext.getCurrentInstance().update("msgRFA");

    }

    public void actualizarInfoFormatoA() {

        try {
            okm.addGroup(documento.getPath(), "okg:FormatoA");
            List<FormElement> fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:FormatoA");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoA.docente")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getProfesor());
                }
                if (fElement.getName().equals("okp:FormatoA.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getTitulo());
                }
                if (fElement.getName().equals("okp:FormatoA.Fecha")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getFecha());
                }

                if (fElement.getName().equals("okp:FormatoA.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante1());
                }
                if (fElement.getName().equals("okp:FormatoA.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante2());
                }
                if (fElement.getName().equals("okp:FormatoA.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getActaAprobacion());
                }
            }
            okm.setPropertyGroupProperties(documento.getPath(), "okg:FormatoA", fElements);
        } catch (NoSuchGroupException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LockException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathNotFoundException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AccessDeniedException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExtensionException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (AutomationException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknowException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebserviceException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("formMetadatosEditFormatoA");
        requestContext.execute("PF('dlgEditarFormatoA').hide()");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información se editó con éxito"));
        requestContext.getCurrentInstance().update("msgRFA");
    }

    public void cargarDatosEdicion(com.openkm.sdk4j.bean.Document documento) {
        this.documento = documento;
        List<FormElement> fElements;
        try {
            fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:FormatoA");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoA.docente")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setProfesor(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoA.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setTitulo(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoA.Fecha")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setFecha(name.getValue());
                }

                if (fElement.getName().equals("okp:FormatoA.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setNombreEstudiante1(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoA.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setNombreEstudiante2(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoA.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setActaAprobacion(name.getValue());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchGroupException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathNotFoundException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RepositoryException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DatabaseException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknowException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WebserviceException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("formMetadatosEditFormatoA");
        requestContext.execute("PF('dlgEditarFormatoA').show()");

    }

    public void agregarMetadatos() {
        // create document and writer
        Document document = new Document(PageSize.A4);
        PdfWriter writer;
        String ruta = ResourceBundle.getBundle("/BundleOpenKm").getString("Ruta");
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(ruta + "aguaabril2016.pdf"));
            // add meta-data to pdf
            document.addAuthor("Memorynotfound");
            document.addCreationDate();
            document.addCreator("Memorynotfound.com");
            document.addTitle("Add meta data to PDF");
            document.addSubject("how to add meta data to pdf using itext");
            document.addKeywords(metadatosAnteproyectos.getTitulo() + "," + metadatosAnteproyectos.getProfesor());
            document.addLanguage(Locale.ENGLISH.getLanguage());
            document.addHeader("type", "tutorial, example");

            // add xmp meta data
            writer.createXmpMetadata();

            document.open();
            document.add(new Paragraph("Add meta-data to PDF using iText"));
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            //-------
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

            // requestContext.execute("PF('mensajeRegistroExitoso').show()");
            requestContext.update("msg");

            requestContext.update("formListaAnteproyectos");
        } catch (Exception e) {

        }
    }

    public void cancelarEditar() {
        System.out.println("incas");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEditarFormatoA').hide()");

    }

    public void cancelarEdicion() {
        System.out.println("incas");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEditarFormatoA').hide()");
        requestContext.execute("PF('dlgRegistroFormatoA').hide()");

    }

    public void cancelarRegistro() {
        System.out.println("invocado apá");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoA");
        requestContext.update("formMetadatosFormatoA");
        requestContext.update("formArchivoSelecionadoFormatoA");
        requestContext.execute("PF('dlgRegistroFormatoA').hide()");
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
    
    
    /**
     * Devuelve la ruta del formatoA para el coordinador que inicio sesion
     * @return la ruta donde se encuantra la carpeta de FormatoA para el coordinador especifico de cada 
     * programa (El que inicio sesion)
     */
    public String getPathFormatoA(){
       return "/okm:root/Coordinacion/Anteproyectos/" + programaTemporal + "/FormatoA/";
    }

}
