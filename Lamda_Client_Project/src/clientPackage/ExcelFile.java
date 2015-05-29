package clientPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("unused")
public class ExcelFile {
	private static final Exception SheetExists = new Exception("Sheet Exists");
	private String name;
	private Workbook workbook;
	private ArrayList<ArrayList<ArrayList<String>>> data;
	
	/*When the constructor is called it opens the specified excel document
	*If one does not exist then, one is created under "name"
	*The path for where the file is located is contained in name
	*A 3D arrayList is created. This is where data will be stored from excel
	*/
	public ExcelFile(String n) throws IOException{
		name = n;
		try{
			//Creating a file input stream allows for a file to be updated rather than overwritten
			FileInputStream file = new FileInputStream(name);
			workbook = new HSSFWorkbook(file);
		}catch (FileNotFoundException e){
			workbook = new HSSFWorkbook();
		}
		data = new ArrayList<ArrayList<ArrayList<String>>>();
		//Creating a file output stream allows for a file to be written to
    		FileOutputStream out = new FileOutputStream(name);
    		workbook.write(out);
    		out.close();
	}
	
	/* This tries to create a new sheet in the excel file
	* If that she already exists then the error is caught.
	*/
	public void createSheet(String n) throws Exception{
		try{
			workbook.createSheet(n);
		} catch (java.lang.IllegalArgumentException e){
			throw SheetExists;
			//System.out.println("Sheet already exists");
		}
	
	/* Getter method for the sheet name at a certain position
	*/
	}
	public String getSName(int i){
		return(workbook.getSheetName(i));
	}
	
	/* reads all of the data in an excel file and stores it in a 3d array list
	* the method iterates through every column, in every row, in each sheet
	* Code inspired from the below link
	* http://viralpatel.net/blogs/java-read-write-excel-file-apache-poi/
	*/
	public ArrayList<ArrayList<ArrayList<String>>> readAll(){
        try {
            
        	int r=0;
            FileInputStream file = new FileInputStream(name);
            
            for(int i = 0; i < workbook.getNumberOfSheets(); i++){

            	HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(i);
            	data.add(new ArrayList<ArrayList<String>>());
            	r = 0;
            
            	//Iterates through the filled cells in the document in row-major order 
            	Iterator<Row> rowIterator = sheet.iterator();
            	while(rowIterator.hasNext()) {
            		Row row = rowIterator.next();
            		data.get(i).add(new ArrayList<String>());
                 
            		//Iterates through the filled cells in each row
            		Iterator<Cell> cellIterator = row.cellIterator();
            		while(cellIterator.hasNext()) {
                     
            			Cell cell = cellIterator.next();              
                    
            			//Assigns the value in that cell to a 3-D ArrayList
            			//The contents of the ArrayList mirror the layout of the documents contents
            			switch(cell.getCellType()) {
            				case Cell.CELL_TYPE_NUMERIC:
            					data.get(i).get(r).add("" + (int)(cell.getNumericCellValue()));
            					break;
            				case Cell.CELL_TYPE_STRING:
            					data.get(i).get(r).add(cell.getStringCellValue());
            					break;
            			}
            		}
            		r++;
            	}
            	file.close();
            	FileOutputStream out = new FileOutputStream(name);
            	workbook.write(out);
            	out.close();
            }
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return data;
        
	}
	
	//Writes data from the java application to the Excel file so it can be saved
	public void write(int s, int r, int c, String val){	
		try {
		   FileInputStream file = new FileInputStream(name);
		    
		    HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(s);
		    
		    Row row =  sheet.getRow(r);
		    
	    /*Allows cells which already have values to be updated
	    * Those that do not have values created and given values
	    */
            if (row == null){
                row = sheet.createRow(r);
            }
            Cell cell = row.getCell(c);
            if (cell == null){
                cell = row.createCell(c);
            }
            cell.setCellValue(val);
		    
	    	file.close();
	     
	    	FileOutputStream outFile = new FileOutputStream(name);
	    	workbook.write(outFile);
	    	outFile.close();
		     
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}
		
	//This funciton deletes an entire row from the Excel File
	public void deleteRow(int s, int r){
		
		try{
			
			FileInputStream file  =   new FileInputStream(name);
			HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(s);
			
			sheet.removeRow(sheet.getRow(r));
			
			file.close();
	     
			FileOutputStream outFile = new FileOutputStream(name);
			workbook.write(outFile);
			outFile.close();
    	
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
       
	}
	
	//Getter method for the number of sheets in the Excel File
	public int getNumOfSheets(){
		return workbook.getNumberOfSheets();
	}
	
}
