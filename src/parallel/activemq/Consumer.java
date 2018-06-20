package activemq;

import algorithm.ConcurrentMergeSort;
import org.apache.activemq.ActiveMQConnectionFactory;
import util.Utils;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 * User: Kevin
 * Date: 6/3/2017
 * Time: 11:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class Consumer {
    // either connect to the remote ActiveMQ running on the PI, or on the localhost
//    private static String url = "failover:(tcp://169.254.1.1:61616,localhost:8161)";
    private static String url = "failover:(tcp://169.254.1.1:61616,tcp://localhost:61616)";
    private static String subject = "result";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);

        int count = 0;

        List<Integer> list = new ArrayList<>();

        while (true) {

            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                count++;
                TextMessage textMessage = (TextMessage) message;

                String str = textMessage.getText();
                String[] integerStrings = str.split(" ");  //to store the string of numbers retrieved from the queue

                for (String integerString : integerStrings) {
                    int j = Integer.parseInt(integerString);
                    list.add(j);
                }

                if (count == Producer.NODES) {
                    int[] array = list.stream().mapToInt(i -> i).toArray();
                    ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(array);
                    concurrentMergeSort.sort();

                    System.out.println("Is it sorted?");
                    System.out.println(Utils.isSorted(array));
                }
            }
        }

//        System.out.println("Closing connection on " + LocalDateTime.now());

//        connection.close();
    }
}
