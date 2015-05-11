package clientPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Tags {
	private boolean[] valBin=new boolean[8];//0-511
	private int val; //Decimal value
	private String name;
	private void convBin(int val){
		if(val>Math.pow(2, 8)){
			System.out.println("Error");
		}
		else{
			for(int i=7;i>=0;i--){
				if (val>Math.pow(2, i)){
					valBin[8-i-1]=true;
				}
			}
		}
	}
	private static ArrayList<Integer> vals=new ArrayList<Integer>();
	//returns amount of lines in the file
		public static int lines(String file) throws FileNotFoundException {
			Scanner sc = new Scanner(new File(file));
			int count = 0;
			while (sc.hasNextLine()) {
				count++;
				sc.nextLine();
			}
			sc.close();
			return count;
		}

		//returns specified line
		public static String getLine(String file, int l) throws IOException {
			FileInputStream fs = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fs));
			for (int i = 0; i < l - 1; ++i) {
				if (!(lines(file) <= i)) {
					br.readLine();
				} else {
					br.close();
					return "";
				}
			}
			String temp = br.readLine();
			br.close();
			return temp;
		}

		//writes line to end of file
		public static void addLine(String file, String s) throws IOException {
			FileWriter t = new FileWriter(file, true);
			BufferedWriter bufferWritter = new BufferedWriter(t);
			bufferWritter.write(s + "\n");
			bufferWritter.close();
		}

/*	private String findLastTag(){
		try {
			return getLine("tags.txt",lines("tags.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}*/
	public Tags(String n){
		val=-1;
		try {
			for(int i=0;i<lines("tags.txt");i++){
				if (getLine("tags.txt",i).equals(n)){
					val=i;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (val==-1){
				val=lines("tags.txt")+1;
				try {
					addLine("tags.txt",n);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name=n;
		//write to file
		
	}
	public Tags(int v){ //find tag for value at v
		val=v;
		try {
			name=getLine("tags.txt",v);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
