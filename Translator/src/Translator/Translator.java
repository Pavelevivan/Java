package Translator;
import Readers.Languages.*;
import Token.Token;
import java.util.HashMap;

public class Translator {
    private HashMap<String, ILanguage> languages;

    public Translator(){
        languages = new HashMap<>();
    }

    public void register(ILanguage lang, String key) {
        languages.put(key, lang);
    }

    public String translate(String input, String keyFrom, String keyTo) {
        ILanguage langFrom = languages.get(keyFrom);
        ILanguage langTo = languages.get(keyTo);
        Token[] text = langFrom.interpret(input);
        String result = langTo.translate(text);
        return result;
    }

    private static void print(Token[] tokens){
        for (Token e : tokens) System.out.println(e);
    }
}
