/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman;
/**
 *
 * @author usuario
 */
public class Gulosa {
    
    private double[][] custos;
    private int[] rota;
    private int[][] coordenadas;
    private int pontoPartida;
    
    public Gulosa(double[][] custos, int[][] coordenadas, int pontoPartida){
        this.custos = custos;
        this.coordenadas = coordenadas;
        this.pontoPartida = pontoPartida;                
        //calcula o tanaho do vetor rota
        rota = new int[this.custos.length];
        //Limpa vetor
        for(int i = 0; i < this.custos.length; i++){
            rota[i] = -1;
        }
        rota[0] = pontoPartida;
        //Calculando a rota
        calculaRota(pontoPartida, 0);
    }
    private double[][] OrdenaCusto(int ponto){
        
        double[][] aux = new double[this.custos.length][2];
        double troca;
        for(int i = 0; i < aux.length; i++){
            aux[i][0] = i; //pega o indice
            aux[i][1] = this.custos[ponto][i];//pega o custo
        }
        //Ordenando de forma crecente
        for(int i = 0; i < aux.length - 1; i++){
            for(int j = i + 1; j < aux.length; j++){
              if(aux[i][1] > aux[j][1]){
                  //traca custo
                  troca = aux[i][1];
                  aux[i][1] = aux[j][1];
                  aux[j][1] = troca;
                  //troca indice
                  troca = aux[i][0];
                  aux[i][0] = aux[j][0];
                  aux[j][0] = troca;
                  
              }  
            }
        }
         return aux;
    }
    private boolean usado(int ponto){
        //Ponto é a próxima cidade a ser visitada
        boolean aux = false;
        for(int i = 0; i < this.custos.length; i++){
            if(this.rota[i] == ponto){
                aux = true;
            }
        }
        return aux;
    }
    private void calculaRota(int pontoAtual, int contRota){
        
        double custoAtual[][] = OrdenaCusto(pontoAtual);
        int i = 0;
        //verifica se o ponto já está incluído na rota
        while(usado((int)custoAtual[i][0])){
            i++;
            }
        contRota++;
        this.rota[contRota] = (int)custoAtual[i][0];
        pontoAtual = (int)custoAtual[i][0];
        
        //Chamada recursiva até preencher a rota
        if(contRota < this.custos.length - 1){
            calculaRota(pontoAtual, contRota);
        }
        
    }
        public int[] getRota() {
        return rota;
    }
}
