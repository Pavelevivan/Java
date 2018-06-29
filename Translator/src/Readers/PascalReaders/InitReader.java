package Readers.PascalReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;
import java.util.regex.*;

public class InitReader implements IReadable {
    private String type = "init";
    private String subtype;
    private String[] values;
    private Pattern pattern = Pattern.compile("(((\\w,(\\s)*))+\\w|\\w)(\\s)*:(\\s)*(String|integer|char|real|boolean);");

    @Override
    public Token tryReadToken(String expression) {
        int i = WhiteSpace.skipWS(expression, 0);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()){
            if (matcher.start() != i)
                return null;
            subtype = matcher.group(7);
            values = matcher.group(1).split(",");
            return new Token(expression.substring(0, matcher.end()),type, subtype, values);
        }
        else
            return null;
    }

    private int getValues(String s, int i) {
        i = WhiteSpace.skipWS(s, i);
        int ind = s.indexOf("begin")-1;
        int t = ind == -1 ? s.length() : ind;
        values = s.substring(i, t).split(";|:");
        return t;
    }
}
