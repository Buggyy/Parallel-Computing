package java.rmi;

/**
 * Maintained and created by:
 * R. Lobato
 * C. Verra
 */
public interface Service extends Remote {

    void ping() throws RemoteException;

    String sendMessage(String message) throws RemoteException;

    <T> T executeTask(Task<T> t) throws RemoteException;

    void setTask(Task task) throws RemoteException;

    Task getTask() throws RemoteException;
}
