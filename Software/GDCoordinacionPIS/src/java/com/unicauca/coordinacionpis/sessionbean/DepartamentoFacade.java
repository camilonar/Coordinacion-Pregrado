/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicauca.coordinacionpis.sessionbean;

import com.unicauca.coordinacionpis.entidades.Departamento;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ROED26
 */
@Stateless
public class DepartamentoFacade extends AbstractFacade<Departamento> {

    @PersistenceContext(unitName = "GDCoordinacionPISPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DepartamentoFacade() {
        super(Departamento.class);
    }

    public boolean buscarUsuarioPorNombreDeDepartamentoBool(String nombreDep) {

        Query query = getEntityManager().createNamedQuery("Departamento.findByNombre");
        query.setParameter("nombre", nombreDep);
        List<Departamento> resultList = query.getResultList();
        if (resultList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //Retorna  el nombre de el jefe asignado a ese depto, null si no hay ninguno
    public String findByDepartamentoJefe(Integer idDepartamento) {
        Query query = getEntityManager().createNamedQuery("Departamento.findByIdDepartamento");
        query.setParameter("idDepartamento", idDepartamento);
        Departamento departamento = (Departamento) query.getSingleResult();
        if(!departamento.getUsuarioDepartamentoList().isEmpty()){
            return departamento.getUsuarioDepartamentoList().get(0).getUsuario().getUsunombreusuario();
        }else{
            return null;
        }
    }

}
