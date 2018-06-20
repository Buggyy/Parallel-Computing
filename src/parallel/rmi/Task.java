package rmi;

/**
 * Created by IntelliJ IDEA.
 * User: Kevin
 * Date: 5/31/2017
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Task<T> {

    T execute();
}
