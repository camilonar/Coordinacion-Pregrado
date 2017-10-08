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

    @EJB
    private UsuarioDepartamentoFacade usuarioDepartamentoFacade;

    @EJB
    private UsuarioProgramaFacade usuarioProgramaFacade;

    @EJB
    private DepartamentoFacade departamentoFacade;

    @EJB
    private ProgramaFacade programaFacade;

    public enum TIPO_USUARIO{ADMIN, COORDINADOR, JEFE};
    
    @EJB
    private com.unicauca.coordinacionpis.sessionbean.UsuarioFacade ejbUsuario;
    @EJB
    private UsuariogrupoFacade ejbUsuarioGrupo;

    private List<Usuario> items = null;
    private Cargo cargo;
    private Grupo grupo;
    private List<Usuario> filtroBusqueda;

    private boolean campoFoto;
    private boolean campoContrasena;

    private String contrasena;
    private String datoBusqueda;

    private Usuario usuario;
    private UploadedFile file;

    private SimpleDateFormat formatoFecha;
    
    
    private byte[] imagen;
    private DefaultStreamedContent miImagen;
    private UploadedFile uploadedFile;
    
    private boolean fotoDefecto;
    
    private TIPO_USUARIO tipo;
    private Departamento dpto;
    private Programa programa;

    public UsuarioController() {
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
    }

    public Programa getPrograma() {
        if(programa == null)
        {
            programa = programaFacade.find(26); //Por defecto sistemas
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

    public void setDatoBusqueda(String datoBusqueda) {
        this.datoBusqueda = datoBusqueda;
    }

    public SimpleDateFormat getFormatoFecha() {
        return formatoFecha;
    }

    public void setFormatoFecha(SimpleDateFormat formatoFecha) {
        this.formatoFecha = formatoFecha;
    }

    public Usuario prepareCreate() {
        System.out.println("prepareCreate");
        usuario = new Usuario();
        usuario.setUsunombres("");
        this.file = null;
        if(!fotoDefecto)
        {
            this.establecerFotoPorDefecto();
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.update("formfoto");
        }
        this.fotoDefecto = true;
        this.limpiarRegistrarUsuario();
        
        this.tipo = TIPO_USUARIO.ADMIN;
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

    public void registrarUsuario() 
    {
        this.usuario.setUsucontrasena(Cifrar.sha256(this.usuario.getUsucontrasena()));
                
        if(!fotoDefecto)
        {
            this.usuario.setUsufoto(imagen);
        }
        else
        {
            this.usuario.setUsufoto(null);
        }
        usuario.setCarid(cargo);
        ejbUsuario.create(usuario);
        
        if(this.tipo == TIPO_USUARIO.COORDINADOR)
        {
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
        else if(this.tipo == TIPO_USUARIO.JEFE)
        {
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
        dpto = null;
        programa = null;
        
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
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Información","El usuario se registró con éxito.");
        FacesContext.getCurrentInstance().addMessage(null,msg);
        
       // requestContext.execute("PF('mensajeRegistroExitoso').show()");
        requestContext.update("msg");
        requestContext.update("formfoto");

    }
    
    public void editarUsuario() {
        usuario.setCarid(cargo);
        if(!fotoDefecto)
        {
            this.usuario.setUsufoto(imagen);
        }
        else
        {
            this.usuario.setUsufoto(null);
        }
        Usuariogrupo usuarioGrupo = new Usuariogrupo();
        UsuariogrupoPK usuarioGrupoPK = new UsuariogrupoPK();
        ejbUsuario.edit(usuario);
        
        for(UsuarioPrograma up: this.usuario.getUsuarioProgramaList())
        {
            usuarioProgramaFacade.remove(up);
        }
        for(UsuarioDepartamento ud: this.usuario.getUsuarioDepartamentoList())
        {
            usuarioDepartamentoFacade.remove(ud);
        }
        if(this.tipo == TIPO_USUARIO.COORDINADOR)
        {
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
        else if(this.tipo == TIPO_USUARIO.JEFE)
        {
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
        requestContext.execute("PF('UsuarioEditDialog').hide()");
        ejbUsuario.limpiarCache();
        items = ejbUsuario.findAll();
        usuario = new Usuario();
        usuario.setUsugenero('M');
      
        requestContext.execute("PF('UsuarioCreateDialog').hide()");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Información","El usuario se editó con éxito.");
        FacesContext.getCurrentInstance().addMessage(null,msg);
        
       // requestContext.execute("PF('mensajeRegistroExitoso').show()");
        requestContext.update("msg");
        requestContext.update("formfoto");
        requestContext.update("UsuarioListForm");
        
    }  

    public void seleccionarUsuarioEditar(Usuario usuario) {
        this.usuario = usuario;
        this.imagen = this.usuario.getUsufoto();
        if(this.imagen ==null)
        {
            this.miImagen = (DefaultStreamedContent) Utilidades.getImagenPorDefecto("foto");
            convertirBytesAImagen();
            System.out.println("seleccionarUsuarioEditar file = null");
            this.fotoDefecto = true;
        }
        else
        {
            this.fotoDefecto = false;
        }
        this.cargo = usuario.getCarid();
        this.grupo = ejbUsuarioGrupo.buscarPorNombreUsuarioObj(usuario.getUsunombreusuario()).getGrupo();
        this.tipo = TIPO_USUARIO.ADMIN;
        if(!this.usuario.getUsuarioDepartamentoList().isEmpty())
        {
            this.dpto = this.usuario.getUsuarioDepartamentoList().get(0).getDepartamento();
            this.tipo = TIPO_USUARIO.JEFE;
        }
        else
            this.dpto = null;
        
        if(!this.usuario.getUsuarioProgramaList().isEmpty())
        {
            this.programa = this.usuario.getUsuarioProgramaList().get(0).getPrograma();
            this.tipo = TIPO_USUARIO.COORDINADOR;
        }
        else
            this.programa =null;
    }

    public void seleccionarUsuarioVer(Usuario usuario) {
        this.usuario = usuario;
        this.imagen = this.usuario.getUsufoto();
        if(this.imagen ==null)
        {
            this.miImagen = (DefaultStreamedContent) Utilidades.getImagenPorDefecto("foto");
            convertirBytesAImagen();
        }
        
        this.cargo = usuario.getCarid();
        this.grupo = ejbUsuarioGrupo.buscarPorNombreUsuarioObj(usuario.getUsunombreusuario()).getGrupo();
        
        this.tipo = TIPO_USUARIO.ADMIN;
        if(!this.usuario.getUsuarioDepartamentoList().isEmpty())
        {
            this.dpto = this.usuario.getUsuarioDepartamentoList().get(0).getDepartamento();
            this.tipo = TIPO_USUARIO.JEFE;
        }
        else
            this.dpto = null;
        
        if(!this.usuario.getUsuarioProgramaList().isEmpty())
        {
            this.programa = this.usuario.getUsuarioProgramaList().get(0).getPrograma();
            this.tipo = TIPO_USUARIO.COORDINADOR;
        }
        else
            this.programa =null;
    }

    public void mostrarModificarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.campoContrasena = false;
        this.contrasena = "";
        requestContext.update("UsuarioEditForm");
    }

    public void cancelarActualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        this.campoContrasena = true;
        this.contrasena = "";
        requestContext.update("UsuarioEditForm");
    }

    public void actualizarContrasena() {
        RequestContext requestContext = RequestContext.getCurrentInstance();

        this.campoContrasena = true;
        System.out.println("contasena "+contrasena);
        this.usuario.setUsucontrasena(Cifrar.sha256(this.contrasena));
        this.ejbUsuario.edit(this.usuario);

        requestContext.update("UsuarioEditForm");
    }

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
        items = getFacade().findAll();
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
    
    public StreamedContent getImagenDefecto(){
        return Utilidades.getImagenPorDefecto("foto");
    }

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
    public void cancelarEditarUsuario(){
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
    public void convertirBytesAImagen()
    {
        if(imagen != null)
        {
            InputStream is = new ByteArrayInputStream((byte[]) imagen);
            miImagen = new DefaultStreamedContent(is, "image/png");
        }
    }
    
    public void imagenPorDefecto(){
        imagen=null;
    }
    
    public void convertirImagenABytes(FileUploadEvent event) {
        try
        {
            
            String type = event.getFile().getContentType();
            System.out.println(type);
            System.out.println(""+!type.equals("image/png"));
            if(!type.equals("image/png") && !type.equals("image/jpeg") &&!type.equals("image/jpg"))
            {
                FacesMessage msg = new FacesMessage("Información","El formato de la imagen debe ser png, jpeg o jpg");  
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
            fotoDefecto = false;
            uploadedFile = event.getFile();
            InputStream is = event.getFile().getInputstream();
            ByteArrayOutputStream os = new ByteArrayOutputStream();            
            byte[] buffer = new byte[0xFFFF];

            for (int len; (len = is.read(buffer)) != -1;)
            {
                os.write(buffer, 0, len);
            }

            os.flush();
            imagen = os.toByteArray();
            usuario.setUsufoto(imagen);
            convertirBytesAImagen();
        }
        catch(IOException e)
        {
            
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
    
    public void limpiarRegistrarUsuario()
    {
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
    public Date getFechaHoy()
    {
        Date min = new Date();
        min.setYear(min.getYear() - 18);
        min.setMonth(11);
        min.setDate(31);
        return min;
    }

    public TIPO_USUARIO getTipo() {
        return tipo;
    }
    
    
    
    public void establecerFotoPorDefecto()
    {
        System.out.println("establecer foto por defecto");
        this.fotoDefecto = true;
    }
    
    public TIPO_USUARIO getTipoAdmin()
    {
        return TIPO_USUARIO.ADMIN;
    }
        public TIPO_USUARIO getTipoCoordinador()
    {
        return TIPO_USUARIO.COORDINADOR;
    }
    public TIPO_USUARIO getTipoJefe()
    {
        return TIPO_USUARIO.JEFE;
    }
    public void setTipoUsuario()
    {
        switch(grupo.getGruid())
        {
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
    public void setTipoUsuarioEditar()
    {
        switch(grupo.getGruid())
        {
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
    }

    public Departamento getDpto() {
        if(dpto == null)
        {
            dpto = departamentoFacade.find(8);
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
