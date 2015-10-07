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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StudentFacade() {
        super(Student.class);
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

    public List<Course> getCourseListByStudentId(int studentId) {
        List<Course> courseList = new ArrayList<>();
        Query query = em.createNativeQuery("SELECT c.* "
                + "FROM  course c "
                + "JOIN student_has_course shc ON (shc.course_ID = c.ID) "
                + "JOIN student s ON (s.ID = shc.student_ID) "
                + "WHERE s.ID = " + studentId + ";");
        List<Object[]> courseArray = (List<Object[]>) query.getResultList();
        for (Object[] courseFields : courseArray) {
            Course c = em.find(Course.class, courseFields[0]);
            courseList.add(c);
        }
        return courseList;
    }

    public List<Course> findAvailableCoursesForStudent(int studentId) {
        Query query = em.createNamedQuery("Course.findAll", Course.class
        );
        List<Course> allCourses = (List<Course>) query.getResultList();

        allCourses.removeAll(getCourseListByStudentId(studentId));
        return allCourses;
    }

    private void dropAllCoursesByStudentId(int studentId) {
        Query query = em.createNativeQuery("delete shc "
                + "FROM  student_has_course shc "
                + "WHERE shc.student_ID = " + studentId + ";");
        query.executeUpdate();
    }

    public List<StudentDomain> getStudentListByCourseName(String courseName) {
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
        List<StudentDomain> studentDomainList = convertEntityListToDomainList(studentList);
        return studentDomainList;
    }

    public List<StudentDomain> getStudentListById(List<Integer> studentListSendToRektor) {
        List<Student> studentList = new ArrayList<>(studentListSendToRektor.size());
        for (int studentId : studentListSendToRektor) {
            Query query = em.createNamedQuery("Student.findById", Student.class);
            query.setParameter("id", studentId);
            Student student = (Student) query.getSingleResult();
            studentList.add(student);
        }
        List<StudentDomain> studentDomainList = convertEntityListToDomainList(studentList);
        return studentDomainList;
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
