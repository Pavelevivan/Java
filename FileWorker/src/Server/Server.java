package Server;

import FileWorker.FileWorker;
import ThreadWorker.ThreadDispatcher;
import Utils.Constants;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private final static ThreadDispatcher dispatcher = ThreadDispatcher.getInstance();
    private final int port;
    private final FileWorker worker;
    private final String host;

    public Server(FileWorker worker, String host, int port){
        this.worker = worker;
        this.port = port;
        this.host = host;
    }

    public void run()
    {
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true){
                Socket client = socket.accept();
                dispatcher.Add(new ClientThread(client, worker));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
