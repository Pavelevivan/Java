package Vector;

public class Main {

    public static void main(String[] args) {
	Vector2D v1 = new Vector2D(1, 3);
	Vector2D v2 = new Vector2D(1, 4);
	Vector3D v3 = new Vector3D(1,2,3);
	//System.out.print(v2.getDifference(v3d));
	System.out.println("Vector v1:" + v1);
	System.out.println("Vector v2:" + v2);
	System.out.println("Scalar product v1,v2: " + v1.scalarProduct(v2));
	System.out.println("Difference of v1 and v2: " + v1.subtract(v2));
	System.out.println("Sum of v1 and v2:" + v1.add(v2));
    }
}
