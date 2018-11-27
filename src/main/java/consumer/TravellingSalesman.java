package consumer;

public class TravellingSalesman {

	private int[][] weights;
	private int[] route;

	public TravellingSalesman(int[][] weights) {
		this.weights = weights;
		route = new int[this.weights.length];
		
		for (int i = 0; i < this.weights.length; i++) {
			route[i] = -1;
		}
	}

	public int[] getRoute() {
		return route;
	}
	
	private int[][] sortWeights(int vertex) {

		int[][] sorted = new int[this.weights.length][2];
		int troca;
		
		for (int i = 0; i < sorted.length; i++) {
			sorted[i][0] = i; 							// pega o indice
			sorted[i][1] = this.weights[vertex][i];		// pega o custo
		}
		
		// Ordenando de forma crecente
		for (int i = 0; i < sorted.length - 1; i++) {
			for (int j = i + 1; j < sorted.length; j++) {
				if (sorted[i][1] > sorted[j][1]) {
					
					// troca custo
					troca = sorted[i][1];
					sorted[i][1] = sorted[j][1];
					sorted[j][1] = troca;
					
					// troca indice
					troca = sorted[i][0];
					sorted[i][0] = sorted[j][0];
					sorted[j][0] = troca;
				}
			}
		}
		return sorted;
	}

	private boolean isAlreadyInRoute(int vertex) {
		boolean isInRoute = false;
		for (int i = 0; i < this.weights.length; i++) {
			if (this.route[i] == vertex)
				isInRoute = true;
		}
		return isInRoute;
	}
	
	public void findShortestPath (int startPoint) {
		route[0] = startPoint;
		findRoute(startPoint, 0);
	}

	private void findRoute(int currentVertex, int routeIndex) {
		int currentWeight[][] = sortWeights(currentVertex);
		int i = 0;
		
		if(i >= weights.length)
			return;
		
		while (isAlreadyInRoute(currentWeight[i][0])) {
			i++;
		}
		
		routeIndex++;
		this.route[routeIndex] = currentWeight[i][0];
		currentVertex = currentWeight[i][0];

		if (routeIndex < this.weights.length - 1)
			findRoute(currentVertex, routeIndex);
	}
	
	public String printRoute() {
		StringBuilder content = new StringBuilder();

		for (int i = 0; i < route.length; i++)
			content.append(route[i]).append(", ");
		
		return content.toString();
	}
}
