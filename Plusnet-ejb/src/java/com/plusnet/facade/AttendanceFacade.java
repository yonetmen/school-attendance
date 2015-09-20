/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plusnet.facade;

import com.plusnet.entity.Attendance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kasim
 */
@Stateless
public class AttendanceFacade extends AbstractFacade<Attendance> {
    @PersistenceContext(unitName = "Plusnet-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceFacade() {
        super(Attendance.class);
    }
    
}
