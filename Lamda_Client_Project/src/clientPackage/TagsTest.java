package clientPackage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.io.File;

import javax.imageio.ImageIO;

public class TagsTest {

	public static void main(String[] args) throws IOException {
		PieceType Azeem = new PieceType("Jack");
		Azeem.getTag();
		Azeem.setTotal(6);
		Azeem.losePiece();
		Azeem.losePiece();
		System.out.println(Azeem.getTotal());
		System.out.println(Azeem.getLost());
	}
}
