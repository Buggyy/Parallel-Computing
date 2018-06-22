package java.helper;

/**
 * Parallel Computing
 * Configuration Class
 *
 * AUTHOR: R. Lobato & C. Verra
 */
public class Config {

	public static final String ACTIVEMQ_URL = "failover:(tcp://127.0.0.1:61616,localhost:8161)";
	public static final String FROM_CSP = "producerQueue";
	public static final String TO_CSP = "consumerQueue";

}
