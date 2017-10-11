/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixeldiff;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 * @author mariela
 */
public class Resultado {

	private boolean resultado;
	private int cantPixDiferentes;
	private int cantPixTotal;
	private String navegador1;
	private String navegador2;
	private BufferedImage mapaDeCalor;

	public Resultado(boolean resultado, int cantPixDiferentes, int cantPixTotal, String navegador1, String navegador2,
			BufferedImage mapaDeCalor) {
		this.resultado = resultado;
		this.cantPixDiferentes = cantPixDiferentes;
		this.cantPixTotal = cantPixTotal;
		this.navegador1 = navegador1;
		this.navegador2 = navegador2;
		this.mapaDeCalor = mapaDeCalor;
		System.out.println(mapaDeCalor);
	}

	public void guardarResultado(String path) {
		try {
			if (this.resultado == true) {
				BufferedImage exito = ImageIO.read(new File("C:\\pixeldiff\\utils\\exito.png"));
				ImageIO.write(exito, "png", new File(path));
				return;
			}

			if ((this.resultado == false) && (this.mapaDeCalor == null)) {
				BufferedImage fallo = ImageIO.read(new File("C:\\pixeldiff\\utils\\fallo.png"));
				ImageIO.write(fallo, "png", new File(path));
				return;
			}

			if ((this.resultado == false) && (this.mapaDeCalor != null)) {

				ImageIO.write(this.mapaDeCalor, "png", new File(path));

			}
		} catch (IOException ex) {
			System.out.println("Error de entrada/salida");
			System.out.println("No se puede guardar la imagen en la ruta especificada: " + path);
		}

	}

	/**
	 * @return the resultado
	 */
	public boolean isResultado() {
		return resultado;
	}

	/**
	 * @param resultado
	 *            the resultado to set
	 */
	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	/**
	 * @return the cantPixDiferentes
	 */
	public int getCantPixDiferentes() {
		return cantPixDiferentes;
	}

	/**
	 * @param cantPixDiferentes
	 *            the cantPixDiferentes to set
	 */
	public void setCantPixDiferentes(int cantPixDiferentes) {
		this.cantPixDiferentes = cantPixDiferentes;
	}

	/**
	 * @return the cantPixTotal
	 */
	public int getCantPixTotal() {
		return cantPixTotal;
	}

	/**
	 * @param cantPixTotal
	 *            the cantPixTotal to set
	 */
	public void setCantPixTotal(int cantPixTotal) {
		this.cantPixTotal = cantPixTotal;
	}

	/**
	 * @return the navegador1
	 */
	public String getNavegador1() {
		return navegador1;
	}

	/**
	 * @param navegador1
	 *            the navegador1 to set
	 */
	public void setNavegador1(String navegador1) {
		this.navegador1 = navegador1;
	}

	/**
	 * @return the navegador2
	 */
	public String getNavegador2() {
		return navegador2;
	}

	/**
	 * @param navegador2
	 *            the navegador2 to set
	 */
	public void setNavegador2(String navegador2) {
		this.navegador2 = navegador2;
	}

	/**
	 * @return the mapaDeCalor
	 */
	public BufferedImage getMapaDeCalor() {
		return mapaDeCalor;
	}

	/**
	 * @param mapaDeCalor
	 *            the mapaDeCalor to set
	 */
	public void setMapaDeCalor(BufferedImage mapaDeCalor) {
		this.mapaDeCalor = mapaDeCalor;
	}

}
