package finproject;

public class Road {
	int cost;
	boolean built = true;
	Village start, end; // start and end points
	Village topSortPredecessor;
	Village topSortFollowing;
	String edgeType;
	
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
	// when  adding predecessor edge. have that predecessor edge add thing to following edge
	public Road(String edge,Village pred, Village follow){ // for topSort
		this.edgeType = edge;
		this.topSortPredecessor = pred;
		this.topSortFollowing = follow;
	}
		
	public String printRoad() { // string representation of this road
		return "\nRoad from village " + start.getName() + " to village " + end.getName() + " at cost " + this.cost;
	}

}//end Road class