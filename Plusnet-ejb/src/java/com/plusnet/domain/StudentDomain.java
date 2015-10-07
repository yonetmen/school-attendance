package com.plusnet.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StudentDomain implements Serializable {
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private Date startDate;
    private List<CourseDomain> courseList;
    private List<AttendanceDomain> attendanceList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<CourseDomain> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseDomain> courseList) {
        this.courseList = courseList;
    }

    public List<AttendanceDomain> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<AttendanceDomain> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
