/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman;

import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author usuario
 */
public final class geraPonto {

    private static Vertices vert;

    protected void pontoSimple(Graphics g) {
        //Desenhando as linhas
        //g.drawOval(coordX, coordY, aaltura, largura);
        g.drawOval(100, 70, 10, 10);
        //g.drawString(priPonto, coordX, coordY);
        //escreve o numero um na tela
        g.drawString("1", 105, 75);
    }
    protected void pontoAleatorio(Graphics g) {

        Random r = new Random();
        int x = r.nextInt(500);
        int y = r.nextInt(300);
        g.drawOval(x, y, 10, 10);
        g.drawString("1", x + 15, y + 15);
    }
}
