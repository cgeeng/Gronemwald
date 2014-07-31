package finproject;

public class Queue {
	int length;
	Node firstNode;
	Node lastNode;
	
	public Queue(){ // constructor
		this.length = 0;
	}
	
	public void insert(Node nodeWithVillage){
		if(isEmpty()){
			firstNode = nodeWithVillage;
			lastNode = firstNode;
		}
		else{
			lastNode.next = nodeWithVillage;
			lastNode = nodeWithVillage;
		}
		length++;
	}
	
	public Node remove(){
		Node temp = firstNode;
		if(firstNode.next != null){
			firstNode = firstNode.next;
		}else{
			firstNode = null;
		}
		length--;
		return temp;
	}
	
	public boolean isEmpty(){
		return length==0;
	}
	
}
