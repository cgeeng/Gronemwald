package finproject;

public class Road {
	int cost;
	boolean built = true;
	Village start, end; // start and end points
	
	//Constructor
	public Road (int cost, Village end) {
		this.cost = cost;
		this.end = end;
	} //end Constructor
	
	public Road (boolean built, int cost, Village end) {
		this.built = built;
		this.cost = cost;
		this.end = end;
	} //end Constructor

	public Road(Village start, Village end, int cost) {
		this.cost = cost;
		this.start = start;
		this.end = end;
	}
}//end Road class