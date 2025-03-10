package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Departamento;
import com.unicauca.coordinacionpis.entidades.Materia;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil.PersistAction;
import com.unicauca.coordinacionpis.sessionbean.MateriaFacade;

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
 * Controlador para las vistas: CrearMateria, EditarMateria, ListarMaterias y
 * VerMateria.
 * También es el encargado de la gestión de las materias: creación, edición y 
 * consulta.
 */
public class MateriaController implements Serializable {
    /**
     * Conexión con la tabla materia
     */
    @EJB
    private com.unicauca.coordinacionpis.sessionbean.MateriaFacade ejbFacade;
    /**
     * Listado de materias registradas
     */
    private List<Materia> items = null;
    /**
     * Materia seleccionada
     */
    private Materia materia;
    /**
     * Departamento al cual pertenece la materia seleccionada
     */
    private Departamento departamento;
    /**
     * Datos de la busqueda que desea hacer el usuario
     */
    private String datoBusqueda;

    public MateriaController() {
        materia = new Materia();
        departamento = new Departamento();
        datoBusqueda = "";
    }

    public Materia getSelected() {
        return materia;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public void setSelected(Materia selected) {
        this.materia = selected;
        this.departamento = selected.getIdDepartamento();
    }

    public String getDatoBusqueda() {
        return datoBusqueda;
    }

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public List<Materia> getListaMaterias() {
        ejbFacade.limpiarCache();
        return ejbFacade.buscarMateria(datoBusqueda);
    }
    /**
     * Registra una materia e la base de datos
     */
    public void registrarMateria() {
        materia.setIdDepartamento(departamento);
        ejbFacade.create(materia);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('MateriaCreateDialog').hide()");
        items = ejbFacade.findAll();
        departamento = new Departamento();
        materia = new Materia();

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La materia se registró con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        requestContext.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado

        requestContext.update("MateriaListForm");
        requestContext.update("MateriaCreateForm");
    }
    /**
     * Cambia los datos de una materia 
     */
    public void editarMateria() {
        materia.setIdDepartamento(departamento);
        ejbFacade.edit(materia);
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('MateriaEditDialog').hide()");
        ejbFacade.limpiarCache();
        items = ejbFacade.findAll();
        departamento = new Departamento();
        materia = new Materia();
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La materia se editó con éxito"));
//        requestContext.execute("PF('mensajeRegistroExitoso').show()");

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La materia se editó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        requestContext.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado

        requestContext.update("MateriaListForm");
        requestContext.update("MateriaCreateForm");

    }
    /**
     * Cancela la edición de una materia en GUI
     */
    public void cancelarEdicion() {
        this.departamento = new Departamento();
        this.materia = new Materia();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('MateriaEditDialog').hide()");

    }
    /**
     * Cancela el registro de una materia, en GUI
     */
    public void cancelarRegistro() {
        this.departamento = new Departamento();
        this.materia = new Materia();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('MateriaCreateDlg').hide()");
    }
    /**
     * Mensaje para preguntar si está seguro de eliminar una materia
     * @param materia 
     */
    public void confirmarEliminacion(Materia materia) {
        RequestContext context = RequestContext.getCurrentInstance();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "¿Está seguro que desea eliminar la materia " + materia.getNombreMateria() + " ?"));
        context.execute("PF('Confirmacion').show()");
        this.materia = materia;
    }
    /**
     * Elimina la materia seleccionada
     */
    public void eliminarMateria() {
        ejbFacade.remove(materia);
        items = ejbFacade.findAll();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("MateriaListForm:datalist");
        requestContext.execute("PF('Confirmacion').hide()");
        departamento = new Departamento();
        materia = new Materia();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La materia se eliminó con éxito");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        requestContext.update("msg");//Actualiza la etiqueta growl para que el mensaje pueda ser mostrado
        requestContext.update("MateriaListForm");
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MateriaFacade getFacade() {
        return ejbFacade;
    }

    public List<Materia> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (materia != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(materia);
                } else {
                    getFacade().remove(materia);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleMateria").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/BundleMateria").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Materia getMateria(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Materia> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Materia> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Materia.class)
    public static class MateriaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MateriaController controller = (MateriaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "materiaController");
            return controller.getMateria(getKey(value));
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
            if (object instanceof Materia) {
                Materia o = (Materia) object;
                return getStringKey(o.getIdMateria());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Materia.class.getName()});
                return null;
            }
        }

    }

}
