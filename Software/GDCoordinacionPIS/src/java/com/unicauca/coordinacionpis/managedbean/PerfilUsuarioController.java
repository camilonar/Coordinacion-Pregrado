package com.unicauca.coordinacionpis.managedbean;


import com.unicauca.coordinacionpis.entidades.Usuario;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import com.unicauca.coordinacionpis.utilidades.Cifrar;
import com.unicauca.coordinacionpis.validadores.ValidarEdicionUsuarios;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;


@ManagedBean
@ViewScoped
public class PerfilUsuarioController implements Serializable {
    
    /**
     * Facade para la conexión a la tabla usuario
     */
    @EJB
    private UsuarioFacade usuarioEJB;
    /**
     * Usuario seleccionado en la sesión
     */
    private Usuario usuario;
    /**
     * Formateador de la fecha
     */
    private SimpleDateFormat sdf;
    /**
     * Determina si se está o no mostrando la contraseña
     */
    private boolean mostrarContrasena;
    /**
     * Determina si se está o no mostrando el teléfono
     */
    private boolean mostrarTelefono;
    /**
     * Determina si mostrar o no una extensión
     */
    private boolean mostrarExtension;
    /**
     * Determina si mostrar o no el celular
     */
    private boolean mostrarCelular;
    /**
     * contraseña digitada
     */
    private String contrasena;
    /**
     * Confirmación de la contraseña digitada
     */
    private String confirmarContrasena;
    /**
     * TElefono del usuairo
     */
    private String telefono;
    /**
     * Extensión del usuario
     */
    private String extension;
    /**
     * Celular del usuairo
     */
    private String celular;

    public PerfilUsuarioController() {
        this.sdf = new SimpleDateFormat("yyyy-MM-dd");
    }

    @PostConstruct
    private void init() {
        buscarUsuario();
        iniciarVariables();

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isMostrarContrasena() {
       
        return mostrarContrasena;
        
    }

    public void setMostrarContrasena(boolean mostrarContrasena) {
        this.mostrarContrasena = mostrarContrasena;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmarContrasena() {
        return confirmarContrasena;
    }

    public void setConfirmarContrasena(String confirmarContrasena) {
        this.confirmarContrasena = confirmarContrasena;
    }

    public boolean isMostrarTelefono() {
        return mostrarTelefono;
    }

    public void setMostrarTelefono(boolean mostrarTelefono) {
        this.mostrarTelefono = mostrarTelefono;
    }

    public boolean isMostrarExtension() {
        return mostrarExtension;
    }

    public void setMostrarExtension(boolean mostrarExtension) {
        this.mostrarExtension = mostrarExtension;
    }

    public boolean isMostrarCelular() {
        return mostrarCelular;
    }

    public void setMostrarCelular(boolean mostrarCelular) {
        this.mostrarCelular = mostrarCelular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    /**
     * Busca un usuario según el usuario que haya iniciado sesión
     */
    private void buscarUsuario() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
        if (req.getUserPrincipal() != null) {
            this.usuario = this.usuarioEJB.buscarUsuarioPorNombreDeUsuario(req.getUserPrincipal().getName());
        }
    }

    public SimpleDateFormat getSdf() {
        return sdf;
    }

    public void setSdf(SimpleDateFormat sdf) {
        this.sdf = sdf;
    }
    /***
     * Muestra el modificar contraseña
     */
    public void mostrarModificarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarContrasena = false;
        requestContext.update("formularioPerfilDatosPersonales");
    }
    /**
     * Cancela el modificar contraseña
     */
    public void cancelarActualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarContrasena = true;
        this.contrasena = "";
        this.confirmarContrasena = "";
        requestContext.update("formularioPerfilDatosPersonales");
    }
    /**
     * Edita la contraseña del usuario
     */
    public void cambiarContrasena() {
        ValidarEdicionUsuarios validarEdicionUsuario = new ValidarEdicionUsuarios();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        if (validarEdicionUsuario.validarContrasenaConConfirmacion(this.contrasena, this.confirmarContrasena)) {
            
            this.usuario.setUsucontrasena(Cifrar.sha256(this.contrasena));
            
            this.usuarioEJB.edit(this.usuario);
            
            this.mostrarContrasena = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "La contraseña fue editada con éxito"));
        }  
        requestContext.getCurrentInstance().update("msgConEx");
        requestContext.update("formularioPerfilDatosPersonales");        
    }
    /**
     * Cancela la actualización del telefon
     */
    public void cancelarActualizarTelefono() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarTelefono = true;
        this.telefono = "";
        requestContext.update("formularioPerfilDatosPersonales");
    }

    

    
    /**
     * Cancela la actualización de la extensión
     */
    public void cancelarActualizarExtension() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.mostrarExtension = true;
        this.extension = "";
        requestContext.update("formularioPerfilDatosPersonales");
    }

    

    

    /**
     * Inicializa las variables por defecto
     */

    private void iniciarVariables() {
        this.mostrarContrasena = true;  
        this.mostrarTelefono = true;
        this.mostrarExtension = true;
        this.mostrarCelular = true;
    }
}
