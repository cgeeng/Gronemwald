package finproject;

public class Queue {
	private int length;
	private Node firstNode;
	private Node lastNode;
	
	public Queue(){ // constructor
		this.length = 0;
	}
	
	public int length() {return this.length;}
	public Node getFirst() {return this.firstNode;}
	public Node getLast() {return this.lastNode;}
	public boolean isEmpty() {return length==0;}
	
	public Village find(int name) { // TODO create binary search tree?
		try {
			if (! isEmpty()) {
				Node current = this.firstNode;
				while (current != null) {
					if (current.getVillage().getName() == name) {return current.getVillage();}
					current = current.getNext();
				} throw new NotFoundException("This village could not be found.");
			} else {System.out.println("This graph is empty."); return null;}
		} catch (NotFoundException e) {return null;}
	} // end of method find()
	
	public void insert(Node nodeWithVillage){
		if(isEmpty()){
			firstNode = nodeWithVillage;
			lastNode = firstNode;
		}
		else{
			lastNode.setNext(nodeWithVillage);
			lastNode = nodeWithVillage;
		}
		length++;
	}
	
	public Node remove(){
		Node temp = firstNode;
		if(firstNode.getNext() != null){
			firstNode = firstNode.getNext();
		}else{
			firstNode = null;
		}
		length--;
		return temp;
	}
	
	public void printGraph() { // string representation of graph, used for testing
		if (! isEmpty()) {
			Node current = this.firstNode;
			while (current != null) {
				System.out.println("Village " + current.getVillage().getName() + " holds " + current.getVillage().populationSize + " gnomes.");
				System.out.println("   It leads to " + current.getVillage().getAdjList());
				
				current = current.getNext();
			}
		} else {System.out.println("This graph is empty.");}
	} // end of printGraph()
	
	public class NotFoundException extends Exception {
		public NotFoundException(String message) {
			super(message);
		}//end default constructor
	}//end RoadAlreadyExistsException
}
