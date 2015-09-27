package com.plusnet.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Course.findAll", query = "SELECT c FROM Course c"),
    @NamedQuery(name = "Course.findById", query = "SELECT c FROM Course c WHERE c.id = :id"),
    @NamedQuery(name = "Course.findByCourseName", query = "SELECT c FROM Course c WHERE c.courseName = :courseName"),
    @NamedQuery(name = "Course.findByCourseCode", query = "SELECT c FROM Course c WHERE c.courseCode = :courseCode"),
    @NamedQuery(name = "Course.findByLanguage", query = "SELECT c FROM Course c WHERE c.courseLanguage = :courseLanguage"),
    @NamedQuery(name = "Course.findByCourseLevel", query = "SELECT c FROM Course c WHERE c.courseLevel = :courseLevel"),
    @NamedQuery(name = "Course.findByResponsible", query = "SELECT c FROM Course c WHERE c.responsible = :responsible")})
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 45, message = " (Mata in mellan 5-45 tecken)")
    @Column(name = "COURSE_NAME")
    private String courseName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 4, max = 6, message = " (Mata in mellan 4-6 tecken)")
    @Column(name = "COURSE_CODE")
    private String courseCode;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 45, message = " (Mata in mellan 5-45 tecken)")
    @Column(name = "COURSE_LANGUAGE")
    private String courseLanguage;
    
    @Basic(optional = false)
    @NotNull(message = " (Kan inte vara tom)")
    @Size(min = 4, max = 10)
    @Column(name = "COURSE_LEVEL")
    private String courseLevel;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 45, message = " (Mata in mellan 5-45 tecken)")
    @Column(name = "RESPONSIBLE")
    private String responsible;
    
    @JoinTable(name = "student_has_course", joinColumns = {
        @JoinColumn(name = "course_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "student_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<Student> studentList;

    public Course() {
    }

    public Course(Integer id) {
        this.id = id;
    }

    public Course(Integer id, String courseName, String courseCode, String language, String courseLevel, String responsible) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.courseLanguage = language;
        this.courseLevel = courseLevel;
        this.responsible = responsible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseLanguage() {
        return courseLanguage;
    }

    public void setCourseLanguage(String courseLanguage) {
        this.courseLanguage = courseLanguage;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Course)) {
            return false;
        }
        Course other = (Course) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.plusnet.entity.Course[ id=" + id + " ]";
    }

}
