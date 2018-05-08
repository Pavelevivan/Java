package Vector;
import Calculator.ComplexNumber;

public class VectorND
        extends BaseVector
{
    public VectorND(double ... vector)
    {
        super(vector);
    }

    public VectorND(ComplexNumber ... vector)
    {
        super(vector);
    }

    @Override
    protected VectorND create(ComplexNumber[] vector)
    {
        return new VectorND(vector);
    }

    public VectorND add(VectorND vector)
    {
        return (VectorND)internalGetSum(vector);
    }

    public VectorND subtract(VectorND vector)
    {
        return (VectorND)internalGetDifference(vector);
    }

    public ComplexNumber scalarProduct(VectorND vector)
    {
        return internalScalarProduct(vector);
    }

    public VectorND multiply(ComplexNumber number) {
        return (VectorND)internalMultiply(number);
    }
    public VectorND multiply(double number) {
        return (VectorND)internalMultiply(new ComplexNumber(number, 0.0));
    }
}
