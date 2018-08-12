package activemq;

import algoritmes.ConcurrentMergeSort;
import helper.CustomUtilities;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;
import java.util.List;

import static helper.Config.ACTIVEMQ_URL;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class Consumer {
    private static String subject = "resultaat";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue(subject);
        MessageConsumer consumer = session.createConsumer(destination);

        int count = 0;

        //	Lijst van Integers
        List<Integer> list = new ArrayList<>();

        while (true) {

            Message message = consumer.receive();
            if (message instanceof TextMessage) {
                count++;
                TextMessage textMessage = (TextMessage) message;

                // String aan integers opslaan
                String str = textMessage.getText();
                //	String aan integers opsplitten
                String[] integerStrings = str.split(" ");

                //	Parse String Integers naar ints
                for (String integerString : integerStrings) {
                    int j = Integer.parseInt(integerString);
                    //	Voeg toe aan list
                    list.add(j);
                }

                //	Als het aantal lijsten gelijk is aan het aantal producers
                if (count == Producer.NODES) {
                    int[] array = list.stream().mapToInt(i -> i).toArray();
                    //	Sorteer lijsten individueel
                    ConcurrentMergeSort concurrentMergeSort = new ConcurrentMergeSort(array);
                    concurrentMergeSort.sort();

                    if (CustomUtilities.isArraySorted(array))
                        break;
                }
            }
        }

        connection.close();
    }
}