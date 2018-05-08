package Lexer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Calculator.ComplexNumber;
public class IntegerReader
        implements IReadable
{
    private Pattern pattern = Pattern.compile("^\\d+");
    public Token tryReadToken(String expression)
    {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find())
        {
            String substring = expression.substring(matcher.start(), matcher.end());
            return new Token("Integer", substring, new ComplexNumber(Integer.parseInt(substring), 0));
        }
        return null;
    }
}
