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
	private String name;
	private Workbook workbook;
	private ArrayList<ArrayList<ArrayList<String>>> data;
	
	public ExcelFile(String n) throws IOException{
		name = n;
		FileInputStream file = new FileInputStream(name);
		workbook = new HSSFWorkbook(file);
		data = new ArrayList<ArrayList<ArrayList<String>>>();
	}

	public void createSheet(String n){
		try{
			workbook.createSheet(n);
		} catch (java.lang.IllegalArgumentException e){
			System.out.println("Sheet already exists");
		}
	}
		
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
	
	public String read(int s, int r, int c){
		return null;
	}
	
	//Writes data from the java application to the Excel file so it can be saved
	public void write(int s, int r, int c, String val){	
		try {
		   FileInputStream file = new FileInputStream(name);
		    
		    HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(s);
		    Cell cell = null;
		 
		    //Finds a cell, only works if that cell already has a value
		    try{
		    	cell = sheet.getRow(r).getCell(c);
		    } catch (NullPointerException e){
		    	try{
		    	cell = sheet.createRow(r).createCell(c);
		    	} catch (NullPointerException e1){
		    		cell = sheet.getRow(r).createCell(c);
		    	}
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
	
	public int getNumOfSheets(){
		return workbook.getNumberOfSheets();
	}
	
}
