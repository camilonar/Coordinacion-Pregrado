/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.materias.test_cases;

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
import test.materias.utils.AbstractMateriaTest;
import test.materias.utils.EditarMateriaNames;
/**
 * Pruebas de los campos de texto del formulario de registro de materias
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarMateriaTextFieldsTest extends AbstractMateriaTest{
    
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
            { EditarMateriaNames.idCodigo,"320-2/MAT302",EditarMateriaNames.idMsgCodigo,true},
            { EditarMateriaNames.idCodigo,"MAT101",EditarMateriaNames.idMsgCodigo,true},
            { EditarMateriaNames.idCodigo,"FIS301L",EditarMateriaNames.idMsgCodigo,true},
            { EditarMateriaNames.idCodigo,"Exactamente 30 caracteres 1234",EditarMateriaNames.idMsgCodigo,true},
            { EditarMateriaNames.idCodigo,"Más de 30 caracteres definitivo",EditarMateriaNames.idMsgCodigo,false},
            { EditarMateriaNames.idCodigo,"",EditarMateriaNames.idMsgCodigo,false},
            { EditarMateriaNames.idNombre,"Fisioterapia I",EditarMateriaNames.idMsgNombre,true},
            { EditarMateriaNames.idNombre,"Laboratorio de Electromagnetismo",EditarMateriaNames.idMsgNombre,true},
            { EditarMateriaNames.idNombre,"Una materia de exactamente setenta caracteres rellenorellenorellenoasd",EditarMateriaNames.idMsgNombre,true},
            { EditarMateriaNames.idNombre,"AB#_",EditarMateriaNames.idMsgNombre,false},
            { EditarMateriaNames.idNombre,"12345",EditarMateriaNames.idMsgNombre,false},
            { EditarMateriaNames.idNombre,"",EditarMateriaNames.idMsgNombre,false},
            { EditarMateriaNames.idCreditos,"4",EditarMateriaNames.idMsgCreditos,true},
            { EditarMateriaNames.idCreditos,"0",EditarMateriaNames.idMsgCreditos,true},
            { EditarMateriaNames.idCreditos,"-1",EditarMateriaNames.idMsgCreditos,false},
            { EditarMateriaNames.idCreditos,"d$%#",EditarMateriaNames.idMsgCreditos,false},
            { EditarMateriaNames.idCreditos,"letras",EditarMateriaNames.idMsgCreditos,false},
            { EditarMateriaNames.idCreditos,"",EditarMateriaNames.idMsgCreditos,false},
            { EditarMateriaNames.idIntensidad,"4",EditarMateriaNames.idMsgIntensidad,true},
            { EditarMateriaNames.idIntensidad,"0",EditarMateriaNames.idMsgIntensidad,true},
            { EditarMateriaNames.idIntensidad,"-1",EditarMateriaNames.idMsgIntensidad,false},
            { EditarMateriaNames.idIntensidad,"d$%#",EditarMateriaNames.idMsgIntensidad,false},
            { EditarMateriaNames.idIntensidad,"letras",EditarMateriaNames.idMsgIntensidad,false},
            { EditarMateriaNames.idIntensidad,"",EditarMateriaNames.idMsgIntensidad,false},
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
        JWebUnit.setWorkingForm(EditarMateriaNames.idFormulario);
        JWebUnit.assertFormPresent(EditarMateriaNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarMateriaNames.idRegistrarBtn);
        JWebUnit.submit(EditarMateriaNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarMateriaTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
