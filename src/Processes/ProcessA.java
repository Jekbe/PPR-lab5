package Processes;

import javax.jms.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class ProcessA extends Thread {
    private final Connection connection;
    private final Session session;
    private final MessageProducer producerAB, producerAD;
    private final MessageConsumer consumerCA;
    private final Random random;

    public ProcessA() throws JMSException, jakarta.jms.JMSException {
        random = new Random();

        ConnectionFactory connectionFactory = Config.JMS.getConnectionFactory();
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        producerAB = session.createProducer(session.createQueue("A-B"));
        consumerCA = session.createConsumer(session.createQueue("C-A"));
        producerAD = session.createProducer(session.createQueue("A-D"));
    }

    @Override
    public void run(){
        ObjectMessage message;
        double[] reciveTab, tab;

        while (true){
            tab = generate();

            System.out.println("Send to B");
            try{
                message = session.createObjectMessage(tab);
                producerAB.send(message);
                producerAD.send(message);

                waitRandomTime();
                message = (ObjectMessage) consumerCA.receive();
                if (message == null) break;
                reciveTab = (double[]) message.getObject();

                System.out.println("Otrzymana tablica: " + Arrays.toString(reciveTab) + "; Destination: " + message.getJMSDestination().toString());
                waitRandomTime();
            } catch (JMSException e){
                System.out.println("Błąd: " + e);
            }
        }
    }

    public void destroy() throws JMSException {
        connection.close();
    }

    private double[] generate(){
        return IntStream.range(0, 100).mapToDouble(_ -> random.nextInt(900000) + 100000).toArray();
    }

    private void waitRandomTime(){
        try{
            Thread.sleep((random.nextInt(4) + 1) * 1000);
        } catch (InterruptedException e) {
            System.out.println("Błąd: " + e);
        }
    }
}

