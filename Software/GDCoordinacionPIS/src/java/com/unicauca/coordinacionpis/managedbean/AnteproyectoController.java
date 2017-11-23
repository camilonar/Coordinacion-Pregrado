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
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author David
 */
@ManagedBean
@ViewScoped
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
        Anteproyecto ant = this.ejbAnteproyecto.find(this.anteproyectoSelected.getIdAnteproyecto());
        if (ant != null) {
            this.anteproyectoSelected = ant;
            this.estudiantes = this.anteproyectoSelected.getEstudianteList();
            this.directorSelected = this.anteproyectoSelected.getDirectorAnteproyecto();
        }
    }

    public AnteproyectoController() {
        datoBusqueda = "";
        estudianteSelected = new Estudiante();
        this.estudiantes = new ArrayList<>();
        this.anteproyectoSelected = new Anteproyecto();
        this.directorSelected = new Profesor();
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

    public void buscarAnteproyectos() {
        anteproyectos = ejbAnteproyecto.buscarProyecto(datoBusqueda.toLowerCase());
    }

    public Date obtenerFechaActual()
    {
        return new Date();
    }
    /**
     * Añade un estudiante a la lista de estudiantes 
     * del anteproyecto seleccionado
     */
    public void addToListEstudiantes() {
        if (this.estudiantes.size() < 2 && !this.estudiantes.contains(estudianteSelected)) {
            this.estudiantes.add(estudianteSelected);
            estudianteSelected = new Estudiante();
        } else {

            RequestContext requestContext = RequestContext.getCurrentInstance();
            if (this.estudiantes.size() >=2) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Solo es posible agregar 2 estudiantes por anteproyecto.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                requestContext.update("msgEstudiantes");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El estudiante ya está agregado a este anteproyecto.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                requestContext.update("msgEstudiantes");

            }

            requestContext.update("formEstudiantes");

        }
    }
    /**
     * Remueve un estudiante del anteporyecto
     * @param e 
     */
    public void removeToListEstudiantes(Estudiante e) {
        this.estudiantes.remove(e);
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

        System.out.println("a:" + directorSelected.getIdProfesor() + "," + directorSelected.getNombreProfesor());

        Profesor completo = this.ejbProfesor.findByCodigo(directorSelected.getCodigoProfesor());
        if (completo != null) {

            this.directorSelected = completo;
            System.out.println("si hizo el cambio");
        }

        System.out.println("d:" + directorSelected.getIdProfesor() + "," + directorSelected.getNombreProfesor());
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
        System.out.println("E completo");
    }
    /**
     * Cambia los datos de un anteproyecto
     */
    public void editarAnteproyecto() {

        Programa prgramaUsuario = getPrgramaUsuario();

        for (Estudiante estudiante : estudiantes) {

            if (this.ejbEstudiante.find(estudiante.getIdEstudiante()) == null) {
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
        System.out.println("E completo");
    }

    public void setAnteproyecto(Anteproyecto a) {
        this.anteproyectoSelected = a;
        this.estudiantes = a.getEstudianteList();
    }
    /**
     * Carga un anteporyecto para edición
     */
    public void cargarDatosEdicion() {
        System.out.println("Se llamó :v");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMetadatosEditAnteproyecto");
        requestContext.execute("PF('dlgEditarAnteproyecto').show()");
        System.out.println("mande a ejecutar");
    }

    public Programa getPrgramaUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
        UsuarioPrograma usuarioPrograma = usuario.getUsuarioProgramaList().get(0); ///VERIFICAR SI ES SOLO UNO TODO TO DO
        return usuarioPrograma.getPrograma();

    }

}
