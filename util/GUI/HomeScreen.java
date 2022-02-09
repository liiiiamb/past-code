package util.GUI;
import javax.swing.*;


import util.Manager.FileManager;
import util.Manager.FileWatcher;
import util.Manager.MediaEditor;
import util.Manager.WatchVideo;
import util.Manager.WatchImage;
import util.Manager.WatchMusic;

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


public class HomeScreen{
	
	static MediaEditor mediaEditor = null; //creates a null object of both the MediaEditor and FileManager classes to be used later
	static FileManager fileManager = null;
	static FileWatcher fileWatcher = null;
	/*
	 * Sets empty methods that access both the FileManager and MediaEditor classes and allow them to be used within this class.
	 */
	public HomeScreen (MediaEditor me)
	{
	}
	public HomeScreen (FileManager me) {
		
	}
	public HomeScreen (FileWatcher me) {
		
	}

	public static void main(String[] args) {
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
		JFrame frame = new JFrame ("Home Page"); //Creates a blank frame with the title "Home Page"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes the program when the user clicks the X at the top of the frame
		frame.setSize(700,500); //Sets the default size of the frame, this frame will never be any smaller or larger than this size.

		/*
		 * Creates objects to access the two below classes and allow them to be used. 
		 */
		mediaEditor = new MediaEditor();
		fileManager = new FileManager();
		fileWatcher = new FileWatcher();
		
		
		JPanel panel = new JPanel(); //Creates a blank panel
		GridLayout layout = new GridLayout(1,3); //assigns a grid layout to the panel and its components, 1,3 means it takes up 1 row and has 3 sections within that row
		layout.setHgap(20); //this sets a gap between the added components on this line.
		
		JLabel title = new JLabel ("SortYourMedia"); //creates a label which is the name of the program and then gives it a font, the same font used throughout
		title.setFont(new Font("Rockwell Bold", Font.PLAIN, 14));

		panel.add(title); //adds the title label to the blank panel

		panel.add(Box.createHorizontalStrut(200)); //Creates an empty box which is used for creating a horziontal gap between components of 200. 
		
		/*
		 * The below first creates a text area which has a default height and an added width of 10, this is used as a search box, it then has a key listener which is coded within the fileManager class.
		 * KeyListener means that when a key is entered or deleted, a certain action takes place. The final line of this block adds the text area to the panel. 
		 */
		JTextArea search = new JTextArea(0, 10);	
		search.addKeyListener(fileManager.getKeyListenerForSearch());
		panel.add(search);
		
		frame.getContentPane().add(panel, "North"); //This adds the panel and all of its components to the frame at the northern-most point. 

		
		JPanel panel2 = new JPanel(); //This creates a second blank panel. 
		panel2.setLayout(new BorderLayout()); //sets the layout of this panel to a border layout.
		
		JPanel subPanel2 = new JPanel(); //creates a subpanel that is later going to be added within the panel2		
		subPanel2.add(Box.createVerticalStrut(300)); //creates a vertical gap of 300 between components. 
		/*
		 * The below block of code first creates a button that has the text "Music" inside, it sets it's preferred size, this means that this size is not going to be
		 * required but preferred, so there is the chance that it could be larger or smaller depending on the sizing of other components. The button is then added into
		 * the previously created subpanel before being associated with an actionListener that is created within FileManager.java
		 */
		JButton music = new JButton("Music");
		music.setPreferredSize(new Dimension(200,100));
		subPanel2.add(music);
		music.addActionListener(fileManager.getActionListenerForButtonM());
		
		//The below is the same as the above for Music but altered to contain different text, have a different variable name and get a different actionListener
		JButton images = new JButton("Images");
		images.setPreferredSize(new Dimension(200, 100));
		subPanel2.add(images);
		images.addActionListener(fileManager.getActionListenerForButtonI());
		
		//The below is the same as the above for Music but altered to contain different text, have a different variable name and get a different actionListener
		JButton videos = new JButton("Videos");
		videos.setPreferredSize(new Dimension(200, 100));
		subPanel2.add(videos);
		videos.addActionListener(fileManager.getActionListenerForButtonV());
		
		
		
		JPanel subPanel = new JPanel(); //Creates another new blank subPanel 

		JButton myPlay = new JButton("My Playlists"); //Creates a button that contains the text "My Playlists", this is then added into the subPanel that was just created in the center-most point
		subPanel.add(myPlay, "Center");
		myPlay.addActionListener(fileManager.getActionListenerForButtonAP()); //This adds an actionListener to the button, this is created and defined within the fileManager class.
		
		panel2.add(subPanel2, "North"); //This adds the first subPanel2 to the northen most point within the previously created panel
		panel2.add(subPanel); //This adds the second created subPanel within the second panel, it doesn't have a defined location as it automatically places itself in the correct place.
		
		frame.getContentPane().add(panel2, "Center"); //once the second panel contains the components, it is added into the frame at the center-most point.
		
		
		JPanel panel3 = new JPanel();
		JLabel label = new JLabel("For Instructions on how to use this program please view SortYourMedia How-To ");
		label.setSize(500,70);
		panel3.add(label);
		frame.getContentPane().add(panel3, "South");
		
		
		frame.setVisible(true); //sets the frame as visible as soon as the code is ran, this is the first screen to be opened upon running the code. 

	}
}
	
