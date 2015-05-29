package clientPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

//This class is used for iterating through a directory (myDirectoryPath) and calling imgrec to read tags. Then it adds to the excel sheet the integer num
public class Iterate {
	public static void iter(String myDirectoryPath, int num){ //A directory and integer for how much to add
		File dir = new File(myDirectoryPath); //create the directory
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) { //if it is an existing directory
			  BufferedImage i = null;
		    for (File child : directoryListing) { //looks at every file in the folder
				try {
					i = ImageIO.read(child); //reads the file as an image
				} catch (IOException e) {
				}
				int[] tags=(ImgRec.readAllTags(i)); //gets all tags in a file using imgrec
				//System.out.println(Arrays.toString(tags));
				for(int x : tags){ //iterates through all tags found
					//System.out.println(x);
					for(int j=0;j<InventoryGUI.Sheetdata.get(0).size();j++){ //get all rows in sheet
						if(InventoryGUI.Sheetdata.get(0).get(j).get(1).equals(String.valueOf(x))){ //when it finds the matching tag
							//System.out.println("found");
							InventoryGUI.inventoryFile.write(InventoryGUI.curSheet,j,2,String.valueOf(Integer.parseInt(InventoryGUI.Sheetdata.get(0).get(j).get(2))+num)); //add num to the quantity
						}
					}
				}
				child.delete(); //delete the file
		    }
		  } else {
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }
	}

}
