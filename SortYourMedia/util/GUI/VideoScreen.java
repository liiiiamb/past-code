package util.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;


import util.Manager.FileManager;
import util.Manager.MediaEditor;
import util.Manager.PlaylistEditor;
import util.Manager.WatchFolder;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;
import util.Manager.WatchVideo;
/*
 * Code was used from the following resources to assist with the creation 
 * and development of this class. 
 * 
 * https://www.w3schools.com/java/java_arraylist.asp
 * https://www.w3schools.com/java/java_user_input.asp
 * https://docs.oracle.com/javase/tutorial/essential/io/notification.html
 * http://www.java2s.com/Code/Java/Swing-JFC/FiledataTablefilenamesizetype.htm
 * https://stackoverflow.com/questions/14526260/how-do-i-get-the-file-name-from-a-string-containing-the-absolute-file-path
 * https://stackoverflow.com/questions/10221652/how-to-add-data-to-jtable-from-different-class
 * https://stackoverflow.com/questions/3549206/how-to-add-row-in-jtable?rq=1
 * https://stackoverflow.com/questions/14753408/display-an-arraylist-of-arraylists-in-a-jtable
 * 
 * The code from these resources was not copied directly. It was used as a guide to create the 
 * relevant methods/variables used both inside this class and within other classes.
 * 
 */
public class VideoScreen extends FileManager{ //gets all of the methods and variables from the filemanager class
	DefaultTableModel model = new DefaultTableModel(); //creates a new dtm object - this is created both here and in filemanager and is altered in both
	static DefaultTableModel defaultModel = new DefaultTableModel(); //creates another new dtm object - this is altered in both this and the filemanager class
	private int rowNumber = 0; //creates a private int variable which is set to 0
	JTable table = new JTable(model); //creates a JTable with the defaulttablemodel of model which was created earlier 
	protected JLabel label; //creates a jlabel to be used later in the class
	/*
	 * the following block of code allows mediaeditor, filemanager and playlisteditor to use this class and vice versa 
	 */
	MediaEditor mediaEditor = null; 
	static FileManager fileManager = null;
	PlaylistEditor playlistEditor = null;
	public VideoScreen (PlaylistEditor ac) {
		this.playlistEditor = ac;
	}
	public VideoScreen (FileManager me)
	{
		this.fileManager = me;
	}


	public VideoScreen ()
	{
		
	}
	public VideoScreen (MediaEditor me)
	{
		this.mediaEditor = me;
	}
	

	
	

	int row = table.getSelectedRow(); //creates an int variable which gets the user selected row within the jtable 

	public void main() {
		/*
		 * watches the 3 chosen directories from the watchfolder class at any time 
		 * this screen is visible and executes code if a file is added/deleted/modified
		 * within them.
		 */
		System.out.println("Starting Thread - running in background");
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.submit(new WatchVideo());
		executor.submit(new WatchImage());
		executor.submit(new WatchMusic());

	
		/*
		 * gets the chosen values from filemanager so they can be used within this class
		 */
		fileManager = new FileManager();
		FileManager.getColumnNames();
		FileManager.getData();
		FileManager.getFileLM();
		FileManager.getFilePathS();
		FileManager.getFileT();
		FileManager.getfLength();
		FileManager.getfLengthS();
		FileManager.getS();
		FileManager.getFiles();
		
		/*
		 * creates a new frame - sets the program to close when the user exits the screen and sets the default size of the frame 
		 */
		JFrame frame = new JFrame ("Videos");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		
		/*
		 * creates a new panel and assigns a grid layout to it - this contains 1 row and 3 sections withinn that row 
		 */
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(1,3);
		layout.setHgap(20);
		
		ImageIcon home_icon = new ImageIcon(new ImageIcon("home.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); //assigns the image icon to the image button - scales it so it can properly fit within the button
		
		JButton home = new JButton(home_icon);//creates a new jbutton and assigns the home icon that was just set inside the button
		home.addActionListener(fileManager.getActionListenerForButtonHome()); //adds an actionlistener for the home button that is created within the filemanager class
		home.setPreferredSize(new Dimension(20,20)); //sets the preferred size of the button - this can be changed by other components 
		panel.add(home); //adds the home button to the panel 
		JButton save = new JButton("Save");
		save.addActionListener(saveToFile()); //adds an actionlistener to the save button - this is created later in this file 
		panel.add(save);
		panel.add(Box.createHorizontalStrut(200)); //creates an empty horizontal gap between the save button and the next component, allows for components to be spaced out
		
		JLabel title = new JLabel ("Videos");
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14)); //assigns the standard program font and font size to the label 
		panel.add(title);
		panel.add(Box.createHorizontalStrut(200));
		
		JTextArea search = new JTextArea(0,10); //creates a new text area which has the default height size and a +10 to the default width size 
		panel.add(search);
		search.addKeyListener(this.fileManager.getKeyListenerForSearch()); //adds a key listener to the search text area - when a key is entered an action takes place this is defined in filemanager
		frame.getContentPane().add(panel, "North"); //adds the panel to the frame in the northern most point 
		
		
		
		JPanel panel2 = new JPanel();
		JPopupMenu popup = new JPopupMenu(); //creates a popup menu that is shown when the user right clicks on the table 
		JMenuItem renameItem = new JMenuItem (new AbstractAction("Rename File") { //abstractaction allows for an action to be specified and ran
			public void actionPerformed(ActionEvent e) {
				/*'
				 * creates a new frame when the popup menu item is clicked - this allows the user
				 * to pick a new name for the selected file and replace its current name
				 */
				JFrame renameFrame = new JFrame ("Name File");
				renameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				renameFrame.setSize(700,500);
				
				JPanel panel = new JPanel();
				GridLayout layout = new GridLayout(1,3);
				layout.setHgap(20);
				ImageIcon home_icon = new ImageIcon(new ImageIcon("home.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
				JButton home = new JButton(home_icon);
				home.setPreferredSize(new Dimension(20,20));
				panel.add(home, "West");
				panel.add(Box.createHorizontalStrut(170));
				home.addActionListener(fileManager.getActionListenerForButtonHome());
				
				JLabel title = new JLabel("Name File");
				title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));
				panel.add(title, "Center");
				panel.add(Box.createHorizontalStrut(200));
				renameFrame.getContentPane().add(panel, "North");
				
				JPanel panel2 = new JPanel(new GridBagLayout());
				

				
				JLabel text = new JLabel("Please choose a name for the file");
				text.setFont(new Font("Rockwell Bold", Font.PLAIN, 13));

				
				JTextField enterName = new JTextField(20);
				
				
				GridBagConstraints c = new GridBagConstraints();
				/*
				 * the below block of code first sets x and y as their
				 * default position, then after a component is added y is changed to be 
				 * a higher number, this moves the next component with c attached to it to a 
				 * lower point in the screen 
				 */
				
				c.gridx = 0;
				c.gridy = 0;
				panel2.add(text, c);
				panel2.add(Box.createVerticalStrut(40));
				c.gridy = 2;
				c.anchor=GridBagConstraints.CENTER; //anchors the next component to the center of its area 
				panel2.add(enterName, c);
				
				renameFrame.getContentPane().add(panel2, "Center"); //adds the panel to the center of the frame 
				
				JPanel panel3 = new JPanel();
				panel3.add(Box.createHorizontalStrut(500));
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(fileManager.getActionListenerForButtonCancel());
				
				cancel.setPreferredSize(new Dimension(80, 20));
				panel3.add(cancel);
				JButton save = new JButton("Save");
				save.addActionListener(event -> { //tested the use of lambda expressions here 
						String newNameValue = enterName.getText(); //gets what was entered into the text field created earlier and assigns it to a string
						System.out.println("The new name is: " + newNameValue);
						System.out.println("Save has been clicked!!");
						defaultModel.removeRow(table.getSelectedRow()); //removes the row the user selects from the table 
						//set new object here to be added into the default model - new string array
						files[0] = files[0].replace(s, newNameValue); //replaces the first item in the array with the new name 
						System.out.println("The new array is " + Arrays.toString(files)); //test prints out the array 
						renameFrame.dispose(); //disposes the current renameFrame as it is no longer needed
						frame.setVisible(true); //reshows the screen that has the videoscreen table 
						defaultModel.addRow(files); //adds a row to the video table with the new name updated - all other details are the same 
						
				});
			
				save.setPreferredSize(new Dimension(80, 20)); //sets the preferred size of the save button - this can be changed by other components 
				panel3.add(save);
				
				renameFrame.getContentPane().add(panel3, "South");
				
				renameFrame.setVisible(true);
				FileManager fm = new FileManager(); //creates a new instance of the file manager class
				
			}
		});
		
		/*
		 * the below block of code starts by creating a main menu item using menu
		 * menuitems can then be added into it, creating a show on hover feature. 
		 * The menu items are then defined and their actions are assigned to them - these
		 * are created within the filemanager class. The menu items are 
		 * then assigned to the main parent item.
		 */
		JMenu addToPlay = new JMenu ("Add to Playlist");
		JMenuItem playlist1 = new JMenuItem ("Playlist 1");
		playlist1.addActionListener(fileManager.getActionListenerAddItemToPlay1()); //-- FIX THIS!!
		JMenuItem playlist2 = new JMenuItem ("Playlist 2");
		playlist2.addActionListener(fileManager.getActionListenerAddItemToPlay2());
		JMenuItem playlist3 = new JMenuItem ("Playlist 3");
		playlist3.addActionListener(fileManager.getActionListenerAddItemToPlay3());
		addToPlay.add(playlist1);
		addToPlay.add(playlist2);
		addToPlay.add(playlist3);
		popup.add(renameItem); //adds the rename menu item to the popup menu
		popup.add(addToPlay); //adds the parent add to playlist menu item to the popup menu
		FileManager.getDefaultModel(); //gets the default model from the file manager class after it has been altered
		defaultModel = FileManager.getDefaultModel(); //sets the default model object as the defaultmodel from the filemanager class - this allows it to be used within this class 
		WatchFolder.getDefaultModel();
		defaultModel = WatchFolder.getDefaultModel();
		table = new JTable(defaultModel); //creates a new table with the default model created and obtained in the previous lines 
		
		


		
		table.setComponentPopupMenu(popup); //sets the popup menu for the table as the popup menu just created 
		JScrollPane scroller = new JScrollPane(table); //adds a scroller to the table - this only appears when there are a certain amount of items 
		panel2.add(scroller);
		
		
		frame.getContentPane().add(panel2, "Center");
		
		JPanel panelBttm = new JPanel();
		JPanel panel3 = new JPanel();
	
		JButton deleteBtn = new JButton("Delete Video");
		deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() { 
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	/*
		    	 * this code happens as soon as the mouse enters the area of the delete
		    	 * button. When the mouse enters the area, the background and the text colour
		    	 * of the button changes to show that the button is being hovered
		    	 * over 
		    	 */
		    	deleteBtn.setBackground(Color.GRAY);
		    	deleteBtn.setForeground(Color.WHITE);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	/*
		    	 * this code happens when the mouse exits the button area 
		    	 * after it has entered it, the color of the button resets to its
		    	 * original colour. 
		    	 */
		    	deleteBtn.setBackground(UIManager.getColor("control"));
		    	deleteBtn.setForeground(Color.BLACK);
		    }
		});
		deleteBtn.addActionListener(getActionListenerForButtonDelFile());
		panel3.add(deleteBtn, "West");
		panel3.add(Box.createHorizontalStrut(170));
	
		
	
		
		
		JPanel panelPlay = new JPanel();
		panelPlay.setPreferredSize(new Dimension(100,40));
		panelPlay.setLayout(new BorderLayout());
		JLabel timeStart = new JLabel ("00:00");
		panelPlay.add(timeStart, "West");
		
		Icon playBtn =  new ImageIcon(new ImageIcon("play.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); ; //this creates a play button that contains a play sybmol similar to how the home symbol was created before  
		JButton play = new JButton(playBtn);
		/*
		 * the below 3 lines allow the play button to be viewed and shown on the screen
		 */
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		
		panelPlay.add(play, "Center");
		
		
		JLabel timeEnd = new JLabel ("00:00"); //currently placeholders, would need to be replaced with time when user clicks on file 
		panelPlay.add(timeEnd, "East");
		
		label = new JLabel("Name of Files"); //this is a placeholder - it is shown when the GUI screen is shown but is replaced when the user selects on a row 
		label.setPreferredSize(new Dimension(300, 20)); //sets the size of the label - make it wide enough to fit the name of any file
		panelPlay.add(label, "South");


		
		
		JPanel panel4 = new JPanel();
		JButton addBtn = new JButton("Add Video");
		addBtn.addActionListener(fileManager.getActionListenerForButtonAddVFile());
		panel4.add(addBtn, "East");
		panel4.setBorder(BorderFactory.createEmptyBorder(0,120, 0, 0)); //creates an empty border around the play area to stop other components from getting too close or from interferring with the layout 
		panelBttm.add(panel3, "West");
		panelBttm.add(panelPlay, "Center");
		panelBttm.add(panel4, "East");
		
		frame.getContentPane().add(panelBttm, "South");
		
		frame.setVisible(true);
		table.addMouseListener(new java.awt.event.MouseAdapter() {
		@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) { //this code runs when the mouse is clicked within the table 
			rowNumber = table.rowAtPoint(evt.getPoint()); //the row number variable created earlier, gets the row clicked by the user and gives it a number 
			System.out.println(rowNumber); //test - prints out the number of the row clicked changes based on the row clicked 
			String selectedCellValue = (String) table.getValueAt(table.getSelectedRow() , table.getSelectedColumn()); //gets a specific cell by getting the selected column and row by where the user clicked 
			System.out.println("You have selected: " + selectedCellValue); //test - prints out the correct selected cell
			//need to set value of the bottom jlabel here
			label.setText(selectedCellValue); //sets the text of the label at the bottom of the screen where the file name is shown to the file clicked by the user 
			int selectedRow = table.getSelectedRow(); //creates an int variable which gets the row the user selects 
			try { //tries the below code until an error is encountered 
				Desktop.getDesktop().open(new File((String) table.getValueAt(selectedRow, 4)));//uses the file path from the jtable to open the file in the users default media viewer 
			} catch (IOException e) {
				e.printStackTrace(); //this only happens when an error occurs 
			}
		}});
		}
/*
 * the below four blocks of code are used to get and set the current 
 * model and table state so they can be accessed and used by other classes
 * by calling VideoScreen.getModel(), etc. 
 */
	public DefaultTableModel getModel() {
		return model;
	}
	public void setModel(DefaultTableModel model) {
		this.model = model;
	}
	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}

	
	
	public ActionListener getActionListenerForButtonDelFile()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
							DefaultTableModel defaultModel = (DefaultTableModel) table.getModel(); //assigns a new instance of defaultModel to the current model of the table 
							defaultModel.removeRow(table.getSelectedRow()); //removes a row from the table that the user selects - this sends the defaultmodel back to the table where it updates the table and the row is removed 
			}	
			
		};
		return ac;
	}
	
	public ActionListener saveToFile() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { //tries until an error is encountered 
					File saveFile = new File("TestV.txt"); //looks for a file on the users system called TestV.txt
					if (!saveFile.exists()) { //if the file doesnt exist then the program creates the file 
						saveFile.createNewFile();
					}
					/*
					 * the below are used as a combination to get the file and write the table 
					 * to it 
					 */
					FileWriter fwrite = new FileWriter(saveFile.getAbsoluteFile());
					BufferedWriter brw = new BufferedWriter(fwrite);
					 
					for (int i = 0; i<table.getRowCount(); i++) { //runs until the last row is completed
						for(int p = 0; p<table.getColumnCount(); p++) { //runs until the last column is completed 
							brw.write((String)table.getModel().getValueAt(i, p)+" "); //writes the table and all of its contents into the txt file 
						}
						brw.write("\n__________\n"); //adds each table row onto a new line within the txt file 
					}
					/*
					 * the below two lines close the buffered and the file writer 
					 */
					brw.close();
					fwrite.close();
					JOptionPane.showMessageDialog(null, "The table has been saved to TestV.txt"); //confirms to the user that the table has been saved to the specific file 
					
					
				}catch(Exception ex) {
					ex.printStackTrace(); //ran when an error occurs. 
				}
			}
		};
		return ac;
	}
	


}
