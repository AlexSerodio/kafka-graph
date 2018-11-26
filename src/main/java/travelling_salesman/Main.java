package travelling_salesman;

public class Main {

	public static void main(String[] args) {

		Graph graph = new Graph();
		TravellingSalesman salesman = new TravellingSalesman(graph.getWeights());
		
		salesman.findShortestPath(3);
		
		System.out.println(graph.printWeights());
		System.out.println(salesman.printRoute());
	}
}
