package FirstAndFollow;

import java.util.ArrayList;

public class rule {

	public String source;
	public ArrayList<String> production = new ArrayList<String>();

	public rule(String source, ArrayList<String> destinationList) {
		source = this.source;
		destinationList = this.production;
	}
	public rule() {
		// TODO Auto-generated constructor stub
	}

}
