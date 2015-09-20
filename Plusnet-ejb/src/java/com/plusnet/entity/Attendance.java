/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Kasim
 */
@Entity
@Table(name = "attendance")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attendance.findAll", query = "SELECT a FROM Attendance a"),
    @NamedQuery(name = "Attendance.findByAttendanceId", query = "SELECT a FROM Attendance a WHERE a.attendanceId = :attendanceId"),
    @NamedQuery(name = "Attendance.findByIsAttended", query = "SELECT a FROM Attendance a WHERE a.isAttended = :isAttended"),
    @NamedQuery(name = "Attendance.findByRecordDate", query = "SELECT a FROM Attendance a WHERE a.recordDate = :recordDate")})
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "attendance_id")
    private Integer attendanceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_attended")
    private boolean isAttended;
    @Basic(optional = false)
    @NotNull
    @Column(name = "record_date")
    @Temporal(TemporalType.DATE)
    private Date recordDate;
    @JoinTable(name = "student_has_attendance", joinColumns = {
        @JoinColumn(name = "attendance_id", referencedColumnName = "attendance_id")}, inverseJoinColumns = {
        @JoinColumn(name = "student_id", referencedColumnName = "student_id")})
    @ManyToMany
    private List<Student> studentList;

    public Attendance() {
    }

    public Attendance(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Attendance(Integer attendanceId, boolean isAttended, Date recordDate) {
        this.attendanceId = attendanceId;
        this.isAttended = isAttended;
        this.recordDate = recordDate;
    }

    public Integer getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Integer attendanceId) {
        this.attendanceId = attendanceId;
    }

    public boolean getIsAttended() {
        return isAttended;
    }

    public void setIsAttended(boolean isAttended) {
        this.isAttended = isAttended;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    @XmlTransient
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceId != null ? attendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendanceId == null && other.attendanceId != null) || (this.attendanceId != null && !this.attendanceId.equals(other.attendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.plusnet.entity.Attendance[ attendanceId=" + attendanceId + " ]";
    }
    
}
