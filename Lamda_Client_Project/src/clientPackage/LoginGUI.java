package clientPackage;
import java.security.*;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings({ "serial", "unused" })
public class LoginGUI extends JFrame{ //GUI for authentication
	//Creates components
	JPanel panel=new JPanel();
	JLabel info=new JLabel(" LAMDΛ Coding");
	JLabel log=new JLabel("Login:");
	JLabel userlabel=new JLabel("Username:");
	JTextField username=new JTextField("",0);
	JLabel passlabel=new JLabel("Password:");
	JPasswordField password=new JPasswordField("",0);
	JButton submit=new JButton("Submit");
	JButton change=new JButton("Change Password");
	JLabel output=new JLabel(" ");
	ImageIcon img = new ImageIcon("Read/logo.png"); //icon
	JLabel spacing1=new JLabel(" ");
	JLabel spacing2=new JLabel(" ");
	JLabel spacing3=new JLabel(" ");
	JLabel spacing4=new JLabel(" ");
	private static String md5Hash(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException{ //makes md5 hash of a string
		byte[] bytesOfMessage = s.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		return Arrays.toString(thedigest);
	}
	private static void changePass(){ //change password method
		//makes new frame for changing password
		JPanel passpanel=new JPanel();
		final JFrame passFrame=new JFrame("Change Password");
		JLabel info=new JLabel("Change Password");
		JLabel pass1=new JLabel("Input new password: ");
		JLabel pass2=new JLabel("Confirm password: ");
		JButton submit=new JButton("Submit");
		final JLabel error=new JLabel(" ");
		final JPasswordField pass1F=new JPasswordField("",0);
		final JPasswordField pass2F=new JPasswordField("",0);
		//Formatting/aesthetics
		passFrame.setSize(300,150);
		passFrame.setBackground(Color.WHITE);
		passFrame.setResizable(true);
		passFrame.setLocationRelativeTo(null);
		passFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		passpanel.setLayout((LayoutManager) new BoxLayout(passpanel, BoxLayout.Y_AXIS));
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		Box box1=new Box(BoxLayout.LINE_AXIS);
		Box box2=new Box(BoxLayout.LINE_AXIS);
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		box1.setAlignmentX(Component.CENTER_ALIGNMENT);
		//add components to the gui
		box1.add(pass1);
		box1.add(pass1F);
		box2.setAlignmentX(Component.CENTER_ALIGNMENT);
		box2.add(pass2);
		box2.add(pass2F);
		passpanel.add(info);
		passpanel.add(box1);
		passpanel.add(box2);
		passpanel.add(submit);
		passpanel.add(error);
		submit.addActionListener(new ActionListener() { //add action listener to button so it does stuff
		    @SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) //what button does on click
		    {
		    	String one=pass1F.getText(); //get text from entries
		    	String two=pass2F.getText();
		    	if (one.equals(two)){ //if the passwords match
		    		try {
						repLine("Read/password.txt",md5Hash(one)); //replace the hash in password.txt
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						e1.printStackTrace();
					}
		    		passFrame.dispose();
		    	}
		    	else{
		    		error.setText("Passwords do not match"); //if the passwords do not match, tell the user
		    		//change color of error message
		    		if(error.getForeground().equals(Color.RED)){
		    			error.setForeground(Color.BLACK);
		    		}else{
		    			error.setForeground(Color.RED);
		    		}
		    	}
		    }
		    public void repLine(String file, String s) throws IOException { //replaces line in file with string s
		    	PrintWriter a =new PrintWriter(file,"UTF-8"); //clears file
		    	a.close();
				FileWriter t = new FileWriter(file, true); //reopens file
				BufferedWriter bufferWritter = new BufferedWriter(t);
				bufferWritter.write(s + "\n"); //writes to file
				bufferWritter.close();
			}
		    	}
		    );
		passFrame.add(passpanel);
		passFrame.setVisible(true); //show GUI
		
	}
	

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException { //main method
		LoginGUI loginGUI=new LoginGUI("VEX Inventory Application",args); //start gui

	}
	public LoginGUI(String s,final String[] args){ //main gui
		
		super(s); //inheritss JFrame constructor for argument of string
		//Aesthetics
		setSize(300,250);
		setBackground(Color.WHITE);
		setIconImage(img.getImage());
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		//info.setAlignmxentX(150);
		log.setAlignmentX(Component.CENTER_ALIGNMENT);
		//log.setAlignmentX(150);
		output.setAlignmentX(Component.CENTER_ALIGNMENT);
		change.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		Box userBox = new Box(BoxLayout.LINE_AXIS);
		Box passBox = new Box(BoxLayout.LINE_AXIS);
		Box infoBox=new Box(BoxLayout.LINE_AXIS);
		infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		//add components
		infoBox.add(new JLabel(new ImageIcon("Read/FullLogo.png")));
		//infoBox.add(info);
		panel.add(infoBox);
		panel.add(log);
		userBox.add(spacing1);
		userBox.add(userlabel);
		userBox.add(username);
		userBox.add(spacing2);
		panel.add(userBox);
		passBox.add(spacing3);
		passBox.add(passlabel);
		passBox.add(password);
		passBox.add(spacing4);
		panel.add(passBox);
		panel.add(submit);
		panel.add(change);
		panel.add(output);
		change.addActionListener(new ActionListener() { //action listener for change password button
		    public void actionPerformed(ActionEvent e) //what to do on click
		    {
		    	//setVisible(false);
		    	String user=username.getText(); //get username text
		    	@SuppressWarnings("deprecation")
				String pass=password.getText(); //get password text
		    	String passHash="";
		    	try {
					passHash=getLine("Read/password.txt", 0); //get hash from password.txt
				} catch (IOException e2) {
					e2.printStackTrace();
				}
		    	try {
					if(md5Hash(user).equals("[33, 35, 47, 41, 122, 87, -91, -89, 67, -119, 74, 14, 74, -128, 31, -61]")&&md5Hash(pass).equals(passHash)){
						//username=admin;password=password in password.txt
						dispose(); //close the current gui
						changePass(); //run the changePass gui
					}
					else{
			    		output.setText("Incorrect username and/or password"); //incorrect username and/or password
			    		//flash error message
			    		if(output.getForeground().equals(Color.RED)){
			    			output.setForeground(Color.BLACK);
			    		}else{
			    			output.setForeground(Color.RED);
			    		}
			    	}
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
		    	/*if(user.equals("test1") && pass.equals("securePass")){ 
		    		//username:[20, -60, -80, 107, -126, 78, -59, -109, 35, -109, 98, 81, 127, 83, -117, 41]
		    		//password:[95, 77, -52, 59, 90, -89, 101, -42, 29, -125, 39, -34, -72, -126, -49, -103]
		    		//Lets go to a new screen!
		    		dispose();
		    	}
		    	else{
		    		output.setText("Incorrect username and/or password");
		    	}*/
		    	//setVisible(true);
		    	
		       //frameToClose.dispose();
		    }
		    public int lines(String file) throws FileNotFoundException { //get number of lines in a file
				Scanner sc = new Scanner(new File(file));
				int count = 0;
				while (sc.hasNextLine()) { //checks if another line
					count++; //adds to count
					sc.nextLine();
				}
				sc.close();
				return count;
			}
		    public String getLine(String file, int l) throws IOException { //get line at line number l in file
				l += 1;
				FileInputStream fs = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs)); //open file
				for (int i = 0; i < l - 1; ++i) { //iterate through lines until it reaches ;
					if (!(lines(file) <= i)) {
						br.readLine();
					} else {
						br.close();
						return "";
					}
				}
				String temp = br.readLine(); //reads the line at l
				br.close();
				return temp;
			}

		});
		submit.addActionListener(new ActionListener() { //add action listener to submit button
		    public void actionPerformed(ActionEvent e) //action when button clicked
		    {
		    	//setVisible(false);
		    	String user=username.getText(); //get username entry
		    	@SuppressWarnings("deprecation")
				String pass=password.getText(); //get password entry
		    	String passHash="";
		    	try {
					passHash=getLine("Read/password.txt", 0); //get hash from password.txt
				} catch (IOException e2) {
					e2.printStackTrace();
				}
		    	try {
					if(md5Hash(user).equals("[33, 35, 47, 41, 122, 87, -91, -89, 67, -119, 74, 14, 74, -128, 31, -61]")&&md5Hash(pass).equals(passHash)){
						//username=admin;password=hash in password.txt
						dispose(); //close gui
						InventoryGUI.main(args);
						//run next part of program
					}
					else{
						//incorrect username and password
			    		output.setText("Incorrect username and/or password");
			    		//flash error message color
			    		if(output.getForeground().equals(Color.RED)){
			    			output.setForeground(Color.BLACK);
			    		}else{
			    			output.setForeground(Color.RED);
			    		}
			    	}
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}
		    	
 catch (IOException e1) {
					e1.printStackTrace();
				}
		    	
		       //frameToClose.dispose();
		    }
		    public int lines(String file) throws FileNotFoundException { //get number of lines in file
				Scanner sc = new Scanner(new File(file));
				int count = 0;
				while (sc.hasNextLine()) { //if there is another line
					count++; //add to count
					sc.nextLine();
				}
				sc.close();
				return count;
			}
		    public String getLine(String file, int l) throws IOException { //get line l in file
				l += 1;
				FileInputStream fs = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fs)); //open file
				for (int i = 0; i < l - 1; ++i) {
					if (!(lines(file) <= i)) {
						br.readLine(); //read lines upto l
					} else {
						br.close();
						return "";
					}
				}
				String temp = br.readLine(); //read line l
				br.close();
				return temp;
			}

		});
		add(panel);
		setVisible(true); //make GUI visible
	}

}
