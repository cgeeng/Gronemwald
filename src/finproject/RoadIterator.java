
package finproject;

public class RoadIterator {
	//A linked list iterator which holds a road for an AdjList
	
	private Road data;
	private RoadIterator next, previous;
	
	public RoadIterator (Road data) {
		this.data = data;
	}//end constructor
	
	//methods
	public Village endVillage() {return data.end;} //returns village that road leads to
	public Village startVillage() {return data.start;}
	public void setNext(RoadIterator n) {this.next = n;}
	public RoadIterator getNext() {return this.next;}
	public void setPrev(RoadIterator p) {this.previous = p;}
	public RoadIterator getPrev() {return this.previous;}
	public int getCost() { return data.cost; };
<<<<<<< HEAD
	public boolean getBuilt() { return data.built; }
=======
	public Road getData() {return this.data;}
>>>>>>> origin/master

}//end class
