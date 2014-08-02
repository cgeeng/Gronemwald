package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;

public class ProposalGraph {
	//DEEP copy of object Graph, STRIPPEd DOWN
	//will NOT be updated if Graph is updated
	//should probably deleted after proposals all made
	//I don't think indegrees out degrees are necessary for this
	
	public Road[] toBuild; //contains acceptable roads to be built after minimum spanning tree found
	
	public int length = 0;
	Village firstVillage;
	Village lastVillage;
	
	//constructor
	
	public ProposalGraph (Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		deepCopy(original);
	}
	
	//methods

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
		//uses priority queue
		//returns a Graph?
		//Adds to list of roads to be made
		//straight up COPY of textbook pseudocode for REFERENCE
		
		/*
		 * takes in list of EDGES, number of VERTICES
		 * new GRAPH
		 * PriorityQueue pq
		 * Graph gr
		 * 
		 * while graph.length() != numberVertices - 1 
		 * 
		 * road r = pq.deleteMin();
		 * Village u = get road home
		 * VIllage v = get road destination
		 * 
		 * if (u != v) { gr.add( r ); union stuff???
		 */
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
}
