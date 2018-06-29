package Server;

import Commands.CommandPacket;
import Commands.CommandHandler;
import Commands.ICommand;
import FileWorker.FileWorker;
import ThreadWorker.ThreadedTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientThread extends ThreadedTask{
    private final Socket socket;
    private final FileWorker worker;
    public ClientThread(Socket socket, FileWorker worker){
        this.socket = socket;
        this.worker = worker;
    }
    @Override
    public void run() {
        try(InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream()) {
            while (!socket.isClosed())
            {
                CommandPacket packet = CommandHandler.getRequest(input);
                if (!packet.isSuccess())
                    System.out.println(String.format("Unrecognised command: %s",packet.getMessage()));
                else {
                    ICommand command = packet.getCommand();
                    CommandPacket response = command.execute(worker);
                    CommandHandler.sendCommand(output, response);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Client out");
        }
    }
}
