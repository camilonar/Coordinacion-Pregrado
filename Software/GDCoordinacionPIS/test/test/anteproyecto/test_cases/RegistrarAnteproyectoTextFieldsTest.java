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
import test.anteproyecto.utils.RegistrarAnteproyectoNames;
import test.departamento.utils.RegistrarDepartamentoNames;
import test.materias.utils.AbstractMateriaTest;
import test.materias.utils.RegistrarMateriaNames;
/**
 * Pruebas de los campos de texto del formulario de registro de anteproyectos
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class RegistrarAnteproyectoTextFieldsTest extends AbstractAnteproyectoTest{
    
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
            { RegistrarAnteproyectoNames.idTitulo,"Metodología nueva",RegistrarAnteproyectoNames.idMsgTitulo,true},
            { RegistrarAnteproyectoNames.idTitulo,"Metodología nueva 3.0",RegistrarAnteproyectoNames.idMsgTitulo,true},
            { RegistrarAnteproyectoNames.idTitulo,"12345",RegistrarAnteproyectoNames.idMsgTitulo,false},
            { RegistrarAnteproyectoNames.idTitulo,"#$%@",RegistrarAnteproyectoNames.idMsgTitulo,false},
            { RegistrarAnteproyectoNames.idTitulo,"",RegistrarAnteproyectoNames.idMsgTitulo,false},
            { RegistrarAnteproyectoNames.idFecha,"02/03/1985",RegistrarAnteproyectoNames.idMsgFecha,true},
            { RegistrarAnteproyectoNames.idFecha,"04/03/1995",RegistrarAnteproyectoNames.idMsgFecha,true},
            { RegistrarAnteproyectoNames.idFecha,"15/01/2018",RegistrarAnteproyectoNames.idMsgFecha,false},
            { RegistrarAnteproyectoNames.idFecha,"15/01/201A",RegistrarAnteproyectoNames.idMsgFecha,false},
            { RegistrarAnteproyectoNames.idFecha,"2501/1970",RegistrarAnteproyectoNames.idMsgFecha,false},
            { RegistrarAnteproyectoNames.idFecha,"32/05/1990",RegistrarAnteproyectoNames.idMsgFecha,false},
            { RegistrarAnteproyectoNames.idFecha,"",RegistrarAnteproyectoNames.idMsgFecha,false},
            { RegistrarAnteproyectoNames.idNombre1,"Juan José Rodriguez Calderón",RegistrarAnteproyectoNames.idMsgNombre1,true},
            { RegistrarAnteproyectoNames.idNombre1,"T",RegistrarAnteproyectoNames.idMsgNombre1,true},
            { RegistrarAnteproyectoNames.idNombre1,"",RegistrarAnteproyectoNames.idMsgNombre1,true},
            { RegistrarAnteproyectoNames.idNombre1,"Sara1",RegistrarAnteproyectoNames.idMsgNombre1,false},
            { RegistrarAnteproyectoNames.idNombre1,"Sara_%",RegistrarAnteproyectoNames.idMsgNombre1,false},
            { RegistrarAnteproyectoNames.idNombre2,"Juan José Rodriguez Calderón",RegistrarAnteproyectoNames.idMsgNombre2,true},
            { RegistrarAnteproyectoNames.idNombre2,"T",RegistrarAnteproyectoNames.idMsgNombre2,true},
            { RegistrarAnteproyectoNames.idNombre2,"",RegistrarAnteproyectoNames.idMsgNombre2,true},
            { RegistrarAnteproyectoNames.idNombre2,"Sara1",RegistrarAnteproyectoNames.idMsgNombre2,false},
            { RegistrarAnteproyectoNames.idNombre2,"Sara_%",RegistrarAnteproyectoNames.idMsgNombre2,false}
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
        JWebUnit.setWorkingForm(RegistrarAnteproyectoNames.idFormulario);
        JWebUnit.assertFormPresent(RegistrarAnteproyectoNames.idFormulario);
        JWebUnit.setTextField(idCampo, contenido);
        JWebUnit.assertSubmitButtonPresent(RegistrarAnteproyectoNames.idRegistrarBtn);
        JWebUnit.submit(RegistrarAnteproyectoNames.idRegistrarBtn);
        JWebUnit.assertElementPresent(idMensaje);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(RegistrarAnteproyectoTextFieldsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isCorrect){
            JWebUnit.assertNotMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        } else{
            JWebUnit.assertMatch("ui-message-error", JWebUnit.getElementById(idMensaje).getAttribute("class"));
        }
    }
    
}
