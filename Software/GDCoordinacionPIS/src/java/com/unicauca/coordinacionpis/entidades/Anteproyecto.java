/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "anteproyecto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Anteproyecto.findAll", query = "SELECT a FROM Anteproyecto a")
    , @NamedQuery(name = "Anteproyecto.findByIdAnteproyecto", query = "SELECT a FROM Anteproyecto a WHERE a.idAnteproyecto = :idAnteproyecto")
    , @NamedQuery(name = "Anteproyecto.findByTituloAnteproyecto", query = "SELECT a FROM Anteproyecto a WHERE a.tituloAnteproyecto = :tituloAnteproyecto")
    , @NamedQuery(name = "Anteproyecto.findByFechaAnteproyecto", query = "SELECT a FROM Anteproyecto a WHERE a.fechaAnteproyecto = :fechaAnteproyecto")})
public class Anteproyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAnteproyecto")
    private Integer idAnteproyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "tituloAnteproyecto")
    private String tituloAnteproyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaAnteproyecto")
    @Temporal(TemporalType.DATE)
    private Date fechaAnteproyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoFormatoA")
    private Collection<Formatoa> formatoaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoFormatoC")
    private Collection<Formatoc> formatocCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoFormatoB")
    private Collection<Formatob> formatobCollection;

    public Anteproyecto() {
    }

    public Anteproyecto(Integer idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public Anteproyecto(Integer idAnteproyecto, String tituloAnteproyecto, Date fechaAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
        this.tituloAnteproyecto = tituloAnteproyecto;
        this.fechaAnteproyecto = fechaAnteproyecto;
    }

    public Integer getIdAnteproyecto() {
        return idAnteproyecto;
    }

    public void setIdAnteproyecto(Integer idAnteproyecto) {
        this.idAnteproyecto = idAnteproyecto;
    }

    public String getTituloAnteproyecto() {
        return tituloAnteproyecto;
    }

    public void setTituloAnteproyecto(String tituloAnteproyecto) {
        this.tituloAnteproyecto = tituloAnteproyecto;
    }

    public Date getFechaAnteproyecto() {
        return fechaAnteproyecto;
    }

    public void setFechaAnteproyecto(Date fechaAnteproyecto) {
        this.fechaAnteproyecto = fechaAnteproyecto;
    }

    @XmlTransient
    public Collection<Formatoa> getFormatoaCollection() {
        return formatoaCollection;
    }

    public void setFormatoaCollection(Collection<Formatoa> formatoaCollection) {
        this.formatoaCollection = formatoaCollection;
    }

    @XmlTransient
    public Collection<Formatoc> getFormatocCollection() {
        return formatocCollection;
    }

    public void setFormatocCollection(Collection<Formatoc> formatocCollection) {
        this.formatocCollection = formatocCollection;
    }

    @XmlTransient
    public Collection<Formatob> getFormatobCollection() {
        return formatobCollection;
    }

    public void setFormatobCollection(Collection<Formatob> formatobCollection) {
        this.formatobCollection = formatobCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnteproyecto != null ? idAnteproyecto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anteproyecto)) {
            return false;
        }
        Anteproyecto other = (Anteproyecto) object;
        if ((this.idAnteproyecto == null && other.idAnteproyecto != null) || (this.idAnteproyecto != null && !this.idAnteproyecto.equals(other.idAnteproyecto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Anteproyecto[ idAnteproyecto=" + idAnteproyecto + " ]";
    }
    
}
