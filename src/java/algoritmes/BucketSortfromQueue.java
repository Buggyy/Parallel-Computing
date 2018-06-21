package java.algoritmes;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;

import static java.utilities.Config.ACTIVEMQ_URL;

/**
 * The ActiveMQ using the BucketSort algorithm
 * Maintained and created by:
 * R. Lobato
 */
public class BucketSortfromQueue {

    private static String subjectFrom = "testQueue1";
    private static String subjectTo = "testQueue2";

    public static void main(String args[]) throws Exception {

        System.out.println("BucketSort from testQueue1 started.\n");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue = session.createQueue(subjectFrom);
        MessageConsumer consumer = session.createConsumer(destination_fromQueue);
        Message message = consumer.receive();


        Integer[] arrayToSort = null;

        if (message instanceof TextMessage) {
            System.out.println("Retrieving Message from testQueue2.\n");

            TextMessage textMessage = (TextMessage) message;
            //  Get String array back
            String strArrayFromQueue = textMessage.getText();

            //  Store string integers retrieved from queue
            //  Clean the output
            String[] integers = strArrayFromQueue
                    .replaceAll("\\[", "")
                    .replaceAll("\\]", "")
                    .replaceAll("\\s", "")
                    .split(",");


            arrayToSort = new Integer[integers.length];

            System.out.println("Turning String Array into Integer Array.\n");

            for (int i = 0; i < arrayToSort.length; i++) {
                try {
                    arrayToSort[i] = Integer.parseInt(integers[i]);
                } catch (NumberFormatException nfe) {
                    // Code, to recover from formatting errors
                }

            }
        } else {
            System.err.println("Failed to get Message!");
        }

        Destination destination_toQueue = session.createQueue(subjectTo);
        MessageProducer producer = session.createProducer(destination_toQueue);

        //  Default Bucket Size = 5
        BucketSort.sort(arrayToSort);

        String stringForConsumer = Arrays.toString(arrayToSort);

        TextMessage messageTo = session.createTextMessage(stringForConsumer);
        producer.send(messageTo);
        connection.close();

        System.out.println("Message sent to testQueue2.\n");
        System.out.println("Start Consumer.main() to continue..");

    }
}

