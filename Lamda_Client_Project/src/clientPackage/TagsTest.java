package clientPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class TagsTest {

	public static void main(String[] args) throws IOException {
		Tag item1=new Tag(1);
		System.out.println(item1.getName());
		System.out.println(item1.getVal());
		System.out.println(Arrays.toString(item1.getBin()));
		Tag item2=new Tag("part1");
		System.out.println(item2.getName());
		System.out.println(item2.getVal());
		System.out.println(Arrays.toString(item2.getBin()));
		Tag item3=new Tag("part8");
		System.out.println(item3.getName());
		System.out.println(item3.getVal());
		System.out.println(Arrays.toString(item3.getBin()));
		
		Tag t = new Tag(144);
		t.makeTag();
		t.writeTag();
		BufferedImage i = null;
		try {
			i = ImageIO.read(new File("tag144.png"));
		} catch (IOException e) {
		}
		boolean[] a = ImgRec.readTag(i);
		for (int n = 0; n < 8; n++) {
			System.out.println(a[n]);
		}
		File f = new File("pic_test.png");
		ImageIO.write(i, "PNG", f);	}
	}

