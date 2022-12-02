package traveling_salesman;

import java.io.IOException;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Path;


public class InputReader {
	private String fileName,matrixInputType, inputGraphString;
	private Integer eof, startMatrixIndex, graphDimention;
	private int graphMatrix[][];
	
	
	public InputReader (String filename) {
		this.fileName = filename;
		this.graphDimention = -1;
		this.matrixInputType = "";
	}
	
	//Check if all needed variables are populated
	private boolean isClassComplete() {
		if (!this.graphDimention.equals(-1) && !this.matrixInputType.isEmpty()) {
			return true;
		} else return false;
	}

	
	private void createFromUpperDiagonal(String[] inputIntegers) {
		// Populate graph from the lower diagonal input		//TODO - Replace by log 
		System.out.println( "Creating matrix from lower diagonal input");

		Integer iterator = inputIntegers.length-1;
		for (int i = (this.graphDimention-1); i >= 0; i--) {
			for (int j= (this.graphDimention-1); j>=i; j--) {
					//if i = j, no need for check...
					this.graphMatrix[i][j] = Integer.parseInt(inputIntegers[iterator]);
					this.graphMatrix[j][i] = Integer.parseInt(inputIntegers[iterator]); 
					iterator --;
				}
			}
	}
	
	public String getGraphString() {
		// Transform the graph in a human readible string.
		String graph = "";
		for (int i=0;i< this.graphDimention;i++) {
			String line = "\n";
			for (int j=0;j< this.graphDimention;j++) {
				line = line +" "+  this.graphMatrix[i][j];
			}
			graph = graph + line;
		}
		
		return graph;
	}
	
	
	private void createFromLowerDiagonal(String[] inputIntegers) {
		// Populate graph from the lower diagonal input		//TODO - Replace by log 
		System.out.println( "Creating matrix from lower diagonal input");
		Integer iterator = 0;
		for (int i = 0; i <this.graphDimention; i++) {
			for (int j= 0; j<=i; j++) {
					//if i = j, no need for check...
					this.graphMatrix[i][j] = Integer.parseInt(inputIntegers[iterator]);
					this.graphMatrix[j][i] = Integer.parseInt(inputIntegers[iterator]); 
					iterator ++;
				}
			}
		}	

	
	
	public int[][] readInputMatrix() {
		// TODO - Replace by log
		System.out.println("Reading file: " + this.fileName);
		String fileContent = null;
		// Read input file content
		try { 
			Path filePath = Path.of(this.fileName);
			fileContent = Files.readString(filePath);
		} catch (IOException e) {
			e.printStackTrace();
			// Exit program in case read file breaks
			System.exit(0);
		}
		
		//Process file content
		String listFileContent[] = fileContent.split("\\n"); 
		int fileLinesNumber = listFileContent.length;
		
		
		//TODO - replace by log
		System.out.println("Readed file ended. \n"
				+ "File has " + fileLinesNumber +" lines.");
		
		boolean shouldExitLoop = false;
		int index = 0;
		//Find where the data starts and ends on the file
		while (!shouldExitLoop) {
			if (listFileContent[index].equals("EDGE_WEIGHT_SECTION"))
			{
				this.startMatrixIndex = index;
				
			}else if(listFileContent[index].equals("EOF")){
				this.eof = index;
				shouldExitLoop = true;
			}
			index ++;
		}
		
		// Find type of matrix, and graph size
		shouldExitLoop = false;
		index = 0;
		while (!shouldExitLoop) {
			String currentLine = listFileContent[index].split("\\s+")[0];
			
			if(currentLine.equals("EDGE_WEIGHT_FORMAT:")) {
				this.matrixInputType = listFileContent[index].split("\\s+")[1];
				//TODO - Replace by log
				System.out.println("Input matrix of type:"+ this.matrixInputType);
				
			}
			if(currentLine.equals("DIMENSION:")) {
				this.graphDimention = Integer.valueOf(listFileContent[index].split("\\s+")[1]);
				//TODO - Replace by log
				System.out.println("Graph dimension:"+ this.graphDimention);
			}
			shouldExitLoop = this.isClassComplete();
			index ++;
		}
		
		this.inputGraphString ="";
		for (int i=this.startMatrixIndex+1; i<this.eof;i++) {
			//create a big string with input data
			this.inputGraphString = this.inputGraphString  + listFileContent[i];
		}
		
		String[] inputStringsplit = this.inputGraphString.split(" "); 
		
		
		if (this.matrixInputType.equals("UPPER_DIAG_ROW")) {
			this.graphMatrix = new int[this.graphDimention][this.graphDimention];
			this.createFromUpperDiagonal(inputStringsplit);

		} else {
			this.graphMatrix = new int[this.graphDimention][this.graphDimention];
			this.createFromLowerDiagonal(inputStringsplit);

		}
		
		
		return this.graphMatrix;
	}
	
	public Integer getGraphDimention() {
		return this.graphDimention;
	}
}
