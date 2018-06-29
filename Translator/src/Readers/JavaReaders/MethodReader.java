package Readers.JavaReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class MethodReader implements IReadable {
    private String[] args;
    private String type = "method";
    private String scope;
    private String access;
    private String name;
    private String return_type;

    @Override
    public Token tryReadToken(String expression) {
        int i = findScope(expression);
        if (i == -1) return null;
        i = findReturnType(expression, i, "return");
        if (i == -1) return null;
        i = findReturnType(expression, i, "name"); //find name is the same method
        if (i == -1) return null;
        try {
            i = findArguments(expression, i);
            String subtype = scope + ";" + access + ";" + return_type + ";" + name;
            return new Token(expression.substring(0, i), type, subtype, args);
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private int findArguments(String expression, int i) throws Exception
    {
        i = WhiteSpace.skipWS(expression, i);
        if (expression.charAt(i++) != '(') throw new Exception();

        for (int t = i, count = 1; i < expression.length(); i++)
            if (expression.charAt(i) == '(') count++;
            else if (expression.charAt(i) == ')' && --count == 0) {
                args = expression.substring(t, i).split(" |, ");
                return i + 1;
            }
        throw new Exception();
    }
    private int findScope(String s) {
        access = "dynamic";
        int start = 0;
        int i = 0;
        if (s.startsWith(scope="public", i)) i += 6;
        else if (s.startsWith(scope="private", i)) i += 7;
        else if (s.startsWith(scope="protected", i)) i+=9;
        if (i == start) return -1;
        i = WhiteSpace.skipWS(s, i);
        if (s.startsWith("static", i)) i += 6;
            access = "static";
        return i;
    }

    private int findReturnType(String expression, int i, String var) {
        i = WhiteSpace.skipWS(expression, i);
        int start = i;
        while (Character.isLetterOrDigit(expression.charAt(i))) {
            i++;
        }
        if (start == i) return -1;
        if (var.equals("name"))
            name = expression.substring(start, i);
        else if (var.equals("return"))
            return_type = expression.substring(start, i);
        return i;
    }
}
