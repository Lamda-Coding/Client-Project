package clientPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImgRecTest {

	public static void drawLine(BufferedImage i, int x1, int y1, int x2,
			int y2, int bw, Color c) {
		boolean useX = x2 - x1 > y2 - y1;
		if (useX) {
			double slope = (double) (y2 - y1) / (double) (x2 - x1);
			double k = (double) y1 - slope * (double) x1;
			for (int n = 0; n < i.getWidth(); n++) {
				for (int m = 0; m < i.getHeight(); m++) {
					if (Math.abs(n * slope + k - m) * 2 < bw
							&& n >= x1 - bw / 2 && n <= x2 + bw / 2) {
						i.setRGB(n, m, c.getRGB());
					}
				}
			}
		} else {
			for (int m = 0; m < i.getHeight(); m++) {
				for (int n = 0; n < i.getWidth(); n++) {
					double slope = (double) (x2 - x1) / (double) (y2 - y1);
					double k = (double) x1 - slope * (double) y1;
					if (Math.abs(m * slope + k - n) * 2 < bw
							&& m >= y1 - bw / 2 && m <= y2 + bw / 2) {
						i.setRGB(n, m, c.getRGB());
					}
				}
			}
		}
	}

	public static void drawSquare(BufferedImage i, int x, int y, int w, int h,
			int bw, Color c) {
		drawLine(i, x, y, w, y, bw, c);
		drawLine(i, w, y, w, h, bw, c);
		drawLine(i, x, h, w, h, bw, c);
		drawLine(i, x, y, x, h, bw, c);
	}
	
	public static void fillSquare(BufferedImage i, int x, int y, int w, int h, Color c) {
		for (int n = x; n < w; n++) {
			for (int m = y; m < h; m++) {
				i.setRGB(n, m, c.getRGB());
			}
		}
	} 

	public static boolean sameColor(Color a, Color b, int e) {
		double d = Math.sqrt(Math.pow(b.getRed() - a.getRed(), 2)
				+ Math.pow(b.getGreen() - a.getGreen(), 2)
				+ Math.pow(b.getBlue() - a.getBlue(), 2));
		return d < e;
	}

	public static int getTop(BufferedImage i, int x, int y, Color c, int e) {
		i.setRGB(x,  y, Color.RED.getRGB());
		Color a = new Color(i.getRGB(x, y));
		if (y > 0 && sameColor(a, c, e)) {
			return getTop(i, x, y - 1, c, e);
		}
		return y;
	}

	public static int getBottom(BufferedImage i, int x, int y, Color c, int e) {
		i.setRGB(x,  y, Color.RED.getRGB());
		Color a = new Color(i.getRGB(x, y));
		Color z;
		if (x + 5 < i.getWidth()) {
			z = new Color(i.getRGB(x + 5, y));	
		} else {
			z = Color.BLACK;
		}
		if (y < i.getHeight() - 1 && sameColor(a, c, e)) {
			return getBottom(i, x, y + 1, c, e);
		} else if (sameColor(z, c, e)) {
			return getBottom(i, x + 1, y, c, e);
		}
		return x;
	}

	public static int getLeft(BufferedImage i, int x, int y, Color c, int e) {
		i.setRGB(x,  y, Color.RED.getRGB());
		Color a = new Color(i.getRGB(x, y));
		Color z = new Color(i.getRGB(x, y + 5));
		if (x > 0 && sameColor(a, c, e)) {
			return getLeft(i, x - 1, y, c, e);
		} else if (sameColor(z, c, e)) {
			return getLeft(i, x, y + 1, c, e);
		}
		return x;
	}

	public static int getRight(BufferedImage i, int x, int y, Color c, int e) {
		i.setRGB(x,  y, c.getRGB());
		Color a = new Color(i.getRGB(x, y));
		Color z = new Color(i.getRGB(x, y + 5));
		if (x < i.getWidth() - 1 && sameColor(a, c, e)) {
			return getRight(i, x + 1, y, c, e);
		} else if (sameColor(z, c, e)) {
			return getRight(i, x, y + 1, c, e);
		}
		return x;
	}

	public static int[] findRegion(BufferedImage i, Color c, int e) {
		int[] region = new int[4];
		Color x;
		int n = 0;
		int m = 0;
		int xVal = 0;
		int yVal = 0;
		boolean size = false;
		for (m = 0; m < i.getHeight(); m += 5) {
			for (n = 0; n < i.getWidth(); n += 5) {
				x = new Color(i.getRGB(n, m));
				if (sameColor(c, x, e)) {
					xVal = n;
					yVal = m;
					if (!(xVal == i.getWidth() && yVal == i.getHeight())) {
						region[0] = getLeft(i, xVal, yVal, c, e);
						region[1] = getTop(i, xVal, yVal, c, e);
						region[2] = getRight(i, xVal, yVal, c, e);
						region[3] = getBottom(i, xVal, yVal, c, e);
						System.out.println("actualSize: "
								+ (region[2] - region[0])
								* (region[3] - region[1]));
						System.out.println("targetSize: " + i.getWidth() / 6
								* i.getHeight() / 6);
						size = (region[2] - region[0])
								* (region[3] - region[1]) > (i.getWidth() / 6
								* i.getHeight() / 6);
						System.out.println("size: " + size);
						if (size) {
							m = i.getHeight();
							n = i.getWidth();
						}
					} else {
						System.out.println("nothing recognized");
						m = i.getHeight();
						n = i.getWidth();
					}
				}
			}
		}
		return region;
	}

	public static void main(String[] args) throws IOException {
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File("phonepic.jpg"));
		} catch (IOException e) {
		}
		Color c = new Color(99, 143, 87);
		System.out.println("width: " + i.getWidth() + "  height: "
				+ i.getHeight());
		System.out.println("c: " + (c.getRed() + c.getGreen() + c.getBlue()));
		int[] region = findRegion(i, c, 5);
		for (int n : region) {
			System.out.println(n);
		}
		//drawSquare(i, region[0], region[1], region[2], region[3], 5, Color.RED);
		File f = new File("pic_test.png");
		ImageIO.write(i, "PNG", f);
	}
	
	//HI AZEEM

}
