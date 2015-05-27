package clientPackage;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtil {
	//http://stackoverflow.com/questions/5694385/getting-the-filenames-of-all-files-in-a-folder
	public static ArrayList<File> readImages(String dir){
		File f = new File(dir);
		ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
		return files;
	}
	
	//https://docs.oracle.com/javase/tutorial/essential/io/delete.html
	// Example of a call: FileUtil.deleteImage(Paths.get("C:\\Users\\Andrew\\Desktop\\Hello.pptx"));
	public static void deleteImage(Path dir){
		try {
		    Files.delete(dir);
		} catch (NoSuchFileException e) {
		    System.err.format("%s: no such" + " file or directory%n", dir);
		} catch (DirectoryNotEmptyException e) {
		    System.err.format("%s not empty%n", dir);
		} catch (IOException e) {
		    // File permission problems are caught here.
		    System.err.println(e);
		}
	}
}
