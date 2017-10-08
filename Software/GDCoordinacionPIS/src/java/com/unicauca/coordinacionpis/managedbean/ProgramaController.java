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
public class ProgramaController implements Serializable {

    @EJB
    private com.unicauca.coordinacionpis.sessionbean.ProgramaFacade ejbFacade;
    private List<Programa> items = null;
    
    
    private Programa programa;
   

    public ProgramaController() {
        programa = new Programa();
    }

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
