package clientPackage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PieceType {
	
	private String type;
	private Tag tag;
	private int total;
	private int storage;
	private int checkedOut;
	private int lost;
	
	public PieceType(String t) throws FileNotFoundException {
		type = t;
		tag = new Tag(type);
	}
	
	public void getTag() throws IOException {
		tag.makeTag();
	}
	
	public void addPiece() {
		total++;
		storage++;
	}
	
	public void checkOut(int a) {
		storage -= a;
		checkedOut += a;
	}
	
	public void checkIn(int a) {
		storage += a;
		checkedOut -=a;
	}
	
	public void losePiece() {
		total--;
		lost++;
	}
	
	public void findPiece() {
		total++;
		lost--;
	}
	
	public void setAmount(int a) {
		total = a;
		storage = a;
	}
	
}
