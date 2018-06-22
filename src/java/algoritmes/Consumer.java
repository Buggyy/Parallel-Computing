package java.algoritmes;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static java.algoritmes.BucketSortfromQueue.ACTIVEMQ_URL;

/**
 * De ActiveMQ Consumer Class
 * Maintained and created by:
 * R. Lobato
 */
public class Consumer {
    //    Queue naam van Consumer
    private static String subject = "testQueue2";

    public static void main(String[] args) throws JMSException {

        System.out.println("Consumer gestart.\n");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);

        System.out.println("Ophalen Message van testQueue2.\n");
        MessageConsumer consumer = session.createConsumer(destination);
        Message message = consumer.receive();


        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;

            System.out.println(textMessage.getText());
        }

        connection.close();

        System.out.println("RMI is klaar.\n");

    }
}
