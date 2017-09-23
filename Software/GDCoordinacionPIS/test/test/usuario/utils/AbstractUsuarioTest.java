/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.usuario.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jwebunit.junit.JWebUnit;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertButtonPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickButton;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;
import test.usuario.registrar.RegistrarUsuarioTextFieldsTest;

/**
 *
 * @author Camilo
 */
public class AbstractUsuarioTest {
    
    //Configuración
    private static String nombreUsuario = "admin";
    private static String contrasena = "jefe";
    
    protected void goToUsuario(){
        beginAt("/GDCP/sinSesion/index.xhtml"); 
        JWebUnit.setTextField("form-inicio:nombreUsuario", nombreUsuario);
        JWebUnit.setTextField("form-inicio:contrasena", contrasena);
        assertButtonPresent("form-inicio:login");
        clickButton("form-inicio:login");
        assertLinkPresent("menu-admin:menuitem-usuarios");
        clickLink("menu-admin:menuitem-usuarios");
    }
    
    protected void goToRegistrar(){
        goToUsuario();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("UsuarioCreateDlg").getAttribute("aria-hidden"));
        assertButtonPresent("form:registrar-usuario");
        clickButton("form:registrar-usuario");
        JWebUnit.assertMatch("false", JWebUnit.getElementById("UsuarioCreateDlg").getAttribute("aria-hidden"));
    }
    
        protected void goToEditar(){
        goToUsuario();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("UsuarioEditDlg").getAttribute("aria-hidden"));
        assertButtonPresent("UsuarioListForm:datalist:0:editButton");
        clickButton("UsuarioListForm:datalist:0:editButton");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarUsuarioTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("UsuarioEditDlg").getAttribute("aria-hidden"));
    }
}
