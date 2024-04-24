import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Program1B {
	public static void main(String args[]) throws IOException {
		BufferedImage inputImage = ImageIO.read(new File("bright.jpg"));
		darken(inputImage);
	}
	
	public static void darken(BufferedImage inputImage) {
		BufferedImage outputImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < inputImage.getHeight(); y++) {
			for (int x = 0; x < inputImage.getWidth(); x++) {
				int pixel = inputImage.getRGB(x, y);
				int red = (pixel & 0x00ff0000) >> 16;
				int green = (pixel & 0x0000ff00) >> 8;
				int blue = pixel & 0x000000ff;
				int alpha = pixel & 0xff000000;
				
				double gain = 0.5;
				
				double newRed = red * gain;
				if (newRed > 255.0)
					newRed = 255.0;
				
				double newGreen = green * gain;
				if (newGreen > 255.0)
					newGreen = 255.0;
				
				double newBlue = blue * gain;
				if (newBlue > 255.0)
					newBlue = 255.0;

				pixel = alpha | (int)newBlue & 0x000000ff | ((int)newGreen << 8) & 0x0000ff00 | ((int)newRed << 16) & 0x00ff0000;

				outputImage.setRGB(x, y, pixel);
			}
		}
		
		try {
			ImageIO.write(outputImage, "jpg", new File("dark.jpg"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
