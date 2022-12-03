package traveling_salesman;

import java.util.List;
import java.lang.Math;
import java.util.ArrayList;

public class SimulatedAnnealing {
	private double startingTemperature;//Defines the starting temperature for the solver
	private int numberOfIterations; // Maximum number of iterations, in case temperature don't drops
	private int outLoopInterations;
	private double coolingRate;  // Cooling rate - smaller makes the temp drops less per iteration
	private int graphSize;
	double lowerCost;
	private int graph[][];
	private List<Integer> currentTravel; // stores the current path for the travel
	private int bestCost;
	
	private void generateInitialTravel () {
		// Creates the starting point for the algorithm
		// CReate a list with elements from 1 to n 
		this.currentTravel.add(0); //Always start at vertex 0	
		List<Integer> myList = new ArrayList<Integer>();
		for (int i =1; i<this.graphSize; i++) {
			myList.add(i);
		}
		// get a random list number, and append to curernt travel
		while (myList.size() != 0) {
			int randomIndex = (int) (Math.random() * myList.size());
			this.currentTravel.add(myList.get(randomIndex));
			myList.remove(randomIndex);
		}
		this.currentTravel.add(0); //Always ends at vertex 0
	}
	
	private List<Integer> getSwap(List<Integer> mySwappedList ){
		//Return a list with 2 swapped cities - exclude start and end
		int randomIndexA = (int) (Math.random() * (this.graphSize-1)) +1;
		int randomIndexB = (int) (Math.random() * (this.graphSize-1)) +1;
		List<Integer> swappedList = mySwappedList;
		int newA = mySwappedList.get(randomIndexB);
		int newB = mySwappedList.get(randomIndexA);
		swappedList.set(randomIndexA, newA);
		swappedList.set(randomIndexB, newB);
		return swappedList;
	}
	
	private int getTravelDistance(List<Integer> travel) {
		int cost =0;
		for (int i =1; i < this.graphSize+1 ; i++) {
			cost += this.graph[travel.get(i-1)][travel.get(i)];
		}
		return cost;
	}
	
	public SimulatedAnnealing (double startingTemperature, int numberOfIterations,
		double coolingRate, int graphSize, int graph[][], int outLoopIterations) {
		this.startingTemperature = startingTemperature;
		this.numberOfIterations = numberOfIterations;
		this.coolingRate = coolingRate;
		this.graphSize = graphSize;
		this.currentTravel = new ArrayList<Integer>();
		this.graph = graph;
		this.outLoopInterations = outLoopIterations;

		
	}
	
	
	public int getoptimizedRouteCost () {
		
		this.generateInitialTravel();
		//Current best cost is initial cost
		this.bestCost = this.getTravelDistance(this.currentTravel);
		double t = this.startingTemperature;
		List<Integer> mySwappedListOut = this.currentTravel;
		for (int ol=0; ol< this.outLoopInterations; ol++) {
			List<Integer> mySwappedListInner = this.getSwap(mySwappedListOut);
			for (int i = 0; i < numberOfIterations; i++) { //iterates to find the best solution
				mySwappedListInner = this.getSwap(mySwappedListInner);
		    	// Random swaps 2 element
		    	int swappedListCost = this.getTravelDistance(mySwappedListInner);
		    	int swappedListCostOut = this.getTravelDistance(mySwappedListOut);
		    	// use new list as best in case it is the lower cost.
		    	if (swappedListCost < swappedListCostOut) {
		    		mySwappedListOut = mySwappedListInner;
		    	} else if (Math.exp((this.bestCost  - this.getTravelDistance(mySwappedListInner)) / t) > Math.random()+1) {
		    		mySwappedListOut = mySwappedListInner;
		    	}
			}
			
			int currentCostOut = this.getTravelDistance(mySwappedListOut);
			if (this.bestCost > currentCostOut) {
				this.bestCost = currentCostOut;
				this.currentTravel = mySwappedListOut;			
				
			} 
			t = t*this.coolingRate;
		}
		return this.bestCost;
	}
	
}
	
