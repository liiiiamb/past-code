package util.Manager;

import java.awt.event.*;


import util.GUI.MusicScreen;
import util.GUI.ImageScreen;
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
public class MediaEditor {
/*
 * creates class variables for later use 
 */
	private String fileName;
	private float fileLength;
	private int fileType;
	private String fileLocation;
	/*
	 * creates instances of the music image and video screen for them to be used by/use this class 
	 */
	private MusicScreen musicScreen;
	private ImageScreen imageScreen;
	private VideoScreen videoScreen;
	/*
	 * this class consists of getters and setters that gets the current value of the file name, length, type and location to be used by other classes
	 */
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public float getFileLength() {
		return fileLength;
	}
	public void setFileLength(float fileLength) {
		this.fileLength = fileLength;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public void editFileDetails() {
		
	}
	
	public void changeFileName() {
		
	}
	
	public void changeFileLocation() {
		
	}
	
	
	

}
