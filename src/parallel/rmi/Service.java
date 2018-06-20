package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Kevin
 * Date: 5/31/2017
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Service extends Remote {

    void ping() throws RemoteException;

    String sendMessage(String message) throws RemoteException;

    <T> T executeTask(Task<T> t) throws RemoteException;

    void setTask(Task task) throws RemoteException;

    Task getTask() throws RemoteException;
}
