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
 * Entidad que representa a un Formato B guardado en el gestor documental
 * @author David
 */
@Entity
@Table(name = "formatob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Formatob.findAll", query = "SELECT f FROM Formatob f")
    , @NamedQuery(name = "Formatob.findByIdFormatoB", query = "SELECT f FROM Formatob f WHERE f.idFormatoB = :idFormatoB")
    , @NamedQuery(name = "Formatob.findByClaveFormatoB", query = "SELECT f FROM Formatob f WHERE f.claveFormatoB = :claveFormatoB")})
public class Formatob implements Serializable {

    /**
     * Versión de la base de datos
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del formato B en la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFormatoB")
    private Integer idFormatoB;
    /**
     * Clave generada por el gestor documental para el documento asociado
     */
    @Size(max = 45)
    @Column(name = "claveFormatoB")
    private String claveFormatoB;
    /**
     * Anteproyecto asociado al formato B
     */
    @JoinColumn(name = "anteproyectoFormatoB", referencedColumnName = "idAnteproyecto")
    @ManyToOne(optional = false)
    private Anteproyecto anteproyectoFormatoB;

    public Formatob() {
    }

    public Formatob(Integer idFormatoB) {
        this.idFormatoB = idFormatoB;
    }

    public Integer getIdFormatoB() {
        return idFormatoB;
    }

    public void setIdFormatoB(Integer idFormatoB) {
        this.idFormatoB = idFormatoB;
    }

    public String getClaveFormatoB() {
        return claveFormatoB;
    }

    public void setClaveFormatoB(String claveFormatoB) {
        this.claveFormatoB = claveFormatoB;
    }

    public Anteproyecto getAnteproyectoFormatoB() {
        return anteproyectoFormatoB;
    }

    public void setAnteproyectoFormatoB(Anteproyecto anteproyectoFormatoB) {
        this.anteproyectoFormatoB = anteproyectoFormatoB;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFormatoB != null ? idFormatoB.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Formatob)) {
            return false;
        }
        Formatob other = (Formatob) object;
        if ((this.idFormatoB == null && other.idFormatoB != null) || (this.idFormatoB != null && !this.idFormatoB.equals(other.idFormatoB))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Formatob[ idFormatoB=" + idFormatoB + " ]";
    }
    
}
