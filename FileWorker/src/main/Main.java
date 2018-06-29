package main;
import FileWorker.*;
import Server.Server;
import ThreadWorker.SleepWorker;
import ThreadWorker.ThreadDispatcher;
import Utils.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Main {

    public static void Task_1(){
        String path = "C:\\Users\\pavel\\Desktop\\Java\\FileWorker\\Java 4 семестр\\Task_1.txt";
        try {
            FileWorker worker = new FileWorker(path, false);
            IExecutable command = new Md5Executor();
            worker.execute(command);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        }
    public static void main(String[] args) throws InterruptedException{
        Task_3();
    }
    static void Task_3(){
        try {
            FileWorker worker = new FileWorker(Constants.startDorectory, true);
            Server server = new Server(worker, Constants.HOST, Constants.SERVER_PORT);
            server.run();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public static void Task_2() throws InterruptedException{
        ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
        dispatcher.Add(new SleepWorker());
        Thread.sleep(1000);
        dispatcher.Add(new SleepWorker());
        Thread.sleep(500);
        dispatcher.Add(new SleepWorker());
        Thread.sleep(5000);
    }
}
