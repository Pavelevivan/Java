package Readers.PascalReaders;
import Readers.WhiteSpace;
import Token.Token;
import Readers.IReadable;
public class AssignmentReader implements IReadable{
    private static String[] operands;
    private static final String type = "assign";

    @Override
    public Token tryReadToken(String expression) {
        clear();
        int i;
        try {
            i = findLeftOperand(expression, 0);
            i = findOperation(expression, i);
            if (i == -1) return null;

            else i = findRightOperand(expression, i);
        } catch (Exception e) {
            return null;
        }
        return new Token(expression.substring(0, i), type, operands);
    }

    private int findRightOperand(String expression, int i) {
        i = WhiteSpace.skipWS(expression, i);
        int t = i;
        while (expression.charAt(i) != ';') i++;
        operands[1] = expression.substring(t, i);
        return i;
    }

    private void clear() {
        operands = new String[2];
    }

    private int findOperation(String expression, int i) {
        i = WhiteSpace.skipWS(expression, i);
        if (expression.substring(i, i + 2).equals(":="))
            return i + 2;

        return -1;
    }

    private int findLeftOperand(String expression, int i) {
        i = WhiteSpace.skipWS(expression, i);
        int t = i;
        while (Character.isLetterOrDigit(expression.charAt(i))
                || expression.charAt(i) == '[' || expression.charAt(i) == ']')
            i++;
        operands[0] = expression.substring(t, i);
        return i;
    }
}
