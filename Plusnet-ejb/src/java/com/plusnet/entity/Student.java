package com.plusnet.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author Kasim
 */
@Entity
@Table(name = "student")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByStudentId", query = "SELECT s FROM Student s WHERE s.studentId = :studentId"),
    @NamedQuery(name = "Student.findByFirstName", query = "SELECT s FROM Student s WHERE s.firstName = :firstName"),
    @NamedQuery(name = "Student.findByLastName", query = "SELECT s FROM Student s WHERE s.lastName = :lastName"),
    @NamedQuery(name = "Student.findByTelefon", query = "SELECT s FROM Student s WHERE s.telefon = :telefon"),
    @NamedQuery(name = "Student.findByAddress", query = "SELECT s FROM Student s WHERE s.address = :address"),
    @NamedQuery(name = "Student.findByEmail", query = "SELECT s FROM Student s WHERE s.email = :email"),
    @NamedQuery(name = "Student.findByStartDate", query = "SELECT s FROM Student s WHERE s.startDate = :startDate")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "student_id")
    private Integer studentId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 45, message = " (Mata in mellan 2-45 tecken)")
    @Column(name = "first_name")
    private String firstName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 2, max = 70, message = " (Mata in mellan 2-70 tecken)")
    @Column(name = "last_name")
    private String lastName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 10, max = 13, message = " (Mata in mellan 10-13 tecken)")
    @Column(name = "telefon")
    private String telefon;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 10, max = 150, message = " (Mata in mellan 10-150 tecken)")
    @Column(name = "address")
    private String address;
    
    @Email(message = " (Ogiltig email)")
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 60, message = " (Mata in mellan 5-60 tecken)")
    @Column(name = "email")
    private String email;
    
    @Basic(optional = false)
    @NotNull(message = " (Kan inte vara tomt)")
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    
    @ManyToMany(mappedBy = "studentList")
    private List<Attendance> attendanceList;
    
    @ManyToMany(mappedBy = "studentList")
    private List<Course> courseList;

    public Student() {
    }

    public Student(Integer studentId) {
        this.studentId = studentId;
    }

    public Student(Integer studentId, String firstName, String lastName, String telefon, String address, String email, Date startDate) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.telefon = telefon;
        this.address = address;
        this.email = email;
        this.startDate = startDate;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @XmlTransient
    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @XmlTransient
    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        return !((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId)));
    }

    @Override
    public String toString() {
        return "com.plusnet.entity.Student[ studentId=" + studentId + " ]";
    }
}