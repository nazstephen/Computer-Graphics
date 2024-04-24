import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Transformation {
	public static void main(String args[]) throws IOException {
		BufferedImage inputImage = ImageIO.read(new File("inputImage.png"));
		BufferedImage outputImage = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		
		MyMatrix matrix = new MyMatrix(Math.sqrt(2.0), -Math.sqrt(2.0), 400.0,
									   Math.sqrt(2.0), Math.sqrt(2.0), -(800.0 * Math.sqrt(2.0)) + 400.0,
									   0.0, 0.0, 1.0);
	
		for (int y = 0; y < inputImage.getHeight(); y++) {
			for (int x = 0; x < inputImage.getWidth(); x++) {				
				MyVector v = new MyVector(x, y, 1.0);
				MyVector v_dash = matrix.multiply(v);
				
				int x_dash = (int)v_dash.x;
				int y_dash = (int)v_dash.y;
				
				if (x_dash < 0) 
					x_dash = 0;
				else if (x_dash >= inputImage.getWidth())
					x_dash = inputImage.getWidth() - 1;
				
				if (y_dash < 0) 
					y_dash = 0;
				else if (y_dash >= inputImage.getHeight())
					y_dash = inputImage.getHeight() - 1;
			
				outputImage.setRGB(x, y, inputImage.getRGB(x_dash, y_dash));
			}
		}
		
		
		try {
			ImageIO.write(outputImage, "png", new File("transformed.png"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
