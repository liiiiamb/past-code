package util.Manager;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import util.GUI.ConfirmDeletion;
//import util.GUI.ConfirmRename;
import util.GUI.CreatePlaylistScreen;
import util.GUI.HomeScreen;
import util.GUI.ImageScreen;
import util.GUI.MusicScreen;
//import util.GUI.NameFileScreen;
import util.GUI.PickFile;
import util.GUI.PlaylistScreen;
import util.GUI.SearchScreen;
import util.GUI.VideoScreen;

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
public class FileManager{
	/*
	 * the below 9 lines of code first create string variables that are going to be altered and used later in file and then imports the 
	 * different screens that FileManager is used in/uses. 
	 */
	public String fileDetails;
	private String fileLibrary;
	private MusicScreen musicScreen;
	private ImageScreen imageScreen;
	private VideoScreen videoScreen;
	public PlaylistScreen playlistScreen;
	private HomeScreen homeScreen;
	public ConfirmDeletion confirmDeletion;
	private PickFile pickFile;
	private SearchScreen searchScreen;

	private CreatePlaylistScreen createPlaylistScreen;
	//class variables for video screen 
	public static String s;
	protected static String Play1S;
	protected static String Play2S;
	protected static String Play3S;
	protected static String fLengthS;
	static long fLength;
	protected static String fileT;
	protected static String fileLM;
	public String[] videoItems;
	public static String filePathS;
	public static String selFile;
	public static String filePath;
	public static String filePathI;
	public static String filePathM;
	public static String pathH;
	public static int play1ItemNum;
	public static String[] playlistNames = new String[25]; //limit to amount of songs that can be added
	/*
	 * the 4 lines before create arraylist and list objects to 
	 * put items added into - this is used as a way of checking if a 
	 * duplicate file has been uploaded 
	 */
	public static ArrayList<String> playlist1Items = new ArrayList<String>(); 
	public static ArrayList<String> playlist2Items = new ArrayList<String>(); 
	public static ArrayList<String> playlist3Items = new ArrayList<String>(); 
	public static List<String> videoItemCheck = new ArrayList<String>();
	//class variables for music screen
	protected static String sM;
	protected static String fLengthSM;
	static long fLengthM;
	protected static String fileTM;
	protected static String fileLMM;
	public static String filePathSM;

	public static String pathHM;
	public static String p1N; 
	public static String p1T;
	public static String p2N; 
	public static String p2T;
	public static String p3N; 
	public static String p3T;
	public static File file;

/*
 * The below block of code is where the arrays are created that are used to fill 
 * up the tables, files, filesM and filesI stores the information about the file, which is 
 * also stored in the respective data object.
 */
	public static String[] files = new String[5];
	public static String[] filesM = new String[5];
	public static String[] filesI = new String[5];
	public static Object[][] data = { {s, fLengthS, fileT, fileLM} };
	public static Object[][] dataM = { {sM, fLengthSM, fileTM, fileLMM} };
	public static Object[][] dataP = { {"Playlist 1:", Play1S}, {"Playlist 2:", Play2S}, {"Playlist 3:", Play3S}};
	public static Object[][] dataP1 = { {p1N, p1T} };
	public static Object[][] dataP2 = { {p2N, p2T} };
	public static Object[][] dataP3 = { {p3N, p3T} };
	
	public static String[] columnNames = {"Name:", "File Size:", "File Type:", "Last Modified", "File Path:"};
	public static String[] columnNamesP = {"Playlist Name: ", "Amount of Items:"};
	public static String[] columnNamesP1 = {"File Name: ", "File Type:"};
	public static String[] columnNamesP2 = {"File Name: ", "File Type:"};
	public static String[] columnNamesP3 = {"File Name: ", "File Type:"};
	static String ac;
	
	//class variables for image screen
	protected static String sI;
	protected static String fLengthSI;
	static long fLengthI;
	protected static String fileTI;
	protected static String fileLMI;
	public static String filePathSI;
	//public static String selFile;
	public static String pathHI;
	public static Object[][] dataI = { {sI, fLengthSI, fileTI, fileLMI} };
	/*
	 * the below block creates the defaulttablemodels that are used to fill the tables in the program
	 * all of the objects and their variables are currently empty at this part of the class, they are just 
	 * used as a template and a way to fill the model, the variables and objects are added to later in this
	 * class.
	 */
	protected static DefaultTableModel defaultModel = new DefaultTableModel(data, columnNames);
	protected static DefaultTableModel defaultModelI = new DefaultTableModel(dataI, columnNames);
	protected static DefaultTableModel defaultModelM = new DefaultTableModel(dataM, columnNames);
	protected static DefaultTableModel defaultModelPlay = new DefaultTableModel(dataP, columnNamesP);
	public static DefaultTableModel defaultModelPlay1 = new DefaultTableModel (dataP1, columnNamesP1);
	public static DefaultTableModel defaultModelPlay2 = new DefaultTableModel (dataP2, columnNamesP2);
	public static DefaultTableModel defaultModelPlay3 = new DefaultTableModel (dataP3, columnNamesP3);
	//setting the playlist table array values 
	public static String[] newArr2 = new String[2];
	public static String[] newArr = new String[2];
	public static String[] newArr3 = new String[2];
	
	protected JLabel label;
	JTable play1Table = new JTable(); //creates a table for later use
	
	private JTable table;
	public FileManager() {};
	public FileManager (PickFile me)
	{
		this.pickFile = me;
	}
	public FileManager (VideoScreen vc) {
		this.videoScreen = vc;
	}

	public void searchLibrary() {
		
	}
	
	public void addToPlaylist() {
		
	}
	
	public void deleteFromPlaylist() {
		
	}

	public void playFile() {
		
	}
	
	public void deleteFile() {
		
	}
	
	public ActionListener getActionListenerForButtonM()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				musicScreen = new MusicScreen(FileManager.this); //imports the music screen and creates a new instance to be used by filemanager
				musicScreen.main(); //runs the main method of the music screen - shows the music screen.
			}
		};
		return ac;
	}
	
	public ActionListener getActionListenerForButtonAP()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				playlistScreen = new PlaylistScreen(FileManager.this); //imports the playlist screen
				playlistScreen.main(); //runs the main method of the playlist screen
			}
		};
		return ac;
	}
	
	public ActionListener getActionListenerForButtonI() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imageScreen = new ImageScreen(FileManager.this); //imports the image screen
				imageScreen.main(); //runs the code in the main method of the image screen 
			}
		};
		return ac;
	}
	
	public ActionListener getActionListenerForButtonV() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				videoScreen = new VideoScreen(FileManager.this); //imports the video screen
				videoScreen.main(); //runs the code in the main method of the video screen. 
			}
		};
		return ac;
	}


	
	public boolean alreadyExists(JTable table, Object[] input) { //tries to check if a file already exists 
		/*
		 * two int variables are created that get the row amount and 
		 * column amount of the table. 
		 */
		int rowNums = table.getRowCount();
		int colNums = table.getColumnCount();
		
		String currentInput = ""; //creates an empty string variable 
		for (Object p : input) { //checks through every object inside the input array 
			String q = p.toString(); //creates a string and assigns the objects that were just seperated into a string 
			currentInput = currentInput + " " + q; //assigns the earlier created empty string variable to itself, plus an empty space, plus the seperated objects
		}
		
		for (int i = 0; i<rowNums; i++) { //carries on as long as rows still exist in the table 
			String rowInput = ""; //creates an empty row input string variable 
			for (int g = 0; g<colNums; g++) //carries on as lomng as columns still exist in the table 
				rowInput = rowInput + " " + table.getValueAt(i, g).toString(); //assigns the rowinput variable to itself, plus an empty space, plus the value in a specific cell (which is turned into a string)
			if (rowInput.equalsIgnoreCase(currentInput)) { //checks if the file already exists and matches the file the user chooses 
				System.out.println("The file already exists"); //test - prints out that the file already exists within the table 
				return true; //needs to return true as it is a boolean type 
			}
			else { //if the file doesnt match a file already in the table then the console prints out that the file was uploaded. 
				System.out.println("File uploaded");
			}
		}
		return false;
	}
	

	
	public ActionListener getActionListenerForButtonHome() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				homeScreen = new HomeScreen(FileManager.this);
				homeScreen.main(null); //runs the main method from the home screen 
			}
		};
		return ac;
	}
	
	
	
	/*public static void watchDir() { //this block of code attempts to watch the directory for any sort of changes and then adds a row to the table with that new file added or the file changed 
		try(WatchService service = FileSystems.getDefault().newWatchService()){ //creates a watchservice object 
			Map<WatchKey, Path> keyMap = new HashMap<>(); //creates a hashmap for the key that maps the watchkey and the path of the file 
			Path path = Paths.get("Image Files");  //gets the path of a certain folder - in this case the chosen folder that is watched is Image Files
			keyMap.put(path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY), path); //creates watch events, these are what will cause an action to happen - whether an a new file was created, deleted or modified 
		WatchKey watchKey; //creates a watchkey object 
		
		
		do { //runs the below code blockm
			watchKey = service.take(); //assigns the watchkey variable just created to service.take which waits for the next completed result from the watcher - such as the next file created and returns that. 
			Path eventDir = keyMap.get(watchKey); //creates another path variable which gets the key that has certain file events related to it 
			
			for (WatchEvent<?> event : watchKey.pollEvents()) { //for each key event - entry create, entry delete and entry modify the below code runs
				WatchEvent.Kind<?> kind = event.kind(); //it first gets the kind of event - whether it is create delete or modify 
				Path eventPath = (Path)event.context(); //then creates a path variable which gets the context of the event, e.g. where the file was created
				System.out.println(eventDir + ": " + kind + ": " + eventPath); //test prints out the path then the kind of event then where the file was created
				String[] newFileChange = new String[5]; //creates a new string array which will hold the new file details 
				String eventPathToS = eventPath.toString(); //converts the path where the event happens to a string 
				newFileChange[0] = eventPathToS; //assigns the path of the event to the first item in the array 
				/*
				 * fills up the rest of the array with test strings - this code doesn't work in the correct way so these 
				 * are used to test if it has been fixed 
				 */
				/*newFileChange[1] = "test1";
				newFileChange[2] = "test2";
				newFileChange[3] = "test3";
				newFileChange[4] = "test4";
				
				defaultModel.addRow(newFileChange); //adds a row to the table when a new file has been created 
			}
			
		} while (watchKey.reset()); //resets the key so the code can run more than once
		} catch(Exception e) {
			e.printStackTrace(); //only happens when an error is ran into 
		}
	}*/
	
	public static void watchVDirectory() throws IOException, InterruptedException {
		Path vFolder = Paths.get("./Video Files");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		vFolder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
		
		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();
			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileNameNew = event.context().toString();
					System.out.println("The new file created is: " + fileNameNew);
				}
			}
			valid = watchKey.reset();
		}while(valid);
	}
	
	
	
	
	public ActionListener getActionListenerAddItemToPlay1() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getS(); //getting the name of the file to add it to the playlist
				System.out.println("The file name is: " + getS()); //test to print out the name of the file
				playlist1Items.add(getS()); //adding the name of the playlist into playlist 1.
				int play1ItemNum = playlist1Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x = new Integer(play1ItemNum); //convers the int value to an integer allowing it to be converted to a string 
				String Play1S = x.toString(); //converts the integer value to a string 
				System.out.println("The playlist has " + play1ItemNum + " item(s) in"); //test print - prints out how many items are in the integer.
				defaultModelPlay.setRowCount(0); //removes all rows in the table for themm to be readded with the new amount of items in each playlist 
				String[] newArr = new String[2];
				newArr[0] = "Playlist 1";
				newArr[1] = Play1S; 
				int play2ItemNum = playlist2Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x2 = new Integer(play2ItemNum);
				String Play2S = x2.toString();
				String[] newArr2 = new String[2];
				newArr2[0] = "Playlist 2";
				newArr2[1] = Play2S;
				int play3ItemNum = playlist3Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x3 = new Integer(play3ItemNum);
				String Play3S = x3.toString();
				String[] newArr3 = new String[2];
				newArr3[0] = "Playlist 3";
				newArr3[1] = Play3S;
				/*
				 * the below 3 lines add all of the rows after they have been modified back into the table at the correct position. it 
				 * is important that they are put back in the same position as when the playlists are opened - that code is based off the row
				 * clicked which opens a specific playlist. 
				 */
				defaultModelPlay.addRow(newArr);
				defaultModelPlay.addRow(newArr2);
				defaultModelPlay.addRow(newArr3);
				playlistScreen = new PlaylistScreen(FileManager.this); //creates a new instance of the playlistScreen class0
				playlistScreen.main(); //runs the main code in the playlist screen - in this case it opens up the playlist screen where all of the playlists can be viewed.
			
				
				String fileType1 = s.substring(s.length() - 4);	 //gets the type of the file by getting the last 4 characters of the file name which usually contains the file type 
				p1N = getS(); //gets the name of the file from a getter within this class and passes it to a string variable 
				p1T = fileType1; //gets the type of the file just created and passes it to a string variable 
				/*
				 * creates a new string array and passes the file name and type string variables just created 
				 */
				String[] play1Array = new String[2];
				play1Array[0] = p1N;
				play1Array[1] = p1T;
				defaultModelPlay1.addRow(play1Array); //adds the row to the playlist 1 table 
				defaultModelPlay1.fireTableDataChanged(); //causes the table to be updated 
			}
		};
		return ac;
	}
	/*
	 * the below two methods are created in the same way as the above method - the only difference is that instead of adding a new row to playlist 1,
	 * they add it playlist 2 and 3, and they update their own playlist item numbers. 
	 */
	public ActionListener getActionListenerAddItemToPlay2() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getS(); //getting the name of the file to add it to the playlist
				System.out.println("The file name is: " + getS()); //test to print out the name of the file
				playlist2Items.add(getS()); //adding the name of the playlist into playlist 1.
				int play2ItemNum = playlist2Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x = new Integer(play2ItemNum);
				String Play2S = x.toString();
				System.out.println("The playlist has " + play2ItemNum + " item(s) in"); //test print - prints out how many items are in the integer.
				defaultModelPlay.setRowCount(0);
				int play1ItemNum = playlist1Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x1 = new Integer(play1ItemNum);
				String Play1S = x1.toString();
				String[] newArr = new String[2];
				newArr[0] = "Playlist 1";
				newArr[1] = Play1S;
				String[] newArr2 = new String[2];
				newArr2[0] = "Playlist 2";
				newArr2[1] = Play2S;
				
				int play3ItemNum = playlist3Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x3 = new Integer(play3ItemNum);
				String Play3S = x3.toString();
				String[] newArr3 = new String[2];
				newArr3[0] = "Playlist 3";
				newArr3[1] = Play3S;
				defaultModelPlay.addRow(newArr);
				defaultModelPlay.addRow(newArr2);
				defaultModelPlay.addRow(newArr3);
				playlistScreen = new PlaylistScreen(FileManager.this); //creates a new instance of the playlistScreen class0
				playlistScreen.main(); //runs the main code in the playlist screen - in this case it opens up the playlist screen where all of the playlists can be viewed.
				
				
				String fileType2 = s.substring(s.length() - 4);	
				p2N = getS();
				p2T = fileType2;		
				String[] play2Array = new String[2];
				play2Array[0] = p2N;
				play2Array[1] = p2T;
				defaultModelPlay2.addRow(play2Array);
				defaultModelPlay2.fireTableDataChanged();
			}
		};
		return ac;
	}
	

	
	public ActionListener getActionListenerAddItemToPlay3() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getS(); //getting the name of the file to add it to the playlist
				System.out.println("The file name is: " + getS()); //test to print out the name of the file
				playlist3Items.add(getS()); //adding the name of the playlist into playlist 1.
				int play3ItemNum = playlist3Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x = new Integer(play3ItemNum);
				String Play3S = x.toString();
				System.out.println("The playlist has " + play3ItemNum + " item(s) in"); //test print - prints out how many items are in the integer.
				defaultModelPlay.setRowCount(0);
				int play1ItemNum = playlist1Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x1 = new Integer(play1ItemNum);
				String Play1S = x1.toString();
				String[] newArr = new String[2];
				newArr[0] = "Playlist 1";
				newArr[1] = Play1S;
				int play2ItemNum = playlist2Items.size(); //creates an integer that holds the number of items in the first playlist - this is updated when new items are added.
				Integer x2 = new Integer(play2ItemNum);
				String Play2S = x2.toString();
				String[] newArr2 = new String[2];
				newArr2[0] = "Playlist 2";
				newArr2[1] = Play2S;
				String[] newArr3 = new String[2];
				newArr3[0] = "Playlist 1";
				newArr3[1] = Play3S;
				defaultModelPlay.addRow(newArr);
				defaultModelPlay.addRow(newArr2);
				defaultModelPlay.addRow(newArr3);
				playlistScreen = new PlaylistScreen(FileManager.this); //creates a new instance of the playlistScreen class0
				playlistScreen.main(); //runs the main code in the playlist screen - in this case it opens up the playlist screen where all of the playlists can be viewed.
				
				String fileType3 = s.substring(s.length() - 4);	
				p3N = getS();
				p3T = fileType3;		
				String[] play3Array = new String[2];
				play3Array[0] = p3N;
				play3Array[1] = p3T;
				defaultModelPlay3.addRow(play3Array);
				defaultModelPlay3.fireTableDataChanged();
			}
			
		};
		return ac;
	}
	
	
	public ActionListener getActionListenerForButtonAddVFile() { //only happens when this is called, its called in both ArrayCreation and VideoScreen
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				pickFile = new PickFile(FileManager.this); 
				pickFile.main(); //runs the main method from the pickfile class. 
				JFileChooser fileChoose = new JFileChooser(); //creates a new jfilechooser for the user to pick a file to upload 
				FileNameExtensionFilter filterV = new FileNameExtensionFilter("MP4","MKV","MOV","WMV","AVI"); //this only allows the most popular video file formats to be shown in the filechooser
				fileChoose.addChoosableFileFilter(filterV);
				JFrame frame = new JFrame ("Videos");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(700,500);
				if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //if the user clicks OPEN on the file chooser the below code runs 
					java.io.File h = fileChoose.getSelectedFile(); //gets the selected file and assigns it to a file variable 
 					String pathH = h.getPath(); //gets the path of the file and assigns it to a string variable 
					File file = new File(pathH); //creates a new file based off the path that has just been obtained
					s = file.getName(); //saves the file name in string s
					System.out.println("The file name is: " + s);
					Path path = Paths.get(pathH); //gets the exact path of the file 
					long fLength = 0; //initalizes a long variable 
					try {
						fLength = Files.size(path); //gets the size of the file within that specific path. 
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					System.out.println("The size of the file is: " + fLength);
					String fLengthS = Long.toString(fLength); //saves the file size in string fLength
					String fileT = s.substring(s.length() - 4); //gets the type of the file by getting the last four characters of the file name 
					System.out.println("The type of the file is: " + fileT); //saves the file type in string fileT
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //gives a format to the last modified date or time
					System.out.println("The file was last modified: " + sdf.format(file.lastModified()));
					String fileLM = sdf.format(file.lastModified()); //saves the last modified time and date in string fileLM
					filePath = file.getAbsolutePath(); //gets the absolute file path so it can be stored in the table and used to open the file
					System.out.println("The file path is " + filePath);
					ArrayList listColV = new ArrayList(); //creates new arraylist that will store all of the names of the files uploaded
					for (int i = 0; i<defaultModel.getRowCount();i++) { //carries on based on the amount of rows - if there are 5 rows this code runs 5 times
						listColV.add(defaultModel.getValueAt(i, 0)); //gets the value in the column, which is the name of the file and adds it to the arraylist
						System.out.println("Test" + listColV); //test prints out the arraylist
						if (listColV.contains(file.getName())) { //checks it the arraylist contains the chosen file 
							System.out.println("The file exists"); //test print
							Object[] choice = {"Yes", "No"}; //creates two choices to go inside the optionpane below
							int a = JOptionPane.showOptionDialog(null, " This file already exists\n" + "Do you still wish to upload?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choice, choice[1]); //creates option pane which two choices, yes and no
							if (a == 0){ //if the user chooses yes the below code happens 
								System.out.println("The file has been uploaded"); //test print
								defaultModel.addRow(files); //adds the chosen file and its information to the table
								JOptionPane.getRootFrame().dispose(); //hides the optionpane. 
								
									
							}
							
							
						}
						else {
							System.out.println("The file does not exist");
						}
					}
					//strings need to be passed through into ArrayCreation - s, fLength, fileT, fileLM 
					/*
					 * assigns the above created variables to different spaces in the files array 
					 */
					files[0] = s;
					files[1] = fLengthS;	
					files[2] = fileT;
					files[3] = fileLM; 
					files[4] = filePath;
					System.out.println(Arrays.toString(files));
					//JTable table = new JTable(model);
					
					//model.addRow(videoFiles);
					videoScreen = new VideoScreen(FileManager.this);
					videoScreen.getTable(); //gets the table and model from videoscreen so filemanager can pass data into it 
					videoScreen.getModel();
					defaultModel.addRow(files);
					/*videoItemCheck.add(Arrays.toString(files)); //adds the item the user uploads into an array so it can be check for duplication
					System.out.println("The current items in the list are: " + videoItemCheck);*/
					
					//boolean check = videoItemCheck.contains(s); //checks if the name of the file uploaded is already within the arraylist 
					
					int rowCount = defaultModel.getRowCount();
					int fileColNum = 0;
					
					/*for (int i= 0; i < rowCount; i++) {
						String cellValue = defaultModel.getValueAt(i, fileColNum).toString();
						if (cellValue.equals(s)) {
							System.out.println("The file already exists");
						}
						System.out.println("The file does not exist - test");
					}*/
					
					/*if(videoItemCheck.contains(s)) {
						System.out.println("The file exists ");
						System.exit(0);
					}
						
					else
						System.out.println("The file does not exist yet");
						defaultModel.addRow(files); //adds the row to the table if it doesnt exist yet 
				
					
					
					/*
					 * gets the file name and type and assigns them to variables 
					 */
					p1N = file.getName();
					p1T = p1N.substring(p1N.length() - 4);
					
				
					
				
					
				}
			}
		};
		return ac;
	}
	
	public void checkForDuplicate() { //this method checks for duplicates - its the same that was used within the add files to video method 
		videoItemCheck.add(Arrays.toString(files)); //adds any files uploaded into an arraylist
		System.out.println("The current items in the list are: " + videoItemCheck);
		
		boolean check = videoItemCheck.contains(s); //checks if the arraylist contains the name of any new files uploaded
		
		if(check) //if the file does exist and the boolean is true run the below code 
			System.out.println("the file already exists ");
		else
			System.out.println("file uploaded");
	}
	
	public ActionListener getActionListenerForSearch() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooseDir = new JFileChooser(); //creates a new jfilechooser 
				chooseDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //only allows the user to select directories 
				if(chooseDir.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { //if the user clicks open the below code runs 
					System.out.println("The chosen directory is " + chooseDir.getCurrentDirectory()); //test - gets the directory the user chooses and prints it out 
				}
			}
		};
		return ac;
	}
	/*
	 * the below two methods - adding files to the image and music screen run the exact same as the above 
	 * method where items are added into the video screen - the only exception is the naming ofthe variables differs 
	 * and the relevant screens table and model is obtained instead so the data can be passed into the correct table. 
	 */
	public ActionListener getActionListenerForButtonAddIFile() { //only happens when this is called, its called in both ArrayCreation and VideoScreen
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pickFile = new PickFile(FileManager.this); 
				pickFile.main();
				JFileChooser fileChoose = new JFileChooser();
				FileNameExtensionFilter filterI = new FileNameExtensionFilter("jpeg","tiff","gif","png","jpg"); //this only allows the most popular image file formats to be shown in the filechooser
				fileChoose.addChoosableFileFilter(filterI);
				JFrame frame = new JFrame ("Videos");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(700,500);
				if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					java.io.File hI = fileChoose.getSelectedFile();
					String pathHI = hI.getPath();
					File fileI = new File(pathHI);
					sI = fileI.getName(); //saves the file name in string s
					System.out.println("The file name is: " + sI);
					Path pathI = Paths.get(pathHI); //gets the exact path of the file 
					long fLengthI = 0; //initalizes a long variable 
					try {
						fLengthI = Files.size(pathI); //gets the size of the file within that specific path. 
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					System.out.println("The size of the file is: " + fLengthI);
					String fLengthSI = Long.toString(fLengthI); //saves the file size in string fLength
					String fileTI = sI.substring(sI.length() - 4);
					System.out.println("The type of the file is: " + fileTI); //saves the file type in string fileT
					SimpleDateFormat sdfI = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					System.out.println("The file was last modified: " + sdfI.format(fileI.lastModified()));
					String fileLMI = sdfI.format(fileI.lastModified()); //saves the last modified time and date in string fileLM
					filePathI = fileI.getAbsolutePath();
					System.out.println("The file path is " + filePathI);
					ArrayList listColI = new ArrayList();
					for (int i = 0; i<defaultModelI.getRowCount();i++) {
						listColI.add(defaultModelI.getValueAt(i, 0));
						System.out.println("Test" + listColI);
						if (listColI.contains(fileI.getName())) {
							System.out.println("The file exists");
							Object[] choice = {"Yes", "No"};
							int a = JOptionPane.showOptionDialog(null, " This file already exists\n" + "Do you still wish to upload?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choice, choice[1]);
							if (a == 0){
								System.out.println("The file has been uploaded");
								defaultModelI.addRow(filesI);
								JOptionPane.getRootFrame().dispose();
									
							}
							
							
						}
						else {
							System.out.println("The file does not exist");
						}
					}
					//strings need to be passed through into ArrayCreation - s, fLength, fileT, fileLM 
					
					filesI[0] = sI;
					filesI[1] = fLengthSI;	
					filesI[2] = fileTI;
					filesI[3] = fileLMI; 
					filesI[4] = filePathI;
					System.out.println(Arrays.toString(filesI));
					
					defaultModelI.addRow(filesI);
					
					
					
					
					
				
					
				
					
				}
			}
		};
		return ac;
	}
	
	public ActionListener getActionListenerForButtonAddMFile() { //only happens when this is called, its called in both ArrayCreation and VideoScreen
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pickFile = new PickFile(FileManager.this); 
				pickFile.main();
				JFileChooser fileChoose = new JFileChooser();
				FileNameExtensionFilter filterM = new FileNameExtensionFilter("wav","mp3","wma","ogg","aac"); //this only allows the most popular music file formats to be shown in the filechooser
				fileChoose.addChoosableFileFilter(filterM);
				JFrame frame = new JFrame ("Videos");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(700,500);
				if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					java.io.File hM = fileChoose.getSelectedFile();
					String pathHM = hM.getPath();
					File fileM = new File(pathHM);
					sM = fileM.getName(); //saves the file name in string s
					System.out.println("The file name is: " + sM);
					Path pathM = Paths.get(pathHM); //gets the exact path of the file 
					long fLengthM = 0; //initalizes a long variable 
					try {
						fLengthM = Files.size(pathM); //gets the size of the file within that specific path. 
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
					System.out.println("The size of the file is: " + fLengthM);
					String fLengthSM = Long.toString(fLengthM); //saves the file size in string fLength
					String fileTM = sM.substring(sM.length() - 4);
					System.out.println("The type of the file is: " + fileTM); //saves the file type in string fileT
					SimpleDateFormat sdfM = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					System.out.println("The file was last modified: " + sdfM.format(fileM.lastModified()));
					String fileLMM = sdfM.format(fileM.lastModified()); //saves the last modified time and date in string fileLM
					filePathM = fileM.getAbsolutePath();
					System.out.println("The file path is " + filePathM);
					ArrayList listColM = new ArrayList();
					for (int i = 0; i<defaultModelM.getRowCount();i++) {
						listColM.add(defaultModelM.getValueAt(i, 0));
						System.out.println("Test" + listColM);
						if (listColM.contains(fileM.getName())) {
							System.out.println("The file exists");
							Object[] choice = {"Yes", "No"};
							int a = JOptionPane.showOptionDialog(null, " This file already exists\n" + "Do you still wish to upload?", null, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choice, choice[1]);
							if (a == 0){
								System.out.println("The file has been uploaded");
								defaultModelM.addRow(filesM);
								JOptionPane.getRootFrame().dispose();
									
							}
							
							
							
						}
						else {
							System.out.println("The file does not exist");
						}
					}
					//strings need to be passed through into ArrayCreation - s, fLength, fileT, fileLM 
					
					filesM[0] = sM;
					filesM[1] = fLengthSM;	
					filesM[2] = fileTM;
					filesM[3] = fileLMM; 
					filesM[4] = filePathM;
					System.out.println(Arrays.toString(filesM));
				
					defaultModelM.addRow(filesM);
					
					
					
					
					
				
					
				
					
				}
			}
		};
		return ac;
	}
	

	public KeyListener getKeyListenerForSearch() {
		KeyListener ke = new KeyListener() {
			public void keyTyped(KeyEvent e) {
				searchScreen = new SearchScreen(FileManager.this);
				searchScreen.main();//runs the code within the search screens main method when a key is typed within the search text area 
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		return ke;
		}
	
	public ActionListener getActionListenerForButtonCancel() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				playlistScreen = new PlaylistScreen(FileManager.this);
				playlistScreen.main();
			}
		};
		return ac;
	}

	/*
	 * the last part of this class creates getters and setters for a number of variables 
	 * to allow them to be passed to and used by other classes within this program. 
	 */
	public static String getS() {
		return s;
	}
	public static void setS(String s) {
		FileManager.s = s;
	}
	public static String getfLengthS() {
		return fLengthS;
	}
	public static void setfLengthS(String fLengthS) {
		FileManager.fLengthS = fLengthS;
	}
	public static long getfLength() {
		return fLength;
	}
	public static void setfLength(long fLength) {
		FileManager.fLength = fLength;
	}
	public static String getFileT() {
		return fileT;
	}
	public static void setFileT(String fileT) {
		FileManager.fileT = fileT;
	}
	public static String getFileLM() {
		return fileLM;
	}
	public static void setFileLM(String fileLM) {
		FileManager.fileLM = fileLM;
	}
	public static String getFilePathS() {
		return filePathS;
	}
	public static void setFilePathS(String filePathS) {
		FileManager.filePathS = filePathS;
	}
	public static Object[][] getData() {
		return data;
	}
	public static void setData(Object[][] data) {
		FileManager.data = data;
	}
	public static String[] getColumnNames() {
		return columnNames;
	}
	public static void setColumnNames(String[] columnNames) {
		FileManager.columnNames = columnNames;
	}
	public static String[] getFiles() {
		return files;
	}
	public static void setFiles(String[] files) {
		FileManager.files = files;
	}
	public static DefaultTableModel getDefaultModel() {
		return defaultModel;
	}
	public void setDefaultModel(DefaultTableModel defaultModel) {
		this.defaultModel = defaultModel;
	}
	public static DefaultTableModel getDefaultModelI() {
		return defaultModelI;
	}
	public static void setDefaultModelI(DefaultTableModel defaultModelI) {
		FileManager.defaultModelI = defaultModelI;
	}
	public static DefaultTableModel getDefaultModelM() {
		return defaultModelM;
	}
	public static void setDefaultModelM(DefaultTableModel defaultModelM) {
		FileManager.defaultModelM = defaultModelM;
	}
	public static int getPlay1ItemNum() {
		return play1ItemNum;
	}
	public static void setPlay1ItemNum(int play1ItemNum) {
		FileManager.play1ItemNum = play1ItemNum;
	}
	public static DefaultTableModel getDefaultModelPlay() {
		return defaultModelPlay;
	}
	public static void setDefaultModelPlay(DefaultTableModel defaultModelPlay) {
		FileManager.defaultModelPlay = defaultModelPlay;
	}
	public static DefaultTableModel getDefaultModelPlay2() {
		return defaultModelPlay2;
	}
	public static void setDefaultModelPlay2(DefaultTableModel defaultModelPlay2) {
		FileManager.defaultModelPlay2 = defaultModelPlay2;
	}
	public static DefaultTableModel getDefaultModelPlay3() {
		return defaultModelPlay3;
	}
	public static void setDefaultModelPlay3(DefaultTableModel defaultModelPlay3) {
		FileManager.defaultModelPlay3 = defaultModelPlay3;
	}
	public static String getP1N() {
		return p1N;
	}
	public static void setP1N(String p1n) {
		p1N = p1n;
	}
	public static String getP1T() {
		return p1T;
	}
	public static void setP1T(String p1t) {
		p1T = p1t;
	}
	public static DefaultTableModel getDefaultModelPlay1() {
		return defaultModelPlay1;
	}
	public static void setDefaultModelPlay1(DefaultTableModel defaultModelPlay1) {
		FileManager.defaultModelPlay1 = defaultModelPlay1;
	}
	public static File getFile() {
		return file;
	}
	public static void setFile(File file) {
		FileManager.file = file;
	}
}
	
	
	