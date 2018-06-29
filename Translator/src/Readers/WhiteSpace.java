package Readers;

public class WhiteSpace {
    public static int skipWS(String expression, int i){
        for (; i < expression.length(); i++)
            if (expression.charAt(i) != ' ')
                break;
        return i;
    }
}
