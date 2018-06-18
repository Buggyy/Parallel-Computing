package rmi;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Maintained and created by:
 * Chris Verra
 * S. R. Lobato
 */
public class Server extends UnicastRemoteObject implements Service {

    private static final int port = 2000;
    private static final String serviceName = "Test";
    static final String masterNodeName = "Kevin";

    private static String localHostname;

    private Task task;

    private Server() throws RemoteException {
    }

    static Service connect(String host) {
        Service remoteService = null;

        try {
            System.out.println("Connecting to " + host);

            Registry r = LocateRegistry.getRegistry(host, port);
            remoteService = (Service) r.lookup(serviceName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return remoteService;
    }

    public static void main(String[] args) {
        try {
            // get the hostname of this node
            localHostname = InetAddress.getLocalHost().getHostName();

            // start a new server object
            Server server = new Server();

            // start the registry service on this node
            Registry registry = LocateRegistry.createRegistry(port);

            // add binding to this server object and use a specific ServiceName to reference it
            registry.bind(serviceName, server);


            System.out.println(serviceName + " running on " + localHostname);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ping() throws RemoteException {
        return;
    }

    public String sendMessage(String message) throws RemoteException {
        System.out.println("echo: " + message);
        return message + " received at " + localHostname;
    }

    public <T> T executeTask(Task<T> t) throws RemoteException {
        return t.execute();
    }

    public void setTask(Task task) throws RemoteException {
        this.task = task;
    }

    public Task getTask() throws RemoteException {
        return task;
    }
}
