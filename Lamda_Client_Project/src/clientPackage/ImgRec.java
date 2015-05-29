package clientPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//Image Recognition Utility
//Used for drawing on images, locating tags, and reading tags

public class ImgRec {
	
	//draws line on image i from (x1, y1) to (x2, y2) with size bw and color c
	public static void drawLine(BufferedImage i, int x1, int y1, int x2,
			int y2, int bw, Color c) {
		boolean useX = x2 - x1 > y2 - y1;	//determines whether to iterate through x or y first as to avoid infinite slopes
		if (useX) {
			double slope = (double) (y2 - y1) / (double) (x2 - x1);	//determines slope of line
			double k = (double) y1 - slope * (double) x1;	//determines y intercept
			for (int n = 0; n < i.getWidth(); n++) {	//changes row
				for (int m = 0; m < i.getHeight(); m++) {	//changes column
					if (Math.abs(n * slope + k - m) * 2 < bw	//tests whether pixel is within bw / 2 of the line
							&& n >= x1 - bw / 2 && n <= x2 + bw / 2) {
						i.setRGB(n, m, c.getRGB());	//changes pixel to c
					}
				}
			}
		} else {	//same as other case except when noted
			for (int m = 0; m < i.getHeight(); m++) {
				for (int n = 0; n < i.getWidth(); n++) {
					double slope = (double) (x2 - x1) / (double) (y2 - y1);	//slope with x and y values switched
					double k = (double) x1 - slope * (double) y1;	//determines x intercept
					if (Math.abs(m * slope + k - n) * 2 < bw
							&& m >= y1 - bw / 2 && m <= y2 + bw / 2) {
						i.setRGB(n, m, c.getRGB());
					}
				}
			}
		}
	}
	
	//draws square on i from (x, y) to (w, h) with brush size bw and color c
	public static void drawSquare(BufferedImage i, int x, int y, int w, int h,
			int bw, Color c) {
		drawLine(i, x, y, w, y, bw, c);	//draws 4 lines to make the square
		drawLine(i, w, y, w, h, bw, c);
		drawLine(i, x, h, w, h, bw, c);
		drawLine(i, x, y, x, h, bw, c);
	}
	
	//fills in square on i from (x, y) to (w, h) with color c
	public static void fillSquare(BufferedImage i, int x, int y, int w, int h, Color c) {
		for (int n = x; n < w; n++) {	//loops through all pixels in specified region and sets to c
			for (int m = y; m < h; m++) {
				i.setRGB(n, m, c.getRGB());
			}
		}
	} 
	
	//uses distance theorem to calculate where color a and color b are a similar color, given sensitivity e
	public static boolean sameColor(Color a, Color b, int e) {
		double d = Math.sqrt(Math.pow(b.getRed() - a.getRed(), 2)
				+ Math.pow(b.getGreen() - a.getGreen(), 2)
				+ Math.pow(b.getBlue() - a.getBlue(), 2));	//finds virtual distance between a and b RGB values
		return d < e;	//returns whether distance is within sensitivity
	}
	
	//finds the average color of the 9 pixels in the square from (x - 1, y - 1) to (x + 1, y + 1)
	public static int getRegionColor(BufferedImage i, int x, int y) {
		float avgRed = 0;
		float avgBlue = 0;
		float avgGreen = 0;
		int loops = 0;	//accounts for pixels being skipped due to being out of bounds
		Color c;
		for (int n = 0; n < 3; n++) {	//loop through all 9 pixels
			for (int m = 0; m < 3;  m++) {
				if (x - m + 1  >= 0 && x - m + 1 < i.getWidth() && y - n + 1 >= 0 && y - n + 1 < i.getHeight()) {	//make sure pixel is in bounds
					c = new Color(i.getRGB(x - m + 1, y - n + 1));	//finds color of current pixel
					avgRed += c.getRed();	//adds current pixel's RGB values
					avgBlue += c.getBlue();
					avgGreen += c.getGreen();
					loops++;
				}
			}
		}
		avgRed /= loops;	//finds average each color
		avgBlue /= loops;
		avgGreen /= loops;
		int avgColor = (new Color((int)avgRed, (int)avgGreen, (int)avgBlue)).getRGB();	//converts to RGB
		return avgColor;
	}
	
	//finds the top boundary of the region of color c on i, starting at (x, y) with sensitivity e
	public static int getTop(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));	//gets color of current pixel
		if (y > 0 && sameColor(a, c, e)) {	//tests whether a is same as c
			return getTop(i, x, y - 1, c, e);	//calls getTop for one pixel above
		}
		return y;
	}
	
	//similar to getTop, but finds the bottom boundary and moves right to prevent slanted regions from not being found
	public static int getBottom(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));	//gets color of current pixel
		Color z;
		if (x + 5 < i.getWidth()) {	//tests whether pixel 5 to the right is in bounds
			z = new Color(getRegionColor(i, x + 5, y));		//sets z to 5 pixels to the right
		} else {
			z = Color.BLACK;	//sets z to black
		}
		if (y < i.getHeight() - 1 && sameColor(a, c, e)) {	//tests whether a is same as c
			return getBottom(i, x, y + 1, c, e);	//calls getBottom for one pixel down
		} else if (sameColor(z, c, e)) {	//tests whether z is same as c
			return getBottom(i, x + 1, y, c, e);	//calls getBottom for one pixel right
		}
		return y;
	}
	
	//similar to getBottom, but gets left boundary and moves up to account for slanted regions
	public static int getLeft(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));	//gets color of current pixel
		Color z;
		if (y + 5 < i.getHeight()) {	//tests whether pixel 5 below is in bounds
			z = new Color(getRegionColor(i, x, y + 5));	//sets z to 5 pixels below
		} else {
			z = Color.BLACK;	//sets z to black
		}
		if (x > 0 && sameColor(a, c, e)) {	//test whether a is same as c
			return getLeft(i, x - 1, y, c, e);	//calls getLeft for one pixel left
		} else if (sameColor(z, c, e)) {	//tests whether z is same as c
			return getLeft(i, x, y + 1, c, e);	//calls getLeft for one pixel down
		}
		return x;
	}
	
	//similar to getLeft, but gets right boundary and moves up recursively
	public static int getRight(BufferedImage i, int x, int y, Color c, int e) {
		Color a = new Color(getRegionColor(i, x, y));	//gets color of current pixel
		Color z;
		if (y + 5 < i.getHeight()) {	//tests whether pixel 5 below is in bounds
			z = new Color(getRegionColor(i, x, y + 5));	//sets z to pixel 5 below
		} else{
			z = Color.BLACK;	//sets z to black
		}
		if (x < i.getWidth() - 1 && sameColor(a, c, e)) {	//tests whether a is same as c
			return getRight(i, x + 1, y, c, e);	//calls getRight for one pixel right
		} else if (sameColor(z, c, e)) {	//tests whether z is same as c
			return getRight(i, x, y + 1, c, e);	//calls getRight for one pixel down
		}
		return x;
	}
	
	//finds a square region of color c on i with sensitivity e
	//used for testing purposes only
	public static int[] findRegion(BufferedImage i, Color c, int e) {
		int[] region = new int[4];
		Color x;
		int n = 0;
		int m = 0;
		int xVal = 0;
		int yVal = 0;
		boolean size = false;
			for (m = 0; m < i.getHeight() - 5; m += 5) {	//loops through all pixels
				for (n = 0; n < i.getWidth() - 5; n += 5) {
					x = new Color(i.getRGB(n, m));	//set x to current pixel
					if (sameColor(c, x, e)) {	//test whether current pixel is same as target
						xVal = n;
						yVal = m;
						if (xVal <= i.getWidth() && yVal <= i.getHeight()) {	//test whether current pixel is in range
							region[0] = getLeft(i, xVal, yVal, c, e);	//finds bounds starting at current pixel
							region[1] = getTop(i, xVal, yVal, c, e);
							region[2] = getRight(i, xVal, yVal, c, e);
							region[3] = getBottom(i, xVal, yVal, c, e);
							size = (region[2] - region[0]) * (region[3] - region[1]) > (i.getWidth() / 6 * i.getHeight() / 6);	//calculates size of region
							if (size) {	//tests whether size is large enough
								m = i.getHeight();	//ends loop
								n = i.getWidth();
							}
						} else {
							System.out.println("nothing recognized");
							m = i.getHeight();	//ends loop
							n = i.getWidth();
						}
					}
				}
			}
		return region;
	}
	
	//same as findRegion but starts at startCoords rather than (0, 0)
	public static int[] findRegionAlt(BufferedImage i, Color c, int e, int[] startCoords) {
		int[] region = new int[4];
		Color x;
		int n = 0;
		int m = 0;
		int xVal = 0;
		int yVal = 0;
		boolean size = false;
			for (m = 0; m < i.getHeight() - 5; m += 5) {	//loops through all pixels starting at startCoords
				for (n = 0; n < i.getWidth() - 5; n += 5) {
					x = new Color(i.getRGB(n, m));	//set x to current pixel
					if (sameColor(c, x, e)) {	//tests whether current pixel is same as target
						xVal = n;
						yVal = m;
						if (xVal <= i.getWidth() && yVal <= i.getHeight()) {	//tests whether current pixel is in range
							region[0] = getLeft(i, xVal, yVal, c, e);	//finds bounds starting at current pixel
							region[1] = getTop(i, xVal, yVal, c, e);
							region[2] = getRight(i, xVal, yVal, c, e);
							region[3] = getBottom(i, xVal, yVal, c, e);
							size = (region[2] - region[0]) * (region[3] - region[1]) > (i.getWidth() / 6 * i.getHeight() / 6);	//calculates size of region
							if (size) {	//tests whether size is large enough
								m = i.getHeight();	//ends loop
								n = i.getWidth();
							}
						} else {
							m = i.getHeight();	//ends loop
							n = i.getWidth();
						}
					}
				}
			}
		return region;
	}
	
	//finds the boundaries of the blue portion of the tag
	public static int[] findTag(BufferedImage i) {
		Color c;
		Color d = new Color(10, 120, 160);	//sets d to approximate target blue color
		int[] coords = {0, 0, 0, 0};
		int[] pix = new int[2];
		for (int n = 0; n < i.getWidth(); n += 5) {	//loops through all pixels
			for (int m = 0; m < i.getHeight(); m += 5) {
				pix[0] = n;	//sets pix to current pixel coordinates
				pix[1] = m;
				c = new Color(getRegionColor(i, n, m));	//sets c to getRegionColor of current pixel
				if(sameColor(c, d, 100)) {	//tests whether current pixel is blue
					coords = findRegionAlt(i, d, 100, pix);	//finds bounds of the blue part of the tag
					return coords;
				}
			}
		}
		return coords;
	}
	
	//once tag is found, this reads the binary sequence of the tag on i
	public static boolean[] readTag(BufferedImage i) {
		int[] bounds = findTag(i);	//sets bounds to bounds of blue region
		if (bounds[0] == 0 && bounds[1] == 0 && bounds[2] == 0 && bounds[3] == 0) {	//tests whether a tag was found
			boolean[] a = {false};
			return a;
		}
		boolean[] binSeq = new boolean[8];
		int y = (bounds[1] + bounds[3]) / 2;	//set y to middle y value of blue region
		int a = 0;
		boolean s = false;
		for (int n = bounds[0]; n < i.getWidth() && a < 8; n ++) {	//loop through pixels at y starting at left bound of blue region
			Color pColor =  new Color(getRegionColor(i, n, y));
			if (sameColor(pColor, Color.BLACK, 100)) {
				binSeq[a] = true;
				s = true;
			} else if (sameColor(pColor, new Color(200, 200, 200), 100)) {
				s = true;
				binSeq[a] = false;
			} else if (sameColor(pColor, new Color(190, 60, 60), 100) && s) {
				a++;
				s = false;
			}
			//i.setRGB(n, y, Color.GREEN.getRGB());	//set current pixel to green to show path
		}
		int height = bounds[3] - bounds[1];	//calculate height of blue region
		fillSquare(i, bounds[0] - 10, bounds[1] - 10, bounds[0] + height + 10, bounds[3] + 10, Color.RED);	//fill in blue region so tag is not read twice
		return binSeq;
	}
	
	//goes through image i and reads all tags
	public static int[] readAllTags(BufferedImage i) {
		ArrayList<boolean[]> tags = new ArrayList<boolean[]>();
		boolean[] tagNum;
		boolean loop = true;
		while(loop) {
			tagNum = readTag(i);	//set tagNum to the binary sequence of the current tag
			if (tagNum.length != 1) {	//test whether a tag was found
				tags.add(tagNum);	//adds tagBum to tags ArrayList
			} else {
				loop = false;
			}
		}
		int[] tagsDec = new int[tags.size()];
		for (int n = 0; n < tags.size(); n++) {
			tagsDec[n] = (Tag.convDec(tags.get(n)));	//add decimal version of tags.get(n) to tagsDec
		}
		return tagsDec;
	}

}
