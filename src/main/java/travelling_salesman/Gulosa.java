package travelling_salesman;

public class Gulosa {

	private int[][] custos;
	private int[] rota;

	public Gulosa(int[][] custos) {
		this.custos = custos;
		rota = new int[this.custos.length];
		
		for (int i = 0; i < this.custos.length; i++) {
			rota[i] = -1;
		}
	}

	public int[] getRota() {
		return rota;
	}
	
	private double[][] ordenaCusto(int ponto) {

		double[][] aux = new double[this.custos.length][2];
		double troca;
		
		for (int i = 0; i < aux.length; i++) {
			aux[i][0] = i; 							// pega o indice
			aux[i][1] = this.custos[ponto][i];		// pega o custo
		}
		
		// Ordenando de forma crecente
		for (int i = 0; i < aux.length - 1; i++) {
			for (int j = i + 1; j < aux.length; j++) {
				if (aux[i][1] > aux[j][1]) {
					
					// troca custo
					troca = aux[i][1];
					aux[i][1] = aux[j][1];
					aux[j][1] = troca;
					
					// troca indice
					troca = aux[i][0];
					aux[i][0] = aux[j][0];
					aux[j][0] = troca;
				}
			}
		}
		return aux;
	}

	private boolean usado(int ponto) {
		// Ponto é a próxima cidade a ser visitada
		boolean aux = false;
		for (int i = 0; i < this.custos.length; i++) {
			if (this.rota[i] == ponto)
				aux = true;
		}
		return aux;
	}
	
	public void findShortestPath (int startPoint) {
		rota[0] = startPoint;
		calculaRota(startPoint, 0);
	}

	private void calculaRota(int pontoAtual, int contRota) {
		double custoAtual[][] = ordenaCusto(pontoAtual);
		int i = 0;
		
		// verifica se o ponto já está incluído na rota
		while (usado((int) custoAtual[i][0]))
			i++;
		
		contRota++;
		this.rota[contRota] = (int) custoAtual[i][0];
		pontoAtual = (int) custoAtual[i][0];

		// Chamada recursiva até preencher a rota
		if (contRota < this.custos.length - 1)
			calculaRota(pontoAtual, contRota);
	}
}
