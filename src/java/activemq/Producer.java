package java.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

import static utilities.Utilities.*;

/**
 * Maintained and created by:
 * R. Lobato
 * C. Verra
 */

public class Producer {
    private static String subject = "testQueue1"; // Queue Name

    public static final int NODES = 2;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


        int[] array = generateArray(10);
        arrayShuffler(array);

        int[][] chunkArray = createChunksOfArray(array, array.length / NODES);

        if (chunkArray != null) {
            for (int i = 0; i < chunkArray.length; i++) {
                sendMessage(arrayToString(chunkArray[i]), session,
                        "queue" + (i + 1));
            }
        }

        connection.close();
    }

    private static void sendMessage(String str, Session session, String queue) throws JMSException {
        Destination destination = session.createQueue(queue);
        MessageProducer producer = session.createProducer(destination);

        TextMessage message = session.createTextMessage(str);
        producer.send(message);

        System.out.println("Sent Message");
    }
}
