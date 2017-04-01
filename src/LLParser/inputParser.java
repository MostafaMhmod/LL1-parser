package LLParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import FirstAndFollow.grammer;

public class inputParser {
	public ArrayList<String> elements;

	public inputParser() {
		// TODO Auto-generated constructor stub
	}

	public inputParser(String fileName, grammer grammer) throws Exception {
		try {
			File file = new File(fileName);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String text = "";
			String var = "";
			while ((text = reader.readLine()) != null) {
				var = "";

				int i = 0;
				ArrayList<String> out = new ArrayList<String>();
				while (i < text.length()) {
					var = "";
					while (true) {
						if (i >= text.length())
							break;

						var += text.charAt(i);
						i++;
						if (grammer.terminals.contains(var)) {

							break;
						}

					}
					if (var != null || !var.equals("")) {
						out.add(var);
					}

				}

				elements = out;
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
