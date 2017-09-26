package com.unicauca.coordinacionpis.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="ValidarCampoApellido")
public class ValidarCampoApellido implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        
       if(texto.length()>75){
           FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información","El apellido admite máximo 75 caracteres");
           throw new ValidatorException(msg);
       }
       Pattern patron = Pattern.compile("[^A-Za-záéíóúÁÉÍÓÚ ]");
       Matcher encaja = patron.matcher(texto);  
       if(encaja.find())
       {
           FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información","El apellido solo admite caracteres alfabéticos.");
           throw new ValidatorException(msg);
       }
    }
    
    
    
    
    
}
