package Config;

import jakarta.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.ConnectionFactory;

public class JMS {
    public static ConnectionFactory getConnectionFactory () throws JMSException {
        return (ConnectionFactory) new ActiveMQConnectionFactory("vm://localhost").createConnection();
    }
}
