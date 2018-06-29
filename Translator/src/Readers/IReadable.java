package Readers;
import Token.Token;

public interface IReadable
{
    Token tryReadToken(String expression);
}
