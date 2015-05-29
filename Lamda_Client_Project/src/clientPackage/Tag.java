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
	
	//generates a new tag image
	public void makeTag() throws IOException {
		ImgRec.fillSquare(tagPic, 0, 0, 19, 39, Color.CYAN);	//draws blue region
		ImgRec.drawSquare(tagPic, 0, 0, 19, 39, 4, Color.RED);	//draws box around blue region
		Color c;
		for (int n = 20; n < 140; n += 15) {	//loop through x columns
			if (valBin[(n - 20) / 15] == true) {	//tests whether current region should be black or white
				c = Color.BLACK;
			} else {
				c = Color.WHITE;
			}
			ImgRec.fillSquare(tagPic, n, 0, n + 15, 39, c);	//fill current region with appropriate color
			ImgRec.drawSquare(tagPic, n - 1, 0, n + 14, 39, 4, Color.RED);	//draw red square around current region
		}
		writeTag();	//generate the new tag
	}
	
	//generates a tag that has already been created
	public void writeTag() throws IOException {
<<<<<<< HEAD
		File f = new File(name + val + ".png");	//creates a new file with appropriate name and corresponding id number
		ImageIO.write(tagPic, "PNG", f);	//writes file as a png
=======
		File f = new File("Tags/"+name + val + ".png");
		ImageIO.write(tagPic, "PNG", f);
>>>>>>> branch 'master' of https://github.com/Lamda-Coding/Client-Project.git
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
	
	//finds the decimal number represented by binary sequence a
	public static int convDec(boolean[] a) {
		int val = 0;
		for (int n = 0; n < a.length; n++) {	//loops through binary sequence
			if (a[n])
				val += Math.pow(2, a.length - n - 1);	//adds correct power of 2 to val
		}
		return val;
	}

	// ----------------------Text Editing Methods----------------------------
	// returns amount of lines in the file
	public static int lines(String file) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(file));	//creates sc to read from file
		int count = 0;
		while (sc.hasNextLine()) {	//runs through each line n file
			count++;	//counts number of lines that have been read
			sc.nextLine();
		}
		sc.close();
		return count;
	}

	// returns specified line l in file
	public static String getLine(String file, int l) throws IOException {
		l += 1;
		FileInputStream fs = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fs));	//create br to read from file
		for (int i = 0; i < l - 1; ++i) {	//runs through file until line l is reached
			if (!(lines(file) <= i)) {
				br.readLine();
			} else {
				br.close();
				return "";
			}
		}
		String temp = br.readLine();	//sets temp to content of line l
		br.close();
		return temp;
	}

	// writes line s to end of file
	public static void addLine(String file, String s) throws IOException {
		FileWriter t = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(t);	//creates bufferWriter to write to file
		bufferWritter.write(s + "\n");	//writes s to end of file
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
