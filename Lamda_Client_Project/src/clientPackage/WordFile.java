package clientPackage;

import java.io.*;
import java.io.FileOutputStream;   

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;   

public class WordFile {
	
	private XWPFDocument doc;
	private String name;
	
    public WordFile(String n) throws IOException{   
          name = n;
          try{
              FileInputStream file = new FileInputStream("C:\\Users\\Andrew\\Desktop\\"+ name + ".docx");
        	  doc = new XWPFDocument(file);
          }catch (FileNotFoundException e){
        	  doc = new XWPFDocument();
          }
          FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Andrew\\Desktop\\"+ name + ".docx"));   
          doc.write(fos);   
          fos.close();   
     }
     public void writeImg(String path) throws InvalidFormatException, IOException{
    	 
    	 FileInputStream file = new FileInputStream("C:\\Users\\Andrew\\Desktop\\"+ name + ".docx");
     
    	 CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File("C:\\Users\\Andrew\\Desktop\\" + name + ".docx")));
    	 FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Andrew\\Desktop\\" + name + ".docx"));

    	 String blipId = document.addPictureData(new FileInputStream(new File(path)), document.PICTURE_TYPE_JPEG);

    	 document.createPicture(blipId,document.getNextPicNameNumber(document.PICTURE_TYPE_JPEG), 500, 500);

    	 document.write(fos);
    	 
    	 file.close();
    	 fos.flush();
    	 fos.close();
     }
     
}
