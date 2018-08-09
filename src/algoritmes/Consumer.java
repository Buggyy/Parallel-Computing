package algoritmes;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import remote.Server;
import java.util.logging.Logger;

import static helper.Config.ACTIVEMQ_URL;

/**
 * De ActiveMQ Consumer Class
 * Maintained and created by:
 * R. Lobato
 */
public class Consumer {
    //    Queue naam van Consumer
    private static String subject = "test2";
    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());


    public static void main(String[] args) throws JMSException {

        LOGGER.info("Consumer gestart.\n");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);

        LOGGER.info("Ophalen Message van test2.\n");
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive();


        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;

            System.out.println(textMessage.getText());
        }

        connection.close();

        LOGGER.info("RMI is klaar.\n");

    }
}
