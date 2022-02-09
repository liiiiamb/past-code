package util.Manager;
import util.GUI.AddToPlaylistScreen;
import util.GUI.ConfirmDeletion;
//import util.GUI.ConfirmRename;
import util.GUI.CreatePlaylistScreen;
import util.GUI.MusicScreen;
//import util.GUI.NameFileScreen;
import util.GUI.PlaylistScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
public class PlaylistEditor extends FileManager { //allows all of file managers methods and variables to be used 
	private String playlistName; //creates a new string class variable
	private int amountOfSongs;//creates a new int class variable
	/*
	 * allows for the below gui screens and classes to access/be accessed by this class
	 */
	private AddToPlaylistScreen addToPlaylistScreen;
	public PlaylistScreen playlistScreen;
	private ConfirmDeletion confirmDeletion;
	private CreatePlaylistScreen createPlaylistScreen;
	public static String[] playlistNames = new String[25]; //limit to amount of songs that can be added 
	public static ArrayList<String> playlist1Items = new ArrayList<String>();  //creates an arraylist that stores the amount of items in a playlist
	
/*
 * four empty methods below 
 */
	public void createPlaylist() {
	
		
		
		
	}
	public void editPlaylist() {
		
	}

	
	public void deletePlaylist() {
		
	}
	
	public void viewPlaylist() {
		
	}
	public ActionListener getActionListenerForButtonAddToPlay()
	{
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				addToPlaylistScreen = new AddToPlaylistScreen(PlaylistEditor.this);
				addToPlaylistScreen.main(); //runs the main method from the addtoplaylistscreen class 
			}
		};
		return ac;
	}
	
	public ActionListener getActionListenerForAddToPlay1() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileManager.getS(); //getting the name of the file to add it to the playlist
				System.out.println("The file name is: " + getS()); //test to print out the name of the file
				playlist1Items.add(getS()); //adding the name of the playlist into playlist 1.
			}
		};
		return ac;
	}
	
	public ActionListener getActionListenerForButtonRemovePlay() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmDeletion = new ConfirmDeletion(PlaylistEditor.this);
				confirmDeletion.main(); //runs the main method from the confirmdeletion class 
				
			}
		};
		return ac;
	}
	

	
	public ActionListener getActionListenerForButtonCreatePlay() {
		ActionListener ac = new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				createPlaylistScreen = new CreatePlaylistScreen(PlaylistEditor.this);
				createPlaylistScreen.main(); //runs the main method from the createplaylistscreen class 
			}
		};
		return ac;
	}
	/*
	 * the last part of this class creates getters and setters to allow
	 * certain variables and methods from this class be used and altered by 
	 * other classes 
	 */
	public static ArrayList<String> getPlaylist1Items() {
		return playlist1Items;
	}
	public static void setPlaylist1Items(ArrayList<String> playlist1Items) {
		PlaylistEditor.playlist1Items = playlist1Items;
	}
	
	public String getPlaylistName() {
		return playlistName;
	}
	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}
	public int getAmountOfSongs() {
		return amountOfSongs;
	}
	public void setAmountOfSongs(int amountOfSongs) {
		this.amountOfSongs = amountOfSongs;
	}

	

}
