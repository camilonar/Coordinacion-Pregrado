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
import test.formato_b.utils.RegistrarFormatoBNames;
/**
 * Pruebas de los campos de texto del formulario de registro de formato B
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarFormatoBTextFieldsTest extends AbstractFormatoBTest{
    
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
            { RegistrarFormatoBNames.idTitulo,"Metodología nueva",RegistrarFormatoBNames.idMsgTitulo,true},
            { RegistrarFormatoBNames.idTitulo,"Metodología nueva 3.0",RegistrarFormatoBNames.idMsgTitulo,true},
            { RegistrarFormatoBNames.idTitulo,"12345",RegistrarFormatoBNames.idMsgTitulo,false},
            { RegistrarFormatoBNames.idTitulo,"#$%@",RegistrarFormatoBNames.idMsgTitulo,false},
            { RegistrarFormatoBNames.idTitulo,"",RegistrarFormatoBNames.idMsgTitulo,false},
            { RegistrarFormatoBNames.idFecha,"02/03/1985",RegistrarFormatoBNames.idMsgFecha,true},
            { RegistrarFormatoBNames.idFecha,"04/03/1995",RegistrarFormatoBNames.idMsgFecha,true},
            { RegistrarFormatoBNames.idFecha,"15/01/2018",RegistrarFormatoBNames.idMsgFecha,false},
            { RegistrarFormatoBNames.idFecha,"15/01/201A",RegistrarFormatoBNames.idMsgFecha,false},
            { RegistrarFormatoBNames.idFecha,"2501/1970",RegistrarFormatoBNames.idMsgFecha,false},
            { RegistrarFormatoBNames.idFecha,"32/05/1990",RegistrarFormatoBNames.idMsgFecha,false},
            { RegistrarFormatoBNames.idFecha,"",RegistrarFormatoBNames.idMsgFecha,false},
            { RegistrarFormatoBNames.idNombre1,"Juan José Rodriguez Calderón",RegistrarFormatoBNames.idMsgNombre1,true},
            { RegistrarFormatoBNames.idNombre1,"T",RegistrarFormatoBNames.idMsgNombre1,true},
            { RegistrarFormatoBNames.idNombre1,"",RegistrarFormatoBNames.idMsgNombre1,false},
            { RegistrarFormatoBNames.idNombre1,"Sara1",RegistrarFormatoBNames.idMsgNombre1,false},
            { RegistrarFormatoBNames.idNombre1,"Sara_%",RegistrarFormatoBNames.idMsgNombre1,false},
            { RegistrarFormatoBNames.idNombre2,"Juan José Rodriguez Calderón",RegistrarFormatoBNames.idMsgNombre2,true},
            { RegistrarFormatoBNames.idNombre2,"T",RegistrarFormatoBNames.idMsgNombre2,true},
            { RegistrarFormatoBNames.idNombre2,"",RegistrarFormatoBNames.idMsgNombre2,true},
            { RegistrarFormatoBNames.idNombre2,"Sara1",RegistrarFormatoBNames.idMsgNombre2,false},
            { RegistrarFormatoBNames.idNombre2,"Sara_%",RegistrarFormatoBNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(RegistrarFormatoBNames.idFormulario);
        JWebUnit.assertFormPresent(RegistrarFormatoBNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarFormatoBNames.idRegistrarBtn);
        JWebUnit.submit(RegistrarFormatoBNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarFormatoBTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
