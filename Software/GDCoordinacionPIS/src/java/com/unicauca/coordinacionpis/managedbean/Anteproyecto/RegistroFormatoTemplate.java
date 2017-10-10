/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean.Anteproyecto;

import com.openkm.sdk4j.OKMWebservices;
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
import com.unicauca.coordinacionpis.classMetadatos.MetadatosAntepoyecto;
import com.unicauca.coordinacionpis.entidades.Usuario;
import com.unicauca.coordinacionpis.entidades.UsuarioPrograma;
import com.unicauca.coordinacionpis.managedbean.RegistroFormatoAController;
import com.unicauca.coordinacionpis.managedbean.RegistroOfertaAcademicaController;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author David
 */
public abstract class RegistroFormatoTemplate {

    String programaFormato;

    @EJB
    private UsuarioFacade ejbUsuario;

    /**
     * Retorna la ruta donde se guardará el formato esta ruta debe incluir la
     * ruta de inicio e.j "/okm:root/" donde root es el usuario.
     *
     * @return
     */
    public abstract String getPathFormato();

    //Agraga los metadatos al archivo guardado en openKM
    /**
     *
     * @param okm Objeto con la sesion en openKM
     * @param archivOferta Archivo guardado en openKM
     */
    public abstract void addMetadata(OKMWebservices okm, UploadedFile archivOferta);

    public void subirFormato(OKMWebservices okm, UploadedFile archivOferta) {

        try {
            this.crearRutaFormato(okm);
            okm.createDocumentSimple(this.getPathFormato() + archivOferta.getFileName(), archivOferta.getInputstream());

            this.addMetadata(okm, archivOferta);
        } catch (PathNotFoundException | RepositoryException | DatabaseException | UnknowException | WebserviceException | AccessDeniedException | ItemExistsException | ExtensionException | AutomationException | IOException | UnsupportedMimeTypeException | FileSizeExceededException | UserQuotaExceededException | VirusDetectedException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * crea la ruta donde se guardará el formato si es necesario e.g la primera
     * vez que se registra un tipo de formato en un programa
     *
     * @param okm objeto con la sesion en openKM
     */
    public void crearRutaFormato(OKMWebservices okm) throws RepositoryException, AccessDeniedException, DatabaseException, UnknowException, WebserviceException, PathNotFoundException, ItemExistsException, ExtensionException, AutomationException {

        String ruta = this.getPathFormato();

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

    //LISTAR D EMANERA GENERIA CON PAGINACINOO 
    public List<com.openkm.sdk4j.bean.Document> getListaFormatos(OKMWebservices okm, String name) throws PathNotFoundException, RepositoryException {

        String ruta = this.getPathFormato();
        List<com.openkm.sdk4j.bean.Document> listadoDocsFormatos = new ArrayList<>();
        listadoDocsFormatos.clear();
        try {
            if (okm.hasNode(ruta)) {
                QueryParams parametros = new QueryParams();
                parametros.setPath(ruta);
                parametros.setName(name);
                List<QueryResult> lista = okm.find(parametros);
                for (int i = 0; i < lista.size(); i++) {
                    listadoDocsFormatos.add(lista.get(i).getDocument());

                }
            }

        } catch (DatabaseException | UnknowException | WebserviceException | IOException | ParseException ex) {
            Logger.getLogger(RegistroOfertaAcademicaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoDocsFormatos;
    }

    public String getPrgramaUsuario() {

        if (this.programaFormato == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
            Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
            UsuarioPrograma usuarioPrograma = usuario.getUsuarioProgramaList().get(0); ///VERIFICAR SI ES SOLO UNO TODO TO DO
            String nombrePrograma = usuarioPrograma.getPrograma().getNombrePrograma();
            
            this.programaFormato = nombrePrograma;
            
            return nombrePrograma;
            
        } else {
           
            return programaFormato;
        }

    }

}
