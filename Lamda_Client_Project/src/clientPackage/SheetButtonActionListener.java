package clientPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;

public class SheetButtonActionListener implements ActionListener {
	public int snum;
	public JButton b;

	public SheetButtonActionListener(JButton a){
		b=a;
		String p=(b.getText());
		snum=Integer.parseInt(p.substring(p.length()-1));
		
	}
	public int getnum(){
		return snum;
		
	}
	public void actionPerformed(ActionEvent e) {
		
		// TODO Auto-generated method stub
		
	}

}
