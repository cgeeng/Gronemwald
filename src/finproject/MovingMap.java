//Add exceptions to method connect, Gnome.place

package finproject;

import java.io.*;

import finproject.Village.RoadAlreadyExistsException;

public class MovingMap {
	
	static PrintWriter pw = new PrintWriter (System.out, true);
	static Village[] testVillage = new Village[15];
	static Gnome[] testGnomes = new Gnome[10];
	

	public static void main(String[] args) throws NumberFormatException, IOException, RoadAlreadyExistsException{

		//MapGUI hello = new MapGUI();
		
		/*testVillage[1] = new Village();
		testVillage[2] = new Village();
		testVillage[3] = new Village();
		testVillage[4] = new Village();
		testVillage[1].connect(3,  testVillage[2]);
		testVillage[1].connect(1,  testVillage[3]);
		testVillage[1].connect(1,  testVillage[4]);
		testVillage[2].connect(5,  testVillage[3]);
		testVillage[4].connect(5,  testVillage[1]);
		
		printMap( testVillage );
		
		testGnomes[0] = new Gnome();

		//testGnomes[0].travelRandom();
		testGnomes[0].travelPick();
		printMap( testVillage );*/
		for(int i = 1; i <=14; i++){
			testVillage[i] = new Village();
		}
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
	//	A B C D E F G H I  J  K  L  M  N 
	//	1 2 3 4 5 6 7 8 9 10 11 12 13 14
		printMap(testVillage);
		testGnomes[0] = new Gnome();
		testGnomes[0].travelTopSort();
	}
	
	public static void printMap( Village[] map ) {
		
		for (int i = 1; i <= 4; i++) {
			
			String roadList = "";
			RoadIterator current = map[i].adjacent.firstRoad;
			while (current != null ) { //get adjacent roads

				roadList += "Road to " + current.getVillageName() + " cost " + current.getCost() + ", ";
				current = current.next;
				
			} //AdjList loop
			pw.println("Village " + map[i].name + ":" + roadList);
			
			String population = "Population is Gnome ";
			int j = 0;
			if (map[i].population[j] == null) population = "Population is no one.";
			else {
				while (map[i].population[j] != null) { //get population
				
					population += map[i].population[j].ID + ", ";
					j++;
				}
			}
			pw.println(population);
			
		}//Village list loop
		
	

	} // end printMap method
	
}
