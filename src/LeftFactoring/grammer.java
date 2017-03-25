package LeftFactoring;

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

	public grammer() {

	}

	public grammer(String fileName) throws Exception {
		try {
			File file = new File(fileName);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String text = null;
			String leftSide = "";

			while ((text = reader.readLine()) != null) {
				rule rule = new rule();

				rule.source = text;
				if ((text = reader.readLine()) != null)
					leftSide += text;

				ArrayList<String> setOfProductions = new ArrayList<String>(Arrays.asList(leftSide.split("\\|")));
				rule.production = setOfProductions;

				leftSide = "";
				grammer.add(rule);
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void print() {
		for (int m = 0; m < grammer.size(); m++) {
			System.out.print(grammer.get(m).source + " " + "->");
			System.out.println(grammer.get(m).production);

		}
	}
	public void writeInFile() throws IOException{
		try{
		PrintWriter out = new PrintWriter(new FileWriter("Sample.out"));
		for (int m = 0; m < grammer.size(); m++) {
			out.print(grammer.get(m).source + " " + "->"+" ");
			out.println(grammer.get(m).production);

		}
		out.close();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

}
