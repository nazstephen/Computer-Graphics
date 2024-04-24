
public class MyMatrix {
	public MyVector[] row = new MyVector[3];
	public MyMatrix(double xx, double xy, double xw,
					double yx, double yy, double yw,
					double wx, double wy, double ww) {
		
		row[0] = new MyVector(xx, xy, xw);
		row[1] = new MyVector(yx, yy, yw);
		row[2] = new MyVector(wx, wy, ww);
	}

	public MyVector multiply(MyVector v) {
		double x_dash = row[0].dot(v);
		double y_dash = row[1].dot(v);
		double w_dash = row[2].dot(v);
		return new MyVector(x_dash, y_dash, w_dash);
	}
}
