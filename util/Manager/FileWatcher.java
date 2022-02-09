package util.Manager;
//this file is not yet in use, could be used as a way to watch a directory for changes if it doesn't happen within filemanager 
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.Callable;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.GUI.ImageScreen;
import util.GUI.MusicScreen;
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
public class FileWatcher {
	private String fileName;
	private String fileLocation;
	private String fileType;
	private String directoryName;
	public static String[] files = new String[5];
	public static String[] filesM = new String[5];
	public static String[] filesI = new String[5];
	private String directoryLocation;
	private MusicScreen musicScreen;
	private ImageScreen imageScreen;
	private VideoScreen videoScreen;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getDirectoryName() {
		return directoryName;
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}
	public String getDirectoryLocation() {
		return directoryLocation;
	}
	public void setDirectoryLocation(String directoryLocation) {
		this.directoryLocation = directoryLocation;
	}
	
	public void deleteFile() {
		
	}
	
	public void renameFile() {
		
	}
	
	public void addFile() {
		
	}
	

	
	public void displayErrorMessage() {
		
	}
	
	public void closeProgram(){
		System.exit(0);
	}
	
	/*public static void main(String[] args) {
		watchVFolder();
	}*/
	
/*	public static void watchVFolder() {
		try {
			System.out.println("Watching Video Files directory for changes");
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path directory = Path.of("Video Files");
			WatchKey watchKey = directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
			while (true) {
				for (WatchEvent<?> event: watchKey.pollEvents()) {
					WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
					Path fileName = pathEvent.context();
					WatchEvent.Kind<?> kind = event.kind();
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("A new file is created : " + fileName);
						files[0] = "dog";
						files[1] = "cat";
						files[2] = "zebra";
						files[3] = "penguin";
						files[4] = "giraffe";
						defaultModel.addRow(files);
					}
					if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
						System.out.println("This file has been deleted : " + fileName);
					}
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
						System.out.println("This file has been modified : " + fileName);
					}
				}
				boolean valid = watchKey.reset();
				if(!valid) {
					break;
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
	
	
}


