package finproject;

public class Village {
	static int totalVillage = 0;
	int name;
	AdjList adjacent;
	Gnome[] population = new Gnome[11]; //Limit of 10 gnomes can be here
	int outdegree;
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
	
	public int getOutdegree(){
		return outdegree=adjacent.length;
	}
	
	public Village() {
		this.name = ++totalVillage; //Create name starting from 1
		adjacent = new AdjList();
		
	}//end Constructor
	
	//methods
	
	public boolean connect (int cost, Village newNeighbor) {
		
		if (!adjacent.isEmpty()) { //if list not empty
			RoadIterator currentRoad = adjacent.firstRoad;
			do { //first check if road exists already
				if (currentRoad.getVillage() == newNeighbor) return false; 
				currentRoad = currentRoad.next;
			} while (currentRoad != null);
			
			Road newRoad = new Road (cost, newNeighbor );
			RoadIterator newRoadIt = new RoadIterator (newRoad);
			
			//update existing AdjList
			adjacent.lastRoad.next = newRoadIt;
			newRoadIt.previous = adjacent.lastRoad;
			adjacent.lastRoad = newRoadIt;

						
		}// end if
		else { //construct new road
			Road newRoad = new Road (cost, newNeighbor );
			RoadIterator newRoadIt = new RoadIterator (newRoad);
			
			adjacent.firstRoad = newRoadIt;
			adjacent.lastRoad = newRoadIt;
		}
		adjacent.length++;
		return true;
	}//connect
	
	public String getAdjList() {

		String roadList = "";
		RoadIterator current = this.adjacent.firstRoad;
		if (current == null)  roadList += "nowhere."; 
		else {
			while (current != null ) {
	
				roadList += "Village " + current.getVillageName() + ", cost " + current.getCost() + ", ";
				current = current.next;
				
			} //AdjList loop
		}
		return roadList;
	}
	
	
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
	

	
}//end Village class
