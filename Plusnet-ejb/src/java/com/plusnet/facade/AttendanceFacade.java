package com.plusnet.facade;

import com.plusnet.domain.AttendanceDomain;
import com.plusnet.entity.Attendance;
import com.plusnet.entity.Student;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Kasim
 */
@Stateless
public class AttendanceFacade extends AbstractFacade<Attendance> {
    @PersistenceContext
    private EntityManager em;
    
    @EJB private StudentFacade studentFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AttendanceFacade() {
        super(Attendance.class);
    }
    
    public List<Integer> getStudentIdsByAttendanceDate(Date date) {
        Query query = em.createNamedQuery("Attendance.findByRecordDate", Attendance.class);
        query.setParameter("recordDate", date);
        List<Attendance> result = (List<Attendance>) query.getResultList();
        List<Integer> studentIds = new ArrayList<>(result.size());
        for(Attendance item :  result) {
            Student student = item.getStudent();
            int studentId = student.getId();
            studentIds.add(studentId);
        }
        return studentIds;
    }

    public void createAttendance(AttendanceDomain attendance) {
        Attendance att = new Attendance();
        att.setIsAttended(attendance.getAttended());
        att.setRecordDate(attendance.getRecordDate());
        Student std = studentFacade.convertDomainToEntity(attendance.getStudentDomain());
        att.setStudent(std);
        em.persist(att);
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public void setStudentFacade(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }
}
