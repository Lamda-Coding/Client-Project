package clientPackage;
import java.security.*;
import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginGUI extends JFrame{
	JPanel panel=new JPanel();
	JLabel info=new JLabel("LAMDΛ Coding");
	JLabel log=new JLabel("Login:");
	JTextField username=new JTextField("Username: ",0);
	JPasswordField password=new JPasswordField("Password: ",0);
	JButton submit=new JButton("Submit");
	JLabel output=new JLabel("");
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
		setSize(300,200);
		//setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		info.setAlignmentX(Component.CENTER_ALIGNMENT);
		//info.setAlignmxentX(150);
		log.setAlignmentX(Component.CENTER_ALIGNMENT);
		//log.setAlignmentX(150);
		output.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(info);
		panel.add(log);
		panel.add(username);
		panel.add(password);
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
