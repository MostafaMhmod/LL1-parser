package LLParser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Stack;

import FirstAndFollow.FirstAndFollow;
import FirstAndFollow.grammer;

public class LL1Parser {
	public static String table[][];
	public static ArrayList<derivations> output = new ArrayList<derivations>();

	public static void parser(grammer grammar, ArrayList<String> input) {

		Stack<String> pda = new Stack<String>();
		boolean error = false;
		boolean first = true;

		for (int a = 0; a < input.size(); a++) {

			boolean done = false;
			while (!done) {
				if (error)
					break;

				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {
						String var = input.get(a);
						if (table[0][j].equals(var)) {

							if (!(table[i][0].equals(""))) {
								if (table[i][j] != null && first) {
									pda.push(table[i][0]);
									first = false;
								}

								if (table[i][j] != null && !pda.isEmpty() && !first && !(table[i][j].equals(var))
										&& table[i][0].equals(pda.peek())) {
									derivations d = new derivations();
									d.input = var;
									d.topOfStack = pda.pop();
									d.out = table[i][j];

									ArrayList<String> temp = new ArrayList<String>();
									temp.add(d.out);
									ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);

									for (int k = productions.get(0).size() - 1; k >= 0; k--) {
										if (!(productions.get(0).get(k)).equals("!"))
											pda.push(productions.get(0).get(k));

									}

									output.add(d);

								}
								if (table[i][j] != null && !pda.isEmpty() && (!first && table[i][j].equals(var))
										&& table[i][0].equals(pda.peek())) {
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
								if (table[i][j] != null && grammar.terminals.contains(pda.peek())
										&& pda.peek().equals(var)) {
									derivations d = new derivations();
									d.input = "";
									d.out = pda.peek();
									d.topOfStack = pda.pop();
									done = true;
									output.add(d);

								}
								if (table[i][j] == null && table[0][j].equals(var) && table[i][0].equals(pda.peek())
										&& !done) {
									error = true;
								}
							}

						}
					}

				}

			}

		}

		if (!error)
			dollarPrsing(pda, grammar);
		if (error) {
			output = null;
		}
	}

	public static boolean haveNonEpislon(String table[][], String var) {
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (table[0][j].equals(var)) {
					if (table[i][j] != null && (!table[i][j].equals("!")) && i > 0) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static void dollarPrsing(Stack<String> pda, grammer grammar) {
		while (!pda.isEmpty()) {

			for (int i = 0; i < table.length; i++) {
				for (int j = 0; j < table[i].length; j++) {
					if (table[0][j].equals("$")) {
						if (table[i][j] != null && !(table[i][0].equals(""))) {

							if (!pda.isEmpty() && table[i][0].equals(pda.peek())) {
								derivations d = new derivations();
								d.input = "$";
								d.topOfStack = pda.pop();
								d.out = table[i][j];

								ArrayList<String> temp = new ArrayList<String>();
								temp.add(d.out);
								ArrayList<ArrayList<String>> productions = splitProduction(temp, grammar);

								for (int k = productions.get(0).size() - 1; k >= 0; k--) {
									if (!(productions.get(0).get(k)).equals("!"))
										pda.push(productions.get(0).get(k));
								}
								output.add(d);

							}

						}
					}
				}
			}
		}
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
		if (output != null)
			for (int i = 0; i < output.size(); i++) {
				System.out.print(output.get(i).topOfStack);
				if (!output.get(i).input.isEmpty())
					System.out.print("[" + output.get(i).input + "]" + "--->" + output.get(i).out);
				else
					System.out.print("--->" + output.get(i).out);

				System.out.println();
			}
		else
			System.out.println("Parse error");
	}

	public static void writeOutputInFile(ArrayList<derivations> output) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter("Input.out"));

		if (output != null)
			for (int i = 0; i < output.size(); i++) {
				out.print(output.get(i).topOfStack);
				if (!output.get(i).input.isEmpty())
					out.print("[" + output.get(i).input + "]" + "--->" + output.get(i).out);
				else
					out.print("--->" + output.get(i).out);

				out.println();
			}
		else
			out.println("Parse error");

		out.close();

	}

	@SuppressWarnings("static-access")
	public static void printTable(parseTable t) {
		for (String[] row : t.table) {
			t.printRow(row);
		}
	}

	public static String printRowInFile(String[] row) {
		String out ="";
		for (String i : row) {
			out+= i + "		";
		}
		return out;
	}

	@SuppressWarnings("static-access")
	public static void writeTableInFile(parseTable t) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter("Table.out"));

		for (String[] row : t.table) {
			out.println(printRowInFile(row));
		}
		out.close();
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
		t.table = new String[grammar.nonTerminals.size() + 1][grammar.terminals.size() + 2];
		t.filler(grammar);

		printTable(t);
		
		writeTableInFile(t);
		System.out.println();
		System.out.println("NOW THE TABLE IS IN THE TABLE.OUT FILE");
		System.out.println();

		table = t.table;

		// *************************************************
		// **********Change the cases from Here*************
		// *************************************************

		inputParser n = new inputParser("Input1.in", grammar);

		System.out.println("__________________________________________________________");
		System.out.println();

		parser(grammar, n.elements);
		printOutput(output);
		writeOutputInFile(output);
		System.out.println();
		System.out.println("NOW THE OUTPUT IS IN THE INPUT.OUT FILE");

	}
}
