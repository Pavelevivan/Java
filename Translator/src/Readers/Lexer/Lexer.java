package Readers.Lexer;
import Readers.IReadable;
import Token.Token;
import java.util.ArrayList;
import java.lang.StringBuilder;



public class Lexer
{
    private ArrayList<IReadable> readers = new ArrayList<IReadable>();

    public void setRegister(IReadable reader) {
        readers.add(reader);
    }

    public void removeRegister(IReadable reader) { readers.remove(reader); }

    public Token[] getToxens(String str_equation ) {
        StringBuilder expression = new StringBuilder(str_equation);
        ArrayList<Token> tokens = new ArrayList<Token>();
        int maxLength = 0;
        for (int i = 0; i < expression.length(); i+=maxLength)
        {
            maxLength = 0;
            Token currentToken = null;
            int currentLength = 0;
            for (IReadable reg : readers) {
                Token token = reg.tryReadToken(expression.substring(i));

                if (token == null)
                    continue;

                currentLength = token.getToken().length();

                if (currentLength > maxLength)
                {
                    currentToken = token;
                    maxLength = currentLength;
                }
            }

            if (currentToken == null)
            {
                System.out.print(expression.substring(i));
                throw new NullPointerException("There is no such token");
            }

            tokens.add(currentToken);
        }

        return tokens.toArray(new Token[tokens.size()]);
    }
}


