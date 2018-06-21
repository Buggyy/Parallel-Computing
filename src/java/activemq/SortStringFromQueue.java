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

    private static String subjectFrom = "testQueue1"; // @Raf anderenamen hier
    private static String subjectTo = "testQueue2";

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
        int[] integerList = null; // to hold the converted and sorted numbers
        if (message instanceof TextMessage) {
            TextMessage message = (TextMessage) message;
            String string = message.getText();
            String[] listOfIntegerStrings = string.split(" ");  //to store the string of numbers retrieved from the queue
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

        System.out.println(consumerString); // kan weg?

        TextMessage messageTo = session.createTextMessage(consumerString); // ook renamen?
        messageProducer.send(messageTo);

        //connection.close();
    }
}
