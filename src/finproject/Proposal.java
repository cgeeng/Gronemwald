package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;
import finproject.Exceptions.VillageNotFoundException;

public class Proposal {
	private int[] villages = new int[30]; //VILLAGES START AT INDEX 1
	private Edge[] roads = new Edge[50];
	private Edge[] toBuild;
	private int[] rank;
	
	private int p=0; //number of villages;
	private int q=0; //number of roads
	private int toBuildLength = 0;
	Graph original;
	
	public Proposal(Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//Takes existing graph structures and translates them into two arrays
		this.original = original;
		deepCopy(original);
	}
	
	//methods
	public void deepCopy(Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//Recreate villages and roads
		int[] tempVillage = new int[30];
		
		if (original.firstVillage == null) { System.out.println("I don't think your country exists!"); }
		else {
			p = original.getLength();
			
			Village originalVillage = original.firstVillage;
			for (int i =1 ; i <= original.getLength(); i++) { 
				//save village names
				tempVillage[i] = originalVillage.getName();
				//iterate through original Village linked list, CREATE EDGES
				if ( !originalVillage.outgoing.isEmpty() ) { 
					//loop through through and add road to proposal village
					RoadIterator oRoad = originalVillage.outgoing.firstRoad;
					
					for (int j = 1; j <= originalVillage.outgoing.length; j++ ) {
						roads[q] = new Edge( oRoad.startVillage().getName(), oRoad.endVillage().getName(), 100*oRoad.getCost() );
						q++;
						oRoad = oRoad.getNext();
										
					}//end road loop
				}//end if
				originalVillage = originalVillage.getNext();
			}//end village loop
			
			initializeVillages( tempVillage );
		}//end else
	}//end deep copy

	public boolean union (int u, int v) throws VillageNotFoundException {
		int root1 = find(u);
		int root2 = find(v);
		if (root1 == root2) return false;
		
		//check if rank of root1 is smaller		
		if ( rank[root1] > rank[root2] ) { 
			int t = root1;
			root1 = root2;
			root2 = t;
		}
		//if equal, deepen tree
		if ( rank[root1] == rank[root2] ) rank[root2]++;
		
		villages[root1] = root2;
		return true;
	}//end union
	
	public int find (int u) throws VillageNotFoundException { 
		//If village referenced by road doesn't exist, throw
		if ( villages[u] == 0 ) throw new VillageNotFoundException(u);
		//If village is not rooted at itself, find and return root
		if ( villages[u] != villages[villages[u]]) {
			villages[u] = find( villages[u]);
		}
		return villages[u];
	}//end find
	
	public void kruskal() {
		try {
			//Set up arrays
			toBuild = new Edge[p-1]; 
			rank = new int[p+1];
			for( int i = 1; i <= p; i++) rank[i] = 1; //initialize rank array
			sortEdges(); //sort roads by cost, lowest first
			
			//Go through roads while roads to be built don't exceed needed for minimum
			for (int i = 0; i < q && toBuildLength != q - 1; i++) {
				if( union( roads[i].u, roads[i].v ) ) {
					//no cycle, add road to be built
					toBuild[toBuildLength] = roads[i];
					toBuildLength++;
				}
			}//end for loop
		} catch (VillageNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}//end kruskal
	
	public void sortEdges() {
		boolean swapped = true;
		//if nothing was swapped during iteration, then everything sorted
		while (swapped) {
			swapped = false;
			for (int i = 0; i < q-1; i++) {
				if (roads[i].cost > roads[i+1].cost) {
					//swap if not smaller on left
					Edge temp = roads[i];
					roads[i] = roads[i+1];
					roads[i+1] = temp;
					swapped = true;
				}//end if
			}
		}//end while
	}//end sort edges
	
	public void printRoads() {
		//to test
		System.out.println("There are "+q+" roads.");
		for (int i = 0; i < q; i++) {
			System.out.println(roads[i].u+" to "+ roads[i].v+" cost "+roads[i].cost);
		}
	}//end printRoads
	public void printToBuild() {
		//to test
		System.out.println("To build "+toBuildLength+" roads.");
		for (int i = 0; i < toBuildLength; i++) {
			System.out.println(toBuild[i].u+" to "+ toBuild[i].v+" cost "+toBuild[i].cost);
		}
	}//end printToBuild
	
	public void printVillages() {
		//to test
		String temp = "";
		for (int i = 1; i <= p; i++) {
			if ( villages[i] != 0 ) temp+= villages[i]+" ";
		}
		System.out.println("Villages include: "+ temp);
	}//end printToBuild
	
	public Road[] getProposal() throws NotFoundException, GraphEmptyException {
		kruskal();
		Road[] transfer = new Road[toBuildLength];
		//create roads to be returned
		for (int i = 0; i < toBuildLength; i++) {
			transfer[i] = new Road( original.find(toBuild[i].u), original.find(toBuild[i].v), toBuild[i].cost);
		}
		return transfer;
	}//end getProposal
	
	public void initializeVillages( int[] temp ) {
		//Initialize villages[] by mapping to village names
		int i = 1;
		while ( temp[i] != 0 ){
			villages[ temp[i] ] = temp[i];
			i++;
		}
	}//end initializeVillages
	
	//edge class
	public class Edge {
		//two endpoints of road
		int u, v, cost;
		public Edge (int u, int v, int cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
	}//end Edge class
	
}//end Proposal class
