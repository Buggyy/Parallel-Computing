package com.hva;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static com.hva.BucketSortfromQueue.ACTIVEMQ_URL;

/**
 * The ActiveMQ Consumer Class
 *
 */
public class Consumer {
    //    Queue name of Consumer
    private static String subject = "testQueue2";

    public static void main(String[] args) throws JMSException {

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
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
