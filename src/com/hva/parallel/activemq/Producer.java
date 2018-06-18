package activemq;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import static utilities.Utilities.*;

/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */
public class Producer {
    // url voor het verbinden met de Raspberry PI
    Private static String url = "tcp://192.252.1.1:61616"  // klopt dit?
    private static String subject = "testQueue1"; // Queue Name

    public static final int NODES = 2;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        int[] array = fillArray(10);
        shuffleArray(array);

        int[][] chunkArray = chunkArray(array, array.length / NODES);

        for (int i = 0; i < chunkArray.length; i++) {
            sendMessage(arrayToString(chunkArray[i]), session,
                    "queue" + (i + 1));
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
