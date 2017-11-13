/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
 * @author David
 */
@Entity
@Table(name = "formatoa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formatoa.findAll", query = "SELECT f FROM Formatoa f")
    , @NamedQuery(name = "Formatoa.findByIdFormatoA", query = "SELECT f FROM Formatoa f WHERE f.idFormatoA = :idFormatoA")
    , @NamedQuery(name = "Formatoa.findByClaveFormatoA", query = "SELECT f FROM Formatoa f WHERE f.claveFormatoA = :claveFormatoA")})
public class Formatoa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idFormatoA")
    private Integer idFormatoA;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "claveFormatoA")
    private String claveFormatoA;
    @JoinColumn(name = "anteproyectoFormatoA", referencedColumnName = "idAnteproyecto")
    @ManyToOne(optional = false)
    private Anteproyecto anteproyectoFormatoA;

    public Formatoa() {
    }

    public Formatoa(Integer idFormatoA) {
        this.idFormatoA = idFormatoA;
    }

    public Formatoa(Integer idFormatoA, String claveFormatoA) {
        this.idFormatoA = idFormatoA;
        this.claveFormatoA = claveFormatoA;
    }

    public Integer getIdFormatoA() {
        return idFormatoA;
    }

    public void setIdFormatoA(Integer idFormatoA) {
        this.idFormatoA = idFormatoA;
    }

    public String getClaveFormatoA() {
        return claveFormatoA;
    }

    public void setClaveFormatoA(String claveFormatoA) {
        this.claveFormatoA = claveFormatoA;
    }

    public Anteproyecto getAnteproyectoFormatoA() {
        return anteproyectoFormatoA;
    }

    public void setAnteproyectoFormatoA(Anteproyecto anteproyectoFormatoA) {
        this.anteproyectoFormatoA = anteproyectoFormatoA;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormatoA != null ? idFormatoA.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formatoa)) {
            return false;
        }
        Formatoa other = (Formatoa) object;
        if ((this.idFormatoA == null && other.idFormatoA != null) || (this.idFormatoA != null && !this.idFormatoA.equals(other.idFormatoA))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Formatoa[ idFormatoA=" + idFormatoA + " ]";
    }
    
}
