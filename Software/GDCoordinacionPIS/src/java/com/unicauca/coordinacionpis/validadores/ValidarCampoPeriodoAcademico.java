package com.unicauca.coordinacionpis.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator(value="ValidarCampoPeriodoAcademico")
public class ValidarCampoPeriodoAcademico implements Validator
{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 
    {
        String texto = String.valueOf(value);
       if(texto.length()>30){
           FacesMessage msg= new FacesMessage(FacesMessage.SEVERITY_ERROR,"Información","El campo admite máximo 30 caracteres");
           throw new ValidatorException(msg);
        }
        
        

    }
    
    
    
    
    
}
