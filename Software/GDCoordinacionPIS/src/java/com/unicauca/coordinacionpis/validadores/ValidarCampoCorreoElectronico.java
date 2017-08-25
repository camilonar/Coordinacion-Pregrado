
package com.unicauca.coordinacionpis.validadores;



import com.unicauca.coordinacionpis.sessionbean.UsuarioFacade;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="ValidarCampoCorreoElectronico")
public class ValidarCampoCorreoElectronico implements Validator
{
    @EJB
    private UsuarioFacade usuarioEJB;
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        
        String texto = String.valueOf(value);        
       if(!texto.isEmpty()){
            Pattern patron = Pattern.compile("([_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,}))");
        Matcher encaja = patron.matcher(texto);        
        if(!encaja.find())
        {
            FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información","El formato del correo no es válido. Ej: correo@correo.com");
            throw new ValidatorException(msg);
        }
        else
        {
            if(usuarioEJB.buscarPorEmail(texto))
            {
                FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información","El correo electrónico y se encuentra registrado.");
                throw new ValidatorException(msg);
            }
        }
       }
       
        
    }
    
}