package Vector;
import Calculator.ComplexNumber;

public class Vector3D extends BaseVector
    implements IVector
{
    public Vector3D(double x, double y, double z)
    {
        super(x, y, z);
    }

    public Vector3D(ComplexNumber x, ComplexNumber y, ComplexNumber z)
    {
        super(new ComplexNumber[]{x, y, z});
    }

    @Override
    protected  Vector3D create(ComplexNumber[] vector)
    {
        return new Vector3D(vector[0], vector[1], vector[2]);
    }

    public Vector3D add(Vector3D vector)
    {
        return (Vector3D)internalGetSum(vector);
    }

    public Vector3D subtract(Vector3D vector)
    {
        return (Vector3D)internalGetDifference(vector);
    }

    public ComplexNumber scalarProduct(Vector3D vector)
    {
        return internalScalarProduct(vector);
    }

    public Vector3D multiply(ComplexNumber number)
    {
        return (Vector3D)internalMultiply(number);
    }
    public Vector3D multiply(double number)
    {
        return (Vector3D)internalMultiply(new ComplexNumber(number, 0.0));
    }
}
