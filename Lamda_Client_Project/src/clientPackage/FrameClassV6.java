package clientPackage;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;


public class FrameClassV6 extends JFrame {
	JTable table;
	public static boolean yesno = false;
	public FrameClassV6() throws IOException {
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
		ExcelFile f = new ExcelFile("Inventory.xls");
		ArrayList<ArrayList<ArrayList<String>>> Sheetdata = f.readAll();
		ArrayList<ArrayList<String>> data=Sheetdata.get(0);
		System.out.println(data);
		Object columnNames[] = new Object[data.get(0).size()+2];
		for (int i = 0; i<data.get(0).size(); i++){
			columnNames[i] = data.get(0).get(i);
		}
		columnNames[data.get(0).size()] = "Check In (+1)";
		columnNames[data.get(0).size()+1] = "Check Out (-1)";
		
		Object rowData[][] = new Object[data.get(0).size()+2][data.size()];
		
		for (int i = 0; i<data.size(); i++){
			for (int j = 0; j<data.get(0).size(); j++){
				rowData[i][j] = data.get(0).get(i);
			}
		}
		for (int i = data.get(0).size(); i<rowData.length;i++){
			
		}
//		Object rowData[][] = { { "Item 1", 0, 128, "+", "-" },
//		        			   { "Item 1", 0, 128, "+", "-" }, 
//							   { "Item 1", 0, 128, "+", "-"  } };
	    JTable table = new JTable(rowData, columnNames);
	    // Renders the last two columns as buttons
	    table.getColumn("Check In (+1)").setCellRenderer(new ButtonRenderer6());
	    table.getColumn("Check In (+1)").setCellEditor(new ButtonEditor6(new JCheckBox()));
	    table.getColumn("Check Out (-1)").setCellRenderer(new ButtonRenderer6());
	    table.getColumn("Check Out (-1)").setCellEditor(new ButtonEditor6(new JCheckBox()));
	    JScrollPane scrollPane = new JScrollPane(table);
	    tablePanel.add(scrollPane, BorderLayout.CENTER);	//creates a panel containing the table
	    //creates some button without a current use
	    JButton btnSave = new JButton("Save to File");
	    JButton btnAdd = new JButton("Update from File");
	    buttonPanel.add(btnAdd);
	    buttonPanel.add(btnSave);
	 // adds the panels to the frame and sets it to be visible
		frame.setSize(660, 660);
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
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		FrameClassV6 frame = new FrameClassV6();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	  }
	
}


//The next several classes render and edit the buttons contained in the tables using the specific mouse click, and record the location of 
//the mouse click relative to the table by storing the row and column of the click.
class ButtonRenderer6 extends JButton implements TableCellRenderer {
	  public ButtonRenderer6() {
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
class ButtonEditor6 extends DefaultCellEditor {
	  protected JButton button;
	  private String label;
	  
	  private boolean isPushed;
	  public ButtonEditor6(JCheckBox checkBox) {
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
