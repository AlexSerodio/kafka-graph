package travelling_salesman;

public class Principal {

	public static void main(String[] args) {

		Graph graph = new Graph();

		Gulosa g = new Gulosa(graph.getWeights());
		g.findShortestPath(3);
		
		int[] route = g.getRota();
		String aux = "";

		for (int i = 0; i < route.length; i++) {
			if (i == 0)
				aux += route[i];
			else
				aux += ", " + route[i];
		}
		
		System.out.println(graph.printCustos());
		System.out.println(aux);
	}
}
