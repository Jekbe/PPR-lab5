package Processes;

import javax.jms.*;

public class ProcessD extends Thread {
    private final Connection connection;
    private final Session session;
    private final MessageConsumer consumerAD;
    private final MessageProducer producerDC;

    public ProcessD() throws jakarta.jms.JMSException, JMSException {
        ConnectionFactory connectionFactory = Config.JMS.getConnectionFactory();
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        consumerAD = session.createConsumer(session.createQueue("A-D"));
        producerDC = session.createProducer(session.createQueue("D-C"));
    }

    @Override
    public void run(){

    }

    public void destroy() throws JMSException {
        connection.close();
    }
}
