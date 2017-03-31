package LLParser;

import java.util.ArrayList;

import FirstAndFollow.FirstAndFollow;
import FirstAndFollow.first;
import FirstAndFollow.follow;
import FirstAndFollow.grammer;

public class parseTable {
	static ArrayList<first> first = new ArrayList<first>();
	static ArrayList<follow> follow = new ArrayList<follow>();
	static String table[][];

	public static void filler(grammer grammar) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (i == 0) {
					if(j==0)
						table[i][j] = "";
					if (j > 0 && j - 1 < grammar.terminals.size()) 
						table[i][j] = grammar.terminals.get(j - 1);
					
					if(j > 0 && j - 1 == grammar.terminals.size())
						table[i][j] = "$";

				}
				else if (i != 0){
					if (j==0) {
						table[i][j] = grammar.nonTerminals.get(i-1);
					}
				}
			}

		}
	}

	public static void printRow(String[] row) {
		for (String i : row) {
			System.out.print(i);
			System.out.print("\t");
		}
		System.out.println();
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		grammer grammar = new grammer("Sample4.in");
		FirstAndFollow f = new FirstAndFollow();
		f.First(grammar);
		f.Follow(grammar);
		first = f.first;
		follow = f.follow;
		// f.printFirst(first);
		// f.printFollow(follow);
		table = new String[grammar.nonTerminals.size() + 1][grammar.terminals.size() + 2];
		filler(grammar);

		for (String[] row : table) {
			printRow(row);
		}
	}
}
