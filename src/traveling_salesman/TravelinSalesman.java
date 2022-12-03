package traveling_salesman;
import java.time.Instant;

public class TravelinSalesman {
		
	
		public static void main (String args[]) {
			// Solving the brute force approach 
			// Goes trough all iago files, loads it and then attempt the solution
			/*String test_name = "/Users/iago/Documents/morallito/masters/AEDS/traveling-salesman/iago01.txt";
			InputReader myreader = new InputReader(test_name);
			BruteForceSolver mysolver1 = new BruteForceSolver(myreader.readInputMatrix(), myreader.getGraphDimention());
			String myGraph = myreader.getGraphString();
			System.out.println(myGraph);*/
			
			/*for (int i = 1; i<=12; i++) {
				String test_name = "/Users/iago/Documents/morallito/masters/AEDS/traveling-salesman/iago0" + i + ".txt";
				InputReader myreader = new InputReader(test_name);

				BruteForceSolver mysolver1 = new BruteForceSolver(myreader.readInputMatrix(), myreader.getGraphDimention());
				mysolver1.getShortPath();
				long startTime =  Instant.now().toEpochMilli();
				System.out.println("Start solving "+ i +" in time: "+ startTime);
				mysolver1.getShortPath();
				mysolver1.getShortPath();
				long endTime =  Instant.now().toEpochMilli();
				System.out.println("Finished solving "+ i +" in time: "+ endTime);				
				
			}*/
			String test_name = "/Users/iago/Documents/morallito/masters/AEDS/traveling-salesman/si1032.txt";
			InputReader myreader = new InputReader(test_name);
			int [][] inputMatrix = myreader.readInputMatrix();
			double startingTemp = 5000;
			double coolingRate = 0.9999995;
			int numberOfIterations = 1000;
			int outLoopIterations = 1000000;
			
			
			SimulatedAnnealing mySA = new SimulatedAnnealing(startingTemp,numberOfIterations,coolingRate,myreader.getGraphDimention(), inputMatrix, outLoopIterations);
			System.out.println("Optimized cost is:" + mySA.getoptimizedRouteCost());
			
			
			
						
		}
}
