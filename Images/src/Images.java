import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Images {
	public static void main(String args[]) throws IOException {
		BufferedImage image1 = ImageIO.read(new File("anonymous.jpg"));
		BufferedImage image2 = ImageIO.read(new File("spiderman.jpg"));
		BufferedImage image3 = ImageIO.read(new File("view.jpg"));
		BufferedImage image4 = ImageIO.read(new File("space.jpg"));
		BufferedImage image5 = ImageIO.read(new File("shrek.jpg"));
		
		invert(image1);
		grayscale(image2);
		colorCorrect(image3);
		mix(image4, image5);
	}
	
	public static void mix(BufferedImage image1, BufferedImage image2) {
		for (int y = 0; y < image1.getHeight(); y++) {
			for (int x = 0; x < image1.getWidth(); x++) {
				int pixel1 = image1.getRGB(x, y); 
				int red1 = (pixel1 & 0x00ff0000) >> 16;
				int green1 = (pixel1 & 0x0000ff00) >> 8;
				int blue1 = pixel1 & 0x000000ff;
				int alpha1 = pixel1 & 0xff000000;
				
				int pixel2 = image2.getRGB(x, y);
				int red2 = (pixel2 & 0x00ff0000) >> 16;
				int green2 = (pixel2 & 0x0000ff00) >> 8;
				int blue2 = pixel2 & 0x000000ff;
				int alpha2 = pixel2 & 0xff000000;
				
				double mix = 0.75;
				
				double mixRed = mix * red1 + (1.0 - mix) * red2;
				double mixGreen = mix * green1 + (1.0 - mix) * green2;
				double mixBlue = mix * blue1 + (1.0 - mix) * blue2;
				double mixAlpha = mix * alpha1 + (1.0 - mix) * alpha2;
				
				pixel1 = (int)mixAlpha | (int)mixBlue & 0x000000ff | (((int)mixGreen << 8) & 0x0000ff00) | (((int)mixRed << 16) & 0x00ff0000);
				
				image1.setRGB(x, y, pixel1);

			}
		}
	
		try {
			ImageIO.write(image1, "jpg", new File("mix.jpg"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void colorCorrect(BufferedImage image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				int red = (pixel & 0x00ff0000) >> 16;
				int green = (pixel & 0x0000ff00) >> 8;
				int blue = pixel & 0x000000ff;
				int alpha = pixel & 0xff000000;
				
				int gain = 2;
				int offset = 50;
				double gamma = 2.0;
				
				int newRed = red * gain;
				if (newRed > 255)
					newRed = 255;
				
				int newGreen = green + offset;
				if (newGreen > 255)
					newGreen = 255;
				
				double bf = ((float)blue) / 255.f;
				bf = Math.pow(bf, gamma);
				int newBlue = (int)((bf * 255.f) + 0.5f);
				if (newBlue > 255)
					newBlue = 255;

				pixel = alpha | (int)newBlue & 0x000000ff | ((newGreen << 8) & 0x0000ff00) | ((newRed << 16) & 0x00ff0000);

				image.setRGB(x, y, pixel);
			}
		}
		
		try {
			ImageIO.write(image, "jpg", new File("colorCorrect.jpg"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void grayscale(BufferedImage image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				int red = (pixel & 0x00ff0000) >> 16;
				int green = (pixel & 0x0000ff00) >> 8;
				int blue = pixel & 0x000000ff;
				int alpha = pixel & 0xff000000;
				
				int average = (red + green + blue) /3;

				pixel = alpha | (average & 0x000000ff) | ((average << 8) & 0x0000ff00) | ((average << 16) & 0x00ff0000);

				image.setRGB(x, y, pixel);
			}
		}
		
		try {
			ImageIO.write(image, "jpg", new File("grayscale.jpg"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public static void invert(BufferedImage image) {
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = image.getRGB(x, y);
				int red = (pixel & 0x00ff0000) >> 16;
				int green = (pixel & 0x0000ff00) >> 8;
				int blue = pixel & 0x000000ff;
				int alpha = pixel & 0xff000000;
				
				int invertRed = 255 - red;
				int invertGreen = 255 - green;
				int invertBlue = 255 - blue;

				pixel = alpha | (invertBlue & 0x000000ff) | ((invertGreen<<8) & 0x0000ff00) | ((invertRed<<16) & 0x00ff0000);
				
				image.setRGB(x, y, pixel);
			}
		}
		
		try {
			ImageIO.write(image, "jpg", new File("invert.jpg"));
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}