/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.sessionbean;

import com.unicauca.coordinacionpis.entidades.Anteproyecto;
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
public class AnteproyectoFacade extends AbstractFacade<Anteproyecto> {

    @PersistenceContext(unitName = "GDCoordinacionPISPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AnteproyectoFacade() {
        super(Anteproyecto.class);
    }

    public List<Anteproyecto> buscarProyecto(String datoBusqueda) {
        Query query = getEntityManager().createNamedQuery("Anteproyecto.findByBusquedaTitulo");
        query.setParameter("busqueda", "%" + datoBusqueda + "%");
        List<Anteproyecto> resultList = query.getResultList();
        return resultList;
    }

}
