package clientPackage;
import java.security.*;
import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class LoginGUI extends JFrame{
	JPanel panel=new JPanel();
	JLabel info=new JLabel(" LAMDΛ Coding");
	JLabel log=new JLabel("Login:");
	JLabel userlabel=new JLabel("Username:");
	JTextField username=new JTextField("",0);
	JLabel passlabel=new JLabel("Password:");
	JPasswordField password=new JPasswordField("",0);
	JButton submit=new JButton("Submit");
	JLabel output=new JLabel(" ");
	ImageIcon img = new ImageIcon("lamda.png");
	JLabel spacing1=new JLabel(" ");
	JLabel spacing2=new JLabel(" ");
	JLabel spacing3=new JLabel(" ");
	JLabel spacing4=new JLabel(" ");
	private String md5Hash(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		byte[] bytesOfMessage = s.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		return Arrays.toString(thedigest);
	}
	

	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		LoginGUI loginGUI=new LoginGUI("VEX Inventory Application");

	}
	public LoginGUI(String s){
		
		super(s);
		setSize(300,225);
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
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		Box userBox = new Box(BoxLayout.LINE_AXIS);
		Box passBox = new Box(BoxLayout.LINE_AXIS);
		Box infoBox=new Box(BoxLayout.LINE_AXIS);
		infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		infoBox.add(new JLabel(new ImageIcon("FullLogo.png")));
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
		panel.add(output);
		submit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	//setVisible(false);
		    	String user=username.getText();
		    	String pass=password.getText();
		    	try {
					if(md5Hash(user).equals("[20, -60, -80, 107, -126, 78, -59, -109, 35, -109, 98, 81, 127, 83, -117, 41]")&&md5Hash(pass).equals("[95, 77, -52, 59, 90, -89, 101, -42, 29, -125, 39, -34, -72, -126, -49, -103]")){
						//username=username;password=password
						dispose();
						//run next part of program
					}
					else{
			    		output.setText("Incorrect username and/or password");
			    		if(output.getForeground().equals(Color.RED)){
			    			output.setForeground(Color.BLACK);
			    		}else{
			    			output.setForeground(Color.RED);
			    		}
			    	}
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoSuchAlgorithmException e1) {
					// TODO Auto-generated catch block
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
		});
		add(panel);
		setVisible(true);
	}

}
