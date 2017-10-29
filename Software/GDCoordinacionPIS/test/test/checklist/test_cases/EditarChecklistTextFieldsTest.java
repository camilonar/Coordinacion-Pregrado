/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.checklist.test_cases;

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
import test.checklist.utils.AbstractChecklistTest;
import test.checklist.utils.EditarChecklistNames;
/**
 * Pruebas de los campos de texto del formulario de edición de checklist
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarChecklistTextFieldsTest extends AbstractChecklistTest{
    
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
            { EditarChecklistNames.idTitulo,"Metodología nueva",EditarChecklistNames.idMsgTitulo,true},
            { EditarChecklistNames.idTitulo,"Metodología nueva 3.0",EditarChecklistNames.idMsgTitulo,true},
            { EditarChecklistNames.idTitulo,"12345",EditarChecklistNames.idMsgTitulo,false},
            { EditarChecklistNames.idTitulo,"#$%@",EditarChecklistNames.idMsgTitulo,false},
            { EditarChecklistNames.idTitulo,"",EditarChecklistNames.idMsgTitulo,false},
            { EditarChecklistNames.idFecha,"02/03/1985",EditarChecklistNames.idMsgFecha,true},
            { EditarChecklistNames.idFecha,"04/03/1995",EditarChecklistNames.idMsgFecha,true},
            { EditarChecklistNames.idFecha,"15/01/2018",EditarChecklistNames.idMsgFecha,false},
            { EditarChecklistNames.idFecha,"15/01/201A",EditarChecklistNames.idMsgFecha,false},
            { EditarChecklistNames.idFecha,"2501/1970",EditarChecklistNames.idMsgFecha,false},
            { EditarChecklistNames.idFecha,"32/05/1990",EditarChecklistNames.idMsgFecha,false},
            { EditarChecklistNames.idFecha,"",EditarChecklistNames.idMsgFecha,false},
            { EditarChecklistNames.idNombre1,"Juan José Rodriguez Calderón",EditarChecklistNames.idMsgNombre1,true},
            { EditarChecklistNames.idNombre1,"T",EditarChecklistNames.idMsgNombre1,true},
            { EditarChecklistNames.idNombre1,"",EditarChecklistNames.idMsgNombre1,false},
            { EditarChecklistNames.idNombre1,"Sara1",EditarChecklistNames.idMsgNombre1,false},
            { EditarChecklistNames.idNombre1,"Sara_%",EditarChecklistNames.idMsgNombre1,false},
            { EditarChecklistNames.idNombre2,"Juan José Rodriguez Calderón",EditarChecklistNames.idMsgNombre2,true},
            { EditarChecklistNames.idNombre2,"T",EditarChecklistNames.idMsgNombre2,true},
            { EditarChecklistNames.idNombre2,"",EditarChecklistNames.idMsgNombre2,true},
            { EditarChecklistNames.idNombre2,"Sara1",EditarChecklistNames.idMsgNombre2,false},
            { EditarChecklistNames.idNombre2,"Sara_%",EditarChecklistNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(EditarChecklistNames.idFormulario);
        JWebUnit.assertFormPresent(EditarChecklistNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarChecklistNames.idRegistrarBtn);
        JWebUnit.submit(EditarChecklistNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarChecklistTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
