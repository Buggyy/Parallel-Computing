package java.rmi;


import java.algoritmes.ConcurrentMergeSort;
import java.helper.CustomUtilities;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */
public class Client {

	private final static Logger LOGGER = Logger.getLogger(Client.class.getName());

	public Client() {
	}

	public static void main(final String... args) {
		try {
			String localHostname = InetAddress.getLocalHost().getHostName();
			LOGGER.info("de host is: " + localHostname);

			String serviceHost = Server.hostName;

			Service service = Server.connect(serviceHost);

			long t1, t2;

			t1 = System.currentTimeMillis();
			service.ping();
			t2 = System.currentTimeMillis();
			System.out.println("Ping duurde " + (t2 - t1) + " ms.");

			t1 = System.currentTimeMillis();
			String bericht = service.sendMessage("Hello daar om: " + LocalDateTime.now());
			t2 = System.currentTimeMillis();

			LOGGER.info("Client side: " + bericht);
			LOGGER.info("SendMessage duurde " + (t2 - t1) + " ms.");

			service.executeTask(new Sort());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class Sort implements Task<Boolean> {

		private int[] array;

		public Sort() {
			this.array = CustomUtilities.generateArray(1_024_000);
		}

		@Override
		public Boolean execute() {
			ConcurrentMergeSort mergeSort = new ConcurrentMergeSort(array);
			mergeSort.sort();

			return CustomUtilities.isArraySorted(array);
		}
	}
}
