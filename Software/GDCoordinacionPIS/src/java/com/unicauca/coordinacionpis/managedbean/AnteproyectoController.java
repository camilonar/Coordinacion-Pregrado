/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Anteproyecto;
import com.unicauca.coordinacionpis.entidades.Estudiante;
import com.unicauca.coordinacionpis.entidades.Profesor;
import com.unicauca.coordinacionpis.entidades.Programa;
import com.unicauca.coordinacionpis.entidades.Usuario;
import com.unicauca.coordinacionpis.entidades.UsuarioPrograma;
import com.unicauca.coordinacionpis.sessionbean.AnteproyectoFacade;
import com.unicauca.coordinacionpis.sessionbean.EstudianteFacade;
import com.unicauca.coordinacionpis.sessionbean.ProfesorFacade;
import com.unicauca.coordinacionpis.sessionbean.ProgramaFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author David
 */
@ManagedBean
@ViewScoped
/**
 * Controlador de las vistas: anteproyecto, EditarAnteproyecto, Ver, create y verAnteproyecto.
 * También es el encargado de la gestión de un usuario: crear, editar, cambiar
 * rol, desactivar. 
 */
public class AnteproyectoController implements Serializable {

    /**
     * Anteproyecto seleccionado en GUI
     */
    private Anteproyecto anteproyectoSelected;
    /**
     * EStudiante que se está ingresando en GUI al anteproyecto
     */
    private Estudiante estudianteSelected;
    /*
     *Director asociado al anteproyecto 
     */
    private Profesor directorSelected;
    /**
     * EStudiantes del anteproyecto
     */
    private List<Estudiante> estudiantes;
    /**
     * Listado de los anteproyectos de la app
     */
    private List<Anteproyecto> anteproyectos;
    /**
     * Valor de la busqueda sobre anteproyecto
     */
    private String datoBusqueda;

    LazyDataModel<Anteproyecto> dataModelAnteproyecto = new LazyDataModel<Anteproyecto>() {
        @Override
        public List<Anteproyecto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

            Programa programa = getPrgramaUsuario();
            setRowCount(ejbAnteproyecto.contarbuscarProyecto(datoBusqueda.toLowerCase(), programa));

            List<Anteproyecto> buscarProyecto = ejbAnteproyecto.buscarProyecto(datoBusqueda.toLowerCase(), programa, pageSize, first);

            return buscarProyecto;

        }
    };

    @EJB
    private UsuarioFacade ejbUsuario;
    @EJB
    private AnteproyectoFacade ejbAnteproyecto;
    @EJB
    private EstudianteFacade ejbEstudiante;
    @EJB
    private ProfesorFacade ejbProfesor;
    @EJB
    private ProgramaFacade ejbPrograma;

    /**
     * Carga toda la información de un anteproyecto según un identificador
     */
    public void cargarAnteproyecto() {
        //verificar si puede cargar estoo para un usuario determinado ...s....        
        //System.out.println("---:" + this.anteproyectoSelected);
        if (this.anteproyectoSelected.getIdAnteproyecto() == null) {
            return;
        }
        Anteproyecto ant = this.ejbAnteproyecto.find(this.anteproyectoSelected.getIdAnteproyecto());
        if (ant == null) {

            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encuentra el Anteproyecto");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            requestContext.update("msg");

            return;
        }

        if (!Objects.equals(ant.getProgramaAnteproyecto().getIdPrograma(), this.getPrgramaUsuario().getIdPrograma())) {
            //TODO: NO PUEDE MODIFICARLO POR QUE EL ANTEPROYECTO NO ES DE SU PROGRAMA, 
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No tiene Acceso a este Antreproyecto");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            requestContext.update("msg");
            return;
        }

        this.anteproyectoSelected = ant;
        this.estudiantes = this.anteproyectoSelected.getEstudianteList();
        this.directorSelected = this.anteproyectoSelected.getDirectorAnteproyecto();

    }

    public AnteproyectoController() {

        datoBusqueda = "";
        estudianteSelected = new Estudiante();
        this.estudiantes = new ArrayList<>();
        this.anteproyectoSelected = new Anteproyecto();
        this.directorSelected = new Profesor();

    }

    public void buscarAnteproyectos() {
        anteproyectos = ejbAnteproyecto.buscarProyecto(datoBusqueda.toLowerCase(), getPrgramaUsuario(), null, null);

    }

    public Date obtenerFechaActual() {
        return new Date();
    }

    /**
     * Añade un estudiante a la lista de estudiantes del anteproyecto
     * seleccionado
     */
    public void addToListEstudiantes() {
        if (this.estudiantes.size() < 2 && !this.estudiantes.contains(estudianteSelected)) {
            this.estudiantes.add(estudianteSelected);
            estudianteSelected = new Estudiante();
            // System.out.println("agregado");
        } else {
           
            RequestContext requestContext = RequestContext.getCurrentInstance();
            if (this.estudiantes.size() >= 2) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Solo es posible agregar 2 estudiantes por anteproyecto.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                requestContext.update("msg");

            } else {

                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "El estudiante ya está agregado a este anteproyecto.");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                requestContext.update("msg");

            }

            requestContext.update("formEstudiantes");

        }
    }

    /**
     * Remueve un estudiante del anteporyecto
     *
     * @param e
     */
    public void removerEstListEstudiantes(Estudiante e) {

        if (this.estudiantes.remove(e)) {
            //System.out.println("Estudiante eliminador " + e.getNombreEstudiante());
        }

    }

    /**
     * Busca si el estudiante está registrado y lo autocompleta en la GUI
     */
    public void autocompletarEstudiante() {
        Estudiante completo = this.ejbEstudiante.findByCodigo(estudianteSelected.getCodigoEstudiante());
        if (completo != null) {
            this.estudianteSelected = completo;

        }

    }

    /**
     * Busca si el profesor ya está registrado y lo autocompleta en la GUI
     */
    public void autocompletarDirector() {

        //System.out.println("a:" + directorSelected.getIdProfesor() + "," + directorSelected.getNombreProfesor());
        Profesor completo = this.ejbProfesor.findByCodigo(directorSelected.getCodigoProfesor());
        if (completo != null) {

            this.directorSelected = completo;
            //System.out.println("si hizo el cambio");
        }

        //  System.out.println("d:" + directorSelected.getIdProfesor() + "," + directorSelected.getNombreProfesor());
    }

    /**
     * REgistra un anteproyecto
     */
    public void registrarAnteproyecto() {

        Programa prgramaUsuario = getPrgramaUsuario();

        for (Estudiante estudiante : estudiantes) {

            if (this.ejbEstudiante.findByCodigo(estudiante.getCodigoEstudiante()) == null) {
                estudiante.setProgramaEstudiante(prgramaUsuario);
                this.ejbEstudiante.create(estudiante);
            }

        }

        Profesor director = this.ejbProfesor.findByCodigo(directorSelected.getCodigoProfesor());
        if (director == null) {
            // registrar profesor nuevo
            this.directorSelected.setProgramaProfesor(prgramaUsuario);
            this.ejbProfesor.create(this.directorSelected);
            director = this.directorSelected;

        }

        anteproyectoSelected.setDirectorAnteproyecto(director);
        anteproyectoSelected.setProgramaAnteproyecto(prgramaUsuario);
        //anteproyectoSelected.setEstudianteList(estudiantes);
        this.ejbAnteproyecto.create(anteproyectoSelected);
        this.anteproyectoSelected.setEstudianteList(estudiantes);
        this.ejbAnteproyecto.edit(anteproyectoSelected);
        //  System.out.println("E completo");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Anteproyecto creado con exito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        requestContext.update("msg");
    }

    /**
     * Cambia los datos de un anteproyecto
     */
    public void editarAnteproyecto() {

        Programa prgramaUsuario = getPrgramaUsuario();

        for (Estudiante estudiante : estudiantes) {

            if (this.ejbEstudiante.findByCodigo(estudiante.getCodigoEstudiante()) == null) {
                estudiante.setProgramaEstudiante(prgramaUsuario);
                this.ejbEstudiante.create(estudiante);
            }

        }

        Profesor director = this.ejbProfesor.find(directorSelected.getIdProfesor());
        if (director == null) {
            // registrar profesor nuevo
            this.directorSelected.setProgramaProfesor(prgramaUsuario);
            this.ejbProfesor.create(this.directorSelected);
            director = this.directorSelected;

        }

        anteproyectoSelected.setDirectorAnteproyecto(director);
        anteproyectoSelected.setProgramaAnteproyecto(prgramaUsuario);
        anteproyectoSelected.setEstudianteList(estudiantes);
        this.ejbAnteproyecto.edit(anteproyectoSelected);
        //System.out.println("E completo");

        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Anteproyecto editado con exito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        requestContext.update("msg");

    }

    

    /**
     * Carga un anteporyecto para edición
     */
    public void cargarDatosEdicion() {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMetadatosEditAnteproyecto");
        requestContext.execute("PF('dlgEditarAnteproyecto').show()");
        // System.out.println("mande a ejecutar");
    }

    public Programa getPrgramaUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
        UsuarioPrograma usuarioPrograma = usuario.getUsuarioProgramaList().get(0); ///VERIFICAR SI ES SOLO UNO TODO TO DO
        return usuarioPrograma.getPrograma();

    }

    public LazyDataModel<Anteproyecto> getDataModelAnteproyecto() {
        return dataModelAnteproyecto;
    }

    public void setDataModelAnteproyecto(LazyDataModel<Anteproyecto> dataModelAnteproyecto) {
        this.dataModelAnteproyecto = dataModelAnteproyecto;
    }
    
    public String getDatoBusqueda() {
        return datoBusqueda;
    }

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public Estudiante getEstudianteSelected() {
        return estudianteSelected;
    }
    public void setEstudianteSelected(Estudiante estudianteSelected) {
        this.estudianteSelected = estudianteSelected;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public Anteproyecto getAnteproyectoSelected() {
        return anteproyectoSelected;
    }

    public void setAnteproyectoSelected(Anteproyecto anteproyectoSelected) {
        this.anteproyectoSelected = anteproyectoSelected;
    }

    public Profesor getDirectorSelected() {
        return directorSelected;
    }

    public void setDirectorSelected(Profesor directorSelected) {
        this.directorSelected = directorSelected;
    }

    public List<Anteproyecto> getAnteproyectos() {
        ejbAnteproyecto.limpiarCache();
        buscarAnteproyectos();
        return anteproyectos;
    }

    public void setAnteproyectos(List<Anteproyecto> anteproyectos) {
        this.anteproyectos = anteproyectos;
    }
    public void setAnteproyecto(Anteproyecto a) {
        this.anteproyectoSelected = a;
        this.estudiantes = a.getEstudianteList();
    }
}
