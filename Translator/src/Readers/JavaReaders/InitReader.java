package Readers.JavaReaders;
import Readers.IReadable;
import Token.Token;
import Readers.WhiteSpace;
public class InitReader implements IReadable {
    private static String name = "";
    private static final String type = "init";
    private static String subtype = "";

    @Override
    public Token tryReadToken(String expression) {
        String value;
        int i = WhiteSpace.skipWS(expression, 0);
        i = findInit(expression, i);
        if (i == -1) return null;
        try {
            i = checkForArray(expression, i);
            value = getName(expression, i);
            if (subtype.equals("arr")) {
                String size = findSize(expression, i);
                String[] arr = {name, value, size};
                return new Token(expression.substring(0, expression.indexOf(";")+1), type, subtype, arr);
            }
        } catch (Exception e) {
            return null;
        }
        String[] arr = {name, value};
        return new Token(expression.substring(0, i-1), type, subtype, arr);
    }

    private String getName(String s, int i) throws Exception {
        int t = WhiteSpace.skipWS(s, i);
        i = t;
        if (!Character.isLetter(s.charAt(t))) throw new Exception();
        while (Character.isLetter(s.charAt(i))
                || Character.isDigit((s.charAt(i))))
            i++;
        return s.substring(t, i);
    }

    private String  findSize(String s, int i) throws Exception {
        while (s.charAt(i) != '[')
            i++;
        i = WhiteSpace.skipWS(s, i);
        int start = i;
        if (!Character.isDigit(s.charAt(i))) throw new Exception();
        while (true)
        {
            if (Character.isDigit(s.charAt(i)))
                i++;
            else
                if (s.charAt(i) == ']' || s.charAt(i) == ' ')
                    break;
                else
                    throw new Exception();
        }
        return s.substring(start, i);
    }

    private int findInit(String s, int i) {
        if (s.startsWith(name="int", i)) return i + 3;
        if (s.startsWith(name="double", i)) return i + 6;
        if (s.startsWith(name="String", i)) return i + 6;
        if (s.startsWith(name="char", i)) return i + 4;
        if (s.startsWith(name="boolean", i)) return i + 7;
        name = null;
        return -1;

    }

    private int checkForArray(String s, int i) throws Exception {
        int t = WhiteSpace.skipWS(s, i);
        if (s.startsWith("[]", t)) {
            subtype = "arr";
            return t + 3;
        }
        if (t == i) throw new Exception();
        subtype = "unit";
        return t;
    }
}
