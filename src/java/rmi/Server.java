package java.rmi;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

/**
 * Parallel Computing
 * RMI Server
 * AUTHOR: R. Lobato & C. Verra
 */
public class Server extends UnicastRemoteObject implements Service {

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

	private static final int PORT = 2000;
	private static final String SERVICE_NAME = "testing";

	static String hostName = "Geen host gedefinieerd";
	private Task task;

	private Server() throws RemoteException {
	}

	/**
	 * @param host om mee te verbinden
	 * @return Service
	 */
	static Service connect(String host) {
		Service remoteService = null;

		Registry registry = LocateRegistry.getRegistry(host, PORT);
		remoteService = (Service) registry.lookup(SERVICE_NAME);

		try {
			LOGGER.info("Connectie met " + host);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return remoteService;
	}

	public static void main(String[] args) {
		try {
			// Hostname ophalen
			hostName = InetAddress.getLocalHost().getHostName();

			// Server object starten
			Server server = new Server();

			public static void main (String[]args){
				try {
					//hostname ophalen
					hostName = InetAddress.getLocalHost().getHostName();
					// server object starten
					Server server = new Server();

					// Registery starten
					Registry registry = LocateRegistry.createRegistry(PORT);

					// Server aan object binden aan de hand van service naam
					registry.bind(SERVICE_NAME, server);

					LOGGER.info(SERVICE_NAME + " gestart op " + hostName);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void ping () {
			}

			public void setTask (Task task){
				this.task = task;
			}

			public Task getTask () {
				return task;
			}


			public <T > T executeTask(Task < T > t) {
				return t.execute();
			}

			public String sendMessage (String message){
				LOGGER.info("Bericht: " + message);

				return message + " ontvangen door " + hostName;
			}
		}