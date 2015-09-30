package com.plusnet.facade;

import com.plusnet.entity.Course;
import com.plusnet.entity.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class StudentFacade extends AbstractFacade<Student> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
    }
    
    public long getStudentCountByCourseLanguage(String language) {
        Query query = em.createNativeQuery("SELECT count(*) " +
                                            "FROM course AS c " +
                                            "INNER JOIN student_has_course AS shc ON shc.course_id = c.id " +
                                            "WHERE c.COURSE_LANGUAGE = '" + language + "';");
        long result = (long) query.getSingleResult();
        return result;
    }
    
    public boolean addCourse(int studentId, int courseId) {
        Student student = em.find(Student.class, studentId);
        Course course = em.find(Course.class, courseId);
        return student.getCourseList().add(course);
    }
    
    public List<Course> getCourseList(int studentId) {
        Query query = em.createNativeQuery("SELECT c.COURSE_NAME " +
                                            "FROM  course c " +
                                            "JOIN student_has_course shc ON (shc.course_ID = c.ID) " +
                                            "JOIN student s ON (s.ID = shc.student_ID) " +
                                            "WHERE s.ID = " + studentId + ";");
        List<Course> result = (List<Course>) query.getResultList();
        return result;
    }
}
