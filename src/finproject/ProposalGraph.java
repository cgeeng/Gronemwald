package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;

public class ProposalGraph {
	
	public Road[] toBuild; //contains acceptable roads to be built after minimum spanning tree found
	public ConnectedGraph connected = new ConnectedGraph();
	public PriorityQueue pq = new PriorityQueue();
	public int length = 0;
	Village firstVillage;
	Village lastVillage;
	
	//constructor
	
	public ProposalGraph (Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		deepCopy(original);
	}
	
	//methods
	
	//This is method is sorta useless unless we want to destroy existing roads......oops
	public void deepCopy(Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//Recreate villages first
		//Then create roads
		if (original.firstVillage == null) { System.out.println("I don't think your country exists!"); }
		else {
			Village originalVillage = original.firstVillage;
			for (int i = 1; i <= original.getLength(); i++) { 
				//iterate through original Village linked list, CREATE NEW VILLAGES
				Village temp = new Village(true, originalVillage.getName());
				insert(temp);
				originalVillage = originalVillage.getNext();
			}//end village loop
			
			//loop to get ROADS
			originalVillage = original.firstVillage;
			Village proposalVillage = firstVillage;
			for (int i = 1; i <= original.getLength(); i++) { 
				//double check if villages are mapped correctly in case
				//if (originalVillage.getName() != proposalVillage.name) throws new ProposalVillageDoesNotMatchException;
				if ( originalVillage == proposalVillage ) { System.out.println("It's the same village! Deep copy goofed."); }
				if ( !originalVillage.outgoing.isEmpty() ) { 
					//loop through through and add road to proposal village
					RoadIterator oRoad = originalVillage.outgoing.firstRoad;
					for (int j = 1; j <= originalVillage.outgoing.length; j++ ) {
						proposalVillage.connect( oRoad.getCost() , find(oRoad.getData().end.getName()));
						oRoad = oRoad.getNext();
					}//end road loop
				}//end if
				originalVillage = originalVillage.getNext();
				proposalVillage = proposalVillage.getNext();
			}//end village loop
		}//end else
		
	}//end deep copy
	
	public void findMinSpanTree() {
		//Assumes input is connected graph
		//for now just adds roads to be built
		//Adds to list of roads to be made
		while ( length != connected.length) {}
	}
	
	public void addProposal (Village a, Village b, int cost) throws SameVillageException, RoadAlreadyExistsException, GraphEmptyException {
		find(a.getName()).connect( cost, find(b.getName()) );
	}
	
	public boolean findCycle (Road road) {
		if ( connected.find(road.end.getName()) == null ) return true;
		else return false;
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
	
	public Village find(int name) throws GraphEmptyException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (! isEmpty()) {
			Village current = this.firstVillage;
			while (current != null) {					
				if (current.getName() == name) {return current;}
				current = current.getNext();
			} 
			Village newVillage = new Village (true, name);
			insert( newVillage );
			return newVillage;
		} else {throw new GraphEmptyException();}
	}
	
	public boolean isEmpty() { return length == 0; }
	public int getLength() { return length; }

	public void printGraph() { // string representation of graph, used for testing
		System.out.println("Proposal graph representation!");
		if (! isEmpty()) {
			Village current = this.firstVillage;
			while (current != null) {
				System.out.println("(Proposal) Village " + current.getName() + " holds " + current.populationSize + " gnomes.");
				//System.out.println("   It leads to " + current.getAdjList());
				current = current.getNext();
			}
		} else {System.out.println("This graph is empty.");}
	} // end of printGraph()
	
	
	public class ConnectedGraph {
		private int length = 0;
		Village firstVillage;
		Village lastVillage;
		ProposalGraph proposal;
		
		//constructor
		public ConnectedGraph() {
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
		
		public Village find(int name) {
			if (! isEmpty()) {
				Village current = this.firstVillage;
				while (current != null) {					
					if (current.getName() == name) {return current;}
					current = current.getNext();
				} 
			} else return null;
		}//end find
			
	}//end proposalgraph
		
	public class pRoad {
		pRoad next, previous;
		int cost;
		Village starting, end;
		
		public pRoad(Village starting, Village end, int cost) {
			this.starting = starting;
			this.end = end;
			this.cost = cost;
		}
	}
		
	
}
