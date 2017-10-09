/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.formato_b.test_cases;

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
import test.formato_b.utils.AbstractFormatoBTest;
import test.formato_b.utils.EditarFormatoBNames;
/**
 * Pruebas de los campos de texto del formulario de edición de formato B
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarFormatoBTextFieldsTest extends AbstractFormatoBTest{
    
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
            { EditarFormatoBNames.idTitulo,"Metodología nueva",EditarFormatoBNames.idMsgTitulo,true},
            { EditarFormatoBNames.idTitulo,"Metodología nueva 3.0",EditarFormatoBNames.idMsgTitulo,true},
            { EditarFormatoBNames.idTitulo,"12345",EditarFormatoBNames.idMsgTitulo,false},
            { EditarFormatoBNames.idTitulo,"#$%@",EditarFormatoBNames.idMsgTitulo,false},
            { EditarFormatoBNames.idTitulo,"",EditarFormatoBNames.idMsgTitulo,false},
            { EditarFormatoBNames.idFecha,"02/03/1985",EditarFormatoBNames.idMsgFecha,true},
            { EditarFormatoBNames.idFecha,"04/03/1995",EditarFormatoBNames.idMsgFecha,true},
            { EditarFormatoBNames.idFecha,"15/01/2018",EditarFormatoBNames.idMsgFecha,false},
            { EditarFormatoBNames.idFecha,"15/01/201A",EditarFormatoBNames.idMsgFecha,false},
            { EditarFormatoBNames.idFecha,"2501/1970",EditarFormatoBNames.idMsgFecha,false},
            { EditarFormatoBNames.idFecha,"32/05/1990",EditarFormatoBNames.idMsgFecha,false},
            { EditarFormatoBNames.idFecha,"",EditarFormatoBNames.idMsgFecha,false},
            { EditarFormatoBNames.idNombre1,"Juan José Rodriguez Calderón",EditarFormatoBNames.idMsgNombre1,true},
            { EditarFormatoBNames.idNombre1,"T",EditarFormatoBNames.idMsgNombre1,true},
            { EditarFormatoBNames.idNombre1,"",EditarFormatoBNames.idMsgNombre1,false},
            { EditarFormatoBNames.idNombre1,"Sara1",EditarFormatoBNames.idMsgNombre1,false},
            { EditarFormatoBNames.idNombre1,"Sara_%",EditarFormatoBNames.idMsgNombre1,false},
            { EditarFormatoBNames.idNombre2,"Juan José Rodriguez Calderón",EditarFormatoBNames.idMsgNombre2,true},
            { EditarFormatoBNames.idNombre2,"T",EditarFormatoBNames.idMsgNombre2,true},
            { EditarFormatoBNames.idNombre2,"",EditarFormatoBNames.idMsgNombre2,true},
            { EditarFormatoBNames.idNombre2,"Sara1",EditarFormatoBNames.idMsgNombre2,false},
            { EditarFormatoBNames.idNombre2,"Sara_%",EditarFormatoBNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(EditarFormatoBNames.idFormulario);
        JWebUnit.assertFormPresent(EditarFormatoBNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarFormatoBNames.idRegistrarBtn);
        JWebUnit.submit(EditarFormatoBNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarFormatoBTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
