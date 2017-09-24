/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.departamento.utils;

import test.usuario.utils.*;
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
public class AbstractDepartamentoTest {
    
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
    
    protected void goToDepartamento(){
        loginAsAdmin();
        assertLinkPresent("menu-admin:menuitem-departamentos-facultades");
        clickLink("menu-admin:menuitem-departamentos-facultades");
    }
    
    protected void goToRegistrar(){
        goToDepartamento();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("DepartamentoCreateDlg").getAttribute("aria-hidden"));
        assertButtonPresent("form:createButton");
        clickButton("form:createButton");
        JWebUnit.assertMatch("false", JWebUnit.getElementById("DepartamentoCreateDlg").getAttribute("aria-hidden"));
    }
    
        protected void goToEditar(){
        goToDepartamento();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("DepartamentoEditDlg").getAttribute("aria-hidden"));
        assertButtonPresent("DepartamentoListForm:datalist:0:editButton");
        clickButton("DepartamentoListForm:datalist:0:editButton");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarUsuarioTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("DepartamentoEditDlg").getAttribute("aria-hidden"));
    }
}
