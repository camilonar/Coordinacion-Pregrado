/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.classMetadatos;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Andrés
 */
public class MetadatosAntepoyecto {
    
    private String profesor;
    private String titulo;
    private String fecha;
    private String viabilidad;
    private String actaAprobacion;
    private String nombreEstudiante1;
    private String nombreEstudiante2;
    private Date date3;
    

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getActaAprobacion() {
        return actaAprobacion;
    }

    public void setActaAprobacion(String actaAprobacion) {
        this.actaAprobacion = actaAprobacion;
    }

    public String getNombreEstudiante1() {
        return nombreEstudiante1;
    }

    public void setNombreEstudiante1(String nombreEstudiante1) {
        this.nombreEstudiante1 = nombreEstudiante1;
    }

    public String getNombreEstudiante2() {
        return nombreEstudiante2;
    }

    public void setNombreEstudiante2(String nombreEstudiante2) {
        this.nombreEstudiante2 = nombreEstudiante2;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public Date getDate3() 
    {
        System.out.println("en metodo getDate3 "+date3);
        return date3;
    }
 
    public void setDate3(Date date3) 
    {
        System.out.println("en metodo setDate3 "+date3);
        this.date3 = date3;
    }

    public String getFecha() 
    {
        return fecha;
    }

    public void setFecha(String fecha) 
    {
        this.fecha = fecha;
    }

    public String getViabilidad() 
    {
        return viabilidad;
    }

    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }
    
    
    
    
}
