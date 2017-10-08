/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.formato_a.test_cases;

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
import test.formato_a.utils.AbstractAnteproyectoTest;
import test.departamento.utils.RegistrarDepartamentoNames;
import test.materias.utils.AbstractMateriaTest;
import test.materias.utils.RegistrarMateriaNames;
import test.formato_a.utils.RegistrarFormatoANames;
/**
 * Pruebas de los campos de texto del formulario de registro de formato A
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarFormatoATextFieldsTest extends AbstractAnteproyectoTest{
    
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
            { RegistrarFormatoANames.idTitulo,"Metodología nueva",RegistrarFormatoANames.idMsgTitulo,true},
            { RegistrarFormatoANames.idTitulo,"Metodología nueva 3.0",RegistrarFormatoANames.idMsgTitulo,true},
            { RegistrarFormatoANames.idTitulo,"12345",RegistrarFormatoANames.idMsgTitulo,false},
            { RegistrarFormatoANames.idTitulo,"#$%@",RegistrarFormatoANames.idMsgTitulo,false},
            { RegistrarFormatoANames.idTitulo,"",RegistrarFormatoANames.idMsgTitulo,false},
            { RegistrarFormatoANames.idFecha,"02/03/1985",RegistrarFormatoANames.idMsgFecha,true},
            { RegistrarFormatoANames.idFecha,"04/03/1995",RegistrarFormatoANames.idMsgFecha,true},
            { RegistrarFormatoANames.idFecha,"15/01/2018",RegistrarFormatoANames.idMsgFecha,false},
            { RegistrarFormatoANames.idFecha,"15/01/201A",RegistrarFormatoANames.idMsgFecha,false},
            { RegistrarFormatoANames.idFecha,"2501/1970",RegistrarFormatoANames.idMsgFecha,false},
            { RegistrarFormatoANames.idFecha,"32/05/1990",RegistrarFormatoANames.idMsgFecha,false},
            { RegistrarFormatoANames.idFecha,"",RegistrarFormatoANames.idMsgFecha,false},
            { RegistrarFormatoANames.idNombre1,"Juan José Rodriguez Calderón",RegistrarFormatoANames.idMsgNombre1,true},
            { RegistrarFormatoANames.idNombre1,"T",RegistrarFormatoANames.idMsgNombre1,true},
            { RegistrarFormatoANames.idNombre1,"",RegistrarFormatoANames.idMsgNombre1,true},
            { RegistrarFormatoANames.idNombre1,"Sara1",RegistrarFormatoANames.idMsgNombre1,false},
            { RegistrarFormatoANames.idNombre1,"Sara_%",RegistrarFormatoANames.idMsgNombre1,false},
            { RegistrarFormatoANames.idNombre2,"Juan José Rodriguez Calderón",RegistrarFormatoANames.idMsgNombre2,true},
            { RegistrarFormatoANames.idNombre2,"T",RegistrarFormatoANames.idMsgNombre2,true},
            { RegistrarFormatoANames.idNombre2,"",RegistrarFormatoANames.idMsgNombre2,true},
            { RegistrarFormatoANames.idNombre2,"Sara1",RegistrarFormatoANames.idMsgNombre2,false},
            { RegistrarFormatoANames.idNombre2,"Sara_%",RegistrarFormatoANames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(RegistrarFormatoANames.idFormulario);
        JWebUnit.assertFormPresent(RegistrarFormatoANames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarFormatoANames.idRegistrarBtn);
        JWebUnit.submit(RegistrarFormatoANames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarFormatoATextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
