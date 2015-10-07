package com.plusnet.managedbean;

import com.plusnet.domain.AttendanceDomain;
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

    private StudentDomain studentDomain;
    private List<StudentDomain> attendants;
    private List<StudentDomain> studentListByCourseName;
    private String courseName;
    private List<Integer> studentIds;
    private List<Integer> studentListSendToRektor;
    private List<JmsContent> jmsContent;
    private Date date;

    public AttendanceManagedBean() {
    }

    @PostConstruct
    public void init() {
        studentIds = new ArrayList<>();
        studentListSendToRektor = new ArrayList<>();
        date = new Date();
        attendants = new ArrayList<>();
        jmsContent = new ArrayList<>();
    }

    public void getStudentsByCourseName() {
        studentListByCourseName = studentFacade.getStudentListByCourseName(courseName);
    }

    public void dateControlForCourse() {
        studentIds = attendanceFacade.getStudentIdsByAttendanceDate(date);
        if (!attendants.isEmpty()) {
            for (StudentDomain sd : attendants) {
                if (studentIds.contains(sd.getId()) && !attendanceFacade.getCourseNameByStudentId(sd.getId(), courseName)) {
                    studentListSendToRektor.add(sd.getId());
                    AttendanceDomain attendance = new AttendanceDomain();
                    attendance.setAttended((short) 1);
                    attendance.setRecordDate(date);
                    attendance.setStudentDomain(sd);
                    attendance.setCourseName(courseName);
                    attendanceFacade.createAttendance(attendance);
                    JmsContent item = new JmsContent(sd.getFirstName() + " " + sd.getLastName(),
                                                    sd.getEmail(), date, courseName);
                    jmsContent.add(item);
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, courseName, "Närvaro lista sparats"));
        }
    }
    
    // NOT WORKING
    public void sendJMS() {
        JmsSenderBean jms = new JmsSenderBean();
        StudentDomain std = attendants.get(0);
        jms.sendJms(std);
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

    public List<Integer> getStudentListSendToRektor() {
        return studentListSendToRektor;
    }

    public void setStudentListSendToRektor(List<Integer> studentListSendToRektor) {
        this.studentListSendToRektor = studentListSendToRektor;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<JmsContent> getJmsContent() {
        return jmsContent;
    }

    public void setJmsContent(List<JmsContent> jmsContent) {
        this.jmsContent = jmsContent;
    }
    
    public class JmsContent {
        
        private String studentName;
        private String studentEmail;
        private Date date;
        private String courseName;

        public JmsContent() {
        }

        public JmsContent(String studentName, String studentEmail, Date date, String courseName) {
            this.studentName = studentName;
            this.studentEmail = studentEmail;
            this.date = date;
            this.courseName = courseName;
        }

        public String getStudentName() {
            return studentName;
        }

        public void setStudentName(String studentName) {
            this.studentName = studentName;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }    
    }
}
