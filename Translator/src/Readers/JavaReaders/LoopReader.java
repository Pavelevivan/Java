package Readers.JavaReaders;

import Readers.IReadable;
import Readers.WhiteSpace;
import Token.Token;

public class LoopReader implements IReadable {
    private static String[] exp;
    private static String type = "";
    private static String name = "";

    @Override
    public Token tryReadToken(String expression) {

            int i;
            try {
                i = WhiteSpace.skipWS(expression, 0);
                i = findOperation(expression, i);
                if (i == -1) return null;
                i = getExp(expression, i);
            } catch (Exception e) {
                return null;
            }
            return new Token(expression.substring(0, i), type, name, exp);
        }

    private int findOperation(String s, int i) {
        type = "loop";
        if (s.startsWith(name="while", i)) return i + 5;
        if (s.startsWith(name="for", i)) return i + 3;
        return -1;
    }

    private int getExp(String s, int i) throws Exception {
        i = WhiteSpace.skipWS(s, i);
        if (s.charAt(i) != '(') throw new Exception();
        int start = ++i;
        for (int count = 1; i < s.length(); i++)
            if (s.charAt(i) == '(') count++;
            else if (s.charAt(i) == ')' && --count == 0) {
                if (name.equals("for"))
                    exp = getFromTo(s.substring(start, i).split(";"));
                return i + 1;
            }
        throw new Exception();
    }

    private String[] getFromTo(String[] e) {
        String operand = getOper(e[0]);
        String from = e[0].replaceAll("[^0-9]+", "");
        String to = e[1].replaceAll("[^0-9]+", "");
        String sign = e[2].replaceAll("[^+]+", "");
        return new String[]{operand, from, to, sign};
    }

    private String getOper(String s) {
        int i = 4;
        for (; s.charAt(i) != ' ' && s.charAt(i) != '+'; i++);
        return s.substring(4, i);
    }
}
