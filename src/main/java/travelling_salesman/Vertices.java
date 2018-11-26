/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman;

import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author usuario
 */
public class Vertices extends JPanel {

    private int vertices;
    private int[][] coordenadas;
    private double[][] custos; //ira armazenar a distancia entre todos os pontos
    private int largura, altura;

   // geraPonto ponto = new geraPonto(vertices, coordenadas);
    public Vertices(int vertices, int largura, int altura) {
        this.coordenadas = new int[vertices][2];
        this.custos = new double[vertices][vertices];
        this.vertices = vertices;
        this.largura = largura;
        this.altura = altura;
        gerar();
        calculaCusto();
    }
    public void paint(Graphics g) {
        super.paintComponents(g);
        geraPonto ponto = new geraPonto();

        //ponto.pontoSimple(g);
        //ponto.pontoAleatorio(g);
        pontoGerado(g);
    }
    public int getVertices() {
        return vertices;
    }
    public void setVertices(int vertices) {
        this.vertices = vertices;
    }
    public int[][] getCoordenadas() {
        return coordenadas;
    }
    public void setNumCoordenadas(int[][] coordenadas) {
        this.coordenadas = coordenadas;
    }
    public double[][] getCustos() {
        return custos;
    }
    public void setCustos(double[][] custos) {
        this.custos = custos;
    }
    public int getLargura() {
        return largura;
    }
    public void setLargura(int largura) {
        this.largura = largura;
    }
    public int getAltura() {
        return altura;
    }
    public void setAltura(int altura) {
        this.altura = altura;
    }
    //gera os numeros aleatórios
    private void gerar() {

        Random r = new Random();
        int x, y;
        for (int i = 0; i < this.vertices; i++) {
            x = r.nextInt(this.largura);
            y = r.nextInt(this.altura);
            this.coordenadas[i][0] = x;
            this.coordenadas[i][1] = y;
        }
    }
    public void calculaCusto() {

        double hip, catA, catO;
        for (int i = 0; i < this.vertices; i++) {
            for (int j = 0; j < this.vertices; j++) {
                if (i == j) {
                    hip = 0;
                } else {
                    catA = this.coordenadas[i][0] - this.coordenadas[j][0];
                    catA = Math.abs(catA);
                    catO = this.coordenadas[i][1] - this.coordenadas[j][1];
                    catO = Math.abs(catO);
                    hip = Math.sqrt(Math.pow(catA, 2) + Math.pow(catO, 2));
                }
                custos[i][j] = hip;
            }
        }
    }
    private void pontoGerado(Graphics g) {
        
         super.paintComponent(g);
         
        for (int i = 0; i < this.vertices; i++) {
            g.fillOval(this.coordenadas[i][0], this.coordenadas[i][1], 10, 10);
            //g.drawString(" " + (i + 1), this.Coordenadas[i][0], this.Coordenadas[i][1]);
            g.drawString(" " + (i), this.coordenadas[i][0], this.coordenadas[i][1]);
        }
        //Dezenhando umalinha interligando todos os vertices
        for(int i = 0; i < this.vertices; i ++){
            for(int j = 0; j < this.vertices; j++){
              if(i != j){
                  g.drawLine(this.coordenadas[i][0], this.coordenadas[i][1], 
                             this.coordenadas[j][0], this.coordenadas[j][1]);
              } 
            }
        }
    }
}
