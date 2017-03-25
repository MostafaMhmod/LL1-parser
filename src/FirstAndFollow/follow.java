package FirstAndFollow;

import java.util.ArrayList;

public class follow {
	String variable;
	ArrayList<String> setOfFollow = new ArrayList<String>();

	public follow(String variable, ArrayList<String> setOfFollow) {
		variable = this.variable;
		setOfFollow = this.setOfFollow;
	}

	public follow() {
		// TODO Auto-generated constructor stub
	}

	public void print() {

		System.out.print("Follow(" + variable + ")" + ":");
		System.out.println(setOfFollow);

	}

	public String printInFile() {
		return "Follow(" + variable + ")" + ":" + setOfFollow;

	}

}
