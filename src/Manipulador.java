/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pixeldiff;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 *
 * @author mariela
 */
public class Manipulador {
    
    private final String ruta = "C://pixeldiff//tmp//"; 
    private String nombrePrueba;
    private String fecha;
    private String path;

    public Manipulador(String nombreProyecto) {
        Utils.crearCarpetaTMP();
        this.nombrePrueba = nombreProyecto;
        this.crearCarpeta(this.ruta, this.nombrePrueba);   
    }
    
    private void crearCarpeta(String ruta, String nombreProyecto){
        this.fecha = Utils.getFecha();
        String nombrePrueba = Utils.removerCaracteresInvalidos(nombreProyecto);
        
        String titulo;
        String tituloDir;
        
        if (nombrePrueba.isEmpty()) {
            titulo = "Sin Titulo";
            tituloDir = titulo.trim(); //trim = le saca los espacios al texto.
        } else if (nombrePrueba.length() > 50) {
            titulo = nombrePrueba.substring(0, 47) + "...";
            tituloDir = nombrePrueba.substring(0, 47).trim();
        } else {
            titulo = nombrePrueba;
            tituloDir = titulo.trim();
        }
        
        this.path = ruta + this.fecha + " - " + tituloDir + "//";
        Utils.crearCarpeta(path);
    }
    
    public Imagen capturarPantalla(WebDriver navegador){
        
        try {
            navegador.manage().window().maximize(); //manege: manipula el browser, maximiza pantalla
            
            Thread.sleep(10000);
            navegador.findElement(By.tagName("body")).click();
                       
            Robot robot = new Robot(); //maneja mi compu
            Thread.sleep(8000);
            
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(dimension);
            BufferedImage image = robot.createScreenCapture(rectangle); //guardo la captura en la variable dinamica
            
            RemoteWebDriver navegadorRemoto = (RemoteWebDriver) navegador; //1º se parsea el navegador a un navegador Remoto
            Capabilities cap = navegadorRemoto.getCapabilities();  //2º obtiene las opciones del navegador
            String nombreNavegador = cap.getBrowserName(); //Obtengo el nombre del navegador de las opciones
            
            this.guardarImagen(image, nombreNavegador + ".png");
            
            return new Imagen(nombreNavegador, image);
            
        } catch (AWTException ex) {
            System.out.println("No se encontro un dispositivo gráfico)");
            return null;
        } catch (Exception ex) {
            System.out.println("Error inesperado");
            return null;
        }
    }
    
    public Imagen capturarPantallaMaxScroll(WebDriver navegador){
        
        try {
            navegador.manage().window().maximize(); //manege: manipula el browser, maximiza pantalla
            
            Thread.sleep(10000);
            navegador.findElement(By.tagName("body")).click();
            Actions action = new Actions(navegador);
            action.sendKeys(Keys.F11).perform(); // pantalla completa
            
            Robot robot = new Robot(); //maneja mi compu
            Thread.sleep(8000);
            
            robot.mouseMove(450, 450);
            robot.mouseWheel(-500);
            Thread.sleep(1500);
            
            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle rectangle = new Rectangle(dimension);
            BufferedImage image = robot.createScreenCapture(rectangle); //guardo la captura en la variable dinamica
            
            RemoteWebDriver navegadorRemoto = (RemoteWebDriver) navegador; //1º se parsea el navegador a un navegador Remoto
            Capabilities cap = navegadorRemoto.getCapabilities();  //2º obtiene las opciones del navegador
            String nombreNavegador = cap.getBrowserName(); //Obtengo el nombre del navegador de las opciones
            
            this.guardarImagen(image, nombreNavegador + ".png");
            
            return new Imagen(nombreNavegador, image);
            
        } catch (AWTException ex) {
            System.out.println("No se encontro un dispositivo gráfico)");
            return null;
        } catch (Exception ex) {
            System.out.println("Error inesperado");
            return null;
        }
    }
    
    public boolean guardarImagen(BufferedImage imagen, String nombre){
        try {
            ImageIO.write(imagen, "png", new File(this.path + nombre)); //llamo al metodo write de la clase estatica IO
            return true;
        } catch (IOException ex) {
            System.out.println("No se puede guardar la imagen en " + nombre);
            return false;
        }
    }
    
    public Resultado compararImagenes(Imagen imagen1, Imagen imagen2){
        Comparador comparador = new Comparador();
        Resultado resultado = comparador.ejecutarAlgoritmoDeComparacion(imagen1, imagen2);
        resultado.guardarResultado(this.path + "resultado.png");
        return resultado;
    }
    
    public boolean crearReporte(Resultado resultado){
        
        String navegador1 = resultado.getNavegador1() ;
        String navegador2 = resultado.getNavegador2() ;
        int cantPixIguales = resultado.getCantPixTotal() - resultado.getCantPixDiferentes();
        int cantPixDiferentes= resultado.getCantPixDiferentes();
        
        return Utils.crearReporte(this.path + "reporte.html", navegador1, navegador2,
                this.nombrePrueba, this.fecha, cantPixIguales, cantPixDiferentes);

    }

}
