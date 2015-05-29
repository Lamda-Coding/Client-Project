package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class WordActionListener implements ActionListener{ //special action listener for word file
	public static ArrayList<File> images=new ArrayList<File>(); //public static fields
	public static ArrayList<JTextField> in=new ArrayList<JTextField>();
	public static JFrame wordGUI=new JFrame("Generate Word Doc");
	@Override
	public void actionPerformed(ActionEvent arg0) { //to be overwritten
		// TODO Auto-generated method stub
		
	}

}
