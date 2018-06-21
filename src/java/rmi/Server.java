package java.rmi;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Maintained and created by:
 * R. Lobato
 * C. Verra
 */

public class Server extends UnicastRemoteObject implements Service {
    private static final int port = 2000;
    private static final String serviceName = "testing";
    private static String hostName;


    private Task task;

    private Server() throws RemoteException {
    }

    static Service connect(String host) {
        Service remoteService = null;

        try {
            System.out.println("verbinden met " + host); // kan dit weg?

            Registry registry = LocateRegistry.getRegistry(host, port);
            remoteService = (Service) registry.lookup(serviceName);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return remoteService;
    }

    public static void main(String[] args) {
        try {
            //hostname ophalen
            hostName = InetAddress.getLocalHost().getHostName();
            // sercer object starten
            Server server = new Server();

            // registery starten
            Registry registry = LocateRegistry.createRegistry(port);
            // server aan object binden aan de hand van servicenaam
            registry.bind(serviceName, server);
            // sout
            System.out.println(serviceName + " gestart op " + hostName); // kan dit weg?

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ping() throws RemoteException {
        return;
    }

    public void setTask(Task task) throws RemoteException {
        this.task = task;
    }

    public Task getTask() throws RemoteException {
        return task;
    }

    public <T> T executeTask(Task<T> t) throws RemoteException {
        return t.execute();
    }

    public String sendMessage(String message) throws RemoteException {
        System.out.println("bericht: " + message);
        return message + " ontvangen door " + hostName;
    }



}
