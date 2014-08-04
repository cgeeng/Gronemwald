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
	
	public Village find2(int name) {
		Node current = this.firstNode;
		while (current != null) {						
			if (current.getVillage().getName() == name) {
				return current.getVillage();
			}
			current = current.getNext();
		} 
		return null;
	} // end of method find()
	
	public void insertLikeStack(Node n){
		if(isEmpty()){
			firstNode = n;
			lastNode = n;
		} else {
			n.setNext(firstNode);
			firstNode.setPrev(n);
			firstNode = n;
		}
		length++;
	}
	
	public Node delete(Village toDelete){ 
		Node removed = firstNode;
		while(removed.getVillage() != toDelete){
			removed = removed.getNext();
		}
		if(removed == firstNode & removed == lastNode){
			firstNode = null;
			lastNode = null;
			//firstNode.setNext(null);
			//firstNode.setPrev(null);
			//lastNode.setNext(null);
			//lastNode.setPrev(null);
		}else if(removed == firstNode){
			firstNode = firstNode.getNext();
			firstNode.setPrev(null);
		}else if(removed == lastNode){
			lastNode = lastNode.getPrev();
			lastNode.setNext(null);
		}else{
			System.out.println("looking for village toDelete "+toDelete.getName()+" and removed is "+removed.getVillage().getName());
			Node before = removed.getPrev();
			//System.out.println("removed.getPrev() is "+removed.getPrev().getVillage().getName());
			Node after = removed.getNext();
			System.out.println("removed.getNext() is "+removed.getNext().getVillage().getName());
			before.setNext(after);
			after.setPrev(before);
		}
		length--;
		return removed;
	}
	
	public void insert(Node nodeWithVillage){
		if(isEmpty()){
			firstNode = nodeWithVillage;
			lastNode = firstNode;
			firstNode.setNext(null);
			firstNode.setPrev(null);
			lastNode.setNext(null);
			lastNode.setPrev(null);
		}
		else{
			nodeWithVillage.setPrev(lastNode);
			lastNode.setNext(nodeWithVillage);
			lastNode = nodeWithVillage;
			lastNode.setNext(null);
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
	
	
	public void printQueue() { // prints string representation of queue
		if (! isEmpty()) {
			System.out.println("Starting village: " + firstNode.getVillage().getName());
		
			Village current = firstNode.getVillage();
			while (current != null) {
				System.out.println(" to village " + current.getName());
				current = current.getNext();
			}
		}
	} // end of printQueue()
	
	public void printGraph() { // string representation of graph, used for testing
		if (! isEmpty()) {
			Village current = firstNode.getVillage();
			while (current != null) {
				System.out.println("Village " + current.getName() + " holds " + current.populationSize + " gnomes.");
				System.out.println("   It leads to " + current.getAdjList());
				current = current.getNext();
			}
		} else {System.out.println("This graph is empty.");}
	} // end of printGraph()

}
