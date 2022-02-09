/**
 * 
 * @author lboyd
 *
 */
public class Triangle {
	/**
	 * the below sets the values of a, b and c, as they are all floats, 
	 * the value has to be a floating point number, meaning it needs to have a decimal.
	 */
	private float a = 0.0f; 
	private float b = 0.0f;
	private float c = 0.0f; 
	/**
	 * the below section of code sets the current values of a, b and c, which at this point are all 0.0
	 * to the variable sa, sb and sc and their values.
	 * @param sa
	 * @param sb
	 * @param sc
	 */
	public Triangle (float sa, float sb, float sc) {
		this.a = sa; 
		this.b = sb; 
		this.c = sc; 
	}
	
		public float getA() {
			return this.a; //this gets and returns the current value of the variable and side A
		
		}
	
		public void setA(float sa) {
		this.a = sa; //this sets the current value of the variable a to the variable sa and its value.
		}
	
		public float getB() {
			return this.b; //this gets and returns the current value of the variable b
		
		}
	
		public void setB(float sb) {
			this.b = sb; //this sets the current value of the variable b to the variable sb.
		}
	
		public float getC() {
			return this.c; //this gets and returns the current value of the variable c. 
		
		}
	
		public void setC(float sc) {
			this.c = sc; //this sets the current value of the variable c to the variable sc.	
		}
}
