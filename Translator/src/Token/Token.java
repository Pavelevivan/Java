package Token;

public class Token
{
    private final Object value;
    private final String type;
    private final String subtype;
    private final String text;

    public Token(String text, String type, Object value)
    {
        this.text = text;
        this.type = type;
        this.subtype = type;
        this.value = value;
    }

    public Token(String text, String type, String subtype, Object value)
    {
        this.text = text;
        this.type = type;
        this.subtype = subtype;
        this.value = value;
    }

    public String getToken(){return text;}
    public String getType() {return type;}
    public String getSubtype(){return subtype;}
    public Object getValue() {return value;}

    @Override
    public String toString()
    {
        return this.type + ": \"" + this.text + "\" ";
    }
}
