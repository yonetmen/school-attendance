package com.plusnet.managedbean;

import com.plusnet.entity.Teacher;
import com.plusnet.facade.TeacherFacade;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class TeacherManagedBean {

    @EJB
    private TeacherFacade teacherFacade;
    private Teacher teacher;

    public TeacherManagedBean() {}

    @PostConstruct
    public void init() {
        teacher = new Teacher();
    }

    @PreDestroy
    public void destroy() {
        teacher = null;
    }

    public TeacherFacade getTeacherFacade() {
        return teacherFacade;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String authenticateTeacher() {
        String outcome = null;

        Teacher t = teacherFacade.authenticateTeacher(teacher.getUserName(),
                teacher.getPassword());
        if (t != null) {
            outcome = "index?faces-redirect=true";
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, null,
                    "FEL: Användernamn/Lösenord är fel");
            FacesContext.getCurrentInstance().addMessage("teacher_login:teacher_username", message);
        }
        return outcome;
    }
    
    public String logout() {
        return "login";
    }
}
