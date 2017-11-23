package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Usuario;
import com.unicauca.coordinacionpis.entidades.Usuariogrupo;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuariogrupoFacade;
import com.unicauca.coordinacionpis.utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;
import javax.faces.context.Flash;
import javax.faces.event.PhaseId;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class SesionController implements Serializable {
    /**
     * Facade para la conexión a la tabla usuario-grupo
     */
    @EJB
    private UsuariogrupoFacade ejbUsuarioGrupo;
    /**
     * Facade para la conexión a la tabla usuario
     */
    @EJB
    private UsuarioFacade ejbUsuario;
    
    @PersistenceContext(unitName = "GDCoordinacionPISPU")
    private EntityManager em;
    /**
     * Nombre del usuario intentando entrar a la app
     */
    private String nombreDeUsuario;
    /**
     * Contraseña provista por un usuario para entrar a la app
     */
    private String contrasenia;
    /**
     * Identificación del usuario que entró a la app
     */
    private String identificacion;
    /**
     * Grupo al cual pertenece el usuario que inició sesión en la app
     */
    private String grupo;
    /**
     * Nombre de la plantilla a utilizar en pantalla según el usuario 
     * que inició sesión
     */
    private String plantilla;

    //bools
    /**
     * Determina si un usuario ha iniciado sesión
     */
    private boolean haySesion;
    /**
     * Determina si hubo un erro en la sesión
     */
    private boolean errorSesion;
    /**
     * Determina si se deben o no mostrar las opciones del coordinador
     */
    private boolean opcionesCoordinador;
    /**
     *Determina si se deben o no mostrar las ppciones del administrado 
     */
    private boolean opcionesAdministrador;
    /**
     * Indica si el usuario que inicia sesión está o no activo
     */
    private boolean activo;

    public SesionController() {
        opcionesAdministrador = false;
        opcionesCoordinador = true;
    }

    protected EntityManager getEntityManager() {
        return em;
    }
    
    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isHaySesion() {
        return haySesion;
    }

    public void setHaySesion(boolean haySesion) {
        this.haySesion = haySesion;
    }

    public boolean isErrorSesion() {
        return errorSesion;
    }

    public void setErrorSesion(boolean errorSesion) {
        this.errorSesion = errorSesion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public boolean isOpcionesCoordinador() {
        return opcionesCoordinador;
    }

    public void setOpcionesCoordinador(boolean opcionesCoordinador) {
        this.opcionesCoordinador = opcionesCoordinador;
    }

    public boolean isOpcionesAdministrador() {
        return opcionesAdministrador;
    }

    public void setOpcionesAdministrador(boolean opcionesAdministrador) {
        this.opcionesAdministrador = opcionesAdministrador;
    }
    /**
     * Entrada a la aplicación, determina si las credenciales son correctas
     * y redirige el usuario a la pagina que le corresponda según su rol
     * @throws IOException
     * @throws ServletException 
     */
    public void login() throws IOException, ServletException {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext fc = FacesContext.getCurrentInstance();
        this.activo=true;
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        
        if (req.getUserPrincipal() != null) 
            req.logout();
          
        try{ iniciarSesion(req); }catch (ServletException e) {this.errorSesion = true;}
      
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Flash flash = getCurrentInstance().getExternalContext().getFlash();
        flash.setKeepMessages(true);
        FacesContext context = FacesContext.getCurrentInstance();        
        if (this.errorSesion && this.activo==false) {
            req.logout();            
            context.addMessage(null, new FacesMessage("Error", "El usuario "+this.nombreDeUsuario+" se encuentra deshabilitado y por tanto el acceso al sistema ha sido denegado. Para mayor información contáctese con el administrador del sistema"));   
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GDCoordinacionPIS/");
        }
        else if(this.errorSesion)
        {
            context.addMessage(null, new FacesMessage("Error", "Nombre de usuario o contraseña erróneos"));  
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GDCoordinacionPIS/");
        }
        
    }
    /**
     * Cierra la sesión de un usuario
     * @throws IOException
     * @throws ServletException 
     */
    public void logout() throws IOException, ServletException {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        try {
            req.logout();
            req.getSession().invalidate();
            fc.getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/GDCoordinacionPIS/");

        } catch (ServletException e) {
            fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ocurrió un error cerrando la sesión"));
        }

    }
    /**
     * Renderiza la información a mostrar del usuario que inició sesión
     * @return 
     */
    public String paraMostrar() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() == null) {
            return "";
        } else {
            Usuario usuario = ejbUsuario.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
            if (usuario == null) {
                return "";
            } else {
                String[] nombres = usuario.getUsunombres().split(" ");
                String[] apellidos = usuario.getUsuapellidos().split(" ");

                String nombreAMostrar = "";
                if (nombres.length > 0) {
                    nombreAMostrar = nombreAMostrar + nombres[0];
                }
                if (apellidos.length > 0) {
                    nombreAMostrar = nombreAMostrar + " " + apellidos[0];
                }

                return nombreAMostrar;
            }

        }
    }
    /**
     * Convierte la imagen del usuario para mostrar en pantalla,
     * si el usuario no tiene imagen seleccionada se devuelve la foto por defecto
     * @return foto del usuario si la posee
     */
    public StreamedContent getImagenFlujo() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        } else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            Usuario usu = ejbUsuario.buscarPorIdUsuario(Long.valueOf(id)).get(0);
            if (usu.getUsufoto() == null) {
                return Utilidades.getImagenPorDefecto("foto");
            } else {
                return new DefaultStreamedContent(new ByteArrayInputStream(usu.getUsufoto()));
            }
        }
    }

    public void modficarOpciones(String opcion, CargarFormularioController cargarFormularioController, AddNavegacionController addNavegacionController) {
        if (opcion.equalsIgnoreCase("coordinador")) {
            opcionesAdministrador = false;
            opcionesCoordinador = true;
        } else {
            opcionesAdministrador = true;
            opcionesCoordinador = false;
        }
        cargarFormularioController.setRuta("");
        addNavegacionController.cambioDeOpciones();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formMenu");
        requestContext.update("formMenuUsuario");
        requestContext.update("panelContent");
        requestContext.update("navegacion");
    }
    /**
     * Determina si un usuario está o no activo
     * @param usunombreusuario nombre del usuario que se quiere saber si está
     * o no activo
     * @return verdadero si está activo, falso en caso contrario
     */
    private boolean Activo(String usunombreusuario) 
    {
        Query query = getEntityManager().createNamedQuery("Usuario.findByUsunombreusuario");
        query.setParameter("usunombreusuario", usunombreusuario);
        List<Usuario> resultList = query.getResultList();
        if (resultList.size() > 0) {
            Usuario u=(Usuario)resultList.get(0);
            if(u.getUsuestado())
                return true;
            else
            {
                this.haySesion = false;
                this.errorSesion = true;
                this.activo=false;
                return false;
            }
            
        } else {
            this.haySesion = false;
            this.errorSesion = true;
            this.activo=false;
            return false;
        }
    }

    private void iniciarSesion(HttpServletRequest req) throws IOException, ServletException {
        req.login(this.nombreDeUsuario, this.contrasenia); 
        if(Activo(this.nombreDeUsuario))
        {
            req.getServletContext().log("Autenticacion exitosa");
            this.haySesion = true;
            this.errorSesion = false;
            this.activo=true;
            Usuariogrupo usuariogrupo = this.ejbUsuarioGrupo.buscarPorNombreUsuarioObj(req.getUserPrincipal().getName());
            ELContext elContext = FacesContext.getCurrentInstance().getELContext();
            CargarFormularioController cargarFormularioController = (CargarFormularioController) FacesContext.getCurrentInstance().getApplication()
            .getELResolver().getValue(elContext, null, "cargarFormulariosController");

            this.grupo = usuariogrupo.getUsuariogrupoPK().getGruid();
            if (grupo.equalsIgnoreCase("1")) {
                this.plantilla = "/sesionAdmin/_admintmp.xhtml";
                FacesContext.getCurrentInstance().getExternalContext().redirect("/GDCoordinacionPIS/GDCP/administrador/usuario/ListarUsuarios.xhtml");

                 identificacion = "" + this.ejbUsuarioGrupo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getUsuid();

            } else if (grupo.equalsIgnoreCase("2")) {
                this.plantilla = "/sesionCoordinador/_coordinadortmp.xhtml";
                FacesContext.getCurrentInstance().getExternalContext().redirect("/GDCoordinacionPIS/GDCP/coordinador/PlandeEstudio/PlandeEstudio.xhtml");
                identificacion = "" + this.ejbUsuarioGrupo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getUsuid();

            } else if (grupo.equalsIgnoreCase("3")) {
                this.plantilla = "/sesionJefe/_jefetmp.xhtml";
                FacesContext.getCurrentInstance().getExternalContext().redirect("/GDCoordinacionPIS/GDCP/jefe/OfertaAcademica/ofertasAcademicas.xhtml");
                identificacion = "" + this.ejbUsuarioGrupo.buscarPorNombreUsuario(req.getUserPrincipal().getName()).get(0).getUsuario().getUsuid();

            }
        }
        
    }

}
