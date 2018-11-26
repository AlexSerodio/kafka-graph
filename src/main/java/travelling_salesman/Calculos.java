/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author usuario
 */
public class Calculos extends JPanel{
    
   private double[][] custos;
   private int[] rota;
   private int[][] coordenadas;
  
   //geraPonto ponto = new geraPonto(this.custos.length, coordenadas);

    public Calculos(double[][] custos, int[] rota, int[][] coordenadas) {
        this.custos = custos;
        this.rota = rota;
        this.coordenadas = coordenadas;
    }
   
    public void paint(Graphics g) {
        super.paintComponent(g);

       // ponto.pontoSimple(g);
        // ponto.pontoAleatorio(g);
       // ponto.pontoGerado(g);
            for (int i = 0; i < this.custos.length; i++) {
            //Dezenha um circlo zazado
            //g.fillOval(this.coordenadas[i][0], this.coordenadas[i][1], 10, 10);
            //Dezenha um circlo feichado
            g.fillOval(this.coordenadas[i][0], this.coordenadas[i][1], 10, 10);
            //g.drawString(" " + (i + 1), this.Coordenadas[i][0], this.Coordenadas[i][1]);
            g.drawString(" " + (i), this.coordenadas[i][0], this.coordenadas[i][1]);
        }
        //Dezenhando uma linha interligando todos os vertices
        int X1, Y1, X2, Y2 = 0;   
        int i;
        for(i = 0; i < this.rota.length - 1; i++){
            if(i == 0){
                //g.setColor(Color.red);
            }else{
                //g.setColor(Color.black);
            }
            X1 = this.coordenadas[this.rota[i]][0];
            Y1 = this.coordenadas[this.rota[i]][1];
            X2 = this.coordenadas[this.rota[i + 1]][0];
            Y2 = this.coordenadas[this.rota[i + 1]][1];
            g.drawLine(X1, Y1, X2, Y2);
        }
        //Voltando ao ponto de origem
        g.setColor(Color.blue);
            X1 = this.coordenadas[this.rota[i]][0];
            Y1 = this.coordenadas[this.rota[i]][1];
            X2 = this.coordenadas[this.rota[0]][0];
            Y2 = this.coordenadas[this.rota[0]][1];
            g.drawLine(X1, Y1, X2, Y2);
     
    /*    for(i = 0; i < this.custos.length - 1; i ++){
                  g.drawLine(this.coordenadas[i][0], this.coordenadas[i][1],  
                             this.coordenadas[i + 1][0], this.coordenadas[i + 1][1]);
              }
        //Volta para o ponto de origem
         g.drawLine(this.coordenadas[i][0], this.coordenadas[i][1],  
                    this.coordenadas[0][0], this.coordenadas[0][1]);
    */          
    }
}
