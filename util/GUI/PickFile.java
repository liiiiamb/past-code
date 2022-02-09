package util.GUI;
import javax.swing.*;

import util.Manager.FileManager;
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


public class PickFile {

	
	protected FileManager fileManager = null; //allows filemanager to be accessed by this file and for filemanager to access and use this class 
	
	public PickFile() {};
	public PickFile (FileManager me)
	{
		this.fileManager = me;
	}
	
	
	public void main() { //this code is the code that is ran when the class is called. 
		JFileChooser fileChoose = new JFileChooser(); //creates a new filechooser object
		fileChoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //only allows the user to select files and directories, can be restricted more using Files only or Directories only
		fileChoose.setFileHidingEnabled(false); //doesnt allow files to be hidden within the file chooser 
	}
}

	