package finproject;

import finproject.Exceptions.NotFoundException;


public class AStack {
	int length;
	Village firstVillage;
	Village lastVillage;
	Village min;
	
	public AStack(){ // constructor
		this.length = 0;
	}
	
	public int length() {return this.length;}
	public Village getFirst() {return this.firstVillage;}
	public Village getLast() {return this.lastVillage;}
	public boolean isEmpty() {return length==0;}
	
	public Village find(Village r) throws NotFoundException {
		Village current = this.firstVillage;
		while (current != null) {					
			if (current.equals(r)) {return current;}
			current = current.getNext();
		} throw new NotFoundException();
	} // end of find()
	
	public void insert(Village village){
		if(isEmpty()){
			firstVillage = village;
			lastVillage = firstVillage;
		}
		else{
			lastVillage.setNext(village);
			village.setPrev(lastVillage);
			lastVillage = village;
		}
		length++;
	}//end insert
}
