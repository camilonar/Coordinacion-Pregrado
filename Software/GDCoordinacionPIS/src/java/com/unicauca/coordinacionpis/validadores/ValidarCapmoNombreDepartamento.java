/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.validadores;

import com.unicauca.coordinacionpis.sessionbean.DepartamentoFacade;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author David
 */
@FacesValidator(value = "ValidarCampoNombreDepartamento")
public class ValidarCapmoNombreDepartamento implements Validator, Serializable {

    @EJB
    private DepartamentoFacade departamentoEJB;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String texto = String.valueOf(value);
       

        if (departamentoEJB.buscarUsuarioPorNombreDeDepartamentoBool(texto)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Información", "El nombre de departamento ya se encuentra en uso");
            throw new ValidatorException(msg);
        }

    }

}
