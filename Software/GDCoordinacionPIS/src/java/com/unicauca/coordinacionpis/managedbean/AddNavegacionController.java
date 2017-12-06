/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.managedbean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 * Controlador utilizado para manejar la mina de pan, se utiliza para ubicarse
 * en la pagina actual.
 *
 * @author edwin
 */
@Named("addNavegacionController")
@SessionScoped
public class AddNavegacionController implements Serializable {

    /**
     * Menu de navegacion retornado en la vista
     */
    private MenuModel menuNavegacion;
    /**
     * Item que identifica el inicio "Home" a la barra miga de pan
     */
    DefaultMenuItem index;
    /**
     * Item que identifica la pagina numero 1 en la miga de pan
     */
    DefaultMenuItem miga1;
    /**
     * Item que identifica la pagina numero 2 en la miga de pan
     */
    DefaultMenuItem miga2;

    /**
     * Inicializa el modelo configurando estilos como tipo de letra y colores
     */
    public AddNavegacionController() {
        menuNavegacion = new DefaultMenuModel();
        index = new DefaultMenuItem();
        index.setStyle("cursor: default;text-decoration: none;");
        index.setValue("Gestión de departamentos o facultades");
        miga1 = new DefaultMenuItem();
        miga1.setStyle("cursor: default;text-decoration: none;");
        miga1.setValue("Gestión de departamentos o facultades");
        miga2 = new DefaultMenuItem();
        miga2.setStyle("cursor: default;text-decoration: none;");
        miga2.setValue("Gestión de departamentos o facultades");
    }

    /**
     * Metodo que retorna la miga de pan depende a la pagina que se visite
     *
     * @param tipo
     * @return Modelo
     */
    public MenuModel migaPrincipal(String tipo) {
        if (tipo.equals("1")) {
            this.menuNavegacion = new DefaultMenuModel();
            index.setValue("Index");
            miga1.setValue("Usuarios");
            miga2.setValue("Gestión de usuarios");

            this.menuNavegacion.addElement(index);
            this.menuNavegacion.addElement(miga1);
            this.menuNavegacion.addElement(miga2);
        }
        if (tipo.equals("2")) {
            this.menuNavegacion = new DefaultMenuModel();
            index.setValue("Index");
            miga1.setValue("Planes de estudio");
            miga2.setValue("Gestión planes de estudio");

            this.menuNavegacion.addElement(index);
            this.menuNavegacion.addElement(miga1);
            this.menuNavegacion.addElement(miga2);
        }

        if (tipo.equals("3")) {
            this.menuNavegacion = new DefaultMenuModel();
            index.setValue("Index");
            miga1.setValue("Oferta académica");
            miga2.setValue("Listado de ofertas académicas");

            this.menuNavegacion.addElement(index);
            this.menuNavegacion.addElement(miga1);
            this.menuNavegacion.addElement(miga2);
        }

        return menuNavegacion;
    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * gestion de usuarios
     */
    public void addGestionUsuario() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Usuarios");
        miga2.setValue("Gestión de usuarios");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);
    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * gestion de permisos
     */
    public void addGestionUsuariRol() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Usuarios");
        miga2.setValue("Gestión de permisos");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);
    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de mi
     * perfil
     */
    public void addPerfilUsuario() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga2.setValue("Mi perfil");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga2);
    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * Gestión de ofertas académicas
     */
    public void addOfertaAcademicaGestion() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Oferta académica");
        miga2.setValue("Gestión de ofertas académicas");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);
    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * Listado de ofertas académicas
     */
    public void addOfertaAcademicaLista() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Oferta académica");
        miga2.setValue("Listado de ofertas académicas");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);

    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * Gestión de materias
     */
    public void addOfertaAcademicaRegistroMaterias() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Materias");
        miga2.setValue("Gestión de materias");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);

    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * Gestión de departamentos o facultades
     */
    public void addOfertaAcademicaRegistroDepFac() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Departamentos y facultades");
        miga2.setValue("Gestión de departamentos o facultades");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);

    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * Gestión planes de estudio
     */
    public void addPlanesDeEstudioGestion() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Planes de estudio");
        miga2.setValue("Gestión planes de estudio");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);

    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de
     * Gestión de anteproyectos
     */
    public void addAnteproyectoGestion() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");
        miga1.setValue("Anteproyecto");
        miga2.setValue("Gestión de anteproyectos");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(miga1);
        this.menuNavegacion.addElement(miga2);

    }

    /**
     * Se agrega al modelo la miga de pan correspondiente a la pestaña de inicio
     */
    public void cambioDeOpciones() {
        this.menuNavegacion = new DefaultMenuModel();
        index.setValue("Index");

        DefaultMenuItem inicio = new DefaultMenuItem();
        inicio.setStyle("cursor: default;text-decoration: none;");
        inicio.setValue("Gestión de departamentos o facultades");
        inicio.setValue("Inicio");

        this.menuNavegacion.addElement(index);
        this.menuNavegacion.addElement(inicio);
    }

    /**
     * Getters and setters
     */
    public MenuModel getMenu() {
        return menuNavegacion;
    }
}
