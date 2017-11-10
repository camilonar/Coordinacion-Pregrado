/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author David
 */
@Entity
@Table(name = "profesor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Profesor.findAll", query = "SELECT p FROM Profesor p")
    , @NamedQuery(name = "Profesor.findByIdProfesor", query = "SELECT p FROM Profesor p WHERE p.idProfesor = :idProfesor")
    , @NamedQuery(name = "Profesor.findByNombreProfesor", query = "SELECT p FROM Profesor p WHERE p.nombreProfesor = :nombreProfesor")
    , @NamedQuery(name = "Profesor.findByUniversidadProfesor", query = "SELECT p FROM Profesor p WHERE p.universidadProfesor = :universidadProfesor")
    , @NamedQuery(name = "Profesor.findByExternoProfesor", query = "SELECT p FROM Profesor p WHERE p.externoProfesor = :externoProfesor")})
public class Profesor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idProfesor")
    private Integer idProfesor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreProfesor")
    private String nombreProfesor;
    @Size(max = 50)
    @Column(name = "universidadProfesor")
    private String universidadProfesor;
    @Column(name = "externoProfesor")
    private Boolean externoProfesor;
    @ManyToMany(mappedBy = "profesorList")
    private List<Anteproyecto> anteproyectoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "directorAnteproyecto")
    private List<Anteproyecto> anteproyectoList1;
    @JoinColumn(name = "programaProfesor", referencedColumnName = "idPrograma")
    @ManyToOne(optional = false)
    private Programa programaProfesor;

    public Profesor() {
    }

    public Profesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Profesor(Integer idProfesor, String nombreProfesor) {
        this.idProfesor = idProfesor;
        this.nombreProfesor = nombreProfesor;
    }

    public Integer getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(Integer idProfesor) {
        this.idProfesor = idProfesor;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getUniversidadProfesor() {
        return universidadProfesor;
    }

    public void setUniversidadProfesor(String universidadProfesor) {
        this.universidadProfesor = universidadProfesor;
    }

    public Boolean getExternoProfesor() {
        return externoProfesor;
    }

    public void setExternoProfesor(Boolean externoProfesor) {
        this.externoProfesor = externoProfesor;
    }

    @XmlTransient
    public List<Anteproyecto> getAnteproyectoList() {
        return anteproyectoList;
    }

    public void setAnteproyectoList(List<Anteproyecto> anteproyectoList) {
        this.anteproyectoList = anteproyectoList;
    }

    @XmlTransient
    public List<Anteproyecto> getAnteproyectoList1() {
        return anteproyectoList1;
    }

    public void setAnteproyectoList1(List<Anteproyecto> anteproyectoList1) {
        this.anteproyectoList1 = anteproyectoList1;
    }

    public Programa getProgramaProfesor() {
        return programaProfesor;
    }

    public void setProgramaProfesor(Programa programaProfesor) {
        this.programaProfesor = programaProfesor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfesor != null ? idProfesor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Profesor)) {
            return false;
        }
        Profesor other = (Profesor) object;
        if ((this.idProfesor == null && other.idProfesor != null) || (this.idProfesor != null && !this.idProfesor.equals(other.idProfesor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Profesor[ idProfesor=" + idProfesor + " ]";
    }
    
}
