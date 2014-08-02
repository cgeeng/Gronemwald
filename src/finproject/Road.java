package finproject;

public class Road {
	int cost;
<<<<<<< HEAD
	boolean built = true;
	Village startVillage, endVillage; // start and end points
	
	//Constructor
	public Road (int cost, Village endVillage) {
		this.cost = cost;
		this.endVillage = endVillage;
	} //end Constructor
	
	public Road(int cost, Village startVillage, Village endVillage) {
=======
	Village start, end; // start and end villages

	public Road(Village start, Village end, int cost) {
>>>>>>> origin/master
		this.cost = cost;
		this.start = start;
		this.end = end;
	}
}//end Road class