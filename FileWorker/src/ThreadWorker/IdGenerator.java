package ThreadWorker;

public class IdGenerator {
    private static final IdGenerator generator = new IdGenerator();
    private volatile int i;
    private IdGenerator(){
        this.i = 1;
    }
    public static IdGenerator getInstance() {
        return generator;
    }
    public synchronized int getNextId(){
        return i++;
    }
}
