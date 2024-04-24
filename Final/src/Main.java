import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    static public class MyVector {
        public double x;
        public double y;
        public double w;

        public MyVector(double x, double y, double w) {
            this.x = x;
            this.y = y;
            this.w = w;
        }

        public double dot(MyVector z) {
            double product = (z.x * x) + (z.y * y) + (z.w * w);
            return product;
        }
    }

     static public class MyMatrix {
        public MyVector[] row = new MyVector[3];
        public MyMatrix(double xx, double xy, double xw,
                        double yx, double yy, double yw,
                        double wx, double wy, double ww) {

            row [0] = new MyVector(xx, xy, xw);
            row [1] = new MyVector(yx, yy, yw);
            row [2] = new MyVector(wx, wy, ww);
        }

        public MyVector multiply(MyVector z) {
            double x_dash = row[0].dot(z);
            double y_dash = row[1].dot(z);
            double w_dash = row[2].dot(z);
            return new MyVector(x_dash, y_dash, w_dash);
        }
    }


    public static  int lerp (int a, int b, float ratio) {
        if (ratio > 1f) {
            ratio = 1f;
        } else if (ratio < 0f) {
            ratio = 0f;
        }
        float iRatio = 1.0f - ratio;

        int aA = (a >> 24 & 0xff);
        int aR = ((a & 0xff0000) >> 16);
        int aG = ((a & 0xff00) >> 8);
        int aB = (a & 0xff);

        int bA = (b >> 24 & 0xff);
        int bR = ((b & 0xff0000) >> 16);
        int bG = ((b & 0xff00) >> 8);
        int bB = (b & 0xff);

        int A = ((int)(aA * iRatio) + (int)(bA * ratio));
        int R = ((int)(aR * iRatio) + (int)(bR * ratio));
        int G = ((int)(aG * iRatio) + (int)(bG * ratio));
        int B = ((int)(aB * iRatio) + (int)(bB * ratio));

        return A << 24 | R << 16 | G << 8 | B;
    }

    public static void main(String args []) throws IOException {
        BufferedImage inputImage = ImageIO. read (new File("input.jpg"));

        BufferedImage outputImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);

        MyMatrix matrix = new MyMatrix(0.8, 0.0, 0.0, 0.0, 0.5, 0.0, 0.0, 0.0, 0.5);

        for (int y = 0; y < outputImage.getHeight(); y ++) {
            for (int x = 0; x < outputImage.getWidth(); x ++) {
                MyVector z = new MyVector(x , y , 1.0);
                MyVector z_dash = matrix.multiply(z);

                int xl = (int) (z_dash.x); // Floor of x'
                int yl = (int) (z_dash.y); // Floor of y'
                int xu = xl + 1; // Ceil of x'
                int yu = yl + 1; // Ceil of y'
                float fx = (float)z_dash.x - (float)xl; // x lerp factor
                float fy = (float)z_dash.y - (float)yl; // y lerp factor

                int pixelA = inputImage.getRGB(xl, yl);
                int pixelB = inputImage.getRGB(xu, yl);
                int pixelC = inputImage.getRGB(xl, yu);
                int pixelD = inputImage.getRGB(xu, yu);

                int pixelAB = lerp(pixelA, pixelB, fx);
                int pixelCD = lerp(pixelC, pixelD, fx);
                int pixelABCD = lerp(pixelAB, pixelCD, fy);


                outputImage.setRGB(x, y, pixelABCD);
            }
        }

        try {
            ImageIO.write(outputImage, "png", new File("out.jpg"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}