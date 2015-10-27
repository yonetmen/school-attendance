package com.plusnet.facade;

import com.plusnet.entity.Course;
import com.plusnet.entity.Student;
import java.util.ArrayList;
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

    public List<Course> findCourseListByStudentId(int studentId) {
        if(studentId > 0) {
            Student std = em.find(Student.class, studentId);
        return std.getCourseList();
        } 
        return null;
    }

    // This method is not exposed to users
    public long getStudentCountByCourseLanguage(String language) {
        Query query = em.createNativeQuery("SELECT count(*) "
                + "FROM course AS c "
                + "INNER JOIN student_has_course AS shc ON shc.course_id = c.id "
                + "WHERE c.COURSE_LANGUAGE = '" + language + "';");
        long result = (long) query.getSingleResult();
        return result;
    }

    // Add selected courses from "pick list" into Student's course list.
    public void addToStudentsCourseList(int studentId, List<Course> courseList) {
        dropAllCoursesByStudentId(studentId);
        Student student = em.find(Student.class, studentId);
        for (Course course : courseList) {
            student.getCourseList().add(course);
            course.getStudentList().add(student);
            em.merge(course);
            em.merge(student);
        }
    }

    public List<Course> findAvailableCoursesForStudent(int studentId) {
        Query query = em.createNamedQuery("Course.findAll", Course.class
        );
        List<Course> allCourses = (List<Course>) query.getResultList();

        allCourses.removeAll(em.find(Student.class, studentId).getCourseList());
        return allCourses;
    }

    // This method is not exposed to users
    private void dropAllCoursesByStudentId(int studentId) {
        Query query = em.createNativeQuery("delete shc "
                + "FROM  student_has_course shc "
                + "WHERE shc.student_ID = " + studentId + ";");
        query.executeUpdate();
    }

    public List<Student> getStudentListById(List<Integer> studentListSendToRektor) {
        List<Student> studentList = new ArrayList<>(studentListSendToRektor.size());
        for (int studentId : studentListSendToRektor) {
            Query query = em.createNamedQuery("Student.findById", Student.class);
            query.setParameter("id", studentId);
            Student student = (Student) query.getSingleResult();
            studentList.add(student);
        }
        return studentList;
    }
    
    // This method is not exposed to users
    public List<Student> getStudentListByCourseName(String courseName) {
        List<Student> studentList = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT  s.* "
                + "FROM student s "
                + "JOIN student_has_course shc ON (s.ID = shc.student_ID) "
                + "JOIN course c ON (shc.course_ID = c.ID) "
                + "where c.COURSE_NAME = '" + courseName + "';");

        List<Object[]> studentArray = (List<Object[]>) query.getResultList();
        for (Object[] students : studentArray) {
            Student s = em.find(Student.class, students[0]);
            studentList.add(s);
        }
        return studentList;
    }
}
