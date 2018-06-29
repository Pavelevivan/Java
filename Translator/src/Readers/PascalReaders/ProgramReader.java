package Readers.PascalReaders;
import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class ProgramReader implements IReadable{
    private String name;
    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        i = getName(expression, i);
        if (i == -1) return null;
        return new Token(expression.substring(0, i+1),"method", new String[]{"public", "static", "void", name});
    }

    private int getName(String s, int i) {
        if (!s.startsWith("Program", i)) return -1;
        else i += 7;
        i = WhiteSpace.skipWS(s, i);
        int t = i;
        while (Character.isLetterOrDigit(s.charAt(i)))
            i++;
        name = s.substring(t, i);
        return i;
    }
}
