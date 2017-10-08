/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.plan_estudios.test_cases;

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
import test.plan_estudios.utils.AbstractPlanEstudiosTest;
import test.plan_estudios.utils.EditarPlanEstudiosNames;
/**
 * Pruebas de los campos de texto del formulario de edición de plan de estudios
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarPlanEstudiosTextFieldsTest extends AbstractPlanEstudiosTest{
    
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
            { EditarPlanEstudiosNames.idAcuerdo,"Acuerdo #1",EditarPlanEstudiosNames.idMsgAcuerdo,true},
            { EditarPlanEstudiosNames.idAcuerdo,"Cinco",EditarPlanEstudiosNames.idMsgAcuerdo,true},
            { EditarPlanEstudiosNames.idAcuerdo,"Cuat",EditarPlanEstudiosNames.idMsgAcuerdo,false},
            { EditarPlanEstudiosNames.idAcuerdo,"Treinta12301234567890123456789",EditarPlanEstudiosNames.idMsgAcuerdo,true},
            { EditarPlanEstudiosNames.idAcuerdo,"TreintayUno12301234567890123456",EditarPlanEstudiosNames.idMsgAcuerdo,false},
            { EditarPlanEstudiosNames.idAcuerdo,"",EditarPlanEstudiosNames.idMsgAcuerdo,false},
            { EditarPlanEstudiosNames.idVigencia,"02/03/1985",EditarPlanEstudiosNames.idMsgVigencia,true},
            { EditarPlanEstudiosNames.idVigencia,"04/03/1995",EditarPlanEstudiosNames.idMsgVigencia,true},
            { EditarPlanEstudiosNames.idVigencia,"15/01/2018",EditarPlanEstudiosNames.idMsgVigencia,false},
            { EditarPlanEstudiosNames.idVigencia,"15/01/201A",EditarPlanEstudiosNames.idMsgVigencia,false},
            { EditarPlanEstudiosNames.idVigencia,"2501/1970",EditarPlanEstudiosNames.idMsgVigencia,false},
            { EditarPlanEstudiosNames.idVigencia,"32/05/1990",EditarPlanEstudiosNames.idMsgVigencia,false},
            { EditarPlanEstudiosNames.idVigencia,"",EditarPlanEstudiosNames.idMsgVigencia,false}
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
        JWebUnit.setWorkingForm(EditarPlanEstudiosNames.idFormulario);
        JWebUnit.assertFormPresent(EditarPlanEstudiosNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarPlanEstudiosNames.idRegistrarBtn);
        JWebUnit.submit(EditarPlanEstudiosNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarPlanEstudiosTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
