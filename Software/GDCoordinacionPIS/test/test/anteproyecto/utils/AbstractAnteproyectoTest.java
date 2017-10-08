/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.anteproyecto.utils;

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
public class AbstractAnteproyectoTest {
    /**
     * Nota importante: para las pruebas el formulario de registro de anteproyectos
     * debe estar renderizado desde el inicio
     */
    //Configuración
    private static String nombreUsuario = "pmage";
    private static String contrasena = "coordinador";
    private static String archivoSubida = "C:\\Users\\Camilo\\Downloads\\4.5-9.pdf";
    
    protected void loginAsCoordinador(){
        beginAt("/GDCP/sinSesion/index.xhtml"); 
        JWebUnit.setTextField("form-inicio:nombreUsuario", nombreUsuario);
        JWebUnit.setTextField("form-inicio:contrasena", contrasena);
        assertButtonPresent("form-inicio:login");
        clickButton("form-inicio:login");
    }
    
    protected void goToAnteproyecto(){
        loginAsCoordinador();
        assertLinkPresent("menu-coordinador:menuitem-anteproyectos");
        clickLink("menu-coordinador:menuitem-anteproyectos");
    }
    
    protected void goToRegistrar(){
        goToAnteproyecto();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("AnteproyectoCreateDlg").getAttribute("aria-hidden"));
        assertButtonPresent("form:createButton");
        clickButton("form:createButton");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractAnteproyectoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("AnteproyectoCreateDlg").getAttribute("aria-hidden"));
    }
    
        protected void goToEditar(){
        goToAnteproyecto();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("AnteproyectoEditDlg").getAttribute("aria-hidden"));
        assertButtonPresent("formListaAnteproyectos:datalist:0:editButton");
        clickButton("formListaAnteproyectos:datalist:0:editButton");
        JWebUnit.assertMatch("true", JWebUnit.getElementById("AnteproyectoEditDlg").getAttribute("aria-hidden"));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractAnteproyectoTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("AnteproyectoEditDlg").getAttribute("aria-hidden"));
    }
}
