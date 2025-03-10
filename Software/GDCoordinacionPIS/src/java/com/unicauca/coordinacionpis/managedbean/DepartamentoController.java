package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Departamento;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil.PersistAction;
import com.unicauca.coordinacionpis.sessionbean.DepartamentoFacade;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
/**
 * Controlador que permite desplegar el listado de departamentos registrados
 * en la aplicación.
 * Controlador de las vistas: agregar, editar, listar y ver departamento.
 * También se encarga de la gestión de un departamento: creación, edición y 
 * consulta de estos
 */
public class DepartamentoController implements Serializable {
    /**
     * Conexión a la tabla departamento
     */
    @EJB
    private com.unicauca.coordinacionpis.sessionbean.DepartamentoFacade ejbFacade;
    /**
     * Departamentos registrados
     */
    private List<Departamento> items = null;
    /**
     * Departamento seleccionado
     */
    private Departamento departamento;

    public DepartamentoController() {
        departamento = new Departamento();
    }

    

    protected void initializeEmbeddableKey() {
    }

    public Departamento prepareCreate() {
        departamento = new Departamento();
        initializeEmbeddableKey();
        return departamento;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DepartamentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DepartamentoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DepartamentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            departamento = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (departamento != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(departamento);
                } else {
                    getFacade().remove(departamento);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    @FacesConverter(forClass = Departamento.class)
    public static class DepartamentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DepartamentoController controller = (DepartamentoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "departamentoController");
            return controller.getDepartamento(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Departamento) {
                Departamento o = (Departamento) object;
                return getStringKey(o.getIdDepartamento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Departamento.class.getName()});
                return null;
            }
        }

    }
    /**
     * Registra un departamento en la base de datos
     */
    public void registrarDepartamento() {

        ejbFacade.create(departamento);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('DepartamentoCreateDialog').hide()");
        items = ejbFacade.findAll();
        departamento = new Departamento();
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información fue registrada con éxito."));

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El departamento se registró con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        requestContext.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado

        requestContext.update("DepartamentoCreateForm");
        requestContext.update("DepartamentoListForm");

    }
    /**
     * Cambia los datos de un departamento 
     */
    public void editarDepartamento() {

        ejbFacade.edit(departamento);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('DepartamentoEditDialog').hide()");
        items = ejbFacade.findAll();
        departamento = new Departamento();
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La información fue editada con éxito"));
//        requestContext.execute("PF('mensajeRegistroExitoso').show()");

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El departamento se editó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        requestContext.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado

        requestContext.update("DepartamentoEditForm");
        requestContext.update("DepartamentoListForm");

    }
    /**
     * Cancela la edición de un departaento en GUI
     */
    public void cancelarEdicion() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('DepartamentoEditDialog').hide()");
        departamento = new Departamento();
    }
    /**
     * Cancela el registro de un departamento en GUI
     */
    public void cancelarRegistro() {
        departamento = new Departamento();
    }
    /**
     * Cancela la eliminación de un departamento en GUI
     */
    public void cancelarEliminacion() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('mensajeConfirmarEliminar').hide()");
        departamento = new Departamento();
    }
    /**
     * Elimina un departamento de la base de datos
     */
    public void eliminarDepartamento() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (departamento != null) {
            if (departamento.getMateriaList().isEmpty()) {
                ejbFacade.remove(departamento);
                requestContext.execute("PF('mensajeConfirmarEliminar').hide()");
                items = ejbFacade.findAll();
               
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El departamento se eliminó con éxito");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                requestContext.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado

                requestContext.update("DepartamentoCreateForm");
                requestContext.update("DepartamentoListForm");
                requestContext.update("DepartamentoListForm:datalist");
            } else {
                requestContext.execute("PF('mensajeConfirmarEliminar').hide()");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "El departamento tiene materias asociadas"));
                requestContext.execute("PF('mensajeError').show()");
            }
        }
        departamento = new Departamento();

    }
    /**
     * Despliega un mensaje confirmando que el departamento se eliminó
     * @param departamento 
     */
    public void mostrarMensajeConfirmarEliminarDepartamento(Departamento departamento) {
        this.departamento = departamento;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "¿Está seguro que desea eliminar el departamento?"));
        requestContext.execute("PF('mensajeConfirmarEliminar').show()");

    }
    
    /**
     * Métodos de get y set
     */
        public Departamento getSelected() {
        return departamento;
    }

    public void setSelected(Departamento selected) {
        this.departamento = selected;
    }

    protected void setEmbeddableKeys() {
    }


    private DepartamentoFacade getFacade() {
        return ejbFacade;
    }
    public List<Departamento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    public Departamento getDepartamento(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Departamento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Departamento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
}
