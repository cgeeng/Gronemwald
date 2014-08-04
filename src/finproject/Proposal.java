package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;

public class Proposal {
	int[] villages = new int[20]; //VILLAGES START AT INDEX 1
	Edge[] roads = new Edge[50];
	int p=0; //number of villages;
	int q=0; //number of roads
	Edge[] toBuild;
	int toBuildLength = 0;
	int[] rank;
	
	public Proposal(Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//Takes existing graph structures and translates them into two arrays
		deepCopy(original);
	}
	
	//methods
	public void deepCopy(Graph original) throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//Recreate villages first
		//Then create roads
		if (original.firstVillage == null) { System.out.println("I don't think your country exists!"); }
		else {
			p = original.getLength();
			
			Village originalVillage = original.firstVillage;
			for (int i =1 ; i <= original.getLength(); i++) { 
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
		}//end else
	}//end deep copy

	public boolean union (int u, int v) {
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
	}
	
	public int find (int u) { 
		if ( villages[u] != villages[villages[u]]) villages[u] = find( villages[u]);
		return villages[u];
	}
	
	public void kruskal() {
		toBuild = new Edge[p-1]; 
		rank = new int[p+1];
		for( int i = 1; i <= p; i++) villages[i] = i; //INDEX ZERO LEFT EMPTY
		for( int i = 1; i <= p; i++) rank[i] = 1; //initialize rank array
		sortEdges(); //sort roads by cost, lowest first
		
		for (int i = 0; i < q && toBuildLength != q - 1; i++) {
			if( union( roads[i].u, roads[i].v ) ) {
				//no cycle, add road to be built
				toBuild[toBuildLength] = roads[i];
				toBuildLength++;
			}
		}//end for loop
	}
	
	public void sortEdges() {
		boolean swapped = true;
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
	}
	
	//public Road[] returnProposal(){
		//creates array of Roads using data in toBuilt array
	//}
	
	public void printRoads() {
		System.out.println("There are "+q+" roads.");
		for (int i = 0; i < q; i++) {
			System.out.println(roads[i].u+" to "+ roads[i].v+" cost "+roads[i].cost);
		}
	}
	public void printToBuild() {
		System.out.println("To build "+toBuildLength+" roads.");
		for (int i = 0; i < toBuildLength; i++) {
			System.out.println(toBuild[i].u+" to "+ toBuild[i].v+" cost "+toBuild[i].cost);
		}
	}

	
	//edge class
	public class Edge {
		//two endpoints of road
		int u, v, cost;
		public Edge (int u, int v, int cost) {
			this.u = u;
			this.v = v;
			this.cost = cost;
		}
		//needs comparator
	}
}//end Proposal class
