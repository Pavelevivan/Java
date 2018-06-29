package Readers.Languages;

import Readers.Lexer.Lexer;
import Readers.PascalReaders.*;
import Token.Token;

public class PascalLanguage implements ILanguage {
    private Lexer lexer;
    public PascalLanguage(){
        lexer = new Lexer();
        lexer.setRegister(new ProgramReader());
        lexer.setRegister(new VarReader());
        lexer.setRegister(new InitReader());
        lexer.setRegister(new BlockReader());
        lexer.setRegister(new AssignmentReader());
        lexer.setRegister(new LoopReader());
        lexer.setRegister(new ConditionReader());
        lexer.setRegister(new EndStatementReader());
    }
    @Override
    public Token[] interpret(String program) {
        return lexer.getToxens(program);
    }

    @Override
    public String translate(Token[] tokens) {
        StringBuilder program = new StringBuilder();
        StringBuilder init = new StringBuilder("Var");
        for(Token token: tokens)
        {
            switch (token.getType())
            {
                case "method":
                {
                    String[] parameters = (String[]) token.getValue();
                    if(parameters.equals(new String[] {"public", "static", "void", "main"}))
                        program.append("Program main");
                    break;
                }
                case "init":
                {
                    String[] val = (String[]) token.getValue();
                    init.append("\n\r\t").append(val[1]).append(" : ");
                    if (val[0].equals("int")) val[0] = "integer";
                    else if (val[0].equals("double")) val[0] = "real";

                    if (token.getSubtype().equals("arr"))
                        init.append(String.format("array [0..%s] of %s", val[2], val[0]));
                    else init.append(val[0]);
                    init.append(";");
                    break;
                }
                case "block":
                {
                    if (token.getSubtype().equals("open"))
                        program.append("\n\rbegin\n");
                    else
                        program.append("end;\n\r\t");
                    break;
                }
                case "assign":
                {
                    String[] val = (String[]) token.getValue();
                    String sign = token.getSubtype();
                    if (sign.equals("="))
                        program.append(String.format("%s :%s %s", val[0], sign, val[1]));
                    else program.append(String.format("%s := %s %s %s", val[0], val[0], sign, val[1]));
                    break;
                }
                case "condition":
                {
                    String type = token.getSubtype();
                    program.append(type);
                    if (type.equals("else")) program.append("\n\t");
                    else {
                        String val = ((String) token.getValue());
                        val = val
                                .replaceAll("\\&\\&", ") and (")
                                .replaceAll("\\|\\|", ") or (")
                                .replaceAll("==", "=")
                                .replaceAll("!=", "<>");

                        program.append(" ( ").append(val).append(" ) then\n\t");
                    }
                    break;
                }
                case "loop":
                {
                    String[] val = (String[]) token.getValue();
                    String type = token.getSubtype();
                    program.append(type);
                    if (type.equals("for")) {
                        if (val[3].equals("++"))
                            program.append(String.format(" %s := %s to %s do\n\r\t", val[0], val[1], val[2]));
                        else program.append(String.format(" %s := %s downto %s do\n\r\t", val[0], val[1], val[2]));
                        init.append(String.format("\n\r\t%s : integer;", val[0]));
                    } else program.append(" ( ").append(val[0]).append(" ) do\n\t");
                    break;
                }
                case "endstatement":
                {
                    program.append(";\n\r");
                }
            }
        }
        return init.append(program).toString();
    }
}
