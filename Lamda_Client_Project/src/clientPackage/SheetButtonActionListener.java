package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
//action listener used for sheet buttons
public class SheetButtonActionListener implements ActionListener {
	private int snum; //public fields

	public SheetButtonActionListener(int i){ //constructor with integer representing sheet number
		snum=i;
		
	}
	public int getnum(){ //getter method for number
		return snum;
		
	}
	public void actionPerformed(ActionEvent e) { //to be overridden
		
		
	}

}
