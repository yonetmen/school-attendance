package com.plusnet.managedbean;

import com.plusnet.entity.Attendance;
import com.plusnet.entity.Student;
import com.plusnet.facade.AttendanceFacade;
import com.plusnet.facade.StudentFacade;
import com.plusnet.mdb.JmsContent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Kasim
 */
@ManagedBean
@SessionScoped
public class AttendanceManagedBean implements Serializable {

    @EJB
    private AttendanceFacade attendanceFacade;
    @EJB
    private StudentFacade studentFacade;
    
    @ManagedProperty(value = "#{jmsSender}")
    JmsSenderBean jmsSender;

    private Student student;
    private List<Student> attendants;
    private List<Student> studentListByCourseName;
    private String courseName;
    private List<Integer> studentIds;
    private List<JmsContent> jmsContents;
    private Date date;

    public AttendanceManagedBean() {
    }

    @PostConstruct
    public void init() {
        studentIds = new ArrayList<>();
        date = new Date();
        attendants = new ArrayList<>();
        jmsContents = new ArrayList<>();
    }

    public void getStudentsByCourseName() {
        studentListByCourseName = studentFacade.getStudentListByCourseName(courseName);
    }
    
    public void dateControlForCourse() {
        studentIds = attendanceFacade.getStudentIdsByAttendanceDate(date);
        if (!attendants.isEmpty()) {
            for (Student sd : attendants) {
                if (!attendanceFacade.getCourseNameByStudentId(sd.getId(), courseName)) {
                    Attendance attendance = new Attendance();
                    attendance.setIsAttended((short) 1);
                    attendance.setRecordDate(date);
                    attendance.setStudent(sd);
                    attendance.setCourseName(courseName);
                    attendanceFacade.create(attendance);
                    JmsContent item = new JmsContent(sd.getFirstName() + " " + sd.getLastName(),
                                                    sd.getEmail(), date, courseName);
                    jmsContents.add(item);
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, courseName, "Närvaro lista sparats"));
        }
    }
    
    // Sending JMS Messages from WAR project to EBJ project
    public void sendJMS() throws Exception {
        jmsSender.sendMessage(jmsContents);
        jmsContents = new ArrayList<>();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Tack!", "Närvaro listan skickats"));
    }

    // GETTERS & SETTERS //
    public AttendanceFacade getAttendanceFacade() {
        return attendanceFacade;
    }

    public StudentFacade getStudentFacade() {
        return studentFacade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<Student> attendants) {
        this.attendants = attendants;
    }

    public List<Student> getStudentListByCourseName() {
        return studentListByCourseName;
    }

    public void setStudentListByCourseName(List<Student> studentListByCourseName) {
        this.studentListByCourseName = studentListByCourseName;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setJmsSender(JmsSenderBean jmsSender) {
        this.jmsSender = jmsSender;
    }

    public List<JmsContent> getJmsContents() {
        return jmsContents;
    }

    public void setJmsContents(List<JmsContent> jmsContents) {
        this.jmsContents = jmsContents;
    }
}
