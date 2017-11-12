/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author David
 */
@Embeddable
public class UsuarioDepartamentoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idUsuario")
    private long idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idDepartamento")
    private int idDepartamento;

    public UsuarioDepartamentoPK() {
    }

    public UsuarioDepartamentoPK(long idUsuario, int idDepartamento) {
        this.idUsuario = idUsuario;
        this.idDepartamento = idDepartamento;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUsuario;
        hash += (int) idDepartamento;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioDepartamentoPK)) {
            return false;
        }
        UsuarioDepartamentoPK other = (UsuarioDepartamentoPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idDepartamento != other.idDepartamento) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.UsuarioDepartamentoPK[ idUsuario=" + idUsuario + ", idDepartamento=" + idDepartamento + " ]";
    }
    
}
