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
import test.checklist.utils.RegistrarChecklistNames;
/**
 * Pruebas de los campos de texto del formulario de registro de checklist
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarChecklistTextFieldsTest extends AbstractChecklistTest{
    
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
            { RegistrarChecklistNames.idTitulo,"Metodología nueva",RegistrarChecklistNames.idMsgTitulo,true},
            { RegistrarChecklistNames.idTitulo,"Metodología nueva 3.0",RegistrarChecklistNames.idMsgTitulo,true},
            { RegistrarChecklistNames.idTitulo,"12345",RegistrarChecklistNames.idMsgTitulo,false},
            { RegistrarChecklistNames.idTitulo,"#$%@",RegistrarChecklistNames.idMsgTitulo,false},
            { RegistrarChecklistNames.idTitulo,"",RegistrarChecklistNames.idMsgTitulo,false},
            { RegistrarChecklistNames.idFecha,"02/03/1985",RegistrarChecklistNames.idMsgFecha,true},
            { RegistrarChecklistNames.idFecha,"04/03/1995",RegistrarChecklistNames.idMsgFecha,true},
            { RegistrarChecklistNames.idFecha,"15/01/2018",RegistrarChecklistNames.idMsgFecha,false},
            { RegistrarChecklistNames.idFecha,"15/01/201A",RegistrarChecklistNames.idMsgFecha,false},
            { RegistrarChecklistNames.idFecha,"2501/1970",RegistrarChecklistNames.idMsgFecha,false},
            { RegistrarChecklistNames.idFecha,"32/05/1990",RegistrarChecklistNames.idMsgFecha,false},
            { RegistrarChecklistNames.idFecha,"",RegistrarChecklistNames.idMsgFecha,false},
            { RegistrarChecklistNames.idNombre1,"Juan José Rodriguez Calderón",RegistrarChecklistNames.idMsgNombre1,true},
            { RegistrarChecklistNames.idNombre1,"T",RegistrarChecklistNames.idMsgNombre1,true},
            { RegistrarChecklistNames.idNombre1,"",RegistrarChecklistNames.idMsgNombre1,false},
            { RegistrarChecklistNames.idNombre1,"Sara1",RegistrarChecklistNames.idMsgNombre1,false},
            { RegistrarChecklistNames.idNombre1,"Sara_%",RegistrarChecklistNames.idMsgNombre1,false},
            { RegistrarChecklistNames.idNombre2,"Juan José Rodriguez Calderón",RegistrarChecklistNames.idMsgNombre2,true},
            { RegistrarChecklistNames.idNombre2,"T",RegistrarChecklistNames.idMsgNombre2,true},
            { RegistrarChecklistNames.idNombre2,"",RegistrarChecklistNames.idMsgNombre2,true},
            { RegistrarChecklistNames.idNombre2,"Sara1",RegistrarChecklistNames.idMsgNombre2,false},
            { RegistrarChecklistNames.idNombre2,"Sara_%",RegistrarChecklistNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(RegistrarChecklistNames.idFormulario);
        JWebUnit.assertFormPresent(RegistrarChecklistNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarChecklistNames.idRegistrarBtn);
        JWebUnit.submit(RegistrarChecklistNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarChecklistTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
