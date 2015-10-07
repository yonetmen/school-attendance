package com.plusnet.managedbean;

import com.plusnet.domain.JmsContent;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

/**
 *
 * @author Kasim
 */
@ManagedBean(name = "jmsSender")
@SessionScoped
public class JmsSenderBean implements Serializable {

    @Resource(mappedName = "jms/myQueue")
    private Queue myQueue;

    @Resource(mappedName = "jms/myQueueConnectionFactory")
    private ConnectionFactory factory;

    public JmsSenderBean() {}

    public void sendMessage(List<JmsContent> list) {
        sendJMSMessageToMyQueue(list);
    }

    private void sendJMSMessageToMyQueue(List<JmsContent> attendantList) {
        try {
            Connection conn = factory.createConnection();
            Session session = conn.createSession();
            MessageProducer producer = session.createProducer(myQueue);
            ObjectMessage objectMessage = session.createObjectMessage((Serializable) attendantList);
            producer.send(objectMessage);
        } catch (JMSException ex) {
            Logger.getLogger(JmsSenderBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
