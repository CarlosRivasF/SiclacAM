/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

/**
 *
 * @author ZionSystem
 */
public class Configurador {

    private String url;
    private String baseDatos;
    private static Configurador miconfigurador = null;

    private Configurador(String url, String baseDatos) {
        this.url = url;
        this.baseDatos = baseDatos;
    }

    public static Configurador getConfigurador(String url, String baseDatos) {
        if (miconfigurador == null) {
            miconfigurador = new Configurador(url, baseDatos);
        }
        return miconfigurador;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseDatos() {
        return baseDatos;
    }

    public void setBaseDatos(String baseDatos) {
        this.baseDatos = baseDatos;
    }
}
