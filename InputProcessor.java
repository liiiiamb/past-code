import java.util.*; //these two lines import the java libraries used by the code, allowing things such as scanners to be used in the file.
import java.lang.*;



public class InputProcessor { //sets the start of the file, class name has to be the same as the file name. 
	/**
	 * the below is the main method needed by the file to actually run. 
	 * In this main method, the scanner is introduced under the variable
	 * scn, and the user is asked to enter a word. The scanner then scans 
	 * that entered in text and passes through the asciiToBinary method so 
	 * it is able to be used by that method. 
	 * @param args
	 */
	public static void main(String[] args) {
		String text;
		Scanner scn = new Scanner (System.in);
		System.out.println("Enter a word: ");
		text = scn.nextLine();
		asciiToBinary(text);
		binaryToAscii(text);
	}
		


	public static String asciiToBinary(String text) {
		byte [] b = text.getBytes(); //this gets the byte value of the entered text. 
		/**
		 * the below for loop goes over every letter in the text input and converts it to a binary value using
		 * the .toBinaryString function. It converts every letter in the string and returns the binary equivalent. 
		 * If it was just 1 letter then only 8 numbers would be outputted. 
		 */
		for (int i = 0; i < b.length;i++) { 
			int a = (b[i]);
			String intBinary = null; //this is the string that stores the binary value, its defined as empty at the start so it could be manipulated from a value of 0. 
			intBinary = Integer.toBinaryString(a); //this is the line that converts the byte value of the individual letters to a binary value. 
			System.out.print(intBinary); //this prints out the binary version of the entered text. 
		}
		return text;
	}
	/**
	 * the below method is where the binary number is translated back to its textual form. It doesn't work due to the
	 * use of the parseInt and because the last method saved the Binary value in a value that cant be changed into an
	 * int, it returns an error. 
	 */
	public static String binaryToAscii(String text) {
		StringBuilder sb = new StringBuilder(); //this is similar to a string but is an object that can be modified. 
		Arrays.stream( //this opens up the array stream that goes over each individual number within the array. 
				text.split("(?<=\\G.{8})") //this splits the array up into blocks of 8 as binary is an 8 bit number, or a byte. 
		).forEach(s -> //this means that it goes over each number within the string and does the code underneath to that value. 
			sb.append((char) Integer.parseInt(s,2)) //this line converts the binary number to both an ascii value then to an actual letter. The parseInt here is whats causing the error. 
		);
		return text;
	}
	
	/**
	 * the below is the method that checks if the text is a palindrome or not
	 * the code never reaches here due to the previous error of the parseInt but 
	 * it has been checked in a seperate file and doesn't bring up any errors. 
	 * @param text
	 * @return
	 */
	public static boolean isPalindrome(String text) {
		for (int i = 0; i <= text.length() / 2; i++) { //this creates a for loop that only loops through half of the word as the other half should be the same. 
			if (text.charAt(i) != text.charAt(text.length() - i - 1)) { 
				/**
				 * the above if statement says that if the character at the location of i doesn't equal the value of the character
				 * at the total length of the word - i - 1, the value of i changes every time the loop runs to ensure that every 
				 * letter is checked. this carries on until the whole word is checked, if at any point the letters dont match then the 
				 * word is not a palindrome and the boolean value would be false. 
				 */
				return false;
			}
	
				
		}
		
		return true; //this only returns a value of true if all of the letters have been checked and if they all match up with each other, making it a palindrome.
	}
}
