package finproject;

import finproject.Exceptions.*;

public class TestClass {
	public static void main (String [] args) {
		// testGraphThread();
		testMinExpPath();
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
	
	public static void testMinExpPath() {
		try {
			Graph graph = new Graph();
			for (int i=0; i<5; i++) {graph.insert(new Village(2));} // 5 villages with 2 gnomes each

			graph.find(1).connect(2, graph.find(2));
			graph.find(1).connect(4, graph.find(3));
			graph.find(2).connect(5, graph.find(4));
			graph.find(2).connect(1, graph.find(3));
			graph.find(4).connect(1, graph.find(5));
			graph.find(5).connect(1, graph.find(4));
			graph.find(5).connect(3, graph.find(3));
			
			graph.travelMinExpPath(graph.find(1), graph.find(4));
		} catch (VillageFullException | SameVillageException
				| RoadAlreadyExistsException | NotFoundException
				| GraphEmptyException | NoOutgoingRoadsException | 
				NoIncomingRoadsException e) {
			System.out.println(e.getMessage());
		}
	}
	
	
} // end of TestClass
