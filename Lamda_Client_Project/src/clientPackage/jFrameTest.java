package clientPackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/* FrameDemo.java requires no other files. */
public class jFrameTest {
	public static void main(String s[]) {
		JFrame frame = new JFrame("JFrame Source Demo");
		// Add a window listener for close button
		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		// This is an empty content area in the frame
		JLabel jlbempty = new JLabel("LAMDΛ Coding");
		jlbempty.setPreferredSize(new Dimension(25, 25));
		frame.getContentPane().add(jlbempty, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
	}
}