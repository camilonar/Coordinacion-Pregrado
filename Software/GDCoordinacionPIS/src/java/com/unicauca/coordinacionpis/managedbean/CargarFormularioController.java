/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;


@Named("cargarFormulariosController")
@SessionScoped
public class CargarFormularioController implements Serializable {
    /**
     * Ruta actual del usuario de la app
     */
    private String ruta;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public CargarFormularioController() {
        
    }
    /**
     * Dirige a la GUI de oferta academica
     */
    public void cargarGestionOfertaAcademica() {
        this.ruta = "/coordinador/OfertaAcademica/ofertasAcademicas.xhtml";
    }
    /**
     * Dirige a la GUI de registrar departamento
     */
    public void cargarRegistrarDepartamento() {
        this.ruta = "/administrador/departamento/listarDepartamento.xhtml";
    }
    /**
     * Dirige a la GUI de registrar materia
     */
    public void cargarRegistrarMateria() {
        this.ruta = "/administrador/materia/ListarMaterias.xhtml";
    }
    /**
     * Dirige a la GUI de ver plan de estudio
     */
    public void cargarPlanesdeEstudio() {
        this.ruta = "/coordinador/PlandeEstudio/PlandeEstudio.xhtml";
    }
    /**
     * Dirige a la GUI de gestionar anteproyecto
     */
    public void cargarGestionAnteproyecto() {
        this.ruta = "/coordinador/anteproyecto/FormatoA/formatoA.xhtml";
    }

    /**
     * Dirige a la GUI de listar la oferta academica
     */
    public void cargarListaOfertaAcademica() {
        this.ruta = "/jefe/OfertaAcademica/ofertasAcademicas.xhtml";
    }
    /**
     * Dirige a la GUI de ver el perfil del usuario logueado
     */
    public void cargarPerfilUsuario() {
        this.ruta = "/perfilUsuario.xhtml";
    }
    
   /**
     * Dirige a la GUI de gestionar usuarios
     */
    public void cargarRegistrarUsuarios() {
        this.ruta = "/administrador/usuario/ListarUsuarios.xhtml";
    }
}
