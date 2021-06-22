import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * The above 4 lines of code imports the correct java libraries that are going to be used later on in the program. Each one of the libraries here are used and they
 * would need to be imported here for them to actually work and be able to be used. 
 * @author lboyd
 *
 */
public class ERFReader { //Sets the starting class of ERFReader that is going to allow the rest of the file to work, has to have the same name as the file. 
	
	public String getContents(String path) { 
				BufferedReader in = null;
				/**
				 * this method here sets the value of the BufferedReader to null or empty, which means its value can be altered later on, and it will add on to the value here
				 * which is 0. 
				 */
		try
		{
			in = new BufferedReader(new FileReader(path));
			/**
			 * this here opens the stream to the file allowing it to be opened and used. using the new function allows a new object to be created. 
			 */
			try
			{
				String line = ""; //this sets the contents of the line variable as empty
				String contents = ""; //the same as the above line, but sets the contents of the contents variable to being empty. 
				
				
				while ((line = in.readLine()) != null)
					/**
					 * this while loop here, loops the below program of the line being printed and using the carriage return character of \r as long as 
					 * the line isn't empty. the != means is not, and null means empty. 
					 */
				{
					contents += line + "\r"; //this line here adds to the variable contents, the value of the variable line as well as the carriage return character \r which returns to the start of each line. 
					System.out.println(line); //this prints out the value of line. 
				}
				return contents; //this returns the value of contents that's just been added to, so it can be used within the rest of the program, as well as in other files, including the helper file. 
			}
			catch(IOException e) {return null;}
			finally {in.close();}
			/**
			 * this block of code as well as the below 2 all do the same things, when the error is caught, nothing happens. returning null means the same as returning an empty string or returning 0, it just means that the error 
			 * is caught even if it doesn't do anything to it. 
			 */
		}
			catch (FileNotFoundException e) {
				return null;
			}
			catch (IOException e) {
				return null; 
			}
		
		}
}

