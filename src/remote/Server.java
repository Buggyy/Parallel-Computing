package remote;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
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
	private static final String SERVICE_NAME = "testing";

	static String hostName = "Geen host gedefinieerd";
	private Task task;

	private Server() throws RemoteException {
	}

	/**
	 * @param host om mee te verbinden
	 * @return Service
	 */
	static Service connect(String host) throws RemoteException, NotBoundException {
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

	public static void main(String[] args) throws UnknownHostException,
			RemoteException, AlreadyBoundException {

		// Hostname ophalen
		hostName = InetAddress.getLocalHost().getHostName();

		// Server object starten
		Server server = new Server();

		//hostname ophalen
		hostName = InetAddress.getLocalHost().getHostName();

		// Registery starten
		Registry registry = LocateRegistry.createRegistry(PORT);

		// Server aan object binden aan de hand van service naam
		registry.bind(SERVICE_NAME, server);

		LOGGER.info(SERVICE_NAME + " gestart op " + hostName);
	}

	@Override
	public void ping() throws RemoteException {

	}

	@Override
	public String sendMessage(String message) throws RemoteException {
		LOGGER.info("Bericht: " + message);
		return message + " ontvangen door " + hostName;
	}

	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		return t.execute();
	}

	@Override
	public void setTask(Task task) throws RemoteException {
		this.task = task;

	}

	@Override
	public Task getTask() throws RemoteException {
		return task;
	}
}