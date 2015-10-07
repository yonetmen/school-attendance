package com.plusnet.mdb;

import com.plusnet.domain.StudentDomain;
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
            ObjectMessage objectMessage = (ObjectMessage) message;
            StudentDomain std = (StudentDomain) objectMessage.getObject();
            System.out.println("STUDENT RECEIVED:::" + std.getFirstName() + " " + std.getLastName());
        } catch (JMSException jmse) {
            System.err.println(jmse.getMessage());
        }
    }
}
