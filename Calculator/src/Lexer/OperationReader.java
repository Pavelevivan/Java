package Lexer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperationReader
        implements IReadable
{
    private Pattern pattern = Pattern.compile("^[{}()\\[\\]+-^/*]");
    public Token tryReadToken(String expression)
    {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find())
        {
            String substring = expression.substring(matcher.start(),matcher.end());
            return new Token("Operator", substring, substring);
        }
        return null;
    }
}
