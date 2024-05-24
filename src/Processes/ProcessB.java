package Processes;

import javax.jms.*;
import java.util.Arrays;
import java.util.Random;

public class ProcessB extends Thread{
    private final Connection connection;
    private final Session session;
    private final MessageConsumer consumerAB;
    private final MessageProducer producerBC;
    private final Random random;

    public ProcessB() throws jakarta.jms.JMSException, JMSException {
        random = new Random();

        ConnectionFactory connectionFactory = Config.JMS.getConnectionFactory();
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        consumerAB = session.createConsumer(session.createQueue("A-B"));
        producerBC = session.createProducer(session.createQueue("B-C"));
    }

    @Override
    public void run(){
        ObjectMessage objectMessage;
        double[] tab;

        while (true){
            try {
                objectMessage = (ObjectMessage) consumerAB.receive();
                if (objectMessage == null) break;
                tab = (double[]) objectMessage.getObject();
                tab = Arrays.stream(tab).map(Math::log).toArray();

                waitRandomTime();
                objectMessage = session.createObjectMessage(tab);
                producerBC.send(objectMessage);

                waitRandomTime();
            } catch (JMSException e) {
                System.out.println("Błąd: " + e);
            }
        }
    }

    public void destroy() throws javax.jms.JMSException {
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
