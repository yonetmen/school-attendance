package com.plusnet.domain;

import java.util.Date;

/**
 *
 * @author Kasim
 */
public class AttendanceDomain {
    
    private int id;
    private short attended;
    private Date recordDate;
    private StudentDomain studentDomain;

    public int getId() {
        return id;
    }

    public short getAttended() {
        return attended;
    }

    public void setAttended(short attended) {
        this.attended = attended;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public StudentDomain getStudentDomain() {
        return studentDomain;
    }

    public void setStudentDomain(StudentDomain studentDomain) {
        this.studentDomain = studentDomain;
    }
}
