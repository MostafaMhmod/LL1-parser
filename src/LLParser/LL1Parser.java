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
		boolean first = true;
//		System.out.println(input);
		for (int a = 0; a < input.size(); a++) {
			boolean done = false;
			while (!done) {
				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {
						String var = input.get(a);
						if (table[0][j].equals(var)) {
							if (table[i][j] != null && !(table[i][0].equals(""))) {
								if (first) {
									pda.push(table[i][0]);
									first = false;
								}

								// System.out.println("var->"+var);
								// System.out.println(pda);

								if (!first && !(table[i][j].equals(var)) && table[i][0].equals(pda.peek())) {
									derivations d = new derivations();
									d.input = var;
									d.topOfStack = pda.pop();
									d.out = table[i][j];

									// System.out.println(d.out);

									ArrayList<String> temp = new ArrayList<String>();
									temp.add(d.out);
									ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);
									// System.out.println(productions);

									for (int k = productions.get(0).size() - 1; k >= 0; k--) {
										if (!(productions.get(0).get(k)).equals("!"))
											pda.push(productions.get(0).get(k));
									}

									output.add(d);

								}
								if ((!first && table[i][j].equals(var)) && table[i][0].equals(pda.peek())) {
									derivations d = new derivations();
									d.input = var;
									d.out = table[i][j];
									d.topOfStack = pda.pop();

									ArrayList<String> temp = new ArrayList<String>();
									temp.add(d.out);
									ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);
									for (int k = productions.get(0).size() - 1; k >= 0; k--) {
										if (!(productions.get(0).get(k)).equals("!"))
											pda.push(productions.get(0).get(k));
									}

									output.add(d);

								}
								if (!pda.isEmpty())
									if (grammar.terminals.contains(pda.peek()) && pda.peek().equals(var)) {
										// System.out.println(pda.peek());
										derivations d = new derivations();
										d.input = "";
										d.out = pda.peek();
										d.topOfStack = pda.pop();
										// System.out.println("hnaa-->"+pda.peek());
										done = true;
										output.add(d);

									}

							}
						}
					}
				}
			}
		}
		System.out.println(pda);
		// input.add("$");

	}

	public static ArrayList<ArrayList<String>> splitProduction(ArrayList<String> production, grammer grammer) {
		ArrayList<ArrayList<String>> out = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < production.size(); i++) {
			String tempo = production.get(i);
			ArrayList<String> aProduction = new ArrayList<String>();
			String temp = "";
			for (int j = 0; j < tempo.length(); j++) {
				temp += tempo.charAt(j);
				if (j + 1 < tempo.length() && (tempo.charAt(j + 1) + "").equals("'")) {
					temp += "'";
					j++;
				}
				if (grammer.nonTerminals.contains(temp) || grammer.terminals.contains(temp) || temp.equals("!")) {
					aProduction.add(temp);
					temp = "";
				}

			}
			out.add(aProduction);
		}
		return out;

	}

	public static void printOutput(ArrayList<derivations> output) {
		for (int i = 0; i < output.size(); i++) {
			System.out.print(output.get(i).topOfStack);
			if (!output.get(i).input.isEmpty())
				System.out.print("[" + output.get(i).input + "]" + "--->" + output.get(i).out);
			else
				System.out.print("--->" + output.get(i).out);

			System.out.println();
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
		// f.printFirst(t.first);
		// f.printFollow(t.follow);
		t.table = new String[grammar.nonTerminals.size() + 1][grammar.terminals.size() + 2];
		t.filler(grammar);
		for (String[] row : t.table) {
			t.printRow(row);
		}
		table = t.table;

		inputParser n = new inputParser("Input1.in", grammar);
		System.out.println("__________________________________________________________");
		System.out.println();

		parser(grammar, n.elements);
//		printOutput(output);

	}
}
