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

public class TagGUI {
	public static JTextField input;
	public static JLabel output;
	public static JLabel image;
	public static void main(final String[] args){
		JFrame frame=new JFrame("Tags");
		frame.setSize(500,250);
		frame.setBackground(Color.WHITE);
		ImageIcon img = new ImageIcon("Read/logo.png");
		frame.setIconImage(img.getImage());
		frame.setResizable(true);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//dispose();
				try {
					InventoryGUI.main(args);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
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
		JButton wordTags=new JButton("Create Word Document with Tags");
		output=new JLabel(" ");
		image=new JLabel(new ImageIcon(""));
		wordTags.addActionListener(new WordActionListener() {
			
		    public void actionPerformed(ActionEvent e)
		    {
		    	
		    	File dir = new File("Tags");
				  File[] directoryListing = dir.listFiles();
				  //ArrayList<File> images=new ArrayList<File>();
				  if (directoryListing != null) {
				    for (File child : directoryListing) {
				    	if(child.toString().substring(child.toString().length()-4).equals(".png")){
				    		images.add(child);
				    	}
				    	
				    }
				  }
				  
				  Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
					int x = (dim.width)/2;
					int y = (dim.height)/2;
					wordGUI.setSize(x, y);
					wordGUI.setLocationRelativeTo(null);
				  JPanel panel=new JPanel();
				  ArrayList<JPanel> panelList=new ArrayList<JPanel>();
				  //ArrayList<JTextField> in=new ArrayList<JTextField>();
				  for (File g:images){
					  JLabel imageLabel=(new JLabel(new ImageIcon(g.toString())));
					  JLabel label=(new JLabel(g.toString()));
					  JTextField inputs=(new JTextField("0",2));
					  JPanel a=new JPanel();
					  a.add(imageLabel);
					  a.add(label);
					  a.add(inputs);
					  in.add(inputs);
					  panelList.add(a);
				  }
				  panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
				  for (JPanel w:panelList){
					  panel.add(w);
				  }
				  JButton submit=new JButton("Submit");
				  panel.add(submit);
				  submit.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						try {
							WordFile imagesDoc=new WordFile("Tags/Print");
							for (int i=0;i<WordActionListener.in.size();i++){
								int x=Integer.parseInt(WordActionListener.in.get(i).getText());
								for(int j=0;j<x;j++){
									BufferedImage a=null;
									try {
										a = ImageIO.read(WordActionListener.images.get(i));
									} catch (IOException e) {
									}
									String s=WordActionListener.images.get(i).toString();
									imagesDoc.writeImg(s,a.getWidth(),a.getHeight());
								}
							}
							WordActionListener.wordGUI.dispose();
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
				
				  JScrollPane scroll=new JScrollPane(panel);
				  wordGUI.add(scroll);
				  wordGUI.setVisible(true);
		    }
		});
		submit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	String tagName=TagGUI.input.getText();
		    	Tag tagMade = null;
		    	if (Character.isDigit(tagName.charAt(0))){ //currently checks first character; check all chars
		    		tagMade=new Tag(Integer.parseInt(tagName));
		    	}
		    	else{
		    	try {
					tagMade=new Tag(tagName);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}}
		    	try {
					tagMade.makeTag();
					tagMade.writeTag();
					output.setText("Tag saved in file Tags/"+tagMade.getName()+tagMade.getVal()+".png");
					//image=new JLabel(new ImageIcon(tagMade.getName()+tagMade.getVal()+".png"));
					image.setIcon(new ImageIcon("Tags/"+tagMade.getName()+tagMade.getVal()+".png"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
		wordTags.setAlignmentX(Component.CENTER_ALIGNMENT);
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
		panel.add(wordTags);
		panel.add(output);
		panel.add(image);
		frame.add(panel);
		frame.setVisible(true);
	}

}
