package clientPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.WorkbookUtil;

import java.io.FileOutputStream;

public class File {
	private String name;
	public Workbook workbook;
	public Sheet sheet;
	
	
	public File(String n){
		name = n;
		workbook = new HSSFWorkbook();
		try{
			FileOutputStream output = new FileOutputStream(name);
			workbook.write(output);
			output.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void createSheet(String n){
		sheet = workbook.createSheet(n);
	}
	
	public void read(int r, int c){
		Cell cell = sheet.createRow(r).createCell(c);
		System.out.println(cell.getRichStringCellValue().toString());
		
		try{
			FileOutputStream output = new FileOutputStream(name);
			workbook.write(output);
			output.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(int r, int c, String val){	
		Cell cell = sheet.createRow(r).createCell(c);
		cell.setCellValue(val);

		try{
			FileOutputStream output = new FileOutputStream(name);
			workbook.write(output);
			output.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
