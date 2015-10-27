package com.plusnet.mdb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author Kasim
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/myQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class JmsReceiver implements MessageListener {

    public JmsReceiver() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            List<JmsContent> listData = null;
            ObjectMessage objMessage = (ObjectMessage) message;

            listData = (ArrayList) objMessage.getObject();

            for (JmsContent item : listData) {
                System.out.println("Student Namn: " + item.getStudentName());
                System.out.println("Kurs Namn: " + item.getCourseName());
                System.out.println("Datum: " + item.getDate());
            }
        } catch (JMSException jmse) {
            System.err.println(jmse.getMessage());
        }
    }
}
