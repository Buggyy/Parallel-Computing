package algoritmes;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Arrays;
import java.util.Scanner;

import static helper.Config.ACTIVEMQ_URL;
import static helper.Config.FROM_CSP;


/**
 * Parallel Computing
 * ActiveMQ Producer Class
 *
 * AUTHOR: R. Lobato & C. Verra
 */
public class Producer {

    //    Queue naam van Producer
    private static String subject = "testQueue1";

    public static void main(String[] args) throws JMSException {

        System.out.println("Producer gestart.\n");

        //  maak scanner
        Scanner scanner = new Scanner(System.in);

        //  maak test Integer[] Object
        Integer[] arrayToSort;

        System.out.println("hoeveelheid te genereren integers opgegven: ");
        int amountOfIntegers = scanner.nextInt();

        System.out.println("genereren van " + amountOfIntegers + " integers...\n");

        //  vul test Array Object
        arrayToSort = GenerateArray(amountOfIntegers);

        //  maak verbinding met ActiveMQ
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //  bestemming van  Queue voor Producer
        Destination destination = session.createQueue(FROM_CSP);

        MessageProducer producer = session.createProducer(destination);

        TextMessage message = session.createTextMessage(Arrays.toString(arrayToSort));
        producer.send(message);

        connection.close();
    }

    /**
     * Genereer array met random integers
     *
     * @param amountOfIndexes
     * @return Generated Array
     */
    private static Integer[] GenerateArray(int amountOfIndexes) {
        //  Genereer Integer[] met opgegeven aantal indexes
        Integer[] numbers = new Integer[amountOfIndexes];

        for (int i = 0; i < numbers.length; i++) {
            // vul index met random nummer tot de 100000
            //  bij java heap space exception een outofmemory error
            numbers[i] = (int) (Math.random() * 100000);
        }

        return numbers;
    }

    /**
     * kijken of de input array gesorteerd is of niet
     * @param listToBeChecked
     * @return true if array is sorted
     */
    public static boolean isSorted(int[] listToBeChecked) {
        for (int i = 0; i < listToBeChecked.length - 1; i++) {
            if (listToBeChecked[i] > listToBeChecked[i + 1])
                return false;
        }
        return true;
    }
}