
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
	public Village endVillage() { return data.end; } //returns village that road leads to
	public Village startVillage() {return data.start;}
	public int getCost() { return data.cost; };

}//end class
