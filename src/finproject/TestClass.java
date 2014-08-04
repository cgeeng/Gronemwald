package finproject;

import finproject.Exceptions.GraphEmptyException;
import finproject.Exceptions.NotFoundException;
import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;
import finproject.Exceptions.VillageFullException;
import finproject.Exceptions.*;

public class TestClass {
	public static void main (String [] args) {
		// testGraphThread();
		// testTopologicalSort();
		
		//createProposal returns array of Roads. 
		Graph.createProposal();
		
		testRoadProposal();

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
	
	public static void testTopologicalSort() {
		try {
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
		} catch (VillageFullException | SameVillageException
				| RoadAlreadyExistsException | NotFoundException
				| GraphEmptyException e) {
			System.out.println(e.getMessage());
		}
	} // end of testTopologicalSort()
	
	public static void testRoadProposal() {
		try {
			Graph graph = new Graph("GNOMENWALD");
			for (int i=0; i<7; i++) {graph.insert(new Village(5));} // 7 villages with 5 gnomes each
			
			graph.find(1).connect(2, graph.find(2));
			graph.find(1).connect(4, graph.find(3));
			graph.find(2).connect(5, graph.find(4));
			graph.find(2).connect(1, graph.find(3));
			graph.find(4).connect(1, graph.find(5));
			graph.find(5).connect(2, graph.find(3));
			graph.find(5).connect(4, graph.find(6));
			graph.find(6).connect(3, graph.find(7));
			
			
			// should be no, because there is already an existing road
			graph.roadProposal(graph.find(1), graph.find(2));
			// should be no, because it is the same village
			graph.roadProposal(graph.find(4), graph.find(4));
			// should be no, because there is only one intermediate village
			graph.roadProposal(graph.find(5), graph.find(7));
			
			// should be yes, because 3 has no outgoing roads
			graph.roadProposal(graph.find(3), graph.find(4));
			// should be yes, because 1 has no incoming roads
			graph.roadProposal(graph.find(5), graph.find(1));
			// should be yes, because there is more than one intermediate village
			graph.roadProposal(graph.find(4), graph.find(7));

			} catch (RoadAlreadyExistsException | GraphEmptyException |
					NotFoundException | SameVillageException | VillageFullException e) {
				System.out.println(e.getMessage());
			} 
	} // end of testRoadProposal()
	
	
} // end of TestClass
