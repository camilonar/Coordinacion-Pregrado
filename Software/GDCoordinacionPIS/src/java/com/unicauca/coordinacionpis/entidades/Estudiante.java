/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa a un Estudiante de la Universidad
 * @author David
 */
@Entity
@Table(name = "estudiante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estudiante.findAll", query = "SELECT e FROM Estudiante e")
    , @NamedQuery(name = "Estudiante.findByIdEstudiante", query = "SELECT e FROM Estudiante e WHERE e.idEstudiante = :idEstudiante")
    , @NamedQuery(name = "Estudiante.findByCodigoEstudiante", query = "SELECT e FROM Estudiante e WHERE e.codigoEstudiante = :codigoEstudiante")
    , @NamedQuery(name = "Estudiante.findByNombreEstudiante", query = "SELECT e FROM Estudiante e WHERE e.nombreEstudiante = :nombreEstudiante")})
public class Estudiante implements Serializable {

    /**
     * Versión de la base de datos
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del estudiante
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idEstudiante")
    private Integer idEstudiante;
    /**
     * Código del estudiante
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codigoEstudiante")
    private String codigoEstudiante;
    /**
     * Nombre del estudiante
     */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombreEstudiante")
    private String nombreEstudiante;
    /**
     * Lista de anteproyectos en los que participa el estudiante
     */
    @ManyToMany(mappedBy = "estudianteList")
    private List<Anteproyecto> anteproyectoList;
    /**
     * Programa que cursa el estudiante
     */
    @JoinColumn(name = "programaEstudiante", referencedColumnName = "idPrograma")
    @ManyToOne(optional = false)
    private Programa programaEstudiante;

    public Estudiante() {
    }

    public Estudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Estudiante(Integer idEstudiante, String codigoEstudiante, String nombreEstudiante) {
        this.idEstudiante = idEstudiante;
        this.codigoEstudiante = codigoEstudiante;
        this.nombreEstudiante = nombreEstudiante;
    }

    public Integer getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Integer idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    @XmlTransient
    public List<Anteproyecto> getAnteproyectoList() {
        return anteproyectoList;
    }

    public void setAnteproyectoList(List<Anteproyecto> anteproyectoList) {
        this.anteproyectoList = anteproyectoList;
    }

    public Programa getProgramaEstudiante() {
        return programaEstudiante;
    }

    public void setProgramaEstudiante(Programa programaEstudiante) {
        this.programaEstudiante = programaEstudiante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstudiante != null ? idEstudiante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estudiante)) {
            return false;
        }
        Estudiante other = (Estudiante) object;
        if ((this.codigoEstudiante == null && other.codigoEstudiante != null) || (this.codigoEstudiante != null && !this.codigoEstudiante.equals(other.codigoEstudiante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Estudiante[ idEstudiante=" + idEstudiante + " ]";
    }
    
}
