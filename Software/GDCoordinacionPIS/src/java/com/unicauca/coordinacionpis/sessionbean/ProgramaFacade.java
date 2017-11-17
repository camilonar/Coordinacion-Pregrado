/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.sessionbean;

import com.unicauca.coordinacionpis.entidades.Programa;
import com.unicauca.coordinacionpis.entidades.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Daniela
 */
@Stateless
public class ProgramaFacade extends AbstractFacade<Programa> {

    @PersistenceContext(unitName = "GDCoordinacionPISPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProgramaFacade() {
        super(Programa.class);
    }

    //Retorna  el nombre de el coordinador asignado a ese programa, null si no hay ninguno
    public String findByProgramaCoordinador(Integer idPrograma) {
        Query query = getEntityManager().createNamedQuery("Programa.findByIdPrograma");
        query.setParameter("idPrograma", idPrograma);
        Programa programa = (Programa) query.getSingleResult();
        if(!programa.getUsuarioProgramaList().isEmpty()){
            return programa.getUsuarioProgramaList().get(0).getUsuario().getUsunombreusuario();
        }else{
            return null;
        }        
    }
    
    public void limpiarCache(){
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

}