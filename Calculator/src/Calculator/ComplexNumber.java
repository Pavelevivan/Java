package Calculator;

public class ComplexNumber {

    public final double real;
    public final double image;

    public ComplexNumber(double real, double image) {
        this.real = real;
        this.image = image;
    }

    public ComplexNumber add(ComplexNumber number) {
        return new ComplexNumber(this.real + number.real, this.image + number.image);
    }

    public ComplexNumber subtract(ComplexNumber number) {
        return new ComplexNumber(this.real - number.real, this.image - number.image);
    }

    public ComplexNumber multiply(ComplexNumber number) {
        double real = this.real * number.real - this.image * number.image;
        double image = this.real * number.image + this.image * number.real;
        return new ComplexNumber(real, image);
    }

    public ComplexNumber divide(ComplexNumber number){
        double module = number.real * number.real + number.image * number.image;

        double real = this.real * number.real + this.image * number.image;
        double image = this.image * number.real - this.real * number.image;
        return new ComplexNumber(real / module, image / module);
    }

    public ComplexNumber pow(double degree){
        ComplexNumber result = this;
        for(int i = 1; i < degree; i++){
            result = result.multiply(this);
        }
        if(degree < 0) return null;
        if(degree == 0) return new ComplexNumber(1,0);
        return result;
    }

    @Override
    public final String toString(){
        StringBuilder builder = new StringBuilder();
        if(real != 0) {
            builder.append(real);
            if(image > 0) {
                builder.append('+');
            }
        }
        if(image != 0) {
            builder.append(image);
            builder.append('i');
        }
        if (image == 0 && real == 0)
            return "0";
        return builder.toString();
    }
}
