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
	Village village;
	Node next;
	Node previous;
	public Node(Village someVill){ // constructor
		 this.village = someVill;
	}
	
	
}//end Node class