package Commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static Utils.Constants.CHARSET;
import static Utils.Constants.EOF;

public class CommandHandler {
//    private static final Set<String> commands = Set.of("hash", "list");
    public static CommandPacket getRequest(InputStream stream) throws IOException{
        String request = readRequest(stream);
        return parseRequest(request);
    }
    private static String readRequest(InputStream stream) throws IOException{
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int pointer = 0;
        int index = -1;
        while (index < 0 && pointer != -1) {
            pointer = stream.read(buffer, 0, buffer.length);
            String str = new String(buffer, CHARSET);
            index = str.indexOf(EOF);
            builder.append(index >= 0 ? str.substring(0, index) : str);
        }
        return builder.toString();
    }
    private static CommandPacket parseRequest(String request){
        String[] operands = request.split("\\s");
        String type = operands[0];
        try{
        switch (type) {
            case "list":
                if (operands.length == 1)
                    return new CommandPacket(new ListCommand(), true, request);
            case "hash":
                if (operands.length == 2)
                    return new CommandPacket(new Hash(operands[1]), true, request);
            default:
                throw new IllegalArgumentException("incorrect command");
        }
        }
        catch (Exception e){
            return new CommandPacket(new ListCommand(), false, request);
        }
    }
    public static void sendCommand(OutputStream out, CommandPacket response)throws IOException{
        out.write(response.getMessage().getBytes(CHARSET));
    }

}
