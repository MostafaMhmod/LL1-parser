package LLParser;

import java.util.Stack;

import FirstAndFollow.FirstAndFollow;
import FirstAndFollow.grammer;

public class LL1Parser {
	public static String table[][];

	public static void parser(grammer grammar) {
		Stack<String> pda = new Stack<String>();
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		grammer grammar = new grammer("Sample4.in");
		FirstAndFollow f = new FirstAndFollow();
		f.First(grammar);
		f.Follow(grammar);
		parseTable t = new parseTable();
		t.first = f.first;
		t.follow = f.follow;
		f.printFirst(t.first);
		f.printFollow(t.follow);
		t.table = new String[grammar.nonTerminals.size() + 1][grammar.terminals.size() + 2];
		t.filler(grammar);
		for (String[] row : t.table) {
			t.printRow(row);
		}
		table = t.table;
	}
}
