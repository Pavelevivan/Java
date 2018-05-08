package Calculator;

import Lexer.*;
import Vector.BaseVector;
import Vector.Vector2D;
import Vector.Vector3D;
import Vector.VectorND;

import java.util.*;

public class Calculator {
    private Lexer lexer;
    private Set<String> operators = Set.of("+", "-", "*", "/", "^", "{", "}", "(", ")", "[","]");
    private static Set<String> brackets = Set.of("(",")","[","]","{","}");
    private Set<String> operands = Set.of("Integer", "Double", "Complex");
    private Set<String> vectorTypes = Set.of("Vector2D", "Vector3D", "VectorND");
    private String open;
    private String close;
    public Calculator()
    {
        lexer = new Lexer();
        lexer.setRegister(new ComplexNumberReader());
        lexer.setRegister(new DoubleReader());
        lexer.setRegister(new VectorReader());
        lexer.setRegister(new IntegerReader());
        lexer.setRegister(new WhitespaceReader());
        lexer.setRegister(new OperationReader());
    }
    static private byte getPriority(String  s)
    {
        switch (s)
        {
            case "(": return 0;
            case ")": return 1;
            case "+": return 2;
            case "-": return 3;
            case "*": return 4;
            case "/": return 4;
            case "^": return 5;
            default: return 6;
        }
    }

    private boolean isCorrectBracketsSequence(String input){
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < input.length();i++) {
            if(input.charAt(i) == '('|| input.charAt(i) == '['|| input.charAt(i) == '{')
                stack.push(input.charAt(i));
            if(input.charAt(i) == ')'|| input.charAt(i) == ']'|| input.charAt(i) == '}')
                if(!stack.empty())
                    stack.pop();
                else return false;
        }
        return true;
    }

    private Token[] replaceSubtracting(Token[] tokens)
    {
        ComplexNumber negativeComplex = new ComplexNumber(-1, 0);
        Expression negative = new Expression(new Token("Integer", negativeComplex.toString(), negativeComplex));
        for (int i = tokens.length - 1; i >= 0; i--)
        {
            if (tokens[i].getText().equals("-"))
                if (operands.contains(tokens[i+1].getType()))
                {
                    ComplexNumber value = (ComplexNumber)tokens[i+1].getValue();
                    ComplexNumber value_mult = value.multiply(negativeComplex);
                    if (i > 0 && !operators.contains(tokens[i-1].getText()))
                    {
                        tokens[i + 1] = new Token(tokens[i + 1].getType(), value_mult.toString(), value_mult);
                        tokens[i] = new Token("Operation", "+", "+");
                    }
                    else {
                        tokens[i] = new Token(tokens[i+1].getType(), value_mult.toString(), value_mult);
                        tokens[i + 1] = new Token("Space", " ", " ");
                    }
                }
                else if (vectorTypes.contains(tokens[i+1].getType()))
                {
                    Expression expression = new Expression(getVector(tokens[i + 1].getText()));
                    expression = expression.Operate(negative,"*");
                    tokens[i + 1] = expression.tokenize()[0];
                    if (i > 0 && !operators.contains(tokens[i-1].getText())) {
                        tokens[i + 1] = expression.tokenize()[0];
                        tokens[i] = new Token("Operation", "+", "+");
                    }
                    else {
                        tokens[i + 1] = new Token("Space", " ", " ");
                        tokens[i] = expression.tokenize()[0];
                    }
                }
        }
        return tokens;
    }
    private Token[] removeSpaces(Token[] tokens) {
        ArrayList<Token> tokensNoSpaces = new ArrayList<>();
        for (Token token : tokens) {
            if(!token.getType().equals("Space"))
                tokensNoSpaces.add(token);
        }
        return tokensNoSpaces.toArray(new Token[tokensNoSpaces.size()]);
    }

    public String calculate(String expression)
    {
        if(!isCorrectBracketsSequence(expression))
            throw new ArithmeticException("Input should be a correct brackets sequence!");

        Token[] tokens = removeSpaces(lexer.getTokens(expression));
        System.out.println();
        tokens = replaceSubtracting(tokens);
        Token[]tokensInPolishEntry = getReversePolishNotation(tokens);
        tokens = counting(tokensInPolishEntry);
        if(tokens.length == 0)
            return "0";
        StringBuilder builder = new StringBuilder();

        for (Token token : tokens) {
            builder.append(token.getText() + " ");
            builder.append("+ ");
        }
        builder.delete(builder.length() - 2, builder.length() - 1);
        return builder.toString();
    }

    private Token[] counting(Token[] tokens) {

        Stack<Expression> stack = new Stack<Expression>();
        for (Token token : tokens) {
            if (vectorTypes.contains(token.getType())) {
                stack.push(new Expression(getVector(token.getText())));
            } else if (brackets.contains(token.getText())) continue;
            else if (operands.contains(token.getType())) {
                stack.push(new Expression(token));
            }
            if (operators.contains(token.getText())) {
                Expression second = stack.pop();
                Expression first = stack.pop();
                stack.push(first.Operate(second, token.getText()));
                }
            }
        return stack.peek().tokenize();
        }


    private Token[] getReversePolishNotation(Token[] tokens)
    {
        Stack<Token> stack = new Stack<>();
        ArrayList<Token> result = new ArrayList<>();
        try {
            for (Token token : tokens) {
                if (token.getType().equals("Space")) continue;
                if (operands.contains(token.getType()) || vectorTypes.contains(token.getType()))
                    result.add(token);
                else {
                    String text = token.getText();
                    if (operators.contains(text)) {
                        if (text.equals("(")) {
                            stack.push(token);
                            continue;
                        }
                        if (text.equals(")")) {
                            Token tok = stack.pop();
                            while (!tok.getText().equals("(")) {
                                if (stack.size() == 0)
                                    throw new InputMismatchException("Wrong sequence of brackets");
                                result.add(tok);
                                tok = stack.pop();
                            }
                            continue;
                        } else if (!stack.empty())
                            if (getPriority(token.getText()) < getPriority(stack.peek().getText()))
                                result.add(stack.pop());
                        stack.push(token);
                    }
                }
            }
        }
        catch (EmptyStackException e) {
            System.out.println("Wrong expression entered");
            throw new EmptyStackException();
        }
        while (!stack.empty())
            result.add(stack.pop());
        return result.toArray(new Token[result.size()]);
    }

    private BaseVector getVector(String input)
    {
        ArrayList<ComplexNumber> coordinates = new ArrayList<ComplexNumber>();
        input = input.substring(1);
        int index = input.indexOf(',');
        while (index >= 0){
            String exp = input.substring(0, index);
            Token[] tokens = lexer.getTokens(exp);
            ComplexNumber number = new ComplexNumber(0,0);
            for (Token token : counting(getReversePolishNotation(tokens))) {
                number = number.add((ComplexNumber)token.getValue());
            }
            coordinates.add(number);
            input = input.substring(index + 1);
            index = input.indexOf(',');
        }
        Token[] tokens = lexer.getTokens(input.substring(0, input.length() - 1));
        ComplexNumber number = new ComplexNumber(0,0);
        for (Token token : counting(getReversePolishNotation(tokens))) {
            number = number.add((ComplexNumber)token.getValue());
        }
        coordinates.add(number);
        switch (coordinates.size()){
            case 2: return new Vector2D(coordinates.get(0), coordinates.get(1));
            case 3: return new Vector3D(coordinates.get(0), coordinates.get(1), coordinates.get(2));
            default:  return new VectorND(coordinates.toArray(new ComplexNumber[coordinates.size()]));
        }
    }
}


