package Lexer;

public class Token
{
    private final Object value;
    private final String type;
    private final String text;

    public Token(String type, String text, Object value)
    {
        this.text = text;
        this.type = type;
        this.value = value;
    }

    public String getText(){return text;}
    public String getType() {return type;}
    public Object getValue() {return value;}

    @Override
    public String toString()
    {
        return this.type + ": \"" + this.text + "\" ";
    }
}
