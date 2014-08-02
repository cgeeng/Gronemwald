package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.*;

public class PriorityQueue {
	private int length;
	private Node firstNode;
	private Node lastNode;
	
	public PriorityQueue(){ // constructor
		this.length = 0;
	}
	
	public int length() {return this.length;}
	public Node getFirst() {return this.firstNode;}
	public Node getLast() {return this.lastNode;}
	public boolean isEmpty() {return length==0;}
	
	public Village find(int name) throws NotFoundException, GraphEmptyException { // TODO create binary search tree?
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
	}//end insert
	
	public void removeMin() {}
	
}//end class
