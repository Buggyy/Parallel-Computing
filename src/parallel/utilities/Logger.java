package utilities;

/**
 * Maintained and created by:
 * S. R. Lobato
 * C. Verra
 */

// @Stefan, alleen class renamed, denk dat dit alleen voor logs nodig is namelijk
public class Logger {

    private long previousTimeStamp = -1;
    private boolean showLog = false;

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

    public long log(String label) {
        long thisTimeStamp = System.nanoTime();
        long duration = thisTimeStamp - previousTimeStamp;

        if (showLog)
            System.out.println(label + " takes " + duration / 1e6 + " ms");

        previousTimeStamp = thisTimeStamp;

        return previousTimeStamp;
    }
}