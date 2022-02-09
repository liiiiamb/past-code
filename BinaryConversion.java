import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class BinaryConversion {

	public static void main(String[] args) {
		String text = "011001000110111101100111"; // Binary input as String
		StringBuilder sb = new StringBuilder(); // Some place to store the chars

		Arrays.stream( // Create a Stream
		    text.split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
		).forEach(s -> // Go through each 8-char-section...
		    sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
		);

		String output = sb.toString(); // Output text (t)
		Arrays.stream(text.split("(?<=\\G.{8})")).forEach(s -> System.out.print((char) Integer.parseInt(s, 2))); 
		System.out.print('\n');
				
		}
	}
		


