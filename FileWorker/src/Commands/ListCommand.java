package Commands;

import FileWorker.FileWorker;

import java.io.File;

public class ListCommand implements ICommand {
    @Override
    public CommandPacket execute(FileWorker fileWorker){
        try {
            StringBuilder builder = new StringBuilder();
            File file = new File(fileWorker.getPath());
            File[] files = file.listFiles();
            System.out.println("here");
            for(File f: files){
                builder.append(f.getName());
                builder.append("\n");
            }
            String message = String.format("Command list executed successfully.\n Result:\n %s",builder.toString());
            System.out.println(message);
            return new CommandPacket(this, true, message);
        }
        catch (Exception e){
            return new CommandPacket(this, false, e.toString());
        }
    }
}
