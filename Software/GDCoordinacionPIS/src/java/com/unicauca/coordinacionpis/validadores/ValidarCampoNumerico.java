package com.unicauca.coordinacionpis.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="ValidarCampoNumerico")
public class ValidarCampoNumerico implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
        
        try
        {
            long campo= Long.parseLong(texto);
            
        }catch(Exception e)
        {
           FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información","El campo solo admite valores numéricos");
           throw new ValidatorException(msg);
        }
        
        

    }
    
    
    
    
    
}
