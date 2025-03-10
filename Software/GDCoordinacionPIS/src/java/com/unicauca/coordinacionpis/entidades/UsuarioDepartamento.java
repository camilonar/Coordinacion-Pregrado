/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que relaciona al Usuario con el departamento que dirige
 * @author David
 */
@Entity
@Table(name = "usuario_departamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioDepartamento.findAll", query = "SELECT u FROM UsuarioDepartamento u")
    , @NamedQuery(name = "UsuarioDepartamento.findByIdUsuario", query = "SELECT u FROM UsuarioDepartamento u WHERE u.usuarioDepartamentoPK.idUsuario = :idUsuario")
    , @NamedQuery(name = "UsuarioDepartamento.findByIdDepartamento", query = "SELECT u FROM UsuarioDepartamento u WHERE u.usuarioDepartamentoPK.idDepartamento = :idDepartamento")
    , @NamedQuery(name = "UsuarioDepartamento.findByNombreUsuario", query = "SELECT u FROM UsuarioDepartamento u WHERE u.nombreUsuario = :nombreUsuario")})
public class UsuarioDepartamento implements Serializable {

    /**
     * Versión de la base de datos
     */
    private static final long serialVersionUID = 1L;
    /**
     * Llave primaria de la entidad
     */
    @EmbeddedId
    protected UsuarioDepartamentoPK usuarioDepartamentoPK;
    /**
     * Nombre de usuario del usuario asociado
     */
    @Size(max = 75)
    @Column(name = "nombreUsuario")
    private String nombreUsuario;
    /**
     * Departamento asociado
     */
    @JoinColumn(name = "idDepartamento", referencedColumnName = "id_departamento", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Departamento departamento;
    /**
     * Usuario asociado
     */
    @JoinColumn(name = "idUsuario", referencedColumnName = "USUID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UsuarioDepartamento() {
    }

    public UsuarioDepartamento(UsuarioDepartamentoPK usuarioDepartamentoPK) {
        this.usuarioDepartamentoPK = usuarioDepartamentoPK;
    }

    public UsuarioDepartamento(long idUsuario, int idDepartamento) {
        this.usuarioDepartamentoPK = new UsuarioDepartamentoPK(idUsuario, idDepartamento);
    }

    public UsuarioDepartamentoPK getUsuarioDepartamentoPK() {
        return usuarioDepartamentoPK;
    }

    public void setUsuarioDepartamentoPK(UsuarioDepartamentoPK usuarioDepartamentoPK) {
        this.usuarioDepartamentoPK = usuarioDepartamentoPK;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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
        hash += (usuarioDepartamentoPK != null ? usuarioDepartamentoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDepartamento)) {
            return false;
        }
        UsuarioDepartamento other = (UsuarioDepartamento) object;
        if ((this.usuarioDepartamentoPK == null && other.usuarioDepartamentoPK != null) || (this.usuarioDepartamentoPK != null && !this.usuarioDepartamentoPK.equals(other.usuarioDepartamentoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.UsuarioDepartamento[ usuarioDepartamentoPK=" + usuarioDepartamentoPK + " ]";
    }
    
}
