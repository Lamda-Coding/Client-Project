package clientPackage;

import java.io.*;
import java.io.FileOutputStream;   

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;   


public class WordFile {
	
	private XWPFDocument doc;
	private String name;

    /* This creates a Word document in the docx format or opens one if it already exits
    * name is just the name of the file with no extension
    */
    public WordFile(String n) throws IOException{   
          name = n;
          try{
              //Creating an input stream allows a file to be updated rather than overwritten
              FileInputStream file = new FileInputStream(name + ".docx");
        	  doc = new XWPFDocument(file);
          }catch (FileNotFoundException e){
        	  doc = new XWPFDocument();
          }
          //Creating an output stream allows a file to be written to
          FileOutputStream fos = new FileOutputStream(new File(name + ".docx"));   
          doc.write(fos);   
          fos.close();   
     }
     
     /*This function takes an image and puts into the created word document with the specified size
     * path is the actual path to the image, including the name of the image
     * This function only works with JPEG images
     */
     public void writeImg(String path, int l, int w) throws InvalidFormatException, IOException{
    	 
    	 FileInputStream file = new FileInputStream(name + ".docx");
     
    	 CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File(name + ".docx")));
    	 FileOutputStream fos = new FileOutputStream(new File(name + ".docx"));

    	 String blipId = document.addPictureData(new FileInputStream(new File(path)), document.PICTURE_TYPE_JPEG);

    	 document.createPicture(blipId,document.getNextPicNameNumber(document.PICTURE_TYPE_JPEG), l, w);

    	 document.write(fos);
    	 
    	 file.close();
    	 fos.flush();
    	 fos.close();
     }
     
}
