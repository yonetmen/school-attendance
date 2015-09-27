package com.plusnet.managedbean;

import com.plusnet.entity.Course;
import com.plusnet.facade.CourseFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class CourseManagedBean {

    @EJB
    private CourseFacade courseFacade;
    private Course course;
    private List<Course> courseList;
    
    public CourseManagedBean() {
        course = new Course();
    }

    public void createCourse() {
        courseFacade.create(course);
        course = new Course();
    }
    
    public List<Course> getCourses() {
        return courseList = courseFacade.findAll();
    }
    
    public void editCourse(Course c) {
        courseFacade.edit(course);
        course = new Course();
    }
    
    public void deleteCourse(Course course) {
        courseFacade.remove(course);
    }
    
    
    // GETTERS & SETTERS
    public Course getCourse() {
        return course;
    }

    public CourseFacade getCourseFacade() {
        return courseFacade;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCourseFacade(CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }    
}
