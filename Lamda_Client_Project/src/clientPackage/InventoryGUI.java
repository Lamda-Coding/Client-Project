import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;


public class FrameClassV5 extends JFrame {
	JTable table;
	public static boolean yesno = false;
	public FrameClassV5() {
		// initializes the frame and two panels 
		JFrame frame = new JFrame();
		JPanel tablePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		// initializations for the JFrame as a whole
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setTitle("VEX Component Catalogue");
//		frame.getContentPane().setBackground(Color.CYAN);
		frame.setResizable(false);
		// Sets the logo of LAMDA Coding in the top left
		ImageIcon logoicon = new ImageIcon("logoReal.png");
		Image logo = logoicon.getImage();
		frame.setIconImage(logo);
		// Sets the dimensions of the frame
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = getSize().width;
		int h = getSize().height;
		int x = (dim.width-w)/2;
		int y = (dim.height-h)/2;
		// Initializes the table containing each of the values
		Object columnNames[] = { "Item", "Quantity", "Check In (+1)", "Check Out (-1)" };
		Object rowData[][] = { { "Item 1", 0, "+", "-" },
		        			   { "Item 2", 0, "+", "-" }, 
							   { "Item 3", 0, "+", "-" } };
	    JTable table = new JTable(rowData, columnNames);
	    // Renders the last two columns as buttons
	    table.getColumn("Check In (+1)").setCellRenderer(new ButtonRenderer5());
	    table.getColumn("Check In (+1)").setCellEditor(new ButtonEditor5(new JCheckBox()));
	    table.getColumn("Check Out (-1)").setCellRenderer(new ButtonRenderer5());
	    table.getColumn("Check Out (-1)").setCellEditor(new ButtonEditor5(new JCheckBox()));
	    JScrollPane scrollPane = new JScrollPane(table);
	    tablePanel.add(scrollPane, BorderLayout.CENTER);	//creates a panel containing the table
	    //creates some button without a current use
	    JButton btnSave = new JButton("Save to File");
	    JButton btnAdd = new JButton("Update from File");
	    buttonPanel.add(btnAdd);
	    buttonPanel.add(btnSave);
	 // adds the panels to the frame and sets it to be visible
		frame.setSize(550, 550);
	    frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
	    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	    //creates a menubar that allows for easy exit
	    JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(item);
        frame.setJMenuBar(menuBar);

        //sets all the elements of the frame to be visible
	    frame.setVisible(true);
		setLocation(x,y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FrameClassV5 frame = new FrameClassV5();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	  }
	
}


//The next several classes render and edit the buttons contained in the tables using the specific mouse click, and record the location of 
//the mouse click relative to the table by storing the row and column of the click.
class ButtonRenderer5 extends JButton implements TableCellRenderer {
	  public ButtonRenderer5() {
	    setOpaque(true);
	  }


	  public Component getTableCellRendererComponent(JTable table, Object value,
	      boolean isSelected, boolean hasFocus, int row, int column) {
		  
	    if (isSelected) {
	      setForeground(table.getSelectionForeground());
	      setBackground(table.getSelectionBackground());
	    } else {
	      setForeground(table.getForeground());
	      setBackground(UIManager.getColor("Button.background"));
	    }
	    setText((value == null) ? "" : value.toString());
	    return this;
	    
	  }
	}
class ButtonEditor5 extends DefaultCellEditor {
	  protected JButton button;
	  private String label;
	  
	  private boolean isPushed;
	  public ButtonEditor5(JCheckBox checkBox) {
	    super(checkBox);
	    button = new JButton();
	    button.setOpaque(true);
	    button.addActionListener(new ActionListener() {
	      public void actionPerformed(ActionEvent e) {
	        fireEditingStopped();
	      }
	    });
	  }

	  public Component getTableCellEditorComponent(JTable table, Object value,
	      boolean isSelected, int row, int column) {
	    if (isSelected) {
	      button.setForeground(table.getSelectionForeground());
	      button.setBackground(table.getSelectionBackground());
	    } else {
	      button.setForeground(table.getForeground());
	      button.setBackground(table.getBackground());
	    }
	    label = (value == null) ? "" : value.toString();
	    button.setText(label);
	    isPushed = true;
	    changeTable(table, row, column);
	    return button;
	  }
	  // This method adds or subtracts 1 from the Quantity depending on whether + or - are clicked
	  public void changeTable(JTable table, int row, int col){
		  if (isPushed){
			  if (col==2){
				  table.setValueAt((Integer)table.getValueAt(row,col-1)+1, row, col-1);
			  }
			  else if (col==3){
				  table.setValueAt((Integer)table.getValueAt(row,col-2)-1, row, col-2);
			  }
			 
		  }

	  }
	  public Object getCellEditorValue() {
	    isPushed = false;
	    return new String(label);
	  }

	  public boolean stopCellEditing() {
	    isPushed = false;
	    return super.stopCellEditing();
	  }

	  protected void fireEditingStopped() {
		  super.fireEditingStopped();
	  }
	}

