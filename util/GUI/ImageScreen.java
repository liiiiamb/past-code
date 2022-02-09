package util.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class ImageScreen extends FileManager{ //using extends allows all of the methods and variables to be accessed and used from the FileManager class. Only one class can be extended from another
	DefaultTableModel model = new DefaultTableModel(); //This sets the default table model as a new defaulttablemodel object, this model is referenced and altered in the FileManager class
	DefaultTableModel defaultModelI = new DefaultTableModel(); //similar to above, creates a new defaulttablemodel object that is used in this code and created within the filemanager class.
	private int rowNumber = 0; //creates a class variable that is only able to be accessed within this class, sets its value to 0. 
	JTable table = new JTable(model); //this line creates a table class variable that sets the model of the table to the previously created defaulttablemodel
	int row = table.getSelectedRow(); //creates an int class variable that gets the number of the row that is selected within the JTable, this code is used later in this class.
	/*
	 * The next 14 lines of code are responsible for creating new null objects of the 3 classes, 
	 * this code allows the methods to be accessed from any of the classes by using the code className.methodName
	 */
	MediaEditor mediaEditor = null;
	FileManager fileManager = null;
	public ImageScreen ()
	{
		
	}
	public ImageScreen (MediaEditor me)
	{
		this.mediaEditor = me;
	}
	public ImageScreen (FileManager me)
	{
		this.fileManager = me;
	}

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
		 * The 9 lines of code below get all of the variables and code from within the chosen getters, this code isn't ran as it has nothing to run
		 * it just gets the values so they can be used or altered later in this class. 
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
		JFrame frame = new JFrame ("Images"); //creates a new blank frame that has the title "Images"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //when the user clicks the X at the top right of the program, this line closes the program.
		frame.setSize(700,500); //sets the default size of the frame, the frame will never be any smaller or larger than this. 
		
		JPanel panel = new JPanel(); //creates an empty panel for the components to be added to
		GridLayout layout = new GridLayout(1,3); //creates a grid layout to go alongisde the panel, using 1,3 allows the components to just take up one line but have 3 different sections 
		layout.setHgap(20); //this sets a gap between the components created of 20. 
		/*
		 * The below code creates the icon to go inside the home button, it first defines a new ImageIcon with the path of the image, as the image is saved
		 * in the same folder as this class, it just needs the name of the file. getImage then gets the image itself and then the image is scaled down to a smaller
		 * size so it can be viewed inside the button. The button is then created and instead of having text inside the button it has the ImageIcon variable name that was just
		 * created.
		 */
		ImageIcon home_icon = new ImageIcon(new ImageIcon("home.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); 
		JButton home = new JButton(home_icon);
		home.setPreferredSize(new Dimension(20,20));
		panel.add(home); //adds the button the created panel.
		JButton save = new JButton("Save"); //creates a new JButton, this has the text "Save" inside it
		save.addActionListener(saveToFile()); //adds an actionlistener to the save button, this actionlistener is created later in this file so it doesnt need fileManager., etc.
		panel.add(save); //adds the save button the panel
		panel.add(Box.createHorizontalStrut(200)); //creates a horizontal gap using an empty box between the components.

		
		home.addActionListener(fileManager.getActionListenerForButtonHome()); //adds an actionlistener to the home button, this is defined within the fileManager class so needs fileManager. at the start.
		/*
		 * The below code creates a JLabel with the text "Images", assigns the standard font to it as well as a font size
		 * before adding it to the panel and adding another horizontal gap of 200 to space out the components. 
		 */
		JLabel title = new JLabel ("Images");
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));
		panel.add(title);
		panel.add(Box.createHorizontalStrut(200));
		
		JTextArea search = new JTextArea(0,10); //Creates a text area which has a default height and a width of +10 to the default
		panel.add(search); //adds the text area to the panel
		search.addKeyListener(this.fileManager.getKeyListenerForSearch()); //adds a key listener to the search button so when a key is entered this method from filemanager is called.
		frame.getContentPane().add(panel, "North"); //adds the panel created and changed above to the frame at the northen most point. 
		
		
	
		JPanel panel2 = new JPanel(); //creates an empty second panel 
		JPopupMenu popup = new JPopupMenu(); //this is used upon right clicking the table, popup menus appear small and can have multiple options 
		JMenuItem renameItem = new JMenuItem (new AbstractAction("Rename File") { //adds a menu item to the pop up menu, this first one is responsible for renaming the file, abstractaction creates an action which is defined more below. 
		public void actionPerformed(ActionEvent e) {
			/*
			 * The below first set of code creates a new frame which is only shown when the menu item is clicked 
			 */
			JFrame renameFrame = new JFrame ("Name File");
			renameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			renameFrame.setSize(700,500);
			
			JPanel panel = new JPanel(); //creates a new panel to go inisde the frame
			GridLayout layout = new GridLayout(1,3); //assigns a 1 row, 3 section gridlayout to the panel
			layout.setHgap(20);//sets a gap between the components of 20 to space them out. 
			ImageIcon home_icon = new ImageIcon(new ImageIcon("home.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); //adds the home icon similar to earlier in the code. 
			JButton home = new JButton(home_icon);
			home.setPreferredSize(new Dimension(20,20));
			panel.add(home, "West"); //adds the home button at the western most point of the panel 
			panel.add(Box.createHorizontalStrut(170)); //adds a horizontal spacing of 170 using an empty box between this component and the next 
			home.addActionListener(fileManager.getActionListenerForButtonHome());  //adds an action listener to the home button, this is created within the filemanager class, so it is called using filemanager.methodgetter 
			
			JLabel title = new JLabel("Name File"); //creates a label component with the name "Name File"
			title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14)); //assigns the standard font and size to the label.
			panel.add(title, "Center"); //adds the label to the panel at the center most point
			panel.add(Box.createHorizontalStrut(200)); //creates an empty box spacing horizontally of 200 
			renameFrame.getContentPane().add(panel, "North"); //adds the panel to the frame at the northen most point
			
			JPanel panel2 = new JPanel(new GridBagLayout()); //creates a second panel that uses the gridbag layout 
			

			/*
			 * the below code creates a new JLabel with text and sets its font to the standard font and size 
			 */
			JLabel text = new JLabel("Please choose a name for the file");
			text.setFont(new Font("Rockwell Bold", Font.PLAIN, 13));

			
			JTextField enterName = new JTextField(20); //creates a jtextfield, instead of using 0,20 this line just uses 20 which has the same effect 
			
			/*
			 * the below code uses gridbagconstraints to set the specific location of components, it starts by creating a new gridbagconstraints object then 
			 * setting the x and y co-ords to their defaults, then adds the items into the panel at that position before creating a vertical gap of 40 using an 
			 * invisible box. the y co-ord is then changed before a new component is added to make that comment appear below the previosuly created. 
			 */
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			panel2.add(text, c);
			panel2.add(Box.createVerticalStrut(40));
			c.gridy = 2;
			c.anchor=GridBagConstraints.CENTER;
			panel2.add(enterName, c);
			
			renameFrame.getContentPane().add(panel2, "Center"); //adds the second panel to the frame at the center most point 
			
			JPanel panel3 = new JPanel(); //creates a third new panel
			panel3.add(Box.createHorizontalStrut(500)); //adds a horizontal gap of 500 before any components are added, this moves the components to the far right of the screen
			JButton cancel = new JButton("Cancel"); //creates a new jbutton with the text inside saying "Cancel"
			cancel.addActionListener(fileManager.getActionListenerForButtonCancel()); //adds an actionlistener to the cancel button, this actionlistener is defined in the file manager class
			
			cancel.setPreferredSize(new Dimension(80, 20)); //sets the preferredsize of the button, this can be changed however if any other components take up too much or too little space.
			panel3.add(cancel); //adds the cancel button to the third panel
			JButton save = new JButton("Save"); //creates another jbutton that has the text "Save" inside 
			save.addActionListener(event -> { //tested the use of lambda expressions here, adding an actionlistener to the save button 
					String newNameValue = enterName.getText(); //creates a string variable which gets the text entered into the previously created text area
					System.out.println("The new name is: " + newNameValue); //prints out the name string variable just created - this is a test
					System.out.println("Save has been clicked!!"); //test to see if save button click action works 
					defaultModelI.removeRow(table.getSelectedRow()); //removes the row that has been selected by the user as it contains the old name of the file
					//set new object here to be added into the default model - new string array
					filesI[0] = filesI[0].replace(sI, newNameValue); //sets the first position in the array to be the original first position replaced with the new name 
					System.out.println("The new array is " + Arrays.toString(filesI)); //prints out the array - test to see if it works
					renameFrame.dispose(); //disposes the current rename frame that is shown
					frame.setVisible(true); //shows the original image screen frame when the save button is clicked
					defaultModelI.addRow(filesI); //adds the new row to the image table - this contains the new name chosen by the user, all other details are the same 
					
			});
		
			save.setPreferredSize(new Dimension(80, 20)); //this sets the preferred size of the button, this can be changed by the size of other components
			panel3.add(save); //adds the save button to the panel 
			
			renameFrame.getContentPane().add(panel3, "South"); //adds the third panel to the frame at the southern most point
			
			renameFrame.setVisible(true); //sets the frame as visible 
			FileManager fm = new FileManager(); //creates a new instance of the file manager class 
			
		}
	});
		JMenu addToPlay = new JMenu ("Add to Playlist:"); //creates another new menu item - this is used as a submenu, other items are shown when this item is hovered over
		JMenuItem playlist1 = new JMenuItem ("Playlist 1"); //adds a new menu item that has the text "Playlist 1"
		playlist1.addActionListener(fileManager.getActionListenerAddItemToPlay1()); //adds an actionlistener to the playlist 1 menu item, this is defined in the file manager class
		JMenuItem playlist2 = new JMenuItem ("Playlist 2"); //adds a new menu item that has the text "Playlist 2"
		playlist2.addActionListener(fileManager.getActionListenerAddItemToPlay2()); //adds an actionlistener defined in filemanager
		JMenuItem playlist3 = new JMenuItem ("Playlist 3"); //adds a new menu item that has the text "Playlist 3"
		playlist3.addActionListener(fileManager.getActionListenerAddItemToPlay3()); //adds an actionlistener defined in filemanager
		/*
		 * the below lines add the playlist menu items to the submenu connecting them to each other
		 */
		addToPlay.add(playlist1); 
		addToPlay.add(playlist2);
		addToPlay.add(playlist3);
		popup.add(renameItem); //adds the rename menu item to the popup menu
		popup.add(addToPlay); //adds the playlist submenu item to the popup menu
		defaultModelI = FileManager.getDefaultModelI(); //sets the default model which is used in the table as the default model which is sent from the getter in filemanager
		table = new JTable(defaultModelI);; //creates a new jtable which has the defaultmodel that is altered in both file manager and earlier in this class
		table.setComponentPopupMenu(popup); //sets the popupmenu for the table as the popumenu that was just created
		JScrollPane tableScroller = new JScrollPane(table); //adds a table scroller to the table - this only appears when there are enough items in the table for a scroller to be needed
		panel2.add(tableScroller);
		frame.getContentPane().add(panel2, "Center"); //adds the panel to the frame in the center most point
		
		JPanel panelBttm = new JPanel(); //creates another panel which is used for the bottom part of the frame
		JPanel panel3 = new JPanel(); //creates a subpanel to go inside the bottom panel

		JButton deleteBtn = new JButton("Delete Image"); //creates a button that has the text "Delete Image"
		deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() { //adds a mouse listener to the button, when a certain mouse related action happens it causes something to occur in the program
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
		panel3.add(deleteBtn, "West"); //adds the button the subpanel in the western most point
		panel3.add(Box.createHorizontalStrut(170)); //creates a empty box horizontal gap between the button and the next component
		deleteBtn.addActionListener(getActionListenerForButtonDelFile()); //adds an actionlistener to the delete button, this is created later in this program
		
		
		
		JPanel panelPlay = new JPanel(); //creates another subpanel  
		panelPlay.setPreferredSize(new Dimension(100,40)); //sets the size of this panel, this is much smaller than the rest of the panels or frames
		panelPlay.setLayout(new BorderLayout()); //sets the layout of the panel as borderlayout
		JLabel timeStart = new JLabel ("00:00"); //creates a label that has a string text - this could be changed to be more linked with the song time 
		panelPlay.add(timeStart, "West"); //adds the label to the subpanel at the western most point
		
		ImageIcon playBtn = new ImageIcon(new ImageIcon("play.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); ; //this creates a play button that contains a play sybmol similar to how the home symbol was created before  
		JButton play = new JButton(playBtn); //creates the play button and adds the icon into it 
		/*
		 * the below lines first set the opaquness of the button as false so it can be seen, then sets the content area filled to false and sets the border painted to
		 * false too, this all allows the button to be seen, the prefferedsize of the button is then determined, which can be altered, before the button is added to the subpanel
		 * at the center most point
		 */
		play.setOpaque(false); 
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.setPreferredSize(new Dimension(30,30));
		panelPlay.add(play, "Center");
		
		
		JLabel timeEnd = new JLabel ("00:00"); //this a string that shows the end time of the file, it is currently just a placeholder string - it could be altered with the file time length 
		panelPlay.add(timeEnd, "East"); //adds the label to the subpanel at the eastern most point
		
		JLabel trackName = new JLabel("Track Name Here"); //creates a new jlabel and then adds it to the subpanel at the southern most point 
		panelPlay.add(trackName, "South");

		
		//frame.getContentPane().add(panelPlay);
		
		
		JPanel panel4 = new JPanel(); //creates a fourth new panel
		JButton addBtn = new JButton("Add Image"); //creates a jbutton then adds it to the panel at the eastern most point
		panel4.add(addBtn, "East");
		addBtn.addActionListener(fileManager.getActionListenerForButtonAddIFile()); //adds an actionlistener to the button that is created within the filemanager class
		panel4.setBorder(BorderFactory.createEmptyBorder(0,120, 0, 0)); //sets the border of the fourth panel as an empty clear border
		/*
		 * adds the subpanels to the bottom panel at the western most, centermost and eastern most points respectively 
		 */
		panelBttm.add(panel3, "West"); 
		panelBttm.add(panelPlay, "Center");
		panelBttm.add(panel4, "East");
		
		frame.getContentPane().add(panelBttm, "South"); //adds the bottom panel to the frame at the southern most point 
		
		frame.setVisible(true); //sets the frame as visible when the code is ran
		
		table.addMouseListener(new java.awt.event.MouseAdapter() { //adds a mouse listener to the table, this only happens when a certain mouse action happens
			@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) { //the action only takes place when the mouse is clicked within the table
				rowNumber = table.rowAtPoint(evt.getPoint()); //gets the number of the row selected and assigns it to the rowNumber variable
				System.out.println(rowNumber); //test prints out the rownumber 
				String selectedCellValue = (String) table.getValueAt(table.getSelectedRow() , table.getSelectedColumn()); //gets the value of the selected row and colmn, a single cell
				System.out.println("You have selected: " + selectedCellValue); //test informs the user what cell they have selected
				//need to set value of the bottom jlabel here
				trackName.setText(selectedCellValue); //sets the text of the trackname label which is at the bottom of the frame to the name of the file selected
				int selectedRow = table.getSelectedRow(); //creates a selectedRow variable and assigns the selected row to it
				try {
					Desktop.getDesktop().open(new File((String) table.getValueAt(selectedRow, 4))); //opens the file in their default media viewer, needed to add the file path into the table for this to work, gets the file path from the table and runs the file using that 
				} catch (IOException e) {
					e.printStackTrace(); //this is what happens if an error occurs 
				}
			
			}
			
		});
	}
	public ActionListener getActionListenerForButtonDelFile() 
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { //only happens when the button or the component is clicked
		
							DefaultTableModel defaultModelI = (DefaultTableModel) table.getModel(); //gets the model of the table and sets it to the defaultModelI variable which has been recreated
							defaultModelI.removeRow(table.getSelectedRow()); //removes the row from the table that has been selected before the delete button is pressed
			}	
			
		};
		return ac; //returns the variable previously created - stops the method from being void
	}
	
	public ActionListener saveToFile() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try { //tries the below code, if an error occurs the catch code is ran
					File saveFile = new File("TestI.txt"); //creates a new file
					if (!saveFile.exists()) { //ithe file doesnt exist then the program will create the file for the user 
						saveFile.createNewFile();
					}
					/*
					 * the below lines get the absolute file using filewriter, and then create a bufferedwriter to write the file to the text file 
					 */
					FileWriter fwrite = new FileWriter(saveFile.getAbsoluteFile());
					BufferedWriter brw = new BufferedWriter(fwrite);
					
					for (int i = 0; i<table.getRowCount(); i++) { //continues until it gets to the end of the ampunt of rows in the table
						for(int p = 0; p<table.getColumnCount(); p++) { //this code continues until it gets to the end of the amount of columns in the table
							brw.write((String)table.getModel().getValueAt(i, p)+" "); //writes to the text file, the value at the row and column in the table
						}
						brw.write("\n__________\n"); //writes each line of the table as a new line in the txt file
					}
					/*
					 * the below lines close the bufferedwriter, the filewriter and show a message to inform the user that the table has been saved
					 */
					brw.close();
					fwrite.close();
					JOptionPane.showMessageDialog(null, "The table has been saved to TestI.txt");
					
					
				}catch(Exception ex) {
					ex.printStackTrace(); //occurs when an error is ran into 
				}
			}
		};
		return ac;
	}


}
