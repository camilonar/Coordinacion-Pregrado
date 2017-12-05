package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Grupo;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil.PersistAction;
import com.unicauca.coordinacionpis.sessionbean.GrupoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("grupoController")
@SessionScoped
/**
 * Controlador para listar los grupos registrados en la aplicación. Entiendase 
 * por grupo, como el tipo de usuarios: adminstrador, coordinador, jefe. 
 */
public class GrupoController implements Serializable {
    
    /**
     * Conexión la con la tabla grupo
     */
    @EJB
    private com.unicauca.coordinacionpis.sessionbean.GrupoFacade ejbFacade;
    /**
     * Grupos registrados en la base de datos
     */
    private List<Grupo> items = null;
    /**
     * Grupo seleccionado
     */
    private Grupo selected;

    public GrupoController() {
    }

    

    protected void initializeEmbeddableKey() {
    }


    public Grupo prepareCreate() {
        selected = new Grupo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleAdmin").getString("GrupoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleAdmin").getString("GrupoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleAdmin").getString("GrupoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleAdmin").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleAdmin").getString("PersistenceErrorOccured"));
            }
        }
    }
    /**
     * Métodos Get y Set
     */
    public Grupo getSelected() {
        return selected;
    }

    public void setSelected(Grupo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    private GrupoFacade getFacade() {
        return ejbFacade;
    }
    public List<Grupo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    public Grupo getGrupo(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Grupo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Grupo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Grupo.class)
    public static class GrupoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GrupoController controller = (GrupoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "grupoController");
            return controller.getGrupo(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Grupo) {
                Grupo o = (Grupo) object;
                return getStringKey(o.getGruid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Grupo.class.getName()});
                return null;
            }
        }

    }

}
