package finproject;

import java.util.*;
import java.io.*;

public class Gnome {
	public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int totalGnome = 0;
	public String name = "no name";
	public int ID;
	public Village current;
	static int counter;
	static String[][] villAndCost = new String[MovingMap.testVillage.length][];
	
	/*
	static{
		for (int i = 1; i < MovingMap.testVillage.length; i++){
			villAndCost[i] = new String[3];
			System.out.println("length is "+villAndCost[i].length);
			//System.out.println("HI");
			//System.out.println("length is "+villAndCost.length);
			villAndCost[i][0] = ""; // prior village name
			villAndCost[i][1] = ""; // total cost get to this village		
			// outdegree that decreases to 0, which indicates that village is has been "dealt with"
			villAndCost[i][2] = String.valueOf(MovingMap.testVillage[i].getOutdegree()); 
		}
	}
	*/

	//Constructors
	public Gnome (String name) {
		this.name = name;
		ID = ++totalGnome;
	}
	
	public Gnome () {
		ID = ++totalGnome;		
	}//end default constructor
	
	public Gnome(Village starting) {
		ID = ++totalGnome;
		this.place(starting);
	}//end constructor establishing Gnome's village
	
	//methods
	public Village place(Village starting) { // TODO should be in Village?
		//Return previous village if exists, otherwise return null
		
		//Remove gnome from current village if has been previously placed
		Village oldVillage = null;
		if (current != null) {oldVillage = removeGnome();}
		
		//find next empty spot in population array
		int i = 0;
		while (starting.population[i] != null) {++i;}
		
		starting.population[i] = this;
		current = starting;
		current.populationSize++;
		return oldVillage;
	}//end place
	
	private Village removeGnome() { // TODO should be in Village?
		//remove gnome from its current village. Return its previous village?
		int i = 0;
		while ( current.population[i] != null) {
			if (current.population[i].ID == this.ID) {
				current.population[i] = null;
				current.populationSize--;
			}
		}//Gnome's current village still has not been changed; will be changed in place()
		return current;
	}//end removeGnome method

	
	public void travelRandom () {
		
		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.adjacent.length == 0) throw new NoAdjacentVillagesException(current.name);
			
			Random generate = new Random();
			int randomTraverse = 1 + generate.nextInt(current.adjacent.length);
			System.out.println("Random number is " + randomTraverse);
			
			//Iterate across AdjList of gnome's current village to find random road
			RoadIterator temp = current.adjacent.firstRoad;
			for (int i = 1; i < randomTraverse; i++) {
				System.out.println("i is " + i);
				temp = temp.next;
			}
			//Assign destination village
			Village destination = temp.getVillage();			
			Village oldVillage = this.place(destination);			
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.name + " to Village " + current.name);
			
		} catch (NotInVillageException e) { 
			System.out.println(e.getMessage()); 
		} catch (NoAdjacentVillagesException e) { System.out.println(e.getMessage()); }
	}//end travelRandom
	
	public void travelPick() throws NumberFormatException, IOException { // deal with exception later

		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.adjacent.length == 0) throw new NoAdjacentVillagesException(current.name);
			
			String message = "Gnome " + ID + " can move from Village " + current.name + " to: ";
			String destinations = current.getAdjList();
			System.out.println(message + destinations);
			
			System.out.println("From the above neighboring villages listed, which village would you like to move to? Enter the village number.");
			Village oldVillage = this.place(MovingMap.testVillage[Integer.parseInt(br.readLine())]);
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.name + " to Village " + current.name);
			
		} catch (NotInVillageException e) { 
			System.out.println(e.getMessage()); 
		} catch (NoAdjacentVillagesException e) { System.out.println(e.getMessage()); }
	}//end travelPick()
	
	public String travelTopSort(){ // need to check for cycles?
		Queue q = new Queue();
		Village a;
		RoadIterator b;
		queueZero(q);
		String pathToTake = "";
		System.out.println("is it emtpy? "+q.isEmpty());
		while(!q.isEmpty()){
			a = q.remove().getVillage();
			System.out.println("a has been removed from q. q length is now "+q.length());
			//System.out.println("hello?");
			pathToTake += a.name + " ";
			System.out.println("a.name is "+pathToTake+" and adjacent length is "+a.adjacent.length);
			// for each adjacent village to village a, decrease each indegree and if it equals 0, add to queue
			if(a.adjacent.length!=0){
				b = a.adjacent.firstRoad;
				while(b != null){
					System.out.println("b's indegree is "+b.getVillage().getIndegree());
					b.getVillage().indegree--;
					System.out.println("b's indegree is nowww "+b.getVillage().getIndegree());
					if( b.getVillage().indegree  == 0 ){
						System.out.println("about to insert...");
						q.insert(new Node(b.getVillage()));
						System.out.println("added to the q is village "+b.getVillage().name+" with indegree "+b.getVillage().indegree);
					}
					b = b.next;
				} // end of while
			} // end of if
		} // end of while
		System.out.println(pathToTake+"hi ");
		return pathToTake;
		
	} // end of travelTopSort method
	
	public Queue queueZero(Queue someQ){
		for(int i = 1; i < MovingMap.testVillage.length; i++){
			if(MovingMap.testVillage[i].getIndegree() == 0){
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
