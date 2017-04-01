package LLParser;

import java.util.ArrayList;

import FirstAndFollow.first;
import FirstAndFollow.follow;
import FirstAndFollow.grammer;

public class parseTable {
	public static ArrayList<first> first = new ArrayList<first>();
	public static ArrayList<follow> follow = new ArrayList<follow>();
	public static String table[][];

	public static void filler(grammer grammar) {

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				if (i == 0) {
					if (j == 0)
						table[0][j] = "";
					if (j > 0 && j - 1 < grammar.terminals.size())
						table[0][j] = grammar.terminals.get(j - 1);

					if (j > 0 && j - 1 == grammar.terminals.size())
						table[0][j] = "$";

				} else if (i != 0) {
					if (j == 0) {
						table[i][j] = grammar.nonTerminals.get(i - 1);
					} else if (j != 0) {
						String src = grammar.nonTerminals.get(i - 1);
						ArrayList<String> productions = getProductions(src, grammar);
						if (productions != null) {
							if (containEpsilon(productions)) {
								ArrayList<String> followOf = followOf(src);
								for (String compare : followOf) {
									if (table[0][j].equals(compare)) {
										table[i][j] = "!";
									}
								}

							}
							ArrayList<ArrayList<String>> splitProduction = splitProduction(productions, grammar);

							for (int k = 0; k < splitProduction.size(); k++) {
								String var = splitProduction.get(k).get(0);

								ArrayList<String> firstOf = firstOf(var);
								var = "";
								for (int l = 0; l < splitProduction.get(k).size(); l++)
									var += splitProduction.get(k).get(l);

								if (firstOf != null)
									for (String compare : firstOf) {
										if (table[0][j].equals(compare)) {
											table[i][j] = var;
										}
									}
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

	public static ArrayList<String> followOf(String src) {
		for (int i = 0; i < follow.size(); i++) {
			if (follow.get(i).variable.equals(src)) {
				return follow.get(i).setOfFollow;
			}

		}
		return null;
	}

	public static ArrayList<String> firstOf(String src) {
		for (int i = 0; i < first.size(); i++) {
			if (first.get(i).variable.equals(src)) {
				return first.get(i).setOfFirst;
			}

		}
		return null;
	}

	public static boolean containEpsilon(ArrayList<String> x) {

		for (String string : x) {
			if (string.equals("!")) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<String> getProductions(String src, grammer grammar) {
		for (int i = 0; i < grammar.grammer.size(); i++) {

			if (grammar.grammer.get(i).source.equals(src))
				return grammar.grammer.get(i).production;
		}
		return null;
	}

	public static void printRow(String[] row) {
		for (String i : row) {
			System.out.print(i);
			System.out.print("\t");
		}
		System.out.println();
	}
	


	// @SuppressWarnings("static-access")
	// public static void main(String[] args) throws Exception {
	// grammer grammar = new grammer("Sample4.in");
	// FirstAndFollow f = new FirstAndFollow();
	// f.First(grammar);
	// f.Follow(grammar);
	// first = f.first;
	// follow = f.follow;
	// // f.printFirst(first);
	// // f.printFollow(follow);
	// table = new String[grammar.nonTerminals.size() +
	// 1][grammar.terminals.size() + 2];
	// filler(grammar);
	//
	// for (String[] row : table) {
	// printRow(row);
	// }
	// }
}
