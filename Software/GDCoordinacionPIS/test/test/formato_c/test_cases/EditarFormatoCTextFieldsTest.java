/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.formato_c.test_cases;

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
import test.formato_c.utils.AbstractFormatoCTest;
import test.formato_c.utils.EditarFormatoCNames;
/**
 * Pruebas de los campos de texto del formulario de edición de formato C
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarFormatoCTextFieldsTest extends AbstractFormatoCTest{
    
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
            { EditarFormatoCNames.idTitulo,"Metodología nueva",EditarFormatoCNames.idMsgTitulo,true},
            { EditarFormatoCNames.idTitulo,"Metodología nueva 3.0",EditarFormatoCNames.idMsgTitulo,true},
            { EditarFormatoCNames.idTitulo,"12345",EditarFormatoCNames.idMsgTitulo,false},
            { EditarFormatoCNames.idTitulo,"#$%@",EditarFormatoCNames.idMsgTitulo,false},
            { EditarFormatoCNames.idTitulo,"",EditarFormatoCNames.idMsgTitulo,false},
            { EditarFormatoCNames.idFecha,"02/03/1985",EditarFormatoCNames.idMsgFecha,true},
            { EditarFormatoCNames.idFecha,"04/03/1995",EditarFormatoCNames.idMsgFecha,true},
            { EditarFormatoCNames.idFecha,"15/01/2018",EditarFormatoCNames.idMsgFecha,false},
            { EditarFormatoCNames.idFecha,"15/01/201A",EditarFormatoCNames.idMsgFecha,false},
            { EditarFormatoCNames.idFecha,"2501/1970",EditarFormatoCNames.idMsgFecha,false},
            { EditarFormatoCNames.idFecha,"32/05/1990",EditarFormatoCNames.idMsgFecha,false},
            { EditarFormatoCNames.idFecha,"",EditarFormatoCNames.idMsgFecha,false},
            { EditarFormatoCNames.idNombre1,"Juan José Rodriguez Calderón",EditarFormatoCNames.idMsgNombre1,true},
            { EditarFormatoCNames.idNombre1,"T",EditarFormatoCNames.idMsgNombre1,true},
            { EditarFormatoCNames.idNombre1,"",EditarFormatoCNames.idMsgNombre1,false},
            { EditarFormatoCNames.idNombre1,"Sara1",EditarFormatoCNames.idMsgNombre1,false},
            { EditarFormatoCNames.idNombre1,"Sara_%",EditarFormatoCNames.idMsgNombre1,false},
            { EditarFormatoCNames.idNombre2,"Juan José Rodriguez Calderón",EditarFormatoCNames.idMsgNombre2,true},
            { EditarFormatoCNames.idNombre2,"T",EditarFormatoCNames.idMsgNombre2,true},
            { EditarFormatoCNames.idNombre2,"",EditarFormatoCNames.idMsgNombre2,true},
            { EditarFormatoCNames.idNombre2,"Sara1",EditarFormatoCNames.idMsgNombre2,false},
            { EditarFormatoCNames.idNombre2,"Sara_%",EditarFormatoCNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(EditarFormatoCNames.idFormulario);
        JWebUnit.assertFormPresent(EditarFormatoCNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarFormatoCNames.idRegistrarBtn);
        JWebUnit.submit(EditarFormatoCNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarFormatoCTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
