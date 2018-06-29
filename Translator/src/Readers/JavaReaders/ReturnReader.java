package Readers.JavaReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class ReturnReader implements IReadable {
    private static String value = "";
    private static final String type = "return";

    @Override
    public Token tryReadToken(String input) {
        int i = WhiteSpace.skipWS(input, 0);
        if (!input.startsWith(type, i)) return null;
        try {
            i = getReturnValue(input, i + 6);
        } catch (Exception e) {
            return null;
        }
        return new Token(input.substring(0, i), type, value);
    }

    private int getReturnValue(String input, int i) {
        i = WhiteSpace.skipWS(input, i);
        int t = i;
        while (Character.isLetterOrDigit(input.charAt(i)))
            i++;
        value = input.substring(t, i);
        return i;
    }
}
