package clientPackage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Iterate {
	public static void iter(String myDirectoryPath, int num){
		File dir = new File(myDirectoryPath);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
			  BufferedImage i = null;
		    for (File child : directoryListing) {
				try {
					i = ImageIO.read(child);
				} catch (IOException e) {
				}
				int[] tags=(ImgRec.readAllTags(i));
				//System.out.println(Arrays.toString(tags));
				for(int x : tags){
					//System.out.println(x);
					for(int j=0;j<InventoryGUI.Sheetdata.get(0).size();j++){ //get all rows in sheet
						if(InventoryGUI.Sheetdata.get(0).get(j).get(1).equals(String.valueOf(x))){
							System.out.println("found");
							InventoryGUI.inventoryFile.write(InventoryGUI.curSheet,j,2,String.valueOf(Integer.parseInt(InventoryGUI.Sheetdata.get(0).get(j).get(1))+num));
						}
					}
				}
				child.delete();
		      // Do something with child
		    }
		  } else {
		    // Handle the case where dir is not really a directory.
		    // Checking dir.isDirectory() above would not be sufficient
		    // to avoid race conditions with another process that deletes
		    // directories.
		  }
	}

}
