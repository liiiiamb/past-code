	package util.GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.Manager.FileManager;
import util.Manager.PlaylistEditor;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;
import util.Manager.WatchVideo;

import javax.swing.table.DefaultTableModel;
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
public class PlaylistScreen extends FileManager{
	/*
	 * the below 3lines allow fileManager, playlistEditor and videoScreen to access and use this class and vice versa
	 */
	static FileManager fileManager = null;
	PlaylistEditor playlistEditor = null;
	static VideoScreen videoScreen = null;
	
	public static Object[][] data = { {"Playlist 1", fileManager.getPlay1ItemNum()}, {"Playlist 2", fileManager.getPlay1ItemNum()}, {"Playlist 3", fileManager.getPlay1ItemNum()}}; //creates a new object array - this contains the names of the playlists and how many items are in each 
	public static String columnNames[]= {"Playlist Name:", "Amount of Files:"};  //creates a string array which contains the names of the columns in the table 
	DefaultTableModel defaultModelPlay = new DefaultTableModel(); //creates a defaultModel which is used and altered in both this program and the fileManager class
	/*
	 * the below 3 lines create 3 seperate jtables which are linked with playlist 1, 2 and 3 respectively 
	 */
	JTable play1Table = new JTable();
	JTable play2Table = new JTable();
	JTable play3Table = new JTable();
	/*
	 * the below block of code creates object arrays which contain the file names and types, string arrays which contain the column names 
	 * and then creates a new defaulttablemodel object for each playlist. 
	 */
	public static Object[][] dataP1 = { {p1N, p1T} };
	public static String[] columnNamesP1 = {"File Name: ", "File Type:"};
	DefaultTableModel defaultModelPlay1 = new DefaultTableModel();
	public static Object[][] dataP2 = { {p1N, p1T} };
	public static String[] columnNamesP2 = {"File Name: ", "File Type:"};
	DefaultTableModel defaultModelPlay2 = new DefaultTableModel ();
	public static Object[][] dataP3 = { {p1N, p1T} };
	public static String[] columnNamesP3 = {"File Name: ", "File Type:"};
	DefaultTableModel defaultModelPlay3 = new DefaultTableModel ();
	
	JTable table = new JTable(); //creates a new jtable object - allows jtables to be created later in the program without defining them as a jtable 
	ArrayList<String> playlistDetails = new ArrayList<String>(); //creates an arraylist which contains strings which are the details of each playlist 
	public PlaylistScreen (FileManager me)
	{
		this.fileManager = me;
	}
	
	public PlaylistScreen (PlaylistEditor pe)
	{
		this.playlistEditor = pe;
	}
	
	public PlaylistScreen (VideoScreen ve) {
		this.videoScreen = ve;
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
		
		JFrame frame = new JFrame ("My Playlists"); //creates a frame and defines what happens when the user closes the screen, as well as the size of the frame. 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		/*
		 * creates a blank panel and assigns a gridlayout to it which contains 1 row and 3 sections - there is a gap of 20 between each component
		 */
		JPanel panel = new JPanel(); 
		GridLayout layout = new GridLayout(1,3);
		layout.setHgap(20);
		fileManager = new FileManager(); //allows filemanager methods and variables to be used in this class
		playlistEditor = new PlaylistEditor(); //allows playlisteditor methods and variables to be used in this class
		
		
		ImageIcon home_icon = new ImageIcon(new ImageIcon("home.PNG").getImage().getScaledInstance(20,20, Image.SCALE_DEFAULT)); //sets the home icon of the home button and defines its scaled size 
		JButton home = new JButton(home_icon); //creates the home button and assigns the imageicon to it instead of text 
		home.addActionListener(fileManager.getActionListenerForButtonHome()); //adds an actionlistener to the home button
		home.setPreferredSize(new Dimension(20,20)); //sets the preferredsize of the home button - this can be altered by other components 
		panel.add(home); //adds the home button to the panel
		panel.add(Box.createHorizontalStrut(200)); //creates a horizontal gap between the home button and the next component 
		
		JLabel title = new JLabel ("My Playlists:"); //creates a label which is used as the title for the screen, the font and font size are defined on the following line 
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));
		panel.add(title); //adds the tile label to the panel
		panel.add(Box.createHorizontalStrut(200));
		
		JTextArea search = new JTextArea(0,10); //creates a jtextarea which has a default height size and a +10 to the default width size 
		panel.add(search); //adds the textarea to the panel 
		search.addKeyListener(this.fileManager.getKeyListenerForSearch()); //adds a key listener to the text area - defined in the filemanager class
		frame.getContentPane().add(panel, "North"); //adds the panel to the frame at the northern most point
		
		
	
		JPanel panel2 = new JPanel(); //creates a second blank panel
;
		FileManager.getDefaultModelPlay(); //gets the defaultmodel from the filemanager class
		defaultModelPlay = FileManager.getDefaultModelPlay(); //assigns a new defaultmodel object to the defaultmodel from the filemanager class allowing it to be used here 
		table = new JTable(defaultModelPlay); //creates a new jtable and assigns the defaultmodel to it 
		JScrollPane tableScroller = new JScrollPane(table); //adds a scroller to the table - only shows when there are a certain number of items
		panel2.add(tableScroller);
		frame.getContentPane().add(panel2, "Center");
		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
				int rowNumber = table.rowAtPoint(evt.getPoint()); //gets the row number when a certain row is clicked 
				if (rowNumber == 0){ //only happens if the first row is clicked - this row contains playlist 1 and its number of items 
					JFrame frame = new JFrame(); //brings up an external jframe - shows everything in playlist 1 
					frame.setSize(500,500); //sets size of the new frame
					JPanel panel = new JPanel(); //creates a panel within the frame
					JLabel title = new JLabel("Playlist 1:"); //creates a label title that goes at the top of the frame
					panel.add(title); //adds the label to the created panel
					frame.getContentPane().add(panel, "North"); //adds the panel to the frame at the northen most point 
					JPanel panel2 = new JPanel(); //creates a second panel
					fileManager.getDefaultModelPlay1();
					defaultModelPlay1 = FileManager.getDefaultModelPlay1();
				    play1Table = new JTable(defaultModelPlay1);  //creates a new jtable that has the defaultmodel of defaultModelPlay1
				    JScrollPane scroller = new JScrollPane(play1Table); //creates a scroller for the table
					panel2.add(scroller); //adds the table to the second panel
					frame.getContentPane().add(panel2, "Center"); //adds the second panel to the frame in the center
					JPanel panel3 = new JPanel(); //creates a third panel
					JButton remove = new JButton("Remove"); //creates a button that has the text "Remove" on it
					remove.addActionListener(getActionListenerForButtonDelFileFromPlay1()); //adds an actionlistener to the remove button - when it is clicked the code at the bottom of the program runs
					panel3.add(remove); //adds the button to the third panel
					frame.getContentPane().add(panel3, "South"); //adds the third panel to the frame at the southern most point
					frame.setVisible(true); //sets the frame as visible 
	
					
				}
				else if (rowNumber == 1) { //only happens if the second row is clicked - contains playlist 2
					JFrame frame = new JFrame(); //brings up an external jframe - shows everything in playlist 2
					frame.setSize(500,500); //sets size of the new frame
					JPanel panel = new JPanel(); //creates a panel within the frame
					JLabel title = new JLabel("Playlist 2:"); //creates a label title that goes at the top of the frame
					panel.add(title); //adds the label to the created panel
					frame.getContentPane().add(panel, "North"); //adds the panel to the frame at the northen most point 
					JPanel panel2 = new JPanel(); //creates a second panle
					fileManager.getDefaultModelPlay2();
					defaultModelPlay2 = FileManager.getDefaultModelPlay2();
				    play2Table = new JTable(defaultModelPlay2);  //creates a new jtable that has the defaultmodel of defaultModelPlay2
				    JScrollPane scroller = new JScrollPane(play2Table); //creates a scroller for the table
					panel2.add(scroller); //adds the table to the second panel
					frame.getContentPane().add(panel2, "Center"); //adds the second panel to the frame in the center
					JPanel panel3 = new JPanel(); //creates a third panel
					JButton remove = new JButton("Remove"); //creates a button that has the text "Remove" on it
					remove.addActionListener(getActionListenerForButtonDelFileFromPlay2()); //adds an actionlistener to the remove button - when it is clicked the code at the bottom of the program runs
					panel3.add(remove); //adds the button to the third panel
					frame.getContentPane().add(panel3, "South"); //adds the third panel to the frame at the southern most point
					frame.setVisible(true); 
				}
				else if (rowNumber == 2) { //only happens if the third row is clicked - contains playlist 3
					JFrame frame = new JFrame(); //brings up an external jframe - shows everything in playlist 3
					frame.setSize(500,500); //sets size of the new frame
					JPanel panel = new JPanel(); //creates a panel within the frame
					JLabel title = new JLabel("Playlist 3:"); //creates a label title that goes at the top of the frame
					panel.add(title); //adds the label to the created panel
					frame.getContentPane().add(panel, "North"); //adds the panel to the frame at the northen most point 
					JPanel panel2 = new JPanel(); //creates a second panle
					fileManager.getDefaultModelPlay3();
					defaultModelPlay3 = FileManager.getDefaultModelPlay3();
				    play3Table = new JTable(defaultModelPlay3);  //creates a new jtable that has the defaultmodel of defaultModelPlay3
				    JScrollPane scroller = new JScrollPane(play3Table); //creates a scroller for the table
					panel2.add(scroller); //adds the table to the second panel
					frame.getContentPane().add(panel2, "Center"); //adds the second panel to the frame in the center
					JPanel panel3 = new JPanel(); //creates a third panel
					JButton remove = new JButton("Remove"); //creates a button that has the text "Remove" on it
					remove.addActionListener(getActionListenerForButtonDelFileFromPlay3()); //adds an actionlistener to the remove button - when it is clicked the code at the bottom of the program runs
					panel3.add(remove); //adds the button to the third panel
					frame.getContentPane().add(panel3, "South"); //adds the third panel to the frame at the southern most point
					frame.setVisible(true);  //sets the frame as visible 
				}
			
			}
			
		});
		
		JPanel panelBttm = new JPanel(); //creates a main panel for the bottom of the frame that other panels are added to 
		JPanel panel3 = new JPanel(); //creates a new blank panel
		JButton deleteBtn = new JButton("Remove");
		deleteBtn.addActionListener(getActionListenerForButtonDelPlay());
		panel3.add(deleteBtn, "West");
		panel3.add(Box.createHorizontalStrut(170));
		
		
		
		
		
		JPanel panel4 = new JPanel();
		JButton addBtn = new JButton((new AbstractAction("Create") {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*
				 * creates a new frame which allows the user to create a new playlist 
				 */
				JFrame frame = new JFrame ("Create Playlist");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(500,300);
				
				JPanel panel = new JPanel();
				JLabel title = new JLabel("Create Playlist:");
				title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));
				panel.add(title, "Center");
				
				frame.getContentPane().add(panel, "North");		
				
				
				JPanel panel2 = new JPanel(new GridBagLayout());
				JLabel text = new JLabel("New Playlist:");
				text.setFont(new Font("Rockwell Bold", Font.PLAIN, 13));

				
				JTextArea namePlaylist = new JTextArea(0,20);
				JButton playlistConfirm = new JButton (new AbstractAction("Create") { //if the user clicks the create button within the new frame the following code runs 

					@Override
					public void actionPerformed(ActionEvent e) {
						String nameOfPlay = namePlaylist.getText(); //this should get the value entered into the text field - the name the user chooses for the playlist
						System.out.println("The name of the playlist is: " + nameOfPlay); //test prints out the name of the playlist
						fileManager.getPlay1ItemNum();  //gets the amount of items in playlist 1 from filemanager
						String strGet1Items = String.valueOf(fileManager.getPlay1ItemNum()); //converts the amount of items to a string 
						System.out.println("Testing: " + strGet1Items); //test prints out the amount of items from playlist 1 
						System.out.println("There are " + strGet1Items + "in Playlist 1"); //test prints out the number of items in the playlist.
						String[] newArray = new String[] {nameOfPlay, strGet1Items}; //creates a new string array that assigns the name of the playlist and its items
						defaultModelPlay.addRow(newArray); //adds the newly created array into the table shown to the user.
						
					}
					
					
				});
				
				
				GridBagConstraints c = new GridBagConstraints();
				
				c.gridx = 0;
				c.gridy = 0;
				panel2.add(text, c);
				panel2.add(Box.createVerticalStrut(40));
				c.gridy = 2; //moves the next component with c attached to it, to a lower position than the above component 
				c.anchor=GridBagConstraints.CENTER;
				panel2.add(namePlaylist, c);
				panel2.add(Box.createHorizontalStrut(100));
				
				GridBagConstraints d = new GridBagConstraints();
				d.gridx=5; //sets a pre-determined location for the next component with d attached to it. 
				d.gridy=2;
				d.anchor=GridBagConstraints.EAST;
				panel2.add(playlistConfirm, d);
				frame.getContentPane().add(panel2, "Center"); //adds the panel to the center of the frame 
				
				JPanel panel3 = new JPanel();
				panel3.add(Box.createHorizontalStrut(300));
				JButton cancel = new JButton("Cancel");
				cancel.addActionListener(fileManager.getActionListenerForButtonCancel()); //assigns the actionlistener for the cancel button from the filemanager class

				
				panel3.add(cancel);
			
				
				frame.getContentPane().add(panel3, "South");
				
				
				frame.setVisible(true); //sets the frame as visible 
				
			}
			
		}));
	

		panel4.add(addBtn, "East");
		panel4.setBorder(BorderFactory.createEmptyBorder(0,120, 0, 0)); //creates an empty border around the panel, ensuring that no components are too close to it 
		panelBttm.add(panel3, "West");
	
		panelBttm.add(panel4, "East");
		
		frame.getContentPane().add(panelBttm, "South");
		
		frame.setVisible(true);
	}
	public ActionListener getActionListenerForButtonDelFileFromPlay1()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
							
							
							DefaultTableModel defaultModelPlay1 = (DefaultTableModel) play1Table.getModel(); //gets the current model of the playlist 1 table and assigns it to the model that is already passed into the table 
							defaultModelPlay1.removeRow(play1Table.getSelectedRow()); //removes the selected row upon clicking the remove button
							int numOf1Items = play1Table.getRowCount(); //gets the number of rows within the playlist - could add code here to update it back into the table - would need to remove every row and re-add which would add a lot of code
							System.out.println("There are " + numOf1Items + "in playlist 1"); //test prints out the number of items after a row has been removed 
			}	
			
		};
		return ac;
	}
	public ActionListener getActionListenerForButtonDelFileFromPlay2()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
		
	
							DefaultTableModel defaultModelPlay2 = (DefaultTableModel) play2Table.getModel(); //the same as the above code which deletes a file from playlist 1 
							defaultModelPlay2.removeRow(play2Table.getSelectedRow()); //removes the selected row upon clicking the remove button
							int numOf2Items = play2Table.getRowCount();
							System.out.println("There are " + numOf2Items + "in playlist 2");
			}	
			
		};
		return ac;
	}
	public ActionListener getActionListenerForButtonDelFileFromPlay3()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
							// runs the same as the code for when files are removed from playlist 1.
							DefaultTableModel defaultModelPlay3 = (DefaultTableModel) play3Table.getModel();
							defaultModelPlay3.removeRow(play3Table.getSelectedRow()); //removes the selected row upon clicking the remove button
							int numOf3Items = play3Table.getRowCount();
							System.out.println("There are " + numOf3Items + "in playlist 2");
			}	
			
		};
		return ac;
	}

	public ActionListener getActionListenerForButtonDelPlay() 
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { //only happens when the button or the component is clicked
		
							DefaultTableModel defaultModel = (DefaultTableModel) table.getModel(); //gets the model of the table and sets it to the defaultModelI variable which has been recreated
							defaultModel.removeRow(table.getSelectedRow()); //removes the row from the table that has been selected before the delete button is pressed
			}	
			
		};
		return ac; //returns the variable previously created - stops the method from being void
	}

}
