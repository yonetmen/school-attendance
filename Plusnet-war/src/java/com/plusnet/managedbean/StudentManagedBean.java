package com.plusnet.managedbean;

import com.plusnet.domain.StudentDomain;
import com.plusnet.entity.Course;
import com.plusnet.facade.CourseFacade;
import com.plusnet.facade.StudentFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@SessionScoped
public class StudentManagedBean implements Serializable {

    @EJB
    private StudentFacade studentFacade;
    @EJB
    private CourseFacade courseFacade;
    private StudentDomain studentDomain;
    private List<StudentDomain> studentDomainList;

    // Student Edit : Course Pick List
    private DualListModel<String> courses;
    private List<Course> coursesTarget;
    private List<Course> coursesSource;

    public StudentManagedBean() {

    }

    @PostConstruct
    public void init() {
        studentDomain = new StudentDomain();
        courses = new DualListModel<>();
    }

    public void createStudent() {
        try {
            studentFacade.create(studentDomain);
            studentDomain = new StudentDomain();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    null, " (Email finns i vårt system.)");
            FacesContext.getCurrentInstance().addMessage("registerForm:email", message);
        }
    }

    public void deleteStudent(int studentId) {
        studentFacade.removeStudent(studentId);
    }

//    public void onTransfer(TransferEvent event) throws Exception {
//        if (event != null) {
//            
//            
//            
//            
//            // Gets all course in target pickList.
//            List<String> coursesNames = (List<String>) event.getItems();
//            for (String name : coursesNames) {
//                Course course = courseFacade.findByName(name);
//                coursesTarget.add(course);
//            }
//        }
//    }
    public void editStudent() {

        List<String> selectedCourseNames = courses.getTarget();
        List<Course> selectedCourses = new ArrayList<>(selectedCourseNames.size());
        for (String courseName : selectedCourseNames) {
            Course course = courseFacade.findByName(courseName);
            selectedCourses.add(course);
        }

        // Convert target course list from String to CourseList objects
//        List<Course> selectedCourses = convertFromStringToObject();
        // Set all target courses from PickList into Student.
        studentFacade.addToStudentsCourseList(studentDomain.getId(), selectedCourses);

        studentFacade.editStudent(studentDomain);
        studentDomain = new StudentDomain();
    }

//    private List<Course> convertFromStringToObject() {
//        List<String> selectedCourseNames = courses.getTarget();
//        List<Course> selectedCourses = new ArrayList<>(selectedCourseNames.size());
//        for (String courseName : selectedCourseNames) {
//            Course course = courseFacade.findByName(courseName);
//            selectedCourses.add(course);
//        }
//        return selectedCourses;
//    }
    
    public void getDualListModelForStudent(StudentDomain std) {
        if (std != null) {
            setStudentDomain(std);
            
            // Get participated course list for selected student
            coursesTarget = studentFacade.getCourseListByStudentId(studentDomain.getId());

            // Get Available course list for selected student by id
            coursesSource = studentFacade.findAvailableCoursesForStudent(studentDomain.getId());

            List<String> sourceCourseNames = getCourseNames(coursesSource);
            List<String> targetCourseNames = getCourseNames(coursesTarget);

            courses = new DualListModel<>(sourceCourseNames, targetCourseNames);
        }
    }

    public List<String> getCourseNames(List<Course> courseList) {
        List<String> names = new ArrayList<>();
        for (Course course : courseList) {
            String name = course.getCourseName();
            names.add(name);
        }
        return names;
    }

    public int studentCount() {
        return studentFacade.count();
    }

    public long getStudentCountByCourseLanguage(String language) {
        return studentFacade.getStudentCountByCourseLanguage(language);
    }

    // Showing courselist for selected Student in "Student Details" dialog.
    public List<Course> getCourseListByStudentId(int studentId) {
        return studentFacade.getCourseListByStudentId(studentId);
    }

    public List<StudentDomain> getStudents() {
        return studentDomainList = studentFacade.findAllStudents();
    }

    public void closePopup() {
        studentDomain = new StudentDomain();
    }

    // GETTER AND SETTER //
    public StudentDomain getStudentDomain() {
        return studentDomain;
    }

    public void setStudentDomain(StudentDomain studentDomain) {
        this.studentDomain = studentDomain;
    }

    public DualListModel<String> getCourses() {
        return courses;
    }

    public void setCourses(DualListModel<String> courses) {
        this.courses = courses;
    }

    public List<Course> getCoursesTarget() {
        return coursesTarget;
    }

    public void setCoursesTarget(List<Course> coursesTarget) {
        this.coursesTarget = coursesTarget;
    }

    public CourseFacade getCourseFacade() {
        return courseFacade;
    }

    public void setCourseFacade(CourseFacade courseFacade) {
        this.courseFacade = courseFacade;
    }

    public List<Course> getCoursesSource() {
        return coursesSource;
    }

    public void setCoursesSource(List<Course> coursesSource) {
        this.coursesSource = coursesSource;
    }

    public List<StudentDomain> getStudentDomainList() {
        return studentDomainList;
    }

    public void setStudentDomainList(List<StudentDomain> studentDomainList) {
        this.studentDomainList = studentDomainList;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public void setStudentFacade(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }
}
