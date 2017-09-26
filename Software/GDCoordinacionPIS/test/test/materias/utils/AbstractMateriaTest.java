/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.materias.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jwebunit.junit.JWebUnit;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertButtonPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickButton;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import test.usuario.test_cases.RegistrarUsuarioTextFieldsTest;

/**
 *
 * @author Camilo
 */
public class AbstractMateriaTest {
    
    //Configuración
    private static String nombreUsuario = "admin";
    private static String contrasena = "jefe";
    
    protected void loginAsAdmin(){
        beginAt("/GDCP/sinSesion/index.xhtml"); 
        JWebUnit.setTextField("form-inicio:nombreUsuario", nombreUsuario);
        JWebUnit.setTextField("form-inicio:contrasena", contrasena);
        assertButtonPresent("form-inicio:login");
        clickButton("form-inicio:login");
    }
    
    protected void goToMateria(){
        loginAsAdmin();
        assertLinkPresent("menu-admin:menuitem-materias");
        clickLink("menu-admin:menuitem-materias");
    }
    
    protected void goToRegistrar(){
        goToMateria();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("MateriaCreateDlg").getAttribute("aria-hidden"));
        assertButtonPresent("form:createButton");
        clickButton("form:createButton");
        JWebUnit.assertMatch("false", JWebUnit.getElementById("MateriaCreateDlg").getAttribute("aria-hidden"));
    }
    
        protected void goToEditar(){
        goToMateria();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("MateriaEditDlg").getAttribute("aria-hidden"));
        assertButtonPresent("MateriaListForm:datalist:0:editButton");
        clickButton("MateriaListForm:datalist:0:editButton");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarUsuarioTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("MateriaEditDlg").getAttribute("aria-hidden"));
    }
}
