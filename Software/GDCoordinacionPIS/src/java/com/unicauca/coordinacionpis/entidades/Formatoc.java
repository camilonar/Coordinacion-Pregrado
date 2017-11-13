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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "formatoc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formatoc.findAll", query = "SELECT f FROM Formatoc f")
    , @NamedQuery(name = "Formatoc.findByIdFormatoC", query = "SELECT f FROM Formatoc f WHERE f.idFormatoC = :idFormatoC")
    , @NamedQuery(name = "Formatoc.findByClaveFormatoC", query = "SELECT f FROM Formatoc f WHERE f.claveFormatoC = :claveFormatoC")})
public class Formatoc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFormatoC")
    private Integer idFormatoC;
    @Size(max = 45)
    @Column(name = "claveFormatoC")
    private String claveFormatoC;
    @JoinColumn(name = "anteproyectoFormatoC", referencedColumnName = "idAnteproyecto")
    @ManyToOne(optional = false)
    private Anteproyecto anteproyectoFormatoC;

    public Formatoc() {
    }

    public Formatoc(Integer idFormatoC) {
        this.idFormatoC = idFormatoC;
    }

    public Integer getIdFormatoC() {
        return idFormatoC;
    }

    public void setIdFormatoC(Integer idFormatoC) {
        this.idFormatoC = idFormatoC;
    }

    public String getClaveFormatoC() {
        return claveFormatoC;
    }

    public void setClaveFormatoC(String claveFormatoC) {
        this.claveFormatoC = claveFormatoC;
    }

    public Anteproyecto getAnteproyectoFormatoC() {
        return anteproyectoFormatoC;
    }

    public void setAnteproyectoFormatoC(Anteproyecto anteproyectoFormatoC) {
        this.anteproyectoFormatoC = anteproyectoFormatoC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormatoC != null ? idFormatoC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formatoc)) {
            return false;
        }
        Formatoc other = (Formatoc) object;
        if ((this.idFormatoC == null && other.idFormatoC != null) || (this.idFormatoC != null && !this.idFormatoC.equals(other.idFormatoC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Formatoc[ idFormatoC=" + idFormatoC + " ]";
    }
    
}
