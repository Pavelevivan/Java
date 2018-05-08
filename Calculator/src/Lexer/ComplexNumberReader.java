package Lexer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Calculator.ComplexNumber;

public class ComplexNumberReader
        implements IReadable {
    private Pattern pattern = Pattern.compile("(^(\\d+([,.]\\d+)?)?[iIj])");

    public Token tryReadToken(String expression) {
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {

            String substring = expression.substring(matcher.start(), matcher.end());
            String value = substring.replace(",", ".");
            if(value.length() == 1)
                value = "1 ";
            return new Token("Complex",
                    substring, new ComplexNumber(0, Double.parseDouble(value.substring(0, value.length() - 1))));
        }
        return null;
    }
}

