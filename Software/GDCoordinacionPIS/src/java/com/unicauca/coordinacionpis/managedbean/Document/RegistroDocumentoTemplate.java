/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean.Document;

import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.bean.Document;
import com.openkm.sdk4j.bean.QueryParams;
import com.openkm.sdk4j.bean.QueryResult;
import com.openkm.sdk4j.bean.ResultSet;
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
import com.unicauca.coordinacionpis.classMetadatos.MetadatosAntepoyecto;
import com.unicauca.coordinacionpis.entidades.Usuario;
import com.unicauca.coordinacionpis.entidades.UsuarioPrograma;
import com.unicauca.coordinacionpis.managedbean.RegistroFormatoAController;
import com.unicauca.coordinacionpis.managedbean.RegistroOfertaAcademicaController;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import com.unicauca.coordinacionpis.utilidades.ConexionOpenKM;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author David
 */
public abstract class RegistroDocumentoTemplate {

    String programa;

    private DataModel dataModelDocumentos;

    private ConexionOpenKM conexionOpenKM;
    public OKMWebservices okm;

    @EJB
    private UsuarioFacade ejbUsuario;

    public RegistroDocumentoTemplate() {
        conexionOpenKM = new ConexionOpenKM();
        okm = conexionOpenKM.getOkm();

        dataModelDocumentos = new LazyDataModel<DocumentoMetadatos>() {
            @Override
            public List<DocumentoMetadatos> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

                String ruta = getPathDocumento();

                List<DocumentoMetadatos> listadoDocs = new ArrayList<>();
                listadoDocs.clear();
                try {
                    if (okm.hasNode(ruta)) {
                        QueryParams parametros = new QueryParams();
                        parametros.setPath(ruta);
                       

                        ResultSet result = okm.findPaginated(parametros, first, pageSize);
                        List<QueryResult> lista = result.getResults();
                        setRowCount((int) result.getTotal());
                        for (int i = 0; i < lista.size(); i++) {
                            Document docum = lista.get(i).getDocument();
                            HashMap<String, FormElement> metadata = new HashMap();
                            
                            List<FormElement> propertyGroupProperties = okm.getPropertyGroupProperties(lista.get(i).getDocument().getPath(), getOKGPropierties());
                            for (FormElement propertyGroupProperty : propertyGroupProperties) {
                                metadata.put(propertyGroupProperty.getName(), propertyGroupProperty);
                            }
                            DocumentoMetadatos documento = new DocumentoMetadatos(docum,metadata);
                            listadoDocs.add(documento);
                        }
                    }
                } catch (DatabaseException | UnknowException | WebserviceException | IOException | ParseException ex) {
                    Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RepositoryException ex) {
                    Logger.getLogger(RegistroDocumentoTemplate.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchGroupException ex) {
                    Logger.getLogger(RegistroDocumentoTemplate.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PathNotFoundException ex) {
                    Logger.getLogger(RegistroDocumentoTemplate.class.getName()).log(Level.SEVERE, null, ex);
                }

                return listadoDocs;
            }
        };

    }

    /**
     * Retorna la ruta donde se guardan los documentos .
     *
     * @return
     */
    public abstract String getPathDocumento();

    //Agraga los metadatos al archivo guardado en openKM
    /**
     *
     * @param okm Objeto con la sesion en openKM
     * @param archivOferta Archivo guardado en openKM
     */
    public abstract void addMetadata(String archivOferta);

    public boolean subirDocumento(UploadedFile archivOferta) {
        boolean existeDocumento = false;
        try {
            this.crearRutaDocumento(okm);
            String consecutivo = "";
            String nombreArchivo = archivOferta.getFileName();
            String nombreArchTmp = nombreArchivo.substring(0, nombreArchivo.length() - 4);
            int iter = 0;
            boolean repetido = false;
            while (!repetido) {
                if (!okm.getDocumentChildren(this.getPathDocumento()).isEmpty()) {
                    for (com.openkm.sdk4j.bean.Document doc : okm.getDocumentChildren(this.getPathDocumento())) {
                        if (doc.getPath().equalsIgnoreCase(this.getPathDocumento() + nombreArchTmp + consecutivo + ".pdf")) {//Buscar en openkm si existe el archivo a guardar
                            iter++;
                            consecutivo = "_" + iter;
                            repetido = false;
                            existeDocumento = true;
                            break;
                        }
                        repetido = true;
                    }
                } else {
                    break;
                }
            }
            if (existeDocumento) {
                okm.createDocumentSimple(this.getPathDocumento() + nombreArchTmp + consecutivo + ".pdf", archivOferta.getInputstream());
                this.addMetadata(nombreArchTmp + consecutivo + ".pdf");
            } else {
                okm.createDocumentSimple(this.getPathDocumento() + archivOferta.getFileName(), archivOferta.getInputstream());
                this.addMetadata(archivOferta.getFileName());
            }

        } catch (PathNotFoundException | RepositoryException | DatabaseException | UnknowException | WebserviceException | AccessDeniedException | ItemExistsException | ExtensionException | AutomationException | IOException | UnsupportedMimeTypeException | FileSizeExceededException | UserQuotaExceededException | VirusDetectedException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return existeDocumento;

    }

    /**
     * crea la ruta donde se guardará el formato si es necesario e.g la primera
     * vez que se registra un tipo de formato en un programa
     *
     * @param okm objeto con la sesion en openKM
     */
    public void crearRutaDocumento(OKMWebservices okm) throws RepositoryException, AccessDeniedException, DatabaseException, UnknowException, WebserviceException, PathNotFoundException, ItemExistsException, ExtensionException, AutomationException {

        String ruta = this.getPathDocumento();

        if (okm.hasNode(ruta)) {
            return;
        }

        String[] carpetas = ruta.split("/");
        String home = carpetas[0] + "/" + carpetas[1];
        String carpeta = home;
        for (int i = 2; i < carpetas.length; i++) {
            carpeta += "/" + carpetas[i];
            if (!okm.hasNode(carpeta)) {
                okm.createFolderSimple(carpeta);
            }
        }
    }

    public String getPrgramaUsuario() {

        if (this.programa == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
            Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
            UsuarioPrograma usuarioPrograma = usuario.getUsuarioProgramaList().get(0); ///VERIFICAR SI ES SOLO UNO TODO TO DO
            String nombrePrograma = usuarioPrograma.getPrograma().getNombrePrograma();

            this.programa = nombrePrograma;

            return nombrePrograma;

        } else {

            return programa;
        }

    }

    public DataModel getDataModelDocumentos() {
        return dataModelDocumentos;
    }

    public void setDataModelDocumentos(DataModel dataModelDocumentos) {
        this.dataModelDocumentos = dataModelDocumentos;
        Document d;

    }

    /**
     * Debe retornar el nombre de el OKG grouppropierties del documento
     *
     * @return
     */
    public abstract String getOKGPropierties();

}
