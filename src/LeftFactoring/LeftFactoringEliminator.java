package LeftFactoring;

import java.util.ArrayList;
import java.util.Scanner;

public class LeftFactoringEliminator {
	static grammer output = new grammer();

	public static grammer Eliminator(grammer grammer) throws Exception {

		for (int i = 0; i < grammer.grammer.size(); i++) {
			@SuppressWarnings("unused")
			int Combinations = 0;
			int prefixIndex = 0;
			int x[] = commonPrefixIndexer(grammer.grammer.get(i));

			Combinations = x[0];
			prefixIndex = x[1];

			ArrayList<String> production = new ArrayList<String>();

			// pushing into the array the strings having common prefix
			if (x[0] + x[1] != 0)
				production = stringsWithCommonPrefix(grammer.grammer.get(i), prefixIndex);

			// saving the common prefix of the strings having common prefix
			String prefix = commonPrefix(production);

			// removing the 1st left factor from the rule
			rule[] out = leftFactorRemover(grammer.grammer.get(i), prefix, i);
			output.grammer.add(out[0]);
			x = commonPrefixIndexer(out[1]);


			rule dashed = out[1];
			int y[] = { Integer.MIN_VALUE, Integer.MIN_VALUE };

			while (x[0] + x[1] != 0) {

				Combinations = x[0];
				prefixIndex = x[1];

				// pushing into the array the strings having common prefix
				production = stringsWithCommonPrefix(dashed, prefixIndex);

				// saving the common prefix of the strings having common prefix
				prefix = commonPrefix(production);

				// removing the nth left factor from the rule
				out = leftFactorRemover(dashed, prefix, i);

				x = commonPrefixIndexer(out[0]);
				y = commonPrefixIndexer(out[1]);

				if (x[0] + x[1] == 0)
					output.grammer.add(out[0]);

				if (x[0] + x[1] != 0)
					dashed = out[0];

				if (x[0] + x[1] + y[0] + y[1] == 0)
					break;

				if (y[0] + y[1] != 0) {
					dashed = out[1];
					x[0] = y[0];
					x[1] = y[1];
					production = stringsWithCommonPrefix(out[0], y[1]);
				}

			}

			if (y[0] + y[1] == 0) {
				output.grammer.add(out[1]);
			}

		}
		return output;
	}

	public static ArrayList<String> stringsWithCommonPrefix(rule rule, int index) {
		ArrayList<String> output = new ArrayList<>();
		output.add(rule.production.get(index));

		for (int k = 0; k < rule.production.size(); k++) {
			if (k != index)
				output.add(rule.production.get(k));

			if (k > 0 && commonPrefix(output).equals(""))
				output.remove(output.size() - 1);

		}
		return output;
	}

	public static rule[] leftFactorRemover(rule rule, String prefix, int i) {
		rule primary = new rule();
		rule dashed = new rule();
		primary.source = rule.source;
		dashed.source = rule.source + "'";

		for (int j = 0; j < rule.production.size(); j++) {

			String temp = rule.production.get(j);

			if (temp.equals("!")) {
				dashed.production.add("!");
				primary.production.add("!");
			}

			String compare;
			if (prefix.length() < temp.length())
				compare = temp.substring(0, prefix.length());

			else
				compare = temp.substring(0);

			if (compare.equals(prefix) || (temp.equals("!") && prefix.length() < temp.length())) {
				temp = temp.substring(prefix.length());

				if (!(temp.equals("")))
					dashed.production.add(temp);

			}

			if (primary.production.size() == 0)
				primary.production.add(prefix + dashed.source);
			temp = rule.production.get(j);
			if ((!(compare.equals(prefix))) && !(temp.substring(prefix.length() - 1).equals(""))) {
				primary.production.add(temp.substring(prefix.length() - 1));

			}
		}
		rule[] x = { primary, dashed };
		return x;

	}

	public static int[] commonPrefixIndexer(rule rule) {
		int biggestpossibleNumberOfCombinations = 0;
		int biggestpossibleNumberOfCombinationsIndex = 0;

		for (int j = 0; j < rule.production.size(); j++) {
			int counter = 0;
			ArrayList<String> test = new ArrayList<String>();
			String temp = rule.production.get(j);

			test.add(temp);

			for (int k = j + 1; k < rule.production.size(); k++) {
				temp = rule.production.get(k);
				test.add(temp);
				if (commonPrefix(test).equals("")) {
					test.remove(test.size() - 1);
				}

				else if (!(commonPrefix(test).equals(""))) {
					counter++;

				}

				if (counter > biggestpossibleNumberOfCombinations) {
					biggestpossibleNumberOfCombinations = counter;
					biggestpossibleNumberOfCombinationsIndex = j;

				}

			}

		}
		if (biggestpossibleNumberOfCombinations > 0)
			biggestpossibleNumberOfCombinations++;
		int[] x = { 0, 0 };

		if (biggestpossibleNumberOfCombinations != 0 || biggestpossibleNumberOfCombinationsIndex != 0) {
			x[0] = biggestpossibleNumberOfCombinations;
			x[1] = biggestpossibleNumberOfCombinationsIndex;

		}

		return x;

	}

	public static String commonPrefix(ArrayList<String> arr) {
		int n = arr.size();
		int index = findMinLength(arr, n);
		String prefix = "";
		int low = 0;
		int high = index;

		while (low <= high) {
			int mid = (low + (high - low) / 2);

			if (allContainsPrefix(arr, n, arr.get(0), low, mid)) {
				prefix = prefix + arr.get(0).substring(low, mid + 1);
				low = mid + 1;
			}

			else {
				high = mid - 1;
			}
		}

		return (prefix);
	}

	public static int findMinLength(ArrayList<String> arr, int n) {
		int min = Integer.MAX_VALUE;

		for (int i = 0; i <= n - 1; i++) {
			if (arr.get(i).length() < min) {
				min = arr.get(i).length();
			}
		}
		return (min);
	}

	public static boolean allContainsPrefix(ArrayList<String> arr, int n, String str, int start, int end) {
		for (int i = 0; i <= n - 1; i++) {

			for (int j = start; j <= end; j++) {
				if (j >= arr.get(i).length()) {
					return false;
				}
				if (arr.get(i).charAt(j) + "" != null && (arr.get(i).charAt(j) != str.charAt(j))
						&& str.charAt(j) + "" != null) {
					return (false);

				}

			}
		}
		return (true);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Enter The Grammar File Name :");
		@SuppressWarnings("resource")
		Scanner s = new Scanner(System.in);
		String fileName = s.nextLine();

		grammer grammer = new grammer(fileName);
		grammer = Eliminator(grammer);
		
		grammer.print();
		System.out.println("Check The Output File");
		grammer.writeInFile();

	}
}
