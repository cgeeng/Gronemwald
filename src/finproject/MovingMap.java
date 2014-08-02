//Add exceptions to method connect, Gnome.place

package finproject;

import java.io.*;

import finproject.Exceptions.SameVillageException;
import finproject.Exceptions.*;

public class MovingMap {
	
	static PrintWriter pw = new PrintWriter (System.out, true);
	static Village[] testVillage = new Village[15];
	static Gnome[] testGnomes = new Gnome[10];
	static Graph Gr = new Graph();
	

	public static void main(String[] args) throws NumberFormatException, IOException, RoadAlreadyExistsException, SameVillageException, 
	NotFoundException, GraphEmptyException {

		
		Gr.insert( new Village());
		Gr.insert( new Village());
		Gr.insert( new Village());
		
		Gr.find(1).connect(2, Gr.find(3));
		Gr.find(1).connect(2, Gr.find(2));
		printMap( Gr );

		//testGnomes[0].travelRandom();
		//testGnomes[0].travelPick();

		/*
		for(int i = 1; i <=14; i++){
			testVillage[i] = new Village();
		}
		try {
			testVillage[1].connect(2, testVillage[2]);
			testVillage[1].connect(3, testVillage[3]);
			testVillage[1].connect(3, testVillage[6]);
			testVillage[2].connect(4, testVillage[3]);
			testVillage[2].connect(5, testVillage[4]);
			testVillage[2].connect(5, testVillage[5]);
			testVillage[3].connect(2, testVillage[4]);
			testVillage[4].connect(1, testVillage[9]);
			testVillage[5].connect(2, testVillage[4]);
			testVillage[5].connect(4, testVillage[9]);
			testVillage[6].connect(1, testVillage[7]);
			testVillage[7].connect(2, testVillage[11]);
			testVillage[8].connect(1, testVillage[11]);
			testVillage[9].connect(1, testVillage[8]);
			testVillage[10].connect(2, testVillage[14]);
			testVillage[11].connect(3, testVillage[10]);
			testVillage[12].connect(2, testVillage[11]);
			testVillage[12].connect(1, testVillage[13]);
			testVillage[13].connect(2, testVillage[14]);
		} catch (SameVillageException e) {System.out.println(e.getMessage());}
	//	A B C D E F G H I  J  K  L  M  N 
	//	1 2 3 4 5 6 7 8 9 10 11 12 13 14
		printMap(testVillage);
		testGnomes[0] = new Gnome();
		testGnomes[0].travelTopSort();
		*/
	}
	
	public static void printMap( Graph map) throws NotFoundException, GraphEmptyException {
		int i = 1;
		Village current = map.firstVillage;
		while (i <= map.getLength() ) {
			
			String roadList = "";
			RoadIterator currentRoad = current.outgoing.firstRoad;
			while (currentRoad != null ) { //get adjacent roads

				roadList += "Road to " + currentRoad.endVillage().getName() + " cost " + currentRoad.getCost() + ", ";
				currentRoad = currentRoad.next;
				
			} //AdjList loop
			pw.println("Village " + current.getName() + ":" + roadList);	
			current = current.getNext();
			
			String population = "Population is Gnome ";
			int j = 0;
			if (map.find(i).population[j] == null) population = "Population is no one.";
			else {
				while (map.find(i).population[j] != null) { //get population
				
					population += map.find(i).population[j].getID() + ", ";
					j++;
				}
			}
			pw.println(population);	

			i++;
		}//end while

	}
	
/*	
	public static void printMap( Village[] map ) {
		//for arrays
		for (int i = 1; i <= 4; i++) {
			
			String roadList = "";
			RoadIterator current = map[i].adjacent.firstRoad;
			while (current != null ) { //get adjacent roads

				roadList += "Road to " + current.getVillage().getName() + " cost " + current.getCost() + ", ";
				current = current.next;
				
			} //AdjList loop
			pw.println("Village " + map[i].getName() + ":" + roadList);
			
			String population = "Population is Gnome ";
			int j = 0;
			if (map[i].population[j] == null) population = "Population is no one.";
			else {
				while (map[i].population[j] != null) { //get population
				
					population += map[i].population[j].getID() + ", ";
					j++;
				}
			}
			pw.println(population);
			
		}//Village list loop
		
	

	} // end printMap method
	*/
}
