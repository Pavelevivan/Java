package Readers.JavaReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class AssignmentReader implements IReadable {
    private static String[] operands;
    private static String type = "assign";
    private static boolean binary;
    private static Character operation;

    @Override
    public Token tryReadToken(String expression)
    {
        clear();
        try {
            int i = findLeftOperand(expression, 0);
            if (i == - 1) return null;
            i = findOperation(expression, i);
            if (i == -1) return null;
            if (binary)
            {
                i = findRightOperand(expression, i);
            }
            else
            {
                operands[1] = "1";
            }
            return new Token(expression.substring(0, i), type, operation.toString(), operands);
        }
        catch (Exception e) {
            return null;
        }
    }

    private void clear()
    {
        operands = new String[2];
        binary = true;
        operation = null;
    }

    private int findLeftOperand(String expression, int i)
    {
        int start = i = WhiteSpace.skipWS(expression, i);

        while (Character.isLetterOrDigit(expression.charAt(i))
                || expression.charAt(i) == '[' || expression.charAt(i) == ']')
            i++;
        if (start == i) return -1;
        operands[0] = expression.substring(start, i);
        return i;
    }

    private int findRightOperand(String expression, int i)
    {
        i = WhiteSpace.skipWS(expression, i);
        int t = i;
        while (expression.charAt(i) != ';') i++;
        operands[1] = expression.substring(t, i);
        return i;
    }

    private int findOperation(String expression, int i)
    {
        i = WhiteSpace.skipWS(expression, i);
        switch (expression.substring(i, i + 2)) {
            case "+=": case "-=": case "*=":
            case "/=": case "%=": case "^=":
                operation = expression.charAt(i);
                return i + 2;
            case "++": case "--":
                binary = false;
                operation = expression.charAt(i);
                return i + 2;
        }
        if (expression.charAt(i) != '=' || expression.charAt(i + 1) == '=') return -1;
        operation = expression.charAt(i);
        return i + 1;
    }

}
