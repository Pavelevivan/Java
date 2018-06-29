package Readers.JavaReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class BlockReader implements IReadable {

    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        if (expression.startsWith("{", i))
            return new Token(expression.substring(0, i + 1),"block", "open", "{");
        if (expression.startsWith("}", i))
            return new Token(expression.substring(0, i + 1), "block", "close", "}");
        return null;
    }
}
