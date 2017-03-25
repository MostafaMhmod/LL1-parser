package FirstAndFollow;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class FirstAndFollow {

	static ArrayList<first> first = new ArrayList<first>();
	static ArrayList<follow> follow = new ArrayList<follow>();

	public static void First(grammer grammer) {
		String var = "";
		String firstValue = "";

		for (int i = 0; i < grammer.terminals.size(); i++) {
			first value = new first();
			var = grammer.terminals.get(i);
			value.variable = var;
			firstValue = var;
			value.setOfFirst.add(firstValue);
			first.add(value);
		}

		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		String fst = "";
		boolean change = true;

		for (int i = 0; i < grammer.grammer.size(); i++) {
			temp = splitProduction(grammer.grammer.get(i).production, grammer);
			fst = firstOf(grammer.grammer.get(i).source, grammer, temp);

			first v = new first();
			v.variable = grammer.grammer.get(i).source;

			if ((!(fst.equals(""))) && grammer.terminals.contains(fst.charAt(0) + "")) {
				ArrayList<String> setOfProductions = null;
				if (fst.contains(","))
					setOfProductions = new ArrayList<String>(Arrays.asList(fst.split(",")));

				if (setOfProductions != null) {
					v.setOfFirst.addAll(setOfProductions);

				} else {
					v.setOfFirst.add(fst);
				}
				first.add(v);
			}
		}

		boolean goBack = false;
		while (change) {
			change = false;
			for (int i = 0; i < grammer.grammer.size(); i++) {
				first v = new first();
				String src = grammer.grammer.get(i).source;
				v.variable = src;

				temp = giveMeThe2DArray(grammer, src);

				if (!inFirst(src)) {

					src = temp.get(0).get(0);
					if (!inFirst(src)) {
						goBack = true;
					}

					if (inFirst(src)) {
						int x = FirstIndex(src);
						v.setOfFirst.addAll(first.get(x).setOfFirst);
						first.add(v);
						if (goBack == true)
							change = true;
					}

				}
				if ((src.equals(temp.get(0).get(0).charAt(0) + "")) && containEpsilon2(temp)) {
					ArrayList<String> out = new ArrayList<String>();
					for (int im = 0; im < temp.size(); im++) {
						boolean leftRec = false;
						for (int j = 0; j < temp.get(im).size(); j++) {
							if (temp.get(im).get(j).equals(src)) {
								leftRec = true;
							} else if (!(temp.get(im).get(j).equals(src)) && leftRec) {
								out.addAll(getFirstList(temp.get(im).get(j)));
							}
						}
					}
					out = removeDuplicates(out);
					v.setOfFirst = out;
					first.add(v);
				}
			}

		}

	}

	public static void Follow(grammer grammer) {
		String temp = "";

		for (int i = 0; i < grammer.nonTerminals.size(); i++) {
			temp = "";
			boolean x = false;
			String src = grammer.nonTerminals.get(i);
			follow out = new follow();
			if (i == 0) {
				out.setOfFollow.add("$");

			}
			out.variable = src;
			for (int m = 0; m < grammer.grammer.size(); m++) {
				for (int j = 0; j < grammer.grammer.get(m).production.size(); j++) {
					ArrayList<ArrayList<String>> productions = splitProduction(grammer.grammer.get(m).production,
							grammer);
					for (int k = 0; k < productions.size(); k++) {
						for (int l = 0; l < productions.get(k).size(); l++) {

							if (src.equals(productions.get(k).get(l))) {
								// System.out.println(productions.get(k)+"--------------"+"---------"+giveMeSource(productions.get(k), grammer));
								if (l + 1 < productions.get(k).size()
										&& grammer.terminals.contains(productions.get(k).get(l + 1))) {
									if (!temp.equals(""))
										temp += ",";
									temp += productions.get(k).get(l + 1);
									x = true;
									break;
								}
								// if non terminal
								if (l + 1 < productions.get(k).size()
										&& grammer.nonTerminals.contains(productions.get(k).get(l + 1))) {
									if (!temp.equals(""))
										temp += ",";

									if (!firstTillFollw(productions.get(k), l + 1,
											giveMeSource(productions.get(k), grammer)).contains("kkk")) {
										temp += firstTillFollw(productions.get(k), l + 1,
												giveMeSource(productions.get(k), grammer));
										x = true;
										break;

									}
									if (firstTillFollw(productions.get(k), l + 1,
											giveMeSource(productions.get(k), grammer)).contains("kkk")) {

										break;
									}
								}

								if (l + 1 >= productions.get(k).size()) {
									if (!temp.equals(""))
										temp += ",";

									if (!firstTillFollw(productions.get(k), -1,giveMeSource(productions.get(k), grammer)).contains("kkk")) {
										temp += firstTillFollw(productions.get(k), -1,giveMeSource(productions.get(k), grammer));
										x = true;
										break;

									}
								}
							}

						}

					}
				}
			}

			if (x) {
				if (temp.contains(",")) {
					ArrayList<String> set = new ArrayList<String>(Arrays.asList(temp.split(",")));
					set = removeDuplicates(set);
					out.setOfFollow.addAll(replaceEpislon(set));
					follow.add(out);
				}
			}

		}

	}

	public static String firstTillFollw(ArrayList<String> x, int i, String src) {
		if (i == -1) {
			i = x.size() - 1;
		}
		String out = "";
		for (int j = i; j < x.size(); j++) {

			if (!out.equals(""))
				out += ",";

			if ((i <= x.size() - 1))
				out += arraylistToString(getFirstList(x.get(j)));

			if (!out.equals(""))
				out += ",";

			if (containEpsilon(getFirstList(x.get(j)))) {
				if (i == x.size() - 1) {
					if (getFollowList(src) == null) {
						return "kkk";
					}
					if (getFollowList(src) != null) {
						out += arraylistToString(getFollowList(src));
					}

				}
			}

		}

		return out;

	}

	public static String giveMeSource(ArrayList<String> x, grammer grammer) {
		String s = arraylistToString(x);
		s = s.replaceAll(',' + "", "");
		for (int i = 0; i < grammer.grammer.size(); i++) {
			if (grammer.grammer.get(i).production.contains(s)) {
				return grammer.grammer.get(i).source;

			}
		}

		return null;

	}

	public static ArrayList<String> replaceEpislon(ArrayList<String> x) {

		for (int i = 0; i < x.size(); i++) {
			if (x.get(i).equals("!")) {
				x.remove(i);
				x.add("$");
			}

		}
		return x;
	}

	public static ArrayList<String> removeDuplicates(ArrayList<String> x) {
		Object[] st = x.toArray();
		for (Object s : st) {
			if (x.indexOf(s) != x.lastIndexOf(s)) {
				x.remove(x.lastIndexOf(s));
			}
		}
		return x;
	}

	public static ArrayList<String> getFirstList(String x) {
		for (int i = 0; i < first.size(); i++) {
			if (first.get(i).variable.equals(x)) {
				return first.get(i).setOfFirst;
			}

		}
		return null;
	}

	public static ArrayList<String> getFollowList(String x) {
		for (int i = 0; i < follow.size(); i++) {
			if (follow.get(i).variable.equals(x)) {
				return follow.get(i).setOfFollow;
			}

		}
		return null;
	}

	public static ArrayList<ArrayList<String>> giveMeThe2DArray(grammer grammer, String compareble) {
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		for (int m = 0; m < grammer.grammer.size(); m++) {
			if (grammer.grammer.get(m).source.equals(compareble)) {
				temp = splitProduction(grammer.grammer.get(m).production, grammer);

			}
		}
		return temp;
	}

	public static String firstOf(String source, grammer grammer, ArrayList<ArrayList<String>> temp) {
		String out = "";

		if (grammer.nonTerminals.contains(source) && grammer.nonTerminals.contains(temp.get(0).get(0))) {
			boolean containEpsilon = false;
			for (int i = 0; i < temp.size(); i++) {
				// System.out.println(temp.get(i));

				if (containEpsilon(temp.get(i))) {
					containEpsilon = true;
				}
			}
			if (!containEpsilon) {
				String src = temp.get(0).get(0);
				return src;
			}
			if (containEpsilon) {

			}
		} else if (grammer.nonTerminals.contains(source) && grammer.terminals.contains(temp.get(0).get(0))) {
			for (int i = 0; i < temp.size(); i++) {
				if (grammer.terminals.contains(temp.get(i).get(0)) || temp.get(i).get(0).equals("!")) {
					if (!out.equals(""))
						out += ",";
					out += (temp.get(i).get(0));

				}
			}
		}

		if (!out.equals(""))
			return out;

		return "";
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

	public static boolean inFirst(String z) {
		for (first string : first) {
			if (string.variable.equals(z)) {
				return true;
			}
		}
		return false;
	}

	public static boolean inFollow(String z) {
		for (follow string : follow) {
			if (string.variable.equals(z)) {
				return true;
			}
		}
		return false;
	}

	public static int FirstIndex(String z) {
		int x = 0;
		for (first string : first) {
			if (string.variable.equals(z)) {
				return x;
			}
			x++;

		}
		return -1;
	}

	public static boolean containEpsilon(ArrayList<String> x) {

		for (String string : x) {
			if (string.equals("!")) {
				return true;
			}
		}
		return false;
	}

	public static boolean containEpsilon2(ArrayList<ArrayList<String>> temp) {
		for (int i = 0; i < temp.size(); i++)
			for (String string : temp.get(i)) {
				if (string.equals("!")) {
					return true;
				}
			}
		return false;
	}

	public static void printFirst(ArrayList<first> first) {
		for (int i = 0; i < first.size(); i++) {
			first.get(i).print();
		}

	}

	public static void printFollow(ArrayList<follow> follow) {
		for (int i = 0; i < follow.size(); i++) {
			follow.get(i).print();
		}

	}

	public static void writeFirstInFile(ArrayList<first> first) throws IOException {

		PrintWriter out = new PrintWriter(new FileWriter("first.out"));

		for (int i = 0; i < first.size(); i++) {
			out.println(first.get(i).printInFile());
		}
		out.close();

	}

	public static void writeFollowInFile(ArrayList<follow> follow) throws IOException {

		PrintWriter out = new PrintWriter(new FileWriter("follow.out"));

		for (int i = 0; i < follow.size(); i++) {
			out.println(follow.get(i).printInFile());
		}
		out.close();

	}

	public static String arraylistToString(ArrayList<String> x) {
		String t = "";
		for (int i = 0; i < x.size(); i++) {
			if (!t.equals(""))
				t += ",";
			t += x.get(i);
		}
		return t;
	}

	public static void main(String[] args) throws Exception {
		grammer grammer = new grammer("Sample4.in");
		First(grammer);
		Follow(grammer);
		printFirst(first);
		printFollow(follow);

		System.out.println();
		System.out.println("OUTPUT IS NOW IN first.OUT AND follow.out FILES");
		writeFirstInFile(first);
		writeFollowInFile(follow);

	}
}
