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
import com.unicauca.coordinacionpis.sessionbean.AnteproyectoFacade;
import com.unicauca.coordinacionpis.sessionbean.EstudianteFacade;
import com.unicauca.coordinacionpis.sessionbean.ProfesorFacade;
import com.unicauca.coordinacionpis.sessionbean.ProgramaFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
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
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author David
 */
@ManagedBean
@ViewScoped
public class AnteproyectoController implements Serializable {

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

    private Anteproyecto anteproyectoRegistrar;
    private Estudiante estudiante1Select;
    private Estudiante estudiante2Select;
    private Profesor directorSelect;
    private Profesor codirectorSelect;

    private ArrayList<Estudiante> estudiantesList;
    private ArrayList<Profesor> profesorsList;

    public AnteproyectoController() {
        anteproyectoRegistrar = new Anteproyecto();
        estudiante1Select = new  Estudiante();
        estudiante2Select = new  Estudiante();
        directorSelect = new Profesor();
        codirectorSelect = new Profesor();
 
    }

    
    
    public DataModel getDataModelAnteproyectos() {
        return dataModelAnteproyectos;
    }

    public void setDataModelAnteproyectos(DataModel dataModelAnteproyectos) {
        this.dataModelAnteproyectos = dataModelAnteproyectos;
    }

    public AnteproyectoFacade getEjbAnteproyecto() {
        return ejbAnteproyecto;
    }

    public void setEjbAnteproyecto(AnteproyectoFacade ejbAnteproyecto) {
        this.ejbAnteproyecto = ejbAnteproyecto;
    }

    public Anteproyecto getAnteproyectoSelected() {
        return anteproyectoRegistrar;
    }

    public void setAnteproyectoSelected(Anteproyecto anteproyectoSelected) {
        this.anteproyectoRegistrar = anteproyectoSelected;
    }

    public ArrayList<Estudiante> getEstudiantesList() {
        if (estudiantesList == null) {
            estudiantesList = new ArrayList();
        }
        return estudiantesList;
    }

    public void setEstudiantesList(ArrayList<Estudiante> estudiantesList) {
        this.estudiantesList = estudiantesList;
    }

    public ArrayList<Profesor> getProfesorsList() {
        return profesorsList;
    }

    public void setProfesorsList(ArrayList<Profesor> profesorsList) {
        this.profesorsList = profesorsList;
    }

    public Estudiante getEstudianteSelect() {
        if (estudiante1Select == null) {
            return new Estudiante();
        }
        return estudiante1Select;
    }

    public void setEstudianteSelect(Estudiante estudianteSelect) {
        this.estudiante1Select = estudianteSelect;
    }

    public Estudiante getEstudiante1Select() {
        if (estudiante1Select == null) {
            estudiante1Select = new Estudiante();
        }
        return estudiante1Select;
    }

    public void setEstudiante1Select(Estudiante estudiante1Select) {
        this.estudiante1Select = estudiante1Select;
    }

    public Estudiante getEstudiante2Select() {
        if (estudiante2Select == null) {
            estudiante2Select = new Estudiante();
        }
        return estudiante2Select;
    }

    public void setEstudiante2Select(Estudiante estudiante2Select) {
        this.estudiante2Select = estudiante2Select;
    }

    public Profesor getDirectorSelect() {
        if (directorSelect == null) {
            directorSelect = new Profesor();
        }

        return directorSelect;
    }

    public void setDirectorSelect(Profesor directorSelect) {
        this.directorSelect = directorSelect;
    }

    public Profesor getCodirecotrSelect() {
        if (codirectorSelect == null) {
            codirectorSelect = new Profesor();
        }
        return codirectorSelect;
    }

    public void setCodirecotrSelect(Profesor codirecotrSelect) {
        this.codirectorSelect = codirecotrSelect;
    }

    public void registrarAnteproyecto() {

        Profesor director = ejbProfesor.find(directorSelect.getIdProfesor());
        if (director == null) {
            System.out.println("No existee director");
        }
        System.out.println("Director "+ director.getNombreProfesor());
        anteproyectoRegistrar.setDirectorAnteproyecto(directorSelect);
        Programa programa = ejbPrograma.find(this.getPrgramaUsuario());
        System.out.println("Programa "+programa.getNombrePrograma());
        anteproyectoRegistrar.setProgramaAnteproyecto(programa);
        System.out.println("Fecha: "+anteproyectoRegistrar.getFechaAnteproyecto());
        System.out.println("Titulo: "+anteproyectoRegistrar.getTituloAnteproyecto());
        
        ejbAnteproyecto.create(anteproyectoRegistrar);

    }

    public int getPrgramaUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
        UsuarioPrograma usuarioPrograma = usuario.getUsuarioProgramaList().get(0); ///VERIFICAR SI ES SOLO UNO TODO TO DO
        int idPrograma = usuarioPrograma.getPrograma().getIdPrograma();
        return idPrograma;
    }

    public Anteproyecto getAnteproyectoRegistrar() {
        if(anteproyectoRegistrar==null){
            anteproyectoRegistrar=new Anteproyecto();
        }
        return anteproyectoRegistrar;
        
    }

    public void setAnteproyectoRegistrar(Anteproyecto anteproyectoRegistrar) {
        this.anteproyectoRegistrar = anteproyectoRegistrar;
    }

    
    
}
