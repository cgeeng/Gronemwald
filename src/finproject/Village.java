package finproject;

import finproject.Exceptions.*;
import java.util.Random;

public class Village {
	static int totalVillage = 0;
	
	private int name;
	AdjList incoming, outgoing, outgoingTopSort;
	int populationSize, populationLimit=10; // limit varies, default is 10
	Gnome [] population = new Gnome[populationLimit]; // limit varies
	int indegree, outdegree;
	private Village next, previous;
	boolean forProposal = false;
	boolean visited = false;
	Village prior;
	int visitIn;
	int priorCost;
	String color;
	String edgeType = "";
	Village predecess;
	Village following;
	
	public int getName() {return this.name;}	
	public Village getNext() {return this.next;}
	public void setNext(Village n) {this.next = n;}
	public Village getPrev() {return this.previous;}
	public void setPrev(Village p) {this.previous = p;}
	public boolean isEmpty() {return this.populationSize == 0;}
	public boolean isFull() {return this.populationSize == 10;}
	
	public Village() throws VillageFullException {
		this(0);
	}//end Constructor
	
	
	public Village(boolean forProposal, int name) {
		//CONSTRUCTOR FOR PROPOSALGRAPH
		this.name = name;
		this.forProposal = forProposal;
		outgoing = new AdjList(); incoming = new AdjList();
		outdegree = 0;
		indegree = 0;
		
	}//end Constructor
	
	public Village(int population) throws VillageFullException { // for testing
		this.name = ++totalVillage;
		outgoing = new AdjList(); incoming = new AdjList();
		outdegree = 0;
		indegree = 0;
		
		Random rand = new Random();
		this.populationLimit = 5+rand.nextInt(10); // random between 5 and 15
		for (int i=0; i<population; i++) {this.insertGnome(new Gnome());}
	}
	
	//methods
	
	public synchronized Road connect (int cost, Village newNeighbor) throws SameVillageException, RoadAlreadyExistsException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (this.equals(newNeighbor)) {throw new SameVillageException();}			
		if (! outgoing.isEmpty()) {
			RoadIterator currentRoad = outgoing.firstRoad;
			while (currentRoad != null) { // first check if road exists already
				if (currentRoad.endVillage() == newNeighbor) {
					throw new RoadAlreadyExistsException(this, newNeighbor);
				} currentRoad = currentRoad.getNext();
			}
		}
		Road newRoad = new Road(this, newNeighbor, cost);
	
		this.outgoing.insert(newRoad);	
		newNeighbor.incoming.insert(newRoad);
		
		this.outdegree++;
		newNeighbor.indegree++;
		
		return newRoad;
	}//end connect
	
	public synchronized Road proposalConnect (int cost, Village newNeighbor) throws SameVillageException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (this.equals(newNeighbor)) {throw new SameVillageException();}			

		Road newRoad = new Road(this, newNeighbor, cost);
		
		this.outgoing.insert(newRoad);	
		newNeighbor.outgoing.insert(new Road(newNeighbor,this,cost));
		
		this.outdegree++;
		newNeighbor.outdegree++;
		System.out.println(this.outgoing.getLast().endVillage().getName()+" road to "+newNeighbor.outgoing.getLast().endVillage().getName());

		return newRoad;
	}//end connect
	
	public synchronized void proposalDelete(Road r) throws NotFoundException {
		outgoing.delete(r);
		this.outdegree--;
		r.start.outgoing.delete(r);
		r.start.outdegree++;
	}
	
	
	public synchronized void deleteOutRoad(Road r) throws NotFoundException {
		outgoing.delete(r);
		this.outdegree--;
	}
	
	public synchronized void deleteInRoad(Road r) throws NotFoundException {
		incoming.delete(r);
		this.indegree--;
	}
	
	public synchronized Gnome removeGnome(Gnome g) throws VillageEmptyException {
		if (isEmpty()) {throw new VillageEmptyException();}
		else {
			int gIndex=10;
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
	
	public synchronized void insertGnome(Gnome g) throws VillageFullException {
		if (isFull()) {throw new VillageFullException();}
		else {
			int nextIndex=0; // next open index
			for (int i=0; i<populationSize; i++) {if (population[i] != null) {nextIndex++;}}
			population[nextIndex] = g;
			g.setVillage(this);
			g.getVisited().insert(new Node(this));
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
		for (int i=0; i<populationSize; i++) {gnomes += "g" + population[i].getID() + "  ";}
		System.out.println("Village " + this.name + " has gnomes: " + gnomes);
	} // end of printGnomes()
	
	public String getAdjList() {
		String roadList = "";
		
		if (this.outgoing.isEmpty()) {roadList+="nowhere.";}
		RoadIterator current = this.outgoing.firstRoad;
		while (current != null ) {
			roadList += "Village " + current.endVillage().getName() + " (cost " + current.getCost();
			if (current.equals(this.outgoing.lastRoad)) {roadList += ")";}
			else {roadList += "), ";}
			current = current.getNext();
		}

		return roadList;
	}//end getAdjList()
	
	class AdjList {
		RoadIterator firstRoad;
		RoadIterator lastRoad;
		int length = 0;
				
		//constructors
		public AdjList () {
			firstRoad = null;
			lastRoad = null;
		}
		
		public boolean isEmpty() {return this.length == 0;}
		public RoadIterator getFirst() {return this.firstRoad;}
		public RoadIterator getLast() {return this.lastRoad;}
		
		public synchronized void insert(Road r) {
			RoadIterator ri = new RoadIterator(r);
			if (isEmpty()) {
				firstRoad = ri;
				lastRoad = ri;
			} else {
				lastRoad.setNext(ri);
				ri.setPrev(lastRoad);
				lastRoad = ri;
			}
			length++;
		} // end of insert()
		
		public synchronized void delete(Road r) throws NotFoundException {
			if (length < 2) {
				this.firstRoad = null;
				this.lastRoad = null;}
			else {
				RoadIterator ri = find(r);
				if (ri.equals(firstRoad)) {
					ri.getNext().setPrev(null);
					firstRoad = ri.getNext();}
				else if (ri.equals(lastRoad)) {
					ri.getPrev().setNext(null); 
					lastRoad = ri.getPrev();}
				else {
					ri.getPrev().setNext(ri.getNext());
					ri.getNext().setPrev(ri.getPrev());
				}}
			this.length--;
		} // end of delete()
		
		public synchronized RoadIterator find(Road r) throws NotFoundException {
			RoadIterator current = this.firstRoad;
			while (current != null) {					
				if (current.getData().equals(r)) {return current;}
				current = current.getNext();
			} throw new NotFoundException();
		} // end of find()
		
		public synchronized Road findRoad(Village v) throws NotFoundException {
			// finds road leads to or from given village
			if (! isEmpty()) {
				RoadIterator current = getFirst();
				while(current != null) {
					if (v.equals(current.getData().start) || v.equals(current.getData().end)) {return current.getData();}
					current = current.getNext();
				}
			} return null; // throw new NotFoundException();
		} // end of findRoad()
	
	public boolean proposalFindRoad(Village v) {
		// finds road leads to or from given village
		if (! isEmpty()) {
			RoadIterator current = getFirst();
			while(current != null) {
				if (v.equals(current.getData().start) || v.equals(current.getData().end)) {return true;}
				current = current.getNext();
			}
		} return false;
	} // end of findRoad()

	} // end of AdjList
	
}//end Village class
