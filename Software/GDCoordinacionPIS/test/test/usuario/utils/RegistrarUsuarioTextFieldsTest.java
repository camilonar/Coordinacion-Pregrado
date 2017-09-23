/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.usuario.utils;

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
import test.usuario.registrar.AbstractUsuarioTest;

/**
 * Pruebas de los campos de texto del formulario de registro de usuario
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarUsuarioTextFieldsTest extends AbstractUsuarioTest{
    
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
            { RegistrarUsuarioNames.idContrasena,"1234",RegistrarUsuarioNames.idMsgContrasena,true},
            { RegistrarUsuarioNames.idContrasena,"12$%_()!#@U34a",RegistrarUsuarioNames.idMsgContrasena,true},
            { RegistrarUsuarioNames.idContrasena,"simple",RegistrarUsuarioNames.idMsgContrasena,true},
            { RegistrarUsuarioNames.idContrasena,"campo20caracteres123",RegistrarUsuarioNames.idMsgContrasena,true},
            { RegistrarUsuarioNames.idContrasena,"campo21caracteres1234",RegistrarUsuarioNames.idMsgContrasena,false},
            { RegistrarUsuarioNames.idContrasena,"",RegistrarUsuarioNames.idMsgContrasena,false},
            { RegistrarUsuarioNames.idNombre,"Juan Carlos",RegistrarUsuarioNames.idMsgNombre,true},
            { RegistrarUsuarioNames.idNombre,"D",RegistrarUsuarioNames.idMsgNombre,true},
            { RegistrarUsuarioNames.idNombre,"",RegistrarUsuarioNames.idMsgNombre,false},
            { RegistrarUsuarioNames.idNombre,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuaED",RegistrarUsuarioNames.idMsgNombre,true},
            { RegistrarUsuarioNames.idNombre,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanED",RegistrarUsuarioNames.idMsgNombre,true},
            { RegistrarUsuarioNames.idNombre,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanEDJ",RegistrarUsuarioNames.idMsgNombre,false},
            { RegistrarUsuarioNames.idNombre,"Sara1",RegistrarUsuarioNames.idMsgNombre,false},
            { RegistrarUsuarioNames.idNombre,"Sara_%",RegistrarUsuarioNames.idMsgNombre,false},
            { RegistrarUsuarioNames.idApellido,"Rodriguez Calderón",RegistrarUsuarioNames.idMsgApellido,true},
            { RegistrarUsuarioNames.idApellido,"T",RegistrarUsuarioNames.idMsgApellido,true},
            { RegistrarUsuarioNames.idApellido,"",RegistrarUsuarioNames.idMsgApellido,false},
            { RegistrarUsuarioNames.idApellido,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuaED",RegistrarUsuarioNames.idMsgApellido,true},
            { RegistrarUsuarioNames.idApellido,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanED",RegistrarUsuarioNames.idMsgApellido,true},
            { RegistrarUsuarioNames.idApellido,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanEDJ",RegistrarUsuarioNames.idMsgApellido,false},
            { RegistrarUsuarioNames.idApellido,"Sara1",RegistrarUsuarioNames.idMsgApellido,false},
            { RegistrarUsuarioNames.idApellido,"Sara_%",RegistrarUsuarioNames.idMsgApellido,false},
            { RegistrarUsuarioNames.idFechaNacimiento,"02/03/1985",RegistrarUsuarioNames.idMsgFechaNacimiento,true},
            { RegistrarUsuarioNames.idFechaNacimiento,"04/03/1995",RegistrarUsuarioNames.idMsgFechaNacimiento,true},
            { RegistrarUsuarioNames.idFechaNacimiento,"01/15/2018",RegistrarUsuarioNames.idMsgFechaNacimiento,false},
            { RegistrarUsuarioNames.idFechaNacimiento,"15/01/201A",RegistrarUsuarioNames.idMsgFechaNacimiento,false},
            { RegistrarUsuarioNames.idFechaNacimiento,"2501/1970",RegistrarUsuarioNames.idMsgFechaNacimiento,false},
            { RegistrarUsuarioNames.idFechaNacimiento,"32/05/1990",RegistrarUsuarioNames.idMsgFechaNacimiento,false},
            { RegistrarUsuarioNames.idFechaNacimiento,"",RegistrarUsuarioNames.idMsgFechaNacimiento,false},
            { RegistrarUsuarioNames.idEmail,"valido@unicauca.edu.co",RegistrarUsuarioNames.idMsgEmail,true},
            { RegistrarUsuarioNames.idEmail,"valido_123@gmail.com",RegistrarUsuarioNames.idMsgEmail,true},
            { RegistrarUsuarioNames.idEmail,"invalido@unicauca.edu.",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idEmail,"novalido!#%@yahoo.es",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idEmail,"novalido!#%@yahoo.es",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idEmail,"novalido!#%@yahoo.es",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idEmail,"otro@hotmail.",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idEmail,"pmage@unicauca.edu.co",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idEmail,"",RegistrarUsuarioNames.idMsgEmail,false},
            { RegistrarUsuarioNames.idNombreUsuario,"nuevox",RegistrarUsuarioNames.idMsgNombreUsuario,true},
            { RegistrarUsuarioNames.idNombreUsuario,"novo_123",RegistrarUsuarioNames.idMsgNombreUsuario,true},
            { RegistrarUsuarioNames.idNombreUsuario,"con espacios",RegistrarUsuarioNames.idMsgNombreUsuario,false},
            { RegistrarUsuarioNames.idNombreUsuario,"con20caracteres12345",RegistrarUsuarioNames.idMsgNombreUsuario,true},
            { RegistrarUsuarioNames.idNombreUsuario,"con21caracteres123456",RegistrarUsuarioNames.idMsgNombreUsuario,false},
            { RegistrarUsuarioNames.idNombreUsuario,"pmage",RegistrarUsuarioNames.idMsgNombreUsuario,false},
            { RegistrarUsuarioNames.idNombreUsuario,"",RegistrarUsuarioNames.idMsgNombreUsuario,false},
        });
    }
    
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:7080/GDCoordinacionPIS");
    }
    
    @Test
    public void testRegistrar() {
        goToRegistrar();
        JWebUnit.setWorkingForm(RegistrarUsuarioNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarUsuarioNames.idRegistrarBtn);
        JWebUnit.submit(RegistrarUsuarioNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarUsuarioTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
