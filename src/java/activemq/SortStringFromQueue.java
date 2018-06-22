package java.activemq;

import mergesort.ConcurrentMergeSort;
import utilities.Utilities;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

import static java.utilities.Config.ACTIVEMQ_URL;

/**
 * Maintained and created by:
 * R. Lobato
 * C. Verra
 */

public class SortStringFromQueue {

    private static String subjectFrom = "test1";
    private static String subjectTo = "test2";

    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue;

        if (args.length > 0) {
            destination_fromQueue = session.createQueue(args[0]);
        } else {
            destination_fromQueue = session.createQueue(subjectFrom);
        }

        MessageConsumer messageConsumer = session.createConsumer(destination_fromQueue);
        Message message = messageConsumer.receive();
        // bijhouden geconverte en gesorteerde nummers
        int[] integerList = null;
        if (message instanceof TextMessage) {
            TextMessage message = (TextMessage) message;
            String string = message.getText();
            // bijhouden string van nummers ontvangen van de queue
            String[] listOfIntegerStrings = string.split(" ");
            integerList = new int[listOfIntegerStrings.length];
            for (int i = 0; i < integerList.length; i++) {
                integerList[i] = Integer.parseInt(listOfIntegerStrings[i]);
            }
        }

        Destination destination = session.createQueue("resultaat");
        MessageProducer messageProducer = session.createProducer(destination);

        ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(integerList);
        concurrentMergeSort.sort();

        String consumerString = Utilities.arrayToString(integerList != null ? integerList : new int[0]);

        TextMessage messageTo = session.createTextMessage(consumerString);
        messageProducer.send(messageTo);

        //connection.close();
    }
}
