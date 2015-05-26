package clientPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ImgRec {

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
	
	public static int getRegionColor(BufferedImage i, int x, int y) {
		float avgRed = 0;
		float avgBlue = 0;
		float avgGreen = 0;
		int loops = 0;
		Color c;
		for (int n = 0; n < 3; n++) {
			for (int m = 0; m < 3;  m++) {
				if (x - m + 1  >= 0 && x - m + 1 < i.getWidth() && y - n + 1 >= 0 && y - n + 1 < i.getHeight()) {
					//i.setRGB(x - m + 1, y - n + 1, Color.RED.getRGB());
					c = new Color(i.getRGB(x - m + 1, y - n + 1));
					avgRed += c.getRed();
					avgBlue += c.getBlue();
					avgGreen += c.getGreen();
					loops++;
				}
			}
		}
		avgRed /= loops;
		avgBlue /= loops;
		avgGreen /= loops;
		int avgColor = (new Color((int)avgRed, (int)avgGreen, (int)avgBlue)).getRGB();
		return avgColor;
	}

	public static int getTop(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));
		if (y > 0 && sameColor(a, c, e)) {
			return getTop(i, x, y - 1, c, e);
		}
		return y;
	}

	public static int getBottom(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));
		Color z;
		if (x + 5 < i.getWidth()) {
			z = new Color(getRegionColor(i, x + 5, y));	
		} else {
			z = Color.BLACK;
		}
		if (y < i.getHeight() - 1 && sameColor(a, c, e)) {
			return getBottom(i, x, y + 1, c, e);
		} else if (sameColor(z, c, e)) {
			return getBottom(i, x + 1, y, c, e);
		}
		return y;
	}

	public static int getLeft(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));
		Color z;
		if (y + 5 < i.getHeight()) {
			z = new Color(getRegionColor(i, x, y + 5));
		} else {
			z = Color.BLACK;
		}
		if (x > 0 && sameColor(a, c, e)) {
			return getLeft(i, x - 1, y, c, e);
		} else if (sameColor(z, c, e)) {
			return getLeft(i, x, y + 1, c, e);
		}
		return x;
	}

	public static int getRight(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));
		Color z;
		if (y + 5 < i.getHeight()) {
			z = new Color(getRegionColor(i, x, y + 5));
		} else{
			z = Color.BLACK;
		}
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
		//while (!size) {
			for (m = 0; m < i.getHeight() - 5; m += 5) {
				for (n = 0; n < i.getWidth() - 5; n += 5) {
					x = new Color(i.getRGB(n, m));
					//System.out.println("n and m are: " + n + " and " + m);
					if (sameColor(c, x, e)) {
						xVal = n;
						yVal = m;
						if (xVal <= i.getWidth() && yVal <= i.getHeight()) {
							region[0] = getLeft(i, xVal, yVal, c, e);
							region[1] = getTop(i, xVal, yVal, c, e);
							region[2] = getRight(i, xVal, yVal, c, e);
							region[3] = getBottom(i, xVal, yVal, c, e);
							//System.out.println("actualSize: " + (region[2] - region[0]) * (region[3] - region[1]));
							//System.out.println("targetSize: " + i.getWidth() / 6 * i.getHeight() / 6);
							size = (region[2] - region[0]) * (region[3] - region[1]) > (i.getWidth() / 6 * i.getHeight() / 6);
							//System.out.println("size: " + size);
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
	
	public static int[] findRegionAlt(BufferedImage i, Color c, int e, int[] startCoords) {
		int[] region = new int[4];
		Color x;
		int n = 0;
		int m = 0;
		int xVal = 0;
		int yVal = 0;
		boolean size = false;
		//while (!size) {
			for (m = 0; m < i.getHeight() - 5; m += 5) {
				for (n = 0; n < i.getWidth() - 5; n += 5) {
					x = new Color(i.getRGB(n, m));
					//System.out.println("n and m are: " + n + " and " + m);
					if (sameColor(c, x, e)) {
						xVal = n;
						yVal = m;
						if (xVal <= i.getWidth() && yVal <= i.getHeight()) {
							region[0] = getLeft(i, xVal, yVal, c, e);
							region[1] = getTop(i, xVal, yVal, c, e);
							region[2] = getRight(i, xVal, yVal, c, e);
							region[3] = getBottom(i, xVal, yVal, c, e);
							//System.out.println("actualSize: " + (region[2] - region[0]) * (region[3] - region[1]));
							//System.out.println("targetSize: " + i.getWidth() / 6 * i.getHeight() / 6);
							size = (region[2] - region[0]) * (region[3] - region[1]) > (i.getWidth() / 6 * i.getHeight() / 6);
							//System.out.println("size: " + size);
							if (size) {
								m = i.getHeight();
								n = i.getWidth();
							}
						} else {
							//System.out.println("nothing recognized");
							m = i.getHeight();
							n = i.getWidth();
						}
					}
				}
			}
		return region;
	}
	
	/*
	public static int[] findTag(BufferedImage i) {
		Color c;
		Color d = new Color(10, 120, 160);
		Color f = Color.BLACK;
		int[] coords = {0, 0, 0, 0};
		boolean first = true;
		for (int n = 0; n < i.getWidth(); n += 5) {
			for (int m = 0; m < i.getHeight(); m += 5) {
				c = new Color(getRegionColor(i, n, m));
				System.out.println("n: " + n + " m: " + m);
				f = new Color(c.getRed(), c.getGreen(), c.getBlue());
				if (sameColor(c, d, 100) && first) {
					coords[0] = n;
					coords[1] = m;
					first = false;
				} else if(sameColor(c, d, 100)) {
					coords[2] = n;
					coords[3] = m;
				}
			}
		}
		for (int n = 0; n < 4; n++) {
			System.out.println(coords[n]);
		}
		fillSquare(i, 0, 0, 25, 25, d);
		fillSquare(i, 0, 25, 25, 50, f);
		return coords;
	}
	*/
	
	public static int[] findTag(BufferedImage i) {
		Color c;
		Color d = new Color(10, 120, 160);
		int[] coords = {0, 0, 0, 0};
		int[] pix = new int[2];
		for (int n = 0; n < i.getWidth(); n += 5) {
			for (int m = 0; m < i.getHeight(); m += 5) {
				pix[0] = n;
				pix[1] = m;
				c = new Color(getRegionColor(i, n, m));
				System.out.println("n: " + n + " m: " + m);
				if(sameColor(c, d, 100)) {
					coords = findRegionAlt(i, d, 100, pix);
					return coords;
				}
			}
		}
		for (int n = 0; n < 4; n++) {
			System.out.println(coords[n]);
		}
		return coords;
	}
	
	public static boolean[] readTag(BufferedImage i) {
		int[] bounds = findTag(i);
		if (bounds[0] == 0 && bounds[1] == 0 && bounds[2] == 0 && bounds[3] == 0) {
			boolean[] a = {false};
			return a;
		}
		boolean[] binSeq = new boolean[8];
		int y = (bounds[1] + bounds[3]) / 2;
		int a = 0;
		boolean s = false;
		for (int n = bounds[0]; n < i.getWidth() && a < 8; n ++) {
			i.setRGB(n, y, Color.GREEN.getRGB());
			Color pColor =  new Color(getRegionColor(i, n, y));
			if (sameColor(pColor, Color.BLACK, 100)) {
				System.out.println("black");
				binSeq[a] = true;
				s = true;
			} else if (sameColor(pColor, new Color(200, 200, 200), 100)) {
				System.out.println("white");
				s = true;
				binSeq[a] = false;
			} else if (sameColor(pColor, new Color(190, 60, 60), 100) && s) {
				a++;
				System.out.println("moving on");
				s = false;
			}
		}
		int height = bounds[3] - bounds[1];
		fillSquare(i, bounds[0] - 10, bounds[1] - 10, bounds[0] + height + 10, bounds[3] + 10, Color.RED);
		return binSeq;
	}
	
	public static ArrayList<boolean[]> readAllTags(BufferedImage i) {
		ArrayList<boolean[]> tags = new ArrayList<boolean[]>();
		boolean[] tagNum;
		boolean loop = true;
		while(loop) {
			tagNum = readTag(i);
			if (tagNum.length != 1) {
				tags.add(tagNum);
			} else {
				loop = false;
			}
		}
		return tags;
	}

	public static void main(String[] args) throws IOException {
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File("tag144.png"));
		} catch (IOException e) {
		}
		//Color c = new Color(99, 143, 87);
		Color c = Color.RED;
		System.out.println("width: " + i.getWidth() + "  height: "
				+ i.getHeight());
		System.out.println("c: " + (c.getRed() + c.getGreen() + c.getBlue()));
		int[] region = findRegion(i, c, 5);
		for (int n : region) {
			System.out.println(n);
		}
		File f = new File("pic_test.png");
		ImageIO.write(i, "PNG", f);
	}

}
