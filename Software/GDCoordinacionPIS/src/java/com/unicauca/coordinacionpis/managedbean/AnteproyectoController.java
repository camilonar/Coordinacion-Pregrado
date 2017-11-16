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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.FacesException;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil;
import com.unicauca.coordinacionpis.managedbean.util.PagingInfo;
import com.unicauca.coordinacionpis.sessionbean.AbstractFacade;
import com.unicauca.coordinacionpis.sessionbean.AnteproyectoFacade;
import com.unicauca.coordinacionpis.sessionbean.EstudianteFacade;
import com.unicauca.coordinacionpis.sessionbean.ProfesorFacade;
import com.unicauca.coordinacionpis.sessionbean.ProgramaFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author David
 */
@ManagedBean
@ViewScoped
public class AnteproyectoController implements Serializable {

    private Anteproyecto anteproyectoSelected;
    private Estudiante estudianteSelected;
    private Profesor directorSelected;
    private List<Estudiante> estudiantes;

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

    //modificar para filtrar por cualquier campo 
    private DataModel dataModelAnteproyectos = new LazyDataModel<Anteproyecto>() {
        @Override
        public List<Anteproyecto> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
            setRowCount(ejbAnteproyecto.count());
            int[] range = {first, first + pageSize};
            return ejbAnteproyecto.findRange(range);
        }
    };

    public void cargarAnteproyecto(){
       //verificar si puede cargar estoo para un usuario determinado ...s....        
        Anteproyecto ant = this.ejbAnteproyecto.find(this.anteproyectoSelected.getIdAnteproyecto());
        if(ant!=null){
            this.anteproyectoSelected = ant;
            this.estudiantes = this.anteproyectoSelected.getEstudianteList();
            this.directorSelected = this.anteproyectoSelected.getDirectorAnteproyecto();
        }
    }
    
    
    public AnteproyectoController() {
        estudianteSelected = new Estudiante();
        this.estudiantes = new ArrayList<>();
        this.anteproyectoSelected = new Anteproyecto();
        this.directorSelected = new Profesor();
    }

    public DataModel getDataModelAnteproyectos() {
        return dataModelAnteproyectos;
    }

    public void setDataModelAnteproyectos(DataModel dataModelAnteproyectos) {
        this.dataModelAnteproyectos = dataModelAnteproyectos;
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

    
    
    
    
    
    
    
    
    public void addToListEstudiantes() {
        if (this.estudiantes.size() < 2 && !this.estudiantes.contains(estudianteSelected)) {
            this.estudiantes.add(estudianteSelected);
            estudianteSelected = new Estudiante();
        }
    }

    public void removeToListEstudiantes(Estudiante e) {
        this.estudiantes.remove(e);
    }

    public void autocompletarEstudiante() {
        System.out.println("Solo llama cuando muy triste está :'(");
        Estudiante completo = this.ejbEstudiante.findByCodigo(estudianteSelected.getCodigoEstudiante());
        if (completo != null) {
            this.estudianteSelected = completo;

        }

    }
    public void autocompletarDirector() {
        
        
        System.out.println("a:" + directorSelected.getIdProfesor() +","+directorSelected.getNombreProfesor());
        
        Profesor completo = this.ejbProfesor.find(directorSelected.getIdProfesor());
        if (completo != null) {
           
            this.directorSelected = completo;
            System.out.println("si hizo el cambio");
        }
        
        System.out.println("d:" + directorSelected.getIdProfesor() +","+directorSelected.getNombreProfesor());
    }

    public void registrarAnteproyecto() {

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
        this.ejbAnteproyecto.create(anteproyectoSelected);
        System.out.println("E completo");
    }
    
    
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
    
    
    
    
    public void setAnteproyecto(Anteproyecto a)
    {
        this.anteproyectoSelected = a;
        this.estudiantes = a.getEstudianteList();
    }
    
    
    public void cargarDatosEdicion()
    {
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
