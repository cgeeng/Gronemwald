package finproject;

public class SomeStack {
	int length = 0;
	Node firstVillage; 
	Node lastVillage;
	
	
	//constructor
	public SomeStack() {
		firstVillage = null;
		lastVillage = null;
	} 
	
	public boolean isEmpty() {return length == 0;}
	public int getLength() {return length;}
	public Node getFirst() {return this.firstVillage;}
	public Node getLast() {return this.lastVillage;}
	
	public void insert ( Node newNode ) {
		//Node newNode = new Node(newVillage);
		if (isEmpty()) {
			firstVillage = newNode;
			lastVillage = newNode;
		}
		else {
			lastVillage.setNext(newNode);
			newNode.setPrev(lastVillage);
			lastVillage = newNode;
		}
		System.out.println("added to stack village "+newNode.getVillage().getName());
		length++;
	}
	
	public Node delete(Node predecessor) {
		
		if (!isEmpty()) {
			Node temp = lastVillage;
			lastVillage = lastVillage.getPrev();
			length--;
			int i = 1;
			System.out.println("popped "+temp.getVillage().getName());
			//If Village has roads built out of it, add the end destination villages to the Stack
			//Don't add if the village's parent is the same as the road's end destination
			if (!temp.getVillage().outgoing.isEmpty()) {
				RoadIterator adj = temp.getVillage().outgoing.firstRoad;
				
				while (i <= temp.getVillage().outgoing.length) {
					//if (!temp.getVillage().outgoing.proposalFindRoad(predecessor.getVillage()) ) {
					if (temp.predecessor == null) insert(new Node (adj.endVillage(), predecessor.getVillage()) ); 
					else if (adj.endVillage().getName() != temp.predecessor.getName()){
							insert(new Node (adj.endVillage(), predecessor.getVillage()) ); 
					}
					adj = adj.getNext();
					i++;
				}
			}
			System.out.println("end adding popped' adj.");

			return temp;
		}
		else return null;
	}

	
	public Node find(int name) {

		if (!isEmpty()) {
			Node current = this.firstVillage;
			for (int i = 1; i <= length; i++) {					
				if (current.getVillage().getName() == name) {return current;}
				current = current.getNext();
			} 
		}
		return null;
	}//end find
	public void print() {
		Node temp = firstVillage;
		System.out.println("Stack length"+length);
		for (int i = 1; i <= length; i++) { //Loops forever with while loop checking null
			System.out.println("In Stack: Village "+temp.getVillage().getName());
			temp = temp.getNext();
		}
	}
	
}
