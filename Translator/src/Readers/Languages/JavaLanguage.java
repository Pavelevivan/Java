package Readers.Languages;
import Readers.Lexer.Lexer;
import Token.Token;
import Readers.JavaReaders.*;

import java.util.HashMap;

public class JavaLanguage implements ILanguage{
    private static Lexer lexer;
    public JavaLanguage()
    {
        lexer = new Lexer();
        lexer.setRegister(new AssignmentReader());
        lexer.setRegister(new BlockReader());
        lexer.setRegister(new InitReader());
        lexer.setRegister(new LoopReader());
        lexer.setRegister(new ConditionReader());
        lexer.setRegister(new MethodReader());
        lexer.setRegister(new ReturnReader());
        lexer.setRegister(new EndStatementReader());
    }

        @Override
        public Token[] interpret (String program){
        return lexer.getToxens(program);
    }

        @Override
        public String translate (Token[]tokens){
        StringBuilder program = new StringBuilder();
        StringBuilder init = new StringBuilder();
        for (Token token: tokens)
        {
            switch (token.getType()){
                case "init":{
                    StringBuilder variables = new StringBuilder();
                    for (String var: (String[])token.getValue()) {
                        variables.append(var);
                        variables.append(", ");
                    }
                    variables.delete(variables.length()-2, variables.length());
                    if (token.getSubtype().equals("integer"))
                        init.append(String.format("int %s;\n\t",variables.toString()));
                    else if (token.getSubtype().equals("real"))
                        init.append(String.format("int %s;\n\t", variables.toString()));
                    else
                        init.append(String.format("%s %s;\n\t",token.getSubtype(),variables.toString()));
                    break;
                }
                case "method":{
                    String []args = (String[])token.getValue();
                    program.append(String.format("%s %s %s %s(String[] args)\n\r",args[0], args[1], args[2], args[3]));
                    break;
                }
                case "block":{
                    if (token.getSubtype().equals("open")) {
                        program.append("{\n\t");
                        if (init.length() != 0) {
                            program.append(init);
                            init = new StringBuilder();
                        }
                    }
                    else
                        program.append("}\n\r");
                    break;
                }
                case "condition":{
                    String type = token.getSubtype();
                    program.append(type);
                    if (type.equals("else")) program.append("\n\t");
                    else {
                        String val = (String) token.getValue();
                        val = val
                                .replaceAll("\\(", "")
                                .replaceAll("\\)", "")
                                .replaceAll("and", "&&")
                                .replaceAll("or", "||")
                                .replaceAll("^==", "==")
                                .replaceAll("<>", "!=")
                                .trim();
                        program.append(" (").append(val).append(")\n\t");
                    }
                    break;
                }
                case "loop":{
                    String[] val = (String[]) token.getValue();
                    String type = token.getSubtype();
                    program.append(type);
                    if (type.equals("for"))
                        if (val[3].equals("++"))
                            program.append(String.format(" (%s = %s; %s < %s; %s++)\n\t",
                                    val[0], val[1], val[0], val[2], val[0]));
                        else program.append(String.format(" (%s = %s; %s >= %s; %s--)\n\t",
                                val[0], val[1], val[0], val[2], val[0]));
                    else program.append(" (").append(val[0]).append(")\n\t");
                    break;
                }
                case "assign":
                {
                    String[] val = (String[]) token.getValue();
                    program.append(String.format("%s = %s", val[0], val[1]));
                    break;
                }
                case "endstatement":{
                    program.append(";\n\r\t");
                        break;
                }
            }
        }
        return program.toString();
    }
}

