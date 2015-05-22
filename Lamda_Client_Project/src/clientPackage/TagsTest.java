package clientPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

import javax.imageio.ImageIO;

public class TagsTest {

	public static void main(String[] args) throws IOException {
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File("tagA.png"));
		} catch (IOException e) {
		}
		boolean[] coords = ImgRec.readTag(i);
		int a = Tag.convDec(coords);
		System.out.println(a);
		//ImgRec.fillSquare(i, 0, 0, 25, 25, new Color(ImgRec.getRegionColor(i, 0, 0)));
		File f = new File("pic_test.png");
		ImageIO.write(i, "PNG", f);
	}
}