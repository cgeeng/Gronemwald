package finproject;

public class RoadIterator {
	//A node which contains the roads for an AdjList
	
	Road data;
	RoadIterator previous;
	RoadIterator next;
	
	public RoadIterator (Road data) {
		this.data = data;
	}//end constructor
	
	//methods
	public Village getVillage() { return data.nextVillage; }; //returns village that road leads to
	public int getVillageName() { return data.nextVillage.name; };
	public int getCost() { return data.cost; };

}//end class
