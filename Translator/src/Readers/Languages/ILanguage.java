package Readers.Languages;
import Token.Token;
public interface ILanguage{
    Token[] interpret(String program);
    String translate(Token[] tokens);
}
