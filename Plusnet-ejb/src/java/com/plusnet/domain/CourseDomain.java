package com.plusnet.domain;

import java.util.List;

public class CourseDomain {
    
    private String name;
    private String code;
    private String language;
    private String level;
    private String responsible;
    private List<StudentDomain> studentList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public List<StudentDomain> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentDomain> studentList) {
        this.studentList = studentList;
    }
}
