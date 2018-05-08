package Calculator;

import Lexer.Token;
import Vector.BaseVector;
import Vector.Vector2D;
import Vector.Vector3D;
import Vector.VectorND;

import java.util.ArrayList;
import java.util.HashMap;

public class Expression {
    private ComplexNumber complexNumber;
    private Vector2D vector2D;
    private Vector3D vector3D;
    private HashMap<Integer, VectorND> vectors;

    public Expression(Token token)
    {
        complexNumber = (ComplexNumber)token.getValue();
        vector2D = new Vector2D(0,0);
        vector3D = new Vector3D(0,0,0);
        vectors = new HashMap<>();
    }

    public Expression(BaseVector vector) {
        complexNumber = new ComplexNumber(0.0, 0.0);
        vector2D = new Vector2D(0, 0);
        vector3D = new Vector3D(0, 0, 0);
        vectors = new HashMap<>();
        if (vector instanceof Vector2D)
            vector2D = (Vector2D) vector;
        else if (vector instanceof Vector3D)
            vector3D = (Vector3D) vector;
        else if (vector instanceof VectorND) {
            VectorND vec = (VectorND) vector;
            vectors.put(vec.getDimension(), vec);
        }
    }

    public Expression Operate(Expression expression, String operation)
    {
        switch (operation)
        {
            case "+":
            {
                complexNumber = complexNumber.add(expression.complexNumber);
                vector2D = vector2D.add(expression.vector2D);
                vector3D = vector3D.add(expression.vector3D);
                for(Integer key: expression.vectors.keySet())
                    if (vectors.containsKey(key))
                        vectors.put(key, vectors.get(key).add(expression.vectors.get(key)));
                    else
                        vectors.put(key, expression.vectors.get(key));
                return this;
            }
            case "-":
            {
                complexNumber = complexNumber.subtract(expression.complexNumber);
                vector2D = vector2D.subtract(expression.vector2D);
                vector3D = vector3D.subtract(expression.vector3D);
                for(Integer key: expression.vectors.keySet())
                    if (vectors.containsKey(key))
                        vectors.put(key, vectors.get(key).subtract(expression.vectors.get(key)));
                    else
                        vectors.put(key, expression.vectors.get(key).multiply(-1));
                return this;
            }
            case "*":
            {
                ComplexNumber newComplexNumber = complexNumber.multiply(expression.complexNumber)
                        .add(vector2D.scalarProduct(expression.vector2D))
                        .add(vector3D.scalarProduct(expression.vector3D));
                vector2D = vector2D.multiply(expression.complexNumber).add(expression.vector2D.multiply(complexNumber));
                vector3D = vector3D.multiply(expression.complexNumber).add(expression.vector3D.multiply(complexNumber));
                for (Integer dimension: vectors.keySet()) {
                    vectors.put(dimension, vectors.get(dimension).multiply(expression.complexNumber));
                    if (expression.vectors.containsKey(dimension)) {
                        newComplexNumber = newComplexNumber.add(vectors.get(dimension).scalarProduct(expression.vectors.get(dimension)));
                        vectors.put(dimension, vectors.get(dimension).add((expression.vectors.get(dimension).multiply(complexNumber))));
                    }
                }
                for (Integer dimension: expression.vectors.keySet())
                    if (!vectors.containsKey(dimension))
                        vectors.put(dimension, expression.vectors.get(dimension).multiply(complexNumber));
                complexNumber = newComplexNumber;
                return this;
            }
            case "/":
            {
                if((expression.vectors.keySet().size() == 0) || (expression.vector3D.isZero()) || expression.vector2D.isZero())
                    throw new ArithmeticException("Wrong division: vector on vector");

                ComplexNumber divider = new ComplexNumber(1,0).divide(expression.complexNumber);
                complexNumber = complexNumber.multiply(divider);
                vector2D = vector2D.multiply(divider);
                vector3D = vector3D.multiply(divider);
                for (Integer dimension: vectors.keySet())
                    vectors.put(dimension, vectors.get(dimension).multiply(divider));

                return this;
            }
            case "^": {
                complexNumber = complexNumber.pow(expression.complexNumber.real);
                return this;
            }
        }
        return this;
    }

    public Token[] tokenize()
    {
        ArrayList<Token> tokens = new ArrayList<>();
        if (complexNumber.real != 0)
            tokens.add(new Token("Integer", Double.toString(complexNumber.real) , new ComplexNumber(complexNumber.real, 0)));
        if (complexNumber.image != 0)
            tokens.add(new Token("Complex", Double.toString(complexNumber.image) + "i", new ComplexNumber(0, complexNumber.image)));
        if (!vector2D.isZero())
            tokens.add(new Token("Vector2D", vector2D.toString(), vector2D));
        if (!vector3D.isZero())
            tokens.add(new Token("Vector3D", vector3D.toString(), vector3D));
        for (BaseVector vector : vectors.values())
            if (!vector.isZero())
                tokens.add(new Token("VectorND", vector.toString(), vector));
        return tokens.toArray(new Token[tokens.size()]);
    }
}
