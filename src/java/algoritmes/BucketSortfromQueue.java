package java.algoritmes;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;

import static java.utilities.Config.ACTIVEMQ_URL;

/**
 *  ActiveMQ met het BucketSort algoritme
 * Maintained and created by:
 * R. Lobato
 */
public class BucketSortfromQueue {

    private static String subjectFrom = "testQueue1";
    private static String subjectTo = "testQueue2";

    public static void main(String args[]) throws Exception {

        System.out.println("BucketSort van testQueue1 gestart.\n");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue = session.createQueue(subjectFrom);
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
                    // Hier komt error handling
                }

            }
        } else {
            System.err.println("Bericht ophalen niet gelukt");
        }

        Destination destination_toQueue = session.createQueue(subjectTo);
        MessageProducer producer = session.createProducer(destination_toQueue);

        //  Standaard Bucket Size = 5
        BucketSort.sort(arrayToSort);

        String stringForConsumer = Arrays.toString(arrayToSort);

        TextMessage messageTo = session.createTextMessage(stringForConsumer);
        producer.send(messageTo);
        connection.close();

        System.out.println("Message verzonden naar testQueue2.\n");
        System.out.println("Start Consumer.main() te vervolgen..");

    }
}

