package finproject;

import java.util.*;
import java.io.*;

public class Gnome {
	public BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static int totalGnome = 0;
	public String name = "no name";
	public int ID;
	public Village current;
	
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
	public Village place(Village starting) {
		//Return previous village if exists, otherwise return null
		
		//Remove gnome from current village if has been previously placed
		Village oldVillage = null;
		if (current != null) oldVillage = removeGnome();
		
		//find next empty spot in population array
		int i = 0;
		while (starting.population[i] != null) {
			++i;
		}
		starting.population[i] = this;
		this.current = starting;
		return oldVillage;
	}//end place
	
	private Village removeGnome() {
		//remove gnome from its current village. Return its previous current village?
		int i = 0;
		while ( current.population[i] != null) {
			if (current.population[i].ID == this.ID) {
				current.population[i] = null;
			}
		}//Gnome's current village still has not been changed; will be changed in place()
		return current;
	}//end removeGnome method
	
	public void travelRandom () {
		
		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.adjacent.length == 0) throw new NoAdjacentVillagesException(current.getName());
			
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
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.getName() + " to Village " + current.getName());
			
		} catch (NotInVillageException e) { 
			System.out.println(e.getMessage()); 
		} catch (NoAdjacentVillagesException e) { System.out.println(e.getMessage()); }
	}//end travelRandom
	
	public void travelPick() throws NumberFormatException, IOException { // deal with exception later

		try {
			//Limited to gnomes that are in a village already
			if (current == null) throw new NotInVillageException();
			if (current.adjacent.length == 0) throw new NoAdjacentVillagesException(current.getName());
			
			String message = "Gnome " + ID + " can move from Village " + current.getName() + " to: ";
			String destinations = current.getAdjList();
			System.out.println(message + destinations);
			
			System.out.println("From the above neighboring villages listed, which village would you like to move to? Enter the village number.");
			Village oldVillage = this.place(MovingMap.testVillage[Integer.parseInt(br.readLine())]);
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.getName() + " to Village " + current.getName());
			
		} catch (NotInVillageException e) { 
			System.out.println(e.getMessage()); 
		} catch (NoAdjacentVillagesException e) { System.out.println(e.getMessage()); }
	}//end travelPick()
	
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
