package Lexer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите выражение...");
        String str = in.nextLine();
        Lexer lexer = new Lexer();
        lexer.setRegister(new IntegerReader());
        lexer.setRegister(new DoubleReader());
        lexer.setRegister(new OperationReader());
        lexer.setRegister(new VectorReader());
        lexer.setRegister(new ComplexNumberReader());
        lexer.setRegister(new WhitespaceReader());
        Token[] tokens = lexer.getTokens(str);
        for (Token t : tokens)
            System.out.print(t);

    }
}
