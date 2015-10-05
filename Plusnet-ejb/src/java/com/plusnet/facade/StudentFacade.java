package com.plusnet.facade;

import com.plusnet.domain.StudentDomain;
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

//    List<Course> courseList;
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
//        courseList = new ArrayList<>();
    }

    public void create(StudentDomain std) {
        Student student = convertDomainToEntity(std);
        create(student);
    }

    public List<StudentDomain> findAllStudents() {
        List<Student> all = findAll();
        List<StudentDomain> converted = convertEntityListToDomainList(all);
        return converted;
    }

    public void removeStudent(int studentId) {
        Student std = find(studentId);
        em.remove(em.merge(std));
    }

    public void editStudent(StudentDomain student) {
        Student std = convertDomainToEntity(student);
        em.merge(std);
    }

    public long getStudentCountByCourseLanguage(String language) {
        Query query = em.createNativeQuery("SELECT count(*) "
                + "FROM course AS c "
                + "INNER JOIN student_has_course AS shc ON shc.course_id = c.id "
                + "WHERE c.COURSE_LANGUAGE = '" + language + "';");
        long result = (long) query.getSingleResult();
        return result;
    }

    // Add selected courses from "pick list" into Student's course list.
    // Adding Student and Course List working but removing not working
    public void addToStudentsCourseList(int studentId, List<Course> courseList) {
        Student student = em.find(Student.class, studentId);
        for (Course course : courseList) {
            if (!student.getCourseList().contains(course)) {
                student.getCourseList().add(course);
            }
            if (!course.getStudentList().contains(student)) {
                course.getStudentList().add(student);
            }
            em.merge(course);
            em.merge(student);
        }
    }

    public List<Course> getCourseListByStudentId(int studentId) {
        List<Course> cList = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT c.* "
                + "FROM  course c "
                + "JOIN student_has_course shc ON (shc.course_ID = c.ID) "
                + "JOIN student s ON (s.ID = shc.student_ID) "
                + "WHERE s.ID = " + studentId + ";");

        List<Object[]> courseArray = (List<Object[]>) query.getResultList();

        for (Object[] courseFields : courseArray) {
            Course c = em.find(Course.class, courseFields[0]);
            cList.add(c);
        }
        return cList;
    }

    public List<Course> findAvailableCoursesForStudent(int studentId) {
        Query query = em.createNamedQuery("Course.findAll", Course.class
        );
        List<Course> allCourses = (List<Course>) query.getResultList();

        allCourses.removeAll(getCourseListByStudentId(studentId));
        return allCourses;
    }

    private List<StudentDomain> convertEntityListToDomainList(List<Student> all) {
        List<StudentDomain> list = new ArrayList<>();
        all.stream().forEach((std) -> {
            StudentDomain domain = new StudentDomain();
            domain.setId(std.getId());
            domain.setFirstName(std.getFirstName());
            domain.setLastName(std.getLastName());
            domain.setEmail(std.getEmail());
            domain.setPhone(std.getPhoneNumber());
            domain.setAddress(std.getAddress());
            domain.setStartDate(std.getStartDate());
            list.add(domain);
        });
        return list;
    }

    public Student convertDomainToEntity(StudentDomain domain) {
        Student student = new Student();
        student.setId(domain.getId());
        student.setFirstName(domain.getFirstName());
        student.setLastName(domain.getLastName());
        student.setEmail(domain.getEmail());
        student.setAddress(domain.getAddress());
        student.setPhoneNumber(domain.getPhone());
        student.setStartDate(domain.getStartDate());
        return student;
    }
}
