/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.plan_estudios.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jwebunit.junit.JWebUnit;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertButtonPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickButton;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import test.plan_estudios.test_cases.RegistrarPlanEstudiosTextFieldsTest;
import test.usuario.test_cases.RegistrarUsuarioTextFieldsTest;

/**
 *
 * @author Camilo
 */
public class AbstractPlanEstudiosTest {
    
    /**
     * Nota importante: para las pruebas el formulario de registro de planes de
     * estudio debe estar renderizado desde el inicio
     */
    //Configuración
    private static final String NOMBRE_USUARIO = "pmage";
    private static final String CONTRASENA = "coordinador";
    
    protected void loginAsCoordinador(){
        beginAt("/GDCP/sinSesion/index.xhtml"); 
        JWebUnit.setTextField("form-inicio:nombreUsuario", NOMBRE_USUARIO);
        JWebUnit.setTextField("form-inicio:contrasena", CONTRASENA);
        assertButtonPresent("form-inicio:login");
        clickButton("form-inicio:login");
    }
    
    protected void goToPlanEstudio(){
        loginAsCoordinador();
        assertLinkPresent("menu-coordinador:menuitem-planes-estudio");
        clickLink("menu-coordinador:menuitem-planes-estudio");
    }
    
    protected void goToRegistrar(){
        goToPlanEstudio();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("PlanEstudioCreateDlg").getAttribute("aria-hidden"));
        assertButtonPresent("form:createButton");
        clickButton("form:createButton");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarPlanEstudiosTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("PlanEstudioCreateDlg").getAttribute("aria-hidden"));
    }
    
        protected void goToEditar(){
        goToPlanEstudio();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("PlanEstudioEditDlg").getAttribute("aria-hidden"));
        assertButtonPresent("formPlanesdeEstudio:lstPlanesEstudio:0:editButton");
        clickButton("formPlanesdeEstudio:lstPlanesEstudio:0:editButton");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarUsuarioTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("PlanEstudioEditDlg").getAttribute("aria-hidden"));
    }
}
