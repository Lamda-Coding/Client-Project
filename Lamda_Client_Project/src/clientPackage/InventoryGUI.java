package clientPackage;
// Initialization of packages needed in the implementation of the GUI
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.*;


public class InventoryGUI extends JFrame {
	// Initialization of variables required for use later in the program
	private static final long serialVersionUID = 1L;
	JTable table;
	JPanel tablePanel;
	ArrayList<ArrayList<String>> data;
	static ArrayList<ArrayList<ArrayList<String>>> Sheetdata;
	public static int curSheet;
	public static JFrame frame;
	public static ExcelFile inventoryFile;
	protected static String[] args;
	public void setData(ArrayList<ArrayList<String>> dataA){
		data=dataA;
	}
	public ArrayList<ArrayList<String>> getSheet(int x){
		return Sheetdata.get(x-1);
	}
	public InventoryGUI() throws IOException {
		// initializes the frame and two panels 
		inventoryFile= new ExcelFile("Inventory.xls");
		frame = new JFrame();
		tablePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel sheetPanel=new JPanel();
		ArrayList<JButton> sheetButtons= new ArrayList<JButton>();
		curSheet=0;
		// initializations for the JFrame as a whole
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//frame.setVisible(true);
		frame.setTitle("VEX Component Catalogue");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setSize(465, 500);
		frame.setLocationRelativeTo(null);
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
		Sheetdata = inventoryFile.readAll();
		data=Sheetdata.get(curSheet);
		//System.out.println(data);
		for(int i=0;i<Sheetdata.size();i++){
			sheetButtons.add(new JButton("Sheet "+(i+1)));
			sheetButtons.get(i).addActionListener(new SheetButtonActionListener(sheetButtons.get(i)){
				@Override
	            public void actionPerformed(ActionEvent e) {
					setData(getSheet(this.getnum()));
					curSheet=this.getnum()-1;
					for (int i = 0; i<data.get(0).size(); i++){
						//set headers...now need to update cells
						table.getTableHeader().getColumnModel().getColumn(i).setHeaderValue(data.get(0).get(i));
						for (int j=1;j<data.size();j++){
							//System.out.println(data.get(j).get(i));
							//System.out.println(data.get(i).toString());
							//System.out.println(data.get(j).toString());
							if (Character.isDigit((data.get(j).get(i).charAt(0)))){ //currently checks first; check all
							// sets the value at a cell of the JTable to the corresponding excel cell as an integer 
							// if it is found to be a number.
								table.setValueAt(Integer.parseInt(data.get(j).get(i)), j-1, i);
							}
							//sets the value to the JTable cell without casting it to an integer.
							else{
								table.setValueAt((data.get(j).get(i)), j-1, i);
								}
						}
					}
					table.repaint();
			        tablePanel.setVisible(false);
			        tablePanel.setVisible(true);
					
	            }
			});
			sheetPanel.add(sheetButtons.get(i));
		}
		// Stores the first row in the Excel file which lists the column names in the array list columnNames 
		Object columnNames[] = new Object[data.get(0).size()+2];
		for (int i = 0; i<data.get(0).size(); i++){
			columnNames[i] = data.get(0).get(i);
		}
		// Adds two columns at the end of the array list representing the headings for the check in and check out buttons
		columnNames[data.get(0).size()] = "Check In (+1)";
		columnNames[data.get(0).size()+1] = "Check Out (-1)";
		
		// Stores each remaining row from the excel file in the two dimensional array list rowData
		Object rowData[][] = new Object[data.size()-1][data.get(0).size()+2];
		for (int i = 0; i<data.size()-1; i++){
			for (int j = 0; j<data.get(0).size(); j++){
				if (Character.isDigit((data.get(i+1).get(j).charAt(0)))) {
					rowData[i][j] = Integer.parseInt(data.get(i+1).get(j));
				}
				else{
					rowData[i][j] = data.get(i+1).get(j);
				}
			}
		}
		// Adds two elements to the end of each row that will later be the buttons used to add and subtract the quantities of items.
		for (int i = 0; i<data.size()-1; i++){
			rowData[i][data.get(0).size()] = "+";
			rowData[i][data.get(0).size()+1] = "-";
		}
		
	    table = new JTable(rowData, columnNames);
	    // Renders the last two columns as buttons
	    table.getColumn("Check In (+1)").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Check In (+1)").setCellEditor(new ButtonEditor(new JCheckBox()));
	    table.getColumn("Check Out (-1)").setCellRenderer(new ButtonRenderer());
	    table.getColumn("Check Out (-1)").setCellEditor(new ButtonEditor(new JCheckBox()));
	    table.setFont(new Font ("Sans Serif", Font.PLAIN, 20));
	    table.setRowHeight(40);
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
	    table.setDefaultRenderer(Object.class, centerRenderer);
	    
	    JScrollPane scrollPane = new JScrollPane(table);
	    tablePanel.add(scrollPane, BorderLayout.CENTER);	//creates a panel containing the table
	    //creates some buttons without a current use
	    JButton btnTag = new JButton("Make/Get Tags");
	    btnTag.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				InventoryGUI.frame.dispose();
				TagGUI.main(InventoryGUI.args);
				
			}
	    	
	    });
	    //JButton btnSave = new JButton("Save to File");
	    JButton btnAdd = new JButton("Auto-Update from Images");
	    btnAdd.addActionListener(new ActionListener(){
	    	@Override
	    	public void actionPerformed(ActionEvent arg0){
	    		InventoryGUI.frame.dispose();
	    		Iterate.iter("/");
	    	}
	    });
	    buttonPanel.add(btnTag);
	    buttonPanel.add(btnAdd);
	    //buttonPanel.add(btnSave);
	 // adds the panels to the frame and sets it to be visible
	    frame.getContentPane().add(sheetPanel, BorderLayout.NORTH);
	    frame.getContentPane().add(tablePanel, BorderLayout.WEST);
	    frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	    //creates a menu bar that allows for easy accessibility
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
	// The main program runs the class above and ensures that the program stops upon exiting
	public static void main(String[] args) throws IOException {
		InventoryGUI frame = new InventoryGUI();
	    frame.addWindowListener(new WindowAdapter() {
	      public void windowClosing(WindowEvent e) {
	        System.exit(0);
	      }
	    });
	  }
	
}


//The next several classes render and edit the buttons contained in the tables using the specific mouse click, and record the location of 
//the mouse click relative to the table by storing the row and column of the click.
class ButtonRenderer extends JButton implements TableCellRenderer {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ButtonRenderer() {
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
class ButtonEditor extends DefaultCellEditor {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JButton button;
	  private String label;
	  
	  private boolean isPushed;
	  public ButtonEditor(JCheckBox checkBox) {
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
	    try {
			changeTable(table, row, column);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return button;
	  }
	  // This method adds or subtracts 1 from the Quantity depending on whether + or - are clicked
	  public void changeTable(JTable table, int row, int col) throws IOException{
		  if (isPushed){
			  if (col==3){ //changed from 3 to 2 (might be formatting error for my excel file)
				  //System.out.println((table.getValueAt(row,col-1)));
				  table.setValueAt((Integer)(table.getValueAt(row,col-1))+1, row, col-1);
				  //System.out.println(InventoryGUI.Sheetdata);
				  InventoryGUI.Sheetdata.get(InventoryGUI.curSheet).get(row+1).set(col-1,String.valueOf(table.getValueAt(row,col-1)));
				  //System.out.println(InventoryGUI.Sheetdata);
				  InventoryGUI.inventoryFile.write(InventoryGUI.curSheet,row+1,col-1,String.valueOf(table.getValueAt(row,col-1)));
			  }
			  else if (col==4){ //changed from 4 to 3 (might be formatting error for my excel file)
				  //System.out.println((table.getValueAt(row,col-2)));
				  table.setValueAt((Integer)(table.getValueAt(row,col-2))-1, row, col-2);
				  InventoryGUI.Sheetdata.get(InventoryGUI.curSheet).get(row+1).set(col-2,String.valueOf(table.getValueAt(row,col-2)));
				  InventoryGUI.inventoryFile.write(InventoryGUI.curSheet,row+1,col-2,String.valueOf(table.getValueAt(row,col-2)));
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
