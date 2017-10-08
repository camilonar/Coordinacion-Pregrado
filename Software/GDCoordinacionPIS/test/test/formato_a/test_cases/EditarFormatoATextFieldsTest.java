/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.formato_a.test_cases;

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
import test.formato_a.utils.AbstractFormatoATest;
import test.formato_a.utils.EditarFormatoANames;
/**
 * Pruebas de los campos de texto del formulario de edición de formato A
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarFormatoATextFieldsTest extends AbstractFormatoATest{
    
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
            { EditarFormatoANames.idTitulo,"Metodología nueva",EditarFormatoANames.idMsgTitulo,true},
            { EditarFormatoANames.idTitulo,"Metodología nueva 3.0",EditarFormatoANames.idMsgTitulo,true},
            { EditarFormatoANames.idTitulo,"12345",EditarFormatoANames.idMsgTitulo,false},
            { EditarFormatoANames.idTitulo,"#$%@",EditarFormatoANames.idMsgTitulo,false},
            { EditarFormatoANames.idTitulo,"",EditarFormatoANames.idMsgTitulo,false},
            { EditarFormatoANames.idFecha,"02/03/1985",EditarFormatoANames.idMsgFecha,true},
            { EditarFormatoANames.idFecha,"04/03/1995",EditarFormatoANames.idMsgFecha,true},
            { EditarFormatoANames.idFecha,"15/01/2018",EditarFormatoANames.idMsgFecha,false},
            { EditarFormatoANames.idFecha,"15/01/201A",EditarFormatoANames.idMsgFecha,false},
            { EditarFormatoANames.idFecha,"2501/1970",EditarFormatoANames.idMsgFecha,false},
            { EditarFormatoANames.idFecha,"32/05/1990",EditarFormatoANames.idMsgFecha,false},
            { EditarFormatoANames.idFecha,"",EditarFormatoANames.idMsgFecha,false},
            { EditarFormatoANames.idNombre1,"Juan José Rodriguez Calderón",EditarFormatoANames.idMsgNombre1,true},
            { EditarFormatoANames.idNombre1,"T",EditarFormatoANames.idMsgNombre1,true},
            { EditarFormatoANames.idNombre1,"",EditarFormatoANames.idMsgNombre1,true},
            { EditarFormatoANames.idNombre1,"Sara1",EditarFormatoANames.idMsgNombre1,false},
            { EditarFormatoANames.idNombre1,"Sara_%",EditarFormatoANames.idMsgNombre1,false},
            { EditarFormatoANames.idNombre2,"Juan José Rodriguez Calderón",EditarFormatoANames.idMsgNombre2,true},
            { EditarFormatoANames.idNombre2,"T",EditarFormatoANames.idMsgNombre2,true},
            { EditarFormatoANames.idNombre2,"",EditarFormatoANames.idMsgNombre2,true},
            { EditarFormatoANames.idNombre2,"Sara1",EditarFormatoANames.idMsgNombre2,false},
            { EditarFormatoANames.idNombre2,"Sara_%",EditarFormatoANames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(EditarFormatoANames.idFormulario);
        JWebUnit.assertFormPresent(EditarFormatoANames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarFormatoANames.idRegistrarBtn);
        JWebUnit.submit(EditarFormatoANames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarFormatoATextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
