package activemq; /**ConnectionFactory
 * Created by koningrde on 10-4-2017.
 */

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    // either connect to the remote ActiveMQ running on the PI, or on the localhost
    private static String url = "failover:(tcp://169.254.1.1:61616,localhost:8161)";
    private static String subject = "testQueue2";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive();
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received'"+textMessage.getText() + "'");
        }
        connection.close();
    }
}
