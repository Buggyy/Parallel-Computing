package activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import remote.Server;

import javax.jms.*;
import java.util.logging.Logger;

import static helper.Config.ACTIVEMQ_URL;
import static helper.CustomUtilities.*;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class Producer {
    // naam van de queue
    private static String subject = "test1";
    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    static final int NODES = 2;

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
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

    /**
     *
     * @param str
     * @param session
     * @param queue
     * @throws JMSException
     */
    private static void sendMessage(String str, Session session, String queue) throws JMSException {
        Destination destination = session.createQueue(queue);
        MessageProducer producer = session.createProducer(destination);

        TextMessage message = session.createTextMessage(str);
        producer.send(message);

        LOGGER.info("Bericht verzonden");
    }
}
