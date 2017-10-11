/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;
import com.openkm.sdk4j.bean.Folder;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Zuñiga
 */
@ManagedBean
@ViewScoped
public class FormatoCController implements Serializable{
    private MetadatosAntepoyecto metadatosAnteproyectos;
    private boolean exitoSubirArchivo;
    private String nombreArchivo;
    private UploadedFile archivoFormatoC;
    private StreamedContent streamedContent;
    private String datos;
    private List<com.openkm.sdk4j.bean.Document> listadoDocsFormatoC;
    private com.openkm.sdk4j.bean.Document documento;
    String url = "http://localhost:8083/OpenKM";
    String user = "okmAdmin";
    String pass = "admin";
    OKMWebservices okm = OKMWebservicesFactory.newInstance(url, user, pass);
    private SimpleDateFormat formatoFecha;

    public FormatoCController() {
        this.formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        metadatosAnteproyectos.setViabilidad("Si");
        listadoDocsFormatoC = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        metadatosAnteproyectos.setViabilidad("Si");
        try 
        {
            InputStream in = okm.getContent(documento.getPath());
            streamedContent = new DefaultStreamedContent(in, "application/pdf");
            Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            byte[] b = (byte[]) session.get("reportBytes");
            if (b != null) 
            {
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(b), "application/pdf");
            }
        } catch (Exception e) {}
    }
    
    public void seleccionarArchivo(FileUploadEvent event) {
        nombreArchivo = event.getFile().getFileName();
        archivoFormatoC = event.getFile();
        System.out.println("archivo c:"+ archivoFormatoC.getFileName());
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo '" + event.getFile().getFileName() + "' se selccionó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, message);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("messages");
        exitoSubirArchivo = true;
        requestContext.update("formSeleccionarArchivoFormatoC");
        requestContext.update("formMetadatosFormatoC");
        requestContext.update("formArchivoSelecionadoFormatoC");
    }
    
    public List<com.openkm.sdk4j.bean.Document> getListadoFormatoC() {
        listadoDocsFormatoC.clear();
        try {
            List<QueryResult> lista = okm.findByName(datos);
            for (int i = 0; i < lista.size(); i++) 
            {
                String[] pathDividido = lista.get(i).getDocument().getPath().split("/");
                String path = "/" + pathDividido[1] + "/" + pathDividido[2] + "/" + pathDividido[3];
                if (path.equalsIgnoreCase("/okm:root/Coordinacion/FormatoC")) {
                    listadoDocsFormatoC.add(lista.get(i).getDocument());
                }
            }
        } catch (RepositoryException | DatabaseException | UnknowException | WebserviceException | IOException | ParseException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoDocsFormatoC;
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
    
    public Date getTodayDate() {
        return new Date();
    }
    
    public String nombreDelArchivo(String path) {

        String partesPath[] = path.split("/");
        return partesPath[partesPath.length - 1];
    }

    public String fecha(Calendar fecha) {
        return formatoFecha.format(fecha.getTime());
    }
    
    public StreamedContent descargarDocumento(com.openkm.sdk4j.bean.Document queryResult) 
    {
        StreamedContent file = null;
        com.openkm.sdk4j.bean.Document doc = queryResult;
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
    
    public void visualizarDocumento(com.openkm.sdk4j.bean.Document documento) 
    {
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
   
    public void cargarDatosEdicion(com.openkm.sdk4j.bean.Document documento) 
    {
        this.documento = documento;
        List<FormElement> fElements;
        try {
            fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:FormatoC");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoC.docente")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setProfesor(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoC.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setTitulo(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoC.Fecha")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setFecha(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoC.Viabilidad")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setViabilidad(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoC.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setNombreEstudiante1(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoC.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setNombreEstudiante2(name.getValue());
                }
                if (fElement.getName().equals("okp:FormatoC.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    this.metadatosAnteproyectos.setActaAprobacion(name.getValue());
                }
            }
        } catch (IOException | ParseException | NoSuchGroupException | PathNotFoundException | RepositoryException | DatabaseException | UnknowException | WebserviceException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("formMetadatosEditFormatoC");
        requestContext.execute("PF('dlgEditarFormatoC').show()");

    }
    
    public void confirmarEliminacion(com.openkm.sdk4j.bean.Document documento) 
    {

        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "¿Está seguro que desea eliminar el documento?"));
        context.execute("PF('Confirmacion').show()");
        this.documento = documento;

    }

    public void deleteDocument() 
    {
        try {
            okm.deleteDocument(documento.getPath());
            okm.purgeTrash();
            RequestContext requestContext = RequestContext.getCurrentInstance();

            requestContext.execute("PF('Confirmacion').hide()");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El archivo se eliminó con éxito");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            // requestContext.execute("PF('mensajeRegistroExitoso').show()");
            requestContext.update("msgFormatoC");
            requestContext.update("formListaFormatoC");
        } catch (Exception e) {

        }
    }
    
    public boolean getComprobarConexionOpenKM() 
    {
        boolean conexion = true;
        try {
            okm.getAppVersion();

        } catch (RepositoryException | DatabaseException | UnknowException | WebserviceException ex) {
            conexion = false;
        }
        return conexion;
    }
    
    public List<Docente> getListaDocentes() 
    {
        List<Docente> listaDocentes = new ArrayList<>();
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
    
    public void cambiarArchivo() {
        exitoSubirArchivo = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("dlgRegistroFormatoC");
        requestContext.update("formSeleccionarArchivoFormatoC");
        requestContext.update("formMetadatosFormatoC");
        requestContext.update("formArchivoSelecionadoFormatoC");
    }
    
    public void cancelarFormatoC() {
        exitoSubirArchivo = false;
        nombreArchivo = "";
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoC");
        requestContext.update("formMetadatosFormatoC");
        requestContext.execute("PF('dlgRegistroFormatoC').hide()");
        requestContext.update("formArchivoSelecionadoFormatoC");
    }
    
    public void aceptarFormatoC() {

        boolean existe = false;
        boolean existeFolderCoordinacion = false;
       
        try {

            for (Folder fld : okm.getFolderChildren("/okm:root")) {
                if (fld.getPath().equalsIgnoreCase("/okm:root/Coordinacion")) {

                    existeFolderCoordinacion = true;
                }
            }
            if (existeFolderCoordinacion) {
                for (Folder fld : okm.getFolderChildren("/okm:root/Coordinacion")) {
                    if (fld.getPath().equalsIgnoreCase("/okm:root/Coordinacion/FormatoC")) {
                        existe = true;
                    }
                }
            } else {
                okm.createFolderSimple("/okm:root/Coordinacion");
            }

            if (!existe) {
                okm.createFolderSimple("/okm:root/Coordinacion/FormatoC");
                okm.createDocumentSimple("/okm:root/Coordinacion/FormatoC/" + archivoFormatoC.getFileName(), archivoFormatoC.getInputstream());
            } else {
                okm.createDocumentSimple("/okm:root/Coordinacion/FormatoC/" + archivoFormatoC.getFileName(), archivoFormatoC.getInputstream());
            }
            okm.addGroup("/okm:root/Coordinacion/FormatoC/" + archivoFormatoC.getFileName(), "okg:FormatoC");

            List<FormElement> fElements = okm.getPropertyGroupProperties("/okm:root/Coordinacion/FormatoC/" + archivoFormatoC.getFileName(), "okg:FormatoC");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoC.docente")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getProfesor());
                }
                if (fElement.getName().equals("okp:FormatoC.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getTitulo());
                }
                if (fElement.getName().equals("okp:FormatoC.Fecha")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getFecha());
                }
                if (fElement.getName().equals("okp:FormatoC.Viabilidad")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getViabilidad());
                }
                if (fElement.getName().equals("okp:FormatoC.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante1());
                }
                if (fElement.getName().equals("okp:FormatoC.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante2());
                }
                if (fElement.getName().equals("okp:FormatoC.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getActaAprobacion());
                }
            }
            okm.setPropertyGroupProperties("/okm:root/Coordinacion/FormatoC/" + archivoFormatoC.getFileName(), "okg:FormatoC", fElements);

        } catch (PathNotFoundException | RepositoryException | DatabaseException | UnknowException | WebserviceException | AccessDeniedException | ItemExistsException | ExtensionException | AutomationException | IOException | UnsupportedMimeTypeException | FileSizeExceededException | UserQuotaExceededException | VirusDetectedException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchGroupException | LockException | ParseException | NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }
        agregarMetadatos();
        exitoSubirArchivo = false;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoC");
        requestContext.update("formMetadatosFormatoC");
        requestContext.update("formArchivoSelecionadoFormatoC");
        requestContext.execute("PF('dlgRegistroFormatoC').hide()");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información se registró con éxito"));
        requestContext.getCurrentInstance().update("msgRFC");

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
    
    public void actualizarInfoFormatoC() {

        try {
            okm.addGroup(documento.getPath(), "okg:FormatoC");
            List<FormElement> fElements = okm.getPropertyGroupProperties(documento.getPath(), "okg:FormatoC");
            for (FormElement fElement : fElements) {
                if (fElement.getName().equals("okp:FormatoC.docente")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getProfesor());
                }
                if (fElement.getName().equals("okp:FormatoC.TituloAnteproyecto")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getTitulo());
                }
                if (fElement.getName().equals("okp:FormatoC.Fecha")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getFecha());
                }
                if (fElement.getName().equals("okp:FormatoC.Viabilidad")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getViabilidad());
                }
                if (fElement.getName().equals("okp:FormatoC.PrimerEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante1());
                }
                if (fElement.getName().equals("okp:FormatoC.SegundoEstudiante")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getNombreEstudiante2());
                }
                if (fElement.getName().equals("okp:FormatoC.ActaAprobacion")) {
                    Input name = (Input) fElement;
                    name.setValue(this.metadatosAnteproyectos.getActaAprobacion());
                }
            }
            okm.setPropertyGroupProperties(documento.getPath(), "okg:FormatoC", fElements);
        } catch (NoSuchGroupException | LockException | PathNotFoundException | AccessDeniedException | RepositoryException | DatabaseException | ExtensionException | AutomationException | UnknowException | WebserviceException | IOException | ParseException | NoSuchPropertyException ex) {
            Logger.getLogger(RegistroFormatoAController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestContext requestContext = RequestContext.getCurrentInstance();

        requestContext.update("formMetadatosEditFormatoC");
        requestContext.execute("PF('dlgEditarFormatoC').hide()");
        metadatosAnteproyectos = new MetadatosAntepoyecto();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información se editó con éxito"));
        requestContext.getCurrentInstance().update("msgRFC");
    }
    
    public void cancelarEditar() {
        
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEditarFormatoC').hide()");                
    }
     
    public void cancelarEdicion() {
        System.out.println("SI ENTRA DANIEL");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('dlgEditarFormatoC').hide()");
        requestContext.execute("PF('dlgRegistroFormatoC').hide()");
        requestContext.execute("$('#formMetadatosFormatoC').trigger('reset')");
    }
    
    public void cancelarRegistro() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formSeleccionarArchivoFormatoC");
        requestContext.update("formMetadatosFormatoC");
        requestContext.update("formArchivoSelecionadoFormatoC");
        requestContext.execute("PF('dlgRegistroFormatoC').hide()");
    }
}