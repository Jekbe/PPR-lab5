package Processes;

import javax.jms.*;
import java.util.Random;

public class ProcessA extends Thread {
    private final Session session;
    private final Connection con;
    private final MessageProducer producerAB;
    private final MessageConsumer consumerCA;
    private final MessageConsumer consumerAD;
    Random random;

    public ProcessA() throws JMSException, jakarta.jms.JMSException {
        random = new Random();

        ConnectionFactory factory = Config.JMS.getConnectionFactory();
        con = factory.createConnection();
        con.start();

        session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queueAB = session.createQueue("A-B");
        producerAB = session.createProducer(queueAB);
        Queue queueCA = session.createQueue("C-A");
        consumerCA = session.createConsumer(queueCA);
        Queue queueAD = session.createQueue("A-D");
        consumerAD = session.createConsumer(queueAD);
    }

    @Override
    public void run(){
        while (true){
            double[] tab = new double[100];
            for (int f1 = 0; f1 < 100; f1++) {
                tab[f1] = random.nextInt(900000) + 100000;
            }

            try{
                ObjectMessage message = session.createObjectMessage(tab);
            } catch (JMSException e){
                System.out.println("Błąd: " + e);
            }

        }

    }
}

