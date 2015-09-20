package com.plusnet.managedbean;

import com.plusnet.entity.Student;
import com.plusnet.facade.StudentFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class StudentManagedBean {

    @EJB
    private StudentFacade studentFacade;
    private Student student;
    private List<Student> studentList;

    public StudentManagedBean() {
        student = new Student();
    }

    public List<Student> getStudents() {
        return studentList = studentFacade.findAll();
    }

    public void createStudent() {
        try {
            studentFacade.create(student);
            student = new Student();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    null, " (Email finns i vårt system.)");
            FacesContext.getCurrentInstance().addMessage("registerForm:email", message);
        }
    }

    public void editStudent(Student std) {
        studentFacade.edit(student);
        student = new Student();
    }

    public void deleteStudent(Student std) {
        studentFacade.remove(std);
    }

    // GETTERS & SETTERS
    public Student getStudent() {
        return student;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentFacade(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

}
