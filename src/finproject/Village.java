package finproject;

import finproject.Exceptions.*;

public class Village {
	static int totalVillage = 0;
	public int name;
	AdjList adjacent;
	Gnome[] population = new Gnome[11]; //Limit of 10 gnomes can be here
	int populationSize;
	int outdegree;
	int indegree;
	//int currentPopulation = 1;
	//int maxPopulation = 3;
	//static int defaultPopulation;
	
	//Constructors
	/*public Village ( int currentPopulation, int maxPopulation) {
		this.currentPopulation = currentPopulation;
		this.maxPopulation = maxPopulation;
		this.name = ++totalVillage;
	}//end Village constructor
	
	public Village (int currentPopulation) {
		this(currentPopulation, defaultPopulation);
	}//end Village constructor
	*/
	
	public int getName() {return this.name;}	
	public int getIndegree(){return indegree;}
	public int getOutdegree(){return outdegree;} // = adjacent.length?
	
	public Village() {
		this.name = ++totalVillage; //Create name starting from 1
		adjacent = new AdjList();
		outdegree = 0;
		indegree = 0;
		
	}//end Constructor
	
	public Village(int population) { // for testing
		this.name = ++totalVillage;
		adjacent = new AdjList();
		outdegree = 0;
		indegree = 0;
		
		for (int i=0; i<population; i++) {Gnome temp = new Gnome(this);}
	}
	
	//methods
	
	public void connect (int cost, Village newNeighbor) throws SameVillageException, RoadAlreadyExistsException {
		// exceptions caught by MapGUI so pop-up error message can be generated
			if (this.equals(newNeighbor)) {throw new SameVillageException();}
			if (!adjacent.isEmpty()) { //if list not empty				
					RoadIterator currentRoad = adjacent.firstRoad;
					do { //first check if road exists already
						if (currentRoad.getVillage() == newNeighbor) {throw new RoadAlreadyExistsException(currentRoad.getCost(), this.name, newNeighbor.name);} 
						currentRoad = currentRoad.next;
					} while (currentRoad != null);
					//Construct new road
					Road newRoad = new Road (cost, newNeighbor ); 
					RoadIterator newRoadIt = new RoadIterator (newRoad);
					
					//update existing AdjList
					adjacent.lastRoad.next = newRoadIt;
					newRoadIt.previous = adjacent.lastRoad;
					adjacent.lastRoad = newRoadIt;	
			}// end if
			else { //list empty, construct new road
				Road newRoad = new Road (cost, newNeighbor );
				RoadIterator newRoadIt = new RoadIterator (newRoad);
				
				adjacent.firstRoad = newRoadIt;
				adjacent.lastRoad = newRoadIt;
			}//end else
			newNeighbor.indegree++;
			this.outdegree++;
			adjacent.length++;
		
	}//end connect
	
	public String getAdjList() {

		String roadList = "";
		RoadIterator current = this.adjacent.firstRoad;
		if (current == null)  roadList += "nowhere."; 
		else {
			while (current != null ) {
	
				roadList += "Village " + current.getVillage().getName() + ", cost " + current.getCost() + ", ";
				current = current.next;
				
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
		
		//methods
		public boolean isEmpty() { return length == 0;}
	}
	
//exceptions
	
}//end Village class
