/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Daniela
 */
@Entity
@Table(name = "usuario_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioPrograma.findAll", query = "SELECT u FROM UsuarioPrograma u")
    , @NamedQuery(name = "UsuarioPrograma.findByIdUsuario", query = "SELECT u FROM UsuarioPrograma u WHERE u.usuarioProgramaPK.idUsuario = :idUsuario")
    , @NamedQuery(name = "UsuarioPrograma.findByIdPrograma", query = "SELECT u FROM UsuarioPrograma u WHERE u.usuarioProgramaPK.idPrograma = :idPrograma")
    , @NamedQuery(name = "UsuarioPrograma.findByNombreUsuario", query = "SELECT u FROM UsuarioPrograma u WHERE u.nombreUsuario = :nombreUsuario")})
public class UsuarioPrograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UsuarioProgramaPK usuarioProgramaPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    @JoinColumn(name = "idPrograma", referencedColumnName = "idPrograma", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Programa programa;
    @JoinColumn(name = "idUsuario", referencedColumnName = "USUID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UsuarioPrograma() {
    }

    public UsuarioPrograma(UsuarioProgramaPK usuarioProgramaPK) {
        this.usuarioProgramaPK = usuarioProgramaPK;
    }

    public UsuarioPrograma(UsuarioProgramaPK usuarioProgramaPK, String nombreUsuario) {
        this.usuarioProgramaPK = usuarioProgramaPK;
        this.nombreUsuario = nombreUsuario;
    }

    public UsuarioPrograma(long idUsuario, int idPrograma) {
        this.usuarioProgramaPK = new UsuarioProgramaPK(idUsuario, idPrograma);
    }

    public UsuarioProgramaPK getUsuarioProgramaPK() {
        return usuarioProgramaPK;
    }

    public void setUsuarioProgramaPK(UsuarioProgramaPK usuarioProgramaPK) {
        this.usuarioProgramaPK = usuarioProgramaPK;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioProgramaPK != null ? usuarioProgramaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPrograma)) {
            return false;
        }
        UsuarioPrograma other = (UsuarioPrograma) object;
        if ((this.usuarioProgramaPK == null && other.usuarioProgramaPK != null) || (this.usuarioProgramaPK != null && !this.usuarioProgramaPK.equals(other.usuarioProgramaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.UsuarioPrograma[ usuarioProgramaPK=" + usuarioProgramaPK + " ]";
    }
    
}
