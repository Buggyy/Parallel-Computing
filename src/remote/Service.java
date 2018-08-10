package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Parallel Computing
 * RMI Service
 * AUTHOR: R. Lobato & C. Verra
 */
public interface Service extends Remote {

    void ping() throws RemoteException;

    String sendMessage(String message) throws RemoteException;

	String getMessage() throws RemoteException;

    <T> T executeTask(Task<T> t) throws RemoteException;

    void setTask(Task task) throws RemoteException;

    Task getTask() throws RemoteException;
}
