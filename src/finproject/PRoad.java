package finproject;

public class PRoad {
	PRoad next, previous;
	int cost;
	Village starting, end;
	
	public PRoad(Village starting, Village end, int cost) {
		this.starting = starting;
		this.end = end;
		this.cost = cost;
	}
}//end Road class