package Calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner in = new Scanner(System.in);
        System.out.println("Введите выражение...");
        String str = in.nextLine();
        System.out.println(calculator.calculate(str));
        //System.out.println("-i + (1 + 2 ,2 ,3*5i ,4/2) + 2^4 / 8 - 18i + [1,1]*[30,70] - 2 *([20,30,40] + [30,70] - 9 + (1,2,3,4))");
        //System.out.println(calculator.calculate("i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)+i+(-i)"));
    }
}
