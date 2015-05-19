package clientPackage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

import javax.imageio.ImageIO;

public class TagsTest {

	public static void main(String[] args) throws IOException {
		PieceType Azeem = new PieceType("Azeem");
		Azeem.getTag();
		Azeem.getLost();
	}
}
