package algoritmes;

import org.apache.activemq.ActiveMQConnectionFactory;
import remote.Server;

import javax.jms.*;
import java.util.Arrays;
import java.util.logging.Logger;

import static helper.Config.*;

/**
 * ActiveMQ ft. BucketSort
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class BucketSortfromQueue {

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	public static void main(String args[]) throws Exception {

		LOGGER.info("BucketSort van " + FROM_CSP + " gestart.\n");

		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue = session.createQueue(FROM_CSP);
        MessageConsumer consumer = session.createConsumer(destination_fromQueue);
        Message message = consumer.receive();

        Integer[] arrayToSort = null;

        if (message instanceof TextMessage) {
            System.out.println("Ophalen Message van testQueue2.\n");

            TextMessage textMessage = (TextMessage) message;
            //  krijg stringarray terug
            String strArrayFromQueue = textMessage.getText();

            //  opslaan stringintegers van queue
            //  output opschonen
            String[] integers = strArrayFromQueue
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll("\\s", "")
                    .split(",");

            arrayToSort = new Integer[integers.length];

            System.out.println("String array naar integer array omzetten.\n");

            for (int i = 0; i < arrayToSort.length; i++) {
                try {
                    arrayToSort[i] = Integer.parseInt(integers[i]);
                } catch (NumberFormatException nfe) {
                	LOGGER.severe(String.valueOf(nfe));
                }
            }
        } else {
            System.err.println("Bericht ophalen niet gelukt");
        }

        Destination destination_toQueue = session.createQueue(TO_CSP);
        MessageProducer producer = session.createProducer(destination_toQueue);

        //  Standaard Bucket Size = 5
        BucketSort.sort(arrayToSort);

        String stringForConsumer = Arrays.toString(arrayToSort);

        TextMessage messageTo = session.createTextMessage(stringForConsumer);
        producer.send(messageTo);
        connection.close();

		LOGGER.info("Message sent to " + TO_CSP + ".\n");
		LOGGER.info("Start Consumer.main() to continue..");
    }
}

