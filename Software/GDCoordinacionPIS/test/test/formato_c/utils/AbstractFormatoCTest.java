/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.formato_c.utils;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jwebunit.junit.JWebUnit;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertButtonPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.assertLinkPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickButton;
import static net.sourceforge.jwebunit.junit.JWebUnit.clickLink;

/**
 *
 * @author Camilo
 */
public class AbstractFormatoCTest {
    /**
     * Nota importante: para las pruebas el formulario de registro de formato C
     * debe estar renderizado desde el inicio
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
    
    protected void goToFormatoC(){
        loginAsCoordinador();
        assertLinkPresent("menu-coordinador:menuitem-formatoC");
        clickLink("menu-coordinador:menuitem-formatoC");
    }
    
    protected void goToRegistrar(){
        goToFormatoC();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("AnteproyectoCreateDlg").getAttribute("aria-hidden"));
        assertButtonPresent("form:createButton");
        clickButton("form:createButton");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractFormatoCTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("AnteproyectoCreateDlg").getAttribute("aria-hidden"));
    }
    
        protected void goToEditar(){
        goToFormatoC();
        JWebUnit.assertMatch("true",JWebUnit.getElementById("AnteproyectoEditDlg").getAttribute("aria-hidden"));
        assertButtonPresent("formListaFormatoC:datalist:0:editButton");
        clickButton("formListaFormatoC:datalist:0:editButton");
        JWebUnit.assertMatch("true", JWebUnit.getElementById("AnteproyectoEditDlg").getAttribute("aria-hidden"));
        try {
            Thread.sleep(3500);
        } catch (InterruptedException ex) {
            Logger.getLogger(AbstractFormatoCTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.assertMatch("false", JWebUnit.getElementById("AnteproyectoEditDlg").getAttribute("aria-hidden"));
    }
}
