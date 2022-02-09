package util.Manager;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.GUI.ConfirmDeletion;
import util.GUI.HomeScreen;
import util.GUI.ImageScreen;
import util.GUI.MusicScreen;
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
public class WatchFolder { //this class is responsible for watching the directories and determining what action will be carried out when a file is added/deleted/modified
	/*
	 * the below chunk of code contains class variables that allow the row containing the 
	 * file added to be added into the relevant table. 
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
	public static String[] files = new String[5];
	public static String[] filesM = new String[5];
	public static String[] filesI = new String[5];
	private String directoryLocation;

	//video variables 
	
	protected static String s;
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
	public static Object[][] data = { {s, fLengthS, fileT, fileLM} };
	public static String[] playlistNames = new String[25]; //limit to amount of songs that can be added
	//image variables
	protected static String sI;
	protected static String fLengthSI;
	static long fLengthI;
	protected static String fileTI;
	protected static String fileLMI;
	public static String filePathSI;
	public static Object[][] dataI = { {sI, fLengthSI, fileTI, fileLMI} };
	//music variables 
	protected static String sM;
	protected static String fLengthSM;
	static long fLengthM;
	protected static String fileTM;
	protected static String fileLMM;
	public static String filePathSM;
	public static Object[][] dataM = { {sM, fLengthSM, fileTM, fileLMM} };
	public static String[] columnNames = {"Name:", "File Size:", "File Type:", "Last Modified", "File Path:"};
	protected static DefaultTableModel defaultModel = new DefaultTableModel(data, columnNames);


	protected static DefaultTableModel defaultModelI = new DefaultTableModel(dataI, columnNames);
	protected static DefaultTableModel defaultModelM = new DefaultTableModel(dataM, columnNames);
	private JTable table;	
	public static void main (String[] args) { 
		/*
		 * runs the 3 thread classes created below when this class is called and ran. 
		 */
		watchVFolder();
		watchIFolder();
		watchMFolder();
	}
	
	public static void watchVFolder() {
		try {
			System.out.println("Watching Video Directory for changes");
			WatchService watchService = FileSystems.getDefault().newWatchService(); //creates a new watchservice object 
			Path directory = Path.of("Video Files"); //chooses the directory that is watched - in this case it is the video files folder that is kept within this projects files.
			WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY); //registers the directory to the watchkey and creates three events that will cause an action to be carried out, these are a file is created in the directory, a file is modified or a file is deleted 
			while(true) { //as long as the directory exists the below code is ran continuously. 
				for (WatchEvent<?> event : watchKey.pollEvents()) { //loops for each event and catches when a file is created, deleted or modified.
					WatchEvent<Path> pathEvent = (WatchEvent<Path>) event; //assigns the specific event to the pathEvent variable which watches the chosen directory.
					Path fileName = pathEvent.context(); //gets the file name of the file added/deleted/modified.
					WatchEvent.Kind<?> kind = event.kind(); //gets the kind of event that happens - this allows for an action to be tied to the event 
					
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) { //if the event is a file being created in the area the below code is ran.
						System.out.println("A new file is created : " + fileName); //prints out the name of the file created
						String fileNameS = fileName.toString(); //turns that path variable into a string variable.
						String[] newRow = new String[5]; //creates a new string array
						newRow[0] = fileNameS; //assigns the file name to the first column
						newRow[1] = ""; /*the below 4 string values are empty, they are just placeholders
						to allow for the row to be correctly inserted into the table, but this is where the 
						file type, location, etc. could be stored.*/
						newRow[2] = "";
						newRow[3] = "";
						newRow[4] = "";
						defaultModel.addRow(newRow); //adds the row to the chosen table
					}
					if (kind == StandardWatchEventKinds.ENTRY_DELETE) { //if the event is a file being deleted the below code is ran
						System.out.println("A file is deleted : " + fileName);
					}
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY) { //if the event is a file being modified the below code is ran 
						System.out.println("A file has been modified : " + fileName); 
					}
				}
				boolean valid = watchKey.reset(); //resets the watchkey after each use to allow new files to be added
				if (!valid) {
					break; //causes the code to be stopped if the watch key can't be reset.
				}
			}
					
		} catch (Exception e) {
			e.printStackTrace(); //only happens when an error occurs.
		}
	}
	/*
	 * the below code below is the same as the above, but changed for the image files directory and any 
	 * files are added into the images table.
	 */
	public static void watchIFolder() {
		try {
			System.out.println("Watching Image Directory for changes");
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path directory = Path.of("Image Files"); 
			WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			while(true) {
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
					Path fileName = pathEvent.context();
					WatchEvent.Kind<?> kind = event.kind();
					
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("A new music file has been added created : " + fileName);
						String fileNameS = fileName.toString();
						String[] newRow = new String[5];
						newRow[0] = fileNameS;
						newRow[1] = "";
						newRow[2] = "";
						newRow[3] = "";
						newRow[4] = "";
						defaultModelI.addRow(newRow);
					}
					if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
						System.out.println("A music file has been deleted : " + fileName);
					}
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("A music file has been modified : " + fileName); 
					}
				}
				boolean valid = watchKey.reset();
				if (!valid) {
					break;
				}
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * the below code is the same as the code inside watchVFolder(), but the Music Files 
	 * directory is watched and files are added into the music table.
	 */
	public static void watchMFolder() {
		try {
			System.out.println("Watching Music Directory for changes");
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path directory = Path.of("Music Files"); 
			WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			while(true) {
				for (WatchEvent<?> event : watchKey.pollEvents()) {
					WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
					Path fileName = pathEvent.context();
					WatchEvent.Kind<?> kind = event.kind();
					
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("A new file is created : " + fileName);
						String fileNameS = fileName.toString();
						String[] newRow = new String[5];
						newRow[0] = fileNameS;
						newRow[1] = "";
						newRow[2] = "";
						newRow[3] = "";
						newRow[4] = "";
						defaultModelM.addRow(newRow);
					}
					if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
						System.out.println("A file is deleted : " + fileName);
					}
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("A file is modified : " + fileName); //change this text
					}
				}
				boolean valid = watchKey.reset();
				if (!valid) {
					break;
				}
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * the below blocks of code are getters and setters that allow for the respective
	 * table to be updated with new rows.
	 */
	public static DefaultTableModel getDefaultModel() {
		return defaultModel;
	}

	public static void setDefaultModel(DefaultTableModel defaultModel) {
		WatchFolder.defaultModel = defaultModel;
	}

	public static DefaultTableModel getDefaultModelI() {
		return defaultModelI;
	}

	public static void setDefaultModelI(DefaultTableModel defaultModelI) {
		WatchFolder.defaultModelI = defaultModelI;
	}

	public static DefaultTableModel getDefaultModelM() {
		return defaultModelM;
	}

	public static void setDefaultModelM(DefaultTableModel defaultModelM) {
		WatchFolder.defaultModelM = defaultModelM;
	}
}


