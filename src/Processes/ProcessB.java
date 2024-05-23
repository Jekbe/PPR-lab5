package Processes;

import jakarta.jms.JMSException;
import javax.jms.*;

public class ProcessB extends Thread{
    private final Connection connection;
    private final Session session;
    private final MessageConsumer consumerAB;
    private final MessageProducer producerBC;

    public ProcessB() throws JMSException, javax.jms.JMSException {
        ConnectionFactory connectionFactory = Config.JMS.getConnectionFactory();
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        consumerAB = session.createConsumer(session.createQueue("A-B"));
        producerBC = session.createProducer(session.createQueue("B-C"));
    }

    @Override
    public void run(){

    }

    public void destroy() throws javax.jms.JMSException {
        connection.close();
    }
}
