package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;

public class ProposalGraph {
	
	public PRoad[] toBuild = new PRoad[20]; //contains acceptable roads to be built after minimum spanning tree found
	public ConnectedGraph connected = new ConnectedGraph();
	public PriorityQueue pq = new PriorityQueue();
	public int length = 0;
	Village firstVillage;
	Village lastVillage;
	
	//constructor
	
	public ProposalGraph (Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		deepCopy(original);
	}
	public ProposalGraph() {

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
				if ( originalVillage == proposalVillage ) { System.out.println("It's the same village address! Deep copy goofed."); }
				if ( !originalVillage.outgoing.isEmpty() ) { 
					//loop through through and add road to proposal village
					RoadIterator oRoad = originalVillage.outgoing.firstRoad;
					for (int j = 1; j <= originalVillage.outgoing.length; j++ ) {
						//proposalVillage.connect( oRoad.getCost() , find(oRoad.getData().end.getName()));
						//For now don't actually connect villages, just add road to queue
						addProposal( proposalVillage, this.find(oRoad.getData().end.getName()), oRoad.getCost());
						oRoad = oRoad.getNext();
					}//end road loop
				}//end if
				originalVillage = originalVillage.getNext();
				proposalVillage = proposalVillage.getNext();
			}//end village loop
		}//end else
		
	}//end deep copy
	
	public void findMinSpanTree() throws GraphEmptyException {
		//Assumes input is CONNECTED graph
		int i = 0;
		while ( connected.length != length) {

			PRoad temp = pq.removeMin();
			System.out.println("temp cost is" + temp.cost);
			if ( !findCycle(temp) ) {
				toBuild[i] = temp;
				if (connected.isEmpty()) connected.insert(temp.starting);
				connected.insert(temp.end);
				//System.out.println("Road from Village " + toBuild[i].starting.getName() + " to " + toBuild[i].end.getName()+ " cost " + toBuild[i].cost);
				if(connected.find(temp.end.getName()) == null) connected.insert(temp.end);
				if(connected.find(temp.starting.getName()) == null) connected.insert(temp.starting);
			}
			i++;
			
		}//end while
		//toBuild should now contain all vertices
	}//end findMinSpanTree
	
	public void addProposal (Village a, Village b, int cost) throws SameVillageException, RoadAlreadyExistsException, GraphEmptyException {
		//only add roads connected to a village that has no roads in the real graph
		proposeRoad ( find(a.getName()), cost, find(b.getName()) ); //creates villages if they don't exist in proposal graph

	}
	
	public boolean findCycle (PRoad road) {
		//if ( connected.find(road.end.getName()) != null && connected.find(road.starting.getName()) != null) return true;
		//so wrong
		//djikstras?
		return false;
	}
	
	public PRoad proposeRoad( Village a, int cost, Village b) {
		PRoad newRoad = new PRoad(a, b, 100*cost);
		System.out.println("Road from Village " + newRoad.starting.getName() + " to "+newRoad.end.getName()+" is cost "+newRoad.cost);

		pq.insert(newRoad);
		System.out.println("Priority queue length is " + pq.length);
		return newRoad;
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
		}
			Village newVillage = new Village (true, name);
			insert( newVillage );
			return newVillage;

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
	
	public void printToBuild() {
		int i = 0;
		while (toBuild[i] != null) {
			System.out.println("Road from Village " + toBuild[i].starting.getName() + " to " + toBuild[i].end.getName()+ " cost " + toBuild[i].cost);
			i++;
		}
	}
	
	public class ConnectedGraph {
		int length = 0;
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
			}
			return null;
		}//end find
			
	}//end proposalgraph
	
	//classes


	public class PriorityQueue {
		int length;
		PRoad firstRoad;
		PRoad lastRoad;
		PRoad min;
		
		public PriorityQueue(){ // constructor
			this.length = 0;
		}
		
		public int length() {return this.length;}
		public PRoad getFirst() {return this.firstRoad;}
		public PRoad getLast() {return this.lastRoad;}
		public boolean isEmpty() {return length==0;}
		
		public PRoad find(PRoad r) throws NotFoundException {
			PRoad current = this.firstRoad;
			while (current != null) {					
				if (current.equals(r)) {return current;}
				current = current.next;
			} throw new NotFoundException();
		} // end of find()
		
		public void insert(PRoad RoadWithVillage){
			if(isEmpty()){
				firstRoad = RoadWithVillage;
				lastRoad = firstRoad;
			}
			else{
				lastRoad.next = RoadWithVillage;
				RoadWithVillage.previous = lastRoad;
				lastRoad = RoadWithVillage;
			}
			this.length++;
		}//end insert
		
		public PRoad removeMin() throws GraphEmptyException {
			setMin();
			PRoad temp = min;
			
			if (length == 1) {
				firstRoad = null;
				lastRoad = null;
				length--;
				return temp;
			}
			else if (temp == lastRoad) {
				temp.previous.next = null;
				lastRoad = temp.previous;
				length--;
				return temp;
			}
			else if (temp == firstRoad) {
				temp.next.previous = null;
				firstRoad = temp.next;
				length--;
				return temp;
			}
			temp.previous.next = temp.next;
			temp.next.previous = temp.previous;
			length--;
			return temp;
		}
		
		public void setMin() throws GraphEmptyException {
			min = firstRoad;
			//check if queue is empty
			if (isEmpty()) throw new GraphEmptyException();
			for (int i = 1; i <= length; i++) {
				if (min.next.cost < min.cost) {
					min = min.next;		
					System.out.println("new min " + min.cost);
				}
			}
		}
				
	}//end PriorityQueue
		
}
