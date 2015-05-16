package clientPackage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

import javax.imageio.ImageIO;

public class TagsTest {

	public static void main(String[] args) throws IOException {
		Tag t = new Tag(182);
		t.makeTag();
		t.writeTag();
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File("tag182.png"));
		} catch (IOException e) {
		}
		boolean[] a = ImgRec.readTag(i);
		for (int n = 0; n < 8; n++) {
			System.out.println(a[n]);
		}
		System.out.println(Tag.convDec(a));
		File f = new File("pic_test.png");
		ImageIO.write(i, "PNG", f);
	}
}
