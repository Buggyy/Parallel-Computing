package activemq;

import mergesort.ConcurrentMergeSort;
import utilities.Utilities;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */

public class SortStringFromQueue {

    // either connect to the remote ActiveMQ running on the PI, or on the localhost
//    private static String url = "failover:(tcp://169.254.1.1:61616,localhost:8161)";
    private static String url = "failover:(tcp://169.253.1.1:61616,tcp://localhost:61616)"; // hier andere ip maken i guess? even van iemand anders weer checken of dat t zelfd emoet zijn?
    private static String subjectFrom = "testQueue1"; // anderenamen hier
    private static String subjectTo = "testQueue2";

    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
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

        String consumerString = Utils.arrayToString(integerList);

        System.out.println(consumerString); // kan weg?

        TextMessage messageTo = session.createTextMessage(consumerString); // ook renamen?
        messageProducer.send(messageTo);

        //connection.close();
    }
}
