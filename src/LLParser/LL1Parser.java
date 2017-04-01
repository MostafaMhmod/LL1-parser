package LLParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import FirstAndFollow.FirstAndFollow;
import FirstAndFollow.grammer;

public class LL1Parser {
	public static String table[][];
	// public static derivations d = new derivations();
	public static ArrayList<derivations> output = new ArrayList<derivations>();

	public static void parser(grammer grammar, ArrayList<String> input) {
		Stack<String> pda = new Stack<String>();
		for (int a = 0; a < input.size(); a++) {
			boolean first = true;
			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[i].length; j++) {
					String var = input.get(a);
					if (table[0][j].equals(var)) {

						if (table[i][j] != null) {
							if (first) {
								pda.push(table[i][0]);
								first = false;
							} else if (!first && !table[i][j].equals(var)) {
								derivations d = new derivations();
								d.input = var;
								d.out = table[i][j];
								d.topOfStack = pda.pop();
								pda.push(d.out);
								output.add(d);

							} else if ((!first && table[i][j].equals(var))) {
								derivations d = new derivations();
								d.input = var;
								d.out = table[i][j];
								d.topOfStack = "";
								pda.push(d.out);
								output.add(d);

							}
						}
					}
				}
			}
		}
	}

	public static ArrayList<String> splitProduction(String x) {
		ArrayList<String> out = new ArrayList<String>();
		String f = "";
		for (int i = 0; i < x.length(); i++) {
			f += x.charAt(i);
			if (i + 1 < x.length() && (x.charAt(i + 1) + "").equals("'")) {
				i++;
				f += x.charAt(i);
				out.add(f);
				f = "";
			}
			if (i + 1 < x.length() && !(x.charAt(i + 1) + "").equals("'")) {

				out.add(f);
				f = "";
			}

		}
		return out;
	}

	public static void printOutput(ArrayList<derivations> output) {
		for (int i = 0; i < output.size(); i++) {
			System.out.println(output.get(i).topOfStack + "[" + output.get(i).input + "]" + "--->" + output.get(i).out);

		}
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

		inputParser n = new inputParser("Input1.in", grammar);
		System.out.println(n.elements);
		System.out.println();

		// parser(grammar, n.elements);
		// printOutput(output);
		System.out.println(splitProduction("FT'"));

	}
}
