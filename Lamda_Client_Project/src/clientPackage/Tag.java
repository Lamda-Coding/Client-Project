package clientPackage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;

@SuppressWarnings("unused")
public class Tag {
	// --------------------------------------Fields-------------------------------------
	private BufferedImage tagPic = new BufferedImage(140, 40,
			BufferedImage.TYPE_INT_RGB);
	private boolean[] valBin = new boolean[8];// Binary Value constrained to
	// decimal 0-511
	private int val; // Decimal value
	private String name; // Name of Part

	// ---------------------------------------------------------------------------------

	// ------------------------------------Constructors---------------------------------
	public Tag(String n) throws FileNotFoundException { // Constructor with name
		// given
		val = -1; // initialize value
		ArrayList<String> tagsList=new ArrayList<String>();
		ArrayList<String> namesList=new ArrayList<String>();
		//InventoryGUI.Sheetdata;
		//for (int i=0;i<InventoryGUI.Sheetdata.size();i++){ //get all sheets
			for(int j=0;j<InventoryGUI.Sheetdata.get(0).size();j++){ //get all rows in sheet
				tagsList.add(InventoryGUI.Sheetdata.get(0).get(j).get(1));
				namesList.add(InventoryGUI.Sheetdata.get(0).get(j).get(0));
			}
		//}
		for (int i = 0; i < namesList.size(); i++) { // iterate through
			// tags txt file
			if (namesList.get(i).equals(n)) { // if the part is in the
				// list
				val = Integer.parseInt(tagsList.get(i)); // set value to the value of that part
				break; // break from loop since found
			}
		}
		if (val == -1) { // If not found
				val = tagsList.size()-1; // value is last line number
				for(int x=0;x<InventoryGUI.Sheetdata.size();x++){
					//System.out.println(x);
					//Null pointer exception?
					//null pointer exception when trying more than once
					//InventoryGUI.inventoryFile.write(x,tagsList.size(),0,n);
					add(x,tagsList.size(),0,n);
					String valS=String.valueOf(val);
					//InventoryGUI.inventoryFile.write(x,tagsList.size(),1,valS);
					add(x,tagsList.size(),1,valS);
					//InventoryGUI.inventoryFile.write(x,tagsList.size(),2,"0");
					add(x,tagsList.size(),2,"0");
				//addLine("tags.txt", n); // add part to the file
				}
				//Write to excel file

		}
		name = n; // set part name
		// write to file
		convBin(); // set binary value using method convBin()
		//System.out.println(val);
		//System.out.println(lines("tags.txt"));
	}

	public void makeTag() throws IOException {
		ImgRec.fillSquare(tagPic, 0, 0, 19, 39, Color.CYAN);
		ImgRec.drawSquare(tagPic, 0, 0, 19, 39, 4, Color.RED);
		Color c;
		for (int n = 20; n < 140; n += 15) {
			if (valBin[(n - 20) / 15] == true) {
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}
			ImgRec.fillSquare(tagPic, n, 0, n + 15, 39, c);
			ImgRec.drawSquare(tagPic, n - 1, 0, n + 14, 39, 4, Color.RED);
		}
		writeTag();
	}

	public void writeTag() throws IOException {
		File f = new File(name + val + ".png");
		ImageIO.write(tagPic, "PNG", f);
	}

	public Tag(int v) { // find tag for value at v
		val = v; // set val
		ArrayList<String> tagsList=new ArrayList<String>();
		ArrayList<String> namesList=new ArrayList<String>();
		//for (int i=0;i<InventoryGUI.Sheetdata.size();i++){ //get all sheets
			for(int j=0;j<InventoryGUI.Sheetdata.get(0).size();j++){ //get all rows in sheet
				tagsList.add(InventoryGUI.Sheetdata.get(0).get(j).get(1));
				namesList.add(InventoryGUI.Sheetdata.get(0).get(j).get(0));
			}
		//}
		for(int x=0;x<tagsList.size();x++){
			if (tagsList.get(x).equals(String.valueOf(v))){
				name=namesList.get(x);
				break;
			}
		}
		//name = getLine("tags.txt", v); // get name from file
		convBin(); // set binary
	}

	// ---------------------------------------------------------------------------------
	private void convBin() { // method for setting valBin from val
		int temp = val; // temp value holding val
		if (temp > Math.pow(2, 8)) { // if too big to hold
			System.out.println("Error"); // error
		} else { // if small enough to hold
			for (int i = 7; i >= 0; i--) { // iterate through powers of 2
				if (temp >= Math.pow(2, i)) { // if greater than 2^i
					temp -= Math.pow(2, i); // subtract that value
					valBin[8 - i - 1] = true; // set that power to true (1 in
					// binary)
				}
			}
		}
	}

	public static int convDec(boolean[] a) {
		int val = 0;
		for (int n = 0; n < a.length; n++) {
			if (a[n])
				val += Math.pow(2, a.length - n - 1);
		}
		return val;
	}

	// ----------------------Text Editing Methods----------------------------
	// returns amount of lines in the file
	public static int lines(String file) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(file));
		int count = 0;
		while (sc.hasNextLine()) {
			count++;
			sc.nextLine();
		}
		sc.close();
		return count;
	}

	// returns specified line
	public static String getLine(String file, int l) throws IOException {
		l += 1;
		FileInputStream fs = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));
		for (int i = 0; i < l - 1; ++i) {
			if (!(lines(file) <= i)) {
				br.readLine();
			} else {
				br.close();
				return "";
			}
		}
		String temp = br.readLine();
		br.close();
		return temp;
	}

	// writes line to end of file
	public static void addLine(String file, String s) throws IOException {
		FileWriter t = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(t);
		bufferWritter.write(s + "\n");
		bufferWritter.close();
	}

	// -------------------------------------------------------------------

	// --------------------------Accessor Methods-------------------------
	public boolean[] getBin() { // get binary value as boolean array
		return (valBin); // return boolean array representing binary
	}

	public String getName() { // get name
		return name; // return name
	}

	public int getVal() { // get decimal value
		return val; // return decimal value
	}
	// -----------------------------------------------------
	private void add(int sheet,int row,int col,String val){
		InventoryGUI.inventoryFile.write(sheet,row,col,val);
	}


}
