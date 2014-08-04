package finproject;

import finproject.Exceptions.*;

public class TestClass {
	public static void main (String [] args) {
		testGraphThread();
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
