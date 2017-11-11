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

    public AnteproyectoController() {
        estudianteSelected = new Estudiante();
        this.estudiantes = new ArrayList<>();
        this.anteproyectoSelected = new Anteproyecto();
        this.directorSelected = new Profesor();
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
    
    public void autocompletarEstudiante(){
       Estudiante completo = this.ejbEstudiante.find(estudianteSelected.getIdEstudiante());
       if(completo!=null){
           this.estudianteSelected = completo;
       }

    }
    
      public void autocompletarDirector(){
      Profesor completo = this.ejbProfesor.find(directorSelected.getIdProfesor());
       if(completo!=null){
           this.directorSelected = completo;
       }

    }
      
      public void registrarAnteproyecto(){
          
         Profesor director  = this.ejbProfesor.find(directorSelected.getIdProfesor());
         if(director==null){
          // registrar profesor nuevo
             System.out.println("Profesor no existe");
         }
         anteproyectoSelected.setDirectorAnteproyecto(director);
         anteproyectoSelected.setProgramaAnteproyecto(getPrgramaUsuario());
         this.ejbAnteproyecto.create(anteproyectoSelected);
          System.out.println("Registro completo");
      }
      
       public Programa getPrgramaUsuario() {

       
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
            Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
            UsuarioPrograma usuarioPrograma = usuario.getUsuarioProgramaList().get(0); ///VERIFICAR SI ES SOLO UNO TODO TO DO
            return usuarioPrograma.getPrograma();
     
    }
      

}
