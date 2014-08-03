package finproject;

import finproject.Exceptions.RoadAlreadyExistsException;
import finproject.Exceptions.SameVillageException;
import finproject.Exceptions.*;

import java.util.Random;

public class Graph implements Runnable {
	private int length = 0;
	Village firstVillage;
	Village lastVillage;
	ProposalGraph proposal;
	
	//constructor
	public Graph() {
		firstVillage = null;
		lastVillage = null;
	} 
	
	public boolean isEmpty() {return length == 0;}
	public int getLength() {return length;}
	public Village getFirst() {return this.firstVillage;}
	public Village getLast() {return this.lastVillage;}
	
	public void run() { // run method for Graph
		if (! isEmpty()) { // chooses random village within graph
				Random rand = new Random();
				int v = rand.nextInt(length-1), count=0;
				Village vill = getFirst();
				for (int i=0; i<length; i++) {
					if (count != v) {count++; vill = vill.getNext();}
				}
				System.out.println("start village " + vill.getName());
				
				Village vill2 = getFirst();
				for (int i=0; i<length; i++) {
					if (count != v) {count++; vill2 = vill2.getNext();}
				}
				System.out.println("end village is " + vill2.getName());
				
				try {
					vill.connect(1, vill2);
					System.out.println("New road between village " + vill.getName() + " and village " + vill2.getName());
				} catch (SameVillageException e) {
					System.out.println(e.getMessage());
				} catch (RoadAlreadyExistsException e) {
					System.out.println(e.getMessage());
				}
				
//			 	Thread.sleep(100);
//			} catch (InterruptedException e) {
//				System.out.println("The program was interrupted.");
//			}
		}
	} // end of run()
			
//	public boolean roadExists(Village v1, Village v2) {
//		// checks if road already exists between two villages
//		boolean roadExists = false;
//		if (!v1.outgoing.isEmpty()) {
//			RoadIterator current = v1.outgoing.getFirst();
//			while (current!=null) {
//				if (current.getData().end.equals(v2)) {roadExists = true;}
//			}
//		}
//		return roadExists;
//	}
	
	public synchronized void insert (Village newVillage) {
		if (isEmpty()) {
			firstVillage = newVillage;
			lastVillage = newVillage;
		}
		else {
			lastVillage.setNext(newVillage);
			newVillage.setPrev(lastVillage);
			lastVillage = newVillage;
		}
		length++;
	}
	
	public synchronized void delete(int name) throws GraphEmptyException, NotFoundException {
		if (isEmpty()) {throw new GraphEmptyException();}
		else if (getLength() == 1) {
			this.firstVillage = null;
			this.lastVillage = null; }
		else {
			Village found = find(name);
			if (found.equals(firstVillage)) {
				found.getNext().setPrev(null);
				firstVillage = found.getNext();}
			else if (found.equals(lastVillage)) {
				lastVillage = found.getPrev(); 
				lastVillage.setNext(null);}
			else {
				found.getPrev().setNext(found.getNext());
				found.getNext().setPrev(found.getPrev());
			}}
		this.length--;
	} // end of delete method
	
	public Village find(int name) throws NotFoundException, GraphEmptyException {
		// exceptions caught by MapGUI so pop-up error message can be generated
		if (! isEmpty()) {
			Village current = this.firstVillage;
			while (current != null) {					
				if (current.getName() == name) {return current;}
				current = current.getNext();
			} throw new NotFoundException();
		} else {throw new GraphEmptyException();}
	}

	//Just.....forget this method.............creates deep copy of this graph
	public void createProposal() throws SameVillageException, RoadAlreadyExistsException, NotFoundException, GraphEmptyException {
		//create proposal instance; WILL NOT BE UPDATED IF ROADS ARE BUILT INTO ACTUAL GRAPH
		proposal = new ProposalGraph();
	}
	
	public void clearProposal() {
		//essentially same as createProposal, but here for clarity
		//Use after finishing finding min tree for existing proposal
		proposal = new ProposalGraph();
	}
	
	//To be used with deep copy....don't use this
	public void addOopsProposal(Village a, Village b, int cost) throws NotFoundException, GraphEmptyException, SameVillageException, RoadAlreadyExistsException {
		//Assumes a village with no roads out of it has been created in the Graph
		Village proposalA = proposal.find(a.getName());
		Village proposalB = proposal.find(b.getName());
		Road newRoad = proposalA.connect(cost,  proposalB);
		newRoad.built = false;
		
	}
	
	public void printGraph() { // string representation of graph, used for testing
		if (! isEmpty()) {
			Village current = this.firstVillage;
			while (current != null) {
				System.out.println("Village " + current.getName() + " holds " + current.populationSize + " gnomes.");
				System.out.println("   It leads to " + current.getAdjList());
				current = current.getNext();
			}
		} else {System.out.println("This graph is empty.");}
	} // end of printGraph()
	
}//end VillageList class
