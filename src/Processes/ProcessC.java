package Processes;

import javax.jms.*;
import java.util.Random;

public class ProcessC extends Thread{
    private final Connection connection;
    private final Session session;
    private final MessageConsumer consumerBC, consumerDC;
    private final MessageProducer producerCA;
    private final Random random;

    public ProcessC() throws jakarta.jms.JMSException, JMSException {
        random = new Random();

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
        ObjectMessage objectMessage;
        double[] tabB, tabD, tab;

        while(true){
            try {
                objectMessage = (ObjectMessage) consumerBC.receive();
                if(objectMessage == null) break;
                tabB = (double[]) objectMessage.getObject();

                objectMessage = (ObjectMessage) consumerDC.receive();
                if(objectMessage == null) break;
                tabD = (double[]) objectMessage.getObject();

                tab = new double[tabB.length];
                for (int f1 = 0; f1 < tabD.length; f1++) tab[f1] = tabB[f1] - tabD[f1];

                objectMessage = session.createObjectMessage(tab);
                producerCA.send(objectMessage);

                waitRandomTime();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
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
