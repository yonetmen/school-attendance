package com.plusnet.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findById", query = "SELECT a FROM Attendance a WHERE a.id = :id"),
    @NamedQuery(name = "Attendance.findByIsAttended", query = "SELECT a FROM Attendance a WHERE a.isAttended = :isAttended"),
    @NamedQuery(name = "Attendance.findByRecordDate", query = "SELECT a FROM Attendance a WHERE a.recordDate = :recordDate"),
    @NamedQuery(name = "Attendance.findByCourseName", query = "SELECT a FROM Attendance a WHERE a.courseName = :courseName")})
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ATTENDED")
    private short isAttended;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "RECORD_DATE")
    @Temporal(TemporalType.DATE)
    private Date recordDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "COURSE_NAME")
    private String courseName;
    
    @JoinColumn(name = "student_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Student student;

    public Attendance() {
    }

    public Attendance(Integer id) {
        this.id = id;
    }

    public Attendance(Integer id, short isAttended, Date recordDate) {
        this.id = id;
        this.isAttended = isAttended;
        this.recordDate = recordDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public short getIsAttended() {
        return isAttended;
    }

    public void setIsAttended(short isAttended) {
        this.isAttended = isAttended;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.plusnet.entity.Attendance[ id=" + id + " ]";
    }

}
