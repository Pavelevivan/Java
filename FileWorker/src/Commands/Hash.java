package Commands;

import FileWorker.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static Utils.Constants.HASH_FILENAME;

public class Hash implements ICommand{
    private String fileName;
    public Hash(String fileName){
        this.fileName = fileName;
    }
    @Override
    public CommandPacket execute(FileWorker fileWorker) {
        try {
            File file = new File(fileWorker.getPath());
            File[] files = file.listFiles();
            String filePath = fileWorker.getPath() + "\\" + fileName;
            for (File f : files) {
                if (f.getName().equals(fileName)) {
                    File hashingFile = new File(filePath);
                    String hash = "";
                    if (hashingFile.isDirectory())
                        hash = Md5Executor.getHashFromDirectory(hashingFile);
                    else
                        hash = Md5Executor.getHashFromFile(hashingFile);
                    System.out.println(hash);
                    String response = String.format("Hash of file %s: %s", fileName, hash);
                    return new CommandPacket(this, true, response);
                }
            }
        }
        catch (Exception e){
            return new CommandPacket(this, true, "File Not Found");
        }
        return new CommandPacket(this, true, "File Not Found");
    }
}
