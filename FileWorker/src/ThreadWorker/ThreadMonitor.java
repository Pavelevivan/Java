package ThreadWorker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class ThreadMonitor extends ThreadedTask {
    private final Map<Integer, ThreadedTask> tasks;
    private final File file;
    private final int timeout;
    private volatile boolean isChanged;
    private final Object lock;

    public ThreadMonitor(Map<Integer, ThreadedTask> tasks, int timeout, String file) {
        this.lock = new Object();
        this.file = new File(file);
        this.tasks = tasks;
        this.timeout = timeout;
    }

    public void run() {
        StringBuilder text = new StringBuilder();
        while (true)
        {
            while (true) {
                synchronized (lock) {
                    if (isChanged){
                        isChanged = false;
                        break;
                    }
                    try {
                        lock.wait(timeout);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        text.setLength(0);
        for (Integer id: tasks.keySet())
        {
            text.append(String.format("id: %d \t task: %s \n", id, tasks.get(id).getClass().toString()));
        }
        System.out.println(text.toString());
        try (FileOutputStream out = new FileOutputStream(file)){
            out.write(text.toString().getBytes());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        }
    }
    void notifyAboutChanges()
    {
        synchronized (lock)
        {
            isChanged = true;
            lock.notify();
        }
    }
}
