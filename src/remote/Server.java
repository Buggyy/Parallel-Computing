package remote;

import java.net.InetAddress;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
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
	private static final String SERVICE_NAME = "Server";

	static String message = "Not set";
	static String hostName = "None found";
	private Task task;

	private Server() throws RemoteException {
	}


	public static void main(String[] args) throws Exception {

		//	Hostname ophalen
		hostName = InetAddress.getLocalHost().getHostName();

		//	Server object starten
		Server server = new Server();

		// Registery starten
		Registry registry = LocateRegistry.createRegistry(PORT);

		// Server aan object binden aan de hand van service naam
		registry.bind(SERVICE_NAME, server);

		LOGGER.info(SERVICE_NAME + " gestart op " + hostName);
	}

	/**
	 * @param host om mee te verbinden
	 * @return Service
	 */
	static Service connect(String host) throws RemoteException, NotBoundException {
		Service remoteService;

		Registry registry = LocateRegistry.getRegistry(host, PORT);

		remoteService = (Service) registry.lookup(SERVICE_NAME);

		try {
			LOGGER.info("Connectie met " + host);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return remoteService;
	}

	@Override
	public void ping() {

	}

	@Override
	public String sendMessage(String message) {
		LOGGER.info("Bericht: " + message);
		return message + " ontvangen door " + hostName;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public <T> T executeTask(Task<T> t) {
		return t.execute();
	}

	@Override
	public void setTask(Task task) {
		this.task = task;

	}

	@Override
	public Task getTask() {
		return task;
	}
}