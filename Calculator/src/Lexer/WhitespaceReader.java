package Lexer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WhitespaceReader

implements IReadable
{
    private Pattern pattern = Pattern.compile("^\\s+");
    public Token tryReadToken(String expression) {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find())
        {
            String substring = expression.substring(matcher.start(), matcher.end());
            return new Token("Space", substring, substring);
        }
        return null;
    }
}
