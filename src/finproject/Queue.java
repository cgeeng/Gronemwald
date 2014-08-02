package finproject;

import finproject.Exceptions.*;

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
	
	public Village find(int name) throws NotFoundException, GraphEmptyException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (! isEmpty()) {
			Node current = this.firstNode;
			while (current != null) {						
				if (current.getVillage().getName() == name) {return current.getVillage();}
				current = current.getNext();
			} throw new NotFoundException();
		} else {throw new GraphEmptyException();}
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
	
	public Node remove(){ // default to last in queue	
		Node temp = firstNode;
		if (firstNode.getNext() != null) {
			firstNode = firstNode.getNext();
		} else {
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

}
