package LLParser;

import java.util.ArrayList;

import FirstAndFollow.FirstAndFollow;
import FirstAndFollow.first;
import FirstAndFollow.follow;
import FirstAndFollow.grammer;

public class parseTable {
	 static ArrayList<first> first = new ArrayList<first>();
	 static ArrayList<follow> follow = new ArrayList<follow>();
	String[][] table = new String[3][3];

	public static void filler() {

	}

	public static void main(String[] args) throws Exception {
		grammer grammer = new grammer("Sample4.in");
		FirstAndFollow f = new FirstAndFollow();
		f.First(grammer);
		f.Follow(grammer);

	}
}
