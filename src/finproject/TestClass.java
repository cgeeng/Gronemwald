package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;
import finproject.Exceptions.VillageFullException;
import finproject.Exceptions.*;

public class TestClass {
	public static void main (String [] args) throws VillageFullException, SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//testGraphThread();
		Graph graph = new Graph();
		for(int i = 1; i <= 5; i++){
			graph.insert(new Village());
		}
		graph.find(1).connect(2,graph.find(2));
		graph.find(1).connect(2,graph.find(4));
		graph.find(1).connect(2,graph.find(5));
		graph.find(2).connect(2,graph.find(3));
		graph.find(2).connect(2,graph.find(4));
		//graph.find(4).connect(2,graph.find(1));
		graph.find(4).connect(2,graph.find(3));
		graph.find(4).connect(2,graph.find(5));
		//graph.find(6).connect(2, graph.find(7));
		//graph.find(7).connect(2, graph.find(4));
		graph.topologicalSort();
		//graph.findCycle();

	}
		
	public static void testGraphThread() {
		try {
			Graph graph = new Graph();
			
			for (int i=0; i<10; i++) {graph.insert(new Village());}
			
			Thread g = new Thread(graph);
			
			g.start();
		} catch (VillageFullException e) {
			System.out.println("This village is full.");
		}
	} // end of testGraphThread()
	
} // end of TestClass
