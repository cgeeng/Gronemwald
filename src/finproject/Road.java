package finproject;

public class Road {
	int cost;
	Village start, end; // start and end villages

	public Road(Village start, Village end, int cost) {
		this.cost = cost;
		this.start = start;
		this.end = end;
	}
}//end Road class