package java.rmi;

/**
 * Maintained and created by:
 * R. Lobato
 * C. Verra
 */
// @Stefan, onveranderd, maybe kan jij hier nog iets geks op bedenken?
public interface Task<T> {

    T execute();
}
