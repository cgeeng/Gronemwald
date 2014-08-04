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
		System.out.println("added to stack village "+newVillage.getName());
		length++;
	}
	
	public Village delete() {
		
		if (!isEmpty()) {
			Village temp = lastVillage;
			lastVillage = lastVillage.getPrev();
			length--;
			int i = 1;
			System.out.println("popped "+temp.getName());
			
			if (!temp.outgoing.isEmpty()) {
				RoadIterator adj = temp.outgoing.firstRoad;

				while (i <= temp.outgoing.length) {
					
					insert(adj.endVillage());
					adj = adj.getNext();
					i++;
				}
			}
			System.out.println("end adding popped' adj.");

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
	public void print() {
		Village temp = firstVillage;
		System.out.println("Stack length"+length);
		for (int i = 1; i <= length; i++) { //Loops forever with while loop checking null
			System.out.println("In Stack: Village "+temp.getName());
			temp = temp.getNext();
		}
	}
	
}
