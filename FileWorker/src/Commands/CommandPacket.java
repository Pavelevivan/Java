package Commands;

public class CommandPacket {
    private final ICommand command;
    private boolean isSuccess;
    private String message;
    public CommandPacket(ICommand command, boolean isSuccess, String message)
    {
        this.command = command;
        this.isSuccess = isSuccess;
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public ICommand getCommand(){
        return command;
    }
    public boolean isSuccess(){
        return isSuccess;
    }
}
