
public class MyVector {
    public double x;
    public double y;
    public double w;
   
    public MyVector(double x, double y, double w) {
        this.x = x;
        this.y = y;
        this.w = w;
    }

    public double dot(MyVector v) {
    	double product = (v.x * x) + (v.y * y) + (v.w * w);
        return product; 
    }
}
