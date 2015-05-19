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

public class ExcelFile {
	private String name;
	private Workbook workbook;
	private Sheet sheet;
	private ArrayList<ArrayList<String>> data;
	
	public ExcelFile(String n) throws IOException{
		name = n;
		FileInputStream file = new FileInputStream(name);
		workbook = new HSSFWorkbook(file);
		data = new ArrayList<ArrayList<String>>();
	}

	public void createSheet(String n){
		sheet = workbook.createSheet(n);
	}
	
	//Reads all data from Excel file, should only be called once, when the program initially starts	
	public ArrayList<ArrayList<String>> readAll(){
        try {
            
        	int r=0;
            FileInputStream file = new FileInputStream(name);

            HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
            
            //Iterates through the filled cells in the document in row-major order 
            Iterator<Row> rowIterator = sheet.iterator();
            while(rowIterator.hasNext()) {
                Row row = rowIterator.next();
                data.add(new ArrayList<String>());
                 
                //Iterates through the filled cells in each row
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {
                     
                    Cell cell = cellIterator.next();              
                    
                    //Assigns the value in that cell to a 2-D ArrayList
                    //The contents of the ArrayList mirror the layout of the documents contents
                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                        	data.get(r).add("" + cell.getNumericCellValue());
                            break;
                        case Cell.CELL_TYPE_STRING:
                        	data.get(r).add(cell.getStringCellValue());
                            break;
                    }
                }
                r++;
            }
            file.close();
            FileOutputStream out = new FileOutputStream(name);
            workbook.write(out);
            out.close();
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return data;
        
	}
	
	//Updates cells which currently hold a value
	public void writeU(int r, int c, String val){	
		try {
		   FileInputStream file = new FileInputStream(name);
		    
		    HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
		    Cell cell = null;
		 
		    //Finds a cell, only works if that cell already has a value
		    cell = sheet.getRow(r).getCell(c);
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
	
	//Writes to cells which currently do not have a value
	public void writeN(int r, int c, String val){	
		try {
		   FileInputStream file = new FileInputStream(name);
		    
		    HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
		    Cell cell = null;
		 
		    //Creates cell so that it can store a value
		    cell = sheet.createRow(r).createCell(c);
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
	
}
