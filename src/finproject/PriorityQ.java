package finproject;

import finproject.Exceptions.*;

public class PriorityQ {
	private int length;
	private Node firstNode;
	private Node lastNode;
	
	public PriorityQ(){ // constructor
		this.length = 0;
	}
	
	public int length() {return this.length;}
	public Node getFirst() {return this.firstNode;}
	public Node setFirst(Node nextNode) {return this.firstNode = this.firstNode.getNext();}
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
	
	//when you insert, insert a node such that Node(nextNeighbor.endVillage(),nextCost,frontVertex)

	public void insert(Node nodeWithVillage){ // lowest cost goes in at beginning
		//System.out.println("hallo?");
		if(isEmpty()){
			firstNode = nodeWithVillage;
			lastNode = firstNode;
		}
		else{
			if(nodeWithVillage.getVillage().priorCost<=firstNode.getVillage().priorCost){
				nodeWithVillage.setNext(firstNode);
				firstNode.setPrev(nodeWithVillage);
				firstNode = nodeWithVillage;
			}else if(nodeWithVillage.getVillage().priorCost>=lastNode.getVillage().priorCost){
				lastNode.setNext(nodeWithVillage);
				nodeWithVillage.setPrev(lastNode);
				lastNode = nodeWithVillage;
			}else{
				Node compare = firstNode.getNext();
				while(nodeWithVillage.getVillage().priorCost>compare.getVillage().priorCost & nodeWithVillage.getVillage().priorCost>=compare.getNext().getVillage().priorCost){
					compare = compare.getNext();
				}
				Node before = compare.getPrev();
				Node after = compare.getNext();
				before.setNext(nodeWithVillage);
				after.setPrev(nodeWithVillage);
				nodeWithVillage.setNext(after);
				nodeWithVillage.setPrev(before);
			}
		}
		length++;
	}//end insert
	
	
	public Node remove(){
		Node toReturn = this.getFirst();
		if(this.getFirst() == this.getLast()){
			firstNode = null;
			lastNode = null;		
		}else{
			firstNode = firstNode.getNext();
		}
		length--;
		return toReturn;
	}
	
}//end class