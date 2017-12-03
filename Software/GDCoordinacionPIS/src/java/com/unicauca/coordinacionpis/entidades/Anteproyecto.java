/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    , @NamedQuery(name = "Anteproyecto.findByFechaAnteproyecto", query = "SELECT a FROM Anteproyecto a WHERE a.fechaAnteproyecto = :fechaAnteproyecto")
    , @NamedQuery(name = "Anteproyecto.findByBusquedaTitulo", query = "SELECT a FROM Anteproyecto a WHERE a.tituloAnteproyecto LIKE :busqueda")
    , @NamedQuery(name = "Anteproyecto.findByProgramaAndTitulo", query = "SELECT a FROM Anteproyecto a WHERE a.programaAnteproyecto = :programa AND a.tituloAnteproyecto LIKE :busqueda ORDER BY a.fechaAnteproyecto DESC ")
    , @NamedQuery(name = "Anteproyecto.countFindByProgramaAndTitulo", query = "SELECT count(a.idAnteproyecto) FROM Anteproyecto a WHERE a.programaAnteproyecto = :programa AND a.tituloAnteproyecto LIKE :busqueda ")
})
public class Anteproyecto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAnteproyecto")
    private Integer idAnteproyecto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "tituloAnteproyecto")
    private String tituloAnteproyecto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaAnteproyecto")
    @Temporal(TemporalType.DATE)
    private Date fechaAnteproyecto;
    @JoinTable(name = "codirector", joinColumns = {
        @JoinColumn(name = "anteproyecto", referencedColumnName = "idAnteproyecto")}, inverseJoinColumns = {
        @JoinColumn(name = "profesor", referencedColumnName = "idProfesor")})
    @ManyToMany
    private List<Profesor> profesorList;
    @JoinTable(name = "estudiante_anteproyecto", joinColumns = {
        @JoinColumn(name = "anteproyecto", referencedColumnName = "idAnteproyecto")}, inverseJoinColumns = {
        @JoinColumn(name = "estudiante", referencedColumnName = "idEstudiante")})
    @ManyToMany
    private List<Estudiante> estudianteList;
    @JoinColumn(name = "directorAnteproyecto", referencedColumnName = "idProfesor")
    @ManyToOne(optional = false)
    private Profesor directorAnteproyecto;
    @JoinColumn(name = "programaAnteproyecto", referencedColumnName = "idPrograma")
    @ManyToOne(optional = false)
    private Programa programaAnteproyecto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoFormatoA")
    private List<Formatoa> formatoaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoFormatoC")
    private List<Formatoc> formatocList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "anteproyectoFormatoB")
    private List<Formatob> formatobList;

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
    public List<Profesor> getProfesorList() {
        return profesorList;
    }

    public void setProfesorList(List<Profesor> profesorList) {
        this.profesorList = profesorList;
    }

    @XmlTransient
    public List<Estudiante> getEstudianteList() {
        return estudianteList;
    }

    public void setEstudianteList(List<Estudiante> estudianteList) {
        this.estudianteList = estudianteList;
    }

    public Profesor getDirectorAnteproyecto() {
        return directorAnteproyecto;
    }

    public void setDirectorAnteproyecto(Profesor directorAnteproyecto) {
        this.directorAnteproyecto = directorAnteproyecto;
    }

    public Programa getProgramaAnteproyecto() {
        return programaAnteproyecto;
    }

    public void setProgramaAnteproyecto(Programa programaAnteproyecto) {
        this.programaAnteproyecto = programaAnteproyecto;
    }

    @XmlTransient
    public List<Formatoa> getFormatoaList() {
        return formatoaList;
    }

    public void setFormatoaList(List<Formatoa> formatoaList) {
        this.formatoaList = formatoaList;
    }

    @XmlTransient
    public List<Formatoc> getFormatocList() {
        return formatocList;
    }

    public void setFormatocList(List<Formatoc> formatocList) {
        this.formatocList = formatocList;
    }

    @XmlTransient
    public List<Formatob> getFormatobList() {
        return formatobList;
    }

    public void setFormatobList(List<Formatob> formatobList) {
        this.formatobList = formatobList;
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
