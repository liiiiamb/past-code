
public class TriangleSolver {
	/**
	 * the below section of code assigns the values of a, b and c respectively to the value of t which gets the values of a,
	 *  b and c from the triangle file. 
	 * @param t
	 * @return
	 */
	public String solveTriangle(Triangle t) {
		float a=t.getA();
		float b=t.getB();
		float c=t.getC();
		
		if (areEqual(a,b) && areEqual(b,c)) {
			return ("Equilateral"); //this returns equilateral if all the sides of the triangle are equal. 
		}
		else if ((areEqual (a,b)) && !(areEqual(b,c))) {
			return ("Isoceles");  //this returns isoceles if the sides a and b are equal but the sides b and c arent, using the not operator before the second areEqual test.
		}
		else if ((areEqual (a,c)) && !(areEqual(c,b))) {
			return ("Isoceles"); //this returns isoceles if the sides a and c are equal, but the sides c and b aren't
		}
		else if ((areEqual (b,c)) && !(areEqual(a,c))) {
			return ("Isoceles"); //this returns isoceles if the sides b and c are equal, but the sides a and c aren't
		}
		else {
			return ("Scalene"); //this returns scalene if all of the above are false, meaning if every side is different from each other and the previous tests were all false. 
		}

		
		
	}
	/**
	 * the below compares the two float values of the variables and b, if the are true then it returns true, using the comparison == 0 test. 
	 * if they are different it returns a boolean value of false. 
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean areEqual(float a, float b) {
		int comparison=Float.compare(a, b);
		if (comparison==0) {
			return true;
		}
		else {
			return false;
		}
		}
		
}
