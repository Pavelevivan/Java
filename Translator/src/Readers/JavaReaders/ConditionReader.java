package Readers.JavaReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class ConditionReader implements IReadable {
    private String exp;
    private String type = "condition";
    private String name = "";

    @Override
    public Token tryReadToken(String expression) {
        int i = findOperation(expression);
        if (i == -1) return null;
        i = WhiteSpace.skipWS(expression, i);
        if (name.equals("else"))
            return new Token(expression.substring(0, i), type, name, null);
        else
            try {
                i = getExp(expression, i);
                return new Token(expression.substring(0, i), type, name, exp);
            }
            catch (Exception e)
            {
                return null;
            }
    }

    private int getExp(String expression, int i) throws Exception
    {
        i = WhiteSpace.skipWS(expression, i);
        if (expression.charAt(i) != '(') throw new Exception();
        int start = ++i;
        for (int count = 1; i < expression.length(); i++)
        {
            if (expression.charAt(i) == '(') count++;
            else if (expression.charAt(i) == ')' && --count == 0) {
                exp = expression.substring(start, i);
                return i + 1;
            }
        }
        throw new Exception();
    }

    private int findOperation(String expression){
        int i = WhiteSpace.skipWS(expression, 0);
        if(expression.startsWith(name="if", i)) return i + 2;
        if(expression.startsWith(name="else", i)) return i + 4;
        return -1;
    }
}
