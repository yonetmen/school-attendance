//package com.plusnet.managedbean;
//
//import com.plusnet.entity.Course;
//import com.plusnet.entity.Student;
//import com.plusnet.facade.CourseFacade;
//import com.plusnet.facade.StudentFacade;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//import javax.ejb.EJB;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
//
//@ManagedBean
//@SessionScoped
//public class RelationerMenagedBean implements Serializable {
//
//    @EJB
//    private StudentFacade studentFacade;
//
//    @EJB
//    private CourseFacade courseFacade;
//
//    private int studentId;
//    private int courseId;
//
//    private List<Student> studentList;
//    private List<Course> courseList;
//
//    private Map<Student, Course> relationsMap;
//    private List<Entry<Student, Course>> relationsAsList;
//
//    public RelationerMenagedBean() {
//    }
//
//    public Map<Student, Course> getRelationsMap() {
//        return relationsMap;
//    }
//
//    public void setRelationsMap(Map<Student, Course> relationsMap) {
//        this.relationsMap = relationsMap;
//    }
//
//    public List<Entry<Student, Course>> getRelationsAsList() {
//        return relationsAsList;
//    }
//
//    public void setRelationsAsList(List<Entry<Student, Course>> relationsAsList) {
//        this.relationsAsList = relationsAsList;
//    }
//
//    public List<Student> getStudents() {
//        return studentList = studentFacade.findAll();
//    }
//
//    public List<Course> getCourses() {
//        return courseList = courseFacade.findAll();
//    }
//
//    public CourseFacade getCourseFacade() {
//        return courseFacade;
//    }
//
//    public StudentFacade getStudentFacade() {
//        return studentFacade;
//    }
//
//    public List<Student> getStudentList() {
//        return studentList;
//    }
//
//    public void setStudentList(List<Student> studentList) {
//        this.studentList = studentList;
//    }
//
//    public List<Course> getCourseList() {
//        return courseList;
//    }
//
//    public void setCourseList(List<Course> courseList) {
//        this.courseList = courseList;
//    }
//
//    public int getCourseId() {
//        return courseId;
//    }
//
//    public int getStudentId() {
//        return studentId;
//    }
//
//    public void setCourseId(int courseId) {
//        this.courseId = courseId;
//    }
//
//    public void setStudentId(int studentId) {
//        this.studentId = studentId;
//    }
//
//    public void add() {
//        studentFacade.addCourse(studentId, courseId);
//        courseFacade.addStudent(studentId, courseId);
//    }
//
//    public List<Entry<Student, Course>> getRelations() {
//        List<Course> courses = courseFacade.findAll();
//        List<Student> students = studentFacade.findAll();
//
//        return relationsAsList = new ArrayList<>(relationsMap.entrySet());
//    }
//    
//}
