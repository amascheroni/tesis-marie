/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixeldiff;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author mariela
 */
public class Comparador {
    
    
    public Resultado ejecutarAlgoritmoDeComparacion(Imagen img1, Imagen img2) {
        BufferedImage imagen1 = img1.getBufferedImage();
        BufferedImage imagen2 = img2.getBufferedImage();
        boolean resultado = true;
        Resultado resultadoFinal;
        int cont = 0;
        int pxDiff = 0;
        BufferedImage mapaDeCalor = null;
        
        if (imagen1.getWidth() == imagen2.getWidth() && imagen1.getHeight() == imagen2.getHeight()) {
            mapaDeCalor = new BufferedImage(imagen1.getWidth(), imagen1.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < imagen1.getWidth(); x++) {
                for (int y = 0; y < imagen1.getHeight(); y++) {
                    if (imagen1.getRGB(x, y) != imagen2.getRGB(x, y)) {
                        resultado = false;
                        mapaDeCalor = this.crearMapaDeCalor(mapaDeCalor, x, y);
                        pxDiff++;
                    }
                    cont++;
                }
            }
            
        } else {
            resultado = false;
        }
        
        resultadoFinal = new Resultado(resultado, pxDiff, cont, img1.getNombreNavegador(), img2.getNombreNavegador(), mapaDeCalor);
        return resultadoFinal;
    }
    
    public BufferedImage crearMapaDeCalor(BufferedImage imagen, int x, int y) {
        imagen.setRGB(x, y, Color.ORANGE.getRGB());
        return imagen;
    }
    
}