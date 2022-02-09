package util.GUI;

import javax.swing.*;

import util.Manager.FileManager;
import util.Manager.PlaylistEditor;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;
import util.Manager.WatchVideo;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

public class CreatePlaylistScreen {
	/*
	 * creates two null instances of the playlisteditor and filemanager classes so they can be used.
	 */
	PlaylistEditor playlistEditor = null;
	FileManager fileManager = null;
	
	public CreatePlaylistScreen (PlaylistEditor pe)
	{
		this.playlistEditor = pe; //allows the current instance of the playlisteditor class and its methods to be used by this class
	}
	
	public CreatePlaylistScreen (FileManager me) {
		this.fileManager = me;//allows the current instance of the filemanager class and its methods to be used by this class
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
		playlistEditor = new PlaylistEditor();  //sets playlistEditor object created before to a new instance of the playlisteditor class
		fileManager = new FileManager();//sets fileManager object created before to a new instance of the filemanager class
		JFrame frame = new JFrame ("Create Playlist"); //Creates a new frame with the text "Create Playlist" this frame is currently empty 
		/*
		 * The below two lines first set the program to close when the user clicks the X at the top right of the window and then set the size
		 * of the frame to be the same as every other frame within the program except for a small few.
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setSize(500,300);
		
		JPanel panel = new JPanel(); //Creates a new empty panel for components to be added to
		JLabel title = new JLabel("Create Playlist:"); //Creates a label with the text "Create Playlist", the font is then defined for this label and it is added to the panel in the center-most point
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));
		panel.add(title, "Center");
		
		frame.getContentPane().add(panel, "North");	//Adds the panel to the frame at the northen-most point	
		
		
		/*
		 * The below creates a new panel which uses the GridBagLayout similar to the AddToPlaylistScreen file, text is then created using JLabels and its font is then set.
		 * A TextArea and a button are then created. 
		 */
		JPanel panel2 = new JPanel(new GridBagLayout());
		JLabel text = new JLabel("New Playlist:");
		text.setFont(new Font("Rockwell Bold", Font.PLAIN, 13));

		
		JTextArea namePlaylist = new JTextArea(0,20); //sets the size of the text area as the default height but a width of default +20
		JButton playlistConfirm = new JButton ("Create");
		
		
		/*
		 * The below allows you to set a specific location for the components using GridBagConstraints, the code first sets the default locations of x and y as 0, which is
		 * their default position, then once the first label is added, the Y position is changed before the text area is added, this allows the text area to be placed under the 
		 * label.
		 */
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		panel2.add(text, c);
		panel2.add(Box.createVerticalStrut(40));//Creates an empty vertical box which is used for the spacing between components
		c.gridy = 2;
		c.anchor=GridBagConstraints.CENTER; //positions the components in the center of their space.
		panel2.add(namePlaylist, c);
		panel2.add(Box.createHorizontalStrut(100)); //Creates an empty horizontal box which is used for the spacing between components
		
		/*
		 * The below code is similar but instead of setting the x and y positions as their defaults, it gives them a pre-defined location.
		 */
		GridBagConstraints d = new GridBagConstraints();
		d.gridx=5;
		d.gridy=2;
		d.anchor=GridBagConstraints.EAST; //positions the components in the eastern-most point of their psace.
		panel2.add(playlistConfirm, d);
		frame.getContentPane().add(panel2, "Center"); //adds the panel to the center-most point of the frame.
		
		JPanel panel3 = new JPanel(); //Creates a third empty panel, this will hold the cancel button.
		panel3.add(Box.createHorizontalStrut(300)); //Creates an empty box which positions the button to the far right of the screen.
		JButton cancel = new JButton("Cancel"); //Creates the button which will be shown and interacted with, the text on the button is set as "Cancel"
		cancel.addActionListener(fileManager.getActionListenerForButtonCancel()); //This adds an actionlistener, which occurs when the button is pressed, the action for this is defined in FileManager, the class is then passed to this class using fileManger (the object created before) .methodname

		
		panel3.add(cancel); //this adds the button the panel
	
		
		frame.getContentPane().add(panel3, "South"); // this adds the panel to the frame and the southern-most point or the bottom of the screen
		
		
		frame.setVisible(true); //this shows the frame when this code is ran. 
		
	}

}
