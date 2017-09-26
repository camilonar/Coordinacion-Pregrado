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
import test.departamento.utils.RegistrarDepartamentoNames;
import test.materias.utils.AbstractMateriaTest;
import test.materias.utils.RegistrarMateriaNames;
/**
 * Pruebas de los campos de texto del formulario de registro de materias
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarMateriaTextFieldsTest extends AbstractMateriaTest{
    
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
            { RegistrarMateriaNames.idCodigo,"320-2/MAT302",RegistrarMateriaNames.idMsgCodigo,true},
            { RegistrarMateriaNames.idCodigo,"MAT101",RegistrarMateriaNames.idMsgCodigo,true},
            { RegistrarMateriaNames.idCodigo,"FIS301L",RegistrarMateriaNames.idMsgCodigo,true},
            { RegistrarMateriaNames.idCodigo,"Exactamente 30 caracteres 1234",RegistrarMateriaNames.idMsgCodigo,true},
            { RegistrarMateriaNames.idCodigo,"Más de 30 caracteres definitivo",RegistrarMateriaNames.idMsgCodigo,false},
            { RegistrarMateriaNames.idCodigo,"",RegistrarMateriaNames.idMsgCodigo,false},
            { RegistrarMateriaNames.idNombre,"Fisioterapia I",RegistrarMateriaNames.idMsgNombre,true},
            { RegistrarMateriaNames.idNombre,"Laboratorio de Electromagnetismo",RegistrarMateriaNames.idMsgNombre,true},
            { RegistrarMateriaNames.idNombre,"Una materia de exactamente setenta caracteres rellenorellenorellenoasd",RegistrarMateriaNames.idMsgNombre,true},
            { RegistrarMateriaNames.idNombre,"AB#_",RegistrarMateriaNames.idMsgNombre,false},
            { RegistrarMateriaNames.idNombre,"12345",RegistrarMateriaNames.idMsgNombre,false},
            { RegistrarMateriaNames.idNombre,"",RegistrarMateriaNames.idMsgNombre,false},
            { RegistrarMateriaNames.idCreditos,"4",RegistrarMateriaNames.idMsgCreditos,true},
            { RegistrarMateriaNames.idCreditos,"0",RegistrarMateriaNames.idMsgCreditos,true},
            { RegistrarMateriaNames.idCreditos,"-1",RegistrarMateriaNames.idMsgCreditos,false},
            { RegistrarMateriaNames.idCreditos,"d$%#",RegistrarMateriaNames.idMsgCreditos,false},
            { RegistrarMateriaNames.idCreditos,"letras",RegistrarMateriaNames.idMsgCreditos,false},
            { RegistrarMateriaNames.idCreditos,"",RegistrarMateriaNames.idMsgCreditos,false},
            { RegistrarMateriaNames.idIntensidad,"4",RegistrarMateriaNames.idMsgIntensidad,true},
            { RegistrarMateriaNames.idIntensidad,"0",RegistrarMateriaNames.idMsgIntensidad,true},
            { RegistrarMateriaNames.idIntensidad,"-1",RegistrarMateriaNames.idMsgIntensidad,false},
            { RegistrarMateriaNames.idIntensidad,"d$%#",RegistrarMateriaNames.idMsgIntensidad,false},
            { RegistrarMateriaNames.idIntensidad,"letras",RegistrarMateriaNames.idMsgIntensidad,false},
            { RegistrarMateriaNames.idIntensidad,"",RegistrarMateriaNames.idMsgIntensidad,false},
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
        JWebUnit.setWorkingForm(RegistrarMateriaNames.idFormulario);
        JWebUnit.assertFormPresent(RegistrarMateriaNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarMateriaNames.idRegistrarBtn);
        JWebUnit.submit(RegistrarMateriaNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarMateriaTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
