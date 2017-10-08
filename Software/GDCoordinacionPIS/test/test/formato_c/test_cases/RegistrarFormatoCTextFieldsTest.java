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
import test.formato_c.utils.RegistrarFormatoCNames;
/**
 * Pruebas de los campos de texto del formulario de registro de formato C
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarFormatoCTextFieldsTest extends AbstractFormatoCTest{
    
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
            { RegistrarFormatoCNames.idTitulo,"Metodología nueva",RegistrarFormatoCNames.idMsgTitulo,true},
            { RegistrarFormatoCNames.idTitulo,"Metodología nueva 3.0",RegistrarFormatoCNames.idMsgTitulo,true},
            { RegistrarFormatoCNames.idTitulo,"12345",RegistrarFormatoCNames.idMsgTitulo,false},
            { RegistrarFormatoCNames.idTitulo,"#$%@",RegistrarFormatoCNames.idMsgTitulo,false},
            { RegistrarFormatoCNames.idTitulo,"",RegistrarFormatoCNames.idMsgTitulo,false},
            { RegistrarFormatoCNames.idFecha,"02/03/1985",RegistrarFormatoCNames.idMsgFecha,true},
            { RegistrarFormatoCNames.idFecha,"04/03/1995",RegistrarFormatoCNames.idMsgFecha,true},
            { RegistrarFormatoCNames.idFecha,"15/01/2018",RegistrarFormatoCNames.idMsgFecha,false},
            { RegistrarFormatoCNames.idFecha,"15/01/201A",RegistrarFormatoCNames.idMsgFecha,false},
            { RegistrarFormatoCNames.idFecha,"2501/1970",RegistrarFormatoCNames.idMsgFecha,false},
            { RegistrarFormatoCNames.idFecha,"32/05/1990",RegistrarFormatoCNames.idMsgFecha,false},
            { RegistrarFormatoCNames.idFecha,"",RegistrarFormatoCNames.idMsgFecha,false},
            { RegistrarFormatoCNames.idNombre1,"Juan José Rodriguez Calderón",RegistrarFormatoCNames.idMsgNombre1,true},
            { RegistrarFormatoCNames.idNombre1,"T",RegistrarFormatoCNames.idMsgNombre1,true},
            { RegistrarFormatoCNames.idNombre1,"",RegistrarFormatoCNames.idMsgNombre1,false},
            { RegistrarFormatoCNames.idNombre1,"Sara1",RegistrarFormatoCNames.idMsgNombre1,false},
            { RegistrarFormatoCNames.idNombre1,"Sara_%",RegistrarFormatoCNames.idMsgNombre1,false},
            { RegistrarFormatoCNames.idNombre2,"Juan José Rodriguez Calderón",RegistrarFormatoCNames.idMsgNombre2,true},
            { RegistrarFormatoCNames.idNombre2,"T",RegistrarFormatoCNames.idMsgNombre2,true},
            { RegistrarFormatoCNames.idNombre2,"",RegistrarFormatoCNames.idMsgNombre2,true},
            { RegistrarFormatoCNames.idNombre2,"Sara1",RegistrarFormatoCNames.idMsgNombre2,false},
            { RegistrarFormatoCNames.idNombre2,"Sara_%",RegistrarFormatoCNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(RegistrarFormatoCNames.idFormulario);
        JWebUnit.assertFormPresent(RegistrarFormatoCNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarFormatoCNames.idRegistrarBtn);
        JWebUnit.submit(RegistrarFormatoCNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarFormatoCTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
