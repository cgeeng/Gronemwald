package finproject;

import java.util.*;
import java.io.*;

public class Gnome {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int totalGnome = 0;
	String name = "no name";
	int ID;
	Village current;
	
	//Constructors
	public Gnome (String name) {
		this.name = name;
		ID = ++totalGnome;
	}
	
	public Gnome () {
		ID = ++totalGnome;
		this.place(MovingMap.testVillage[4]);
	}//end Gnome constructor
	
	//methods
	public void place(Village starting) { //MUST BE USED WITH removeGnome(); should change later?
		
		//find next empty spot in population array
		int i = 0;

		while (starting.population[i] != null) {
			++i;
		}
		
		starting.population[i] = this;
		this.current = starting;
		
	}; 
	
	public Village removeGnome() {
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
		
		Village oldVillage = this.removeGnome();
		this.place(destination);
		
		System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.name + " to Village " + current.name);
	}
	
	public void travelPick() throws NumberFormatException, IOException { // deal with exception later
		String message = "Gnome " + ID + " can move from Village " + current.name + " to: ";
		String destinations = current.getAdjList();
		System.out.println(message + destinations);
		
		if (!destinations.equals("nowhere.")) {
			System.out.println("From the above neighboring villages listed, which village would you like to move to? Enter the village number.");
			Village oldVillage = this.removeGnome();
			this.place(MovingMap.testVillage[Integer.parseInt(br.readLine())]);
			System.out.println("Gnome " + ID + " has moved from Village " + oldVillage.name + " to Village " + current.name);
		}
	}
	

}
