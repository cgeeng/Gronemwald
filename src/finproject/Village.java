package finproject;

import finproject.Exceptions.*;

public class Village {
	static int totalVillage = 0;
	
	private int name;
	AdjList incoming, outgoing;
	Gnome [] population = new Gnome[10]; // limit of 10 gnomes
	int populationSize;
	int indegree, outdegree;
	private Village next, previous;
	
	public int getName() {return this.name;}	
	public Village getNext() {return this.next;}
	public void setNext(Village n) {this.next = n;}
	public Village getPrev() {return this.previous;}
	public void setPrev(Village p) {this.previous = p;}
	public boolean isEmpty() {return this.populationSize == 0;}
	public boolean isFull() {return this.populationSize == 10;}
	
	public Village() {
		this.name = ++totalVillage;
		outgoing = new AdjList(); incoming = new AdjList();
		outdegree = 0;
		indegree = 0;
		
	}//end Constructor
	
	public Village(int population) throws VillageFullException { // for testing
		this.name = ++totalVillage;
		outgoing = new AdjList(); incoming = new AdjList();
		outdegree = 0;
		indegree = 0;
		
		for (int i=0; i<population; i++) {this.insertGnome(new Gnome());}
	}
	
	//methods
	
	public void connect (int cost, Village newNeighbor) throws SameVillageException, RoadAlreadyExistsException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (this.equals(newNeighbor)) {throw new SameVillageException();}			
		RoadIterator currentRoad = outgoing.firstRoad;
		do { //first check if road exists already
			if (currentRoad.endVillage() == newNeighbor) {throw new RoadAlreadyExistsException(currentRoad.getCost(), this.name, newNeighbor.name);} 
			currentRoad = currentRoad.getNext();
		} while (currentRoad != null);

		RoadIterator newRoadIt = new RoadIterator (new Road(this, newNeighbor, cost));
					
		outgoing.insert(newRoadIt);	
		newNeighbor.incoming.insert(newRoadIt);
		
		newNeighbor.indegree++;
		this.outdegree++;
	}//end connect
	
	public Gnome removeGnome(Gnome g) throws VillageEmptyException {
		if (isEmpty()) {throw new VillageEmptyException();}
		else {
			int gIndex=10; // will be replaced by wanted gnome's index, unreachable in for loop otherwise
			for (int i=0; i<populationSize; i++) {
				if (population[i].getID() == g.getID()) {
					population[i] = null; g.setVillage(null);
					gIndex=i;}
				if (i>gIndex) {population[i-1] = population[i];}
			}
			this.populationSize--;
			return g;
		}
	} // end of removeGnome()
	
	public void insertGnome(Gnome g) throws VillageFullException {
		if (isFull()) {throw new VillageFullException();}
		else {
			int nextIndex=0; // next open index
			for (int i=0; i<populationSize; i++) {if (population[i] != null) {nextIndex++;}}
			population[nextIndex] = g;
			g.setVillage(this);
			this.populationSize++;
		}
	} // end of insertGnome()
	
	public Gnome find(int gnomeID) throws VillageEmptyException, NotFoundException { // finds gnome with given ID
		if (isEmpty()) {throw new VillageEmptyException();}
		for (int i=0; i<populationSize; i++) {
			if (population[i].getID() == gnomeID) {return population[i];}
		} throw new NotFoundException();
	} // end of method find()
	
	public void printGnomes() { // string representation of gnomes in village, used for testing
		String gnomes = "";
		for (int i=0; i<populationSize; i++) {gnomes += population[i].getID() + "  ";}
		System.out.println("Village " + this.name + " has gnomes: " + gnomes);
	} // end of printGnomes()
	
	public String getAdjList() {
		String roadList = "";
		
		RoadIterator current = this.outgoing.firstRoad;
		if (current == null)  roadList += "nowhere."; 
		else {
			while (current != null ) {
	
				roadList += "Village " + current.endVillage().getName() + ", cost " + current.getCost() + ", ";
				current = current.getNext();
				
			} //AdjList loop
		}
		return roadList;
	}//end getAdjList()
	
	
	//Class AdjList
	public class AdjList {
		RoadIterator firstRoad;
		RoadIterator lastRoad;
		int length = 0;
				
		//constructors
		public AdjList () {
			firstRoad = null;
			lastRoad = null;
		}
		
		public void insert(RoadIterator ri){
			if (isEmpty()) {
				firstRoad = ri;
				lastRoad = ri;
			}
			else {
				lastRoad.setNext(ri);
				ri.setPrev(lastRoad);
				lastRoad = ri;
			}
			length++;
		}
		
		public void delete(RoadIterator ri) {
			if (ri.getData().getStart() != ri.getStart()) {Village end = ri.getData().end;}
			if (length == 1) {
				this.firstRoad = null;
				this.lastRoad = null; }
			else {
				if (ri.equals(firstRoad)) {
					ri.getNext().setPrev(null);
					firstRoad = ri.getNext();}
				else if (ri.equals(lastRoad)) {
					lastRoad = ri.getPrev(); 
					lastRoad.setNext(null);}
				else {
					ri.getPrev().setNext(ri.getNext());
					ri.getNext().setPrev(ri.getPrev());
				}}
			end.incoming.delete(ri);
			this.length--;
		}
		
	} // end of AdjList
	
	
}//end Village class
