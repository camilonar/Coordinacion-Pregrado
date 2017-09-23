package test.login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Arrays;
import java.util.Collection;
import net.sourceforge.jwebunit.junit.JWebUnit;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;


/**
 *
 * @author Camilo
 */
@RunWith(Parameterized.class)
public class LoginTest{
    @Parameter(0)
    /**
     * Nombre de usuario de prueba
     */
    public String nombreUsuario;
    
    @Parameter(1)
    /**
     * Contraseña de prueba
     */
    public String contrasena;
    
    @Parameter(2)
    /**
     * Indica el nombre del menú de usuario del rol del usuario que corresponde 
     * a los datos de prueba. Se debe poner null en caso de que el inicio de 
     * sesión deba ser inválido
     */
    public String rolMenu;
    
    @Parameters(name = "usuario: {0}; contrasena: {1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
            { "pmage", "coordinador", "menu-coordinador" },
            { "fpino", "jefe", "menu-jefe" }, 
            { "admin", "jefe", "menu-admin" },
            { "otro", "1234", null },
            { "", "1234", null },
            { "otro", "", null }});
    }
    
    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT); 
        setBaseUrl("http://localhost:7080/GDCoordinacionPIS");
    }
    
    @Test
    public void testLoginPage() {
        beginAt("/GDCP/sinSesion/index.xhtml"); 
        JWebUnit.setTextField("form-inicio:nombreUsuario", nombreUsuario);
        JWebUnit.setTextField("form-inicio:contrasena", contrasena);
        assertButtonPresent("form-inicio:login");
        clickButton("form-inicio:login");
        if (rolMenu==null){
            assertButtonPresent("form-inicio:login");
        } else {
            assertElementPresent(rolMenu);
        }
    }

}
