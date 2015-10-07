/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plusnet.managedbean;

import com.plusnet.domain.StudentDomain;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

/**
 *
 * @author Kasim
 */
@ManagedBean
@RequestScoped
public class JmsSenderBean {

    @Resource(mappedName = "jms/myQueue")
    private Queue myQueue;

    @Inject
    @JMSConnectionFactory("jms/myQueueConnectionFactory")
    private JMSContext context;

    public void sendJms(StudentDomain student) {
        sendJMSMessageToMyQueue(student);
    }
    
    private void sendJMSMessageToMyQueue(StudentDomain student) {
        ObjectMessage objectMessage = context.createObjectMessage(student);
        context.createProducer().send(myQueue, objectMessage);
    }
}
