/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.usuario.test_cases;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sourceforge.jwebunit.junit.JWebUnit;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import net.sourceforge.jwebunit.util.TestingEngineRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import test.usuario.utils.AbstractUsuarioTest;
import test.usuario.utils.EditarUsuarioNames;

/**
 * Pruebas de los campos de texto del formulario de edición de usuario
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarUsuarioContrasenaTest extends AbstractUsuarioTest{
    
    @Parameterized.Parameter(0)
    /**
     * Id del campo al cuál se le introducirán los datos
     */
    public String idCampo;
    
    @Parameterized.Parameter(1)
    /**
     * Contenido que será introducido en el campo
     */
    public String contenido;
    
    @Parameterized.Parameter(2)
    /**
     * Id del mensaje asociado al campo
     */
    public String idMensaje;
    
    @Parameterized.Parameter(3)
    /**
     * Indica si el caso de prueba es de éxito o de error
     */
    public boolean isCorrect;
    
    @Parameterized.Parameters(name = "campo: {0}; contenido: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {    
            { EditarUsuarioNames.idContrasena,"1234",EditarUsuarioNames.idMsgContrasena,true},
            { EditarUsuarioNames.idContrasena,"12$%_()!#@U34a",EditarUsuarioNames.idMsgContrasena,true},
            { EditarUsuarioNames.idContrasena,"simple",EditarUsuarioNames.idMsgContrasena,true},
            { EditarUsuarioNames.idContrasena,"campo20caracteres123",EditarUsuarioNames.idMsgContrasena,true},
            { EditarUsuarioNames.idContrasena,"campo21caracteres1234",EditarUsuarioNames.idMsgContrasena,false},
            { EditarUsuarioNames.idContrasena,"",EditarUsuarioNames.idMsgContrasena,false}
        });
    }
    
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:7080/GDCoordinacionPIS");
    }
    
    @Test
    public void testEditarContrasena() {
        goToEditar();
        JWebUnit.setWorkingForm(EditarUsuarioNames.idFormulario);
        JWebUnit.assertButtonPresent(EditarUsuarioNames.idActualizarContrasenaBtn);
        JWebUnit.clickButton(EditarUsuarioNames.idActualizarContrasenaBtn);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarUsuarioContrasenaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarUsuarioNames.idAceptarContrasenaBtn);
        JWebUnit.submit(EditarUsuarioNames.idAceptarContrasenaBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarUsuarioContrasenaTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}