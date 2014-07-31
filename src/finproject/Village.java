package finproject;

public class Village {
	static int totalVillage = 0;
	public int name;
	AdjList adjacent;
	Gnome[] population = new Gnome[11]; //Limit of 10 gnomes can be here
	int outdegree;
	int indegree;
	// TODO find and store indegree
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

	
	public Village() {
		this.name = ++totalVillage; //Create name starting from 1
		adjacent = new AdjList();
		outdegree = 0;
		indegree = 0;
		
	}//end Constructor
	
	//methods
	
	public void connect (int cost, Village newNeighbor) throws RoadAlreadyExistsException{

		try {
			if (!adjacent.isEmpty()) { //if list not empty				
					RoadIterator currentRoad = adjacent.firstRoad;
					do { //first check if road exists already
						if (currentRoad.getVillage() == newNeighbor) throw new RoadAlreadyExistsException(currentRoad.getCost(), this.name, newNeighbor.name); 
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
			adjacent.length++;
			
		} catch (RoadAlreadyExistsException e) { System.out.println(e.getMessage()); } 
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
	
	public int getIndegree(){	return indegree;	}
	public int getOutdegree(){	return outdegree=adjacent.length;	}
	
	//Class AdjList
	public class AdjList { // TODO should make this a linked list (insert and delete methods)
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
	
	public class RoadAlreadyExistsException extends Exception {
		public RoadAlreadyExistsException(int cost, int start, int end) {
			super("A road cost " + cost + " already connects Village " + start + " and " + end + "!");
		}//end default constructor
	}//end RoadAlreadyExistsException
	
}//end Village class
