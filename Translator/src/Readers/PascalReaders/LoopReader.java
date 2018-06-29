package Readers.PascalReaders;
import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class LoopReader implements IReadable{
    private String type = "loop";
    private String subtype = "";
    private String[] operands;

    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        i = findOperation(expression, i);
        if (i == -1)
            return null;
        i = getOperands(expression, i);
        if (i == -1)
            return null;
        return new Token(expression.substring(0, i), type, subtype, operands);
    }

    private int findOperation(String expression, int i)
    {
        subtype = "while";
        if (expression.startsWith(subtype, i)) return i + 5;
        subtype = "for";
        if (expression.startsWith(subtype, i)) return i + 3;
        return -1;
    }

    private int getOperands(String expression, int i)
    {
        i = WhiteSpace.skipWS(expression, i);
        {
            for (int t = i; i < expression.length(); i++)
            {
                if (expression.charAt(i) == 'd' && i + 2 < expression.length() && expression.charAt(i+1) == 'o' && expression.charAt(i+2) != 'w'){
                    if (subtype.equals("for"))
                        operands = getFromTo(expression.substring(t, i), i);
                    else operands = new String[] {expression.substring(t, i)
                            .replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll("and", "&&")
                            .replaceAll("or", "||")
                            .replaceAll("^==", "==")
                            .replaceAll("<>", "!=")
                            .trim()};
                    return i + 2;
                }

            }
        }
        return -1;
    }

    private String[] getFromTo(String expression, int i)
    {
        String[] var = expression.split(":=| to");
        if (var.length == 3) return new String[]{var[0].trim(), var[1].trim(), var[2].trim(), "++"};
        var = expression.split(":=| downto");
        return new String[]{var[0].trim(), var[1].trim(), var[2].trim(), "--"};
    }


}
