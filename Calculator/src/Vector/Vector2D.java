package Vector;
import Calculator.ComplexNumber;

public class Vector2D extends BaseVector
    implements IVector
{
    public Vector2D(double x, double y)
    {
        super(x, y);
    }

    public Vector2D(ComplexNumber x, ComplexNumber y)
    {
        super(new ComplexNumber[] {x, y});
    }

    @Override
    protected Vector2D create(ComplexNumber[] vector)
    {
        return new Vector2D(vector[0], vector[1]);
    }

    public Vector2D add(Vector2D vector)
    {
        return (Vector2D)internalGetSum(vector);
    }

    public Vector2D subtract(Vector2D vector)
    {
        return (Vector2D)internalGetDifference(vector);
    }

    public ComplexNumber scalarProduct(Vector2D vector)
    {
        return internalScalarProduct(vector);
    }

    public Vector2D multiply(ComplexNumber number) {
        return (Vector2D)internalMultiply(number);
    }

    public Vector2D multiply(double number)
    {
        return (Vector2D)internalMultiply(new ComplexNumber(number,0.0));
    }
}
