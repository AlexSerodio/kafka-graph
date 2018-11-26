package travelling_salesman;

public class Graph {

	private int [][] weights = new int[][] {
		{ 0, 86, 66, 69, 51, 89, 82, 46, 50, 8, 96, 15, 54, 60, 23 },
		{ 12, 0, 58, 71, 67, 31, 93,  8, 76, 6, 43, 97, 78, 72, 66 },
		{ 98, 93, 0, 91, 88, 0, 26, 49, 21, 40, 77, 83, 93, 11, 39 },
		{ 13, 47, 99, 0, 3, 75, 85, 92, 84, 61, 31, 24, 50, 45, 72 },
		{ 28, 58, 94, 69, 0, 45, 77, 70, 21, 80, 74, 5, 30, 98, 39 },
		{ 74, 1, 96, 29, 62, 0, 44, 39, 52, 0, 78, 43, 98, 76, 36 },
		{ 2, 58, 9, 48, 64, 88, 0, 85, 6, 94, 16, 59, 14, 72, 56 },
		{ 28, 71, 97, 53, 51, 13, 11, 0, 52, 24, 11, 35, 69, 84, 67 },
		{ 66, 69, 8, 19, 89, 70, 68, 82, 0, 95, 60, 67, 3, 78, 37 },
		{ 60, 14, 90, 28, 11, 56, 6, 26, 69, 0, 34, 36, 70, 55, 75 },
		{ 67, 3, 91, 51, 79, 29, 2, 75, 42, 72, 0, 86, 66, 42, 57 },
		{ 46, 67, 28, 84, 9, 6, 84, 36, 68, 69, 47, 0, 78, 81, 75 },
		{ 2, 80, 81, 64, 96, 56, 69, 72, 96, 26, 52, 94, 0, 84, 36 },
		{ 40, 65, 62, 97, 92, 18, 73, 57, 16, 57, 12, 10, 1, 0, 7 },
		{ 39, 38, 83, 25, 87, 97, 15, 66, 69, 14, 9, 15, 87, 99, 0 }
	};
	
	public int vertexAmount() {
		return weights.length;
	}
		
	public int[][] getWeights() {
		return weights;
	}
	
	public String printWeights() {
		StringBuilder content = new StringBuilder();
		for(int i = 0; i < weights.length; i++) {
			for(int j = 0; j < weights.length; j++) {
				content.append(weights[i][j]).append(", ");
			}
			content.append("\n");
		}
		return content.toString();
	}
	
	public String getVertexWeights(int vertex) {
		StringBuilder content = new StringBuilder();
		for(int i = 0; i < weights.length; i++) {
			if(i != 0)
				content.append(", ");
			content.append(weights[vertex][i]);
		}
		return content.toString();
	}
}
