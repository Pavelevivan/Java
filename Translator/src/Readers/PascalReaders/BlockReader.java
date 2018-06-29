package Readers.PascalReaders;
import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class BlockReader implements IReadable {
    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        if (expression.startsWith("begin", i))
            return new Token(expression.substring(0, i + 5),"block", "open", "begin");
        if (expression.startsWith("end;", i) || expression.startsWith("end.", i))
            return new Token(expression.substring(0, i + 4),"block", "close", expression.substring(i, i + 4 ));
        return null;
    }
}
