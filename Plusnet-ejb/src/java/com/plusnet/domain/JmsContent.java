/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plusnet.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Kasim
 */
public class JmsContent implements Serializable {
        
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
