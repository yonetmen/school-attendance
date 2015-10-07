package com.plusnet.managedbean;

import com.plusnet.domain.AttendanceDomain;
import com.plusnet.domain.JmsContent;
import com.plusnet.domain.StudentDomain;
import com.plusnet.facade.AttendanceFacade;
import com.plusnet.facade.StudentFacade;
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

    private StudentDomain studentDomain;
    private List<StudentDomain> attendants;
    private List<StudentDomain> studentListByCourseName;
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
            for (StudentDomain sd : attendants) {
                if (!attendanceFacade.getCourseNameByStudentId(sd.getId(), courseName)) {
                    AttendanceDomain attendance = new AttendanceDomain();
                    attendance.setAttended((short) 1);
                    attendance.setRecordDate(date);
                    attendance.setStudentDomain(sd);
                    attendance.setCourseName(courseName);
                    attendanceFacade.createAttendance(attendance);
                    JmsContent item = new JmsContent(sd.getFirstName() + " " + sd.getLastName(),
                                                    sd.getEmail(), date, courseName);
                    jmsContents.add(item);
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, courseName, "Närvaro lista sparats"));
        }
    }
    
    // Working now
    public void sendJMS() throws Exception {
        jmsSender.sendMessage(jmsContents);
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

    public StudentDomain getStudentDomain() {
        return studentDomain;
    }

    public void setStudentDomain(StudentDomain studentDomain) {
        this.studentDomain = studentDomain;
    }

    public List<StudentDomain> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<StudentDomain> attendants) {
        this.attendants = attendants;
    }

    public List<StudentDomain> getStudentListByCourseName() {
        return studentListByCourseName;
    }

    public void setStudentListByCourseName(List<StudentDomain> studentListByCourseName) {
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
