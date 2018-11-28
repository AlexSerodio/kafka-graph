package slave;

public class TravellingSalesman {

	private int[][] costs;
	private int[] path;

	public TravellingSalesman(int[][] weights) {
		this.costs = weights;
		path = new int[this.costs.length+1];
		
		for (int i = 0; i < this.costs.length; i++) {
			path[i] = -1;
		}
	}

	public int[] getPath() {
		return path;
	}
	
	private int[][] sortCosts(int vertex) {

		int[][] sorted = new int[this.costs.length][2];
		int invert;
		
		for (int i = 0; i < sorted.length; i++) {
			sorted[i][0] = i; 							// pega o indice
			sorted[i][1] = this.costs[vertex][i];		// pega o custo
		}
		
		// Ordenando de forma crecente
		for (int i = 0; i < sorted.length - 1; i++) {
			for (int j = i + 1; j < sorted.length; j++) {
				if (sorted[i][1] > sorted[j][1]) {
					
					// troca custo
					invert = sorted[i][1];
					sorted[i][1] = sorted[j][1];
					sorted[j][1] = invert;
					
					// troca indice
					invert = sorted[i][0];
					sorted[i][0] = sorted[j][0];
					sorted[j][0] = invert;
				}
			}
		}
		return sorted;
	}

	private boolean isInPath(int vertex) {
		boolean inPath = false;
		for (int i = 0; i < this.costs.length; i++) {
			if (this.path[i] == vertex)
				inPath = true;
		}
		return inPath;
	}
	
	public void findShortestPath (int startPoint) {
		path[0] = startPoint;
		path[path.length-1] = startPoint;
		findPath(startPoint, 0);
	}

	private void findPath(int currentVertex, int pathIndex) {
		int currentWeight[][] = sortCosts(currentVertex);
		int i = 0;
		
		if(i >= costs.length)
			return;
		
		while (isInPath(currentWeight[i][0])) {
			i++;
		}
		
		pathIndex++;
		this.path[pathIndex] = currentWeight[i][0];
		currentVertex = currentWeight[i][0];

		if (pathIndex < this.costs.length - 1)
			findPath(currentVertex, pathIndex);
	}
}
