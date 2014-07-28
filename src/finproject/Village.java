package finproject;

public class Village {
	static int totalVillage = 0;
	int name;
	int currentPopulation = 1;
	int maxPopulation = 3;
	static int defaultPopulation;
	
	//Constructors
	public Village ( int currentPopulation, int maxPopulation) {
		this.currentPopulation = currentPopulation;
		this.maxPopulation = maxPopulation;
		this.name = ++totalVillage;
	}//end Village constructor
	
	public Village (int currentPopulation) {
		this(currentPopulation, defaultPopulation);
	}//end Village constructor
	
	public Village() {
		this.name = ++totalVillage;
	}
	

}//end Village class
