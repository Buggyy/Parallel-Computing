package utilities;
/**
 * Parallel Computing
 * AUTHOR: R. Lobato & C. Verra
 */


public class Logger {

    private long previousTimeStamp = -1;
    private boolean showLog = false;

    /**
     *
     * @param showLog
     */
    public Logger(boolean showLog) {
        this.showLog = showLog;
    }

    public void logOff() {
        this.showLog = false;
    }

    public void logOn() {
        this.showLog = true;
    }

    public long start() {
        previousTimeStamp = System.nanoTime();
        return previousTimeStamp;
    }

    /**
     *
     * @param label
     * @return previous timestamp
     */
    public long log(String label) {
        long thisTimeStamp = System.nanoTime();
        long duration = thisTimeStamp - previousTimeStamp;

        if (showLog)
            System.out.println(label + " takes " + duration / 1e6 + " ms");

        previousTimeStamp = thisTimeStamp;

        return previousTimeStamp;
    }
}