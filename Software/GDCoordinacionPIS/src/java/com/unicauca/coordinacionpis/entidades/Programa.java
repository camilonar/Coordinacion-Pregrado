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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa a un programa de la Universidad
 * @author David
 */
@Entity
@Table(name = "programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Programa.findAll", query = "SELECT p FROM Programa p")
    , @NamedQuery(name = "Programa.findByIdPrograma", query = "SELECT p FROM Programa p WHERE p.idPrograma = :idPrograma")
    , @NamedQuery(name = "Programa.findByNombrePrograma", query = "SELECT p FROM Programa p WHERE p.nombrePrograma = :nombrePrograma")})
public class Programa implements Serializable {

    /**
     * Versión de la base de datos
     */
    private static final long serialVersionUID = 1L;
    /**
     * Identificador del programa en la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idPrograma")
    private Integer idPrograma;
    /**
     * Nombre del programa
     */
    @Size(max = 200)
    @Column(name = "nombrePrograma")
    private String nombrePrograma;
    /**
     * Lista de estudiantes del programa
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaEstudiante")
    private List<Estudiante> estudianteList;
    /**
     * Lista de anteproyectos asociados al programa
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaAnteproyecto")
    private List<Anteproyecto> anteproyectoList;
    /**
     * Facultad en la que se dicta el programa
     */
    @JoinColumn(name = "idfacultad", referencedColumnName = "idfacultad")
    @ManyToOne(optional = false)
    private Facultad idfacultad;
    /**
     * Lista de profesores del programa
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programaProfesor")
    private List<Profesor> profesorList;
    /**
     * Lista de coordinadores activos e inactivos del programa
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programa")
    private List<UsuarioPrograma> usuarioProgramaList;

    public Programa() {
    }

    public Programa(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Integer getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Integer idPrograma) {
        this.idPrograma = idPrograma;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    @XmlTransient
    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    @XmlTransient
    public List<Anteproyecto> getAnteproyectoList() {
        return anteproyectoList;
    }

    public void setAnteproyectoList(List<Anteproyecto> anteproyectoList) {
        this.anteproyectoList = anteproyectoList;
    }

    public Facultad getIdfacultad() {
        return idfacultad;
    }

    public void setIdfacultad(Facultad idfacultad) {
        this.idfacultad = idfacultad;
    }

    @XmlTransient
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @XmlTransient
    public List<UsuarioPrograma> getUsuarioProgramaList() {
        return usuarioProgramaList;
    }

    public void setUsuarioProgramaList(List<UsuarioPrograma> usuarioProgramaList) {
        this.usuarioProgramaList = usuarioProgramaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPrograma != null ? idPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Programa)) {
            return false;
        }
        Programa other = (Programa) object;
        if ((this.idPrograma == null && other.idPrograma != null) || (this.idPrograma != null && !this.idPrograma.equals(other.idPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicauca.coordinacionpis.entidades.Programa[ idPrograma=" + idPrograma + " ]";
    }
    
}
