package com.plusnet.managedbean;

import com.plusnet.entity.Student;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class RelationerMenagedBean {

    public RelationerMenagedBean() {}
    
    public List<Student> getStudents() {
        return null;
    }
    
}
