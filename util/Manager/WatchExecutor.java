package util.Manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/*
 * this class is responsible for executing the threads that watch the certain directories.
 */
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
public class WatchExecutor {
	
	public static void main (String[] args) {
		System.out.println("Starting Thread - running in background");
		ExecutorService executor = Executors.newCachedThreadPool(); //creates a new executor object 
		
		/*
		 * submits the 3 created threads to the executor to allow
		 * them to be ran consecutively. 
		 */
		executor.submit(new WatchVideo());
		executor.submit(new WatchImage());
		executor.submit(new WatchMusic());		

		
	}
	
	

}
