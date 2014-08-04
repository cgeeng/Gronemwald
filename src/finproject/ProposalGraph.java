package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;
import finproject.Exceptions.UnconnectedGraphException;

public class ProposalGraph {
	
	public PRoad[] toBuild = new PRoad[20]; //contains acceptable roads to be built after minimum spanning tree found
	int toBuildLength = 0;
	public PriorityQueue pq = new PriorityQueue();
	public int length = 0;
	Node firstVillage;
	Node lastVillage;
	
	//constructor
	
	public ProposalGraph (Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		deepCopy(original);
	}
	public ProposalGraph() {

	}
	/*
	 * 		
		
		Gr.insert( new Village());
		Gr.insert( new Village());
		Gr.insert( new Village());
		//Gr.insert( new Village());
		//Gr.insert( new Village());
		
		Gr.find(1).connect(2, Gr.find(3));
		Gr.find(1).connect(7, Gr.find(2));
		Gr.find(2).connect(4, Gr.find(3));
		
		//Gr.find(4).connect(8, Gr.find(2));
		//Gr.find(4).connect(9, Gr.find(1));
		//Gr.find(4).connect(10, Gr.find(3));
		ProposalGraph proposal = new ProposalGraph(Gr); 

		proposal.findMinSpanTree();
	 */
	
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
			Node proposalVillage = firstVillage;
			for (int i = 1; i <= original.getLength(); i++) { 
				//double check if villages are mapped correctly in case
				//if (originalVillage.getName() != proposalVillage.name) throws new ProposalVillageDoesNotMatchException;
				
				if ( !originalVillage.outgoing.isEmpty() ) { 
					//loop through through and add road to proposal village
					RoadIterator oRoad = originalVillage.outgoing.firstRoad;
					
					for (int j = 1; j <= originalVillage.outgoing.length; j++ ) {
						//proposalVillage.connect( oRoad.getCost() , find(oRoad.getData().end.getName()));
						//For now don't actually connect villages, just add road to queue
						//proposalVillage.connect(oRoad.getCost(), find(oRoad.endVillage().getName()) );
						proposeRoad( proposalVillage.getVillage(), oRoad.getCost(), find(oRoad.endVillage().getName()).getVillage() ) ;
						oRoad = oRoad.getNext();
										
					}//end road loop
				}//end if
				originalVillage = originalVillage.getNext();
				proposalVillage = proposalVillage.getNext();
			}//end village loop

		}//end else

	}//end deep copy
	
	public void findMinSpanTree() throws GraphEmptyException, UnconnectedGraphException, SameVillageException, RoadAlreadyExistsException, NotFoundException {
		//Assumes input is CONNECTED graph
		int i = 0;
		printGraph();
		while ( toBuildLength < length ) {

			PRoad temp = pq.removeMin();
			
			System.out.println("pq length now "+pq.length+". min cost "+temp.cost);
			Node tempStart = find( temp.starting.getName() );
			
			System.out.println("creating road from PQ road village "+tempStart.getVillage().getName()+
					""+temp.end.getName() );

			Road newRoad = tempStart.getVillage().proposalConnect( temp.cost, find( temp.end.getName() ).getVillage());
			
			

			
			if (findCycle(tempStart)) {
				find( temp.starting.getName() ).getVillage().proposalDelete(newRoad);

			}
			else {	
				toBuild[i] = temp;
				toBuildLength++;
				i++;
			}//end if	
			System.out.println("One find cycle passed");
			//printToBuild();
			pq.print();
			
			}//end while
		printToBuild();
		System.out.println("finding min tree done.");
		//toBuild should now contain all vertices
	}//end findMinSpanTree
	
	/*Bad things happen here.
	 * public void addProposal (Village a, Village b, int cost) throws SameVillageException, RoadAlreadyExistsException, GraphEmptyException {
		//only add roads connected to a village that has no roads in the real graph
		
		proposeRoad ( find(a.getName()), cost, find(b.getName()) ); //creates villages if they don't exist in proposal graph
	} */
	public PRoad proposeRoad( Village a, int cost, Village b) {
		PRoad newRoad = new PRoad(a, b, 100*cost);
		
		pq.insert(newRoad);
		return newRoad;
	}
	
	public boolean findCycle (Node start) {
		SomeStack traverse = new SomeStack();
		ConnectedGraph connected = new ConnectedGraph();
		Node current = start;
		

		//Insert first village into stack
		traverse.insert(new Node (current.getVillage() ) );
		Node previous = new Node (current.getVillage());
		int i = 1;
		while (!traverse.isEmpty()) {			
			//Delete top of stack
			//This should insert whatever was adjacent to the top
			//but doesn't reinsert the predecessor			
			Node temp = traverse.delete(previous);
			System.out.println("rpevious village"+previous.getVillage().getName());
			//if in connected graph and if not one road cycle
			if ( connected.find(temp.getVillage().getName())) {
				System.out.println("found cycle");
				return true;
			}
			else connected.insert(temp.getVillage());
			previous.setVillage( temp.getVillage() );
			System.out.println("loop finished "+i);
			i++;
			//connected.print();
			//traverse.print();
		} 
		return false;
	}
	
	public boolean oneRoadCycle(Village previous, Village stackPop) {
		//Checks if the cycle found is because two villages connected via one road
		RoadIterator what = previous.outgoing.firstRoad;
		for (int j = 1; j <= previous.outgoing.length; j++) {
			System.out.println("village "+previous.getName()+" ahs roads to"+what.endVillage().getName());
			System.out.println("j is "+j);
			what=what.getNext();
		}

		
		return previous.outgoing.proposalFindRoad(stackPop);
	}

	//generic methods
	public Node getFirst() {return this.firstVillage;}
	public Node getLast() {return this.lastVillage;}
	
	public void insert ( Village newVillage ) {
		Node newNode = new Node(newVillage);
		if (isEmpty()) {
			firstVillage = newNode;
			lastVillage = newNode;
		}
		else {
			lastVillage.setNext(newNode);
			newNode.setPrev(lastVillage);
			lastVillage = newNode;
		}
		length++;
	}
	
	public Node find(int name) throws GraphEmptyException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (! isEmpty()) {
			Node current = this.firstVillage;
			for (int i = 1; i <= length; i++) {					
				if (current.getVillage().getName() == name) {return current;}
				current = current.getNext();
			} 
		}
		return null;

	}
	
	public boolean isEmpty() { return length == 0; }
	public int getLength() { return length; }

	public void printGraph() { // string representation of graph, used for testing
		System.out.println("Proposal graph representation! Length "+length);
		if (! isEmpty()) {
			Node current = this.firstVillage;
			int i = 1;
			while (i <= length) {
				System.out.println("(Proposal) Village " + current.getVillage().getName() + ". PGraph length is " + length);
				//System.out.println("   It leads to " + current.getAdjList());
				current = current.getNext();
				i++;
			}
		} else {System.out.println("This graph is empty.");}
	} // end of printGraph()
	
	
	public void printToBuild() {
		System.out.println("To build:");
		int i = 0;
		while (toBuild[i] != null) {
			System.out.println("Road from Village " + toBuild[i].starting.getName() + " to " + toBuild[i].end.getName()+ " cost " + toBuild[i].cost);
			i++;
		}
	}
	//classes
	public class ConnectedGraph {
		int length = 0;
		Village firstVillage; 
		Village lastVillage;
		
		
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
		
		public boolean find(int name) {

			if (!isEmpty()) {
				Village current = this.firstVillage;
				for (int i = 1; i <= length; i++) {					
					if (current.getName() == name) {return true;}
					current = current.getNext();
				} 
			}
			return false;
		}//end find
		public void print() {
			Village temp = firstVillage;
			System.out.println("Connected length"+length);
			for (int i = 1; i <= length; i++) { //Loops forever with while loop checking null
				System.out.println("In connected graph: Village "+temp.getName());
				temp = temp.getNext();
			}
		}
		

		
	}//end proposalgraph
	

	public class PRoad {
		PRoad next, previous;
		int cost;
		Village starting, end;
		
		public PRoad(Village starting, Village end, int cost) {
			this.starting = starting;
			this.end = end;
			this.cost = cost;
		}
	}//end Road class

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
		
		public PRoad find(Road r) throws NotFoundException {
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
			length++;
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
				lastRoad = temp.previous;
				lastRoad.next = null;
				length--;
				return temp;
			}
			else if (temp == firstRoad) {
				temp.next.previous = null;
				firstRoad = temp.next;
				length--;
				return temp;
			}
			else {
				temp.previous.next = temp.next;
				temp.next.previous = temp.previous;
				length--;
				return temp;
			}
			
		}
		
		public void setMin() throws GraphEmptyException {
			min = firstRoad;
			PRoad temp = firstRoad;
			//check if queue is empty
			if (isEmpty()) throw new GraphEmptyException();
			while (temp!=null) {
				if (min.cost > temp.cost) min = temp;
				temp = temp.next;
			}
			//System.out.println("min cost is "+min.cost);
		}
				
		public void print() {
			PRoad temp = this.firstRoad;
			System.out.println("PQ length is"+length);
			while (temp != null) {
				System.out.println("Road from Village " + temp.starting.getName()+" to "+temp.end.getName()+"cost "+temp.cost);
				temp = temp.next;
			}
		}//end print
	
	}//end PriorityQueue
	
		
}
