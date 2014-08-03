package finproject;

import java.util.*;
import java.io.*;

import finproject.Exceptions.*;

public class Gnome implements Runnable {
	public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int totalGnome = 0;
	public String name = "no name";
	private int ID;
	public Village current;
	static int counter;
	
	public int getID() {return this.ID;}
	public void setVillage(Village v) {this.current = v;}

	//Constructors
	public Gnome (String name) {
		this.name = name;
		ID = ++totalGnome;
	}
	
	public Gnome () {
		ID = ++totalGnome;		
	}//end default constructor
	
	public Gnome(Village starting) {
		try {
			ID = ++totalGnome;
			starting.insertGnome(this);
		} catch (VillageFullException e) {System.out.println(e.getMessage());}
	}//end constructor establishing Gnome's village
	
	public void run() { // run method for gnomes
		proposeMove();
	}
	
	//methods
	
	public void travelRandom () {
		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.outgoing.length == 0) throw new NoAdjacentVillagesException(current.getName());
			
			Random generate = new Random();
			int randomTraverse = 1 + generate.nextInt(current.outgoing.length);
			System.out.println("Random number is " + randomTraverse);
			
			//Iterate across AdjList of gnome's current village to find random road
			RoadIterator temp = current.outgoing.firstRoad;
			for (int i = 1; i < randomTraverse; i++) {
				System.out.println("i is " + i);
				temp = temp.getNext();
			}
			//Assign destination village
			Village oldVillage = this.current;
			Village destination = temp.endVillage();
			destination.insertGnome(this);			
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.getName() + " to Village " + current.getName());
		} catch (NotInVillageException e) {System.out.println(e.getMessage()); 
		} catch (NoAdjacentVillagesException e) {System.out.println(e.getMessage());
		} catch (VillageFullException e) {System.out.println(e.getMessage());}
	}//end travelRandom
	
	// unnecessary with GUI 
	/*
	public void travelPick() throws NumberFormatException, IOException { // deal with exception later

		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.adjacent.length == 0) throw new NoAdjacentVillagesException(current.name);
			
			String message = "Gnome " + ID + " can move from Village " + current.name + " to: ";
			String destinations = current.getAdjList();
			System.out.println(message + destinations);
			
			System.out.println("From the above neighboring villages listed, which village would you like to move to? Enter the village number.");
			Village oldVillage = this.current;
			MovingMap.testVillage[Integer.parseInt(br.readLine())].insertGnome(this);
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.name + " to Village " + current.name);
			
		} catch (NotInVillageException e) {System.out.println(e.getMessage()); 
		} catch (NoAdjacentVillagesException e) { System.out.println(e.getMessage());
		} catch (VillageFullException e) {System.out.println(e.getMessage());}
	}//end travelPick()
	*/
	
	// TODO make gnome into thread that traverses the minimum path? (animated)
	public String travelTopSort(){ // need to check for cycles?
		Queue q = new Queue();
		Village a;
		RoadIterator b;
		queueZero(q);
		String pathToTake = "";
		System.out.println("is it empty? "+q.isEmpty());
		while(!q.isEmpty()){
			a = q.remove().getVillage();
			System.out.println("a has been removed from q. q length is now "+q.length());
			pathToTake += a.getName() + " ";
			System.out.println("a.name is "+pathToTake+" and adjacent length is "+a.outgoing.length);
			// for each adjacent village to village a, decrease each indegree and if it equals 0, add to queue
			if(a.outgoing.length!=0){
				b = a.outgoing.firstRoad;
				while(b != null){
					System.out.println("b's indegree is "+b.endVillage().indegree);
					b.endVillage().indegree--;
					System.out.println("b's indegree is nowww "+b.endVillage().indegree);
					if( b.endVillage().indegree  == 0 ){
						System.out.println("about to insert...");
						q.insert(new Node(b.endVillage()));
						System.out.println("added to the q is village "+b.endVillage().getName() +" with indegree "+b.endVillage().indegree);
					}
					b = b.getNext();
				} // end of while
			} // end of if
		} // end of while
		System.out.println(pathToTake+"hi ");
		return pathToTake;
		
	} // end of travelTopSort method
	
	public Queue queueZero(Queue someQ){
		for(int i = 1; i < MovingMap.testVillage.length; i++){
			if(MovingMap.testVillage[i].indegree == 0){
				someQ.insert(new Node(MovingMap.testVillage[i]));
			}
		}
		System.out.println("someQ length is"+ someQ.length());
		return someQ;
	}	
	
	//exceptions
	public class NotInVillageException extends Exception{
		public NotInVillageException() {
			super("This gnome is not in a village! Place it first.");
		}//end constructor
	}//end NotInVillageException
	
	public class NoAdjacentVillagesException extends Exception{
		public NoAdjacentVillagesException(int name) {
			super("Village " + name + "  has no roads leading out! Maybe you should build one.");
		}//end constructor
	}//end NotInVillageException
}
