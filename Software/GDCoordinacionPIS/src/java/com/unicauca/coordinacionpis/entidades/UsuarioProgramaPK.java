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
public class UsuarioProgramaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idUsuario")
    private long idUsuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idPrograma")
    private int idPrograma;

    public UsuarioProgramaPK() {
    }

    public UsuarioProgramaPK(long idUsuario, int idPrograma) {
        this.idUsuario = idUsuario;
        this.idPrograma = idPrograma;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idUsuario;
        hash += (int) idPrograma;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioProgramaPK)) {
            return false;
        }
        UsuarioProgramaPK other = (UsuarioProgramaPK) object;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        if (this.idPrograma != other.idPrograma) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.UsuarioProgramaPK[ idUsuario=" + idUsuario + ", idPrograma=" + idPrograma + " ]";
    }
    
}
