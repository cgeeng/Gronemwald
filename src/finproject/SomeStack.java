package finproject;

public class SomeStack {
	int length = 0;
	Village firstVillage; 
	Village lastVillage;
	
	
	//constructor
	public SomeStack() {
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
	
	public Village delete() {
		
		if (!isEmpty()) {
			Village temp = lastVillage;
			lastVillage = lastVillage.getPrev();
			length--;
			int i = 1;
			Village adj = temp.outgoing.firstRoad.endVillage();
			while (i <= temp.outgoing.length) {
				insert(adj);
				adj = temp.getNext();
			}
			return temp;
		}
		else return null;
	}
	
	public Village find(int name) {

		if (!isEmpty()) {
			Village current = this.firstVillage;
			for (int i = 1; i <= length; i++) {					
				if (current.getName() == name) {return current;}
				current = current.getNext();
			} 
		}
		return null;
	}//end find
	public boolean hasVisited (Village village) {
		System.out.println("hi");
		if (find(village.getName()) == null) {
			System.out.println("Village "+village.getName()+"has not been visited.");
			return false;
		}
		System.out.println("Village "+village.getName()+"has been visited.");
		return true;
	}
}
