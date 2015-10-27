package com.plusnet.managedbean;

import com.plusnet.entity.Course;
import com.plusnet.entity.Student;
import com.plusnet.facade.CourseFacade;
import com.plusnet.facade.StudentFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.model.DualListModel;

@ManagedBean
@SessionScoped
public class StudentManagedBean implements Serializable {

    @EJB
    private StudentFacade studentFacade;
    @EJB
    private CourseFacade courseFacade;
    private Student student;
    private List<Student> studentList;
    
    // Student Edit : Course Pick List
    private DualListModel<String> courses;
    private List<Course> coursesTarget;
    private List<Course> coursesSource;

    public StudentManagedBean() {
    }

    @PostConstruct
    public void init() {
        student = new Student();
        courses = new DualListModel<>();
    }

    public void createStudent() {
        studentFacade.create(student);
        student = new Student();
    }

    public void getDualListModelForStudent(Student std) {
        if (std != null) {
            student = std;
            // Get participated course list for selected student
            coursesTarget = studentFacade.find(std.getId()).getCourseList();
            // Get Available course list for selected student by id
            coursesSource = studentFacade.findAvailableCoursesForStudent(std.getId());
            List<String> sourceCourseNames = getCourseNames(coursesSource);
            List<String> targetCourseNames = getCourseNames(coursesTarget);
            courses = new DualListModel<>(sourceCourseNames, targetCourseNames);
        }
    }

    public List<String> getCourseNames(List<Course> courseList) {
        List<String> names = new ArrayList<>();
        for (Course course : courseList) {
            String name = course.getCourseName();
            if (!names.contains(name)) {
                names.add(name);
            }
        }
        return names;
    }

    public void updateStudent() {
        List<String> selectedCourseNames = courses.getTarget();
        List<Course> selectedCourses = new ArrayList<>();
        for (String courseName : selectedCourseNames) {
            Course course = courseFacade.findByName(courseName);
            if (!coursesTarget.contains(course)) {
                selectedCourses.add(course);
            }
        }
        studentFacade.addToStudentsCourseList(student.getId(), selectedCourses);
        studentFacade.edit(student);
        student = new Student();
    }

    public void deleteStudent(Student student) {
        studentFacade.remove(student);
    }

    public int studentCount() {
        return studentFacade.count();
    }

    public long getStudentCountByCourseLanguage(String language) {
        return studentFacade.getStudentCountByCourseLanguage(language);
    }

    // Showing courselist for selected Student in "Student Details" dialog.
    public List<Course> getCourseListByStudentId(int studentId) {
        return studentFacade.findCourseListByStudentId(studentId);
    }

    public List<Student> getStudents() {
        return studentList = studentFacade.findAll();
    }

    public void closePopup() {
        student = new Student();
    }

    // GETTER AND SETTER //
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public void setStudentFacade(StudentFacade studentFacade) {
        this.studentFacade = studentFacade;
    }
}
