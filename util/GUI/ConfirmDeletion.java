package util.GUI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
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

import util.Manager.FileManager;
import util.Manager.PlaylistEditor;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;
import util.Manager.WatchVideo;


public class ConfirmDeletion {
	
	FileManager fileManager = null; //this passes across the file manager class, the below line passes across the playlist editor class.
	PlaylistEditor playlistEditor = null;
	/*
	 * The below two sets of code allows the current instance of the classes to be used rather than just null which is nothing so the classes wouldn't be able to be passed across with all of their methods, etc.
	 * @param me
	 */
	public ConfirmDeletion (FileManager me) 
	{
		this.fileManager = me;
	}
	
	public ConfirmDeletion (PlaylistEditor me) {
		this.playlistEditor = me;
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
		 * This code is just responsible for creating a new frame, all the actions for this GUI take place within the FileManager class
		 */
		JFrame frame = new JFrame ("Confirm Deletion"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,500);	
		}
	}


