package java.rmi;

/**
 * Parallel Computing
 * RMI task
 * AUTHOR: R. Lobato & C. Verra
 */
public interface Task<T> {

    T execute();
}
