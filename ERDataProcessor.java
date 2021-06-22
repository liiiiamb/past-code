/**
 * sets the starting class that allows the program to run, has to have the same name as the file. 
 * @author lboyd
 *
 */
public class ERDataProcessor {
	private double data [] = new double[0]; //creates the double array, the new function creates the new object and using 0 inside the square brackets starts the array at the starting place, setting this value as 1, would start it at the second value. 
	
	private double averageRate  = 0; //sets the starting value of averageRate as 0, it is then altered and changed throughout the program. 
	/**
	 * this block of code below first starts off by creating the method of process and passing through the variable that is going to be used, which in this case is data. 
	 * the first actual line of code in this block splits the strings within the text file using the \r carriage return character which splits it at the end of each of the lines
	 * so each line is separate. the next line creates a new double object that uses the length of each line and sets the value of the rates variable to those lengths. 
	 * a for loop is also used within this block that populates the array. it first sets the value of i to 0, then says i is less than the value of the length of each line, which in the first instance it is as 0
	 * is less than 3. then adds 1 to the value each time the loop is ran. this would carry on until the value of i is greater than the length of the lines within the file. 
	 * @param data
	 */
	public void process (String data) {
		String[] lines=data.split("\r");
		double [] rates=new double[lines.length];
		double sum = 0;
		for (int i=0; i<lines.length; i++) {
					rates[i]=Double.parseDouble(lines[i]);
					sum+=rates[i];
				}
				this.data = rates; //this sets the current value of data to the value of rates using the this. function, which uses the current value of the chosen variable. 
				this.averageRate = sum / (double)lines.length; //this sets the current value of averageRate to the sum which was defined in the loop as adding to the value of rates, then divides it by the length of the lines in the file. 
		
}
	/**
	 * this code below sets the value of d, or dollars, to average rate multiplied by the value of p, or pounds. these are defined in the helper file. 
	 * @param p
	 * @return
	 */
			public double poundToDollar(double p) { 
				return p*averageRate;
					
	}
	
			public double dollarToPound(double d) { //this is similar to the code block above, but it sets the value of pounds instead to the value of dollars divided by the averageRate that was set earlier. 
				return d/averageRate;
	}
	
			public double[] getData() {
				return this.data; //this returns the current value of the data variable. using the this function means it gets the most current value of that variable. 
	}
	
			public void setData(double[] data) {
				this.data = data; //this sets the value of the class variable data to the value of data. this most likely is wrong as it doesn't use the double set in the parentheses. 
	}
	
			public double getAverageRate() {
				return this.averageRate; //this returns the current most value of the averageRate variable. 
	}
	
			public void setAverageRate (double averageRate) {
				this.averageRate = averageRate; //finally, this block of code sets the value of the current most value of averageRate to the value of the averageRate variable. 
	}
}
