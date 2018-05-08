package Lexer;

import java.util.ArrayList;

public interface IReadable
{
    Token tryReadToken(String expression);
}
