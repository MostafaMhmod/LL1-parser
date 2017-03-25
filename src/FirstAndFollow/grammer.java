package FirstAndFollow;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class grammer {

	public ArrayList<rule> grammer = new ArrayList<rule>();
	public ArrayList<String> nonTerminals = new ArrayList<String>();
	public ArrayList<String> terminals = new ArrayList<String>();

	public grammer() {

	}

	public grammer(String fileName) throws Exception {
		try {
			File file = new File(fileName);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String text = "";
			String leftSide = "";
			int counter = 0;

			while ((text = reader.readLine()) != null) {
				leftSide = "";

				if (counter >= 2) {
					rule rule = new rule();

					rule.source = text;
					if ((text = reader.readLine()) != null)
						leftSide += text;
					ArrayList<String> setOfProductions = new ArrayList<String>(Arrays.asList(leftSide.split("\\|")));
					rule.production = setOfProductions;
					leftSide = "";
					grammer.add(rule);
					counter++;
				}

				if (counter == 0) {
					ArrayList<String> temp = new ArrayList<String>(Arrays.asList(text.split(",")));
					nonTerminals.addAll(temp);
					counter++;
					text = "";

				} else if (counter == 1) {
					ArrayList<String> temp = new ArrayList<String>(Arrays.asList(text.split(",")));
					terminals.addAll(temp);
					counter++;
					text = "";

				}

			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void print() {
		 System.out.println(nonTerminals);
		 System.out.println(terminals);
		for (int m = 0; m < grammer.size(); m++) {
			System.out.print(grammer.get(m).source + " " + "->");
			System.out.println(grammer.get(m).production);

		}
	}

	public void writeInFile() throws IOException {
		try {
			PrintWriter out = new PrintWriter(new FileWriter("Sample.out"));
			for (int m = 0; m < grammer.size(); m++) {
				out.print(grammer.get(m).source + " " + "->" + " ");
				out.println(grammer.get(m).production);

			}
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	 public static void main(String[] args) throws Exception {
	 grammer grammer = new grammer("Sample4.in");
	 grammer.print();
	 }

}
