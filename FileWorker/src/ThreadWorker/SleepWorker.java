package ThreadWorker;

public class SleepWorker extends ThreadedTask {
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ignored) {
        }
    }
}
