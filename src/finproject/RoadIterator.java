
package finproject;

public class RoadIterator {
	//A linked list iterator which holds a road for an AdjList
	
	Road data;
	RoadIterator previous;
	RoadIterator next;
	
	public RoadIterator (Road data) {
		this.data = data;
	}//end constructor
	
	//methods
	public Village getVillage() { return data.endVillage; } //returns village that road leads to
	public int getCost() { return data.cost; };

}//end class
