package Readers.JavaReaders;
import Readers.IReadable;
import Token.Token;
import Readers.WhiteSpace;
public class EndStatementReader implements IReadable {
    private String type = "endstatement";

    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        if (expression.startsWith(";", i))
            return new Token(expression.substring(0, i+1), type, ";");
        else return null;
    }
}
