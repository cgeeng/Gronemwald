package finproject;

public class Road {
	int cost;
	Village nextVillage;
	
	//Constructor
	public Road (int cost, Village nextVillage) {
		this.cost = cost;
		this.nextVillage = nextVillage;
	} //end Constructor
}//end Road class