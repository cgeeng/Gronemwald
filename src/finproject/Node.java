package finproject;

public class Node {
/*	Village data;
	Node[] previous; //Contains previous village as well as road cost
	Node[] next;
	int[] previousCost = {1, 1};
	int[] nextCost = {1, 1};
	
	//Constructors
	public Node (Node[] previous, Village data, Node[] next) {
		this.data = data;
		this.previous = previous;
		this.next = next;
	}
	public Node (Village data) {
		this( null, data, null);
	}
	public Node (Village data, Node[] next) {
		this(null, data, next);
	}
	public Node (Node[] previous, Village data) {
		this(previous, data, null);
	}
	public Node () {
		this.data = new Village();
	}
*/
	private Village village;
	private Node next;
	private Node previous;
	Village predecessor;
	int pathCost;
	
	public Village getVillage() {return this.village;}
	public void setNext(Node n) {this.next = n;}
	public Node getNext() {return this.next;}
	public void setPrev(Node p) {this.previous = p;}
	public Node getPrev() {return this.previous;}
	
	public Node(Village someVill){ // constructor
		 this.village = someVill;
	}
	
	public Node(Village vill, int cost, Village predecessor){ // constructor for minExpPath
		this.pathCost = cost;
		this.predecessor = predecessor;
		this.village = vill;
	}
	
}//end Node class