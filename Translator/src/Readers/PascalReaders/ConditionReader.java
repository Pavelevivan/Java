package Readers.PascalReaders;
import Readers.IReadable;
import Token.Token;
import Readers.WhiteSpace;
public class ConditionReader implements IReadable {
    private String type = "condition";
    private String subtype = "";
    private String operands;
    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        i = findCondition(expression, i);
        if (i == -1) return null;
        if (subtype.equals("else")) return new Token(expression.substring(0, i), type, subtype, null);
        else
        {
            int start = i;
            i = expression.indexOf("then");
            if (i == -1) return null;
            operands = expression.substring(start, i)
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .replaceAll("and", "&&")
                    .replaceAll("or", "||")
                    .replaceAll("^==", "==")
                    .replaceAll("<>", "!=")
                    .trim();
            return new Token(expression.substring(0, i + 4), type, subtype, operands);
        }
    }

    private int findCondition(String expression, int i){
        subtype = "if";
        if (expression.startsWith("if", i)) return i + 2;
        subtype = "else";
        if (expression.startsWith("else", i)) return i + 4;
        return -1;
    }
}
