/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.sessionbean;

import com.unicauca.coordinacionpis.entidades.UsuarioPrograma;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daniela
 */
@Stateless
public class UsuarioProgramaFacade extends AbstractFacade<UsuarioPrograma> {

    @PersistenceContext(unitName = "GDCoordinacionPISPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuarioProgramaFacade() {
        super(UsuarioPrograma.class);
    }
    
}
