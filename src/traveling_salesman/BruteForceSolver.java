package traveling_salesman;
import java.util.List;
import java.util.*;  
import java.util.ArrayList;



public class BruteForceSolver {
	Integer graphSize, shortPathSize;
	int[][] graph;
	List<Integer>  costVector;
	
	
	public BruteForceSolver (int[][] graph, Integer graphSize) {
		this.graphSize = graphSize;
		this.graph = graph;
		
	}
	
	public List<List<Integer>> permute(int[] nums) {
	    List<List<Integer>> results = new ArrayList<List<Integer>>();
	    if (nums == null || nums.length == 0) {
	        return results;
	    }
	    List<Integer> result = new ArrayList<>();
	    dfs(nums, results, result);
	    return results;
	}

	public void dfs(int[] nums, List<List<Integer>> results, List<Integer> result) {
	    if (nums.length == result.size()) {
	        List<Integer> temp = new ArrayList<>(result);
	        results.add(temp);
	    }        
	    for (int i=0; i<nums.length; i++) {
	        if (!result.contains(nums[i])) {
	            result.add(nums[i]);
	            dfs(nums, results, result);
	            result.remove(result.size() - 1);
	        }
	    }
	}
	
	private List<Integer> getListOfIndexes (){
		List<Integer> localList = new ArrayList();
		for (Integer i=1; i<this.graphSize ; i++) {
			localList.add(i);
		}
		return localList;
	}
	
	public Integer getShortPath() {
		System.out.println("Start solving graph - size:" + this.graphSize);

		List<Integer> myListToSort = this.getListOfIndexes();
		int[] myIntListToPermute = myListToSort.stream().mapToInt(Integer::intValue).toArray();
		List<List<Integer>> allPossiblePaths = this.permute(myIntListToPermute);
		System.out.println("Solving for all " + allPossiblePaths.size() + " different paths");
		this.costVector = new ArrayList<Integer>();
		for(int i=0; i<allPossiblePaths.size();i++) {
			// Use permutation and add 0 in begin and end - Starts and ends on city 0
			List<Integer> currentCostList = new ArrayList<Integer>(0);
			currentCostList.addAll( allPossiblePaths.get(i));
			currentCostList.add(0);
			int[] indexesToPickCost = currentCostList.stream().mapToInt(Integer::intValue).toArray();
			Integer costForThisPath = 0;
			for (int j=1;j<indexesToPickCost.length;j++) {
				
				costForThisPath += this.graph[j-1][j];
			}
			this.costVector.add(costForThisPath);
			
		}
		this.shortPathSize = Collections.max(this.costVector);
		
		
		System.out.println("Start solving graph - Short path cost:" + this.shortPathSize);
		return this.shortPathSize;
		
	}

}
