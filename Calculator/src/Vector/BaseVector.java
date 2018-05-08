package Vector;
import Calculator.ComplexNumber;

public abstract class BaseVector
    implements IVector
{
    private ComplexNumber[] vector;
    private int dimension;
    
    public int getDimension(){return dimension;}

    public BaseVector(ComplexNumber[] vector)
    {
        this.vector = vector.clone();
        this.dimension = vector.length;
    }

    public BaseVector(double ... vector)
    {
        ComplexNumber[] complexVector = new ComplexNumber[vector.length];
        for (int i = 0; i < vector.length; i++ )
            complexVector[i] = new ComplexNumber(vector[i], 0.0);
        this.vector = complexVector.clone();
        this.dimension = complexVector.length;
    }
    protected abstract BaseVector create(ComplexNumber[] vector);

    public boolean isZero()
    {
        for(ComplexNumber coordinate : vector)
            if (coordinate.image != 0 || coordinate.real != 0)
                return false;
        return true;
    }

    @Override
    public boolean equals(Object vector){
        if (vector == null || (this.getClass() != vector.getClass()))
            if (this == vector)
                return true;
        return false;
    }

    @Override
    public int hashCode(){
        double num = 357;
        int hashSum = 0;
        for (int i = 0; i < dimension;i++)
            hashSum += vector[i].real*(num + 1) + vector[i].image*(num);
        return hashSum;
    }

    @Override
    public String toString(){
        StringBuilder str_view = new StringBuilder();
        str_view.append("(");
        for (int i = 0; i < dimension - 1; i++) {
            str_view.append(vector[i] + ", ");
        }
        str_view.append(vector[dimension - 1]);
        str_view.append(')');
        return str_view.toString();
    }
    private ComplexNumber getCoordinate(int i)
    {
        return vector[i];
    }

    private boolean compareDimension(BaseVector vector)
    {
        return vector.getDimension() == this.getDimension();
    }
    
    protected BaseVector internalGetDifference(BaseVector vector)
    {
        if (!compareDimension(vector))
            throw new NullPointerException("Vectors have different dimension: " + this.vector + " " + vector);

        ComplexNumber[] newVector = new ComplexNumber[dimension];
        for (int i = 0; i < dimension; i++) {
            newVector[i] = new ComplexNumber( this.vector[i].real - vector.getCoordinate(i).real,
                    this.vector[i].image - vector.getCoordinate(i).image);
        }
        return create(newVector);
    }

    protected ComplexNumber internalScalarProduct(BaseVector vector)
    {
        if (!compareDimension(vector))
            throw new NullPointerException("Vectors have different dimension: " + this.vector.toString() + " " + vector);

        ComplexNumber scalarProduct = new ComplexNumber(0,0);
        for (int i = 0; i < dimension; i++)
            scalarProduct = scalarProduct.add(this.vector[i].multiply(vector.getCoordinate(i))) ;
        return scalarProduct;
    }

    protected BaseVector internalMultiply(ComplexNumber num)
    {
        ComplexNumber[] newVector = new ComplexNumber[dimension];
        for (int i = 0; i < dimension; i++)
            newVector[i] = vector[i].multiply(num);
        return create(newVector);
    }

    protected BaseVector internalGetSum(BaseVector vector)
    {
        if (!compareDimension(vector))
            throw new NullPointerException("Vectors have different dimension: " + this.vector + " " + vector);
        ComplexNumber[] newVector = new ComplexNumber[dimension];
        for (int i = 0; i < dimension; i++) {
            newVector[i] = this.vector[i].add(vector.getCoordinate(i));
        }
        return create(newVector);
    }
}
