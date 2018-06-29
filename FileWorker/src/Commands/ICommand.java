package Commands;

import FileWorker.FileWorker;

import java.io.IOException;

public interface ICommand
{
    CommandPacket execute(FileWorker fileWorker);
}
