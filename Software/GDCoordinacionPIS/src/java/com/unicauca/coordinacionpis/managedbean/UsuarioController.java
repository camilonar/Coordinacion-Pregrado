package com.unicauca.coordinacionpis.managedbean;

import com.unicauca.coordinacionpis.entidades.Cargo;
import com.unicauca.coordinacionpis.entidades.Departamento;
import com.unicauca.coordinacionpis.entidades.Grupo;
import com.unicauca.coordinacionpis.entidades.Programa;
import com.unicauca.coordinacionpis.entidades.Usuario;
import com.unicauca.coordinacionpis.entidades.UsuarioDepartamento;
import com.unicauca.coordinacionpis.entidades.UsuarioDepartamentoPK;
import com.unicauca.coordinacionpis.entidades.UsuarioPrograma;
import com.unicauca.coordinacionpis.entidades.UsuarioProgramaPK;
import com.unicauca.coordinacionpis.entidades.Usuariogrupo;
import com.unicauca.coordinacionpis.entidades.UsuariogrupoPK;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil;
import com.unicauca.coordinacionpis.managedbean.util.JsfUtil.PersistAction;
import com.unicauca.coordinacionpis.sessionbean.DepartamentoFacade;
import com.unicauca.coordinacionpis.sessionbean.ProgramaFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuarioDepartamentoFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuarioProgramaFacade;
import com.unicauca.coordinacionpis.sessionbean.UsuariogrupoFacade;
import com.unicauca.coordinacionpis.utilidades.Cifrar;
import com.unicauca.coordinacionpis.utilidades.RedimensionadorImagenes;
import com.unicauca.coordinacionpis.utilidades.Utilidades;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.PhaseId;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named("usuarioController")
@SessionScoped
public class UsuarioController implements Serializable {

    /**
     * Facade para la conexión a la tabla usuario-departamentos
     */
    @EJB
    private UsuarioDepartamentoFacade usuarioDepartamentoFacade;
    /**
     * Facade para la conexión a los usuarios
     */
    @EJB
    private UsuarioProgramaFacade usuarioProgramaFacade;
    /**
     * Facade para la conexión a la tabla departamento
     */
    @EJB
    private DepartamentoFacade departamentoFacade;
    /**
     * Facade para la conexión a la tabla programa
     */
    @EJB
    private ProgramaFacade programaFacade;

    /**
     * Enumeración para identificar los diferentes de usuario
     */
    public enum TIPO_USUARIO {
        ADMIN, COORDINADOR, JEFE
    };
    /**
     * Facade para la conexión a la tabla usuario
     */
    @EJB
    private com.unicauca.coordinacionpis.sessionbean.UsuarioFacade ejbUsuario;
    /**
     * Facade para la conexión a la tabla usuario-grupo
     */
    @EJB
    private UsuariogrupoFacade ejbUsuarioGrupo;
    /**
     * Listado de usuarios
     */
    private List<Usuario> items = null;
    /**
     * Cargo del usuario seleccionado actualmente
     */
    private Cargo cargo;
    /**
     * Grupo del usuario seleccionado actualmente
     */
    private Grupo grupo;
    /**
     * Resultados del filtro de la busqueda de usuarios
     */
    private List<Usuario> filtroBusqueda;
    /**
     *
     */
    private int rolActual;
    /**
     * Determina si una foto se ha seleccionado
     */
    private boolean campoFoto;
    /**
     * Determina si se está mostrando el campo contraseña
     */
    private boolean campoContrasena;
    /**
     * Contraseña ingresada
     */
    private String contrasena;
    /**
     * Valor del dato de busqueda
     */
    private String datoBusqueda;
    /**
     * Usuario actualmente seleccionado
     */
    private Usuario usuario;
    /**
     * Formateador de la fecha
     */
    private SimpleDateFormat formatoFecha;

    private byte[] imagen;
    /**
     * Contenido para mostrar la imagen
     */
    private DefaultStreamedContent miImagen;
    private UploadedFile file;
    private UploadedFile uploadedFile;

    private boolean fotoDefecto;

    private TIPO_USUARIO tipo;
    /**
     * Departamento del usuario seleccionado
     */
    private Departamento dpto;
    /**
     * Programam del usuario seleccionado
     */
    private Programa programa;
    private String progTmp;
    private String deptTmp;
    private int rolOriginal;
    private Departamento deptoNulo;

    public UsuarioController() {
        datoBusqueda = "";
        this.usuario = new Usuario();
        this.cargo = new Cargo();
        this.grupo = new Grupo();
        usuario.setUsugenero('M');
        this.campoFoto = true;
        this.campoContrasena = true;
        this.formatoFecha = new SimpleDateFormat("dd-MM-yyyy");

        this.miImagen = (DefaultStreamedContent) this.getImagenDefecto();
        fotoDefecto = true;
        tipo = TIPO_USUARIO.ADMIN;
        deptoNulo = new Departamento(-1);
    }

    /**
     * Obtiene el programa del usuario seleccionado, si no hay unprograma se
     * devuelve por defecto el programa de sistemas
     *
     * @return programa del usuario seleecionado
     */
    public Programa getPrograma() {
        if (programa == null) {
            programa = new Programa(-1);
        }
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    @PostConstruct
    public void init() {

    }

    public boolean isFotoDefecto() {
        return fotoDefecto;
    }

    public Usuario getSelected() {
        return usuario;
    }

    public void setSelected(Usuario selected) {
        this.usuario = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuarioFacade getFacade() {
        return ejbUsuario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public boolean isCampoContrasena() {
        return campoContrasena;
    }

    public void setCampoContrasena(boolean campoContrasena) {
        this.campoContrasena = campoContrasena;
    }

    public List<Usuario> getFiltroBusqueda() {
        return filtroBusqueda;
    }

    public void setFiltroBusqueda(List<Usuario> filtroBusqueda) {
        this.filtroBusqueda = filtroBusqueda;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDatoBusqueda() {
        return datoBusqueda;
    }

    public Departamento getDeptoNulo() {
        return deptoNulo;
    }

    public void setDeptoNulo(Departamento deptoNulo) {
        this.deptoNulo = deptoNulo;
    }

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public int getRolActual() {
        return rolActual;
    }

    public void setRolActual(int rolActual) {
        this.rolActual = rolActual;
    }

    /**
     * Prepara lo necesario para la creación de un usuario
     *
     * @return nuevo usuario a crear
     */
    public Usuario prepareCreate() {
        System.out.println("prepareCreate");
        rolActual = 0;
        usuario = new Usuario();
        usuario.setUsunombres("");
        this.file = null;
        if (!fotoDefecto) {
            this.establecerFotoPorDefecto();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("formfoto");
        }
        this.fotoDefecto = true;
        this.limpiarRegistrarUsuario();

        this.tipo = TIPO_USUARIO.ADMIN;
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("UsuarioCreateForm");
        this.dpto = null;
        this.programa = null;
        initializeEmbeddableKey();
        return usuario;
    }

    public boolean isCampoFoto() {
        return campoFoto;
    }

    public void setCampoFoto(boolean campoFoto) {
        this.campoFoto = campoFoto;
    }

    /**
     * Realiza el registro de un usuario y presenta un mensaje confirmando el
     * registro o un erro en caso de que se de
     */
    public void registrarUsuario() {
        this.usuario.setUsucontrasena(Cifrar.sha256(this.usuario.getUsucontrasena()));
        if (rolActual == 1) {
            this.usuario.setUsuestado(true);
        } else {
            this.usuario.setUsuestado(false);
        }
        if (!fotoDefecto) {
            this.usuario.setUsufoto(imagen);
        } else {
            this.usuario.setUsufoto(null);
        }
        usuario.setCarid(cargo);
        ejbUsuario.create(usuario);

        if (rolActual == 1 && tipo != TIPO_USUARIO.ADMIN) {
            deshabilitarRolAnterior();
            if (this.tipo == TIPO_USUARIO.COORDINADOR) {
                List<UsuarioPrograma> programas = new ArrayList<>();
                UsuarioProgramaPK usuarioProgramaPK = new UsuarioProgramaPK(usuario.getUsuid(), programa.getIdPrograma());
                UsuarioPrograma up = new UsuarioPrograma(usuarioProgramaPK);
                up.setNombreUsuario(usuario.getUsunombreusuario());
                up.setPrograma(programa);
                up.setUsuario(usuario);
                programas.add(up);
                usuario.setUsuarioProgramaList(programas);
                usuarioProgramaFacade.create(up);
            } else if (this.tipo == TIPO_USUARIO.JEFE) {
                List<UsuarioDepartamento> departamentos = new ArrayList<>();
                UsuarioDepartamentoPK usuarioDepartamentoPK = new UsuarioDepartamentoPK(usuario.getUsuid(), dpto.getIdDepartamento());
                UsuarioDepartamento ud = new UsuarioDepartamento(usuarioDepartamentoPK);
                ud.setNombreUsuario(usuario.getUsunombreusuario());
                ud.setDepartamento(dpto);
                ud.setUsuario(usuario);
                departamentos.add(ud);
                usuario.setUsuarioDepartamentoList(departamentos);
                usuarioDepartamentoFacade.create(ud);
            }
            this.tipo = TIPO_USUARIO.ADMIN;

        }
        Usuariogrupo usuarioGrupo = new Usuariogrupo();
        UsuariogrupoPK usuarioGrupoPK = new UsuariogrupoPK();

        usuarioGrupoPK.setGruid(grupo.getGruid());
        usuarioGrupoPK.setUsuid(this.usuario.getUsuid());
        usuarioGrupo.setUsuariogrupoPK(usuarioGrupoPK);
        usuarioGrupo.setGrupo(grupo);
        usuarioGrupo.setUsuario(usuario);
        usuarioGrupo.setUsunombreusuario(this.usuario.getUsunombreusuario());

        List a = new ArrayList();
        a.add(usuarioGrupo);
        this.usuario.setUsuariogrupoList(a);
        this.ejbUsuarioGrupo.create(usuarioGrupo);
        RequestContext requestContext = RequestContext.getCurrentInstance();

        ejbUsuario.limpiarCache();
        items = ejbUsuario.findAll();
        usuario = new Usuario();
        usuario.setUsugenero('M');

        requestContext.execute("PF('UsuarioCreateDialog').hide()");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se registró con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // requestContext.execute("PF('mensajeRegistroExitoso').show()");
        requestContext.update("msg");
        requestContext.update("formfoto");

    }

    /**
     * Mensaje de que el rol del usuario ha sido deshabilitado
     */
    public void mensajeRolDeshabiltado() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se deshabilitó con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        requestContext.update("msg");
        requestContext.update("UsuarioEditForm");
        System.out.println("vuelve a checkear");
    }

    /**
     * Deshabilita el rol de un usuario
     */
    public void deshabilitarRol() {
        if ("1".equals(grupo.getGruid())) {
            usuario.setUsuestado(false);
            for (UsuarioPrograma up : this.usuario.getUsuarioProgramaList()) {
                usuarioProgramaFacade.remove(up);
            }
            for (UsuarioDepartamento ud : this.usuario.getUsuarioDepartamentoList()) {
                usuarioDepartamentoFacade.remove(ud);
            }
        } else {
            usuario.setUsuestado(false);
            for (UsuarioPrograma up : this.usuario.getUsuarioProgramaList()) {
                usuarioProgramaFacade.remove(up);
            }
            for (UsuarioDepartamento ud : this.usuario.getUsuarioDepartamentoList()) {
                usuarioDepartamentoFacade.remove(ud);
            }
        }
        mensajeRolDeshabiltado();
    }

    /**
     * Deshabilita el rol anterior de un usuario
     */
    public void deshabilitarRolAnterior() {
        if ("2".equals(grupo.getGruid())) {
            String coordinador = programaFacade.findByProgramaCoordinador(programa.getIdPrograma());
            if (coordinador != null) {
                Usuario usuCoor = ejbUsuario.buscarUsuarioPorNombreDeUsuario(coordinador);
                usuCoor.setUsuestado(false);
                for (UsuarioPrograma up : usuCoor.getUsuarioProgramaList()) {
                    usuarioProgramaFacade.remove(up);
                }
                ejbUsuario.edit(usuCoor);
            }
        }
        if ("3".equals(grupo.getGruid())) {
            String jefe = departamentoFacade.findByDepartamentoJefe(dpto.getIdDepartamento());
            if (jefe != null) {
                Usuario usuJefe = ejbUsuario.buscarUsuarioPorNombreDeUsuario(jefe);
                usuJefe.setUsuestado(false);
                for (UsuarioDepartamento ud : usuJefe.getUsuarioDepartamentoList()) {
                    usuarioDepartamentoFacade.remove(ud);
                }
                ejbUsuario.edit(usuJefe);
            }
        }

    }

    /**
     * Determina si el rol seleccionado en el editar de un usuario coincide con
     * el rol que tiene registrado
     *
     * @return
     */
    public boolean esRolActual() {
        if ("1".equals(grupo.getGruid())) {
            if (usuario.getUsuestado()) {
                return true;
            } else {
                return false;
            }
        }
        if ("2".equals(grupo.getGruid())) {
            if (progTmp != null) {
                return true;
            } else {
                return false;
            }
        }
        if ("3".equals(grupo.getGruid())) {
            if (deptTmp != null) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Edita el rol de un usuario
     */
    public void editarUsuarioRol() {
        usuario.setUsuestado(true);
        usuario.setCarid(cargo);
        if (!fotoDefecto) {
            this.usuario.setUsufoto(imagen);
        } else {
            this.usuario.setUsufoto(null);
        }
        Usuariogrupo usuarioGrupo = new Usuariogrupo();
        UsuariogrupoPK usuarioGrupoPK = new UsuariogrupoPK();
        ejbUsuario.edit(usuario);

        for (UsuarioPrograma up : this.usuario.getUsuarioProgramaList()) {
            usuarioProgramaFacade.remove(up);
        }
        for (UsuarioDepartamento ud : this.usuario.getUsuarioDepartamentoList()) {
            usuarioDepartamentoFacade.remove(ud);
        }
        if (this.tipo == TIPO_USUARIO.COORDINADOR) {
            List<UsuarioPrograma> programas = new ArrayList<>();
            UsuarioProgramaPK usuarioProgramaPK = new UsuarioProgramaPK(usuario.getUsuid(), programa.getIdPrograma());
            UsuarioPrograma up = new UsuarioPrograma(usuarioProgramaPK);
            up.setNombreUsuario(usuario.getUsunombreusuario());
            up.setPrograma(programa);
            up.setUsuario(usuario);
            programas.add(up);
            usuario.setUsuarioProgramaList(programas);
            usuarioProgramaFacade.create(up);
        } else if (this.tipo == TIPO_USUARIO.JEFE) {
            List<UsuarioDepartamento> departamentos = new ArrayList<>();
            UsuarioDepartamentoPK usuarioDepartamentoPK = new UsuarioDepartamentoPK(usuario.getUsuid(), dpto.getIdDepartamento());
            UsuarioDepartamento ud = new UsuarioDepartamento(usuarioDepartamentoPK);
            ud.setNombreUsuario(usuario.getUsunombreusuario());
            ud.setDepartamento(dpto);
            ud.setUsuario(usuario);
            departamentos.add(ud);
            usuario.setUsuarioDepartamentoList(departamentos);
            usuarioDepartamentoFacade.create(ud);
        }
        this.tipo = TIPO_USUARIO.ADMIN;

        this.ejbUsuarioGrupo.remove(usuario.getUsuariogrupoList().get(0));

        usuarioGrupoPK.setGruid(grupo.getGruid());
        usuarioGrupoPK.setUsuid(this.usuario.getUsuid());
        usuarioGrupo.setUsuariogrupoPK(usuarioGrupoPK);
        usuarioGrupo.setUsunombreusuario(this.usuario.getUsunombreusuario());
        this.ejbUsuarioGrupo.edit(usuarioGrupo);

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('UsuarioRolDialog').hide()");
        ejbUsuario.limpiarCache();
        items = ejbUsuario.findAll();
        usuario = new Usuario();
        usuario.setUsugenero('M');

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se editó con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        requestContext.update("msg");
        //requestContext.update("UsuarioListForm");

    }

    /**
     * Edita la información de un usuario
     */
    public void editarUsuario() {
        usuario.setCarid(cargo);
        if (!fotoDefecto) {
            this.usuario.setUsufoto(imagen);
        } else {
            this.usuario.setUsufoto(null);
        }
        habilitarRol();
        Usuariogrupo usuarioGrupo = new Usuariogrupo();
        UsuariogrupoPK usuarioGrupoPK = new UsuariogrupoPK();
        ejbUsuario.edit(usuario);

        this.ejbUsuarioGrupo.remove(usuario.getUsuariogrupoList().get(0));

        usuarioGrupoPK.setGruid(grupo.getGruid());
        usuarioGrupoPK.setUsuid(this.usuario.getUsuid());
        usuarioGrupo.setUsuariogrupoPK(usuarioGrupoPK);
        usuarioGrupo.setUsunombreusuario(this.usuario.getUsunombreusuario());
        this.ejbUsuarioGrupo.edit(usuarioGrupo);

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('UsuarioEditDialog').hide()");
        ejbUsuario.limpiarCache();
        items = ejbUsuario.findAll();
        usuario = new Usuario();
        usuario.setUsugenero('M');

        requestContext.execute("PF('UsuarioCreateDialog').hide()");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se editó con éxito.");
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // requestContext.execute("PF('mensajeRegistroExitoso').show()");
        requestContext.update("msg");
        requestContext.update("formfoto");
        requestContext.update("UsuarioListForm");

    }

    /**
     * Selecciona un usuari para su edición, saca su rol, imagen etc para el
     * desliegue exitoso en pantalla
     *
     * @param usuario
     */
    public void seleccionarUsuarioEditar(Usuario usuario) {
        rolActual = 0;
        this.usuario = usuario;
        this.imagen = this.usuario.getUsufoto();
        if (this.imagen == null) {
            this.miImagen = (DefaultStreamedContent) Utilidades.getImagenPorDefecto("foto");
            convertirBytesAImagen();
            System.out.println("seleccionarUsuarioEditar file = null");
            this.fotoDefecto = true;
        } else {
            this.fotoDefecto = false;
        }
        this.cargo = usuario.getCarid();
        this.grupo = ejbUsuarioGrupo.buscarPorNombreUsuarioObj(usuario.getUsunombreusuario()).getGrupo();
        switch (grupo.getGruid()) {
            case "1":
                this.tipo = TIPO_USUARIO.ADMIN;
                if (usuario.getUsuestado()) {
                    rolActual = 1;
                }
                break;
            case "2":
                this.tipo = TIPO_USUARIO.COORDINADOR;
                break;
            case "3":
                this.tipo = TIPO_USUARIO.JEFE;
                break;
        }
        if (!this.usuario.getUsuarioDepartamentoList().isEmpty()) {
            rolActual = 1;
            this.dpto = this.usuario.getUsuarioDepartamentoList().get(0).getDepartamento();
            deptTmp = dpto.getNombre();
            this.tipo = TIPO_USUARIO.JEFE;
        } else {
            this.dpto = null;
        }

        if (!this.usuario.getUsuarioProgramaList().isEmpty()) {
            rolActual = 1;
            this.programa = this.usuario.getUsuarioProgramaList().get(0).getPrograma();
            progTmp = programa.getNombrePrograma();
            this.tipo = TIPO_USUARIO.COORDINADOR;
        } else {
            this.programa = null;
        }
        rolOriginal = rolActual;
    }

    /**
     * Selecciona el usuario para ver. Saca los datos necesarios para su
     * despliegue exitoso en pantalla
     *
     * @param usuario
     */
    public void seleccionarUsuarioVer(Usuario usuario) {
        this.usuario = usuario;
        this.imagen = this.usuario.getUsufoto();
        if (this.imagen == null) {
            this.miImagen = (DefaultStreamedContent) Utilidades.getImagenPorDefecto("foto");
            convertirBytesAImagen();
        }

        this.cargo = usuario.getCarid();
        this.grupo = ejbUsuarioGrupo.buscarPorNombreUsuarioObj(usuario.getUsunombreusuario()).getGrupo();

        this.tipo = TIPO_USUARIO.ADMIN;
        if (!this.usuario.getUsuarioDepartamentoList().isEmpty()) {
            this.dpto = this.usuario.getUsuarioDepartamentoList().get(0).getDepartamento();
            this.tipo = TIPO_USUARIO.JEFE;
        } else {
            this.dpto = null;
        }

        if (!this.usuario.getUsuarioProgramaList().isEmpty()) {
            this.programa = this.usuario.getUsuarioProgramaList().get(0).getPrograma();
            this.tipo = TIPO_USUARIO.COORDINADOR;
        } else {
            this.programa = null;
        }
    }

    /**
     * Dispone el despliegue para mostarr la contraseña
     */
    public void mostrarModificarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.campoContrasena = false;
        this.contrasena = "";
        requestContext.update("UsuarioEditForm");
    }

    /**
     * Cancela la edición de la contraseña
     */
    public void cancelarActualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.campoContrasena = true;
        this.contrasena = "";
        requestContext.update("UsuarioEditForm");
    }

    /**
     * Actualiza la contraseña
     */
    public void actualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        this.campoContrasena = true;
        System.out.println("contasena " + contrasena);
        this.usuario.setUsucontrasena(Cifrar.sha256(this.contrasena));
        this.ejbUsuario.edit(this.usuario);

        requestContext.update("UsuarioEditForm");
    }

    /**
     * Obtiene la fecha de nacimiento del usuario
     *
     * @return
     */
    public String getFecha() {
        String fechaNacimiento = "";
        if (usuario.getUsufechanacimiento() != null) {
            fechaNacimiento = formatoFecha.format(usuario.getUsufechanacimiento());
        }

        return fechaNacimiento;
    }

    public void buscarUsuario() {
        this.items = ejbUsuario.buscarUsuarioEjb(this.datoBusqueda.toLowerCase());
    }

    /**
     *
     * convierte el archivo de la imagen a tipo byte
     *
     * @param file
     * @return
     */
    private byte[] inputStreamToByteArray(UploadedFile file) {
        byte[] imagen = null;
        if (file != null) {
            try {
                try (InputStream input = file.getInputstream()) {
                    imagen = RedimensionadorImagenes.redimensionar(input, 150);
                }

            } catch (Exception ex) {
            }
        }

        return imagen;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/BundleAdmin").getString("UsuarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/BundleAdmin").getString("UsuarioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/BundleAdmin").getString("UsuarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            usuario = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Usuario> getItems() {
        ejbUsuario.limpiarCache();
        buscarUsuario();
        return items;
    }

    public void cargarFoto(FileUploadEvent event) {
        file = event.getFile();
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (usuario != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(usuario);
                } else {
                    getFacade().remove(usuario);
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

    public Usuario getUsuario(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Usuario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Usuario> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public StreamedContent getImagenDefecto() {
        return Utilidades.getImagenPorDefecto("foto");
    }

    /**
     * Obtiene el flujo de la imagen para mostrar en la pantalla
     *
     * @return
     */
    public StreamedContent getImagenFlujo() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            {
                return new DefaultStreamedContent();
            }
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

    public StreamedContent getImagenFlujoEditar() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();

        String id = context.getExternalContext().getRequestParameterMap().get("idUsu");
        if (usuario.getUsufoto() == null) {
            return Utilidades.getImagenPorDefecto("foto");
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(usuario.getUsufoto()));
        }

    }

    public void mostraSubirFoto() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.campoFoto = false;
        requestContext.update("formEditarfoto");

    }

    /**
     * Imprime el rol con un nombre amigable, en los formularios de edicion y
     * registro.
     *
     * @param tipo
     * @return
     */
    public String descripcionTipo(int tipo) {
        switch (tipo) {
            case 0:
                return "administrador";
            case 1:
                return "administrador";
            case 2:
                return "coordinador";
            case 3:
                return "jefe";
            default:
                return "No se encontro rol";
        }
    }

    public void mostrarMensajeRegistrar() {
        if (rolActual == 1) {
            if (grupo.getGruid().equals("2")) {
                programaFacade.flush();
                programaFacade.limpiarCache();
                String coordinador = programaFacade.findByProgramaCoordinador(programa.getIdPrograma());
                if (coordinador != null) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación: ", "El usuario " + coordinador + " es el actual coordinador del programa ¿desea cambiar esto?"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nota: ", "Al realizar este cambio el usuario " + coordinador + " será deshabilitado y no podrá acceder al sistema; para revertir este cambio    puede dirigirse a la edición del usuario " + coordinador));
                    context.execute("PF('HabilitarRolDialog').show()");
                }
            }
            if (grupo.getGruid().equals("3")) {
                String jefe = departamentoFacade.findByDepartamentoJefe(dpto.getIdDepartamento());
                if (jefe != null) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "El usuario " + jefe + " es el actual jefe del departamento ¿desea cambiar esto?"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nota: ", "Al realizar este cambio el usuario " + jefe + " será deshabilitado y no podrá acceder al sistema; para revertir este cambio    puede dirigirse a la edición del usuario " + jefe));
                    context.execute("PF('HabilitarRolDialog').show()");
                }
            }
        }
    }

    public void mostrarMensajeEditar() {
        if (rolActual == 1) {
            if (grupo.getGruid().equals("2")) {
                programaFacade.flush();
                programaFacade.limpiarCache();
                programa.getIdPrograma();
                String coordinador = programaFacade.findByProgramaCoordinador(programa.getIdPrograma());
                if (coordinador != null && !coordinador.equals(usuario.getUsunombreusuario())) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación: ", "El usuario " + coordinador + " es el actual coordinador del programa ¿desea cambiar esto?"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nota: ", "Al realizar este cambio el usuario " + coordinador + " será deshabilitado y no podrá acceder al sistema; para revertir este cambio    puede dirigirse a la edición del usuario " + coordinador));
                    context.execute("PF('HabilitarRolDialog').show()");
                }
            }
            if (grupo.getGruid().equals("3")) {
                String jefe = departamentoFacade.findByDepartamentoJefe(dpto.getIdDepartamento());
                if (jefe != null && !jefe.equals(usuario.getUsunombreusuario())) {
                    RequestContext context = RequestContext.getCurrentInstance();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Confirmación", "El usuario " + jefe + " es el actual jefe del departamento ¿desea cambiar esto?"));
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Nota: ", "Al realizar este cambio el usuario " + jefe + " será deshabilitado y no podrá acceder al sistema; para revertir este cambio    puede dirigirse a la edición del usuario " + jefe));
                    context.execute("PF('HabilitarRolDialog').show()");
                }
            }
        }
    }

    public void habilitarRol() {
        if (rolActual == 1) {
            if (grupo.getGruid().equals("1")) {
                for (UsuarioPrograma up : this.usuario.getUsuarioProgramaList()) {
                    usuarioProgramaFacade.remove(up);
                }
                for (UsuarioDepartamento ud : this.usuario.getUsuarioDepartamentoList()) {
                    usuarioDepartamentoFacade.remove(ud);
                }
            }
            if (grupo.getGruid().equals("2")) {
                limpiarJefeCoordinador();
                List<UsuarioPrograma> programas = new ArrayList<>();
                UsuarioProgramaPK usuarioProgramaPK = new UsuarioProgramaPK(usuario.getUsuid(), programa.getIdPrograma());
                UsuarioPrograma up = new UsuarioPrograma(usuarioProgramaPK);
                up.setNombreUsuario(usuario.getUsunombreusuario());
                up.setPrograma(programa);
                up.setUsuario(usuario);
                programas.add(up);
                usuario.setUsuarioProgramaList(programas);
                usuarioProgramaFacade.create(up);
            }
            if (grupo.getGruid().equals("3")) {
                limpiarJefeCoordinador();
                List<UsuarioDepartamento> departamentos = new ArrayList<>();
                UsuarioDepartamentoPK usuarioDepartamentoPK = new UsuarioDepartamentoPK(usuario.getUsuid(), dpto.getIdDepartamento());
                UsuarioDepartamento ud = new UsuarioDepartamento(usuarioDepartamentoPK);
                ud.setNombreUsuario(usuario.getUsunombreusuario());
                ud.setDepartamento(dpto);
                ud.setUsuario(usuario);
                departamentos.add(ud);
                usuario.setUsuarioDepartamentoList(departamentos);
                usuarioDepartamentoFacade.create(ud);
            }
            RequestContext requestContext = RequestContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "El usuario se habilitó con éxito.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            requestContext.update("msg");
            requestContext.update("UsuarioEditForm");
        } else if (rolActual == 0) {
            deshabilitarRol();
        }
    }

    public void limpiarJefeCoordinador() {
        this.usuario.setUsuestado(true);
        deshabilitarRolAnterior();
        for (UsuarioPrograma up : this.usuario.getUsuarioProgramaList()) {
            usuarioProgramaFacade.remove(up);
        }
        for (UsuarioDepartamento ud : this.usuario.getUsuarioDepartamentoList()) {
            usuarioDepartamentoFacade.remove(ud);
        }
    }

    public void cancelarRolActual() {
        rolActual = 0;
    }

    /**
     * Refresca la foto de un usuario
     *
     * @param event
     */
    public void actualizarFoto(FileUploadEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        UploadedFile file = event.getFile();
        usuario.setUsufoto(inputStreamToByteArray(file));
        this.ejbUsuario.edit(usuario);
        this.campoFoto = true;
        System.out.println("Actualizar foto file = null");
        file = null;
        requestContext.update(":formEditarfoto:panel");
        requestContext.update(":UsuarioListForm:datalist");

    }

    /**
     * Cancela la edciión de un usuario
     */
    public void cancelarEditarUsuario() {
        this.usuario = new Usuario();
        this.fotoDefecto = true;
        usuario.setUsugenero('M');
        this.cargo = new Cargo();
        this.grupo = new Grupo();
        this.campoFoto = true;
        ejbUsuario.limpiarCache();
        this.items = ejbUsuario.findAll();
        this.campoContrasena = true;
    }

    /**
     * Cancela el registro de un usuario
     */
    public void cancelarRegistroUsuario() {
        this.usuario = new Usuario();
        this.fotoDefecto = true;
        usuario.setUsugenero('M');
        this.cargo = new Cargo();
        this.grupo = new Grupo();
        this.campoFoto = true;
        ejbUsuario.limpiarCache();
        this.items = ejbUsuario.findAll();
        this.campoContrasena = true;

        System.out.println("--> cancelar registro");
        this.establecerFotoPorDefecto();
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("formfoto");
        requestContext.execute("PF('UsuarioCreateDialog').hide()");
        requestContext.execute("$('#UsuarioCreateForm').trigger('reset')");
        requestContext.execute("$('#formfoto').trigger('reset');");
    }

    public DefaultStreamedContent getMiImagen() {
        convertirBytesAImagen();
        /*if(miImagen==null)
            miImagen = Utilidades.getImagenPorDefecto("foto");*/
        return miImagen;
    }

    public void convertirBytesAImagen() {
        if (imagen != null) {
            InputStream is = new ByteArrayInputStream((byte[]) imagen);
            miImagen = new DefaultStreamedContent(is, "image/png");
        }
    }

    public void imagenPorDefecto() {
        imagen = null;
    }

    /**
     * Convierte una imagen cargada en un tipo byte
     *
     * @param event
     */
    public void convertirImagenABytes(FileUploadEvent event) {
        try {

            String type = event.getFile().getContentType();
            System.out.println(type);
            System.out.println("" + !type.equals("image/png"));
            if (!type.equals("image/png") && !type.equals("image/jpeg") && !type.equals("image/jpg")) {
                FacesMessage msg = new FacesMessage("Información", "El formato de la imagen debe ser png, jpeg o jpg");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            fotoDefecto = false;
            uploadedFile = event.getFile();
            InputStream is = event.getFile().getInputstream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;) {
                os.write(buffer, 0, len);
            }

            os.flush();
            imagen = os.toByteArray();
            usuario.setUsufoto(imagen);
            convertirBytesAImagen();
        } catch (IOException e) {

        }
    }

    public StreamedContent getImagen(Usuario usuario) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        FacesContext context = FacesContext.getCurrentInstance();

        String id = context.getExternalContext().getRequestParameterMap().get("idUsu");
        if (usuario.getUsufoto() == null) {
            return Utilidades.getImagenPorDefecto("foto");
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(usuario.getUsufoto()));
        }

    }

    /**
     * Limpia el formulario del registro del usuario
     */
    public void limpiarRegistrarUsuario() {
        this.usuario = new Usuario();
        usuario.setUsugenero('M');
        this.cargo = new Cargo();
        this.grupo = new Grupo();
        this.campoFoto = true;
        ejbUsuario.limpiarCache();
        this.items = ejbUsuario.findAll();
        this.campoContrasena = true;
        this.miImagen = null;
        this.imagen = null;
        this.fotoDefecto = true;
        this.tipo = TIPO_USUARIO.ADMIN;
    }

    public Date getFechaHoy() {
        Date min = new Date();
        min.setYear(min.getYear() - 18);
        min.setMonth(11);
        min.setDate(31);
        return min;
    }

    public TIPO_USUARIO getTipo() {
        return tipo;
    }

    public void desactivarRol(long id) {
        Usuario usuario = ejbUsuario.buscarPorIdUsuario(id).get(0);
        usuario.setUsuestado(new Boolean(false));
        ejbUsuario.edit(usuario);
    }

    public void establecerFotoPorDefecto() {
        System.out.println("establecer foto por defecto");
        this.fotoDefecto = true;
    }

    public TIPO_USUARIO getTipoAdmin() {
        return TIPO_USUARIO.ADMIN;
    }

    public TIPO_USUARIO getTipoCoordinador() {
        return TIPO_USUARIO.COORDINADOR;
    }

    public TIPO_USUARIO getTipoJefe() {
        return TIPO_USUARIO.JEFE;
    }

    public void setTipoUsuario() {
        switch (grupo.getGruid()) {
            case "1":
                this.tipo = TIPO_USUARIO.ADMIN;
                break;
            case "2":
                this.tipo = TIPO_USUARIO.COORDINADOR;
                break;
            case "3":
                this.tipo = TIPO_USUARIO.JEFE;
                break;
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("UsuarioCreateForm");
    }

    public void setTipoUsuarioEditar() {
        System.out.println("SETEDITAR");
        switch (grupo.getGruid()) {
            case "1":
                this.tipo = TIPO_USUARIO.ADMIN;
                break;
            case "2":
                this.tipo = TIPO_USUARIO.COORDINADOR;
                break;
            case "3":
                this.tipo = TIPO_USUARIO.JEFE;
                break;
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("UsuarioEditForm");
        requestContext.update("UsuarioRolForm");
    }

    public void setTipoUsuarioEditarRol() {
        switch (grupo.getGruid()) {
            case "1":
                this.tipo = TIPO_USUARIO.ADMIN;
                break;
            case "2":
                this.tipo = TIPO_USUARIO.COORDINADOR;
                break;
            case "3":
                this.tipo = TIPO_USUARIO.JEFE;
                break;
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("UsuarioRolForm");
    }

    public Departamento getDpto() {
        if (dpto == null) {
            return deptoNulo;
        }
        return dpto;
    }

    public void setDpto(Departamento dpto) {
        this.dpto = dpto;

    }

    @FacesConverter(forClass = Usuario.class)
    public static class UsuarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UsuarioController controller = (UsuarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioController");
            return controller.getUsuario(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Usuario) {
                Usuario o = (Usuario) object;
                return getStringKey(o.getUsuid());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Usuario.class.getName()});
                return null;
            }
        }

    }

}
