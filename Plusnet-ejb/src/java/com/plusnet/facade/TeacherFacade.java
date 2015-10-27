/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plusnet.facade;

import com.plusnet.entity.Teacher;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Kasim
 */
@Stateless
public class TeacherFacade extends AbstractFacade<Teacher> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TeacherFacade() {
        super(Teacher.class);
    }

    public Teacher authenticateTeacher(String username, String password) {
        try {
            Teacher teacher = (Teacher) em.createNamedQuery(
                    "Teacher.findByUsernameAndPassword")
                    .setParameter("userName", username)
                    .setParameter("password", password)
                    .getSingleResult();
            return teacher;
        } catch (Exception e) {
            return null;
        }
    }
}
