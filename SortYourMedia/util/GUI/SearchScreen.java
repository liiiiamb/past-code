package util.GUI;
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
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import util.Manager.FileManager;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;
import util.Manager.WatchVideo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class SearchScreen {
	/*
	 * the below 3 lines create string variables which are going to contain
	 * the name of the file, the type of the file and the last modified
	 * date or time of the file 
	 */
	public static String fileName;
	public static String fileType;
	public static String fileLast;
	public static Object[][] data = { {fileName, fileType, fileLast} }; //creates an object array which will contain the file name, its type and last modified date or time 
	public static String[] columnNames= {"File Name:", "File Type:", "File Location:"}; //creates a string array which contains the names of each column
	protected static DefaultTableModel defaultModel = new DefaultTableModel(data, columnNames); //creates a default model that will be passed into the jtable
	JTable table = new JTable(defaultModel); //creates a jtable which passes through the default model - this contains the data object array and the column names 
	public static JTextField search = new JTextField(20); //creates a text field which has a default width of 20
	FileManager fileManager = null; //allows filemanager to access this class and vice versa
	public static String[] recFiles = new String[3]; //creates a string array which allows for 4 items to be added 
	public static String pathname_type; //creates a string variable 
	public static String filePath;
	public SearchScreen (FileManager me) 
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
		JFrame frame = new JFrame ("Search Directory");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);
		
		JPanel panel = new JPanel();
		GridLayout layout = new GridLayout(1,3); //assigns a gridlayout which has a single row and 3 sections within that row
		layout.setHgap(20); //creates a gap of 20 between each component in the layout 
		
		
		JButton chooseDir = new JButton("Choose Directory"); //creates a button that has the text "Choose Directory"
		chooseDir.setPreferredSize(new Dimension(200,20)); //sets the preferred size of the button to a width of 200 and a height of 20, to fit the whole text in
		panel.add(chooseDir, "Center");
		panel.add(Box.createHorizontalStrut(170));
		chooseDir.addActionListener(getActionListenerForDirectory());
		frame.getContentPane().add(panel, "North");
		

		
		
		JPanel panel2 = new JPanel();

		
		table = new JTable(defaultModel);
		
		JScrollPane tableScroller = new JScrollPane(table);
		panel2.add(tableScroller);
		frame.getContentPane().add(panel2, "Center");
		
		frame.setVisible(true);

	}

	public ActionListener getActionListenerForDirectory() { //creates an actionlistener for the directory button
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameReturn = null; //creates an empty nameReturn string that can be updated 
				JFileChooser chooser = new JFileChooser(); //creates a new JFileChooser
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //only allows the user to choose directories with the file chooser
				int chosenDir = chooser.showOpenDialog(null); //creates a new int variable and sets the show open dialog of the jfilechooser to it
				if (chosenDir == JFileChooser.APPROVE_OPTION) { //if the user clicks on open then the below code is run
					/*
					 * the next two lines of code creates a jtextfield and assigns the text entered into it into a string variable																																																																																																																																									
					 */
					JTextField search = new JTextField(20);
					String nameEntered = search.getText();
					String[] pathnames; //creates an empty string array
					String chosenDirectory = chooser.getCurrentDirectory().toString(); //gets the directory chosen by the user converts it to a string and stores it in a string variable
					File dir = new File(chosenDirectory); //creates a new file object which contains the chosen directory
					File[] files = dir.listFiles(); //creates a file array - this lists all files within the directory 
					for (File file : files) { //this loop continues until all files within the directory have been listed
						String fileName = file.getName(); //gets the name of the file
						String fileType = fileName.substring(fileName.length() - 4); //gets the type of the file by just taking the last 4 characters of the string - sometimes filenames dont contain their type
						filePath = file.getAbsolutePath();
						System.out.println("The file path is " + filePath);
						/*
						 * the below 3 lines are test prints to test if the correct file name, type and last modified date are obtained 
						 */
						System.out.println("The name of the file is: " + fileName); 
						System.out.println("The file type is: " + fileType);
						System.out.println("The file was last modified: " + fileLast);
						recFiles[0] = fileName; //assigns the file name to the first item in the recFiles array
						recFiles[1] = fileType; //assigns the file type to the second item in the recFiles array
						recFiles[2] = fileLast; //assigns the file last modified date and time to the third item in the recFiles array
						defaultModel.addRow(recFiles); //adds a row to the table created in this class that contains all of the details about all of the files in the directory
						System.out.println("The array is" + Arrays.toString(recFiles)); //test print - tests if array items are added correctly 
						
						
						
					}
					
					
				}
						}
						
					};
		return ac;
	}
	
	
	
	


}
