package clientPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class TagGUI { //GUI for creating and getting tags
	//public fields
	public static JTextField input;
	public static JLabel output;
	public static JLabel image;
	public static void main(final String[] args){ //main method
		//create GUI with aesthetics
		JFrame frame=new JFrame("Tags");
		frame.setSize(500,250);
		frame.setBackground(Color.WHITE);
		ImageIcon img = new ImageIcon("Read/logo.png");
		frame.setIconImage(img.getImage());
		frame.setResizable(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){ //tell what to do on close
			public void windowClosing(WindowEvent e){
				//dispose();
				try {
					InventoryGUI.main(args); //rerun inventorygui
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		frame.setLocationRelativeTo(null);
		//make components
		JPanel panel=new JPanel();
		JLabel title=new JLabel("Create or Get Existing Tag");
		JLabel buffer=new JLabel(" ");
		//aesthetics
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
		JButton wordTags=new JButton("Create Word Document with Tags");
		output=new JLabel(" ");
		image=new JLabel(new ImageIcon(""));
		wordTags.addActionListener(new WordActionListener() { //add action listener
			
		    public void actionPerformed(ActionEvent e) //when clicked
		    {
		    	
		    	File dir = new File("Tags"); //look in Tags directory
				  File[] directoryListing = dir.listFiles();
				  //ArrayList<File> images=new ArrayList<File>();
				  if (directoryListing != null) {
				    for (File child : directoryListing) { //look at all file in directory
				    	if(child.toString().substring(child.toString().length()-4).equals(".png")){ //only look at .png
				    		images.add(child); //add file to list
				    	}
				    	
				    }
				  }
				  
				  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //get dimensions to set size of gui
					int x = (dim.width)/2;
					int y = (dim.height)/2;
					wordGUI.setSize(x, y);
					wordGUI.setLocationRelativeTo(null);
				  JPanel panel=new JPanel();
				  ArrayList<JPanel> panelList=new ArrayList<JPanel>(); //list of panels for each tag, label and entry
				  //ArrayList<JTextField> in=new ArrayList<JTextField>();
				  for (File g:images){ //look at all image files
					  JLabel imageLabel=(new JLabel(new ImageIcon(g.toString()))); //make label for image
					  JLabel label=(new JLabel(g.toString())); //make text label
					  JTextField inputs=(new JTextField("0",2)); //make entry field
					  JPanel a=new JPanel();
					  //add components
					  a.add(imageLabel);
					  a.add(label);
					  a.add(inputs);
					  in.add(inputs); //add input to list
					  panelList.add(a); //add panel to list
				  }
				  panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
				  for (JPanel w:panelList){
					  panel.add(w); //add all tag panels
				  }
				  JButton submit=new JButton("Submit"); //make submit button
				  panel.add(submit); //add submit button
				  submit.addActionListener(new ActionListener(){ //action listener for submit button

					@Override
					public void actionPerformed(ActionEvent arg0) { //when button clicked
						try {
							WordFile imagesDoc=new WordFile("Tags/Print"); //make or get word doc
							for (int i=0;i<WordActionListener.in.size();i++){ //for all entries
								int x=Integer.parseInt(WordActionListener.in.get(i).getText()); //get the number from the entry
								for(int j=0;j<x;j++){ //loops as many times as number entered
									BufferedImage a=null;
									try {
										a = ImageIO.read(WordActionListener.images.get(i)); //read file as image
									} catch (IOException e) {
									}
									String s=WordActionListener.images.get(i).toString(); //file name of image
									imagesDoc.writeImg(s,a.getWidth(),a.getHeight()); //write to doc
								}
							}
							WordActionListener.wordGUI.dispose(); //close gui
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// TODO Auto-generated method stub
 catch (InvalidFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					  
				  });
				
				  JScrollPane scroll=new JScrollPane(panel); //add scrolling to gui
				  wordGUI.add(scroll);
				  wordGUI.setVisible(true); //show gui
		    }
		});
		submit.addActionListener(new ActionListener() { //action listener for submit button
		    public void actionPerformed(ActionEvent e) //when button clicked
		    {
		    	String tagName=TagGUI.input.getText(); //get text from input field
		    	Tag tagMade = null;
		    	if (Character.isDigit(tagName.charAt(0))){ //checks if first char is number
		    		tagMade=new Tag(Integer.parseInt(tagName)); //make tag with integer constructor
		    	}
		    	else{
		    	try {
					tagMade=new Tag(tagName); //make tag with string constructor
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}}
		    	try {
					tagMade.makeTag(); //make the tag
					tagMade.writeTag(); //write the tag to a file
					output.setText("Tag saved in file Tags/"+tagMade.getName()+tagMade.getVal()+".png"); //tell where file saved
					//image=new JLabel(new ImageIcon(tagMade.getName()+tagMade.getVal()+".png"));
					image.setIcon(new ImageIcon("Tags/"+tagMade.getName()+tagMade.getVal()+".png")); //show tag image
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		//aesthetic
		wordTags.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		output.setAlignmentX(Component.CENTER_ALIGNMENT);
		image.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		//add components
		panel.add(title);
		panel.add(buffer);
		panel.add(instruct1);
		//panel.add(instruct2);
		inputPan.add(inputInfo);
		inputPan.add(input);
		panel.add(inputPan);
		panel.add(submit);
		panel.add(wordTags);
		panel.add(output);
		panel.add(image);
		frame.add(panel);
		frame.setVisible(true); //show gui
	}

}
