package Commands;

import java.io.IOException;

public interface ICommandReader {
    ICommand readCommand() throws IOException;
}
