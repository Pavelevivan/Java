package FileWorker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;


public class FileWorker {
    private boolean isRecursive;
    private final String path;

    public FileWorker(String path, boolean isRecursive) throws FileNotFoundException{
        File f = new File(path);
        if(!f.exists())
            throw new FileNotFoundException();
        this.path = path;
        this.isRecursive = isRecursive;
    }

    public void execute(IExecutable command, String path) throws IOException {
        String[] files = FileWorker.getAllFiles(path);
        Arrays.sort(files);
        Collections.reverse(Arrays.asList(files));
        for (String p : files) {
            File file = new File(p);
            if (file.isDirectory() && isRecursive)
                execute(command, p);
            command.process(file);
        }
    }

    public static String[] getAllFiles(String path) throws IOException{
        if (path == null) throw new IOException("incorrect path");
        File file = new File(path);
        if(!file.exists()) throw new IOException("file doesn't exist");
        File[] files = file.listFiles();
        int index = 0;
        String[] result = new String[files.length];
        for (File f : files)
            result[index++] = f.getPath();
        return result;

    }
    public void execute(IExecutable command) throws IOException{
        execute(command, this.path);
    }
    public String getPath(){ return this.path;}
    public boolean isRecursive() {
        return isRecursive;
    }

    public void setRecursive(boolean recursive) {
        this.isRecursive = recursive;
    }
}
