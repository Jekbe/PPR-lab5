package Processes;

import javax.jms.*;

public class ProcessC extends Thread{
    private final Connection connection;
    private final Session session;
    private final MessageConsumer consumerBC, consumerDC;
    private final MessageProducer producerCA;

    public ProcessC() throws jakarta.jms.JMSException, JMSException {
        ConnectionFactory connectionFactory = Config.JMS.getConnectionFactory();
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        producerCA = session.createProducer(session.createQueue("C-A"));
        consumerBC = session.createConsumer(session.createQueue("B-C"));
        consumerDC = session.createConsumer(session.createQueue("D-C"));
    }

    @Override
    public void run(){

    }

    public void destroy() throws JMSException {
        connection.close();
    }
}
