package util.GUI;
/*
 * The below import code imports all of the java libraries that are in use, in this case it is javax.swing and everything inside that library, everything inside java.awt as well as
 * importing the two classes that are used by this file, which are FileManager and PlaylistEditor
 */
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

import util.Manager.PlaylistEditor;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;
import util.Manager.WatchVideo;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class AddToPlaylistScreen {
	
	PlaylistEditor playlistEditor = null; //Creates a default object of the playlistEditor class - this passes across the class to be used by this file.
	
	public AddToPlaylistScreen (PlaylistEditor pe)
	{
		this.playlistEditor = pe; //creates a new object of the current playlist editor class
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
		JFrame frame = new JFrame ("Add to Playlist"); //creates a new frame - the name of this window is defined in the braces which in this case is "Add to Playlist"
		/*
		 * The next two lines alter the frame, the first line sets what happens when the user presses the cross at the top right of the window and the second line
		 * sets the size of the frame displayed to the user. 
		 */
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,300);
		
		JPanel panel = new JPanel(); //Creates a new empty panel that will go inside the frame and display all of the GUI components. 
		/*
		 * the next 3 lines of code add items to this first panel, the first set of items to be added are the title label, this is first defined as a new JLabel with the text
		 * that is shown, this text is defined in the braces after creating the new JLabel. The font is then set of the label and then the label is added into the panel. "Center"
		 * aligns the label to the center of the panel. 
		 */
		JLabel title = new JLabel("Add to Playlist:"); 
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));
		panel.add(title, "Center");
		
		frame.getContentPane().add(panel, "North");	//this adds the panel to the frame, "North" aligns the panel to the northen most point of the frame. 
		
		
		JPanel panel2 = new JPanel(new GridBagLayout()); //This creates another new panel, it also defines a GridBagLayout to the panel which wasn't present in the first panel I created
		JLabel text = new JLabel("New Playlist:"); //This creates another new text label, again the text shown is defined in the braces.
		text.setFont(new Font("Rockwell Bold", Font.PLAIN, 13)); //Sets the same font for the label as was giving to the title. 

		
		JTextArea namePlaylist = new JTextArea(0,20); //This creates a TextArea in which the user can enter any sort of text, character or numbers, inside the braces, the size is set for the TextArea, 0 leaves the size as default, this gives it a default height and an additional width of 20. 
		JButton playlistConfirm = new JButton ("Create");  //This creates a JButton, the text that is shown on the JButton is defined within the braces. 
		
		
		
		GridBagConstraints c = new GridBagConstraints(); //This defines constraints for the GridBag which allows you to manually set the location of items, or in this case to move items below one another. 
		/*
		 * These first 7 lines of code are responsible for putting the TextArea below the Label that was created a bit earlier in the code, it first sets the default x and y location
		 * to 0, and then after adding in the label at that area, it alters the y co-ordinate to be 2 spaces lower and then adds in the TextArea after that, if the change to 2 didn't 
		 * occur then both components would be taking up space next to each other on the same line.
		 */
		c.gridx = 0;
		c.gridy = 0;
		panel2.add(text, c);
		panel2.add(Box.createVerticalStrut(40)); //This creates a vertical empty box which creates a gap between the previously defined component and the next. 
		c.gridy = 2;
		c.anchor=GridBagConstraints.CENTER; //This ensures that the components are centered in the middle. 
		panel2.add(namePlaylist, c);
		panel2.add(Box.createHorizontalStrut(100));
		
		/*
		 * This code is the same as above but it sets the default x and y co-ords as different numbers, which already gives the button a pre-defined place in the GUI
		 */
		GridBagConstraints d = new GridBagConstraints();
		d.gridx=5;
		d.gridy=2;
		d.anchor=GridBagConstraints.EAST;
		panel2.add(playlistConfirm, d);
		
		frame.getContentPane().add(panel2, "Center"); //This adds the panel to the center-most point of the frame 
		
		
		frame.setVisible(true); //this line of code sets the frame as visible which shows it when the code is ran. 
		
	}

}
