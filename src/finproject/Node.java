package finproject;

public class Node { // node for priority queue (minExpPath)
	private Village village;
	private Node next;
	private Node previous;
	Village predecessor = null;
	int pathCost;
	
	public void setVillage(Village village) { this.village = village; }
	public Village getVillage() {return this.village;}
	public void setNext(Node n) {this.next = n;}
	public Node getNext() {return this.next;}
	public void setPrev(Node p) {this.previous = p;}
	public Node getPrev() {return this.previous;}
	
	public Node(Village v){ // constructor
		 this.village = v;
	}
	public Node(Village vill, Village predecessor){ // constructor for minExpPath
		this.predecessor = predecessor;
		this.village = vill;
	}
	
	public Node(Village vill, int cost, Village predecessor){ // constructor for minExpPath
		this.pathCost = cost;
		this.predecessor = predecessor;
		this.village = vill;
	}
	
}//end Node class