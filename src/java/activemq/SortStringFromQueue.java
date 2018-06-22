package java.activemq;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.algoritmes.ConcurrentMergeSort;
import static java.helper.Config.ACTIVEMQ_URL;
import static java.helper.Config.FROM_CSP;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class SortStringFromQueue {



    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination_fromQueue;

        if (args.length > 0) {
            destination_fromQueue = session.createQueue(args[0]);
        } else {
            destination_fromQueue = session.createQueue(FROM_CSP);
        }

        MessageConsumer messageConsumer = session.createConsumer(destination_fromQueue);
        Message message = messageConsumer.receive();
        int[] integerList = null; // to hold the converted and sorted numbers
        if (message instanceof TextMessage) {
            String string = ((TextMessage) message).getText();
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

        String consumerString = utilities.CustomUtilities.arrayToString(integerList != null ? integerList : new int[0]);

        TextMessage messageTo = session.createTextMessage(consumerString); // ook renamen?
        messageProducer.send(messageTo);

        //connection.close();
    }
}
