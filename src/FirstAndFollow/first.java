package FirstAndFollow;

import java.util.ArrayList;

public class first {
	String variable;
	ArrayList<String> setOfFirst = new ArrayList<String>();

	public first(String variable, ArrayList<String> setOfFirst) {
		variable = this.variable;
		setOfFirst = this.setOfFirst;
	}

	public first() {
		// TODO Auto-generated constructor stub
	}

	public void print() {

		System.out.print("First(" + variable + ")" + ":");
		System.out.println(setOfFirst);

	}

	public String printInFile() {
		return "First(" + variable + ")" + ":" + setOfFirst;

	}

}
