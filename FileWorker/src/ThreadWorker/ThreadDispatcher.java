package ThreadWorker;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadDispatcher {
    private static final ThreadDispatcher dispatcher = new ThreadDispatcher();
    final ConcurrentHashMap<Integer, ThreadedTask> tasks;
    private final ThreadMonitor monitor;
    private final IdGenerator generator;

    public static ThreadDispatcher getInstance() {
        return dispatcher;
    }

    private ThreadDispatcher() {
        tasks = new ConcurrentHashMap<>();
        generator = IdGenerator.getInstance();
        monitor = new ThreadMonitor(tasks, 2000, "ThreadMonitor.txt");
        Add(monitor);
    }

    public void Add(ThreadedTask task){
        int id = generator.getNextId();
        tasks.put(id, task);
        monitor.notifyAboutChanges();
        Thread thread = new Thread(() ->{
            task.run();
            tasks.remove(id);
            monitor.notifyAboutChanges();
        });
        thread.start();
    }
}
