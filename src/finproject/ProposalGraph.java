package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;

public class ProposalGraph {
	//DEEP copy of object Graph, STRIPPE DOWN
	//will NOT be updated if Graph is updated
	//should probably deleted after proposals all made
	
	public Road[] toBuild; //contains acceptable roads to be built after minimum spanning tree found
	
	public int length = 0;
	Village firstVillage;
	Village lastVillage;
	
	//constructor
	
	public ProposalGraph (Graph original) {
		deepCopy(original);
	}
	
	//methods
	public void deepCopy(Graph original) {
		//Recreate villages first
		//Then create roads
		if (original.firstVillage == null) { System.out.println("I don't think your country exists!"); }
		else {
			Village originalVillage = original.firstVillage;
			for (int i = 1; i <= original.getLength(); i++) { 
				//iterate through original Village linked list
				Village temp = new Village()
				
			}
		}
		
		
	}
	
	public void findMinSpanTree() {
		//uses priority queue
		//returns a Graph?
		//Adds to list of roads to be made
	}
	
	//generic methods
	public Village getFirst() {return this.firstVillage;}
	public Village getLast() {return this.lastVillage;}
	
	public void insert ( Village newVillage ) {
		if (isEmpty()) {
			firstVillage = newVillage;
			lastVillage = newVillage;
		}
		else {
			lastVillage.setNext(newVillage);
			newVillage.setPrev(lastVillage);
			lastVillage = newVillage;
		}
		length++;
	}
	
	public Village find(int name) throws NotFoundException, GraphEmptyException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (! isEmpty()) {
			Village current = this.firstVillage;
			while (current != null) {					
				if (current.getName() == name) {return current;}
				current = current.getNext();
			} throw new NotFoundException();
		} else {throw new GraphEmptyException();}
	}
	
	public boolean isEmpty() { return length == 0; }
	public int getLength() { return length; }

	public void printGraph() { // string representation of graph, used for testing
		if (! isEmpty()) {
			Village current = this.firstVillage;
			while (current != null) {
				System.out.println("Village " + current.getName() + " holds " + current.populationSize + " gnomes.");
				System.out.println("   It leads to " + current.getAdjList());
				current = current.getNext();
			}
		} else {System.out.println("This graph is empty.");}
	} // end of printGraph()
}
