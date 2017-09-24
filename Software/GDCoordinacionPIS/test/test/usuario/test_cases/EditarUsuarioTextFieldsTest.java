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
public class EditarUsuarioTextFieldsTest extends AbstractUsuarioTest{
    
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
            { EditarUsuarioNames.idNombre,"Juan Carlos",EditarUsuarioNames.idMsgNombre,true},
            { EditarUsuarioNames.idNombre,"D",EditarUsuarioNames.idMsgNombre,true},
            { EditarUsuarioNames.idNombre,"",EditarUsuarioNames.idMsgNombre,false},
            { EditarUsuarioNames.idNombre,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuaED",EditarUsuarioNames.idMsgNombre,true},
            { EditarUsuarioNames.idNombre,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanED",EditarUsuarioNames.idMsgNombre,true},
            { EditarUsuarioNames.idNombre,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanEDJ",EditarUsuarioNames.idMsgNombre,false},
            { EditarUsuarioNames.idNombre,"Sara1",EditarUsuarioNames.idMsgNombre,false},
            { EditarUsuarioNames.idNombre,"Sara_%",EditarUsuarioNames.idMsgNombre,false},
            { EditarUsuarioNames.idApellido,"Rodriguez Calderón",EditarUsuarioNames.idMsgApellido,true},
            { EditarUsuarioNames.idApellido,"T",EditarUsuarioNames.idMsgApellido,true},
            { EditarUsuarioNames.idApellido,"",EditarUsuarioNames.idMsgApellido,false},
            { EditarUsuarioNames.idApellido,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuaED",EditarUsuarioNames.idMsgApellido,true},
            { EditarUsuarioNames.idApellido,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanED",EditarUsuarioNames.idMsgApellido,true},
            { EditarUsuarioNames.idApellido,"JuanJuanJuanJJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanJuanEDJ",EditarUsuarioNames.idMsgApellido,false},
            { EditarUsuarioNames.idApellido,"Sara1",EditarUsuarioNames.idMsgApellido,false},
            { EditarUsuarioNames.idApellido,"Sara_%",EditarUsuarioNames.idMsgApellido,false},
            { EditarUsuarioNames.idFechaNacimiento,"02/03/1985",EditarUsuarioNames.idMsgFechaNacimiento,true},
            { EditarUsuarioNames.idFechaNacimiento,"04/03/1995",EditarUsuarioNames.idMsgFechaNacimiento,true},
            { EditarUsuarioNames.idFechaNacimiento,"01/15/2018",EditarUsuarioNames.idMsgFechaNacimiento,false},
            { EditarUsuarioNames.idFechaNacimiento,"15/01/201A",EditarUsuarioNames.idMsgFechaNacimiento,false},
            { EditarUsuarioNames.idFechaNacimiento,"2501/1970",EditarUsuarioNames.idMsgFechaNacimiento,false},
            { EditarUsuarioNames.idFechaNacimiento,"32/05/1990",EditarUsuarioNames.idMsgFechaNacimiento,false},
            { EditarUsuarioNames.idFechaNacimiento,"",EditarUsuarioNames.idMsgFechaNacimiento,false}
        });
    }
    
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:7080/GDCoordinacionPIS");
    }
    
    @Test
    public void testEditar() {
        goToEditar();
        JWebUnit.setWorkingForm(EditarUsuarioNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarUsuarioNames.idActualizarBtn);
        JWebUnit.submit(EditarUsuarioNames.idActualizarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarUsuarioTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}