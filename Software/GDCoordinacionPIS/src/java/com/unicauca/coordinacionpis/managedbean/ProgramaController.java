package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Programa;
import com.unicauca.coordinacionpis.sessionbean.ProgramaFacade;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
/**
 * Controlador que provee el listado de los programas registrados en la
 * aplicación.
 * Utilizado en la vista: editar y registrar usuario, 
 */
public class ProgramaController implements Serializable {
    
    /**
     * Facade para el acceso a la tabla programa
     */
    @EJB
    private com.unicauca.coordinacionpis.sessionbean.ProgramaFacade ejbFacade;
    /**
     * Listado de programas
     */
    private List<Programa> items = null;
    
    /**
     * Progrma seleccionado en GUI
     */
    private Programa programa;
   

    public ProgramaController() {
        programa = new Programa();
    }
    /**
     * Métodos Get y Set
     *  
     */

    public Programa getSelected() {
        return programa;
    }

    public void setSelected(Programa selected) {
        this.programa = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProgramaFacade getFacade() {
        return ejbFacade;
    }

    public List<Programa> getItems() {
        items = ejbFacade.findAll();
        return items;
    }

    

}
