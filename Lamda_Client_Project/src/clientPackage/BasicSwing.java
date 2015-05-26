package clientPackage;

import javax.swing.JComboBox;
import javax.swing.JFrame; //Largest(besides window)
import javax.swing.JLabel;
import javax.swing.JPanel; //Within JFrame
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BasicSwing extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel p=new JPanel();
	JButton b=new JButton("Hello");
	JTextField t=new JTextField("Hi",20);//one line;editable; text,column
	JTextArea ta=new JTextArea("How\nare\nyou",5,20);//multiple lines;editable; text,row,column
	JLabel l=new JLabel("What's up");
	String choices[]={"Hallo","Bonjour","Konichiwa"};
	JComboBox cb=new JComboBox(choices);
	public static void main(String[] args) {
		new BasicSwing();

	}
	public BasicSwing(){
		super("Basic Swing App");
		
		setSize(400,300); //Wide, high
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE); //properly close
		//Add stuff to p
		p.add(b); //Add button to panel
		p.add(t);
		p.add(ta);
		p.add(l);
		p.add(cb);
		
		add(p); //Add the panel
		
		setVisible(true);
	}

}
