/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.departamento.test_cases;

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
import test.departamento.utils.AbstractDepartamentoTest;
import test.departamento.utils.EditarDepartamentoNames;

/**
 * Pruebas de los campos de texto del formulario de edición de departamento
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarDepartamentoTextFieldsTest extends AbstractDepartamentoTest{
    
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
            { EditarDepartamentoNames.idNombre,"Dpto. Electrónica",EditarDepartamentoNames.idMsgNombre,true},
            { EditarDepartamentoNames.idNombre,"Sistemas",EditarDepartamentoNames.idMsgNombre,true},
            { EditarDepartamentoNames.idNombre,"SistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasERT",EditarDepartamentoNames.idMsgNombre,true},
            { EditarDepartamentoNames.idNombre,"SistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasERTQ",EditarDepartamentoNames.idMsgNombre,true},
            { EditarDepartamentoNames.idNombre,"SistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasSistemasERTQZ",EditarDepartamentoNames.idMsgNombre,false},
            { EditarDepartamentoNames.idNombre,"Automática1",EditarDepartamentoNames.idMsgNombre,false},
            { EditarDepartamentoNames.idNombre,"F#$_°()",EditarDepartamentoNames.idMsgNombre,false},
            { EditarDepartamentoNames.idNombre,"12345",EditarDepartamentoNames.idMsgNombre,false},
            { EditarDepartamentoNames.idNombre,"",EditarDepartamentoNames.idMsgNombre,false},
            { EditarDepartamentoNames.idNombre,"Contables",EditarDepartamentoNames.idMsgNombre,false}
        });
    }
    
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:7080/GDCoordinacionPIS");
    }
    
    @Test
    public void testRegistrar() {
        goToEditar();
        JWebUnit.setWorkingForm(EditarDepartamentoNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarDepartamentoNames.idRegistrarBtn);
        JWebUnit.submit(EditarDepartamentoNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarDepartamentoTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
