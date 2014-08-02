package finproject;
import finproject.Exceptions.*;

public class Graph {
	private int length = 0;
	Village firstVillage;
	Village lastVillage;
	
	//constructor
	public Graph() {
		firstVillage = null;
		lastVillage = null;

	} 
	
	public void insert ( Village newVillage ) {
		
		if (isEmpty()) {
			firstVillage = newVillage;
			lastVillage = newVillage;
		}
		else {
			lastVillage.next = newVillage;
			newVillage.previous = lastVillage;
			lastVillage = newVillage;
		}
		length++;
	}
	
	public void delete(int villageName){
		Village villToDelete = findVillage(villageName); 
		if(villToDelete.outdegree > 0){
			RoadIterator ri = villToDelete.adjacent.firstRoad;
			while(ri != null){
				ri.getVillage().indegree--;
				ri = ri.next;
			}
		}
		Village villLeadingTo = firstVillage;
		while(villLeadingTo != null){
			if(villLeadingTo.outdegree > 0){
				RoadIterator ri = villLeadingTo.adjacent.firstRoad;
				while(ri != null){
					if(ri.getVillage() == villToDelete){
						//remove(ri); // MAKE METHOD TO REMOVE A ROADITERATOR
					}
					ri = ri.next;
				}
			}
			villLeadingTo = villLeadingTo.next;
		}
	} // end of delete method
	
	public Village findVillage(int name) {
		Village temp = firstVillage;
		Village found = temp;
		try {
			while (temp!= null) {
				if (temp.getName() == name) found = temp;
				temp = temp.next;
			}
			if ( found.getName() != name) { throw new VillageNotFoundException(name); }
		} catch (VillageNotFoundException e) { System.out.println(e.getMessage()); }
		return found;
	}
	
	public boolean isEmpty() { return length == 0; }
	public int getLength() { return length; }
	


}//end VillageList class
