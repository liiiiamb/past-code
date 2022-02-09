package util.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;


import util.Manager.FileManager;
import util.Manager.MediaEditor;
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
public class MusicScreen extends FileManager{ //extending filemanager allows its methods and variables to be used
	DefaultTableModel model = new DefaultTableModel(); //sets the defaultmodel as a new defaulttablemodel object, this is used later in the program and in filemanager
	DefaultTableModel defaultModelM = new DefaultTableModel(); //new defaultmodel is created 
	private int rowNumber = 0; //creates a private class variable that has a default value of 0
	JTable table = new JTable(model); //creates a table class variable that has the model that has been previously created, this is used later in the program
	/*
	 * the below lines created new objects of the mediaeditor and filemanager classes and sets them to null and then 
	 * allows their methods to be accessed within this class
	 */
	MediaEditor mediaEditor = null; 
	FileManager fileManager = null;
	
	int row = table.getSelectedRow(); //creates a row class variable that has a value of the user selected row in the jtable 
	public MusicScreen ()
	{
		
	}
	public MusicScreen (MediaEditor me)
	{
		this.mediaEditor = me;
	}
	public MusicScreen (FileManager me)
	{
		this.fileManager = me;
	}

	public void main() { //all code within this main method is ran 
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
		 * gets all of the chosen getters and their values from the file manager class
		 * so they can be used and altered within this class
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
		JFrame frameM = new JFrame ("Music"); //creates a new frame that has the title "Music"
		frameM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the program when the X is pressed by the user 
		frameM.setSize(700,500); //sets the default size of the frame, its the same as the size throughout the program
		
		JPanel panel = new JPanel(); //creates an empty panel
		GridLayout layout = new GridLayout(1,3); //sets the layout of the panel to a grid layout which uses 1 row and 3 sections in that row
		layout.setHgap(20); //sets the gap of the components in the layout as 20 
		/*
		 * The below code creates the icon to go inside the home button, it first defines a new ImageIcon with the path of the image, as the image is saved
		 * in the same folder as this class, it just needs the name of the file. getImage then gets the image itself and then the image is scaled down to a smaller
		 * size so it can be viewed inside the button. The button is then created and instead of having text inside the button it has the ImageIcon variable name that was just
		 * created.
		 */
		ImageIcon home_icon = new ImageIcon(new ImageIcon("home.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT));
		JButton home = new JButton(home_icon);
		home.addActionListener(fileManager.getActionListenerForButtonHome());
		home.setPreferredSize(new Dimension(20,20));
		panel.add(home); //adds the button to the created panel
		JButton save = new JButton("Save"); //creates a new save button
		save.addActionListener(saveToFile()); //adds an actionlistener to the save button which is created later in this program
		panel.add(save); //adds the save button to the panel
		panel.add(Box.createHorizontalStrut(200)); //creates an empty horizontal box for spacing between the button and the next component
		
		JLabel title = new JLabel ("Music"); //creates a jtabel with the text "Music"
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14)); //sets the default font and size of the text
		panel.add(title); //adds the label to the panel
		panel.add(Box.createHorizontalStrut(200)); //adds a horizontal gap between the label and the next component
		
		JTextArea search = new JTextArea(0,10); //creates a text area which has a default height and a default+10 width
		panel.add(search); //adds the text area to the panel

		search.addKeyListener(this.fileManager.getKeyListenerForSearch()); //adds a keylistener to the text area, this is created in file manager
		frameM.getContentPane().add(panel, "North"); //adds the panel to the frame at the northen most point
		
		
	
		JPanel panel2 = new JPanel(); //creates a second empty panel
		JPopupMenu popup = new JPopupMenu(); //creates a popup menu that will be shown when rightclicking on the table
		JMenuItem renameItem = new JMenuItem (new AbstractAction("Rename File") { //creates a jmenuitem and assigns it an abstractaction which is defined below
			public void actionPerformed(ActionEvent e) {
				/*
				 * creates a new frame when the menu item is clicked, this allows
				 * the user to enter a name into a text area which is then obtained and 
				 * replaces the current name of the file in the jtable 
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
				
				/*
				 * the below code sets the location of the components, the first component
				 * has a x and y default position, after that component has been added the y 
				 * position has been changed to 2 to make it lower then a new component is created
				 * and added 
				 */
				GridBagConstraints c = new GridBagConstraints();
				
				c.gridx = 0;
				c.gridy = 0;
				panel2.add(text, c);
				panel2.add(Box.createVerticalStrut(40));
				c.gridy = 2;
				c.anchor=GridBagConstraints.CENTER;
				panel2.add(enterName, c);
				
				renameFrame.getContentPane().add(panel2, "Center"); //adds the panel to the center of the frame 
				
				JPanel panel3 = new JPanel(); //creates a third blank panel
				panel3.add(Box.createHorizontalStrut(500));
				JButton cancel = new JButton("Cancel"); //creates a cancel button and adds an actionlistener that is defined in filemanager
				cancel.addActionListener(fileManager.getActionListenerForButtonCancel());
				
				cancel.setPreferredSize(new Dimension(80, 20)); //sets the preferredsize for the button, this may be altered by the sizing or placement of other components 
				panel3.add(cancel); //adds the cancel button to the third panel
				JButton save = new JButton("Save"); //creates a save button 
				save.addActionListener(event -> { //tested the use of lambda expressions here - adds actionlistener to the save button
						String newNameValue = enterName.getText(); //gets the text entered into the enterName textfield created earlier
						System.out.println("The new name is: " + newNameValue); //these two lines are to test if the save click works and the name is correct
						System.out.println("Save has been clicked!!"); 
						defaultModelM.removeRow(table.getSelectedRow()); //removes the selected row from the table
						//set new object here to be added into the default model - new string array
						filesM[0] = filesM[0].replace(sM, newNameValue); //replaces the first item in the array with the new name 
						System.out.println("The new array is " + Arrays.toString(filesM)); //test print out the new array
						renameFrame.dispose(); //disposes of this rename frame and returns to the music screen
						frameM.setVisible(true); //shows the music screen again
						defaultModelM.addRow(filesM); //adds a row to the music table with the updated filename 
						
				});
			
				save.setPreferredSize(new Dimension(80, 20)); //sets the preferred size of the button 
				panel3.add(save); //adds the button to the third panel
				
				renameFrame.getContentPane().add(panel3, "South"); //adds the third panel to the frame at the southern most point
				
				renameFrame.setVisible(true); //sets the renameframe to visible
				FileManager fm = new FileManager(); //creates a new instance of the filemanager class 
				
			}
		});
		JMenu addToPlay = new JMenu ("Add to Playlist"); //creates a menu in which other menu items can be attatched to
		/*
		 * the 6 lines below create new menu items for playlists 1,2 and 3 and assign action listeners to each
		 * that are created inside the filemanager class
		 */
		JMenuItem playlist1 = new JMenuItem ("Playlist 1"); 
		playlist1.addActionListener(fileManager.getActionListenerAddItemToPlay1()); //-- FIX THIS!!
		JMenuItem playlist2 = new JMenuItem ("Playlist 2");
		playlist2.addActionListener(fileManager.getActionListenerAddItemToPlay2());
		JMenuItem playlist3 = new JMenuItem ("Playlist 3");
		playlist3.addActionListener(fileManager.getActionListenerAddItemToPlay3());
		/*
		 * the below code adds the playlist items to their parent menu item first, 
		 * then adds each main menu item to the menu which is shown upon right clicking
		 * the table 
		 */
		addToPlay.add(playlist1);
		addToPlay.add(playlist2);
		addToPlay.add(playlist3);
		popup.add(renameItem);
		popup.add(addToPlay);
		FileManager.getDefaultModelM(); //gets the defaultmodel post-altering from the filemanager class
		defaultModelM = FileManager.getDefaultModelM(); //sets the defaultmodel as the defaultmodel from the fm class
		table = new JTable(defaultModelM); //creates a jtable to go inside the frame - this table has the defaultmodel altered from the fm class and this class
		FileManager.getDefaultModelM(); 
		table.setComponentPopupMenu(popup); //sets the popup of the table as the previously created popup menu
		JScrollPane tableScroller = new JScrollPane(table); //adds a table scroller to the table, this only shows when there are a certain amount of items
		panel2.add(tableScroller); //adds the table to the panel2
		frameM.getContentPane().add(panel2, "Center"); //adds panel2 to the frame at the center most point
		
		JPanel panelBttm = new JPanel(); //creates a main panel which other panels are then added into
		JPanel panel3 = new JPanel(); //creates a subpanel
		JButton deleteBtn = new JButton("Delete Song"); //creates a delete button
		deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	/*
		    	 * this code only happens when the mouse enters the button area, when the button is hovered over the background and foreground color changes
		    	 */
		    	deleteBtn.setBackground(Color.GRAY);
		    	deleteBtn.setForeground(Color.WHITE);
		    }
		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	/*
		    	 * this code only happen when the mouse exits the button area after entering, the code color resets to the standard color it was first shown as
		    	 */
		    	deleteBtn.setBackground(UIManager.getColor("control"));
		    	deleteBtn.setForeground(Color.BLACK);
		    }
		});
		panel3.add(deleteBtn, "West"); //adds the delete button the panel at the western most point
		panel3.add(Box.createHorizontalStrut(170)); //creates a horizontal gap between the button and the next component
		
		
		deleteBtn.addActionListener(getActionListenerForButtonDelFile()); //adds an actionlistener to the delete button - this is created later in the file
		
		/*
		 * the below code creates another subpanel, gives it a preferred size and sets
		 * its layout as borderlayout. 
		 */
		JPanel panelPlay = new JPanel();
		panelPlay.setPreferredSize(new Dimension(100,40));
		panelPlay.setLayout(new BorderLayout());
		JLabel timeStart = new JLabel ("00:00"); //creates a placeholder label within the panel - adds it in the western most point
		panelPlay.add(timeStart, "West");
		
		ImageIcon playBtn = new ImageIcon(new ImageIcon("play.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); ; //this creates a play button that contains a play sybmol similar to how the home symbol was created before  
		JButton play = new JButton(playBtn); //creates the play button and assigns the icon to it 
		play.setOpaque(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setPreferredSize(new Dimension(30,30));
		panelPlay.add(play, "Center");
		
		
		JLabel timeEnd = new JLabel ("00:00"); //another placeholder - could be replaced with the time length of the file
		panelPlay.add(timeEnd, "East"); //adds the label to the panel at the eastern most point
		
		JLabel trackName = new JLabel("Track Name Here"); //creates a label which will be replaced with the name of the file selected
		panelPlay.add(trackName, "South"); //adds the label to the panel at the southern most point

		
		//frame.getContentPane().add(panelPlay);
		
		
		JPanel panel4 = new JPanel(); //creates a fourth panel
		JButton addBtn = new JButton("Add Song");
		addBtn.addActionListener(this.fileManager.getActionListenerForButtonAddMFile());
		
		panel4.add(addBtn, "East");
		panel4.setBorder(BorderFactory.createEmptyBorder(0,120, 0, 0)); //creates an empty border around the panel, allowing it to be seperated from other components
		/*
		 * adds the subpanels to the main panel in the western, central and eastern position 
		 */
		panelBttm.add(panel3, "West");
		panelBttm.add(panelPlay, "Center");
		panelBttm.add(panel4, "East");
		
		frameM.getContentPane().add(panelBttm, "South"); //adds the main panel to the frame at the southern most point
		
		
		frameM.setVisible(true); //sets the frame as visible 
		
		table.addMouseListener(new java.awt.event.MouseAdapter() { //listens out for mouse related actions within the table 
			@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
				rowNumber = table.rowAtPoint(evt.getPoint()); //gets the number of the row clicked
				System.out.println(rowNumber); //test print out the row number
				String selectedCellValue = (String) table.getValueAt(table.getSelectedRow() , table.getSelectedColumn()); //gets the specific cell clicked using row and column
				System.out.println("You have selected: " + selectedCellValue); //test prints out the cell selected
				//need to set value of the bottom jlabel here
				trackName.setText(selectedCellValue); //sets the selected cell to the txt entered into the trackName textarea created earlier 
				int selectedRow = table.getSelectedRow(); //gets the user selected row and assigns it to an int variable 
				try { //tries to run the code if it doesn't work correctly, runs the catch error code
					Desktop.getDesktop().open(new File((String) table.getValueAt(selectedRow, 4))); //uses the file path from the table to open the file in their default media viewer
				} catch (IOException e) {
					e.printStackTrace();
				}
			
			}
			
		});
	}

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		
	}
	
	public ActionListener getActionListenerForButtonDelFile() //creates an actionlistener for the delete button
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
							DefaultTableModel defaultModelM = (DefaultTableModel) table.getModel(); //recreates defaultModelM and assigns the current model of the table to it 
							defaultModelM.removeRow(table.getSelectedRow()); //removes the selected row from the table and sends the defaultmodel back to the table - updates the table
			}	
			
		};
		return ac;
	}
	
	public ActionListener saveToFile() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { //tries to run the below code
					File saveFile = new File("TestM.txt"); //looks for a file in the users system called TestM.txt
					if (!saveFile.exists()) { //if the file doesn't exist then the program creates the file 
						saveFile.createNewFile();
					}
					/*
					 * the below uses a combination of filewriter and bufferedwriter to get the absolute file of the TestM.txt file to allow
					 * the table to be written into it 
					 */
					FileWriter fwrite = new FileWriter(saveFile.getAbsoluteFile());
					BufferedWriter brw = new BufferedWriter(fwrite);
					
					for (int i = 0; i<table.getRowCount(); i++) { //runs as long as it doesnt get to the last row of the table
						for(int p = 0; p<table.getColumnCount(); p++) { //runs as long as it doesnt get to the last column of the table 
							brw.write((String)table.getModel().getValueAt(i, p)+" "); //writes the row and column values of the table to the txt file 
						}
						brw.write("\n__________\n"); //creates a new line between each row of the table 
					}
					/*
					 * the below lines close the buffered and the file writers
					 */
					brw.close();
					fwrite.close();
					JOptionPane.showMessageDialog(null, "The table has been saved to TestM.txt"); //shows a confirmation message telling the user the table has been saved
					
					
				}catch(Exception ex) { //this code runs when an error occurs 
					ex.printStackTrace();
				}
			}
		};
		return ac;
	}

}
