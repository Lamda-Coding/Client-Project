package clientPackage;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginGUI extends JFrame{
	JPanel panel=new JPanel();
	JLabel info=new JLabel("LAMDΛ Coding");
	JLabel log=new JLabel("Login:");
	JTextField username=new JTextField("Username: ",20);
	JTextField password=new JTextField("Password: ",20);
	JButton submit=new JButton("Submit");
	

	public static void main(String[] args) {
		LoginGUI loginGUI=new LoginGUI("VEX Inventory Application");

	}
	public LoginGUI(String s){
		super(s);
		setSize(1024,768);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setLayout((LayoutManager) new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(info);
		panel.add(log);
		panel.add(username);
		panel.add(password);
		panel.add(submit);
		submit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	setVisible(false);
		    	String user=username.getText();
		    	String pass=password.getText();
		    	JLabel output;
		    	if(user.equals("test1") && pass.equals("securePass")){
		    		output=new JLabel("Success!"); //not showing up?
		    	}
		    	else{
		    		output=new JLabel("Fail!");
		    	}
		    	panel.add(output);
		    	setVisible(true);
		    	
		       //frameToClose.dispose();
		    }
		});
		add(panel);
		setVisible(true);
	}

}
