package finproject;

import finproject.Exceptions.*;

public class Graph {
	private int length = 0;
	Village firstVillage;
	Village lastVillage;
	ProposalGraph proposal;
	
	//constructor
	public Graph() {
		firstVillage = null;
		lastVillage = null;
	} 
	
	public boolean isEmpty() {return length == 0;}
	public int getLength() {return length;}
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
	
	public void delete(int name) throws GraphEmptyException, NotFoundException {
		if (isEmpty()) {throw new GraphEmptyException();}
		else if (getLength() == 1) {
			this.firstVillage = null;
			this.lastVillage = null; }
		else {
			Village found = find(name);
			if (found.equals(firstVillage)) {
				found.getNext().setPrev(null);
				firstVillage = found.getNext();}
			else if (found.equals(lastVillage)) {
				lastVillage = found.getPrev(); 
				lastVillage.setNext(null);}
			else {
				found.getPrev().setNext(found.getNext());
				found.getNext().setPrev(found.getPrev());
			}}
		this.length--;
		
		// roads are going to be handled in the GUI (user's choice)
		/*
		if (isEmpty()) {throw new GraphEmptyException();}
		Village villToDelete = find(villageName); 
		if(villToDelete.outdegree > 0){
			RoadIterator ri = villToDelete.adjacent.firstRoad;
			while(ri != null){
				ri.endVillage().indegree--;
				ri = ri.next;
			}
		}
		Village villLeadingTo = firstVillage;
		
		
		while(villLeadingTo != null){
			if(villLeadingTo.outdegree > 0){
				RoadIterator ri = villLeadingTo.adjacent.firstRoad;
				while(ri != null){
					if(ri.endVillage() == villToDelete){
						//remove(ri); // MAKE METHOD TO REMOVE A ROADITERATOR
					}
					ri = ri.next;
				}
			}
			villLeadingTo = villLeadingTo.getNext();
		}
		*/
	} // end of delete method
	
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
<<<<<<< HEAD
	
	public Graph 
	
	public boolean isEmpty() { return length == 0; }
	public int getLength() { return length; }
	

=======
>>>>>>> origin/master

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
