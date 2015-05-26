package clientPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;

public class TagGUI {
	public static JTextField input;
	public static JLabel output;
	public static JLabel image;
	public static void main(String[] args){
		JFrame frame=new JFrame("Tags");
		frame.setSize(500,250);
		frame.setBackground(Color.WHITE);
		ImageIcon img = new ImageIcon("lamda.png");
		frame.setIconImage(img.getImage());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		JPanel panel=new JPanel();
		JLabel title=new JLabel("Create or Get Existing Tag");
		JLabel buffer=new JLabel(" ");
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel instruct1=new JLabel("Input the name of the tag or the decimal value of the tag below");
		//JLabel instruct2=new JLabel("decimal value of the tag below");
		instruct1.setAlignmentX(Component.CENTER_ALIGNMENT);
		//instruct2.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel inputPan=new JPanel();
		inputPan.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel inputInfo=new JLabel("Input tag name or number: ");
		input=new JTextField("",20);
		JButton submit=new JButton("Create Tag");
		output=new JLabel(" ");
		image=new JLabel(new ImageIcon(""));
		submit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	String tagName=TagGUI.input.getText();
		    	Tag tagMade = null;
		    	if (Character.isDigit(tagName.charAt(0))){ //currently checks first character; check all chars
		    		tagMade=new Tag(Integer.parseInt(tagName));
		    	}
		    	else{try {
					tagMade=new Tag(tagName);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}}
		    	try {
					tagMade.makeTag();
					tagMade.writeTag();
					output.setText("Tag saved in file "+tagMade.getName()+tagMade.getVal()+".png");
					//image=new JLabel(new ImageIcon(tagMade.getName()+tagMade.getVal()+".png"));
					image.setIcon(new ImageIcon(tagMade.getName()+tagMade.getVal()+".png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		output.setAlignmentX(Component.CENTER_ALIGNMENT);
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(title);
		panel.add(buffer);
		panel.add(instruct1);
		//panel.add(instruct2);
		inputPan.add(inputInfo);
		inputPan.add(input);
		panel.add(inputPan);
		panel.add(submit);
		panel.add(output);
		panel.add(image);
		frame.add(panel);
		frame.setVisible(true);
	}

}
