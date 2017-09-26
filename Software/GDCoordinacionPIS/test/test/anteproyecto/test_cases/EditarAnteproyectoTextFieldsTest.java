/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.anteproyecto.test_cases;

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
import test.anteproyecto.utils.AbstractAnteproyectoTest;
import test.anteproyecto.utils.EditarAnteproyectoNames;
/**
 * Pruebas de los campos de texto del formulario de registro de anteproyectos
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class EditarAnteproyectoTextFieldsTest extends AbstractAnteproyectoTest{
    
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
            { EditarAnteproyectoNames.idTitulo,"Metodología nueva",EditarAnteproyectoNames.idMsgTitulo,true},
            { EditarAnteproyectoNames.idTitulo,"Metodología nueva 3.0",EditarAnteproyectoNames.idMsgTitulo,true},
            { EditarAnteproyectoNames.idTitulo,"12345",EditarAnteproyectoNames.idMsgTitulo,false},
            { EditarAnteproyectoNames.idTitulo,"#$%@",EditarAnteproyectoNames.idMsgTitulo,false},
            { EditarAnteproyectoNames.idTitulo,"",EditarAnteproyectoNames.idMsgTitulo,false},
            { EditarAnteproyectoNames.idFecha,"02/03/1985",EditarAnteproyectoNames.idMsgFecha,true},
            { EditarAnteproyectoNames.idFecha,"04/03/1995",EditarAnteproyectoNames.idMsgFecha,true},
            { EditarAnteproyectoNames.idFecha,"15/01/2018",EditarAnteproyectoNames.idMsgFecha,false},
            { EditarAnteproyectoNames.idFecha,"15/01/201A",EditarAnteproyectoNames.idMsgFecha,false},
            { EditarAnteproyectoNames.idFecha,"2501/1970",EditarAnteproyectoNames.idMsgFecha,false},
            { EditarAnteproyectoNames.idFecha,"32/05/1990",EditarAnteproyectoNames.idMsgFecha,false},
            { EditarAnteproyectoNames.idFecha,"",EditarAnteproyectoNames.idMsgFecha,false},
            { EditarAnteproyectoNames.idNombre1,"Juan José Rodriguez Calderón",EditarAnteproyectoNames.idMsgNombre1,true},
            { EditarAnteproyectoNames.idNombre1,"T",EditarAnteproyectoNames.idMsgNombre1,true},
            { EditarAnteproyectoNames.idNombre1,"",EditarAnteproyectoNames.idMsgNombre1,true},
            { EditarAnteproyectoNames.idNombre1,"Sara1",EditarAnteproyectoNames.idMsgNombre1,false},
            { EditarAnteproyectoNames.idNombre1,"Sara_%",EditarAnteproyectoNames.idMsgNombre1,false},
            { EditarAnteproyectoNames.idNombre2,"Juan José Rodriguez Calderón",EditarAnteproyectoNames.idMsgNombre2,true},
            { EditarAnteproyectoNames.idNombre2,"T",EditarAnteproyectoNames.idMsgNombre2,true},
            { EditarAnteproyectoNames.idNombre2,"",EditarAnteproyectoNames.idMsgNombre2,true},
            { EditarAnteproyectoNames.idNombre2,"Sara1",EditarAnteproyectoNames.idMsgNombre2,false},
            { EditarAnteproyectoNames.idNombre2,"Sara_%",EditarAnteproyectoNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(EditarAnteproyectoNames.idFormulario);
        JWebUnit.assertFormPresent(EditarAnteproyectoNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(EditarAnteproyectoNames.idRegistrarBtn);
        JWebUnit.submit(EditarAnteproyectoNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(EditarAnteproyectoTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
