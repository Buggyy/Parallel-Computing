package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */
// @Stefan, niks veranderd
public interface Service extends Remote {

    void ping() throws RemoteException;

    String sendMessage(String message) throws RemoteException;

    <T> T executeTask(Task<T> t) throws RemoteException;

    void setTask(Task task) throws RemoteException;

    Task getTask() throws RemoteException;
}
