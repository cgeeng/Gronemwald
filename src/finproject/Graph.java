package finproject;

import finproject.Exceptions.*;

public class Graph {
	private int length = 0;
	Village firstVillage;
	Village lastVillage;
	
	//constructor
	public Graph() {
		firstVillage = null;
		lastVillage = null;
	} 
	
	public boolean isEmpty() { return length == 0; }
	public int getLength() { return length; }
	public Village getFirst() {return this.firstVillage;}
	public Village getLast() {return this.lastVillage;}
	
	public void insert ( Village newVillage ) {
		if (isEmpty()) {
			firstVillage = newVillage;
			lastVillage = newVillage;
		}
		else {
			lastVillage.next = newVillage;
			newVillage.previous = lastVillage;
			lastVillage = newVillage;
		}
		length++;
	}
	
	public void delete(int villageName) throws GraphEmptyException, NotFoundException {
		if (isEmpty()) {throw new GraphEmptyException();}
		Village villToDelete = find(villageName); 
		if(villToDelete.outdegree > 0){
			RoadIterator ri = villToDelete.adjacent.firstRoad;
			while(ri != null){
				ri.getVillage().indegree--;
				ri = ri.next;
			}
		}
		Village villLeadingTo = firstVillage;
		while(villLeadingTo != null){
			if(villLeadingTo.outdegree > 0){
				RoadIterator ri = villLeadingTo.adjacent.firstRoad;
				while(ri != null){
					if(ri.getVillage() == villToDelete){
						//remove(ri); // MAKE METHOD TO REMOVE A ROADITERATOR
					}
					ri = ri.next;
				}
			}
			villLeadingTo = villLeadingTo.next;
		}
	} // end of delete method
	
	public Village find(int name) throws NotFoundException, GraphEmptyException {
		if (isEmpty()) {throw new GraphEmptyException();}
		Village temp = firstVillage;
		Village found = temp;
			while (temp!= null) {
				if (temp.getName() == name) found = temp;
				temp = temp.next;
			}
			if ( found.getName() != name) throw new NotFoundException();
		return found;
	}

	

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
	
}//end VillageList class
