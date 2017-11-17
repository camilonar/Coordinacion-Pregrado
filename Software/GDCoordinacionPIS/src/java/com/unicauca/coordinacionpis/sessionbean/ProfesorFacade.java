/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.sessionbean;

import com.unicauca.coordinacionpis.entidades.Estudiante;
import com.unicauca.coordinacionpis.entidades.Profesor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author David
 */
@Stateless
public class ProfesorFacade extends AbstractFacade<Profesor> {

    @PersistenceContext(unitName = "GDCoordinacionPISPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesorFacade() {
        super(Profesor.class);
    }
    public Profesor findByCodigo(String codigo)
    {
        Query query = em.createNamedQuery("Profesor.findByCodigoProfesor");
        query.setParameter("codigoProfesor", codigo);
        List<Profesor> resultList = query.getResultList();
        return resultList.size()>0?resultList.get(0):null;
    }
    
}
