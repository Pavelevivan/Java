package Readers.PascalReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class VarReader implements IReadable{
    private String type = "var";

    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        if (expression.startsWith("Var", i))
            return new Token(expression.substring(0, i + 3), type, "var");
        else
            return null;
    }
}
