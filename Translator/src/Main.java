import Readers.Languages.*;
import Translator.Translator;
public class Main {

    public static void main(String[] args) {
        String program_pascal =  "Program  RRR; Var s : String; s : integer; e : integer; i : integer; e : integer; " +
                "begin i := 2; for e := 0 to 2 do e := e + 1; " +
                "if ( i = 3 ) and ( d <> 3 ) then i := 4; else i := 6; end.";
        String program_java = "public static void RRR(String[] args)" +
                 "{ String s = \"abbfd\"; int i = 2; for (int e = 0; e < 2; e++) e = 3; if(i == 3 && d != 3) i = 4; else i = 6;}";
        Translator translator = new Translator();
        JavaLanguage javaLanguage = new JavaLanguage();
        PascalLanguage pascalLanguage = new PascalLanguage();
        translator.register(javaLanguage, "java");
        translator.register(pascalLanguage, "pascal");
        //String res = translator.translate(program_java, "java", "pascal");
        String res = translator.translate(program_pascal, "pascal", "java");
        System.out.println(res);
    }
}
