package finproject;

public class Road {
	int cost;
	Village startVillage, endVillage; // start and end points
	
	//Constructor
	public Road (int cost, Village endVillage) {
		this.cost = cost;
		this.endVillage = endVillage;
	} //end Constructor
	
	public Road(int cost, Village startVillage, Village endVillage) {
		this.cost = cost;
		this.endVillage = endVillage;
		this.startVillage = startVillage;
	}
}//end Road class