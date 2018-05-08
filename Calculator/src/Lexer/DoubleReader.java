package Lexer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import Calculator.ComplexNumber;
public class DoubleReader
    implements IReadable
{
    private Pattern pattern = Pattern.compile("(^\\d+[.]\\d+)");
    public Token tryReadToken(String expression)
    {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find())
        {
            String substring = expression.substring(matcher.start(), matcher.end());
            return new Token("Double", substring, new ComplexNumber(Double.parseDouble(substring), 0));
        }
        return null;
    }
}
