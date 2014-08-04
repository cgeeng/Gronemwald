package finproject;

import java.util.*;
import java.io.*;

import java.util.Random;

import finproject.Exceptions.*;

public class Gnome implements Runnable {
	public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int totalGnome = 0;
	public String name;
	private int ID;
	public Village current;
	private int sleepTime; // sleep times offset a little
	private Queue visited = new Queue(); // all villages visited - equivalent of "passport stamped"
	
	public int getID() {return this.ID;}
	public void setVillage(Village v) {this.current = v;}
	public Queue getVisited() {return this.visited;}

	//Constructors
	public Gnome (String name) {
		this.name = name;
		ID = ++totalGnome;
		Random rand = new Random();
		this.sleepTime = 2000+rand.nextInt(5000); // between 2 and 7 seconds
	}
	
	public Gnome () {
		this("no name");
	}//end default constructor
	
	public Gnome(Village starting) {
		try {
			ID = ++totalGnome;
			starting.insertGnome(this);
			visited.insert(new Node(starting));
		} catch (VillageFullException e) {System.out.println(e.getMessage());}
	}//end constructor establishing Gnome's village
	
	public void run() { // run method for gnomes
		// stops after 10 changes
		int runCount = 0;
		try {
			while(runCount<10) {
			Thread.sleep(sleepTime);
			travelRandom();
			runCount++;
			}
		} catch (InterruptedException e) {
			System.out.println("The program was interrupted.");
		}
	}
	
	//methods
	
	public synchronized void travelRandom () {
		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.outgoing.length == 0) {return;} // simply doesn't move if there are no outgoing paths
			
				// throw new NoAdjacentVillagesException(current.getName());
			
			Random generate = new Random();
			int randomTraverse = 1 + generate.nextInt(current.outgoing.length);
			
			//Iterate across AdjList of gnome's current village to find random road
			RoadIterator temp = current.outgoing.firstRoad;
			for (int i = 1; i < randomTraverse; i++) {
				temp = temp.getNext();
			}
			//Assign destination village
			Village oldVillage = this.current;
			Village destination = temp.endVillage();
			if (destination.isFull()) {return;} // simply doesn't move if destination village is full
			
			destination.insertGnome(this);			
			System.out.println("Gnome " + ID + " has moved from village " + oldVillage.getName() + " to village " + current.getName());
		} catch (NotInVillageException e) {System.out.println(e.getMessage()); 
		// } catch (NoAdjacentVillagesException e) {System.out.println(e.getMessage());
		} catch (VillageFullException e) {System.out.println(e.getMessage());}
	}//end travelRandom

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
	}//end NoAdjacentVillagesException
}
