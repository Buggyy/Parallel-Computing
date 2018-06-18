package rmi;

import algorithm.ConcurrentMergeSort;
import util.Utils;

import java.net.InetAddress;
import java.time.LocalDateTime;

/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */
public class Client {

    public Client() {
    }

    public static void main(final String... args) {
        try {
            String localHostname = InetAddress.getLocalHost().getHostName();
            System.out.println("This is host: " + localHostname);

            String serviceHost = Server.masterNodeName;

            Service service = Server.connect(serviceHost);

            long t1, t2;

            t1 = System.currentTimeMillis();
            service.ping();
            t2 = System.currentTimeMillis();
            System.out.println("Ping took " + (t2 - t1) + " ms.");

            t1 = System.currentTimeMillis();
            String greeting = service.sendMessage("Hello World at " + LocalDateTime.now());
            t2 = System.currentTimeMillis();

            System.out.println("Client side: " + greeting);
            System.out.println("SendMessage took " + (t2 - t1) + " ms.");

            service.executeTask(new Sort());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Sort implements Task<Boolean> {

        private int[] array;

        public Sort() {
            this.array = Utils.fillArray(1_024_000);
        }

        @Override
        public Boolean execute() {
            ConcurrentMergeSort mergeSort = new ConcurrentMergeSort(array);
            mergeSort.sort();
            return Utils.isSorted(array);
        }
    }
}
