/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class Principal {

    public static void main(String[] args) {

        int largura  = 1200;
        int altura = 900;
        Vertices ponto = new Vertices(15, largura, altura);
        JFrame aplication = new JFrame("Resultado");
        aplication.add(ponto);
        aplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplication.setSize(largura, altura);
        aplication.setVisible(true);
        
        Gulosa g = new Gulosa(ponto.getCustos(), ponto.getCoordenadas(), 3);
        int[] rota = g.getRota();
        String aux = "";
        
        for(int i = 0; i < rota.length; i++){
            if(i == 0){
                aux += rota[i]; 
            }else{
            aux += " , " + rota[i];
            }
        }
         JOptionPane.showMessageDialog(null, aux);
        
        //Visualiza custo
        String aux1 = "";
        double m[][];
        m = ponto.getCustos();

        for(int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                aux1 += " " + m[i][j];
                if (j == m.length - 1) {
                    aux1 += "\n" + m[i][j];
                }
            }
        }
       // JOptionPane.showMessageDialog(null, aux1);
        
        Calculos C = new Calculos(ponto.getCustos(), g.getRota(), ponto.getCoordenadas());
        JFrame solucao = new JFrame("Caminho feliz");
        solucao.add(C);
        solucao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        solucao.setSize(largura, altura);
        solucao.setVisible(true);
    }
}
