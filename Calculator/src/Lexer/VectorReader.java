package Lexer;

import java.util.HashMap;

public class VectorReader
implements IReadable
{
    private static HashMap<Character,Character> quotes = new HashMap<Character, Character>(){{
        put('{','}'); put('[',']'); put('(',')');}};
    public Token tryReadToken(String expression)
    {
        int i = 0;
        if(!quotes.containsKey(expression.charAt(i)))
            return null;
        char quote = expression.charAt(i);
        i++;
        int dimension  = 1;
        while (i < expression.length() && quotes.get(quote)!= expression.charAt(i)) {
            if(quotes.containsValue(expression.charAt(i)) || quotes.containsKey(expression.charAt(i)))
                return null;
            if(expression.charAt(i) == ','){
                dimension++;
            }
            i++;
        }
        i++;
        switch (dimension){
            case 1: return null;
            case 2: return new Token("Vector2D", expression.substring(0, i),expression.substring(0, i).replace(" ",""));
            case 3: return new Token("Vector3D",expression.substring(0, i),expression.substring(0,i).replace(" ",""));
            default: return new Token("VectorND",expression.substring(0, i),expression.substring(0,i).replace(" ",""));
        }
    }
}
