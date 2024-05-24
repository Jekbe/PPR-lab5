package Processes;

import javax.jms.*;
import java.util.Random;

public class ProcessD extends Thread {
    private final Connection connection;
    private final Session session;
    private final MessageConsumer consumerAD;
    private final MessageProducer producerDC;
    private final Random random;

    public ProcessD() throws jakarta.jms.JMSException, JMSException {
        random = new Random();

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

    private void waitRandomTime(){
        try{
            Thread.sleep((random.nextInt(4) + 1) * 1000);
        } catch (InterruptedException e) {
            System.out.println("Błąd: " + e);
        }
    }
}
