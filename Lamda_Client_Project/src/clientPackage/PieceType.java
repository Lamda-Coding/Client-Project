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
	
	public void setTotal(int a) {
		total = a;
		storage = a;
	}
	
	public void getTag() throws IOException {
		tag.makeTag(); 
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getStorage() {
		return storage;
	}
	
	public int getCheckedOut() {
		return checkedOut;
	}
	
	public int getLost() {
		return lost;
	}
	
}
