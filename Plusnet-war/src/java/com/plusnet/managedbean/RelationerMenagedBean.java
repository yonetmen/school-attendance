package com.plusnet.managedbean;

import com.plusnet.entity.Course;
import com.plusnet.entity.Student;
import com.plusnet.facade.CourseFacade;
import com.plusnet.facade.StudentFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RelationerMenagedBean {

    @EJB
    private StudentFacade studentFacade;
    
    @EJB
    private CourseFacade courseFacade;
    
    private Student student;
    private Course course;
    
    public RelationerMenagedBean() {}

    public CourseFacade getCourseFacade() {
        return courseFacade;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }
    
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    
    public List<Student> getStudents() {
        return null;
    }
    
    public void add(int studentId, int courseId) {
        Student s = studentFacade.find(studentId);
        Course c = courseFacade.find(courseId);
    }
    
}
